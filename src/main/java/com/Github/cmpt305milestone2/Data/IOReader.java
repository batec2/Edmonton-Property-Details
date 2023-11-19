/**
 * Class with methods related to user input and reading information from files
 * @author Crush Bate
 */
package com.Github.cmpt305milestone2.Data;

import java.io.File;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Used to read in data and turn data into data structures
 */
public class IOReader {

    /**
     * Takes filename and converts lines in file into entries
     * in a Hashmap
     * @param fileName Valid filename
     * @return Hashmap of all entries in the fill, Null if file can not be opened
     * or file is empty
     */
    public static HashMap<Integer, Property> reader(String fileName){
        File fileIn;
        Scanner scanner = null;
        HashMap<Integer,Property> hashMap = new HashMap<Integer,Property>();
        try{
            fileIn = new File(fileName);
            scanner = new Scanner(fileIn);
        }
        catch (Exception ex){
            System.out.println("Error: Can't Open File");
            return null;
        }
        scanner.useDelimiter("\n");

        //Skips header, if there is no line returns null
        if(scanner.hasNext()){
            scanner.next(); //Skips header
        }
        else{
            System.out.println("Error: File Empty");
            return null;
        }

        //Reads in file into properties
        while (scanner.hasNext()){
            Property property = new Property(scanner.next());
            hashMap.put(property.getAccountNum(), property);
        }
        return hashMap.isEmpty()?null:hashMap;
    }

    /**
     * Takes filename and converts lines in file into entries
     * in a Hashmap
     * @param response
     * HttpResponse from a http request
     * @return
     * Hashmap of all entries in the fill, Null if response is empty
     */
    public static List<Property> reader(HttpResponse<String> response){
        if(response==null){return null;};
        Scanner scanner = new Scanner(response.body());
        ArrayList<Property> propertyList = new ArrayList<>();
        scanner.useDelimiter("\n");
        //Skips header, if there is no line returns null
        if(scanner.hasNext()){
            scanner.next(); //Skips header
        }
        else{
            System.out.println("Error Query Response Empty");
            return null;
        }

        //Reads in Query into properties
        while (scanner.hasNext()){
            Property property = new Property(scanner.next().replace("\"",""));
            propertyList.add(property);
        }
        return propertyList;
    }
}