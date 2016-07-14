package me.gking2224.dbgp.plugin.task

import me.gking2224.buildtools.plugin.HasResolvableObjects

import org.gradle.api.DefaultTask


abstract class DatabaseConnectTask extends DefaultTask 
implements HasResolvableObjects {
    
//	def profile = "default"
//    
//    def getProfileObject(def p) {
//        if (p == null) p = this.profile
//        logger.trace "getProfileObject for $p"
//        project.dbprofile[p]
//	}
//    
//    def getProfileObject() {
//        return getProfileObject(this.profile)
//    }
    
    def username
    def password
    def host
    def port
    def databaseName

    @Override
    def void resolveObjects() {
        doResolveObjects()
    }
    
    abstract def doResolveObjects();
}
