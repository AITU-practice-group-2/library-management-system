package com.practice.librarysystem.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // Optionally:
    // @OneToMany(mappedBy = "category")
    // private List<Book> books;
}
