# Edmonton Property Details
## Description
- This app was created to allow users to view data on Edmonton Property Values which is made publicly available by the City of Edmonton.

## Install And Run
### Requirements 
Sqlite database must be populated with prerequisite data:
initDB.java is used to initalize sqlite database using CSV's from the following sources
- Property Assessments
```https://data.edmonton.ca/City-Administration/Property-Assessment-Data-Current-Calendar-Year-/q7d6-ambg/about_data```
- Edible Fruit Trees
```https://data.edmonton.ca/Environmental-Services/Edible-Fruit-Trees/h4ti-be2n```
- Crime Map
```https://experience.arcgis.com/experience/8e2c6c41933e48a79faa90048d9a459d/page/Table/```
- Cannabis Retail Sales
```https://data.edmonton.ca/Urban-Planning-Economy/Cannabis-Retail-Sales-Licences/xz5r-f944/about_data```
ArcGIS Api Key:
A account must be created with ArcGIS and a API key put into a apiKey.txt file in ./files
```https://developers.arcgis.com/documentation/mapping-apis-and-services/security/api-keys/```

## Features
### Filtering for Data on Property
<img src='github-images/Screenshot 2024-01-11 181858.png' width=60%>

### Arcgis Map representing Properties
<img src='github-images/Screenshot 2024-01-11 183127.png' width=60%>

### Various Charts for Comparing Data
<img src='github-images/Screenshot 2024-01-11 183227.png' width=60%>

# Credits
Crush Bate 
```https://github.com/batec2```
Neal Hamacher
```https://github.com/nealhamacher```
Dan Simmons
```https://github.com/DanSimons```
