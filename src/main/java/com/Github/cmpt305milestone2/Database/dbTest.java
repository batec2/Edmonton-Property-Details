package com.Github.cmpt305milestone2.Database;

import com.Github.cmpt305milestone2.DAO.FruitTreesDAO;

public class dbTest {
    public static void main(String[] args) {
        try {
            Database db = new Database();
            db.dropTables();
            db.createPropertyTable();
            db.createTreesTable();
            db.createCrimeTable();
            db.closeConnection();
        }
        catch (Exception e){
            System.out.println("Error: "+e);
        }
    }
}
