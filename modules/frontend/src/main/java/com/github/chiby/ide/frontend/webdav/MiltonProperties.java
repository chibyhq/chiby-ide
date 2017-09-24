package com.github.chiby.ide.frontend.webdav;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Configuration properties required by Milton.
 */
@Component
@ConfigurationProperties(prefix = "milton",ignoreUnknownFields = true)
public class MiltonProperties {

    /**
     * Resources with this as the first part of their path will not be served
     * from Milton. Instead, this filter will allow filter processing to
     * continue so they will be served by JSP or a servlet
     */
    List<String> excludePaths = new ArrayList<>();

    
    public MiltonProperties(){
    	excludePaths.addAll(Arrays.asList(new String[]{
    			"/ide",
    			"/static",
    		    "/templates",
    		    "/management",
    		    "/webjars"
    	}));
    }
    /**
     *
     */
    String filesystemroot;

    public String getFilesystemroot() {
        return filesystemroot;
    }

    public void setFilesystemroot(String filesystemRoot) {
        this.filesystemroot = filesystemRoot;
    }

    public List<String> getExcludepaths() {
        return excludePaths;
    }

    public void setExcludepaths(List<String> excludePaths) {
        this.excludePaths = excludePaths;
    }

}