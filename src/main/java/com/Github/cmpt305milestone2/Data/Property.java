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
     * Constructor for Property, requires valid string from Property Assessment data file.
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
     * Constructor for Property, takes in existing objects as arguments, used to clone an existing Property
     * @param accountNum Int
     * @param address Address object
     * @param house House object
     * @param geoLocation GeoLocation object
     * @param assessmentClass AssessmentClass object
     */
    public Property(int accountNum,Address address,House house,GeoLocation geoLocation,AssessmentClass assessmentClass){
        this.accountNum = accountNum;
        this.address = address;
        this.house = house;
        this.geoLocation = geoLocation;
        this.assessmentClass = assessmentClass;
    }

    /**
     * Gets class member variables in a readable string
     * @return
     * String of all the member variables
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ACCOUNT #: ").append(this.accountNum)
                .append("\nADDRESS: ").append(this.address.toString())
                .append("\nNEIGHBOURHOOD: ").append(this.getNeighbourhood())
                .append("\nWARD:").append(this.getWard())
                .append("\nASSESSED VALUE: ").append(Money.bigDecimalToMoney(this.getAssessedValue()))
                .append("\nLOCATION: ").append(this.geoLocation)
                .append("\nASSESSMENT CLASS 1: ").append(this.getAssessment1())
                .append(" ").append(this.getAssessmentPercent1());
        if(!this.getAssessment2().isEmpty()) {
            stringBuilder.append("\nASSESSMENT CLASS 2: ").append(this.getAssessment2())
                         .append(" ").append(this.getAssessmentPercent2());
        }
        if(!this.getAssessment3().isEmpty()) {
            stringBuilder.append("\nASSESSMENT CLASS 3: ").append(this.getAssessment3())
                         .append(" ").append(this.getAssessmentPercent3());
        }
        return stringBuilder.toString();
    }

    /**
     * Gets class member variables in a readable string
     * @return
     * String of all the member variables
     */
    //@Override
    public String toStringOLD() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("'"+this.accountNum+"'")
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
     * Gets class member variables in a readable string
     * @return
     * String of all the member variables
     */
    public String toStringNull() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("'"+this.accountNum+"',")
                .append(this.address.toStringNull())
                .append(this.house.toStringNull())
                .append(this.geoLocation.toStringNull())
                .append(this.assessmentClass.toStringNull());
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
    public int getAccountNum(){
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
     * Checks if neighbourhood string contains parameter substring
     * @param neighbourhood substring to check inside neighbourhood
     * @return true if neighbourhood contains substring, false otherwise
     */
    public boolean inNeighbourhood(String neighbourhood){
        return house.inNeighbourhood(neighbourhood.toUpperCase());
    }

    /**
     * Checks if any of the assessment classes match assessment passed in
     * @param assessment assessment to be compared
     * @return true if any of assessment classes match, false otherwise
     */
    public boolean isAssessment(String assessment){
        return assessmentClass.isAssessment(assessment.toUpperCase());
    }

    /**
     * Checks if account number starts with passed in string
     * @param num String to check if account number starts with
     * @return true if accountnumber starts with num, false otherwise
     */
    public boolean isAccountNumber(String num){
        return accountNum.toString().startsWith(num);
    }

    /**
     * Checks if argument is a substring within the address
     * @param inAddress substring to be searched
     * @return true if address contains parameter, false otherwise
     */
    public boolean isAddress(String inAddress){
        return address.getAddress().contains(inAddress.toUpperCase());
    }

    /**
     * Checks if assessed value is less than max
     * @param max maximum assessed value
     * @return true if assessed value less than max, false if assessed value more than max
     */
    public boolean assessmentLessThan(String max){
        try{
            return house.getAssessedValue().compareTo(new BigDecimal(max))<=0;
        }
        catch (NumberFormatException e){
            return false;
        }
    }

    /**
     * Checks if assessed value is higher than min
     * @param min minimum assessed value
     * @return true if assessed value more than min, false if assessed value less than min
     */
    public boolean assessmentMoreThan(String min){
        try{
            return house.getAssessedValue().compareTo(new BigDecimal(min))>=0;
        }
        catch (NumberFormatException e){
            return false;
        }
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
        return new BigDecimal(house.getAssessedValue().toString());
    }

    /**
     * Gets the neighbourhood and ward information
     * @return String of neighbourhood and ward
     */
    public String getNeighbourWard(){
        return this.house.getNeighbourWard();
    }

    /**
     * Creates a clone of property and returns a Property with same member variables
     * @return Property
     */
    public Property clone(){
        return new Property(
                accountNum,
                new Address(address.getSuite(),
                        address.getHouseNum(),
                        address.getStreetName()),
                new House(house.getGarage(),
                        house.getNeighbourhoodID(),
                        house.getNeighbourhood(),
                        house.getWard(),
                        house.getAssessedValue()),
                new GeoLocation(geoLocation.getLatitude(),
                        geoLocation.getLongitude(),
                        geoLocation.getPoint()),
                new AssessmentClass(assessmentClass.
                        getAssessmentPercent1(),
                        assessmentClass.getAssessmentPercent2(),
                        assessmentClass.getAssessmentPercent3(),
                        assessmentClass.getAssessment1(),
                        assessmentClass.getAssessment2(),
                        assessmentClass.getAssessment3())
        );
    }

}
