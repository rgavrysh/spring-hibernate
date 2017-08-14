package hib.controllers;

import hib.services.VenueService;
import hib.logging.APILogger;
import hib.logging.APILoggerImpl;
import hib.util.Response;
import hib.model.Venue;
import hib.restEntity.CreateVenue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VenueController {
    private final APILogger<VenueController> logger = new APILoggerImpl<>(this);

    @Autowired
    private VenueService venueService;

    @RequestMapping(value = "/venue", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    Venue addNewVenue(@RequestBody CreateVenue createVenue) {
        logger.info("Adding new venue.");
        return venueService.create(createVenue);
    }

    @RequestMapping(value = "/venue/{id}", method = RequestMethod.DELETE)
    public
    @ResponseBody
    Response deleteVenue(@PathVariable final int id) {
        logger.info("Delete venue with id: " + id);
        venueService.delete(id);
        return new Response("Entity deleted", String.valueOf(HttpStatus.OK),
                "Venue id: " + id + " has been deleted");
    }

    @RequestMapping(value = "/venues", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Venue> getAllVenue(){
        logger.info("Get all available venues.");
        return venueService.listAllVenues();
    }
}
