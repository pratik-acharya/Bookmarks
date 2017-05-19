package tech.pratikacharya.apps.bookmarks;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import tech.pratikacharya.apps.bookmarks.domain.Account;
import tech.pratikacharya.apps.bookmarks.domain.Bookmark;
import tech.pratikacharya.apps.bookmarks.repository.AccountRepository;
import tech.pratikacharya.apps.bookmarks.repository.BookmarkRepository;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import  static  org.junit.Assert.*;
import static  org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

/**
 * Created by Pratik Acharya on 5/15/2017.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookmarksApplication.class)
@WebAppConfiguration
public class BookmarkRestControllerTest {
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private MockMvc mockMvc;

    private String username = "bdussault";

    private HttpMessageConverter httpMessageConverter;

    private Account account;

    private List<Bookmark> bookmarkList = new ArrayList<>();

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters){
        this.httpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc-> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);
        assertNotNull("the message converter must not be null",this.httpMessageConverter);
    }

    @Before
    public void setUp() throws  Exception{
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        this.bookmarkRepository.deleteAllInBatch();
        this.accountRepository.deleteAllInBatch();
        this.account = this.accountRepository.save(new Account(username, "password"));
        this.bookmarkList.add(this.bookmarkRepository.save(new Bookmark(account, "http://bookmark.com/1/"+username, "A description")));
        this.bookmarkList.add(this.bookmarkRepository.save(new Bookmark(account, "http://bookmark.com/2/"+username,"A description")));
    }


    @Test
    public void userNotFound() throws Exception{
        this.mockMvc.perform(post("/george/bookmarks")
                            .content(this.json(new Bookmark()))
                            .contentType(contentType))
                     .andExpect(status().isNotFound());
    }

    @Test
    public void readSingleBookmark() throws Exception{
        this.mockMvc.perform(get("/"+username+"/bookmarks/"+this.bookmarkList.get(0).getId()))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(contentType))
                    .andExpect(jsonPath("$.id", is(this.bookmarkList.get(0).getId().intValue())))
                    .andExpect(jsonPath("$.uri", is("http://bookmark.com/1/"+username)))
                    .andExpect(jsonPath("$.description", is("A description")));


    }


    @Test
    public void readBookmarks() throws Exception{
        this.mockMvc.perform(get("/"+username+"/bookmarks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(this.bookmarkList.get(0).getId().intValue())))
                .andExpect(jsonPath("$[0].uri", is("http://bookmark.com/1/"+username)))
                .andExpect(jsonPath("$[0].description",is("A description")))
                .andExpect(jsonPath("$[1].id", is(this.bookmarkList.get(1).getId().intValue())))
                .andExpect(jsonPath("$[1].uri", is("http://bookmark.com/2/"+username)))
                .andExpect(jsonPath("$[1].description",is("A description")));

    }

    @Test
    public void createBookmark() throws Exception{
        String bookmarkJson = json(new Bookmark(this.account, "http://pratikacharya.tech","A bookmark"));
        this.mockMvc.perform(post("/"+username+"/bookmarks")
                .contentType(contentType)
                .content(bookmarkJson))
                .andExpect(status().isCreated());

    }

    protected  String json(Object o) throws  Exception{
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.httpMessageConverter.write(o, MediaType.APPLICATION_JSON,mockHttpOutputMessage);
        return  mockHttpOutputMessage.getBodyAsString();
    }
}
