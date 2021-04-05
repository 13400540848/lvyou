package com.bluesimon.wbf.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.ejb.HibernatePersistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.bluesimon.wbf.authentication.AuthenticationHandlerInterceptor;
import com.bluesimon.wbf.resolver.AdminLoginedArgumentResolver;
import com.bluesimon.wbf.resolver.LoginedArgumentResolver;
import com.bluesimon.wbf.resolver.RemoteArgumentResolver;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

/**
 * 是默认自动启动web配置
 * Web Basic Framework
 * Created by Django on 2017/12/5.
 */
public class WbfWebConfig extends WebMvcConfigurerAdapter {

    protected DataSource dataSource() {
        //TODO 具体业务需要实现
        return null;
    }

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/pages/");
        resolver.setSuffix(".jsp");
        resolver.setExposeContextBeansAsAttributes(true);
        return resolver;
    }

    /**
     * 视图配置
     *
     * @param registry
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
//        registry.viewResolver(viewResolver());
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    /**
     * 静态资源配置
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("/");
        super.addResourceHandlers(registry);
    }

    /**
     * 消息格式配置
     *
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        /**MappingJackson2HttpMessageConverter*/
        MappingJackson2HttpMessageConverter mMappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper mObjectMapper = new ObjectMapper();
        mObjectMapper.setPropertyNamingStrategy(new PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy());
        mMappingJackson2HttpMessageConverter.setObjectMapper(mObjectMapper);
        //support media types
        final Map<String, String> parameterMap = new HashMap<String, String>(4);
        parameterMap.put("charset", "utf-8");
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(new MediaType("text", "html", parameterMap));
        mediaTypes.add(new MediaType("application", "json", parameterMap));
        mediaTypes.add(new MediaType("application", "x-www-form-urlencoded"));
        mMappingJackson2HttpMessageConverter.setSupportedMediaTypes(mediaTypes);
//        converters.add(mMappingJackson2HttpMessageConverter);

        /**FastJsonHttpMessageConverter*/
//        FastJsonHttpMessageConverter mFastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
//        mFastJsonHttpMessageConverter.setSupportedMediaTypes(mediaTypes);
//        converters.add(mFastJsonHttpMessageConverter);
    }

    /**
     * 参数解析配置
     *
     * @param argumentResolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new LoginedArgumentResolver());
        argumentResolvers.add(new AdminLoginedArgumentResolver());
        argumentResolvers.add(new RemoteArgumentResolver());
    }

    /**
     * 拦截器配置
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationHandlerInterceptor()).addPathPatterns("/**");
    }

    @Bean
    public AuthenticationHandlerInterceptor authenticationHandlerInterceptor() {
        AuthenticationHandlerInterceptor mAuthenticationHandlerInterceptor = new AuthenticationHandlerInterceptor();
        return mAuthenticationHandlerInterceptor;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer mPreferencesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        return mPreferencesPlaceholderConfigurer;
    }

    //数据库相关

    @Bean
    public JdbcTemplate sqlSessionTemplate() throws Exception {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistence.class);
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setShowSql(true);
        hibernateJpaVendorAdapter.setGenerateDdl(true);
        hibernateJpaVendorAdapter.setDatabase(Database.MYSQL);
        entityManagerFactoryBean.setJpaVendorAdapter(hibernateJpaVendorAdapter);
        entityManagerFactoryBean.setJpaProperties(hibProperties());
        return entityManagerFactoryBean;
    }

    private Properties hibProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.ejb.naming_strategy", "org.hibernate.cfg.ImprovedNamingStrategy");
        properties.put("hibernate.jdbc.batch_size", 50);
        properties.put("hibernate.order_inserts", true);
        properties.put("hibernate.order_updates", true);
        properties.put("hibernate.jdbc.batch_versioned_data", true);
        return properties;
    }

    @Bean
    NamedParameterJdbcTemplate jdbcTemplate() {
        return new NamedParameterJdbcTemplate(dataSource());
    }


    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    @Bean
    public PersistenceAnnotationBeanPostProcessor persistenceAnnotationBeanPostProcessor() {
        return new PersistenceAnnotationBeanPostProcessor();
    }
}
