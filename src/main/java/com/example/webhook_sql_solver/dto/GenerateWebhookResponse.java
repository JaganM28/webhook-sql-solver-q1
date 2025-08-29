package com.example.webhook_sql_solver.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GenerateWebhookResponse {
    private String webhook;
    private String accessToken;
}
