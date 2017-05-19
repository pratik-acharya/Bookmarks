package tech.pratikacharya.apps.bookmarks;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import tech.pratikacharya.apps.bookmarks.domain.Account;
import tech.pratikacharya.apps.bookmarks.domain.Bookmark;
import tech.pratikacharya.apps.bookmarks.repository.AccountRepository;
import tech.pratikacharya.apps.bookmarks.repository.BookmarkRepository;

import java.util.Arrays;

@SpringBootApplication
public class BookmarksApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookmarksApplication.class, args);
	}


	@Bean
	CommandLineRunner init(AccountRepository accountRepository,
						   BookmarkRepository bookmarkRepository) {
		return (evt) -> Arrays.asList(
				"jhoeller,dsyer,pwebb,ogierke,rwinch,mfisher,mpollack,jlong".split(","))
				.forEach(
						a -> {
							Account account = accountRepository.save(new Account(a,
									"password"));
							bookmarkRepository.save(new Bookmark(account,
									"http://bookmark.com/1/" + a, "A description"));
							bookmarkRepository.save(new Bookmark(account,
									"http://bookmark.com/2/" + a, "A description"));
						});
	}


}
