package com.Github.cmpt305milestone2.DAO;


import com.Github.cmpt305milestone2.Data.Crime;
import com.Github.cmpt305milestone2.Data.IOReader;

import java.util.List;

public class CrimeDataDAO {
    /**
     * Gets all Properties from the CSV
     * @return Returns a list of properties
     */
    public List<Crime> getAll() {
        return IOReader.crimeReader("files/Occurrences_CSDP.csv");
    }
}
