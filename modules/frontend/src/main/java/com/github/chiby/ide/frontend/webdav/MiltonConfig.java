package com.github.chiby.ide.frontend.webdav;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.milton.config.HttpManagerBuilder;
import io.milton.http.ResourceFactory;
import io.milton.http.fs.FileSystemResourceFactory;
import io.milton.http.fs.NullSecurityManager;
import io.milton.http.http11.DefaultHttp11ResponseHandler;

/**
 *
 */
@Configuration
@EnableConfigurationProperties(MiltonProperties.class)
public class MiltonConfig {

    @Autowired
    MiltonProperties miltonProperties;

    @Bean
    FilterRegistrationBean filterRegistrationBean()
    {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(springMiltonFilterBean());
        bean.addUrlPatterns("/*");
        return bean;
    }

    @Bean
    SpringMiltonFilterBean springMiltonFilterBean()
    {
        return new SpringMiltonFilterBean();
    }

    @Bean
    ResourceFactory resourceFactory()
    {
        FileSystemResourceFactory fs = new FileSystemResourceFactory();
        fs.setRoot(new File(miltonProperties.getFilesystemroot()));
        fs.setSecurityManager(new NullSecurityManager());
        return fs;
    }

    @Bean
    HttpManagerBuilder httpManagerBuilder()
    {
        HttpManagerBuilder builder = new HttpManagerBuilder();
        builder.setResourceFactory(resourceFactory());
        builder.setBuffering(DefaultHttp11ResponseHandler.BUFFERING.never);
        builder.setEnableCompression(false);
        return builder;
    }
}