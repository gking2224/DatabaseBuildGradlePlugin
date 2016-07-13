package me.gking2224.dbgp.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class DatabaseBuildGradlePlugin implements Plugin<Project> {
    
    def project
    
	void apply(Project project) {
		project.extensions.create("dbconfig", DatabaseBuildPluginExtension.class, project)
        def dir = project.file("db")
        project.ext.dbDir = dir 
	}
}