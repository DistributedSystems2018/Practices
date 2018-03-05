package main.java;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static final String END_TOKEN = "end";
	public static final String EXIT_TOKEN = "exit";

	public static void main(String[] args) {
		boolean usingExtended = false;
		boolean connectOnly = false;
		boolean giveNumbers = false;
		boolean chatMode = false;
		String chatname = "Bob"; // default name
		int giveRandom = 5;

		if (args.length > 0) {
			if (args[0].equals("-2e")) {
				usingExtended = true;
			}

			if (args[0].equals("-4")) {
				connectOnly = true;
			}

			if (args[0].equals("-5")) {
				giveNumbers = true;
				if (args.length > 1) {
					giveRandom = Integer.parseInt(args[1]);
				}
			}

			if (args[0].equals("-6")) {
				chatMode = true;
				if (args.length > 1) {
					chatname = args[1];
				}
			}

		}
		Client client = new Client("localhost", 12345);

		// Exercise 4
		if (connectOnly) {
			System.out.println("Current connection count: " + client.readMsg());
			return;
		}
		// Exercise 5

		if (giveNumbers) {
			System.out.println("Generating " + giveRandom + " random numbers and sending to server.");
			for (int i = 0; i < giveRandom; i++) {
				// some number between 0 and 20
				int num = (int) (Math.random() * 21);
				client.sendMsg(num);
			}
			client.sendMsg(EXIT_TOKEN);
			return;
		}

		// Exercise 6

		if (chatMode) {
			boolean isStopped = false;
			client.sendMsg(chatname);
			// One thread for reading
			new Thread() {
				@Override
				public void run() {
					while (!Thread.currentThread().isInterrupted()) {
						String msg = client.readMsg();
						System.out.println("Got message:");
						System.out.println(msg);
					}
				}
			}.start();
			// One thread for writing
			new Thread() {
				@Override
				public void run() {
					Scanner sc = new Scanner(System.in);
					while (!Thread.currentThread().isInterrupted()) {
						String outgoing = sc.nextLine();
						client.sendMsg(outgoing);
						System.out.println("Sent message:");
						System.out.println(outgoing);
					}
					sc.close();
				}
			}.start();

			while (!isStopped) {
				try {
					System.out.println("Conversation open for 120 seconds.");
					Thread.sleep(120000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			return;
		}

		if (!usingExtended) {
			// Exercise 0
			client.echoFunction("echo this back");
			// Exercise 1
			client.sendMsg("4");
			String answer = client.readMsg();
			System.out.println(answer);

		}
		// Exercise 2
		FileIO fileHandler = new FileIO("main/resources/numberfile.txt");
		ArrayList<Integer> fileNumbers = (ArrayList<Integer>) fileHandler.readNumbers();

		for (int number : fileNumbers) {
			client.sendMsg(number);
		}
		client.sendMsg(END_TOKEN);

		for (int i = 0; i < fileNumbers.size(); i++) {
			String recv = client.readMsg();
			System.out.println(recv);
		}

		// Exercise 3
		String filename = "serverfile.txt";
		client.sendMsg(filename);
		System.out.println(client.readMsg());
		if (usingExtended) {
			return;
		}

	}

}
