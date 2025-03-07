package com.codemajor.observability.observe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;

@Component
public class MyCustomObservation {
    private static final Logger logger = LoggerFactory.getLogger(MyCustomObservation.class);

    private final ObservationRegistry observationRegistry;
    private final BookService bookService;

    public MyCustomObservation(ObservationRegistry observationRegistry, BookService bookService) {
        this.observationRegistry = observationRegistry;
        this.bookService = bookService;
    }

    public int countBorrowedBooksProgrammaticObservation() {
        final AtomicInteger atomicInteger = new AtomicInteger() ;
        Observation.createNotStarted("programmatic-observation", this.observationRegistry)
                   .lowCardinalityKeyValue("observe", "prog")
                   .observe(() -> {
                       atomicInteger.getAndSet(bookService.getBorrowedBooksCount());
                       logger.info("custom observation done");
                   });
        return atomicInteger.get();
    }

    public int countBorrowedBooksProgrammaticAndAnnotatedObservation() {
        final AtomicInteger atomicInteger = new AtomicInteger() ;
        Observation.createNotStarted("prog-none-anno", this.observationRegistry)
                   .lowCardinalityKeyValue("observe", "mixed")
                   .observe(() -> {
                       atomicInteger.getAndSet(bookService.booksCountObservedAtRepo());
                       logger.info("prog and annotated done");
                   });
        return atomicInteger.get();
    }

    public int countBorrowedBooksProgrammaticException() {
        final AtomicInteger atomicInteger = new AtomicInteger() ;
        Observation.createNotStarted("prog-exception", this.observationRegistry)
                   .lowCardinalityKeyValue("observe", "exception")
                   .observe(() -> {
                       bookService.getBorrowedBooksCountWithError();
                       atomicInteger.getAndSet(1);
                   });
        return atomicInteger.get();
    }
}
