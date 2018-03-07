package main.java;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static final String END_TOKEN = "end";
	public static final String EXIT_TOKEN = "exit";

	public static void main(String[] args) {
		int exNum = 0;
		String chatname = "Bob"; // default name
		int giveRandom = 5;

		if (args.length > 0) {
			switch (args[0]) {
			case "-0":
				exNum = 0;
				break;
			case "-1":
				exNum = 1;
				break;
			case "-2":
				exNum = 2;
				break;
			case "-3":
				exNum = 3;
				break;
			case "-4":
				exNum = 4;
				break;
			case "-5":
				exNum = 5;
				if (args.length == 2) {
					giveRandom = Integer.parseInt(args[1]);
				}
				break;
			case "-6":
				exNum = 6;
				if (args.length == 2) {
					chatname = args[1];
				}
				break;
			default:
				System.out.println("Error understanding commandline argument, exiting...");
				return;
			}

		}
		Client client = new Client("localhost", 12345);
		// Exercise 0
		if (exNum == 0) {
			client.echoFunction("echo this back");
			return;
		}
		// Exercise 1
		if (exNum == 1) {

			client.sendMsg("4");
			String answer = client.readMsg();
			System.out.println(answer);
			return;
		}
		// Exercise 2
		if (exNum == 2) {

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

			return;
		}
		// Exercise 3
		if (exNum == 3) {
			String filename = "serverfile.txt";
			client.sendMsg(filename);
			System.out.println(client.readMsg());
		}

		// Exercise 4
		if (exNum == 4) {
			System.out.println("Current connection count: " + client.readMsg());
			return;
		}
		// Exercise 5

		if (exNum == 5) {
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

		if (exNum == 6) {
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

	}

}
