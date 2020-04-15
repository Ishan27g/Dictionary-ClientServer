import java.util.HashMap;
import java.util.Map;


/**
 * 
 */

/**
 * @author ishan
 *
 */
public class DictionaryData {
	
	public HashMap<String,String> ServerDictionary;
	private FileReaderCSV file_data;
	private static String file_path;
	//=new HashMap<String,String>();   
	
	public void load_dictionary() {
		file_data = new FileReaderCSV(file_path, ServerDictionary);
	}
	
	public DictionaryData(String path) {	
		file_path = new String(path);
		ServerDictionary = new HashMap<String,String>();
		load_dictionary();
	}
	
	private boolean lookupKey(String key) {
		/*for (Map.Entry me : ServerDictionary.entrySet()) {
		      System.out.println("Key: "+me.getKey() + " & Value: " + me.getValue());
		}
		*/
		return ServerDictionary.containsKey(key);
	}
	
	public String searchKey(String key) {
		if (lookupKey(key) == true) {
			return ServerDictionary.get(key);
		}
		else
			return "Key not found";
	}
	
	public synchronized boolean addKey(String key, String value) {
		if (lookupKey(key) == true) {
			System.out.println("Key already exists");
			return false;
		}
		ServerDictionary.put(key, value);
		return lookupKey(key);
	}
	
	public synchronized boolean deleteKey(String key) {
		if (lookupKey(key) == false) {
			System.out.println("Key does not exist");
			return false;
		}
		ServerDictionary.remove(key);
		return !lookupKey(key);
	}
	
	public int getSize() {
		return ServerDictionary.size();
	}
	
}