package com.Github.cmpt305milestone2.Data;

import java.util.Arrays;
import java.util.List;

public class FruitTrees {
    private String id;
    private String neighbourhood;
    private String locationType;
    private String speciesBotanical;
    private String genus;
    private String species;
    private String cultivar;
    private String DBH;
    private String condition;
    private String plantedDate;
    private String owner;
    private String bearsEdibleFruit;
    private String count;
    private String latitude;
    private String longitude;
    private String location;
    private String pointLocation;

    public FruitTrees(String data){
        List<String> dataList = Arrays.asList(data.split(","));
        this.id=dataList.get(0);
        this.neighbourhood=dataList.get(1);
        this.locationType=dataList.get(2);
        this.speciesBotanical=dataList.get(3);
        this.genus=dataList.get(4);
        this.species=dataList.get(5);
        this.cultivar=dataList.get(6);
        this.DBH=dataList.get(7);
        this.condition=dataList.get(8);
        this.plantedDate=dataList.get(9);
        this.owner=dataList.get(10);
        this.bearsEdibleFruit=dataList.get(11);
        this.count=dataList.get(12);
        this.latitude=dataList.get(13);
        this.longitude=dataList.get(14);
        this.location=dataList.get(15);
        this.pointLocation=dataList.get(16);
    }

    private List<String> splitData(String data){
        List<String> dataList = Arrays.asList(data.split(","));
        return dataList;
    }
}
