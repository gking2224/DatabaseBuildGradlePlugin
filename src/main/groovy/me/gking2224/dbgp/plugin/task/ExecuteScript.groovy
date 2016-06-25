package me.gking2224.dbgp.plugin.task

import org.gradle.api.tasks.TaskAction

class ExecuteScript extends DatabaseConnectTask {
	
	@TaskAction
	def doAction() {
		print "Connecting to database ${project.dbconfig.host}:${project.dbconfig.port}/"
		print "${project.dbconfig.name} as ${project.dbconfig.username}"
		println
	}
}