package com.belous.crmdemo.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;
import java.util.logging.Logger;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@EnableAspectJAutoProxy
@ComponentScan("com.belous.crmdemo")
@PropertySource({"classpath:persistence-mysql.properties", "classpath:security-persistence-mysql.properties"})
public class DemoAppConfig implements WebMvcConfigurer {

    // set up variable to hold the properties
    @Autowired
    private Environment env;

    // set up a logger for diagnostics
    private final Logger myLogger = Logger.getLogger(getClass().getName());

    // define a bean for ViewResolver
    @Bean
    public ViewResolver viewResolver() {

        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();

        viewResolver.setPrefix("/WEB-INF/view/");
        viewResolver.setSuffix(".jsp");

        return viewResolver;
    }

    // define a bean for our data source
    @Bean
    public DataSource myDataSource() {

        // create connection pool
        ComboPooledDataSource myDataSource = new ComboPooledDataSource();

        // set up the jdbc driver class
        try {
            myDataSource.setDriverClass(env.getProperty("jdbc.driver"));
        } catch (PropertyVetoException exc) {
            throw new RuntimeException(exc);
        }

        // log url and user ... just to make sure we are reading the data
        myLogger.info(">>> jdbc.url=" + env.getProperty("jdbc.url"));
        myLogger.info(">>> jdbc.user=" + env.getProperty("jdbc.user"));

        // set database connection props
        myDataSource.setJdbcUrl(env.getProperty("jdbc.url"));
        myDataSource.setUser(env.getProperty("jdbc.user"));
        myDataSource.setPassword(env.getProperty("jdbc.password"));

        // set connection pool props
        myDataSource.setInitialPoolSize(getIntProp("connection.pool.initialPoolSize"));
        myDataSource.setMinPoolSize(getIntProp("connection.pool.minPoolSize"));
        myDataSource.setMaxPoolSize(getIntProp("connection.pool.maxPoolSize"));
        myDataSource.setMaxIdleTime(getIntProp("connection.pool.maxIdleTime"));

        return myDataSource;
    }

    // define a bean for security data source
    @Bean
    public DataSource securityDataSource() {

        // create connection pool
        ComboPooledDataSource securityDataSource = new ComboPooledDataSource();

        // set up the jdbc driver class
        try {
            securityDataSource.setDriverClass(env.getProperty("security.jdbc.driver"));
        } catch (PropertyVetoException exc) {
            throw new RuntimeException(exc);
        }

        // log url and user ... just to make sure we are reading the data
        myLogger.info(">>> security.jdbc.url=" + env.getProperty("security.jdbc.url"));
        myLogger.info(">>> security.jdbc.user=" + env.getProperty("security.jdbc.user"));

        // set database connection props
        securityDataSource.setJdbcUrl(env.getProperty("security.jdbc.url"));
        securityDataSource.setUser(env.getProperty("security.jdbc.user"));
        securityDataSource.setPassword(env.getProperty("security.jdbc.password"));

        // set connection pool props
        securityDataSource.setInitialPoolSize(getIntProp("security.connection.pool.initialPoolSize"));
        securityDataSource.setMinPoolSize(getIntProp("security.connection.pool.minPoolSize"));
        securityDataSource.setMaxPoolSize(getIntProp("security.connection.pool.maxPoolSize"));
        securityDataSource.setMaxIdleTime(getIntProp("security.connection.pool.maxIdleTime"));

        return securityDataSource;
    }

    // need a helper method
    // read environment property and convert to int

    private int getIntProp(String propName) {

        String propVal = env.getProperty(propName);
        return Integer.parseInt(propVal);
    }

    private Properties getHibernateProperties() {

        // set hibernate properties
        Properties props = new Properties();

        props.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
        props.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));

        return props;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {

        // create session factory
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();

        // set the properties
        sessionFactory.setDataSource(myDataSource());
        sessionFactory.setPackagesToScan(env.getProperty("hibernate.packagesToScan"));
        sessionFactory.setHibernateProperties(getHibernateProperties());

        return sessionFactory;
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {

        // setup transaction manager based on session factory
        HibernateTransactionManager tsManager = new HibernateTransactionManager();

        tsManager.setSessionFactory(sessionFactory);

        return tsManager;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        // add support for reading web resources: css, js, images, etc
        registry
                .addResourceHandler("/resources/**")
                .addResourceLocations("/resources/");
    }
}
