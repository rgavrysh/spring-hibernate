package hib;

import hib.config.Config;
import hib.config.DataSourceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

//@SpringBootApplication
@Configuration
@EnableWebMvc
//@EnableAutoConfiguration(exclude = {HibernateJpaAutoConfiguration.class})
@Import({
        Config.class,
        DataSourceConfig.class
})
public class Starter {

    public static void main(String[] args) {
        SpringApplication.run(Starter.class, args);
//        ApplicationContext applicationContext =
////                new ClassPathXmlApplicationContext("config/BeanLocations.xml");
//                new AnnotationConfigApplicationContext(Starter.class);
//
//        StockBo stockBo = (StockBo) applicationContext.getBean("stockBo");
//
//        Stock stock = new Stock("7668", "APPL");
////        stockBo.save(stock);
//        Stock stock1 = stockBo.findByStockCode("7668");
//        System.out.println(stock1);
//        System.out.println("Done...");
    }
}
