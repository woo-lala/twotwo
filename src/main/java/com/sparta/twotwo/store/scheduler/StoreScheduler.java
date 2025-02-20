package com.sparta.twotwo.store.scheduler;

import com.sparta.twotwo.review.entity.Review;
import com.sparta.twotwo.review.repository.ReviewRepository;
import com.sparta.twotwo.store.entity.Store;
import com.sparta.twotwo.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StoreScheduler {

    private final StoreRepository storeRepository;
    private final ReviewRepository reviewRepository;

    //    @Scheduled(cron = "0 0 * * * *")
    @Scheduled(fixedRate = 10000000)
    public void updateAverageStoreRatingsAndReviewCountTask() {

        List<Store> stores = storeRepository.findStoresWithReviews();

        for (Store store : stores) {
            List<Review> reviews = store.getReviews();
            Integer reviewCount = reviews.size();

            double averageRating = reviews.stream().mapToInt(Review::getRating).average().orElse(0.0);

            store.updateRating(BigDecimal.valueOf(averageRating));
            store.updateReviewCount(reviewCount);

            storeRepository.saveAndFlush(store);

        }

    }

}
