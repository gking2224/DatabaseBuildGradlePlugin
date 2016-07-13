package me.gking2224.dbgp.plugin.task

import me.gking2224.buildtools.plugin.HasResolvableObjects;
import me.gking2224.dbgp.plugin.task.config.DatabaseScript
import me.gking2224.dbgp.plugin.task.config.DatabaseStatement

import org.gradle.api.tasks.TaskAction


class ExecuteDatabaseScript extends DatabaseConnectTask {
	
    def executables = []
	
	@TaskAction
	def doAction() {
        executables.each {exe ->
            exe.execute()
        }
	}
    
    def statement(Closure c) {
        DatabaseStatement statement = new DatabaseStatement(this)
        c.delegate = statement
        c.resolveStrategy = Closure.DELEGATE_FIRST
        c()
        executables << statement
    }
    
    def statement(String stmt) {
        DatabaseStatement statement = new DatabaseStatement(this)
        statement.statement = stmt
        executables << statement
    }
    
    def file(def f) {
        DatabaseScript script = new DatabaseScript(this)
        script.files = [f]
        executables << script
    }
    
    def file(def dir, String name) {
        DatabaseScript script = new DatabaseScript(this)
        script.dir = dir
        script.pattern = name
        executables << script
    }
    
    def files(def dir, String p) {
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
    
    def doResolveObjects() {
        executables.each {
            it.resolveObjects()
        }
    }
}