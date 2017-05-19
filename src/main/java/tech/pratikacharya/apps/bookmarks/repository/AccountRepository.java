package tech.pratikacharya.apps.bookmarks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.pratikacharya.apps.bookmarks.domain.Account;

import java.util.Optional;

/**
 * Created by Pratik Acharya on 5/14/2017.
 */
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUsername(String username);

}
