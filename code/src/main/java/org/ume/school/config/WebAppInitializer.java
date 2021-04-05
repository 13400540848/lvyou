package org.ume.school.config;

import java.nio.charset.StandardCharsets;
import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import org.ume.school.filters.CustomeFileFilter;

/**
 * Created by Django on 2017/12/5.
 */
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{RootConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebConfig.class};
    }

    /**
     * identifies one or more paths that DispatcherServlet will be mapped to.<br>
     * In this case, it’s mapped to /, indicating that it will be the application’s default servlet.<br>
     * It will handle all requests coming into the application.
     */
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        FilterRegistration.Dynamic encodingFilter = servletContext.addFilter("encodingFilter", CharacterEncodingFilter.class);
        encodingFilter.setInitParameter("encoding", String.valueOf(StandardCharsets.UTF_8));
        encodingFilter.setInitParameter("forceEncoding", "true");
        encodingFilter.addMappingForUrlPatterns(null, false, "/*");
        //拦截静态文件
        FilterRegistration.Dynamic fileFilter = servletContext.addFilter("fileFilter", CustomeFileFilter.class);
        encodingFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/statics");
                
        super.onStartup(servletContext);
    }

}
