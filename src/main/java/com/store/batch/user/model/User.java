package com.store.batch.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(value = "USER")
public class User {
    @Id
    private Integer id;
    private String name;
    private Integer age;
    private char gender;
    private String email;
    private String dob;
    private String country;
}
