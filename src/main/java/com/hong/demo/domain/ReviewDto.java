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
import com.hong.demo.validation.StatusValidation;
// import com.fasterxml.jackson.annotation.JsonFormat;


@Data
public class ReviewDto {

    // @NotNull
    @NotBlank(message = "Name is mandatory")
    @Size(min = 3, max = 50, message = "must be minimum 3 characters, and maximum 50 characters long")
    private String name;

    // @NotNull
    @NotBlank(message = "Email is mandatory")
    @Email(message="must be valid")
    private String email;

    // @NotNull
    @NotBlank(message = "Content is mandatory")
    @Size(min = 8, max = 500, message = "must be minimum 8 characters, and maximum 500 characters long")
    private String content;

    // @NotNull
    // @NotBlank(message = "likeStatus is mandatory")
    @StatusValidation(name="likeStatus")  // throws a MethodArgumentNotValidException exception.
    private String likeStatus;

}
