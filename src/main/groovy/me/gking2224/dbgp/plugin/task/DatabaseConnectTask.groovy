package me.gking2224.dbgp.plugin.task

import me.gking2224.dbgp.plugin.DatabaseBuildPluginExtension

import org.gradle.api.DefaultTask


abstract class DatabaseConnectTask extends DefaultTask {
	def profile = "default"
    
    def getProfileObject(def profile) {
        if (profile == null) profile = this.profile
        logger.trace "getProfileObject for $profile"
        DatabaseBuildPluginExtension extension =
            project.extensions.getByType(DatabaseBuildPluginExtension.class)
        return extension.profiles[profile]
	}
    
    def getProfileObject() {
        return getProfileObject(this.profile)
    }
}
