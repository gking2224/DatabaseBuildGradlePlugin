package me.gking2224.dbgp.plugin.task.config

import me.gking2224.buildtools.util.GroovyUtil
import me.gking2224.dbgp.plugin.task.DatabaseConnectTask

import org.slf4j.LoggerFactory

abstract class DatabaseExecutable {

//    def profile
    def host
    def username
    def password
    def port
    def databaseName
    
    def gu = GroovyUtil.instance()
    
    def logger = LoggerFactory.getLogger(this.class)
    DatabaseConnectTask task
    def project
    def failOnError = true
    def dbClientClassName
    def _client
    
    def DatabaseExecutable(DatabaseConnectTask task) {
        this.task = task
        this.project = task.project
    }
    
    def execute() {
        _client = getClientImplementation()
        doExecute()
    }
    
    def resolveObjects() {
        host = gu.resolveValue(host)
        username = gu.resolveValue(username)
        port = gu.resolveValue(port)
        password = gu.resolveValue(password)
        databaseName = gu.resolveValue(databaseName)
        doResolve()
    }
    
    def doResolve() {
        // override in child classes
    }
    
    def getExecutableCommands() {
        _client.getClientCommandLineArgs(
            host?:task.host, port?:task.port,
            username?:task.username, databaseName?:task.databaseName)
    }
    
    def appendPassword(def commands) {
        _client.getPasswordCommandLineArgs(password?:task.password).each {
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
        if (_client == null) _client = Class.forName(dbClientClassName?:task.dbClientClassName).newInstance()
        _client
    }
}
