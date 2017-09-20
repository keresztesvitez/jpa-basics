package com.arnoldgalovics.jpa.service;

import com.arnoldgalovics.jpa.internal.repository.domain.Book;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
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
//        List<Book> books = entityManager.createQuery("SELECT b FROM Book b", Book.class).getResultList();
        List<Book> books = entityManager.createQuery("FROM Book b", Book.class).getResultList();
        books.forEach(out::println);
    }

    @Transactional
    public void selectWithCriteriaApi(String nameFilterValue) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> query = cb.createQuery(Book.class);
        Root<Book> root = query.from(Book.class);
        Path<String> name = root.get("name");

        List<Predicate> predicates = new ArrayList<>();

        if (nameFilterValue != null) {
            predicates.add(cb.equal(name, nameFilterValue));
        }

        query.where(predicates.toArray(new Predicate[predicates.size()]));
        List<Book> books = entityManager.createQuery(query).getResultList();
        books.forEach(out::println);

        //MEMO: Validate queries via logs
    }

}
