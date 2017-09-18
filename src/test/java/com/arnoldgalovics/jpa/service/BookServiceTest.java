package com.arnoldgalovics.jpa.service;

import com.arnoldgalovics.jpa.internal.repository.domain.Book;
import com.arnoldgalovics.jpa.internal.repository.domain.Genre;
import com.arnoldgalovics.jpa.util.CleanUpTestExecutionListener;
import com.arnoldgalovics.jpa.util.ShowTableTestExecutionListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.test.context.TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestExecutionListeners(value = {ShowTableTestExecutionListener.class, CleanUpTestExecutionListener.class}, mergeMode = MERGE_WITH_DEFAULTS)
public class BookServiceTest {

    @Autowired
    BookService bookService;

    @Test
    public void test() {

        Book book1 = new Book("Veritas", 100, 321, 123456789L, Genre.FANTASY);
        Book book2 = new Book("Mysterium", 110, 432, 987654321L, Genre.FANTASY);

        bookService.save(book1);
        bookService.save(book2);
    }

}