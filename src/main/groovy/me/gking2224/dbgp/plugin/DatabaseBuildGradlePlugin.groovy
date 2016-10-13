package me.gking2224.dbgp.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class DatabaseBuildGradlePlugin implements Plugin<Project> {
    
    def project
    
    def configurers = []
    
	void apply(Project project) {
        def dir = project.file("db")
        project.ext.dbDir = dir
         
		project.extensions.create("dbconfig", DatabaseBuildPluginExtension.class, project)
        
        configurers << new DatabaseBuildTasks(project)
        
        configurers.each {c->
            c.configureProject()
        }
	}
}