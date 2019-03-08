package oligo.helpers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class CreateDatabase {
    public static void main(String[] args) throws FileNotFoundException {
        if (args.length != 1) {
            System.out.println("Usage: java CreateDatabase <filename of supersets or subsets of amino acids>");
        } else {
            File file = new File(args[0]);

            if (file.exists()) {
                if (file.canRead()) {
                    outputSQL(file);
                } else {
                    System.out.println("'" + args[0] + "' cannot be read due to insufficient permissions.");
                }
            } else {
                System.out.println("'" + args[0] + "' does not exist.");
            }
        }
    }

    private static void outputSQL(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(new BufferedReader(new FileReader(file)));

        StringBuilder rowInsert = new StringBuilder();

        int oligoNumber;
        StringBuilder AA_subset;
        StringBuilder codonEx;

        int[] oligo_nums = new int[5]; //oligo_num_1 to oligo_num_5
        StringBuilder[] AA_subsets = new StringBuilder[5];  //aa_subset_1 to 5
        int[] cardinalities = new int[5]; //cardinalities
        StringBuilder[] codonExs = new StringBuilder[5];  //codon_ex to 5

        int current;
        ArrayList<String> line = new ArrayList<>();

        while (scanner.hasNextLine() && scanner.hasNextInt()) {
            AA_subset = new StringBuilder();
            codonEx = new StringBuilder();
            rowInsert.setLength(0);

            for (int i = 0; i < 5; i++) { //initialize everything;
                oligo_nums[i] = -1;
                AA_subsets[i] = new StringBuilder("null");
                cardinalities[i] = -1;

                codonExs[i] = new StringBuilder("null");

                line.clear();
            }

            int j = 2;
            line = new ArrayList<>(Arrays.asList((scanner.nextLine()).split("\\s")));

            oligoNumber = Integer.parseInt(line.get(0));
            AA_subset.append(line.get(1));

            while (j < line.size() && !(isNumeric(line.get(j)))) { //until the next group
                codonEx.append(" ").append(line.get(j));
                j++;
            }

            while (j < line.size()) {
                current = Integer.parseInt(line.get(j)) - 1;
                oligo_nums[current] = current + 1;
                j++;
                AA_subsets[current].setLength(0);
                AA_subsets[current].append(line.get(j));
                j++;
                cardinalities[current] = Integer.parseInt(line.get(j));
                j++;
                codonExs[current].setLength(0);
                while (j < line.size() && !(isNumeric(line.get(j)))) { //until the next group
                    codonExs[current].append(line.get(j)).append(" ");
                    j++;
                }
            }

            String insertP1 = "INSERT INTO MINSUPER (oligo_num,AA_subset,codon_ex," +
                    "d1,AA_subset_1,c_1,codon_ex_1, " +
                    " d2, AA_subset_2,c_2,codon_ex_2," +
                    "d3,AA_subset_3,c_3,codon_ex_3," +
                    "d4,AA_subset_4,c_4,codon_ex_4," +
                    "d5,AA_subset_5,c_5,codon_ex_5" +
                    ") VALUES (";

            rowInsert.append(insertP1);

            rowInsert.append(oligoNumber);
            rowInsert
                    .append(", '")
                    .append(AA_subset.toString())
                    .append("', '")
                    .append(codonEx.toString())
                    .append("', "); //append first part

            for (int i = 0; i < 5; i++) {
                if (i > 0) {
                    rowInsert.append(", ");
                }
                if (oligo_nums[i] >= 0)
                    rowInsert.append(oligo_nums[i]);
                else {
                    rowInsert.append("'null'");
                }
                rowInsert.append(", '").append(AA_subsets[i].toString()).append("', ");
                if (cardinalities[i] >= 0)
                    rowInsert.append(cardinalities[i]);
                else {
                    rowInsert.append("'null'");
                }
                rowInsert.append(", '").append(codonExs[i].toString()).append("'");
            }

            rowInsert.append(");");
            System.out.println(rowInsert);
        }

        scanner.close();
    }

    private static boolean isNumeric(String strNum) {
        try {
            Integer.parseInt(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }

        return true;
    }
}