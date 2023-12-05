package com.Github.cmpt305milestone2.DAO;

import com.Github.cmpt305milestone2.Data.FruitTree;
import com.Github.cmpt305milestone2.Data.IOReader;

import java.util.List;

public class FruitTreesDAO {
    /**
     * Gets all Properties from the CSV
     * @return Returns a list of properties
     */
    public List<FruitTree> getAll() {
        return IOReader.treeReader("files/Trees_20231124.csv");
    }

}
