package com.hong.demo.domain;

import lombok.Data;
// import lombok.NoArgsConstructor;

// import java.util.Date;
// import java.time.LocalDate;
import java.time.LocalDateTime;

// import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;

import com.hong.demo.validation.EnumNamePattern;
// import com.fasterxml.jackson.annotation.JsonFormat;

@Data
public class Review {

    private Integer id;

    // @NotBlank(message = "Name is mandatory")
    // @Size(min = 3, max = 50, message = "must be minimum 3 characters, and maximum 50 characters long")
    private String name;

    // @NotBlank(message = "Email is mandatory")
    // @Email(message="must be valid")
    private String email;

    // @NotBlank(message = "Content is mandatory")
    // @Size(min = 8, max = 500, message = "must be minimum 8 characters, and maximum 500 characters long")
    private String content;

    // @EnumNamePattern(name="likeStatus", regexp="Low|Medium|High")
    private LikeStatus likeStatus; // Low, Medium, High

    // @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Europe/Berlin")
    private LocalDateTime createdOn = LocalDateTime.now();

    // @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Europe/Berlin")
    private LocalDateTime updatedOn;
    
    private Integer bookId;
}
