package com.activity.activity.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.Map;

@Document(collation = "activities")
@Data
@Builder //to make object using builder pattern
@AllArgsConstructor
@NoArgsConstructor
public class Activity {

    @Id
    private String id;
    private String userId;

    //enum as ActivityType is also enum
    private ActivityType type;

    private Integer duration;
    private Integer caloriesBurned;
    private LocalDateTime startTime;

    //to store metrics //here using Object to insert any object data
    @Field("metrics") //field name metrics for this
    private Map<String, Object> additionalMetrics;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
