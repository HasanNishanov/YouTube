package com.company.dto.category;

import com.company.enums.CategoryStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CategoryDTO {

    private Integer id;
    private String name;
    private LocalDateTime createdDate;
    private CategoryStatus status;
    Boolean visible;

}
