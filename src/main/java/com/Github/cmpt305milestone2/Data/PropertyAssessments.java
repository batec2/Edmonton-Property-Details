/**
 * Implements class that hold hashmap of property assessments data
 * @author Crush Bate
 */
package com.Github.cmpt305milestone2.Data;

import java.math.BigDecimal;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.function.Predicate;

import static com.Github.cmpt305milestone2.Data.IOReader.reader;
import static com.Github.cmpt305milestone2.Data.Statistics.*;


/**
 * Object for all Property Assessments with methods to calculate statistics on data
 */
public class PropertyAssessments{
    private Map<Integer,Property> properties;

    /**
     * Constructor for if there is existing hashmap
     * @param newMap
     * HashMap containing Properties
     */
    public PropertyAssessments(Map<Integer,Property> newMap){this.properties = newMap;}

    public List<Property> getAll(){
        List<Property> allValues = new ArrayList<Property>();
        properties.forEach((key, value) -> allValues.add(value.clone()));
        return allValues.stream().sorted().toList();
    }

    /**
     * Takes a Predicate and filters hashmap based on predicate
     * @param predicate Predicate that will evaluate to true or false
     * @return Returns a new PropertyAssessments object
     */
    public PropertyAssessments getFiltered(Predicate<Map.Entry<Integer,Property>> predicate){
        Map<Integer,Property> result = new HashMap<>();
        this.properties.entrySet()
                .stream()
                .filter(predicate)
                .forEach(entry->result.put(entry.getKey(), entry.getValue().clone()));//creates new hashmap
        return new PropertyAssessments(result);
    }

    /**
     * Makes a clone of PropertyAssessments Object
     * @return PropertyAssessments object
     */
    public PropertyAssessments clone(){
        Map<Integer,Property> result = new HashMap<>();
        this.properties.forEach((key, value) -> result.put(key, value.clone()));//creates new hashmap
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
}