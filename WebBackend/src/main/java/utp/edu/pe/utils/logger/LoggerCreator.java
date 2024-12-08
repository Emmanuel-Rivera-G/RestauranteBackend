package utp.edu.pe.utils.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerCreator {
    public static Logger getLogger(Class<?> clase) {
        return LoggerFactory.getLogger(clase);
    }
}
