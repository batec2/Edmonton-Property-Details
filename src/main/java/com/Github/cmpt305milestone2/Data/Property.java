/**
 * Implements Property class that holds single entry from Property Assessments
 * @author Crush Bate
 */
package com.Github.cmpt305milestone2.Data;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.Arrays;
import java.util.Objects;

/**
 *
 */
public class Property implements Comparable<Property>{
    private Integer accountNum;
    private Address address;
    private House house;
    private GeoLocation geoLocation;
    private AssessmentClass assessmentClass;

    /**
     * Constructor for address, requires valid string from Property Assessment data file.
     * If data does not have relevant value blank string is used.
     * @param data
     * CSV formatted string from property assessment data
     */
    public Property(String data){
        List<String> splitData = Arrays.asList(data.split(","));
        this.accountNum = Integer.valueOf(splitData.get(0));
        this.address = new Address(data);
        this.house = new House(data);
        this.geoLocation = new GeoLocation(data);
        this.assessmentClass = new AssessmentClass(data);
    }


    /**
     * Gets class member variables in a readable string
     * @return
     * String of all the member variables
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.accountNum)
                .append(" ")
                .append(this.address.toString())
                .append(" ")
                .append(this.house.toString())
                .append(" ")
                .append(this.geoLocation.toString())
                .append(" ")
                .append(this.assessmentClass.toString());
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
    public int compareTo(@NotNull Property o){
        return Integer.compare(getAccountNum(),o.getAccountNum());
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
        Property property = (Property) o;
        return accountNum.equals(property.getAccountNum()) &&
                address.equals(property.getAddress()) &&
                house.equals(property.getHouse()) &&
                geoLocation.equals(property.getGeoLocation()) &&
                assessmentClass.equals(property.getAssessmentClass());
    }

    /**
     * Takes all the member variables and get hash value
     * @return
     * int - hash of all member variables in object
     */
    @Override
    public int hashCode() {
        return Objects.hash(accountNum.hashCode(),
                address.hashCode(),
                house.hashCode(),
                geoLocation.hashCode(),
                assessmentClass.hashCode());
    }

    /**
     * Gets the Account Number information
     * @return
     * Integer of account number
     */
    public Integer getAccountNum(){
        return this.accountNum;
    }

    /**
     * Gets the Address object
     * @return
     * House Address
     */
    public Address getAddress(){
        return this.address;
    }

    /**
     * Gets the House object
     * @return
     * House object
     */
    public House getHouse() {
        return this.house;
    }

    /**
     * Gets the GeoLocation object
     * @return
     * GeoLocation object
     */
    public GeoLocation getGeoLocation() {
        return this.geoLocation;
    }

    /**
     * Gets the AssessmentClass object
     * @return
     * AssessmentClass object
     */
    public AssessmentClass getAssessmentClass() {
        return this.assessmentClass;
    }

    /**
     *
     */
    public boolean inWard(String ward){
        return house.inWard(ward);
    }

    /**
     *
     */
    public boolean inNeighbourhood(String neighbourhood){
        return house.inNeighbourhood(neighbourhood.toUpperCase());
    }

    /**
     *
     */
    public boolean isAssessment(String assessment){
        return assessmentClass.isAssessment(assessment.toUpperCase());
    }

    /**
     * Gets the suite information
     * @return
     * String of suite
     */
    public String getSuite(){
        return address.getSuite();

    }

    /**
     * Gets the house number information
     * @return
     * String of house number
     */
    public String getHouseNum(){
        return address.getHouseNum();
    }

    /**
     * Gets the Street name information
     * @return
     * String of street name
     */
    public String getStreetName(){
        return address.getStreetName();
    }
    /**
     * Gets the assessment percent 1 information
     * @return
     * String of assessment percent 1
     */
    public String getAssessmentPercent1(){
        return assessmentClass.getAssessmentPercent1();
    }


    /**
     * Gets the assessment percent 2 information
     * @return
     * String of assessment percent 2
     */
    public String getAssessmentPercent2() {
        return assessmentClass.getAssessmentPercent2();
    }

    /**
     * Gets the assessment percent 3 information
     * @return
     * String of assessment percent 3
     */
    public String getAssessmentPercent3() {
        return assessmentClass.getAssessmentPercent3();
    }


    /**
     * Gets the assessment percent 1 information
     * @return
     * String of assessment percent 1
     */
    public String getAssessment1() {
        return assessmentClass.getAssessment1();
    }


    /**
     * Gets the assessment percent 2 information
     * @return
     * String of assessment percent 2
     */
    public String getAssessment2() {
        return assessmentClass.getAssessment2();
    }


    /**
     * Gets the assessment percent 3 information
     * @return
     * String of assessment percent 3
     */
    public String getAssessment3() {
        return assessmentClass.getAssessment3();
    }

    /**
     * Gets the latitude information
     * @return
     * String of latitude
     */
    public String getLatitude(){
        return geoLocation.getLatitude();
    }

    /**
     * Gets the longitude information
     * @return
     * String of longitude
     */
    public String getLongitude() {
        return geoLocation.getLongitude();
    }

    /**
     * Gets the point information
     * @return
     * String of point
     */
    public String getPoint(){
        return geoLocation.getPoint();
    }

    /**
     * Gets the garage information
     * @return
     * String of garage
     */
    public String getGarage(){
        return house.getGarage();
    }

    /**
     * Gets the neighbourhood id information
     * @return
     * String of neighbourhood id
     */
    public String getNeighbourhoodID(){
        return house.getNeighbourhoodID();
    }

    /**
     * Gets the neighbourhood information
     * @return
     * String of neighbourhood
     */
    public String getNeighbourhood(){
        return house.getNeighbourhood();
    }

    /**
     * Gets the ward information
     * @return
     * String of ward
     */
    public String getWard(){
        return house.getWard();
    }

    /**
     * Gets the Assessed value information
     * @return
     * BigDecimal of Assessed value
     */
    public BigDecimal getAssessedValue(){
        return house.getAssessedValue();
    }

}