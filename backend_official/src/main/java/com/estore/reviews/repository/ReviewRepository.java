package com.estore.reviews.repository;

import com.estore.reviews.entity.Review;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ReviewRepository extends MongoRepository<Review, String> {
    // Custom query to find all reviews for a specific product
    List<Review> findByProductId(Long productId);
}