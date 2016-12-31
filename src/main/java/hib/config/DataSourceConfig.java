package hib.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//import javax.activation.DataSource;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.sql.SQLException;

@Configuration
@EnableTransactionManagement
@PropertySource("application.properties")
public class DataSourceConfig {

    private String databaseUrl;

    @Value("#{database.host}")
    private String ipAddress;
    @Value("#{database.port}")
    private String port;

    @Value("#{database.name}")
    private String database;

    @Value("${database.user}")
    private String databaseUser;

    @Value("${database.password}")
    private String databasePassword;

//    @Value("${jpa.showSql}")
//    private boolean showSql;

    @Bean
    public DataSource dataSource() throws PropertyVetoException, SQLException {
//        databaseUrl = "postgres://" + ipAddress + ":" + port + "/" + database;
        databaseUrl = "jdbc:postgresql://ec2-54-195-240-107.eu-west-1.compute.amazonaws.com:5432/d5a14h34a2t2m9?sslmode=require&user=pcxfwcgwmgdlkr&password=gLqxKDcO8trJKWuFsk_2cLahGN";
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("org.postgresql.Driver");
        driverManagerDataSource.setUrl(databaseUrl);
        driverManagerDataSource.setUsername("pcxfwcgwmgdlkr");
        driverManagerDataSource.setPassword("gLqxKDcO8trJKWuFsk_2cLahGN");
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


}
