package com.arnoldgalovics.jpa.service;

import com.arnoldgalovics.jpa.repository.domain.Book;
import com.arnoldgalovics.jpa.repository.domain.BookType;
import com.arnoldgalovics.jpa.repository.domain.Comment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static java.lang.System.out;

@Service
public class BookService {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void create(String name, int price, BookType type) {
        Book book = createBook(name, price, type);
        entityManager.persist(book);
    }

    @Transactional
    public Integer createWithComment(String name, int price, BookType type, String commentText) {
        Book book = createBook(name, price, type);
        Comment comment = new Comment();
        comment.setText(commentText);
        comment.setBook(book);
        book.setComments(Collections.singleton(comment));
        entityManager.persist(book);
        return book.getId();
    }

    @Transactional
    public void method() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> query = cb.createQuery(Book.class);
        query.from(Book.class);
        List<Book> books = entityManager.createQuery(query).getResultList();
        books.forEach(out::println);
    }

    @Transactional
    public void removeComment(Integer bookId) {
        Book book = entityManager.find(Book.class, bookId);
        Iterator<Comment> iterator = book.getComments().iterator();
        iterator.next();
        iterator.remove();
    }

    private Book createBook(String name, int price, BookType type) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setType(type);
        return book;
    }
}
