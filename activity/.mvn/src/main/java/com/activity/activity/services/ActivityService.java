package com.activity.activity.services;

import com.activity.activity.dtos.ActivityRequest;
import com.activity.activity.dtos.ActivityResponse;
import com.activity.activity.models.Activity;
import com.activity.activity.repositories.ActivityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final UserValidationService userValidationService;
    //for kafka: with the help of kafka template we will send data:
    private final KafkaTemplate<String, Activity> kafkaTemplate;

    //getting topic name from yml file
    @Value("${kafka.topic.name}")
    private String topicName;


    public ActivityResponse trackActivity(ActivityRequest request){
//        boolean isValidUser = userValidationService.validateUser(request.getUserId());
        boolean isValidUser = userValidationService.validateUser(request.getUserId());
        System.out.println("Is valid ............................" + isValidUser + "id................" + request.getUserId());

        if(!isValidUser){
            throw new RuntimeException("Invalid User: " + request.getUserId());
        }
        Activity activity = Activity.builder()
                .userId(request.getUserId())
                .type(request.getType())
                .duration(request.getDuration())
                .caloriesBurned(request.getCaloriesBurned())
                .startTime(request.getStartTime())
                .additionalMetrics(request.getAdditionalMetrics())
                .build();
        Activity savedActivity = activityRepository.save(activity);
        //for kafka . publish to kafka
        //in send object pass topic then key then object.
        try{
            kafkaTemplate.send(topicName, savedActivity.getUserId(), savedActivity);
        }catch(Exception e){
            e.printStackTrace();
        }
        return mapToResponse(savedActivity);
    }

    private ActivityResponse mapToResponse(Activity activity){
        ActivityResponse response = new ActivityResponse();
        response.setId(activity.getId());
        response.setUserId(activity.getUserId());
        response.setType(activity.getType());
        response.setDuration(activity.getDuration());
        response.setCaloriesBurned(activity.getCaloriesBurned());
        response.setStartTime(activity.getStartTime());
        response.setAdditionalMetrics(activity.getAdditionalMetrics());
        response.setCratedAt(activity.getCreatedAt());
        response.setUpdatedAt(activity.getUpdatedAt());
        return response;
    }

}
