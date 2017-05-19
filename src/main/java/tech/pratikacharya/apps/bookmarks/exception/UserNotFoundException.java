package tech.pratikacharya.apps.bookmarks.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Pratik Acharya on 5/14/2017.
 */
//@ResponseStatus(HttpStatus.NOT_FOUND) moved to controller advice after HATEOAS integration
public class UserNotFoundException extends  RuntimeException {

    public UserNotFoundException(String username){

        super("User does not exist"+ username);

    }
}
