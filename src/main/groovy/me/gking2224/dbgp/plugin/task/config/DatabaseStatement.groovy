package me.gking2224.dbgp.plugin.task.config

import java.lang.ProcessBuilder.Redirect;

class DatabaseStatement extends DatabaseExecutable {

    def statement
    
    def DatabaseStatement(def task) {
        super(task)
    }
    
    def doExecute() {
        logger.info "Executing statement $statement"
        executeStatement(statement)
    }
    
    def executeStatement(def s) {
        
        def exec = getExecutableCommands()
        exec << "-e" << s
        def out = new StringBuffer()
        project.dryRunExecute("Not executing: $exec", {
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
                    assert process.exitValue() == 0 : "Error executing '${s}'"
                }
            }
            else {
                logger.debug(out.toString())
            }
        })
    }
}
