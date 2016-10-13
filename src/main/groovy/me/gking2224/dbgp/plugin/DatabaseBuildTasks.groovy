package me.gking2224.dbgp.plugin

import me.gking2224.buildtools.plugin.AbstractProjectConfigurer
import me.gking2224.dbgp.plugin.task.ExecuteDatabaseScript

import org.gradle.api.Project
import org.gradle.api.tasks.Copy

class DatabaseBuildTasks extends AbstractProjectConfigurer {

    
    def DatabaseBuildTasks(Project p) {
        super(p)
    }
    
    def configureProject() {
        embeddedDb()
        makeDb()
        deployDbTasks()
    }
    
    def embeddedDb() {

        project.task("copyEmbeddedDbScripts", type: Copy) {
            from 'db'
            into 'build/resources/main/db'
            include '**/*.sql'
        }
        project.tasks.classes.dependsOn project.tasks.copyEmbeddedDbScripts
    }
    
    def makeDb() {
        
        project.task("makeDatabase", type:ExecuteDatabaseScript) {
            port = {project.envProps.database.port}
            host = {project.envProps.database.host}
            databaseName = {project.envProps.database.root.name}
            username = {project.envProps.database.root.username}
            password = {project.envProps.database.root.password}
            ext.appDatabaseUser = {project.envProps.database.username}
            ext.appDatabasePassword = {project.envProps.database.password}
            ext.appDatabaseName = {project.envProps.database.name}
            statement {
                failOnError = false
                statement = {"drop user '${appDatabaseUser}'@'localhost'"}
                statement = {"drop user '${appDatabaseUser}'@'%'"}
            }
            file {project.createDatabaseScript}
        }
        project.tasks.makeDatabase.doFirst {
            if (project.env == "prod") assert project.hasProperty("forceProd")
        }
    }
    
    def deployDbTasks() {
        
        project.task("makeSchema", type: ExecuteDatabaseScript) {
            port = {project.envProps.database.port}
            host = {project.envProps.database.host}
            databaseName = {project.envProps.database.name}
            username = {project.envProps.database.username}
            password = {project.envProps.database.password}
            files {
                dir = new File(project.dbDir, "model")
                pattern= /.*.sql/
            }
        }
        project.tasks.makeSchema.mustRunAfter project.tasks.makeDatabase
        project.tasks.makeSchema.doFirst {
            if (project.env == "prod") assert project.hasProperty("forceProd")
        }
        
        project.task("deployDbCode", type: ExecuteDatabaseScript) {
            port = {project.envProps.database.port}
            host = {project.envProps.database.host}
            databaseName = {project.envProps.database.name}
            username = {project.envProps.database.username}
            password = {project.envProps.database.password}
            files {
                dir = new File(project.dbDir, "code")
                pattern= /.*.sql/
            }
        }
        
        project.tasks.deployDbCode.mustRunAfter project.tasks.makeSchema
        
        project.task("deployDbSeedData", type:ExecuteDatabaseScript) {
            port = {project.envProps.database.port}
            host = {project.envProps.database.host}
            databaseName = {project.envProps.database.name}
            username = {project.envProps.database.username}
            password = {project.envProps.database.password}
            files {
                dir = new File(project.dbDir, "seedData")
                pattern= /.*.sql/
            }
        }
        project.tasks.deployDbSeedData.mustRunAfter project.tasks.deployDbCode
        
        project.task("deployDbFull", dependsOn:[project.tasks.makeSchema, project.tasks.deployDbCode, project.tasks.deployDbSeedData])
    }
}