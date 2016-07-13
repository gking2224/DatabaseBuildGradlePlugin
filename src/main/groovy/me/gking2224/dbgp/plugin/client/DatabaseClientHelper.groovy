package me.gking2224.dbgp.plugin.client;

/**
 * @author gk
 *
 */
public interface DatabaseClientHelper {

    def getClientCommandLineArgs(def host, def port, def username, def databaseName)
    
    def getPasswordCommandLineArgs(def password)
    
    def getInlineStatementCommaneLineArgs(def statement)
    
    def getExecutable()
}
