package com.Github.cmpt305milestone2.Data;

import java.util.Arrays;
import java.util.List;

public class FruitTree {
    private String id;
    private String neighbourhood;
    private String locationType;
    private String speciesBotanical;
    private String speciesCommon;
    private String genus;
    private String species;
    private String cultivar;
    private String DBH;
    private String condition;
    private String plantedDate;
    private String owner;
    private String bearsEdibleFruit;
    private String typeEdibleFruit;
    private String count;
    private String latitude;
    private String longitude;
    private String location;
    private String pointLocation;

    public FruitTree(String data){
        //https://stackoverflow.com/questions/15738918/splitting-a-csv-file-with-quotes-as-text-delimiter-using-string-split
        List<String> dataList = Arrays.asList(data.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"));
        this.id=dataList.get(0);
        this.neighbourhood=dataList.get(1);
        this.locationType=dataList.get(2);
        this.speciesBotanical=dataList.get(3);
        this.speciesCommon=dataList.get(4);
        this.genus=dataList.get(5);
        this.species=dataList.get(6);
        this.cultivar=dataList.get(7);
        this.DBH=dataList.get(8);
        this.condition=dataList.get(9);
        this.plantedDate=dataList.get(10);
        this.owner=dataList.get(11);
        this.bearsEdibleFruit=dataList.get(12);
        this.typeEdibleFruit=dataList.get(13);
        this.count=dataList.get(14);
        this.latitude=dataList.get(15);
        this.longitude=dataList.get(16);
        this.location=dataList.get(17);
        this.pointLocation=dataList.get(18);
    }

    /**
     * Gets class member variables in a readable string
     * @return
     * String of all the member variables
     */
    public String toStringNull() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(id.isBlank()?"":"'")
                .append(id.isBlank()?"Null":id.replace("'","''"))
                .append(id.isBlank()?",":"',")
                .append(neighbourhood.isBlank()?"":"'")
                .append(neighbourhood.isBlank()?"Null":neighbourhood.replace("'","''"))
                .append(neighbourhood.isBlank()?",":"',")
                .append(locationType.isBlank()?"":"'")
                .append(locationType.isBlank()?"Null":locationType.replace("'","''"))
                .append(locationType.isBlank()?",":"',")
                .append(speciesBotanical.isBlank()?"":"'")
                .append(speciesBotanical.isBlank()?"Null":speciesBotanical.replace("'","''"))
                .append(speciesBotanical.isBlank()?",":"',")
                .append(speciesCommon.isBlank()?"":"'")
                .append(speciesCommon.isBlank()?"Null":speciesCommon.replace("'","''"))
                .append(speciesCommon.isBlank()?",":"',")
                .append(genus.isBlank()?"":"'")
                .append(genus.isBlank()?"Null":genus.replace("'","''"))
                .append(genus.isBlank()?",":"',")
                .append(species.isBlank()?"":"'")
                .append(species.isBlank()?"Null":species.replace("'","''"))
                .append(species.isBlank()?",":"',")
                .append(cultivar.isBlank()?"":"'")
                .append(cultivar.isBlank()?"Null":cultivar.replace("'","''"))
                .append(cultivar.isBlank()?",":"',")
                .append(DBH.isBlank()?"":"'")
                .append(DBH.isBlank()?"Null":DBH.replace("'","''"))
                .append(DBH.isBlank()?",":"',")
                .append(condition.isBlank()?"":"'")
                .append(condition.isBlank()?"Null":condition.replace("'","''"))
                .append(condition.isBlank()?",":"',")
                .append(plantedDate.isBlank()?"":"'")
                .append(plantedDate.isBlank()?"Null":plantedDate.replace("'","''"))
                .append(plantedDate.isBlank()?",":"',")
                .append(owner.isBlank()?"":"'")
                .append(owner.isBlank()?"Null":owner.replace("'","''"))
                .append(owner.isBlank()?",":"',")
                .append(bearsEdibleFruit.isBlank()?"":"'")
                .append(bearsEdibleFruit.isBlank()?"Null":bearsEdibleFruit.replace("'","''"))
                .append(bearsEdibleFruit.isBlank()?",":"',")
                .append(typeEdibleFruit.isBlank()?"":"'")
                .append(typeEdibleFruit.isBlank()?"Null":typeEdibleFruit.replace("'","''").toUpperCase())
                .append(typeEdibleFruit.isBlank()?",":"',")
                .append(count.isBlank()?"":"'")
                .append(count.isBlank()?"Null":count.replace("'","''"))
                .append(count.isBlank()?",":"',")
                .append(latitude.isBlank()?"":"'")
                .append(latitude.isBlank()?"Null":latitude.replace("'","''"))
                .append(latitude.isBlank()?",":"',")
                .append(longitude.isBlank()?"":"'")
                .append(longitude.isBlank()?"Null":longitude.replace("'","''"))
                .append(longitude.isBlank()?",":"',")
                .append(location.isBlank()?"":"'")
                .append(location.isBlank()?"Null":location.replace("'","''"))
                .append(location.isBlank()?",":"',")
                .append(pointLocation.isBlank()?"":"'")
                .append(pointLocation.isBlank()?"Null":pointLocation.replace("'","''"))
                .append(pointLocation.isBlank()?",":"'");
        return stringBuilder.toString();
    }
}
