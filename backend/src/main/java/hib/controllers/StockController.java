package hib.controllers;

import hib.bo.StockBo;
import hib.logging.APILoggerImpl;
import hib.model.Response;
import hib.model.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class StockController {
    private final APILoggerImpl<StockController> logger = new APILoggerImpl<>(this);

    @Autowired
    StockBo stockBo;

    @RequestMapping(value = "/greeting", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> mainPage() {
        logger.info("Request on root/default resource.");
        Map<String, Object> model = new HashMap<>();
        model.put("id", UUID.randomUUID().toString());
        model.put("content", "Hello User!");
        return model;
//        return new Response("OK", String.valueOf(HttpStatus.OK), "Hello World!");
    }

    @RequestMapping(value = "/user")
    public Principal user(Principal user){
        return user;
    }

//    @RequestMapping(value = "/greeting", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//    public @ResponseBody Response greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
//        logger.info("Request on /greeting resource.");
//        return new Response("OK", String.valueOf(HttpStatus.OK), String.format("Hello, %s!", name));
//    }

    @RequestMapping(value = "/stock", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Stock getStock(@RequestParam(name = "stockCode") String stockCode){
        logger.info("Request on /stock resource with stockCode parameter : "+ stockCode);
        Stock stock = stockBo.findByStockCode(stockCode);
        return stock;
    }
}
