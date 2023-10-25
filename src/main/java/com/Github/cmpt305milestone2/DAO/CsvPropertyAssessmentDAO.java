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
    @Override
    public Property getByAccountnumber(int accountNumber) {
        //propertyAssessments.getAccountNum(accountNumber);
        return null;
    }

    @Override
    public List<Property> getByNeightbourhood(String neighbourhood) {
    //    HashMap<Integer,Property> map=propertyAssessments.getFiltered(entry-> entry.getValue().inWard(neighbourhood));
      //  return ;
        return null;
    }

    @Override
    public List<Property> getByAssessmentClass(String assessmentClass) {
        return null;
    }

    @Override
    public List<Property> getAll() {
        return null;
    }
}
