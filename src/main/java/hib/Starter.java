package hib;

import hib.config.Config;
import hib.config.DataSourceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
//@Configuration
//@EnableWebMvc
//@EnableAutoConfiguration(exclude = {HibernateJpaAutoConfiguration.class})
@Import({
        Config.class,
        DataSourceConfig.class
})
public class Starter {

    public static void main(String[] args) {
        SpringApplication.run(Starter.class, args);
    }
}
