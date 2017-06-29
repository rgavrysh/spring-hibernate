package hib.logging;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class APILoggerImpl<T> implements APILogger<T> {

    private final Logger logger;

    public APILoggerImpl (T t) {
        this.logger = Logger.getLogger(t.getClass());
    }

    @Override
    public void info(String info) {
        this.logger.info(info);
    }

    @Override
    public void info(String info, Throwable t) {
        this.logger.info(info, t);
    }

    @Override
    public void debug(String debug) {
        this.logger.debug(debug);
    }

    @Override
    public void error(String error) {
        this.logger.error(error);
    }

    @Override
    public void error(String error, Exception e) {
        this.logger.error(error, e);
    }

    @Override
    public Logger getLogger() {
        return this.logger;
    }
}
