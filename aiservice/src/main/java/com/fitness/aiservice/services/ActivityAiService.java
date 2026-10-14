package com.fitness.aiservice.services;

import com.fitness.aiservice.models.Activity;
import com.fitness.aiservice.models.RawRecommendation;
import com.fitness.aiservice.models.Recommendation;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityAiService {

    private final GeminiService geminiService;

    private String defaultResponse = "//            * {\n" +
            "//  \"analysis\": {\n" +
            "//    \"overall\": \"A well-executed 45-minute aerobic ride showing solid power consistency (180W) and an efficient, smooth pedal cadence (85 rpm).\",\n" +
            "//    \"pace\": \"Maintained a steady average speed of 21.0 km/h, indicating good pacing control throughout the session.\",\n" +
            "//    \"heartRate\": \"Average heart rate of 135 bpm kept you in an optimal aerobic Zone 2/3, building cardiovascular endurance without excessive fatigue.\",\n" +
            "//    \"caloriesBurned\": \"Burned 420 kcal in 45 minutes, reflecting an effective and high-efficiency caloric burn rate.\"\n" +
            "//  },\n" +
            "//  \"improvements\": [\n" +
            "//    {\n" +
            "//      \"area\": \"Terrain and Equipment Compatibility\",\n" +
            "//      \"recommendation\": \"Switch to a gravel or mountain bike for mountain trail routes, or stick to paved roads with your road bike to optimize handling and power transfer.\"\n" +
            "//    },\n" +
            "//    {\n" +
            "//      \"area\": \"High-End Power Development\",\n" +
            "//      \"recommendation\": \"Incorporate short power surges above 220W to build functional threshold power and improve climbing ability.\"\n" +
            "//    }\n" +
            "//  ],\n" +
            "//  \"suggestions\": [\n" +
            "//    {\n" +
            "//      \"workout\": \"Sweet Spot Power Intervals\",\n" +
            "//      \"description\": \"45-minute ride including 3 x 8-minute intervals at 200–215W with 3-minute easy spins between sets.\"\n" +
            "//    },\n" +
            "//    {\n" +
            "//      \"workout\": \"Zone 2 Base Endurance Ride\",\n" +
            "//      \"description\": \"60–75 minute steady-state ride on paved rolling terrain, keeping HR between 125–135 bpm and cadence above 85 rpm.\"\n" +
            "//    }\n" +
            "//  ],\n" +
            "//  \"safety\": [\n" +
            "//    \"Avoid unpaved or rough mountain trails on narrow road bike tires to minimize the risk of crashes, loss of traction, and pinch flats.\",\n" +
            "//    \"Check tire pressure and inspect brake pads before and after riding on challenging gradients or off-road surfaces.\"\n" +
            "//  ]\n" +
            "//}";

    //this message is called in ActivityMessageListener.
    public RawRecommendation generateRecommendation(Activity activity){
        try {
            String prompt = createPromptForActivity(activity);
            String response = geminiService.getRecommendations(prompt);
//        String response = "testing";
            log.info("RESPONSE FROM AI: {}", response);
            //now create processAiResponse
            return processAiResponse(activity, response);
        }catch (Exception e) {
            e.printStackTrace();
            return processAiResponse(activity, "Error: Unable to generate AI response.");

        }

    }



    private RawRecommendation processAiResponse(Activity activity, String aiResponse){

        return RawRecommendation.builder()
                .activityId(activity.getId())
                .userId(activity.getUserId())
                .recommendation(aiResponse)
                .build();
    }

    private String createPromptForActivity(Activity activity) {
        return String.format("""
        Analyze this fitness activity and provide short recommendations in the following EXACT JSON format:
        {
          "analysis": {
            "overall": "Overall analysis here",
            "pace": "Pace analysis here",
            "heartRate": "Heart rate analysis here",
            "caloriesBurned": "Calories analysis here"
          },
          "improvements": [
            {
              "area": "Area name",
              "recommendation": "Short recommendation"
            }
          ],
          "suggestions": [
            {
              "workout": "Workout name",
              "description": "Short workout description"
            }
          ],
          "safety": [
            "Safety point 1",
            "Safety point 2"
          ]
        }

        Analyze this activity:
        Activity Type: %s
        Duration: %d minutes
        Calories Burned: %d
        Additional Metrics: %s
        
        Provide short analysis focusing on performance, improvements, next workout suggestions, and safety guidelines.
        Ensure the response follows the EXACT JSON format shown above.
        """,
                activity.getType(),
                activity.getDuration(),
                activity.getCaloriesBurned(),
                activity.getAdditionalMetrics()
        );
    }

}
