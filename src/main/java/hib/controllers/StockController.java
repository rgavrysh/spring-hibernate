package hib.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class StockController {

    @RequestMapping(value = "/greeting", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<String> greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return ResponseEntity.status(HttpStatus.OK).body(String.format("Hello, %s!", name));
    }
}
