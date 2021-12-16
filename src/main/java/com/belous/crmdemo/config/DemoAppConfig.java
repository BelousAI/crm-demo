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
@PropertySource({"classpath:persistence-mysql.properties"})
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

    // define a bean for our datasource
    public DataSource myDataSource() {

        // create connection pool
        ComboPooledDataSource dataSource = new ComboPooledDataSource();

        // set up the jdbc driver class
        try {
            dataSource.setDriverClass(env.getProperty("jdbc.driver"));
        } catch (PropertyVetoException exc) {
            throw new RuntimeException(exc);
        }

        // for sanity's sake, let's log url and user ... just to make sure we are reading the data
        myLogger.info(">>> jdbc.url=" + env.getProperty("jdbc.url"));
        myLogger.info(">>> jdbc.user=" + env.getProperty("jdbc.user"));

        // set database connection props
        dataSource.setJdbcUrl(env.getProperty("jdbc.url"));
        dataSource.setUser(env.getProperty("jdbc.user"));
        dataSource.setPassword(env.getProperty("jdbc.password"));

        // set connection pool props
        dataSource.setInitialPoolSize(getIntProp("connection.pool.initialPoolSize"));
        dataSource.setMinPoolSize(getIntProp("connection.pool.minPoolSize"));
        dataSource.setMaxPoolSize(getIntProp("connection.pool.maxPoolSize"));
        dataSource.setMaxIdleTime(getIntProp("connection.pool.maxIdleTime"));

        return dataSource;
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
