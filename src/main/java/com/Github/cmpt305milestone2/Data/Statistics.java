/**
 * Implements class that contains methods to calculate statistics on data
 * @author Crush Bate
 */
package com.Github.cmpt305milestone2.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

/**
 * Various methods that calculate statistics on arraylist of data
 */
public class Statistics{
    /**
     * Gets the min value in an arraylist
     * @param data
     * Arraylist of BigDecimals with data
     * @return
     * Min BigDecimal in ArrayList
     */
    public static BigDecimal getMin(ArrayList<BigDecimal> data){
        if(data.isEmpty()){return null;}
        return data.stream()//turns arraylist into stream
                .min(BigDecimal::compareTo)//gets min value in stream
                .get();//gets bigdecimal
    }

    /**
     * Gets the max value in an arraylist
     * @param data
     * Arraylist of BigDecimals with data
     * @return
     * Max BigDecimal in ArrayList
     */
    public static BigDecimal getMax(ArrayList<BigDecimal> data){
        if(data.isEmpty()){return null;}
        return data.stream()//turns arraylist into stream
                .max(BigDecimal::compareTo)//finds max value in stream
                .get();//returns bigdecimal
    }

    /**
     * Gets the range between max and min values in array List
     * @param data
     * Arraylist of BigDecimals with data
     * @return
     * Difference between min and max values in arraylist
     */
    public static BigDecimal getRange(ArrayList<BigDecimal> data){
        if(data.isEmpty()){return null;}
        return getMax(data).subtract(getMin(data));//calls max and min and gets difference
    }

    /**
     * Gets the mean of the values in an arraylist
     * @param data
     * Arraylist of BigDecimals with data
     * @return
     * mean BigDecimal of values in ArrayList
     */
    /*switch to stream/reduce*/
    public static BigDecimal getMean(ArrayList<BigDecimal> data){
        if(data.isEmpty()){return null;}
        BigDecimal result = new BigDecimal(0);
        //adds all values in arrayList
        for(BigDecimal entry:data){
            result = result.add(entry);
        }
        //Divides result my size of data
        result = result.divide(BigDecimal.valueOf(data.size()),0, RoundingMode.HALF_UP);
        return result;
    }

    /**
     * Sorts values in arraylist and returns median value
     * @param data
     * Arraylist of BigDecimals with data
     * @return
     * median BigDecimal of values in ArrayList
     */
    public static BigDecimal getMedian(ArrayList<BigDecimal> data){
        if(data.isEmpty()){return null;}
        long length = data.stream().count();
        return data.stream()//turns arraylist into stream
                .sorted()//sorts values
                .skip(length/2)//gets the second half of the arraylist
                .findFirst()//finds first value which should be median
                .get();//returns bigdecimal
    }
}
