package me.gking2224.dbgp.plugin.task

import org.gradle.api.tasks.TaskAction

class ExecuteScript extends DatabaseConnectTask {
	
	def scriptFile
	
	@TaskAction
	def doAction() {
		print "Connecting to database ${project.dbconfig.host}:${project.dbconfig.port}/"
		print "${project.dbconfig.name} as ${project.dbconfig.username}"
		println ""
		
		def sqlFile = project.file(scriptFile)
		ant.sql(classpath: project.dbconfig.classpath,
			driver: project.dbconfig.driver,
			url: "jdbc:mysql://${project.dbconfig.host}:${project.dbconfig.port}/${project.dbconfig.name}",
			userid: project.dbconfig.username,
			password: project.dbconfig.password,
			src: sqlFile)
	}
}