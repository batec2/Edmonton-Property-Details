package com.Github.cmpt305milestone2.DAO.DeprecatedDAO;

import com.Github.cmpt305milestone2.Data.Property;

import java.util.List;

/**
 * Interface for Data Access Objects
 */
public interface PropertyAssessmentsDAO {
    //Filtered List of properties
    List<Property> getSearchResults(List<String> input);
    //Unfiltered List of properties
    List<Property> getAll();
}
