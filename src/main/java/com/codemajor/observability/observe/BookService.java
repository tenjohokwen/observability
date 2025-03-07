package com.codemajor.observability.observe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.micrometer.observation.annotation.Observed;

@Component
public class BookService {
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    private final BookRepo bookRepo;

    public BookService(BookRepo bookRepo) {this.bookRepo = bookRepo;}


    /**
     * This method and bookRepo.getReservedCount() will not appear in traces since they are not observed.
     */
    public int getBorrowedBooksCount() {
        logger.info("In getBorrowedBooksCount method");
        return bookRepo.getReservedCount();
    }

    /**
     * The @Observe annotation at this method makes it appear as a span in traces.
     * It invokes method that is not going to appear in the trace because it is not observed
     */
    @Observed
    public int borrowedBooksCountObservedOnlyAtService() {
        logger.info("In borrowedBooksCountObservedOnlyAtService method");
        return bookRepo.getReservedCount();
    }

    /**
     * The @Observe annotation at this method makes it appear as a span in traces.
     * It also invokes an observed method which will also appear in the trace
     */
    @Observed
    public int booksCountObservedAtServiceAndRepo() {
        logger.info("In booksCountObservedAtServiceAndRepo method");
        return bookRepo.getReservedCountObserved();
    }

    /**
     * This method will not appear in traces since is not observed.
     * However, it also invokes an observed method which will appear in traces
     */
    public int booksCountObservedAtRepo() {
        logger.info("In booksCountObservedAtRepo method");
        return bookRepo.getReservedCountObserved();
    }

    public String getBorrowedBooksCountWithError() {
        if(true) {
            throw new RuntimeException("an exception occurred");
        }
        return "SUCCESS";
    }
}
