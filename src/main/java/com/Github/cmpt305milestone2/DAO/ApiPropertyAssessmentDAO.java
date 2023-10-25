package com.Github.cmpt305milestone2.DAO;

import com.Github.cmpt305milestone2.Data.Property;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import static com.Github.cmpt305milestone2.Data.IOReader.accountReader;
import static com.Github.cmpt305milestone2.Data.IOReader.reader;

public class ApiPropertyAssessmentDAO implements PropertyAssessmentsDAO {
    private int limit = 100;
    private int offset = 0;
    private final String endpoint = "https://data.edmonton.ca/resource/q7d6-ambg.csv";
    private String currentQuery;
    private String currentItem;
    private String currentFilter = "All";
    HttpClient client;

    public ApiPropertyAssessmentDAO() {
        //this.endpoint = endpoint;
        this.client = HttpClient.newHttpClient();
    }

    @Override
    public Property getByAccountnumber(int accountNumber) {
        currentQuery = new QueryBuilder(endpoint)
                .add("SELECT","*")
                .add("WHERE","account_number",accountNumber)
                .build();
        HttpResponse<String> response = makeRequest(currentQuery);
        return accountReader(response);
    }

    @Override
    public List<Property> getByNeightbourhood(String neighbourhood) {
        currentFilter = "Neighbour";
        currentItem = neighbourhood.toUpperCase();
        currentQuery = new QueryBuilder(endpoint)
                .add("SELECT","*")
                .add("WHERE","neighbourhood",currentItem)
                .add("ORDER BY","account_number")
                .add("OFFSET",offset)
                .add("LIMIT",limit)
                .build();
        System.out.println(currentQuery);
        HttpResponse<String> response = makeRequest(currentQuery);
        if(reader(response)==null){
            return new ArrayList<>();
        }
        return reader(response);
    }

    @Override
    public List<Property> getByAssessmentClass(String assessmentClass) {
        currentFilter = "Assessment";
        currentItem = assessmentClass.toUpperCase();
        currentQuery = new QueryBuilder(endpoint)
                .add("SELECT","*")
                .add("WHERE","mill_class_1",currentItem)
                .add("OR","mill_class_1",currentItem)
                .add("OR","mill_class_1",currentItem)
                .add("ORDER BY","account_number")
                .add("OFFSET",offset)
                .add("LIMIT",limit)
                .build();
        System.out.println(currentQuery);
        HttpResponse<String> response = makeRequest(currentQuery);
        if(reader(response)==null){
            return new ArrayList<>();
        }
        return reader(response);
    }

    @Override
    public List<Property> getAll() {
        currentFilter = "All";
        currentQuery = new QueryBuilder(endpoint)
                .add("SELECT","*")
                .add("ORDER BY","account_number")
                .add("OFFSET",offset)
                .add("LIMIT",limit)
                .build();
        System.out.println(currentQuery);
        HttpResponse<String> response = makeRequest(currentQuery);
        return reader(response);
    }

    public List<Property> pageCurrentQuery(){
        return switch (currentFilter) {
            case "Neighbour" -> this.getByNeightbourhood(currentItem);
            case "Assessment" -> this.getByAssessmentClass(currentItem);
            case "All" -> this.getAll();
            default -> null;
        };
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
