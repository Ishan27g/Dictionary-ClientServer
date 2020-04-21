import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.util.HashMap;
import java.util.Map;


public class FileWriterCSV {

    BufferedWriter bw = null;
    private int entries;
    public FileWriterCSV(String csvFile, HashMap<String,String> ServerDictionary)
    {
        System.out.println("saving data to file " + csvFile);
        entries = 0;
        try{
            bw = new BufferedWriter(new FileWriter(csvFile));
            for(Map.Entry<String, String> entry : ServerDictionary.entrySet()){
                
                bw.write( "\""+entry.getKey() + "\"" + "," + "\""+ "\"" +  "," + "\"" + entry.getValue()+"\"");
                bw.newLine();
                entries++;
            }
            
            bw.flush();
 
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            
            try{
                //always close the writer
                bw.close();
            }catch(Exception e){

            }
        }
    }

    public int close(){
        return entries;
    }
}