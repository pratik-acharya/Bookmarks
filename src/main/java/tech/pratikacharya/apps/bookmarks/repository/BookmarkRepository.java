package tech.pratikacharya.apps.bookmarks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.pratikacharya.apps.bookmarks.domain.Bookmark;

import java.util.Collection;

/**
 * Created by Pratik Acharya on 5/14/2017.
 */
public interface BookmarkRepository extends JpaRepository<Bookmark, Long>{
    Collection<Bookmark> findByAccountUsername(String username);

}
