/**
 * Implements class that hold hashmap of property assessments data
 * @author Crush Bate
 */
package com.Github.cmpt305milestone2.Data;

import java.math.BigDecimal;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import static com.Github.cmpt305milestone2.Data.IOReader.reader;
import static com.Github.cmpt305milestone2.Data.Statistics.*;


/**
 * Object for all Property Assessments with methods to calculate statistics on data
 */
public class PropertyAssessments{
    private Map<Integer,Property> properties;

    /**
     * Constructor for new object, reads in data from csv
     * @param fileName
     * Properties Assessments csv
     */
    public PropertyAssessments(String fileName) {
        this.properties = reader(fileName);
    }

    /**
     * Constructor for new object, reads from api
     * @param response
     * HttpResponse from a query to api
     */
    /* TODO
    public PropertyAssessments(HttpResponse<String> response) {
        this.properties = reader(response);
    }
    */

    /**
     * Constructor for if there is existing hashmap
     * @param newMap
     * HashMap containing Properties
     */
    public PropertyAssessments(Map<Integer,Property> newMap){this.properties = newMap;}

    /**
     * Gets all hashmap of properties
     * @return
     * Properties hashmap
     */
    public Map<Integer,Property> getProperties() {
        return properties;
    }

    /**
     * Prints all entries in hashmap
     */
    public void printEntrySet(){
        for(Map.Entry<Integer, Property> entry:this.properties.entrySet()){
            System.out.println(entry.getValue().toString());
        }
    }

    /**
     * Gets all entries in hashmap that have matching Neighbourhood with input
     * @param input
     * String of Neighbourhood to be filtered
     * @return
     * Returns new PropertyAssessments object containing filtered hashmap
     */
    public PropertyAssessments getFiltered(Predicate<Map.Entry<Integer,Property>> predicate){
        Map<Integer,Property> result = new HashMap<>();
        this.properties.entrySet()
                .stream()
                .filter(predicate)
                .forEach(entry->result.put(entry.getKey(), entry.getValue()));//creates new hashmap
        return new PropertyAssessments(result);
    }

    /**
     * Gets all entries in hashmap that have matching Neighbourhood with input
     * @param input
     * String of Neighbourhood to be filtered
     * @return
     * Returns new PropertyAssessments object containing filtered hashmap
     */
    public PropertyAssessments getNeighbourhood(String input){
        if(input==null||input.isBlank()){return null;}
        Map<Integer,Property> result = new HashMap<>();
        this.properties.entrySet()
                .stream()
                .filter(entry->entry.getValue().getHouse().inNeighbourhood(input))
                .forEach(entry->result.put(entry.getKey(), entry.getValue()));
        return new PropertyAssessments(result);
    }

    /**
     * Gets all entries in hashmap that have matching ward with input
     * @param input
     * String of ward to be filtered
     * @return
     * Returns new PropertyAssessments object containing filtered hashmap
     */
    public PropertyAssessments getWard(String input){
        if(input==null||input.isBlank()){return null;}
        Map<Integer,Property> result = new HashMap<>();
        this.properties.entrySet()
                .stream()
                .filter(entry->entry.getValue().getHouse().inWard(input))
                .forEach(entry->result.put(entry.getKey(), entry.getValue()));
        return new PropertyAssessments(result);
    }

    /**
     * Gets all entries in hashmap that have matching Assessment Class 1 with input
     * @param input
     * String of Assessment Class 1 to be filtered
     * @return
     * Returns new PropertyAssessments object containing filtered hashmap
     */
    public PropertyAssessments getAssessmentClass(String input){
        if(input==null||input.isBlank()){return null;}
        Map<Integer,Property> result = new HashMap<>();
        this.properties.entrySet()
                .stream()
                .filter(entry->entry.getValue().getAssessmentClass().isAssessment(input))//add to stream if entry is in the ward
                .forEach(entry->result.put(entry.getKey(), entry.getValue()));
        return new PropertyAssessments(result);
    }

    /**
     * Takes all the assessed values for the entries in a map and turns
     * into arraylist of BigDecimal
     * @param data
     * Map of properties data
     * @return
     * BigDecimal ArrayList containing all assessed values
     */
    private ArrayList<BigDecimal> getBigDecimalList(Map<Integer,Property> data){
        ArrayList<BigDecimal> result = new ArrayList<BigDecimal>();
        for(Map.Entry<Integer, Property> entry:data.entrySet()){
            result.add(entry.getValue().getHouse().getAssessedValue());
        }
        return result;
    }

    /**
     * Calculates the mean of assessed values for entries in hashmap
     * @return
     * BigDecimal mean of assessed values for properties
     */
    public BigDecimal mean(){
        return getMean(getBigDecimalList(this.properties));
    }

    /**
     * Finds the min of assessed values for entries in hashmap
     * @return
     * BigDecimal min of assessed values for properties
     */
    public BigDecimal min(){
        return getMin(getBigDecimalList(this.properties));
    }

    /**
     * Finds the max of assessed values for entries in hashmap
     * @return
     * BigDecimal max of assessed values for properties
     */
    public BigDecimal max(){
        return getMax(getBigDecimalList(this.properties));
    }

    /**
     * Calculates range between min and max of assessed values for properties in hashmap
     * @return
     * BigDecimal range between min and max assessed values
     */
    public BigDecimal range(){
        return getRange(getBigDecimalList(this.properties));
    }

    /**
     * Calculates the median of assessed values for entries in hashmap
     * @return
     * BigDecimal median of assessed values for properties
     */
    public BigDecimal median(){
        return getMedian(getBigDecimalList(this.properties));
    }

    /**
     * Gets the size of properties
     * @return
     * Integer size of properties hashmap
     */
    public Integer sizeN(){
        return this.properties.size();
    }

    /**
     * Prints all the calculated stats for the instance
     */
    public void printAllStats(){
        if(this.sizeN()==0){
            System.out.println("No Entries");
        }
        else{
            System.out.println("n = "+this.sizeN());
            System.out.println("min = "+Money.bigDecimalToMoney(this.min()));
            System.out.println("max = "+Money.bigDecimalToMoney(this.max()));
            System.out.println("range = "+Money.bigDecimalToMoney(this.range()));
            System.out.println("mean = "+Money.bigDecimalToMoney(this.mean()));
            System.out.println("median = "+Money.bigDecimalToMoney(this.median()));
        }
    }

    /**
     * Gets a single account and prints information
     * @param input
     * String account number that can be valid integer
     */
    public void getAccountNum(String input){
        Integer intInput;
        //checks if input is valid integer
        try{
            intInput = Integer.parseInt(input);
        }
        catch (Exception ex){
            System.out.println("Account Number is not valid");
            return;
        }
        //gets property with account number
        Property account = properties.get(intInput);
        if(account == null){
            System.out.println("No entry for account number");
        }
        //Prints information related to account
        else{
            System.out.println("Account Number = "+properties.get(intInput).getAccountNum());
            System.out.println("Address = "+properties.get(intInput).getAddress());
            System.out.println("Assessed Value = "+
                    Money.bigDecimalToMoney(properties
                    .get(intInput)
                    .getHouse()
                    .getAssessedValue()));
            System.out.println("Assessment Class = "+"["+properties.get(intInput).getAssessmentClass()+"]");
            System.out.println("Neighbourhood = "+
                            properties.get(intInput).getHouse().getNeighbourhood() +
                        "("+properties.get(intInput).getHouse().getWard()+")");
            System.out.println("Location = "+properties.get(intInput).getGeoLocation().getPoint());
        }
    }

    /**
     * @return
     * True if properties are not null and size is not 0
     * False otherwise
     */
    public boolean isNotEmpty(){
        return ((properties!=null)&&(!properties.isEmpty()));
    }

}