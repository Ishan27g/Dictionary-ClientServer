/**
 * @author Ishan Goyal - 1056051 
 * This class defines the file operations for the dictionary data
 * 
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class FileReaderCSV {

    public FileReaderCSV(String csvFile, HashMap<String,String> ServerDictionary) {

        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] field = line.split(cvsSplitBy, 3);
                field[0] = field[0].replace("\"", "");
                field[2] = field[2].replace("\"", "");
                ServerDictionary.put(field[0], field[2]);
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found : " + csvFile);
        } catch (IOException e) {
            System.out.println("Error reading file : " + csvFile);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println("Error closing file : " + csvFile);
                }
            }
        }
    }
}

