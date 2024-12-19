
package com.hong.demo.domain;

// import lombok.*; 
// import lombok.NoArgsConstructor;
// import lombok.AllArgsConstructor;
import lombok.Data;
 
import java.util.List;
// import java.util.Date;

import java.time.LocalDate;
import java.time.LocalDateTime;
// import java.time.format.DateTimeFormatter;

// import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// import com.fasterxml.jackson.annotation.JsonFormat;


@Data
public class Book { 
    
    private Integer id;
    
    // @NotNull
    @NotBlank(message = "Title is mandatory")
    @Size(min = 3, max = 50, message = "must be minimum 3 characters, and maximum 50 characters long")
    private String title;
   
    // @NotNull
    @NotBlank(message = "Content is mandatory")
    @Size(min = 3, max = 500, message = "must be minimum 3 characters, and maximum 500 characters long")
    private String content;
    
    // @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Europe/Berlin")
    private LocalDateTime createdOn; // LocalDateTime.now();

    // @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Europe/Berlin")
    private LocalDateTime updatedOn;
    
    private List<Review> reviews; 
}
