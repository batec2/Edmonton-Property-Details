/**
 * Class with methods related to user input and reading information from files
 * @author Crush Bate
 */
package com.Github.cmpt305milestone2.Data;

import java.io.File;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 */
public class IOReader {

    /**
     * Takes filename and converts lines in file into entries
     * in a Hashmap
     * @param fileName
     * Valid filename
     * @return
     * Hashmap of all entries in the fill, Null if file can not be opened
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
    public static HashMap<Integer, Property> reader(HttpResponse<String> response){
        Scanner scanner = new Scanner(response.body());
        HashMap<Integer,Property> hashMap = new HashMap<Integer,Property>();
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
            hashMap.put(property.getAccountNum(), property);
        }
        return hashMap.isEmpty()?null:hashMap;
    }

    public static Property accountReader(HttpResponse<String> response){
        Scanner scanner = new Scanner(response.body());
        Property property=null;
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
            property = new Property(scanner.next().replace("\"",""));
        }
        return property;
    }

    /**
     * Prints out a prompt and takes user in user input returns
     * the input in the form of a string
     * @param prompt
     * String prompt to be displayed for the user
     * @return
     * String user input
     */
    public static String userInput(String prompt,Scanner scanner){
        System.out.print(prompt);
        String input = "";
        try{
            input = scanner.nextLine();
        }
        catch (Exception ex){
            System.out.println("Caught: "+ex);
            return null;
        }
        return input;
    }
}