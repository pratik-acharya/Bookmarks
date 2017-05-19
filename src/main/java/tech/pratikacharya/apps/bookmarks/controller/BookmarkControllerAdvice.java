package tech.pratikacharya.apps.bookmarks.controller;

import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import tech.pratikacharya.apps.bookmarks.exception.UserNotFoundException;

/**
 * Created by Pratik Acharya on 5/15/2017.
 */

@ControllerAdvice
public class BookmarkControllerAdvice {
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    VndErrors userNotFoundExceptionHandler(UserNotFoundException ex){
        return new VndErrors("error", ex.getMessage());
    }
}
