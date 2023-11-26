package com.Github.cmpt305milestone2.DAO;


import com.Github.cmpt305milestone2.Data.Crime;
import com.Github.cmpt305milestone2.Data.IOReader;

import java.util.List;

public class CrimeDataDAO {
    public List<Crime> getAll() {
        return IOReader.crimeReader("files/Occurrences_CSDP.csv");
    }
}
