package com.Github.cmpt305milestone2.DAO;

import com.Github.cmpt305milestone2.DAO.ApiPropertyAssessmentDAO;
import com.Github.cmpt305milestone2.Data.IOReader;
import com.Github.cmpt305milestone2.Data.PropertyAssessments;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws URISyntaxException {
        PropertyAssessments propertyAssessments = new PropertyAssessments(IOReader.reader("./Property_Assessment_Data_2023.csv"));
        QueryBuilder query = new QueryBuilder("https://data.edmonton.ca/resource/q7d6-ambg.csv");
        query.addWhere();
        query.addAccountNumber("1194919",true);
        query.addNeighbourhood("MEADOWLARK PARK",false);
        query.addAssessedMin("4000",false);
        query.addAssessedMax("384500",false);
        query.addAddress("88A AVENUE",false);
        HttpClient client = HttpClient.newHttpClient();
        //builds query for api
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(new URI(query.build()))
                .GET()
                .build();
        System.out.println(request);
        try {
            //sends query to api
            HttpResponse<String> response = client
                    .send(request,HttpResponse.BodyHandlers.ofString());
            Scanner scanner = new Scanner(response.body());
            while(scanner.hasNext()){
                System.out.println(scanner.nextLine());
            }

        }
        catch(IOException | InterruptedException e){
            e.printStackTrace();
        }

    }
}
