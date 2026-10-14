package com.fitness.aiservice.controllers;

import com.fitness.aiservice.models.RawRecommendation;
import com.fitness.aiservice.models.Recommendation;
import com.fitness.aiservice.services.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<Recommendation>> getUserRecommendation(@PathVariable String userId){
        return ResponseEntity.ok(recommendationService.getUserRecommendation(userId));
    }

    @GetMapping("/activity/{activityId}")
    public ResponseEntity<Recommendation> getActivityRecommendation(@PathVariable String activityId){
        return ResponseEntity.ok(recommendationService.getActivityRecommendation(activityId));
    }

    @GetMapping("/users/raw/{userId}")
    public ResponseEntity<List<RawRecommendation>> geRawtUserRecommendation(@PathVariable String userId){
        return ResponseEntity.ok(recommendationService.getRawUserRecommendation(userId));
    }

    @GetMapping("/activity/raw/{activityId}")
    public ResponseEntity<RawRecommendation> getRawActivityRecommendation(@PathVariable String activityId){
        return ResponseEntity.ok(recommendationService.getRawRecommendationByActivity(activityId));
    }
}
