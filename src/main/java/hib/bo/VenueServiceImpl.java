package hib.bo;

import hib.dao.VenueDao;
import hib.logging.APILogger;
import hib.logging.APILoggerImpl;
import hib.model.Venue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("venueService")
public class VenueServiceImpl implements VenueService {

    private final APILogger<VenueServiceImpl> logger = new APILoggerImpl<>(this);

    @Autowired
    private VenueDao venueDao;

    @Override
    public Venue findOneByName(String name) {
        logger.info("Find venue by name: " + name);
        return venueDao.findOneByName(name);
    }

    @Override
    public Venue findOneById(int id) {
        logger.info("Find venue by id: " + id);
        return venueDao.findOneById(id);
    }
}
