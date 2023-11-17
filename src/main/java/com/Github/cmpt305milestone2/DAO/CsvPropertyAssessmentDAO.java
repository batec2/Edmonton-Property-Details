package com.Github.cmpt305milestone2.DAO;

import com.Github.cmpt305milestone2.Data.IOReader;
import com.Github.cmpt305milestone2.Data.Property;
import com.Github.cmpt305milestone2.Data.PropertyAssessments;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class CsvPropertyAssessmentDAO implements PropertyAssessmentsDAO {
    PropertyAssessments propertyAssessments;
    public CsvPropertyAssessmentDAO(String file){
        propertyAssessments = new PropertyAssessments(IOReader.reader(file));
    }

    /**
     *
     * @param accountNumber
     * @return
     */
    @Override
    public Property getByAccountnumber(int accountNumber) {
        //propertyAssessments.getAccountNum(accountNumber);
        return null;
    }

    /**
     *
     * @param neighbourhood
     * @return
     */
    @Override
    public List<Property> getByNeightbourhood(String neighbourhood) {
    //    HashMap<Integer,Property> map=propertyAssessments.getFiltered(entry-> entry.getValue().inWard(neighbourhood));
      //  return ;
        return null;
    }

    /**
     *
     * @param assessmentClass
     * @return
     */
    @Override
    public List<Property> getByAssessmentClass(String assessmentClass) {
        return null;
    }

    /**
     *
     * @return
     */
    @Override
    public List<Property> getAll() {
        return propertyAssessments.getAll();
    }

    /**
     *
     * @param input
     * @return
     */
    @Override
    public List<Property> getSearchResults(List<String> input){
        PropertyAssessments filteredProp = propertyAssessments.clone();
        if(!input.get(0).isBlank()){
            filteredProp = filteredProp.getFiltered(e->e.getValue().isAccountNumber(input.get(0)));
        }
        if(!input.get(1).isBlank()){
            filteredProp = filteredProp.getFiltered(e->e.getValue().isAddress(input.get(1)));
        }
        if(!input.get(2).isBlank()){
            filteredProp = filteredProp.getFiltered(e->e.getValue().inNeighbourhood(input.get(2)));
        }
        if(!input.get(3).isBlank()){
            filteredProp = filteredProp.getFiltered(e->e.getValue().isAssessment(input.get(3)));
        }
        if(!input.get(4).isBlank()){
            filteredProp = filteredProp.getFiltered(e->e.getValue().assessmentMoreThan(input.get(4)));
        }
        if(!input.get(5).isBlank()){
            filteredProp = filteredProp.getFiltered(e->e.getValue().assessmentLessThan(input.get(5)));
        }
        return filteredProp.getAll();
    }
}
