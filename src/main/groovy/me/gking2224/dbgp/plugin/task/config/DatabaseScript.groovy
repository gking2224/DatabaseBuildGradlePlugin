package me.gking2224.dbgp.plugin.task.config

import me.gking2224.buildtools.util.FileHelper;

class DatabaseScript extends DatabaseExecutable {

    def files = []
    def pattern
    def dir
    
    def DatabaseScript(def task) {
        super(task)
    }
    
    def setFile(def f) {
        files = [f]
    }
    
    def doResolve() {
        logger.info("Resolving files: pattern=$pattern dir=$dir file=$files")
        if (files.size() == 0 && pattern != null && dir != null) {
            files = resolvePattern()
        }
        else if (pattern != null || dir != null) {
            throw new IllegalStateException("Cannot specify files as well as dir/pattern")
        }
        files = files.collect{resolveFile(it)}
    }
    
    def resolvePattern() {
        project.filesFromPattern(dir, pattern).sort{it.name}
    }
    
    def resolveFile(def f) {
        assert f != null : "Null file"
        if ([String,GString].any{it.isAssignableFrom(f.class)}) {
            logger.info "String to file $f"
            new File(f)
        }
        else if (File.isAssignableFrom(f.class)){
            logger.info "File: $f"
            f
        }
        else if (Closure.isAssignableFrom(f.class)) {
            logger.info "Resolve closure"
            resolveFile(f())
        }
        else throw new IllegalStateException("Unknown type ${f.class}")
    }
    
    def doExecute() {
        files.each{f->
            logger.info "Processing file $f"
            executeScript(f)
        }
    }
    def executeScript(def f) {
        f = project.filteredFile(f, [task:task, project:project])
        
        File outputFile = new File("${f.absolutePath}.out")
        def exec = getExecutableCommands()
        project.dryRunExecute("Not executing: $exec", {
            outputFile.withOutputStream {out->
                logger.info "Executing process: $exec"
                exec = appendPassword(exec)
                ProcessBuilder pb = new ProcessBuilder(exec as String[]).redirectErrorStream(true)
                Process process = pb.start()
                process.withOutputStream {pos->
                    f.withInputStream {fis->
                        byte[] buffer = new byte[1024];
                        int read = 0;
                        while((read = fis.read(buffer)) != -1) {
                            pos.write(buffer, 0, read);
                        }
                    }
                }
                process.consumeProcessOutputStream(out)
                process.waitFor()
                if (process.exitValue() != 0) {
                    if (!failOnError) {
                        logger.warn("Got non-zero exit value executing ${f.absolutePath}, see ${outputFile.absolutePath}")
                    }
                    else {
                        logger.error("See log file at ${outputFile.absolutePath}")
                        assert process.exitValue() == 0 : "Error executing ${f.absolutePath}"
                    }
                }
            }
        })
    }
}
