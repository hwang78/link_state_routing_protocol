package network_protocol;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

public class FileReading {

	private final Logger LOGGER = Logger.getLogger(FileReading.class);
	
	public ArrayList<String> readFile(String filePath) {
		if (filePath == null) {
			LOGGER.info("Invalid file path.");
			return null;
		}
		
		BufferedReader br = null;
		ArrayList<String> result = new ArrayList<String>();
		try {
			String line;
			br = new BufferedReader(new FileReader(filePath));
			while ((line=br.readLine()) != null) {
				result.add(line);				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if (br != null)
					br.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		LOGGER.info("Routing table reads in finished");
		return result;
	}
	
}
