package com.Github.cmpt305milestone2.DAO;

import com.Github.cmpt305milestone2.Data.FruitTree;
import com.Github.cmpt305milestone2.Data.IOReader;
import com.Github.cmpt305milestone2.Data.WeedStore;

import java.util.List;

public class WeedStoreDAO {
    /**
     * Gets all Properties from the CSV
     * @return Returns a list of properties
     */
    public List<WeedStore> getAll() {
        return IOReader.weedReader("files/Cannabis_Retail_Sales_Licences.csv");
    }
}
