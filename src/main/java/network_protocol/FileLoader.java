package network_protocol;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

public class FileLoader {

	private final Logger LOGGER = Logger.getLogger(FileLoader.class);
	
	/**
	 * Read in original routing table
	 * @param filePath
	 * @return
	 * @throws IOException 
	 * @throws NumberFormatException 
	 */
	public ArrayList<ArrayList<Double>> readFile(String filePath) throws NumberFormatException, IOException {
		if (filePath == null) {
			LOGGER.info("Invalid file path.");
			return null;
		}
		
		BufferedReader br = null;
		ArrayList<ArrayList<Double>> result = new ArrayList<ArrayList<Double>>();
		try {
			String line;
			ArrayList<Double> distances;
			br = new BufferedReader(new FileReader(filePath));
			while ((line=br.readLine()) != null) {
				distances = new ArrayList<Double>();
				String[] dis = line.split("[\\s]+");
				for(String s:dis) {
					distances.add(Double.parseDouble(s));
				}
				result.add(distances);				
			}
		}finally {
			try {
				if (br != null)
					br.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return result;
	}
	
}
