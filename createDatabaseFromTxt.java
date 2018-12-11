import java.io.FileNotFoundException;
import java.lang.String;
import java.sql.*;
import java.lang.StringBuilder;
import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;
import java.math.BigInteger;

import java.util.Arrays;

public class createDatabaseFromTxt {
    public static Connection conn;
    public static void main(String[] args) throws SQLException, FileNotFoundException {
        //connectToDatabase();

        StringBuilder rowInsert = new StringBuilder();


        File f = new File("codonSupersetOut.txt");
        Scanner scan = new Scanner(f);
        int oligo_num;
        StringBuilder AA_subset;
        StringBuilder codon_ex;

        int[] oligo_nums = new int[5]; //oligo_num_1 to oligo_num_5
        StringBuilder[] AA_subsets = new StringBuilder[5];  //aa_subset_1 to 5
        int[] cardinalities = new int[5]; //cardinalities
        StringBuilder[] codon_exs = new StringBuilder[5];  //codon_ex to 5


        int current;
        ArrayList<String> line = new ArrayList<String>();



        //String.format("%05d", yournumber);

        while (scan.hasNextLine()&&scan.hasNextInt())
        {
            AA_subset = new StringBuilder();
            codon_ex = new StringBuilder();
            rowInsert.setLength(0);

            for (int i = 0; i < 5; i++){ //initialize everything;
                oligo_nums[i] =  -1;
                AA_subsets[i] = new StringBuilder("null");
                cardinalities[i] = -1;

                codon_exs[i] = new StringBuilder("null");

                line.clear();

            }

            //get the line as an arraylist of strings
            int j = 2;
            line = new ArrayList<String>(Arrays.asList((scan.nextLine()).split("\\s")));


            oligo_num = Integer.parseInt(line.get(0));
            AA_subset.append(line.get(1));

            while(j< line.size() && !(isNumeric(line.get(j)))) { //until the next group
                codon_ex.append(" " + line.get(j));
                j++;

            }
            //move into the rest of the line....///

            while (j < line.size()){
                current = Integer.parseInt(line.get(j)) -1;
                //System.out.println("CURRENT: " + current);
                oligo_nums[current] = current+1;
                j++;
                AA_subsets[current].setLength(0);
                AA_subsets[current].append(line.get(j));
                j++;
                cardinalities[current] = Integer.parseInt(line.get(j));
                j++;
                codon_exs[current].setLength(0);
                while(j< line.size() && !(isNumeric(line.get(j)))) { //until the next group
                    codon_exs[current].append(line.get(j) + " ");
                    j++;

                }

            }

            String insertP1= "INSERT INTO MINSUPER (oligo_num,AA_subset,codon_ex," +
                    "d1,AA_subset_1,c_1,codon_ex_1, " +
                    " d2, AA_subset_2,c_2,codon_ex_2," +
                    "d3,AA_subset_3,c_3,codon_ex_3," +
                    "d4,AA_subset_4,c_4,codon_ex_4," +
                    "d5,AA_subset_5,c_5,codon_ex_5" +
                    ") VALUES (";

            rowInsert.append(insertP1);

            rowInsert.append(oligo_num);
            rowInsert.append(", '" + AA_subset.toString() + "', '" + codon_ex.toString() + "', "); //append first part

            for (int i = 0; i < 5; i++) {
                if (i>0)
                {
                    rowInsert.append(", ");
                }
                if (oligo_nums[i] >= 0)
                    rowInsert.append(oligo_nums[i]);
                else{
                    rowInsert.append("'null'");
                }
                rowInsert.append(", '" + AA_subsets[i].toString() + "', ");
                if (cardinalities[i] >= 0)
                    rowInsert.append(cardinalities[i]);
                else {
                    rowInsert.append("'null'");
                }
                rowInsert.append(", '" + codon_exs[i].toString()+ "'");
            }




            rowInsert.append(");");
            System.out.println(rowInsert);

            //Statement stmt = conn.createStatement();
            //stmt.executeUpdate(rowInsert.toString());




        }












    }

    public static boolean isNumeric(String strNum) {
        try {
            int d = Integer.parseInt(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }


    private static void connectToDatabase() throws SQLException{

        conn = DriverManager.getConnection(
                "jdbc:postgresql://ec2-54-197-249-140.compute-1.amazonaws.com:5432/d5f51fk5mvt090",
                "ajnrdltavbcduq",
                "21e736bec8f3896ac3b51c0ff4fdcefd099f9c309a89f71015fcbda5da37eb0a");


    }
}