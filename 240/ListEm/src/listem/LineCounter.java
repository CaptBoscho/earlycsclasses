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

public class LineCounter implements ILineCounter {

	public LineCounter(){}
	@Override
	public Map<File, Integer> countLines(File directory,
			String fileSelectionPattern, boolean recursive) {
		
		Parent p = new Parent();
		Map<File, Integer> maplc = new HashMap<File, Integer>();
		File[] files = p.Process(directory, fileSelectionPattern, recursive);
		for(int i=0; i<files.length; i++)
		{
			int count = 0;
			Reader fr = null;
			try {
				fr = new FileReader(files[i]);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			BufferedReader reader = new BufferedReader(fr);
			Scanner sc = new Scanner(reader);
		   while (sc.hasNextLine())
		    {
			   sc.nextLine();
			   count++;
		    }
			if(!maplc.containsKey(files[i]));
			{
				maplc.put(files[i], count);
			}
		}
		return maplc;
	}
}
