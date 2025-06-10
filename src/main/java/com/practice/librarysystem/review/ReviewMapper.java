package com.practice.librarysystem.review;
import com.practice.librarysystem.user.User;
import com.practice.librarysystem.book.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface ReviewMapper {

    // For response
    @Mapping(source = "book.title", target = "bookTitle")
    @Mapping(source = "user.login", target = "userName")
    ReviewResponseDTO toDTO(Review review);

    default Review toEntity(ReviewRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Review review = new Review();
        review.setComment(dto.getComment());
        review.setRating(dto.getRating());

        User user = new User();
        user.setId(dto.getUserId());
        review.setUser(user);        

        Book book = new Book();
        book.setId(dto.getBookId()); 
        review.setBook(book);

        review.setCreatedAt(java.time.LocalDateTime.now());

        return review;
    }
}
