package com.sparta.twotwo.store.scheduler;

import com.sparta.twotwo.review.entity.Review;
import com.sparta.twotwo.store.entity.Store;
import com.sparta.twotwo.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class StoreScheduler {

    private final StoreRepository storeRepository;

    @Transactional
//    @Scheduled(cron = "0 0 * * * *")
    public void updateAverageStoreRatingsAndReviewCountTaskUsingDatabase() {

        List<Object[]> reviewStats = storeRepository.findAverageRatingsAndReviewCount();

        for (Object[] reviewStat : reviewStats) {
            UUID storeId = (UUID) reviewStat[0];
            BigDecimal averageRating = BigDecimal.valueOf((Double) reviewStat[1]);
            Integer reviewCount = ((Long) reviewStat[2]).intValue();
            storeRepository.updateRatingAndReviewCount(storeId, averageRating, reviewCount);
        }

    }

//    @Scheduled(fixedRate = 1000000)
    @Scheduled(cron = "0 0 * * * *")
    public void updateStoreReviewStats() {

        List<Store> stores = storeRepository.findStoresWithReviews();

        for (Store store : stores) {
            calculateAndSaveStoreRatingsAsync(store);
        }

    }

    @Async
    @Transactional
    public void calculateAndSaveStoreRatingsAsync(Store store) {
        List<Review> reviews = store.getReviews();
        Integer reviewCount = reviews.size();

        double averageRating = reviews.stream().mapToInt(Review::getRating).average().orElse(0.0);

        store.updateRating(BigDecimal.valueOf(averageRating));
        store.updateReviewCount(reviewCount);

        storeRepository.saveAndFlush(store);
    }

}
