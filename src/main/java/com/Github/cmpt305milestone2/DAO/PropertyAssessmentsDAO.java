package com.Github.cmpt305milestone2.DAO;

import com.Github.cmpt305milestone2.Data.Property;

import java.util.List;

public interface PropertyAssessmentsDAO {
    List<Property> getSearchResults(List<String> input);
    List<Property> getAll();
}
