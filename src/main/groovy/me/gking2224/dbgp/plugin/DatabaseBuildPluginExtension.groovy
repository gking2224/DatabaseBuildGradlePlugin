package me.gking2224.dbgp.plugin

import org.slf4j.LoggerFactory


class DatabaseBuildPluginExtension {
    def logger = LoggerFactory.getLogger(DatabaseBuildPluginExtension.class)
    
    def profiles = [:]
    
    def profile(String name, Closure c) {
        logger.info("Creating profile $name")
        
        def profile = new Profile()
        c.delegate = profile
        c()
        
        profiles[name] = profile
        
    }
}
