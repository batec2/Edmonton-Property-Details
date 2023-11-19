package com.Github.cmpt305milestone2.DAO;

import com.Github.cmpt305milestone2.Data.Property;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static com.Github.cmpt305milestone2.Data.IOReader.reader;

/**
 * Data access object for interacting with API, amount of data per query is limited by the set limit
 */
public class ApiPropertyAssessmentDAO implements PropertyAssessmentsDAO {
    private int limit = 500;//Amount of data received per API request
    private int offset = 0;//Offset of the data for paging
    private final String endpoint = "https://data.edmonton.ca/resource/q7d6-ambg.csv";
    private String currentQuery;
    private List<String> currentItems;
    HttpClient client;

    public ApiPropertyAssessmentDAO() {
        this.client = HttpClient.newHttpClient();
        currentItems = Arrays.asList("","","","","","");
    }

    /**
     * Gets unfiltered data, limited by the limit variable (500)
     * @return Returns a list of properties sorted by account number
     */
    @Override
    public List<Property> getAll() {
        currentQuery = new QueryBuilder(endpoint)
                .add("ORDER BY","account_number::number")
                .add("OFFSET",offset)
                .add("LIMIT",limit)
                .build();
        HttpResponse<String> response = makeRequest(currentQuery);
        //Resets filters
        currentItems = Arrays.asList("","","","","","");
        if(reader(response)==null){
            return new ArrayList<>();
        }
        return reader(response);
    }

    /**
     * Takes a list of inputs from UI and creates a query based on inputs.
     * After query is made it is sent to api to get matching items
     * @param input Takes in a list of inputs to filter by
     * @return Returns a filtered list of properties sorted by account number
     */
    public List<Property> getSearchResults(List<String> input) {
        currentItems = input;
        QueryBuilder qBuilder = new QueryBuilder(endpoint).addWhere();
        boolean first = true;
        for(int i=0;i<input.size();i++){
            switch(i){
                //Account number
                case 0:
                    if(!input.get(i).isBlank()){
                        qBuilder.addAccountNumber(input.get(i),first);
                        first=false;
                    }
                    break;
                //Address
                case 1:
                    if(!input.get(i).isBlank()){
                        qBuilder.addAddress(input.get(i),first);
                        first=false;
                    }
                    break;
                //Neighbourhood
                case 2:
                    if(!input.get(i).isBlank()){
                        qBuilder.addNeighbourhood(input.get(i),first);
                        first=false;
                    }
                    break;
                //Assessment Class
                case 3:
                    if(!input.get(i).isBlank()){
                        qBuilder.addAssessmentClass(input.get(i),first);
                        first=false;
                    }
                    break;
                //Min Assessed Value
                case 4:
                    if(!input.get(i).isBlank()){
                        qBuilder.addAssessedMin(input.get(i),first);
                        first=false;
                    }
                    break;
                //Max Assessed Value
                case 5:
                    if(!input.get(i).isBlank()){
                        qBuilder.addAssessedMax(input.get(i),first);
                        first=false;
                    }
                    break;
            }
        }

        currentQuery = qBuilder
                .add("ORDER BY","account_number::number")
                .add("OFFSET",offset)
                .add("LIMIT",limit)
                .build();

        HttpResponse<String> response = makeRequest(currentQuery);
        if(reader(response)==null){
            return new ArrayList<>();
        }
        return reader(response);
    }

    /**
     * Re-queries current query, if there are no filters it queries for all items and returns filtered items
     * This is used in conjunction with getOffset and setOffset to page through data
     * @return Returns a list of properties sorted by account number
     */
    public List<Property> pageCurrentQuery(){
        //if no filters then gets all else gets the filtered items
      return currentItems.stream().allMatch(String::isBlank)?this.getAll():this.getSearchResults(currentItems);
    }

    /**
     * Makes a request to the API and returns the HTTPResponse String
     * @param query Full query including endpoint and SQL like query
     * @return HttpResponse object, if nothing comes from httprequest returns null
     */
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
            System.out.println("Error: "+e);
        }
        System.out.println(query);
        return response;
    }

    /**
     * Gets the current offset
     * @return int offset
     */
    public int getOffset() {
        return offset;
    }

    /**
     * Sets the offset of the query sent to api
     * @param offset amount to offset the data
     */
    public void setOffset(int offset) {
        this.offset = offset;
    }
}
