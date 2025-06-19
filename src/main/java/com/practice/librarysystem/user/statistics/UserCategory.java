package com.practice.librarysystem.user.statistics;

import com.practice.librarysystem.category.Category;
import com.practice.librarysystem.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "users_categories")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"user", "category"})
public class UserCategory {

    @EmbeddedId
    private UserCategoryId id = new UserCategoryId();

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("categoryId")
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "popularity")
    private int popularity;
}

@Embeddable
@Data
@NoArgsConstructor
class UserCategoryId implements Serializable {
    private int userId;
    private int categoryId;
}
