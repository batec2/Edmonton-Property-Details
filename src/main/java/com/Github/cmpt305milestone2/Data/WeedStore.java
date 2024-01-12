package com.Github.cmpt305milestone2.Data;

import java.util.Arrays;
import java.util.List;

/**
 * Weed store that allows for data to be transfered from CSV to the sql database
 */
public class WeedStore {
    private String category;
    private String tradeName;
    private String address;
    private String licenseNumber;
    private String licenseStatus;
    private String issueDate;
    private String expiryDate;
    private String businessImprovementArea;
    private String neighbourhoodId;
    private String neighbourhood;
    private String ward;
    private String latitude;
    private String longitude;
    private String location;
    private String count;
    private String pointLocation;
    /**
     * String is expected to be formated in a CSV format, the string is then split with the help of
     * regex
     */
    public WeedStore(String data){
        //https://stackoverflow.com/questions/15738918/splitting-a-csv-file-with-quotes-as-text-delimiter-using-string-split
        List<String> dataList = Arrays.asList(data.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"));
        category=dataList.get(0);
        tradeName=dataList.get(1);
        address=dataList.get(2);
        licenseNumber=dataList.get(3);
        licenseStatus=dataList.get(4);
        issueDate =dataList.get(5);
        expiryDate=dataList.get(6);
        businessImprovementArea=dataList.get(7);
        neighbourhoodId=dataList.get(8);
        neighbourhood=dataList.get(9);
        ward=dataList.get(10);
        latitude=dataList.get(11);
        longitude=dataList.get(12);
        location=dataList.get(13);
        count=dataList.get(14);
        pointLocation=(dataList.size()==15)?"":dataList.get(15);
    }

    /**
     * Turns all values stored inside the class into a string that can be inserted into a SQL database use SQL, all
     * empty values are replaced with a NULL
     * @return Returns a string of all the values in the object
     */
    public String toStringNull() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(category.isBlank()?"":"'")
                .append(category.isBlank()?"Null":category.replace("'","''"))
                .append(category.isBlank()?",":"',")
                .append(tradeName.isBlank()?"":"'")
                .append(tradeName.isBlank()?"Null":tradeName.replace("'","''"))
                .append(tradeName.isBlank()?",":"',")
                .append(address.isBlank()?"":"'")
                .append(address.isBlank()?"Null":address.replace("'","''"))
                .append(address.isBlank()?",":"',")
                .append(licenseNumber.isBlank()?"":"'")
                .append(licenseNumber.isBlank()?"Null":licenseNumber.replace("'","''"))
                .append(licenseNumber.isBlank()?",":"',")
                .append(licenseStatus.isBlank()?"":"'")
                .append(licenseStatus.isBlank()?"Null":licenseStatus.replace("'","''"))
                .append(licenseStatus.isBlank()?",":"',")
                .append(issueDate.isBlank()?"":"'")
                .append(issueDate.isBlank()?"Null":issueDate.replace("'","''"))
                .append(issueDate.isBlank()?",":"',")
                .append(expiryDate.isBlank()?"":"'")
                .append(expiryDate.isBlank()?"Null":expiryDate.replace("'","''"))
                .append(expiryDate.isBlank()?",":"',")
                .append(businessImprovementArea.isBlank()?"":"'")
                .append(businessImprovementArea.isBlank()?"Null":businessImprovementArea.replace("'","''"))
                .append(businessImprovementArea.isBlank()?",":"',")
                .append(neighbourhoodId.isBlank()?"":"'")
                .append(neighbourhoodId.isBlank()?"Null":neighbourhoodId.replace("'","''"))
                .append(neighbourhoodId.isBlank()?",":"',")
                .append(neighbourhood.isBlank()?"":"'")
                .append(neighbourhood.isBlank()?"Null":neighbourhood.replace("'","''"))
                .append(neighbourhood.isBlank()?",":"',")
                .append(ward.isBlank()?"":"'")
                .append(ward.isBlank()?"Null":ward.replace("'","''"))
                .append(ward.isBlank()?",":"',")
                .append(latitude.isBlank()?"":"'")
                .append(latitude.isBlank()?"Null":latitude.replace("'","''"))
                .append(latitude.isBlank()?",":"',")
                .append(longitude.isBlank()?"":"'")
                .append(longitude.isBlank()?"Null":longitude.replace("'","''"))
                .append(longitude.isBlank()?",":"',")
                .append(location.isBlank()?"":"'")
                .append(location.isBlank()?"Null":location.replace("'","''"))
                .append(location.isBlank()?",":"',")
                .append(count.isBlank()?"":"'")
                .append(count.isBlank()?"Null":count.replace("'","''"))
                .append(count.isBlank()?",":"',")
                .append(pointLocation.isBlank()?"":"'")
                .append(pointLocation.isBlank()?"Null":pointLocation.replace("'","''"))
                .append(pointLocation.isBlank()?"":"'");
        return stringBuilder.toString().replace("\"","");
    }
}
