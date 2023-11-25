package com.Github.cmpt305milestone2.Database;

import com.Github.cmpt305milestone2.DAO.CsvPropertyAssessmentDAO;
import com.Github.cmpt305milestone2.DAO.PropertyAssessmentsDAO;
import com.Github.cmpt305milestone2.Data.Property;
import com.Github.cmpt305milestone2.Data.PropertyAssessments;

import java.sql.*;
import java.util.List;

public class Database {

    String insertPropertyString = "BEGIN; INSERT INTO PropertyAssessments(account_number,suite,house_number,street_number,garage," +
            " neighbourhood_id,neighbourhood,ward,assessed_value,latitude,longitude,point_location,tax_class_pct_1," +
            "tax_class_pct_2, tax_class_pct_3,mill_class_1,mill_class_2, mill_class_3) VALUES ";

    public void testFunct(){
        Connection connection = null;
        PropertyAssessmentsDAO dao = new CsvPropertyAssessmentDAO("files/Property_Assessment_Data_2023.csv");
        try
        {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:PropertyAssessmentsApp.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.executeUpdate(
                    "create table if not exists PropertyAssessments (account_number text, suite text,house_number text," +
                            "street_number text,garage text, neighbourhood_id integer,neighbourhood text," +
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
                    try {
                        stringBuilder.append("ON CONFLICT(account_number) DO NOTHING; COMMIT;");
                        statement.executeUpdate(stringBuilder.toString());
                        stringBuilder = new StringBuilder(insertPropertyString);
                    } catch (Exception e) {
                        System.out.println(e);
                        System.exit(1);
                    }
                }
                i++;
            };

            /*
            ResultSet rs = statement.executeQuery("select * from PropertyAssessments");
            while(rs.next())
            {
                // read the result set
                System.out.println("account_number = " + rs.getString("account_number"));
                System.out.println("ward = " + rs.getString("ward"));
            }
             */

        }
        catch(SQLException e)
        {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }
        finally
        {
            try
            {
                if(connection != null)
                    connection.close();
            }
            catch(SQLException e)
            {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
    }
}
