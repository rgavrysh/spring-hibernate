package hib.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@Profile("prod")
public class DataSourceConfig {

    @Value("${database.host}")
    private String ipAddress;

    @Value("${database.port}")
    private String port;

    @Value("${database.name}")
    private String database;

    @Value("${database.user}")
    private String databaseUser;

    @Value(value = "${database.password}")
    private String databasePassword;

    @Value("${driver.classname}")
    private String driverClassName;

    @Value("${jpa.dialect}")
    private String hibernateDialect;

    @Value("${hibernate.hbm2ddl.auto}")
    private String hbm2ddl;

    private final String persistenceUnitName = "backend-api";

    //    databaseUrl = "jdbc:postgresql://ec2-54-195-240-107.eu-west-1.compute.amazonaws.com:5432/d5a14h34a2t2m9?sslmode=require&user=pcxfwcgwmgdlkr&password=gLqxKDcO8trJKWuFsk_2cLahGN";
    @Bean
    public DataSource dataSource() {
        final String databaseUrl = "jdbc:postgresql://" + ipAddress + ":" + port + "/" + database +
                "?sslmode=require&user=" + databaseUser + "&password=" + databasePassword;
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName(driverClassName);
        driverManagerDataSource.setUrl(databaseUrl);
        driverManagerDataSource.setUsername(databaseUser);
        driverManagerDataSource.setPassword(databasePassword);
        return driverManagerDataSource;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setShowSql(true);
        hibernateJpaVendorAdapter.setGenerateDdl(false);
        hibernateJpaVendorAdapter.setDatabase(Database.POSTGRESQL);
        return hibernateJpaVendorAdapter;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setJpaProperties(jpaProperties());
        em.setJpaVendorAdapter(jpaVendorAdapter());
        em.setPersistenceUnitName(persistenceUnitName);
        em.setPackagesToScan("hib.model");
        return em;
    }

    @Bean
    public Properties jpaProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", hibernateDialect);
        properties.put("hibernate.hbm2ddl.auto", hbm2ddl);
        return properties;
    }

    @Bean
    public JpaTransactionManager transactionManager(final EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        JpaDialect jpaDialect = new HibernateJpaDialect();
        txManager.setEntityManagerFactory(entityManagerFactory);
        txManager.setJpaDialect(jpaDialect);
        return txManager;
    }
}
