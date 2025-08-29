package com.example.webhook_sql_solver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmitQueryRequest {
    private String finalQuery;
}
