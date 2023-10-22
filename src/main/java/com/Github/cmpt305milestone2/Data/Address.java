/**
 * Implements the address class to hold the Address information
 * @author Crush Bate
 */
package com.Github.cmpt305milestone2.Data;

import org.jetbrains.annotations.NotNull;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Holds the suit, house number, and street name of a property
 */
public class Address implements Comparable<Address>{
    private String suite;
    private String houseNum;
    private String streetName;

    /**
     * Constructor for address, requires valid string from Property Assessment data file.
     * If data does not have relevant value blank string is used.
     * @param data
     * CSV formatted string from property assessment data
     */
    public Address(String data){
        List<String> splitData = Arrays.asList(data.split(","));
        this.suite = splitData.size()>1?splitData.get(1):"";
        this.houseNum = splitData.size()>2?splitData.get(2):"";
        this.streetName = splitData.size()>3?splitData.get(3):"";
    }

    /**
     * Gets class member variables in a readable string
     * @return
     * String of all the member variables
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(suite);
        //empty space to make string readable
        if(!houseNum.isBlank()&&!suite.isBlank()){stringBuilder.append(" ");}
        stringBuilder.append(houseNum);
        if(!streetName.isBlank()){stringBuilder.append(" ");}
        stringBuilder.append(streetName);
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
    public int compareTo(@NotNull Address o) {
        return Comparator
                .comparing(Address::getSuite)
                .thenComparing(Address::getHouseNum)
                .thenComparing(Address::getStreetName)
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
        if (this == o) return true;//checks if reference is the same
        if (o == null || getClass() != o.getClass()) return false;//checks if type of class is the same
        Address address = (Address) o;//casts o into a address object
        return suite.equals(address.getSuite()) &&
                houseNum.equals(address.getHouseNum()) &&
                streetName.equals(address.getStreetName()); //checks if strings are the same
    }

    /**
     * Takes all the member variables and get hash value
     * @return
     * int - hash of all member variables in object
     */
    @Override
    public int hashCode() {
        return Objects.hash(suite, houseNum, streetName);
    }

    /**
     * Gets the suite information
     * @return
     * String of suite
     */
    public String getSuite(){
        return suite;
    }

    /**
     * Gets the house number information
     * @return
     * String of house number
     */
    public String getHouseNum(){
        return houseNum;
    }

    /**
     * Gets the Street name information
     * @return
     * String of street name
     */
    public String getStreetName(){
        return streetName;
    }
}

