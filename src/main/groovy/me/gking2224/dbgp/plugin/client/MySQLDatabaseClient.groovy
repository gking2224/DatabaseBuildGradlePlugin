package me.gking2224.dbgp.plugin.client

class MySQLDatabaseClient implements DatabaseClientHelper {
    
    def final executablePaths = ["/usr/bin/mysql", "/usr/local/bin/mysql", "/usr/local/mysql/bin/mysql"]

    @Override
    def getClientCommandLineArgs(def host, def port, def username, def databaseName) {
        
        [getExecutable(),
            "-v",
            "-h", host,
            "-P", port,
            "-u", username,
            "-D", databaseName]
    }

    @Override
    def getExecutable() {
        executablePaths.find { new File(it).exists() }
    }

    @Override
    def getPasswordCommandLineArgs(def password) {
        ["--password=$password"]
    }

    @Override
    def getInlineStatementCommaneLineArgs(def statement) {
        ["-e", statement]
    }
}
