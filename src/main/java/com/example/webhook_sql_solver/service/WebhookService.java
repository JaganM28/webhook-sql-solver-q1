package com.example.webhook_sql_solver.service;

import com.example.webhook_sql_solver.dto.GenerateWebhookRequest;
import com.example.webhook_sql_solver.dto.GenerateWebhookResponse;
import com.example.webhook_sql_solver.dto.SubmitQueryRequest;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WebhookService {
    private final RestTemplate restTemplate = new RestTemplate();

    public GenerateWebhookResponse generateWebhookResponse(GenerateWebhookRequest request){
        String url = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";
        System.out.println("Sending request to generate webhook at: "+url);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<GenerateWebhookRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<GenerateWebhookResponse> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                GenerateWebhookResponse.class
        );

        System.out.println("Webhook generated successfully");
        return response.getBody();
    }
    public void submitFinalQuery(String webhookUrl, String accessToken, SubmitQueryRequest queryRequest) {
        System.out.println("Submitting the final query to: " + webhookUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", accessToken);

        HttpEntity<SubmitQueryRequest> entity = new HttpEntity<>(queryRequest, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                webhookUrl,
                HttpMethod.POST,
                entity,
                String.class
        );

        System.out.println("Successfully submitted the final query.");
        System.out.println("Response Status: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody());
    }
}
