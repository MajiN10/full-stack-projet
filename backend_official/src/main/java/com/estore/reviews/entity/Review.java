package com.estore.reviews.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.*;
import java.time.LocalDateTime;

@Document(collection = "product_reviews")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Review {
    @Id
    private String id; // MongoDB IDs are usually Strings (ObjectIds)

    private Long productId;   // Reference to JPA Product ID
    private Long userId;      // Reference to JPA User ID
    private String authorName;
    private Integer rating;    // Note out of 5
    private String comment;
    private LocalDateTime createdAt = LocalDateTime.now();
}