package world.ntdi.postglam;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Postglam {

    /**
     * Makes use of SLF4J to create a logger based on the class you're in
     * @param clazz Class you're logging from
     * @return Returns the Logger from LoggerFactory
     * @param <T> Type Parameter
     */
    public static <T> Logger getLogger(Class<T> clazz) {
        return LoggerFactory.getLogger(clazz);
    }
}
