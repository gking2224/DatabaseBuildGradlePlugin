package me.gking2224.dbgp.plugin.task.config

import me.gking2224.dbgp.plugin.task.DatabaseConnectTask;

import org.slf4j.LoggerFactory;

abstract class DatabaseExecutable {

    def profile
    def logger = LoggerFactory.getLogger(this.class)
    DatabaseConnectTask task
    def project
    def failOnError = true
    def dbClientClassName="me.gking2224.dbgp.plugin.client.MySQLDatabaseClient"
    def _client
    
    def DatabaseExecutable(DatabaseConnectTask task) {
        this.task = task
        this.project = task.project
    }
    
    def execute() {
        _client = getClientImplementation()
        profile = task.getProfileObject(profile)
        doExecute()
    }
    
    def resolveObjects() {
        doResolve()
    }
    
    def doResolve() {
        
    }
    
    def getExecutableCommands() {
        _client.getClientCommandLineArgs(profile.host, profile.port, profile.username, profile.databaseName)
    }
    
    def appendPassword(def commands) {
        _client.getPasswordCommandLineArgs(profile.password).each {
            commands << it
        }
        commands
    }
    
    def appendInlineStatement(def commands, def statement) {
        _client.getInlineStatementCommaneLineArgs(statement).each {
            commands << it
        }
        commands
    }
    
    abstract def doExecute();
    
    def getClientImplementation() {
        if (_client == null) _client = Class.forName(dbClientClassName).newInstance()
        _client
    }
}
