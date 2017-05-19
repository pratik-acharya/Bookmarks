package tech.pratikacharya.apps.bookmarks.api;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import tech.pratikacharya.apps.bookmarks.controller.BookmarkRestController;
import tech.pratikacharya.apps.bookmarks.domain.Bookmark;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

/**
 * Created by Pratik Acharya on 5/15/2017.
 */
public class BookmarkResource extends ResourceSupport {

    private final  Bookmark bookmark;

    public BookmarkResource(Bookmark bookmark){
        String username = bookmark.getAccount().getUsername();
        this.bookmark = bookmark;
        this.add(new Link(bookmark.getUri(),"bookmark-uri"));
        this.add(linkTo(BookmarkRestController.class,username).withRel("bookmarks"));
        this.add(linkTo(methodOn(BookmarkRestController.class, username).readBookmark(username,bookmark.getId())).withSelfRel());

    }

    public Bookmark getBookmark(){
        return  bookmark;
    }
}
