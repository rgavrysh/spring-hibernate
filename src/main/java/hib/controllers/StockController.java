package hib.controllers;

import hib.bo.StockBo;
import hib.model.Response;
import hib.model.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class StockController {

    @Autowired
    StockBo stockBo;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Response mainPage() {
        return new Response("OK", String.valueOf(HttpStatus.OK), "Hello World!");
    }

    @RequestMapping(value = "/greeting", method = RequestMethod.GET)
    public @ResponseBody Response greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Response("OK", String.valueOf(HttpStatus.OK), String.format("Hello, %s!", name));
    }

    @RequestMapping(value = "/stock", method = RequestMethod.GET)
    public @ResponseBody Stock getStock(@RequestParam(name = "stockCode") String stockCode){
        Stock stock = stockBo.findByStockCode(stockCode);
        return stock;
    }
}
