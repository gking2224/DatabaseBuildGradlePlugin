package me.gking2224.dbgp.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class DatabaseBuildGradlePlugin implements Plugin<Project> {

	void apply(Project project) {
		project.extensions.create("dbconfig", DatabaseBuildPluginExtension)
        def dir = project.file("db")
        project.ext.dbDir = dir 
	}
}

class DatabaseBuildPluginExtension {
	String host
	int port
	String username
	String password
	String name
	String classpath
	String driver
}