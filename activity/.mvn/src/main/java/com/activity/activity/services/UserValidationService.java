package com.activity.activity.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserValidationService {

    private final WebClient userServiceWebClient; //userServiceWebClient this name also same in config.

    //will use it in activityService
    public boolean validateUser(String userId){
        try{
            return userServiceWebClient.get()
                    .uri("/api/users/{userId}/validate", userId)
                    .retrieve()
                    .bodyToMono(Boolean.class) //for boolean conversion
                    .block(); //to block this tread

            //mono: Expecting 0 or 1 response (single object)
            //flux: Expecting 0 or many responses (stream/collection)
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;

    }
    //above function was using only one exception handling so for other exception ther server was crashing.
//    public boolean validateUser(String userId) {
//        if (userId == null || userId.trim().isEmpty()) {
//            log.warn("Attempted to validate null/empty userId");
//            return false;
//        }
//
//        try {
//            log.debug("Validating user: {}", userId);
//
//            Boolean result = userServiceWebClient.get()
////                    .uri("/api/users/{userId}/validate", userId)
//                    .uri("/api/users/test")
//                    .retrieve()
////                    .onStatus(HttpStatus::is4xxClientError, response -> {
////                        log.warn("Client error for user {}: {}", userId, response.statusCode());
////                        return Mono.empty();
////                    })
////                    .onStatus(HttpStatus::is5xxServerError, response -> {
////                        log.error("Server error for user {}: {}", userId, response.statusCode());
////                        return Mono.empty();
////                    })
//                    .bodyToMono(Boolean.class)
//                    .defaultIfEmpty(false)  // handles empty response
//                    .block();
//
//            log.debug("Validation result for user {}: {}", userId, result);
//            return result != null && result;
//
//        } catch (WebClientResponseException e) {
//            // HTTP errors (4xx, 5xx)
//            log.error("HTTP error validating user {}: Status={}, Body={}",
//                    userId,
//                    e.getStatusCode(),
//                    e.getResponseBodyAsString());
//            return false;
//
//        } catch (WebClientRequestException e) {
//            // Network errors - THIS IS YOUR CURRENT ISSUE
//            log.error("Network error validating user {}: {}", userId, e.getMessage());
//
//            // Log the cause for debugging
//            Throwable cause = e.getCause();
//            if (cause != null) {
//                log.error("Root cause: {}", cause.getMessage());
//            }
//
//            return false;
//
//        } catch (Exception e) {
//            // Catch-all for any unexpected errors
//            log.error("Unexpected error validating user {}: {}", userId, e.getMessage(), e);
//            return false;
//        }
//    }





}
