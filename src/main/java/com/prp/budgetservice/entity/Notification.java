package com.prp.budgetservice.entity;


import com.arangodb.entity.Id;
import com.arangodb.springframework.annotation.Document;
import com.arangodb.springframework.annotation.Field;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("notifications")
public class Notification {

    @Id
    private String id;

    @Field("user_id")
    private String userId;

    @Field("title")
    private String title;

    @Field("message")
    private String message;

    @Field("read")
    private Boolean Read;

    @Field("created_at")
    private LocalDateTime createdAt= LocalDateTime.now();

}
