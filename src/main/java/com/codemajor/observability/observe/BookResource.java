package com.codemajor.observability.observe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/book")
public class BookResource {
    private Logger logger = LoggerFactory.getLogger(BookResource.class);
    private final MyCustomObservation myCustomObservation;
    private final BookService bookService;

    public BookResource(MyCustomObservation myCustomObservation, BookService bookService) {
        this.myCustomObservation = myCustomObservation;
        this.bookService = bookService;
    }

    @GetMapping("/borrowed0")
    public int getBorrowed0() {
       return bookService.getBorrowedBooksCount();
    }

    @GetMapping("/borrowed1")
    public int getBorrowed() {
       return bookService.borrowedBooksCountObservedOnlyAtService();
    }

    /**
     * Invoke this method in order to trace all calls using annotations
     */
    @GetMapping("/borrowed2")
    public int getBorrowed2() {
       return bookService.booksCountObservedAtServiceAndRepo();
    }

    /**
     *  Invoke this to see error traces linked to the error logs
     */
    @GetMapping("/borrowed/exception")
    public String getBorrowedWithException() {
        try {
            return bookService.getBorrowedBooksCountWithError();
        } catch (Exception exception) {
            logger.error("Error occurred...", exception);
        }
        return "Error occurred";
    }

    /**
     * The http call and myCustomObservation.countBorrowedBooksProgrammaticObservation() are traced but the method called by
     * countBorrowedBooksProgrammaticObservation() is not traced since it neither has an annotated observation nor a programmatic one
     */
    @GetMapping("/borrowed3")
    public int getBorrowedProgrammaticObservation() {
       return myCustomObservation.countBorrowedBooksProgrammaticObservation();
    }

    /**
     * The http call, the programmatic and then the annotated are traced
     */
    @GetMapping("/borrowed4")
    public int getBorrowedMixedObservation() {
       return myCustomObservation.countBorrowedBooksProgrammaticAndAnnotatedObservation();
    }

    @GetMapping("/borrowed/exception2")
    public int getBorrowedProgException() {
        try {
            return myCustomObservation.countBorrowedBooksProgrammaticException();
        } catch (Exception exception) {
            logger.error("Error occurred...", exception);
        }
        return 0;
    }
}
