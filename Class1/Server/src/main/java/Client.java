package main.java;

import java.util.*;
import java.util.logging.Logger;
import java.io.*;
import java.net.*;

class Client {

	public Logger logger = Logger.getLogger(this.getClass().getName());

	private Scanner reader;
	private PrintWriter inputPrinter;
	private int clientId;

	public Client(Socket clientSocket, int id) {
		try {
			reader = new Scanner(clientSocket.getInputStream());
			inputPrinter = new PrintWriter(clientSocket.getOutputStream());
			clientId = id;
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public String readMsg() {
		// Blocking request
		while (!reader.hasNext()) {
		}
		String message = reader.nextLine();
		logger.info("Received \"" + message + "\" from client");
		return message;
	}

	public void writeMessage(String message) {
		logger.info("Sending \"" + message + "\" to client");
		inputPrinter.println(message);
		inputPrinter.flush();
	}

	public void writeMessage(int message) {
		logger.info("Sending \"" + message + "\" to client");
		inputPrinter.println(message);
		inputPrinter.flush();
	}

	public void echoFunction() {
		// Receive and send back same msg
		String toEcho = readMsg();
		writeMessage(toEcho);
	}

	public void sendNtimes(String toPrint, int n) {
		String toSend = "";
		for (int i = 0; i < n; i++) {
			toSend += toPrint;
		}
		writeMessage(toSend);
	}

	public void closeClient() {
		inputPrinter.close();
		reader.close();
	}

	public int getId() {
		return clientId;
	}
}
