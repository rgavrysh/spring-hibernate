package hib.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = {
        "hib.dao",
        "hib.bo",
        "hib.model"
})
@Import({ DataSourceConfig.class})
public class Config {

    @Value("${jpa.dialect}")
    private String dialect;

    @Value("${jpa.showsql}")
    private String showSql;

    @Autowired
    DataSource datasource;

    @Autowired
    JpaVendorAdapter jpaVendorAdapter;

    @Value("${hibernate.hbm2ddl.auto}")
    String hbm2ddlAuto;


    @Bean
    LocalContainerEntityManagerFactoryBean entityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(datasource);
        em.setJpaProperties(jpaProperties());
        em.setJpaVendorAdapter(jpaVendorAdapter);
        em.setPersistenceUnitName("api");
        em.setPackagesToScan("hib.model");
        return em;
    }

    @Bean
    Properties jpaProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.put("hibernate.show_sql", true);
        properties.put("hibernate.hbm2ddl.auto", "validate");
        return properties;
    }

//    @Bean
//    public ErrorAttributes errorAttributes() {
//        return new DefaultErrorAttributes() {
//
//            @Override
//            public Map<String, Object> getErrorAttributes(RequestAttributes requestAttributes,
//                                                          boolean includeStackTrace) {
//                Map<String, Object> errorAttributes = super.getErrorAttributes(requestAttributes, includeStackTrace);
//                return errorAttributes;
//            }
//        };
//    }

//    @Bean
//    public FilterRegistrationBean correlationHeaderFilter() {
//        FilterRegistrationBean filterRegBean = new FilterRegistrationBean();
//        filterRegBean.setFilter(new CorrelationHeaderFilter());
//        filterRegBean.setUrlPatterns(Arrays.asList("/*"));
//
//        return filterRegBean;
//    }

    @Bean
    JpaTransactionManager transactionManager(final EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        JpaDialect jpaDialect = new HibernateJpaDialect();
        txManager.setEntityManagerFactory(entityManagerFactory);
        txManager.setJpaDialect(jpaDialect);
        return txManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
        return new PersistenceExceptionTranslationPostProcessor();
    }

//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurerAdapter() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**").allowedOrigins("*");
//            }
//        };
//    }
}
