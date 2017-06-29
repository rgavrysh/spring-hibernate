package hib.logging;

import org.apache.log4j.Logger;

public interface APILogger<T> {

    void info (String var1);
    void info (String msg, Throwable var2);
    void debug(String var1);
    void error(String var1);
    void error(String var1, Exception var2);

    Logger getLogger();
}
