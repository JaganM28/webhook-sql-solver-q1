package com.example.webhook_sql_solver.runner;

import com.example.webhook_sql_solver.dto.GenerateWebhookRequest;
import com.example.webhook_sql_solver.dto.GenerateWebhookResponse;
import com.example.webhook_sql_solver.dto.SubmitQueryRequest;
import com.example.webhook_sql_solver.service.WebhookService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppStartupRunner implements CommandLineRunner{
    private final WebhookService webhookService;

    public AppStartupRunner(WebhookService webhookService){
        this.webhookService = webhookService;
    }

    @Override
    public void run(String... args) throws Exception{
        System.out.println("Application started. Going to run the SQL query");

        try{
            GenerateWebhookRequest initialRequest = new GenerateWebhookRequest(
                    "John Doe",
                    "REG12347",
                    "john@example.com"
            );

            GenerateWebhookResponse webhookResponse = webhookService.generateWebhookResponse(initialRequest);
            if (webhookResponse==null || webhookResponse.getWebhook()==null ||webhookResponse.getAccessToken()==null){
                System.err.println("Failed to retrieve webhook URL or access token");
                return;
            }

            System.out.println("Received Webhook URL: " + webhookResponse.getWebhook());
            System.out.println("Received Access Token: " + webhookResponse.getAccessToken());

            String sqlQuery = getQuery();
            System.out.println("SQL Query:");
            System.out.println(sqlQuery);

            SubmitQueryRequest queryRequest = new SubmitQueryRequest(sqlQuery);
            webhookService.submitFinalQuery(
                    webhookResponse.getWebhook(),
                    webhookResponse.getAccessToken(),
                    queryRequest
            );

            System.out.println("Process Completed");

        }
        catch (Exception e){
            System.err.println("Error occurred: "+e.getMessage());
            e.printStackTrace();
        }
    }
    private String getQuery() {
        // My registration number ends with 18, so I got the even task
        return "SELECT " + "e1.EMP_ID, " + "e1.FIRST_NAME, " + "e1.LAST_NAME, " + "d.DEPARTMENT_NAME, " +
                "(SELECT COUNT(*) FROM EMPLOYEE e2 WHERE e2.DEPARTMENT = e1.DEPARTMENT AND e2.DOB > e1.DOB)"+
                " AS YOUNGER_EMPLOYEES_COUNT " +
                "FROM " + "EMPLOYEE e1 " + "JOIN " +
                "DEPARTMENT d ON e1.DEPARTMENT = d.DEPARTMENT_ID " +
                "ORDER BY " + "e1.EMP_ID DESC;";
    }
}
