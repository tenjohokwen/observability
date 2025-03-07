package com.codemajor.observability.observe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Random;

import io.micrometer.observation.annotation.Observed;

@Component
public class BookRepo {
    private static final Logger logger = LoggerFactory.getLogger(BookRepo.class);

    private final        Random random = new Random();

    /**
     * This method is not observed and consequently will not be observed. It will not appear in any trace
     */
    public int getReservedCount() {
        logger.info("In the getMarkedAsAway method");
        return random.nextInt(20);
    }


    /**
     * The @Observe annotation at this method makes it appear as a span in traces
     */
    @Observed
    public int getReservedCountObserved() {
        logger.info("In the getMarkedAsAwayObserved method");
        return random.nextInt(20);
    }

}
