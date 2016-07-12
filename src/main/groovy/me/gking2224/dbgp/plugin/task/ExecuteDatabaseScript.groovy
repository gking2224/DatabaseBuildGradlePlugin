package me.gking2224.dbgp.plugin.task

import me.gking2224.buildtools.plugin.HasResolvableObjects;
import me.gking2224.dbgp.plugin.task.config.DatabaseScript
import me.gking2224.dbgp.plugin.task.config.DatabaseStatement

import org.gradle.api.tasks.TaskAction


class ExecuteDatabaseScript extends DatabaseConnectTask 
implements HasResolvableObjects {
	
    def executables = []
	
	@TaskAction
	def doAction() {
        executables.each {exe ->
            
            exe.execute()
        }
	}
    
    def statement(Closure c) {
        logger.info("Configuring statement closure")
        DatabaseStatement statement = new DatabaseStatement(this)
        c.delegate = statement
        c.resolveStrategy = Closure.DELEGATE_FIRST
        c()
        executables << statement
    }
    
    def statement(String stmt) {
        logger.info("Configuring statement $stmt")
        DatabaseStatement statement = new DatabaseStatement(this)
        statement.statement = stmt
        executables << statement
    }
    
    def file(def f) {
        logger.info("Configuring file")
        DatabaseScript script = new DatabaseScript(this)
        script.files = [f]
        executables << script
    }
    
    def file(def dir, String name) {
        logger.info("Configuring file dir/name")
        DatabaseScript script = new DatabaseScript(this)
        script.dir = dir
        script.pattern = name
        executables << script
    }
    
    def files(def dir, String p) {
        logger.info("Configuring files: dir/$p")
        DatabaseScript script = new DatabaseScript(this)
        script.dir = dir
        script.pattern = p
        executables << script
    }
    
    def files(Closure c) {
        DatabaseScript script = new DatabaseScript(this)
        c.delegate = script
        c.resolveStrategy = Closure.DELEGATE_FIRST
        c()
        executables << script
    }

    @Override
    public void resolveObjects() {
        executables.each {
            it.resolveObjects()
        }
        
    }
}