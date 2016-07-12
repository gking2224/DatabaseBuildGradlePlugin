package me.gking2224.dbgp.plugin.task.config

import me.gking2224.dbgp.plugin.task.DatabaseConnectTask;

import org.slf4j.LoggerFactory;

abstract class DatabaseExecutable {

    def profile
    def logger = LoggerFactory.getLogger(this.class)
    DatabaseConnectTask task
    def project
    def failOnError = true
    
    def DatabaseExecutable(DatabaseConnectTask task) {
        this.task = task
        this.project = task.project
    }
    
    def execute() {
        profile = task.getProfileObject(profile)
        doExecute()
    }
    
    def resolveObjects() {
        doResolve()
    }
    
    def doResolve() {
        
    }
    
    def getExecutableCommands() {
        ["mysql",
            "-h", "${profile.host}",
            "-P", "${profile.port}",
            "-u", "${profile.username}",
            "-D", "${profile.databaseName}"]
    }
    
    def appendPassword(def commands) {
        commands << "--password=${profile.password}"
        commands
    }
    
    abstract def doExecute();
}
