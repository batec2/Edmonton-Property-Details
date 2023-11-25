/**
 * Implements the GeoLocation class to hold the location information
 * @author Crush Bate
 */
package com.Github.cmpt305milestone2.Data;

import org.jetbrains.annotations.NotNull;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Class for all the location information, latitude,longitude,point
 */
public class GeoLocation implements Comparable<GeoLocation>{
    private String latitude;
    private String longitude;
    private String point;

    /**
     * Constructor for GeoLocation, requires valid string from Property Assessment data file.
     * If data does not have relevant value blank string is used.
     * @param data
     * CSV formatted string from property assessment data
     */
    public GeoLocation(String data){
        List<String> splitData = Arrays.asList(data.split(","));
        this.latitude = splitData.size()>9?splitData.get(9):"";
        this.longitude = splitData.size()>10?splitData.get(10):"";
        this.point = splitData.size()>11?splitData.get(11):"";
    }

    /**
     * Constructor for GeoLocation, Used to create Clone of existing GeoLocation object
     * @param latitude
     * @param longitude
     * @param point
     */
    public GeoLocation(String latitude,String longitude,String point){
        this.latitude = latitude;
        this.longitude = longitude;
        this.point = point;
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
                .append(this.latitude)
                .append(" ")
                .append(this.longitude)
                .append(" ")
                .append(this.point);
        return stringBuilder.toString();
    }

    /**
     * Gets class member variables in a readable string
     * @return
     * String of all the member variables
     */
    public String toStringNull() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(latitude.isBlank()?"Null":latitude)
                .append(",")
                .append(longitude.isBlank()?"Null":longitude)
                .append(",")
                .append(point.isBlank()?"":"'")
                .append(point.isBlank()?"Null":point)
                .append(point.isBlank()?",":"',");
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
    public int compareTo(@NotNull GeoLocation o) {
        return Comparator
                .comparing(GeoLocation::getLatitude)
                .thenComparing(GeoLocation::getLongitude)
                .thenComparing(GeoLocation::getPoint)
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
        GeoLocation geoLocation = (GeoLocation) o;
        return latitude.equals(geoLocation.getLatitude()) &&
                longitude.equals(geoLocation.getLongitude()) &&
                point.equals(geoLocation.getPoint());
    }

    /**
     * Takes all the member variables and get hash value
     * @return
     * int - hash of all member variables in object
     */
    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude, point);
    }

    /**
     * Gets the latitude information
     * @return
     * String of latitude
     */
    public String getLatitude(){
        return this.latitude;
    }

    /**
     * Gets the longitude information
     * @return
     * String of longitude
     */
    public String getLongitude() {
        return this.longitude;
    }

    /**
     * Gets the point information
     * @return
     * String of point
     */
    public String getPoint(){
        return this.point;
    }
}
