package com.Github.cmpt305milestone2.Database;

import com.Github.cmpt305milestone2.DAO.CrimeDataDAO;
import com.Github.cmpt305milestone2.DAO.DeprecatedDAO.CsvPropertyAssessmentDAO;
import com.Github.cmpt305milestone2.DAO.FruitTreesDAO;
import com.Github.cmpt305milestone2.DAO.DeprecatedDAO.PropertyAssessmentsDAO;
import com.Github.cmpt305milestone2.Data.Crime;
import com.Github.cmpt305milestone2.Data.FruitTree;
import com.Github.cmpt305milestone2.Data.Property;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    Connection connection = null;

    Statement statement;

    String insertPropertyString = "BEGIN; INSERT INTO PropertyAssessments(account_number,suite,house_number,street_name,garage," +
            " neighbourhood_id,neighbourhood,ward,assessed_value,latitude,longitude,point_location,tax_class_pct_1," +
            "tax_class_pct_2, tax_class_pct_3,mill_class_1,mill_class_2, mill_class_3) VALUES ";

    String insertTreesString = "BEGIN; INSERT INTO FruitTrees (tree_id,neighbourhood_name,location_type,species_botanical," +
            "species_common,genus,species,cultivar,diameter_breast_height,condition_percent,planted_date," +
            "owner,bears_edible_fruit,type_of_edible_fruit,amount,latitude,longitude,location,point_location) VALUES ";

    String insertCrimeString = "BEGIN; INSERT INTO Crime (longitude,latitude,id,reported_date,occurrence_category,occurrence_group,occurrence_type_group,intersection,reported_day,reported_month,reported_year,date_reported) VALUES ";

    public Database() throws SQLException{
        openConnection();
        statement = connection.createStatement();
        statement.setQueryTimeout(30);  // set timeout to 30 sec.
    }

    public List<Property> queryPropertyAssessments(String query) throws SQLException{
        ResultSet resultSet = statement.executeQuery(query);
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        final int columnCount = resultSetMetaData.getColumnCount();
        List<Property> properties = new ArrayList<>();

        while(resultSet.next())
        {
            List<String> values = new ArrayList<>();
            for (int i = 1; i <= columnCount; i++) {
                values.add(resultSet.getString(i));
            }
            properties.add(new Property(values));
        }
        return properties;
    }

    public void createPropertyTable() throws SQLException{
        PropertyAssessmentsDAO dao = new CsvPropertyAssessmentDAO("files/Property_Assessment_Data_2023.csv");
        statement.executeUpdate(
                "create table if not exists PropertyAssessments (account_number integer, suite text,house_number text," +
                        "street_name text,garage text, neighbourhood_id integer,neighbourhood text," +
                        "ward text,assessed_value integer,latitude real,longitude real,point_location text," +
                        "tax_class_pct_1 text,tax_class_pct_2 text, tax_class_pct_3 text,mill_class_1 text," +
                        "mill_class_2 text, mill_class_3 text,PRIMARY KEY (account_number))");

        List<Property> properties = dao.getAll();
        StringBuilder stringBuilder = new StringBuilder(insertPropertyString);
        int i = 1;
        for(Property item:properties)
        {
            stringBuilder.append((i%1000==0)||(i==properties.size())?"(" + item.toStringNull() + ")":"(" + item.toStringNull() + "),");
            if((i%1000==0)||(i==properties.size())) {
                System.out.println(i);
                stringBuilder.append("ON CONFLICT(account_number) DO NOTHING; COMMIT;");
                statement.executeUpdate(stringBuilder.toString());
                stringBuilder = new StringBuilder(insertPropertyString);
            }
            i++;
        }
    }
    public void createTreesTable() throws SQLException{
        FruitTreesDAO dao = new FruitTreesDAO();
        statement.executeUpdate(
                "create table if not exists FruitTrees (id integer PRIMARY KEY,tree_id text,neighbourhood_name text,location_type text," +
                        "species_botanical text,species_common text,genus text,species text,cultivar text," +
                        "diameter_breast_height integer,condition_percent integer,planted_date text,owner text," +
                        "bears_edible_fruit boolean,type_of_edible_fruit text,amount integer," +
                        "latitude real,longitude real,location text,point_location text)");

        List<FruitTree> fruitTrees = dao.getAll();
        StringBuilder stringBuilder = new StringBuilder(insertTreesString);
        int i = 1;
        for(FruitTree item:fruitTrees)
        {
            stringBuilder.append((i%1000==0)||(i==fruitTrees.size())?"(" + item.toStringNull() + ")":"(" + item.toStringNull() + "),");
            if((i%1000==0)||(i==fruitTrees.size())) {
                System.out.println(i);
                stringBuilder.append("ON CONFLICT(id) DO NOTHING; COMMIT;");
                statement.executeUpdate(stringBuilder.toString());
                stringBuilder = new StringBuilder(insertTreesString);
            }
            i++;
        }
    }

    public void createCrimeTable() throws SQLException{
        CrimeDataDAO dao = new CrimeDataDAO();
        statement.executeUpdate(
                "create table if not exists Crime (longitude real,latitude real,id integer PRIMARY KEY,reported_date text," +
                        "occurrence_category text,occurrence_group text,occurrence_type_group text," +
                        "intersection text,reported_day text,reported_month text,reported_year text," +
                        "date_reported text)");

        List<Crime> crimeList = dao.getAll();
        StringBuilder stringBuilder = new StringBuilder(insertCrimeString);
        int i = 1;
        for(Crime item:crimeList)
        {
            stringBuilder.append((i%1000==0)||(i==crimeList.size())?"(" + item.toStringNull() + ")":"(" + item.toStringNull() + "),");
            if((i%1000==0)||(i==crimeList.size())) {
                System.out.println(i);
                stringBuilder.append("ON CONFLICT(id) DO NOTHING; COMMIT;");
                statement.executeUpdate(stringBuilder.toString());
                stringBuilder = new StringBuilder(insertCrimeString);
            }
            i++;
        }
    }

    public void dropTables()throws SQLException{
        statement.executeUpdate("drop table if exists PropertyAssessments");
        statement.executeUpdate("drop table if exists FruitTrees");
        statement.executeUpdate("drop table if exists Crime");
    }

    private void openConnection()throws SQLException{
        // create a database connection
        this.connection = DriverManager.getConnection("jdbc:sqlite:PropertyAssessmentsApp.db");
    }

    public void closeConnection()throws SQLException{
        if(connection != null){
            connection.close();
        }
    }
}
