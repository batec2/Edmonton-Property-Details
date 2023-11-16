package com.Github.cmpt305milestone2.DAO;

import com.Github.cmpt305milestone2.Data.Property;

import java.util.List;

public interface PropertyAssessmentsDAO {
    Property getByAccountnumber(int accountNumber);
    List<Property> getByNeightbourhood(String neighbourhood);
    List<Property> getByAssessmentClass(String assessmentClass);
    List<Property> getSearchResults(List<String> input);
    List<Property> getAll();
}
