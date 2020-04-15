

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class FileReaderCSV {

    public FileReaderCSV(String csvFile, HashMap<String,String> ServerDictionary) {

       // String csvFile = "/Users/ishan/Downloads/dictionary.csv";
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
                
                //System.out.println("key = " + field[0] + " , val =" + field[2] + "");
                ServerDictionary.put(field[0], field[2]);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}

