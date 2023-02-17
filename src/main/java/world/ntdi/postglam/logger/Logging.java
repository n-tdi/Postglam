package world.ntdi.postglam.logger;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Logging {
    @Getter
    private final Logger logger;

    public Logging(Class clazz) {
        this.logger = LoggerFactory.getLogger(clazz);
    }
}
