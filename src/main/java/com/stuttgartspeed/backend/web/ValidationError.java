package com.stuttgartspeed.backend.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ValidationError
{
    private String fieldName;
    private String message;
    private String rejectedValue;
}
