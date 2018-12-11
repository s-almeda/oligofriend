package oligo;

import java.io.File;
import java.lang.String;
import java.sql.*;
import java.lang.StringBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class OligoService {

    public String fileName = "testcases/GFP.fasta";
    public int printIterationIndices = 0;
    public int printCostofEachOligo = 0;
    public int printOptimalCosts = 0;
    public int printFinalSequence = 1;
    public int drawOligos = 0;

    Connection conn;



    public ArrayList<String> uploadFile() {


        ArrayList<String> result = new ArrayList<String>();
        result.add("INVALID");
        result.add("Sorry. I'm not done coding this part yet.");



        return result;
    }

    public ArrayList<String> uploadText(String textInput, int min_s, int max_s, int min_o, int max_o)throws SQLException{

        //letsOligo(processInput(textInput));
        return letsOligo(processInput(textInput),  min_s, max_s, min_o, max_o);
    }




    private ArrayList<String> letsOligo(String fastaSequence, int min_s, int max_s, int min_o, int max_o) throws SQLException{

        ArrayList<String> result = new ArrayList<String>();
        int n = fastaSequence.length(), i, start, overlap, j, k, oligoCost, lastOligoCost = 0;
        int[] cost = new int[n];


        if (fastaSequence == ""){
            return null;
        } //System.out.print("fasta Sequence:" + fastaSequence + " n: " + n);

        int[] deCodons = getPosCosts(fastaSequence);
       // System.out.println("Done with creating deCodons");

        Node[] ptr = new Node[n];
        ptr[0] = new Node(-1, 0);


        //====== Initialize array cost[] =======//

        for(i = 0; i < min_s-1; i++){ //an oligo cannot end earlier than the minimum size
            cost[i] = Integer.MAX_VALUE; //initialized to infinity
        }

        //====== Beginning of Nested For-Loops ========//

        for (i = min_s-1; i <n; i ++){ //go from the 1st possible end of an oligo to end of the sequence
            if (printIterationIndices == 1)
                System.out.println("i: " + i);
            cost[i] = Integer.MAX_VALUE; //initialize as infinity... and beyond

            for(j = i-min_s; j>= i-max_s; j--){//start from the latest possible beginning point for this oligo to the earliest
                if (printIterationIndices == 1)
                    System.out.print("j: " + j);
                if (j < (min_s - max_o)) //if starting point is < earliest possible starting point for a 2nd oligo, we are dealing with 1st oligo
                {
                    start = 0;	//set starting point to 0 instead

                    j = i-max_s - 1; //we only need to check this once!
                }
                else
                    start = j; //set starting point to j


                for(k = max_o; k >= min_o; k--){

                    if (i < (2*min_s - k)|| start== 0) //if we are dealing with the 1st oligo, which cannot possibly have an overlap
                    {
                        overlap = 0; //the overlap is 0
                        k = 0; //and we only need to check this once

                        if (start!= 0) //oligos besides the first one must have a nonzero overlap
                            continue;
                    }
                    else if ( (start + k - 1) < (min_s - 1) ) { // subtracting 1 to debug
                        continue;
                    }
                    else
                        overlap = k; //otherwise its k

                    oligoCost = oligo_cost(start, i, overlap, deCodons);

                    if (printIterationIndices == 1)
                        System.out.print(" k: " + k);
                    if (printCostofEachOligo== 1){
                        System.out.print(" - cost of oligo ending at " + i + " starting at " + start + " with an overlap of size " + overlap + " is: ");
                        System.out.println(oligoCost);
                    }

                    if ( (start + overlap) == 0) // if we're looking at the first oligo. there is no prior oligo
                    {
                        lastOligoCost = 0;
                    }
                    else
                    {
                        lastOligoCost = cost[start+overlap-1]; // Subtract 1 to get where the last oligo ends
                    }

                    if (printCostofEachOligo== 1)
                        System.out.println("cost (" + oligoCost + ") plus last oligo cost ("  + lastOligoCost + ") = " + (oligoCost + lastOligoCost));

                    if ((oligoCost + lastOligoCost) < cost[i])
                    {
                        if (printCostofEachOligo== 1)
                            System.out.print("...This is smaller than cost[i], so replace it\n");
                        cost[i] = (oligoCost + lastOligoCost);
                        ptr[i] = new Node ((start+overlap-1), overlap); // Subtract 1 to get where the last oligo ends
                    }

                }

            }
            if (printOptimalCosts== 1)
                System.out.println("Optimal Cost for an oligo ending at " + i  + ": " + cost[i]);
        }







        if (printFinalSequence == 1){
            int pos = n-1;
            while(pos >= 0)
            {

                result.add(pos + " with overlap " + ptr[pos].getOverlap() + "\n");
                pos = ptr[pos].getParent();
            }
        }





        return result;
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

        conn.close();
        return oligoNum;

    }



    private void connectToDatabase() throws SQLException{

        conn = DriverManager.getConnection(
                "jdbc:postgresql://ec2-54-197-249-140.compute-1.amazonaws.com:5432/d5f51fk5mvt090?sslmode=require",
                "ajnrdltavbcduq",
                "21e736bec8f3896ac3b51c0ff4fdcefd099f9c309a89f71015fcbda5da37eb0a");


    }


    private String processInput (String textInput){
        // ----   process text input, store sequence in fastaSequence ----- //
        String fastaSequence;
        int i;
        for (i = 0; i < textInput.length(); i++) {
            while (i < textInput.length() && textInput.charAt(i) != '\n') { i++; }
            i++; //Skip first line, then new line character


            break;

        }
        try {
            fastaSequence = textInput.substring(i, textInput.length());
        }
        catch (Exception e){
            fastaSequence = "INVALID";
        }
        return fastaSequence;
    }



    public String getAAIndex(ArrayList<Character> set)
    {
        int[] bits = new int[20];

        for (int i = 0; i < set.size(); i++)
        {
            //System.out.print(set.get(i));
            switch(set.get(i))
            {
                case 'V': bits[19] = 1;
                    break;
                case 'Y': bits[18] = 1;
                    break;
                case 'W': bits[17] = 1;
                    break;
                case 'T': bits[16] = 1;
                    break;
                case 'S': bits[15] = 1;
                    break;
                case 'P': bits[14] = 1;
                    break;
                case 'F': bits[13] = 1;
                    break;
                case 'M': bits[12] = 1;
                    break;
                case 'K': bits[11] = 1;
                    break;
                case 'L': bits[10] = 1;
                    break;
                case 'I': bits[9] = 1;
                    break;
                case 'H': bits[8] = 1;
                    break;
                case 'G': bits[7] = 1;
                    break;
                case 'E': bits[6] = 1;
                    break;
                case 'Q': bits[5] = 1;
                    break;
                case 'C': bits[4] = 1;
                    break;
                case 'D': bits[3] = 1;
                    break;
                case 'N': bits[2] = 1;
                    break;
                case 'R': bits[1] = 1;
                    break;
                case 'A': bits[0] = 1;
                    break;
            }
        }

        System.out.println();
        StringBuilder b = new StringBuilder();
        for (int i = 19; i >= 0; i--)
        {
            b.append(bits[i]);
        }


        return b.toString();
    }

    private int[] getPosCosts(String fs) throws SQLException{

        int pos = 0;
        boolean bracket = false;
        ArrayList<Character> set = new ArrayList<Character>();
        int[] costs = new int[fs.length()];
        for (int i = 0; i < fs.length(); i++)
        {
            char c = fs.charAt(i);

            if (bracket)
            {
                if (c == ']')
                {
                    bracket = false;

                    String index = getAAIndex(set);
                    costs[pos] = (getMinDecodon(index));
                    System.out.println(c + ": " + costs[pos]);

                    set.clear();
                    pos++;
                }
                else
                {
                    set.add(c);
                }
            }
            else if (c == '[')
            {
                bracket = true;
            }
            else
            {
                costs[pos] = 1;
                //System.out.println(c + ": " + 1);
                pos++;
            }
        }

        /*//Uncomment to print costs array as a string, for debug purposes

        StringBuilder costString = new StringBuilder("");
        for (i = 0; i < fastaSequence.length(); i++) {
            costString.append(costs[i]);
        }
        System.out.println("COSTS:" + costString.toString());
        */


        return costs;

    }



    /*==================================================/
	|	 												/
	|	public void oligo_cost(int j, int i, int k)  	/
	|		takes as arguments 							/
	|		j - start index of oligo					/
	|		k - length of overlap						/
	|		i - end of oligo							/
	|		deCodons - array with cost of each			/
	|		returns the cost of the oligo 				/
	| 	 												/
	====================================================*/

    public int oligo_cost(int j, int i, int k, int[] deCodons){
        int x;
        int cost = 1, overlapCost = 1;

        for(x = j; x < (j+k); x++){
            overlapCost *= deCodons[x];
        }
        if (overlapCost > 1)
        {
            overlapCost *= 2;
        }

        for(x = j+k; x <= i; x++){ //from the end of overlap to the end of the oligo

            cost *= deCodons[x];
        }


        return cost * overlapCost * ((i+1) - (j));

    }  /*===== end of oligo_cost() function ==========*/











    public static boolean isNumeric(String strNum) {
        try {
            int d = Integer.parseInt(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }


}
