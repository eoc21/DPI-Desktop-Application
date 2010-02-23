package dpi;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Simple class to provide a list of strings to populate combo boxes for 
 * the GUI.
 * @author ed
 *
 */
public class PopulateComboBoxes {

	public PopulateComboBoxes(){
		
	}
	
	public static String[] populateProperties() throws IOException{
		ArrayList<String> propertyData = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader("Properties.txt"));
		String strLine;
		int counter = 0;
		while((strLine = br.readLine()) != null){
			propertyData.add(strLine);
		}
		String [] properties = new String[propertyData.size()];
		for(int i=0;i<propertyData.size();i++){
			properties[i] = propertyData.get(i);
		}
		return properties;
	}
	
	public static String[] populateEqualityBox(){
		String[] modifiers = {
			"GreaterThan","LessThan","GreatThanOrEqual","LessThanOrEqual","Equal"
		};
		return modifiers;
	}
}
