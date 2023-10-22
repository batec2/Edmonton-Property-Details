/**
 * Implements the House class to hold the house information
 * @author Crush Bate
 */
package com.Github.cmpt305milestone2.Data;

import org.jetbrains.annotations.NotNull;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Class contain house information, garage,neighbourhood id, neighbourhood, ward,assessed value
 */
public class House implements Comparable<House>{
    private String garage;
    private String neighbourhoodID;
    private String neighbourhood;
    private String ward;
    private BigDecimal assessedValue;

    /**
     * Constructor for address, requires valid string from Property Assessment data file.
     * If data does not have relevant value blank string is used.
     * @param data
     * CSV formatted string from property assessment data
     */
    public House(String data){
        List<String> splitData = Arrays.asList(data.split(","));
        this.garage = splitData.get(4);
        this.neighbourhoodID = splitData.get(5);
        this.neighbourhood = splitData.get(6);
        this.ward = splitData.get(7);
        this.assessedValue = new BigDecimal(splitData.get(8));
    }

    /**
     * Gets class member variables in a readable string
     * @return
     * String of all the member variables
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(garage)
                .append(" ")
                .append(neighbourhoodID)
                .append(" ")
                .append(neighbourhood)
                .append(" ")
                .append(ward)
                .append(" ")
                .append(assessedValue);
        return stringBuilder.toString();
    }

    /**
     * Compares member variables of classes
     * @param o the object to be compared.
     * @return
     * 1 < if object is less than object being compared to
     * 0 = if member variables are equal
     * -1 = if less than object being compared to
     */
    @Override
    public int compareTo(@NotNull House o) {
        return Comparator
                .comparing(House::getGarage)
                .thenComparing(House::getNeighbourhoodID)
                .thenComparing(House::getNeighbourhood)
                .thenComparing(House::getWard)
                .thenComparing(House::getAssessedValue)
                .compare(this,o);
    }

    /**
     * Checks if member variables for are equal
     * @param o
     * Address object
     * @return
     * true - objects are the same
     *      - member variables are equal
     * false - object being compared is null
     *       - object being compared is not same class
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        House house = (House) o;
        return garage.equals(house.getGarage()) &&
                neighbourhoodID.equals(house.getNeighbourhoodID()) &&
                neighbourhood.equals(house.getNeighbourhood()) &&
                ward.equals(house.getWard()) &&
                assessedValue.equals(house.assessedValue);
    }

    /**
     * Takes all the member variables and get hash value
     * @return
     * int - hash of all member variables in object
     */
    @Override
    public int hashCode() {
        return Objects.hash(garage, neighbourhoodID, neighbourhood, ward, assessedValue);
    }

    /**
     * Gets the garage information
     * @return
     * String of garage
     */
    public String getGarage(){
        return this.garage;
    }

    /**
     * Gets the neighbourhood id information
     * @return
     * String of neighbourhood id
     */
    public String getNeighbourhoodID(){
        return this.neighbourhoodID;
    }

    /**
     * Gets the neighbourhood information
     * @return
     * String of neighbourhood
     */
    public String getNeighbourhood(){
        return this.neighbourhood;
    }

    /**
     * Gets the ward information
     * @return
     * String of ward
     */
    public String getWard(){
        return this.ward;
    }

    /**
     * Gets the Assessed value information
     * @return
     * BigDecimal of Assessed value
     */
    public BigDecimal getAssessedValue(){
        return this.assessedValue;
    }

    /**
     * Checks if input matches neighbourhood information
     * @param input
     * string of neighbourhood(input is not case-sensitive)
     * @return
     * True if house is in neighbourhood, else false
     */
    public boolean inNeighbourhood(String input){
        return this.neighbourhood.equalsIgnoreCase(input);
    }

    /**
     * Checks if input matches ward information
     * @param input
     * Ward in dataset(input is not case-sensitive)
     * @return
     * True if house is in ward, else false
     */
    public boolean inWard(String input) {return this.ward.equalsIgnoreCase(input);}
}