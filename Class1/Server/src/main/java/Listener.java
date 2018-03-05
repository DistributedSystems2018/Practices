package main.java;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import javafx.util.Pair;

public class Listener implements Runnable {

	private Client client;
	public static BlockingDeque<Pair<Integer, String>> messageBox = new LinkedBlockingDeque<>();
	
	public Listener(Client cli) {
		client = cli;
	}

	@Override
	public void run() {
		int id = client.getId();
		String name = client.readMsg();
		System.out.println("Name of client: " + name);
		while (!Thread.currentThread().isInterrupted()) {
			String incMsg = client.readMsg();
			Pair<Integer, String> message = new Pair<Integer, String>(id, incMsg);
			messageBox.add(message);
		}
	}

}
