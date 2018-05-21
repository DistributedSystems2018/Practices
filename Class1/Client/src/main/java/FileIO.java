package main.java;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileIO {

	private BufferedReader reader;

	public FileIO(String filename) {
		try {
			reader = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	List<Integer> readNumbers() {
		String line;
		ArrayList<Integer> list = new ArrayList<>();
		try {
			while ((line = reader.readLine()) != null) {
				String[] tokens = line.split(",");
				for (String token : tokens) {
					list.add(Integer.parseInt(token));
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

}
