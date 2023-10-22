package com.Github.cmpt305milestone2.DAO;

import com.Github.cmpt305milestone2.DAO.ApiPropertyAssessmentDAO;

import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) throws URISyntaxException {
        /*

        String endpoint = "https://data.edmonton.ca/resource/q7d6-ambg.csv";
        //String query ="https://data.edmonton.ca/resource/q7d6-ambg.json?$query=SELECT%20*%20WHERE%20mill_class_1=%27COMMERCIAL%27";
        //String query = endpoint+"?account_number='3379600'";
        HttpClient client = HttpClient.newHttpClient();
        //builds query for api
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(new URI(new QueryBuilder("https://data.edmonton.ca/resource/q7d6-ambg.csv")
                        .addToQuery("SELECT","*")
                        .addToQuery("ORDER%20BY","account_number")
                        .addToQuery("LIMIT","100")
                        .build()))
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
         */
        ApiPropertyAssessmentDAO apiPropertyAssessmentDAO =
                new ApiPropertyAssessmentDAO();

        System.out.println(apiPropertyAssessmentDAO.getByAccountnumber(10000216));

        System.out.println(apiPropertyAssessmentDAO.getByAssessmentClass("RESIDENTIAL"));


        //apiPropertyAssessmentDAO.propertyAssessments.printEntrySet();

        /*
        PropertyAssessments filtered =
                apiPropertyAssessmentDAO
                        .propertyAssessments
                        .getFiltered(entry->entry
                                .getValue()
                                .inWard("sipiwiyiniwak Ward"));
        System.out.println("FILTER 1");
        filtered.printEntrySet();
        */

        /*
        PropertyAssessments filtered2 = apiPropertyAssessmentDAO.propertyAssessments
                .getWard("sipiwiyiniwak Ward");
        System.out.println("FILTER 2");
        filtered2.printEntrySet();
         */
    }
}
