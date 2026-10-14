package com.fitness.aiservice.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "raw_recommendations")
@Data
@Builder
public class RawRecommendation {

    @Id
    private String id;
    private String activityId;
    private String userId;
    private String recommendation; //will store json as sting in parse in client(ui)

    @CreatedDate
    private LocalDateTime createdAt;
}
