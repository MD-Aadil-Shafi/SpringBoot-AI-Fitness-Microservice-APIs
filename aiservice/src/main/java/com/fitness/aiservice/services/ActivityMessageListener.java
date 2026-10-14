package com.fitness.aiservice.services;

import com.fitness.aiservice.models.Activity;
import com.fitness.aiservice.models.RawRecommendation;
import com.fitness.aiservice.repositories.RawRecommendationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.BackOff;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.stereotype.Service;

//to consumer message

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityMessageListener {

    private final ActivityAiService activityAiService;
    private final RawRecommendationRepository repository;

    //method //topic name from yaml file
//    @RetryableTopic(  //activity-events-retry topic will be created
//            attempts = "2",
//            backOff = @BackOff(delay = 180000)
//    )
    @KafkaListener(topics = "${kafka.topic.name}", groupId = "activity-processor-group") //or take dynamically form yaml in group id as well.
    public void processActivity(Activity activity){
        log.info("Received activity for processing: :::::::::::::::::: {}", activity.getUserId());

        RawRecommendation recommendation = activityAiService.generateRecommendation(activity);
        repository.save(recommendation);

    }
}
