package com.hong.demo.domain;

// import java.util.UUID;
import jakarta.validation.constraints.NotBlank;
// import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Image {
    private Integer id;
    private String uuid;

    @NotBlank
    private String title;
    private Integer bookId;
}
