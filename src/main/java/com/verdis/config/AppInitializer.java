package com.verdis.config;

import com.verdis.config.security.SecurityConfig;
import com.verdis.config.security.SessionConfig;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.FilterRegistration;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletRegistration;
import lombok.NonNull;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import java.util.EnumSet;

public class AppInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(@NonNull ServletContext servletContext) {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(AppConfig.class, SecurityConfig.class, WebConfig.class, SessionConfig.class);
        context.setServletContext(servletContext);

        context.refresh();

        servletContext.addListener(new ContextLoaderListener(context));

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(context));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        FilterChainProxy springSecurityFilterChain = context.getBean(FilterChainProxy.class);
        FilterRegistration.Dynamic securityFilter = servletContext.addFilter("springSecurityFilterChain", springSecurityFilterChain);
        securityFilter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), false, "/*");
    }
}
