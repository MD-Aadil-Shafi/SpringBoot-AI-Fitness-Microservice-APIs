package com.fitness.aiservice.repositories;

import com.fitness.aiservice.models.RawRecommendation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface RawRecommendationRepository extends MongoRepository<RawRecommendation, String> {

    List<RawRecommendation> findByUserId(String userId);
    Optional<RawRecommendation> findByActivityId(String activityId);
}
