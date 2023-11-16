/**
 * Implements the AssessmentClass Class to hold assessment information
 * @author Crush Bate
 */
package com.Github.cmpt305milestone2.Data;

import org.jetbrains.annotations.NotNull;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Holds assessment percent 1,2,3 and assessment types 1,2,3
 */
public class AssessmentClass implements Comparable<AssessmentClass>{
    private String assessmentPercent1;
    private String assessmentPercent2;
    private String assessmentPercent3;
    private String assessment1;
    private String assessment2;
    private String assessment3;

    private String assessmentClass;

    /**
     * Constructor for AssessmentClass, requires valid string from Property Assessment data file.
     * If data does not have relevant value blank string is used.
     * @param data
     * CSV formatted string from property assessment data
     */
    public AssessmentClass(String data){
        //removes new line and carriage return
        String newData = data.replace("\n","").replace("\r","");
        List<String> splitData = Arrays.asList(newData.split(","));
        //if index is not out of bounds for list size get data at index else empty string
        this.assessmentPercent1 = splitData.size()>12?splitData.get(12):"";
        this.assessmentPercent2 = splitData.size()>13?splitData.get(13):"";
        this.assessmentPercent3 = splitData.size()>14?splitData.get(14):"";
        this.assessment1 = splitData.size()>15?splitData.get(15):"";
        this.assessment2 = splitData.size()>16?splitData.get(16):"";
        this.assessment3 = splitData.size()>17?splitData.get(17):"";
        this.assessmentClass = this.toString();
    }

    public AssessmentClass(String assessmentPercent1,String assessmentPercent2, String assessmentPercent3
            ,String assessment1,String assessment2,String assessment3){
        this.assessmentPercent1 = assessmentPercent1;
        this.assessmentPercent2 = assessmentPercent2;
        this.assessmentPercent3 = assessmentPercent3;
        this.assessment1 = assessment1;
        this.assessment2 = assessment2;
        this.assessment3 = assessment3;
        this.assessmentClass = this.toString();
    }

    /**
     * Gets class member variables in a readable string
     * @return
     * String of all the member variables
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(assessment1);
        if(!assessmentPercent1.isBlank()){stringBuilder.append(" ");}
        stringBuilder.append(assessmentPercent1);
        if(!assessment2.isBlank()){stringBuilder.append(" ");}
        stringBuilder.append(assessment2);
        if(!assessmentPercent2.isBlank()){stringBuilder.append(" ");}
        stringBuilder.append(assessmentPercent2);
        if(!assessment3.isBlank()){stringBuilder.append(" ");}
        stringBuilder.append(assessment3);
        if(!assessmentPercent3.isBlank()){stringBuilder.append(" ");}
        stringBuilder.append(assessmentPercent3);

        return stringBuilder.toString();
    }
    /**
     * Compares member variables of classes
     * @param o the object to be compared.
     * @return
     * 1 < if object is less than object being compared to
     * 0 = if member variables are equal
     * -1 = if less than object being compared to
     */
    @Override
    public int compareTo(@NotNull AssessmentClass o) {
        return Comparator
                .comparing(AssessmentClass:: getAssessment1)
                .thenComparing(AssessmentClass:: getAssessment2)
                .thenComparing(AssessmentClass:: getAssessment3)
                .thenComparing(AssessmentClass::getAssessmentPercent1)
                .thenComparing(AssessmentClass::getAssessmentPercent2)
                .thenComparing(AssessmentClass::getAssessmentPercent3)
                .compare(this,o);
    }

    /**
     * Checks if member variables for are equal
     * @param o
     * Address object
     * @return
     * true - objects are the same
     *      - member variables are equal
     * false - object being compared is null
     *       - object being compared is not same class
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AssessmentClass assessmentClass = (AssessmentClass) o;
        return assessment1.equals(assessmentClass.getAssessment1()) &&
                assessment2.equals(assessmentClass.getAssessment2()) &&
                assessment3.equals(assessmentClass.getAssessment3()) &&
                assessmentPercent1.equals(assessmentClass.getAssessmentPercent1()) &&
                assessmentPercent2.equals(assessmentClass.getAssessmentPercent2()) &&
                assessmentPercent3.equals(assessmentClass.getAssessmentPercent3());
    }

    /**
     * Takes all the member variables and get hash value
     * @return
     * int - hash of all member variables in object
     */
    @Override
    public int hashCode() {
        return Objects.hash(
                assessmentPercent1,
                assessmentPercent2,
                assessmentPercent3,
                assessment1,
                assessment2,
                assessment3);
    }

    /**
     * Gets the assessment percent 1 information
     * @return
     * String of assessment percent 1
     */
    public String getAssessmentPercent1(){
        return this.assessmentPercent1;
    }


    /**
     * Gets the assessment percent 1 information
     * @return
     * String of assessment percent 1
     */
    public String getAssessmentPercent2() {
        return this.assessmentPercent2;
    }

    public String getAssessmentPercent3() {
        return this.assessmentPercent3;
    }


    /**
     * Gets the assessment percent 1 information
     * @return
     * String of assessment percent 1
     */
    public String getAssessment1() {
        return this.assessment1;
    }


    /**
     * Gets the assessment percent 2 information
     * @return
     * String of assessment percent 2
     */
    public String getAssessment2() {
        return this.assessment2;
    }


    /**
     * Gets the assessment percent 3 information
     * @return
     * String of assessment percent 3
     */
    public String getAssessment3() {
        return this.assessment3;
    }

    /**
     * Gets the all assessment class information
     * @return
     * String of all assessment class
     */
    public String getAssessmentClass() {
        return this.assessmentClass;
    }

    /**
     * Checks if input matches any assessment
     * @return
     * Boolean
     * True if input matches any assessment
     * False otherwise
     */
    public Boolean isAssessment(String input) {
        return (this.isAssessment1(input)||this.isAssessment2(input)||this.isAssessment3(input));
    }

    /**
     * Checks if input matches assessment 1
     * @return
     * Boolean
     * True if input matches assessment 1
     * False otherwise
     */
    public Boolean isAssessment1(String input) {return input.equalsIgnoreCase(this.assessment1); }


    /**
     * Checks if input matches assessment 2
     * @return
     * Boolean
     * True if input matches assessment 2
     * False otherwise
     */
    public Boolean isAssessment2(String input) {
        return input.equalsIgnoreCase(this.assessment2.toUpperCase());
    }


    /**
     * Checks if input matches assessment 3
     * @return
     * Boolean
     * True if input matches assessment 3
     * False otherwise
     */
    public Boolean isAssessment3(String input) {
        return input.equalsIgnoreCase(this.assessment3.toUpperCase());
    }


}
