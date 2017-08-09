package hib.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.sql.SQLException;

@Configuration
@EnableTransactionManagement
@Profile("prod")
//@PropertySource("classpath:application.properties")
public class DataSourceConfig {

    private String databaseUrl;

    @Value("${database.host}")
    String ipAddress;

    @Value("${database.port}")
    String port;

    @Value("${database.name}")
    String database;

    @Value("${database.user}")
    String databaseUser;

    @Value(value = "${database.password}")
    String databasePassword;

//    @Value("${jpa.showSql}")
//    Boolean showSql;

    @Bean
    public DataSource dataSource() throws PropertyVetoException, SQLException {
        databaseUrl = "jdbc:postgresql://" + ipAddress + ":" + port + "/" + database +
        "?sslmode=require&user="+databaseUser+"&password="+databasePassword;
//        databaseUrl = "jdbc:postgresql://ec2-54-195-240-107.eu-west-1.compute.amazonaws.com:5432/d5a14h34a2t2m9?sslmode=require&user=pcxfwcgwmgdlkr&password=gLqxKDcO8trJKWuFsk_2cLahGN";
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("org.postgresql.Driver");
        driverManagerDataSource.setUrl(databaseUrl);
        driverManagerDataSource.setUsername(databaseUser);
        driverManagerDataSource.setPassword(databasePassword);
        return driverManagerDataSource;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
//        hibernateJpaVendorAdapter.setShowSql(showSql);
        hibernateJpaVendorAdapter.setShowSql(true);
        hibernateJpaVendorAdapter.setGenerateDdl(false);
        hibernateJpaVendorAdapter.setDatabase(Database.POSTGRESQL);
        return hibernateJpaVendorAdapter;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer(){
        return new PropertySourcesPlaceholderConfigurer();
    }
}
