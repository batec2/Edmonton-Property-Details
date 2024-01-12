package com.Github.cmpt305milestone2.Data;

import java.util.Arrays;
import java.util.List;
/**
 * Crime class that allows for data to be transfered from CSV to the sql database
 */
public class Crime {
    private String longitude;
    private String latitude;
    private String id;
    private String reported_date;
    private String occurrence_category;
    private String occurrence_group;
    private String occurrence_type_group;
    private String intersection;
    private String reported_day;
    private String reported_month;
    private String reported_year;
    private String date_reported;
    /**
     * String is expected to be formated in a CSV format, the string is then split with the help of
     * regex
     */
    public Crime(String data){
        //https://stackoverflow.com/questions/15738918/splitting-a-csv-file-with-quotes-as-text-delimiter-using-string-split
        List<String> dataList = Arrays.asList(data.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"));
        this.longitude=dataList.get(0);
        this.latitude=dataList.get(1);
        this.id=dataList.get(2);
        this.reported_date=dataList.get(3);
        this.occurrence_category=dataList.get(4);
        this.occurrence_group=dataList.get(5);
        this.occurrence_type_group=dataList.get(6);
        this.intersection=dataList.get(7);
        this.reported_day=dataList.get(8);
        this.reported_month=dataList.get(9);
        this.reported_year=dataList.get(10);
        this.date_reported=dataList.get(11);
    }

    /**
     * Turns all values stored inside the class into a string that can be inserted into a SQL database use SQL, all
     * empty values are replaced with a NULL
     * @return Returns a string of all the values in the object
     */
    public String toStringNull() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(longitude.isBlank()?"":"'")
                .append(longitude.isBlank()?"Null":longitude.replace("'","''"))
                .append(longitude.isBlank()?",":"',")
                .append(latitude.isBlank()?"":"'")
                .append(latitude.isBlank()?"Null":latitude.replace("'","''"))
                .append(latitude.isBlank()?",":"',")
                .append(id.isBlank()?"":"'")
                .append(id.isBlank()?"Null":id.replace("'","''"))
                .append(id.isBlank()?",":"',")
                .append(reported_date.isBlank()?"":"'")
                .append(reported_date.isBlank()?"Null":reported_date.replace("'","''"))
                .append(reported_date.isBlank()?",":"',")
                .append(occurrence_category.isBlank()?"":"'")
                .append(occurrence_category.isBlank()?"Null":occurrence_category.replace("'","''"))
                .append(occurrence_category.isBlank()?",":"',")
                .append(occurrence_group.isBlank()?"":"'")
                .append(occurrence_group.isBlank()?"Null":occurrence_group.replace("'","''"))
                .append(occurrence_group.isBlank()?",":"',")
                .append(occurrence_type_group.isBlank()?"":"'")
                .append(occurrence_type_group.isBlank()?"Null":occurrence_type_group.replace("'","''").toUpperCase())
                .append(occurrence_type_group.isBlank()?",":"',")
                .append(intersection.isBlank()?"":"'")
                .append(intersection.isBlank()?"Null":intersection.replace("'","''"))
                .append(intersection.isBlank()?",":"',")
                .append(reported_day.isBlank()?"":"'")
                .append(reported_day.isBlank()?"Null":reported_day.replace("'","''"))
                .append(reported_day.isBlank()?",":"',")
                .append(reported_month.isBlank()?"":"'")
                .append(reported_month.isBlank()?"Null":reported_month.replace("'","''"))
                .append(reported_month.isBlank()?",":"',")
                .append(reported_year.isBlank()?"":"'")
                .append(reported_year.isBlank()?"Null":reported_year.replace("'","''"))
                .append(reported_year.isBlank()?",":"',")
                .append(date_reported.isBlank()?"":"'")
                .append(date_reported.isBlank()?"Null":date_reported.replace("'","''"))
                .append(date_reported.isBlank()?",":"'");

        return stringBuilder.toString();
    }
}
