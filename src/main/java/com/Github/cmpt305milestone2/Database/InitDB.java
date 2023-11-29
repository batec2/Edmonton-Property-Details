package com.Github.cmpt305milestone2.Database;

public class InitDB {
    public static void main(String[] args) {
        try {
            Database db = new Database();
            db.dropTables();
            System.out.println("Getting Property Data!");
            db.createPropertyTable();
            System.out.println("Finished Populating Properties!");
            System.out.println("Getting Fruit Tree Data!");
            db.createTreesTable();
            System.out.println("Finished Populating Fruit Trees!");
            System.out.println("Getting Crime Data!");
            db.createCrimeTable();
            db.closeConnection();
        }
        catch (Exception e){
            System.out.println("Error: "+e);
        }
    }
}
