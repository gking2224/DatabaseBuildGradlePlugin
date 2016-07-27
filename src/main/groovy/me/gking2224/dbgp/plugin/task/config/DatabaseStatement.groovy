package me.gking2224.dbgp.plugin.task.config

import java.lang.ProcessBuilder.Redirect;

class DatabaseStatement extends DatabaseExecutable {

    def statements = []
    
    def DatabaseStatement(def task) {
        super(task)
    }
    
    def doExecute() {
        statements.each{
            executeStatement(it)
        }
    }
    
    def executeStatement(def s) {
        
        def exec = getExecutableCommands()
        exec = appendInlineStatement(exec, s)
        def out = new StringBuffer()
        project.dryRunExecute("Not executing: $exec", {
            logger.info "Executing $exec"
            exec = appendPassword(exec)
            ProcessBuilder pb = new ProcessBuilder(exec as String[]).redirectErrorStream(true)
            Process process = pb.start()
            process.consumeProcessOutputStream(out)
            process.waitFor()
            if (process.exitValue() != 0) {
                if (!failOnError) {
                    logger.warn("Got non-zero exit value executing '${s}'")
                    logger.warn(out.toString())
                }
                else {
                    logger.error(out.toString())
                    assert process.exitValue() == 0 : "Error executing '${s}'"
                }
            }
            else {
                logger.debug(out.toString())
            }
        })
    }
    
    def setStatement(def stmt) {
        statements << stmt
    }
    
    def doResolve() {
        logger.info("statements.doResolve()")
        statements = statements.collect{project.resolveValue(it)}
        logger.info("statements now: $statements")
        
    }
}
