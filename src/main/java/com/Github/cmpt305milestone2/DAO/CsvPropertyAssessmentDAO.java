package com.Github.cmpt305milestone2.DAO;

import com.Github.cmpt305milestone2.Data.IOReader;
import com.Github.cmpt305milestone2.Data.Property;
import com.Github.cmpt305milestone2.Data.PropertyAssessments;

import java.util.List;
import java.util.Scanner;

public class CsvPropertyAssessmentDAO implements PropertyAssessmentsDAO {
    public CsvPropertyAssessmentDAO(String file){
        Scanner scanner = new Scanner(System.in);
        PropertyAssessments propertyAssessments =
                new PropertyAssessments(IOReader.reader(file));
    }
    @Override
    public Property getByAccountnumber(int accountNumber) {
        return null;
    }

    @Override
    public List<Property> getByNeightbourhood(String neighbourhood) {
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
