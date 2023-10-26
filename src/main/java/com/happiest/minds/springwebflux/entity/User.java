package com.happiest.minds.springwebflux.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document("user")
@ToString
public class User {

    @Id
    private String userId;
    private String firstname;
    private String lastname;
    private Integer age;
    private String email;
    private String mobile;
    private String city;
}
