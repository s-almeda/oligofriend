package oligo;

import java.lang.String;
import java.sql.*;

public class OligoService {

    Connection conn;

    String greeting = "Hello";

    public String uploadFile() { return greeting+" file! I'm not done coding this part yet woops lol"; }


    public String uploadText(String textInput)throws SQLException{
        //letsOligo(textInput);
        return Integer.toString(getMinDecodon(textInput));
    }




    private String letsOligo(String textInput) throws SQLException{
        getMinDecodon(textInput);
        return  Integer.toString(getMinDecodon(textInput));
    }

    private int getMinDecodon(String AAsubset) throws SQLException {
        String query = "select oligo_num from aasd where aa_subset = '" + AAsubset + "';";
        System.out.println("SQL QUERY: " + query + "\n");

        connectToDatabase();
        Statement stmt = conn.createStatement();
        ResultSet rset = stmt.executeQuery(query);
        int oligoNum = 0;
        while (rset.next()){
            oligoNum = Integer.parseInt(rset.getString("oligo_num"));
        }
        System.out.println("OLIGONUM: " + oligoNum + "\n");
        return oligoNum;

    }

    private void connectToDatabase() throws SQLException{

        conn = DriverManager.getConnection(
                "jdbc:postgresql://ec2-54-197-249-140.compute-1.amazonaws.com:5432/d5f51fk5mvt090?sslmode=require",
                "ajnrdltavbcduq",
                "21e736bec8f3896ac3b51c0ff4fdcefd099f9c309a89f71015fcbda5da37eb0a");


    }


}
