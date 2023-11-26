package com.Github.cmpt305milestone2.DAO;

import com.Github.cmpt305milestone2.Data.IOReader;
import com.Github.cmpt305milestone2.Data.Property;
import com.Github.cmpt305milestone2.Data.PropertyAssessments;

import java.util.*;

public class CsvPropertyAssessmentDAO implements PropertyAssessmentsDAO {
    PropertyAssessments propertyAssessments;

    public CsvPropertyAssessmentDAO(){
        propertyAssessments = new PropertyAssessments(IOReader.reader("files/Property_Assessment_Data_2023.csv"));
    }


    public CsvPropertyAssessmentDAO(String file){
        propertyAssessments = new PropertyAssessments(IOReader.reader(file));
    }

    /**
     * Gets all Properties from the CSV
     * @return Returns a list of properties
     */
    @Override
    public List<Property> getAll() {
        return propertyAssessments.getAll();
    }

    /**
     * Filters properties depending on List of strings
     * @param input List of strings containing filters
     * @return Returns a list of properties
     */
    @Override
    public List<Property> getSearchResults(List<String> input){
        PropertyAssessments filteredProp = propertyAssessments.clone();
        //Account Number
        if(!input.get(0).isBlank()){
            filteredProp = filteredProp.getFiltered(e->e.getValue().isAccountNumber(input.get(0)));
        }
        //Address
        if(!input.get(1).isBlank()){
            filteredProp = filteredProp.getFiltered(e->e.getValue().isAddress(input.get(1)));
        }
        //Neighbourhood
        if(!input.get(2).isBlank()){
            filteredProp = filteredProp.getFiltered(e->e.getValue().inNeighbourhood(input.get(2)));
        }
        //Assessment Class
        if(!input.get(3).isBlank()){
            filteredProp = filteredProp.getFiltered(e->e.getValue().isAssessment(input.get(3)));
        }
        //Min Assessment Value
        if(!input.get(4).isBlank()){
            filteredProp = filteredProp.getFiltered(e->e.getValue().assessmentMoreThan(input.get(4)));
        }
        //Max Assessment Value
        if(!input.get(5).isBlank()){
            filteredProp = filteredProp.getFiltered(e->e.getValue().assessmentLessThan(input.get(5)));
        }
        return filteredProp.getAll();
    }

    public List<String> getNeighbourhoods() {
        return propertyAssessments.getNeighbourhoods();
    }
}
