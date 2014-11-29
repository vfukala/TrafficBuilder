package mainPackage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Calendar;

public class CityType {
	public long time;
	public String name;
	public String folderName;
	
	public CityType(String cityName){
		this.name = cityName;
		this.folderName = getNewCityFolderName(cityName);
		this.time = 0;
		try {
			Files.createDirectory(Paths.get(System.getenv("APPDATA") + "\\TrafficBuilder\\Saves\\" + this.folderName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Functions.writeTextToFile(name, System.getenv("APPDATA") + "\\TrafficBuilder\\Saves\\" + this.folderName + "\\name.txt", true);
	}
	
	public void save(){
		Functions.writeBytesToFile(Functions.longToBytes(this.time), System.getenv("APPDATA")  + "\\TrafficBuilder\\Saves\\" + this.folderName + "\\time.byt", false);
		Calendar now = Calendar.getInstance();
		final byte[] dateData = {(byte) (now.get(Calendar.YEAR) - 2000), (byte) now.get(Calendar.MONTH), (byte) now.get(Calendar.DAY_OF_MONTH), (byte) now.get(Calendar.HOUR_OF_DAY), (byte) now.get(Calendar.MINUTE), (byte) now.get(Calendar.SECOND)};
		Functions.writeBytesToFile(dateData, System.getenv("APPDATA")  + "\\TrafficBuilder\\Saves\\" + this.folderName + "\\lastPlay.byt", false);
	}
	
	
	public static CityType load(String folderName) throws Exception{
		return new CityType(Functions.bytesToLong(Functions.readBytes(System.getenv("APPDATA") + "\\TrafficBuilder\\Saves\\" + folderName + "\\time.byt")),
				Functions.readTextFile(System.getenv("APPDATA") + "\\TrafficBuilder\\Saves\\" + folderName + "\\name.txt"), folderName);
	}
	
	protected CityType(long cityTime, String cityName, String cityFolderName){
		this.time = cityTime;
		this.name = cityName;
		this.folderName = cityFolderName;
	}
	
	static String getNewCityFolderName(String name){
		int counter = 0;
		char[] nameChars = name.toCharArray();
		while(counter < nameChars.length){
			if(nameChars[counter] == '?' | nameChars[counter] == '/' | nameChars[counter] == '<' | nameChars[counter] == '>' | nameChars[counter] == '\\' | nameChars[counter] == ':' | nameChars[counter] == '*' | nameChars[counter] == '.'){
				nameChars[counter] = '_';
			}
			counter++;
		}
		name = new String(nameChars);
		if(name.equals("com1") | name.equals("com2") | name.equals("com3") | name.equals("com4") | name.equals("com5") | name.equals("com6") | name.equals("com7") | name.equals("com8") | name.equals("com9") | name.equals("lpt1") | name.equals("lpt2") | name.equals("lpt3") | name.equals("lpt4") | name.equals("lpt5") | name.equals("lpt6") | name.equals("lpt7") | name.equals("lpt8") | name.equals("lpt9") | name.equals("con") | name.equals("nul") | name.equals("prn")){
			name = "IllegalName";
		}
		if(name.length() == 0){
			name = "IllegalName";
		}
		if(name.length() > 31){
			name = name.substring(0, 31);
		}
		if(Files.exists(Paths.get(System.getenv("APPDATA") + "\\TrafficBuilder\\Saves\\" + name))){
			counter = 2;
			while(Files.exists(Paths.get(System.getenv("APPDATA") + "\\TrafficBuilder\\Saves\\" + name + Integer.toString(counter)))){
				counter++;
			}
			name = name + Integer.toString(counter);
		}
		return name;
	}
}
