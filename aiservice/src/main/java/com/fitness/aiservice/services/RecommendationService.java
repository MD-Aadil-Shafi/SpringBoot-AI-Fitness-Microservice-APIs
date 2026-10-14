package com.fitness.aiservice.services;

import com.fitness.aiservice.models.RawRecommendation;
import com.fitness.aiservice.models.Recommendation;
import com.fitness.aiservice.repositories.RawRecommendationRepository;
import com.fitness.aiservice.repositories.RecommendationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final RecommendationRepository recommendationRepository;
    private final RawRecommendationRepository repository;

    public List<Recommendation> getUserRecommendation(String userId){
        return recommendationRepository.findByUserId(userId);
    }

    public Recommendation getActivityRecommendation(String activityId){
        return recommendationRepository.findByActivityId(activityId)
                .orElseThrow(()-> new RuntimeException("No recommendation found for this id: " + activityId));
    }

    public List<RawRecommendation> getRawUserRecommendation(String userId){
        return repository.findByUserId(userId);
    }


    public RawRecommendation getRawRecommendationByActivity(String activityId){
        return repository.findByActivityId(activityId)
                .orElseThrow(()-> new RuntimeException("No recommendation found for this id: " + activityId));
    }
}
