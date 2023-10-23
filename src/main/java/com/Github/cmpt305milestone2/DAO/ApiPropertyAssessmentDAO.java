package com.Github.cmpt305milestone2.DAO;

import com.Github.cmpt305milestone2.Data.Property;
import com.Github.cmpt305milestone2.Data.QueryBuilder;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static com.Github.cmpt305milestone2.Data.IOReader.accountReader;
import static com.Github.cmpt305milestone2.Data.IOReader.reader;

public class ApiPropertyAssessmentDAO implements PropertyAssessmentsDAO {
    private int limit = 100;
    private int offset = 0;
    private final String endpoint = "https://data.edmonton.ca/resource/q7d6-ambg.csv";
    HttpClient client;

    public ApiPropertyAssessmentDAO() {
        //this.endpoint = endpoint;
        this.client = HttpClient.newHttpClient();
    }

    @Override
    public Property getByAccountnumber(int accountNumber) {
        String query = new QueryBuilder(endpoint)
                .add("SELECT","*")
                .add("WHERE","account_number",accountNumber)
                .build();
        System.out.println(query);
        HttpResponse<String> response = makeRequest(query);
        return accountReader(response);
    }

    @Override
    public List<Property> getByNeightbourhood(String neighbourhood) {
        String query = new QueryBuilder(endpoint)
                .add("SELECT","*")
                .add("WHERE","neighbourhood",neighbourhood)
                .add("ORDER BY","account_number")
                .add("OFFSET",offset)
                .add("LIMIT",limit)
                .build();
        HttpResponse<String> response = makeRequest(query);
        return reader(response).values().stream().toList();
    }

    @Override
    public List<Property> getByAssessmentClass(String assessmentClass) {
        String query = new QueryBuilder(endpoint)
                .add("SELECT","*")
                .add("WHERE","mill_class_1",assessmentClass)
                .add("OR","mill_class_1",assessmentClass)
                .add("OR","mill_class_1",assessmentClass)
                .add("ORDER BY","account_number")
                .add("OFFSET",offset)
                .add("LIMIT",limit)
                .build();
        HttpResponse<String> response = makeRequest(query);
        return reader(response).values().stream().toList();
    }

    @Override
    public List<Property> getAll() {
        String query = new QueryBuilder(endpoint)
                .add("SELECT","*")
                .add("ORDER BY","account_number")
                .add("OFFSET",offset)
                .add("LIMIT",limit)
                .build();
        HttpResponse<String> response = makeRequest(query);
        return reader(response).values().stream().toList();
    }

    private HttpResponse<String> makeRequest(String query){
        HttpRequest request;
        HttpResponse<String> response=null;
        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI(query))
                    .GET()
                    .build();

            response = client
                    .send(request,HttpResponse.BodyHandlers.ofString());
        }
        catch (Exception e){
            System.out.println(e);
        }
        return response;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
