package com.arnoldgalovics.jpa.service;

import com.arnoldgalovics.jpa.internal.repository.domain.Book;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static java.lang.System.out;

@Service
public class BookService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void save(Book book) {
        entityManager.persist(book);
        out.println("after persist");
    }

    @Transactional
    public void selctWithJPQL() {
        List<Book> books = entityManager.createQuery("SELECT b FROM Book b", Book.class).getResultList();
        books.forEach(out::println);
    }
}
