package main.java;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileIO {

	private BufferedReader reader;
	private boolean exists;

	public FileIO(String filename) {
		try {
			reader = new BufferedReader(new FileReader(filename));
			exists = true;
		} catch (FileNotFoundException e) {
			System.err.println("ERROR: Cannot find file");
			exists = false;
		}
	}

	public boolean exists() {
		return exists;
	}

	public String getFileContent() {
		String output = "";
		String line;

		try {
			while ((line = reader.readLine()) != null) {
				output += line + " ";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return output;
	}

}
