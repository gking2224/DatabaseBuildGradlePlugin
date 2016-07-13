package me.gking2224.dbgp.plugin

import org.gradle.api.Project;
import org.slf4j.LoggerFactory


class DatabaseBuildPluginExtension {
    
    def logger = LoggerFactory.getLogger(DatabaseBuildPluginExtension.class)
    
    def project
    
    public DatabaseBuildPluginExtension(def project) {
        this.project = project
        project.ext.dbprofile = [:]
        project.dbprofile[Profile.ALL] = new Profile()
        
        project.afterEvaluate {
            project.dbprofile.each {k,v->
                v.getProperties().each {prop,val->
                    if (val != null && Closure.isAssignableFrom(val.class)) {
                        v[prop] = project.resolveValue(val)
                    }
                }
                
            }
        }
    }
    
    def profile(String name, Closure c) {
        
        def profile
        if (Profile.ALL == name) profile = project.dbprofile[Profile.ALL]
        else profile = new Profile(project.dbprofile[Profile.ALL])
        
        c.delegate = profile
        c()
        
        project.dbprofile[name] = profile
    }
}
