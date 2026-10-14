package com.activity.activity.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@EnableMongoAuditing //for auto crated/updatedAt
public class MongoConfig {

    @Bean
    public MongoClient mongoClient() {
        // Explicit connection string with all details
        //TODO: dynamic uri
        String connectionString = "mongodb://admin:admin@localhost:27018/sb_ai_activity_db_mongo?authSource=admin&connectTimeoutMS=30000&socketTimeoutMS=30000";

        System.out.println("🔗 Creating MongoClient with: " + connectionString.replace("admin:admin", "admin:****"));

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .build();

        MongoClient client = MongoClients.create(settings);

        // Test the connection immediately
        try {
            var db = client.getDatabase("sb_ai_activity_db_mongo");
            var ping = db.runCommand(new org.bson.Document("ping", 1));
            System.out.println("MongoDB connection successful! Ping: " + ping);
        } catch (Exception e) {
            System.err.println("MongoDB connection failed: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to connect to MongoDB", e);
        }

        return client;
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoClient mongoClient) {
        return new MongoTemplate(mongoClient, "sb_ai_activity_db_mongo");
    }
}