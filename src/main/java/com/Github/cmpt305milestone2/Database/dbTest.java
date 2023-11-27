package com.Github.cmpt305milestone2.Database;

import com.Github.cmpt305milestone2.DAO.FruitTreesDAO;

public class dbTest {
    public static void main(String[] args) {
        Database db = new Database();
        //db.createPropertyTable();
        db.createTreesTable();
    }
}
