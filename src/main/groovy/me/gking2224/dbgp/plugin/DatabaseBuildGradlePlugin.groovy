package me.gking2224.dbgp.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class DatabaseBuildGradlePlugin implements Plugin<Project> {

	void apply(Project project) {
		project.task('hello') << {
			println "Hello from databasebuild plugin"
		}
	}
}
