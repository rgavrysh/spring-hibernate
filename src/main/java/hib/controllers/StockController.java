package hib.controllers;

import hib.bo.StockBo;
import hib.logging.APILoggerImpl;
import hib.model.Response;
import hib.model.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;

@RestController
public class StockController {
    private final APILoggerImpl<StockController> logger = new APILoggerImpl<>(this);

    @Autowired
    StockBo stockBo;

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response mainPage() {
        logger.info("Request on root/default resource.");
        return new Response("OK", String.valueOf(HttpStatus.OK), "Hello World!");
    }

    @RequestMapping(value = "/greeting", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Response greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        logger.info("Request on /greeting resource.");
        return new Response("OK", String.valueOf(HttpStatus.OK), String.format("Hello, %s!", name));
    }

    @RequestMapping(value = "/stock", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Stock getStock(@RequestParam(name = "stockCode") String stockCode){
        logger.info("Request on /stock resource with stockCode parameter : "+ stockCode);
        Stock stock = stockBo.findByStockCode(stockCode);
        return stock;
    }
}
