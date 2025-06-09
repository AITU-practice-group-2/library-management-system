package com.practice.librarysystem.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // Optionally:
    // @OneToMany(mappedBy = "author")
    // private List<Book> books;
}
