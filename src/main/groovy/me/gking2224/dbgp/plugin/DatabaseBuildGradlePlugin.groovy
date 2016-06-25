package me.gking2224.dbgp.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class DatabaseBuildGradlePlugin implements Plugin<Project> {

	void apply(Project project) {
		println("applying databasebuild plugin")
		project.task('hello') << {
			println "Hello from databasebuild plugin - updated"
		}
	}
}
