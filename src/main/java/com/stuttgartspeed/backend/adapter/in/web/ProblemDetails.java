package com.stuttgartspeed.backend.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProblemDetails
{
    private String type;
    private String title;
    private int status;
    private String detail;
    private String instance;
}
