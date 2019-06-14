package com.github.adminfaces.showcase;

import net.bull.javamelody.SessionListener;

import javax.servlet.*;
import javax.servlet.annotation.WebListener;
import java.util.EnumSet;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebListener
public class StartupListener implements ServletContextListener {


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setInitParameter("primefaces.THEME", "admin-1.0.2");
        sce.getServletContext().setInitParameter("primefaces.FONT_AWESOME", "true");
        sce.getServletContext().setInitParameter("com.sun.faces.numberOfLogicalViews", "6");
        sce.getServletContext().setInitParameter("com.sun.faces.numberOfViewsInSession", "6");
        sce.getServletContext().setInitParameter("org.omnifaces.VIEW_SCOPE_MANAGER_MAX_ACTIVE_VIEW_SCOPES", "6");
        try {
            sce.getServletContext().createListener(SessionListener.class);
        } catch (ServletException e) {
            Logger.getLogger(StartupListener.class.getName()).log(Level.SEVERE, "Could not create melody listener", e);
        }
        FilterRegistration.Dynamic gzipResponseFilter = sce.getServletContext().addFilter("gzipResponseFilter", "org.omnifaces.filter.GzipResponseFilter");
        gzipResponseFilter.setInitParameter("threshold", "200");
        gzipResponseFilter.addMappingForServletNames(EnumSet.of(DispatcherType.ERROR), true, "Faces Servlet");
        FilterRegistration.Dynamic javaMelodyFilter = sce.getServletContext().addFilter("javamelody", "net.bull.javamelody.MonitoringFilter");
        javaMelodyFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");
        javaMelodyFilter.setAsyncSupported(true);
    }


}