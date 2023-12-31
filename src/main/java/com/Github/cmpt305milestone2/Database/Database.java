package com.Github.cmpt305milestone2.Database;

import com.Github.cmpt305milestone2.DAO.CrimeDataDAO;
import com.Github.cmpt305milestone2.DAO.DeprecatedDAO.CsvPropertyAssessmentDAO;
import com.Github.cmpt305milestone2.DAO.FruitTreesDAO;
import com.Github.cmpt305milestone2.DAO.DeprecatedDAO.PropertyAssessmentsDAO;
import com.Github.cmpt305milestone2.DAO.WeedStoreDAO;
import com.Github.cmpt305milestone2.Data.Crime;
import com.Github.cmpt305milestone2.Data.FruitTree;
import com.Github.cmpt305milestone2.Data.Property;
import com.Github.cmpt305milestone2.Data.WeedStore;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private Connection connection = null;

    private Statement statement;

    private String insertPropertyString = "BEGIN; INSERT INTO PropertyAssessments(account_number,suite,house_number,street_name,garage," +
            " neighbourhood_id,neighbourhood,ward,assessed_value,latitude,longitude,point_location,tax_class_pct_1," +
            "tax_class_pct_2, tax_class_pct_3,mill_class_1,mill_class_2, mill_class_3) VALUES ";

    private String insertTreesString = "BEGIN; INSERT INTO FruitTrees (tree_id,neighbourhood_name,location_type,species_botanical," +
            "species_common,genus,species,cultivar,diameter_breast_height,condition_percent,planted_date," +
            "owner,bears_edible_fruit,type_of_edible_fruit,amount,latitude,longitude,location,point_location) VALUES ";

    private String insertCrimeString = "BEGIN; INSERT INTO Crime (longitude,latitude,id,reported_date,occurrence_category," +
            "occurrence_group,occurrence_type_group,intersection,reported_day," +
            "reported_month,reported_year,date_reported) VALUES ";
    private String insertWeedString = "BEGIN; INSERT INTO WeedStore (category ,trade_name ,address ,licence_number ," +
            "licence_status ,issue_date ,expiry_date ,business_improvement_area ,neighbourhood_ID ,neighbourhood ,ward ," +
            "latitude ,longitude ,location ,count ,geometry_point) VALUES ";

    public Database() throws SQLException{
        openConnection();
        statement = connection.createStatement();
        statement.setQueryTimeout(30);  // set timeout to 30 sec.
    }

    public ResultSet customQuery(String query) throws SQLException{
        return statement.executeQuery(query);
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

    /**
     * Querys the table and gets the count column, Must name count as count
     * @param query Query example - SELECT COUNT(DISTINCT(neighbourhood)) as count from PropertyAssessments
     * @return Returns a int containing the count
     * @throws SQLException Exception
     */
    public int getCount(String query) throws SQLException{
        ResultSet resultSet = statement.executeQuery(query);
        return resultSet.getInt("count");
    }

    /**
     * Takes a query and gets a specific column from the query
     * @param query Query example - SELECT DISTINCT(neighbourhood) from PropertyAssessments
     * @param column Column name example - neighbourhood
     * @return Returns a list of strings containing values in the column
     * @throws SQLException Exception
     */
    public List<String> getColumn(String query,String column) throws SQLException{
        ResultSet resultSet = statement.executeQuery(query);
        List<String> neighbourhoods = new ArrayList<>();

        while(resultSet.next())
        {
            if(resultSet.getString(column)!=null){
                neighbourhoods.add(resultSet.getString(column));
            }
        }
        return neighbourhoods;
    }

    public void createPropertyTable() throws SQLException{
        PropertyAssessmentsDAO dao = new CsvPropertyAssessmentDAO("files/Property_Assessment_Data_2023.csv");
        statement.executeUpdate(
                "create table if not exists PropertyAssessments (account_number integer, suite text,house_number text," +
                        "street_name text,garage text, neighbourhood_id integer,neighbourhood text," +
                        "ward text,assessed_value integer,latitude text,longitude text,point_location text," +
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
                        "latitude text,longitude text,location text,point_location text)");

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
                "create table if not exists Crime (longitude text,latitude text,id integer PRIMARY KEY,reported_date text," +
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

    public void createWeedTable() throws SQLException{
        WeedStoreDAO dao = new WeedStoreDAO();
        statement.executeUpdate(
                "create table if not exists WeedStore (category text,trade_name text,address text,licence_number text," +
                        "licence_status text,issue_date text,expiry_date text,business_improvement_area text," +
                        "neighbourhood_ID text,neighbourhood text,ward text,latitude text,longitude text,location text," +
                        "count text,geometry_point text,id integer PRIMARY KEY)");

        List<WeedStore> weedList = dao.getAll();
        StringBuilder stringBuilder = new StringBuilder(insertWeedString);
        int i = 1;
        for(WeedStore item:weedList)
        {
            stringBuilder.append((i%1000==0)||(i==weedList.size())?"(" + item.toStringNull() + ")":"(" + item.toStringNull() + "),");
            if((i%1000==0)||(i==weedList.size())) {
                System.out.println(i);
                stringBuilder.append("ON CONFLICT(id) DO NOTHING; COMMIT;");
                statement.executeUpdate(stringBuilder.toString());
                stringBuilder = new StringBuilder(insertWeedString);
            }
            i++;
        }
    }

    public void dropTables()throws SQLException{
        statement.executeUpdate("drop table if exists PropertyAssessments");
        statement.executeUpdate("drop table if exists FruitTrees");
        statement.executeUpdate("drop table if exists Crime");
        statement.executeUpdate("drop table if exists WeedStore");
    }

    public void dropWeed() throws SQLException{
        statement.executeUpdate("drop table if exists WeedStore");
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
