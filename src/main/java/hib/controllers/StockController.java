package hib.controllers;

import hib.bo.StockBo;
import hib.model.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class StockController {

    @Autowired
    StockBo stockBo;

    @RequestMapping(value = "/greeting", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<String> greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return ResponseEntity.status(HttpStatus.OK).body(String.format("Hello, %s!", name));
    }

    @RequestMapping(value = "/stock", method = RequestMethod.GET)
    public @ResponseBody Stock getStock(@RequestParam(name = "stockCode") String stockCode){
        Stock stock = stockBo.findByStockCode(stockCode);
        return stock;
    }
}
