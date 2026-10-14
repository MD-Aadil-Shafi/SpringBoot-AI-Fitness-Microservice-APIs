package com.fitness.aiservice.services;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import tools.jackson.databind.JsonNode;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
//@RequiredArgsConstructor (as using own constructor)
public class GeminiService {

//    to call api(webClient)
    private final WebClient webClient;

    @Value("${gemini.api.url}")
    private String geminiApiUrl;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    private GeminiService(WebClient.Builder webClientBuilder){
        this.webClient = webClientBuilder.build();
    }

    //as we'll get response like this:
    /**
     * {
     *     "contents":[
     *     {
     *         "parts":[
     *         {
     *             "text" "What is the capital"
     *         }
     *         ]
     *     }
     *     ]
     * }
     */

    public String getRecommendations(String details){

        //as per new doc:
        Map<String, String> requestBody = Map.of(
                "model", "gemini-3.7-flash",
                "input", details
        );

        try{
            JsonNode response = webClient.post()
                    .uri(geminiApiUrl)
                    .header("Content-Type", "application/json")
                    .header("x-goog-api-key", geminiApiKey)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .block();

//        System.out.println("RESPNSE::: interna;:::" + response);
//                return response.asString();
            log.info("Gemini response:\n{}", response.toPrettyString());
            return response
                    .path("steps")
                    .findValues("content")
                    .get(0)
                    .get(0)
                    .path("text")
                    .asText();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "Unalbe to call API";
    }

}
