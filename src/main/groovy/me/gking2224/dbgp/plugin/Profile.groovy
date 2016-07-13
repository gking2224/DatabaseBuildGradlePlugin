package me.gking2224.dbgp.plugin

class Profile {
    
    static final String ALL = "all"

    final def parent
    def databaseName
    def driverClassName
    def host
    def port = 3306
    def username
    def password
    def url
    
    def Profile() {
        
    }
    
    def Profile(Profile parent) {
        this.parent = parent
    }
    
    def getDatabaseName() {
        nullOrParentValue(databaseName, "databaseName")
    }

    def getDriverClassName() {
        nullOrParentValue(driverClassName, "driverClassName")
    }

    def getHost() {
        nullOrParentValue(host, "host")
    }

    def getPort() {
        nullOrParentValue(port, "port")
    }

    def getUsername() {
        nullOrParentValue(username, "username")
    }

    def getPassword() {
        nullOrParentValue(password, "password")
    }

    def getUrl() {
        nullOrParentValue(url, "url")
    }
    
    def nullOrParentValue(def value, def name) {
        if (value == null && parent != null) parent[name]
        else value
    }
}
