package listem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Grep implements IGrep{

	public Grep(){}
	
	@Override
	public Map<File, List<String>> grep(File directory,
			String fileSelectionPattern, String substringSelectionPattern,
			boolean recursive) {
		
		Parent p = new Parent();
		Map<File, List<String>> mapg = new HashMap<File, List<String>>();
		File[] files = p.Process(directory, fileSelectionPattern, recursive);
		for(int i=0; i<files.length; i++){
			  	Reader fr = null;
				try {
					fr = new FileReader(files[i]);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				BufferedReader reader = new BufferedReader(fr);
				Scanner sc = new Scanner(reader);
			    String line;
			    while (sc.hasNext()) {
			    	line = sc.nextLine();
			    	Pattern pat = Pattern.compile(substringSelectionPattern);
			    	Matcher m = pat.matcher(line);
					boolean b = m.find();
					if(b){
						if(!mapg.containsKey(files[i])){
							List<String> n = new ArrayList<String>();
							mapg.put(files[i], n);
						}
						mapg.get(files[i]).add(line);
					}
			    }
		}
		return mapg;
	}
}
