package me.gking2224.dbgp.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class DatabaseBuildGradlePlugin implements Plugin<Project> {

	void apply(Project project) {
		project.extensions.create("dbconfig", DatabaseBuildPluginExtension.class)
        def dir = project.file("db")
        project.ext.dbDir = dir 
        
        
        project.ext.dbprofile = {String profile->
            
            DatabaseBuildPluginExtension extension =
                project.extensions.getByType(DatabaseBuildPluginExtension.class)
            return extension.profiles[profile]
        }
	}
}