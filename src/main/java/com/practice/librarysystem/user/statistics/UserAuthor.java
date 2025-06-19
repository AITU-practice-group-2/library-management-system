package com.practice.librarysystem.user.statistics;

import com.practice.librarysystem.author.Author;
import com.practice.librarysystem.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "users_authors")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"user", "author"})
public class UserAuthor {
    @EmbeddedId
    private UserAuthorId id = new UserAuthorId();

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("authorId")
    @JoinColumn(name = "author_id")
    private Author author;

    private int popularity;
}

@Embeddable
@Data
@NoArgsConstructor
class UserAuthorId implements Serializable {
    private int userId;
    private int authorId;
}

