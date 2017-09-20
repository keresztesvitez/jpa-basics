package com.arnoldgalovics.jpa.internal.repository.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "books")
public class Book {

    public Book(String name, int price, int pages, long isbn, Genre genre, Set<Comment> comments) {
        this.name = name;
        this.price = price;
        this.pages = pages;
        this.isbn = isbn;
        this.genre = genre;
        this.comments = comments;
    }

    public Book() {
    }
    public Book(int id) {
        this.id = id;
    }

    @Id
    @GeneratedValue
    private int id;

    private String name;

    private int price;

    private int pages;

    @Column(name = "ISBN")
    private long isbn;

    private Genre genre;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private Set<Comment> comments;

    @ManyToMany(mappedBy = "books", cascade = CascadeType.PERSIST)
    private Set<Author> authors;

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public long getIsbn() {
        return isbn;
    }

    public void setIsbn(long isbn) {
        this.isbn = isbn;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        return id == book.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", pages=" + pages +
                ", isbn=" + isbn +
                ", genre=" + genre +
                ", comments=" + comments +
                '}';
    }
}
