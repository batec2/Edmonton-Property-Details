package com.Github.cmpt305milestone2.Database;

import java.sql.ResultSet;

public class InitDB {
    public static void main(String[] args) {
        try {

            Database db = new Database();
            /*
            db.dropTables();
            System.out.println("Getting Property Data!");
            db.createPropertyTable();
            System.out.println("Finished Populating Properties!");
            System.out.println("Getting Fruit Tree Data!");
            db.createTreesTable();
            System.out.println("Finished Populating Fruit Trees!");
            System.out.println("Getting Crime Data!");
            db.createCrimeTable();
            System.out.println("Finished Populating Crime Data!");
            System.out.println("Getting Weed Store Data!");
            db.createWeedTable();
            System.out.println("Finished Populating Weed Store Data!");
            db.closeConnection();
             */
            ResultSet resultSet = db.customQuery("SELECT SQRT((POWER((53.5853100328052-53.58587602843403),2)*(4.357313*POWER(10,9)))+(POWER(((-113.44554719782735)-(-113.4950565870333)),2)*(4.357313*POWER(10,9)))) as GODLIKE");
            System.out.println(resultSet.getString("GODLIKE"));
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Error: "+e);
        }
    }
}
