package hib;

import hib.bo.StockBo;
import hib.config.Config;
import hib.config.DataSourceConfig;
import hib.model.Stock;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

//@SpringBootApplication
@Configuration
//@EnableWebMvc
//@ComponentScan(basePackages = {
//        "hib.dao",
//        "hib.bo",
//        "hib.model",
//        "hib.controllers",
//        "hib.config",
//})
//@EnableAutoConfiguration(exclude = {HibernateJpaAutoConfiguration.class})
@Import({
        Config.class,
        DataSourceConfig.class
})
public class Starter {

    public static void main(String[] args) {
//        SpringApplication.run(Starter.class, args);
        ApplicationContext applicationContext =
//                new ClassPathXmlApplicationContext("config/BeanLocations.xml");
                new AnnotationConfigApplicationContext(Starter.class);

        StockBo stockBo = (StockBo) applicationContext.getBean("stockBo");

        Stock stock = new Stock("7668", "APPL");
//        stockBo.save(stock);
        Stock stock1 = stockBo.findByStockCode("7668");
        System.out.println(stock1);
        System.out.println("Done...");
    }
}
