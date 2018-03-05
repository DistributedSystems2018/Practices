package main.java;

import java.util.ArrayList;

public class Main {

	public static final String END_TOKEN = "end";
	public static final int CLIENT_COUNTER = 5;

	public static void main(String[] args) {
		boolean extendedVersion = false;
		String tmp = "";
		if (args.length > 0) {
			if (args[0].equals("-2e")) {
				extendedVersion = true;
			}
		}

		Server server = new Server(12345);
		server.acceptClient();

		Client client = server.getClient(0);

		if (!extendedVersion) {
			String manyPrints = "ThisManyTimes";

			// Exercise 0
			client.echoFunction();
			// Exercise 1
			tmp = client.readMsg();
			int n = Integer.parseInt(tmp);
			client.sendNtimes(manyPrints, n);
		}
		// Exercise 2
		// For infinite clients set extendedVersion to true

		boolean happened = false;

		while (extendedVersion || !happened) {
			if (extendedVersion && server.getClientCount() < 1) {
				server.acceptClient();
				client = server.getClient(0);
			}

			ArrayList<Integer> numbersToHandle = new ArrayList<>();

			while (!(tmp = client.readMsg()).equals(END_TOKEN)) {
				try {
					numbersToHandle.add(Integer.parseInt(tmp));
				} catch (NumberFormatException e) {
					System.err.println("ERROR: expected integer");
				}
			}

			for (int num : numbersToHandle) {
				int tmpNumber = Util.numberFunction(num);
				client.writeMessage(tmpNumber);
			}

			if (extendedVersion) {
				server.removeClient(client);
			} else {
				happened = true;
			}
		}
		// Exercise 3
		String filename = client.readMsg();
		FileIO serverFile = new FileIO("main/resources/" + filename);
		if (serverFile.exists()) {
			String toSend = serverFile.getFileContent();
			client.writeMessage(toSend);
		} else {
			client.writeMessage("ERROR: Could not find file");
		}

		// Exercise 4
		// Removing all clients before initiating exercise 4
		if (server.getClientCount() != 1) {
			System.out.println("Only expecting one client online at this time");

			return;
		} else {
			server.removeClient(server.getClient(0));
		}

		while (server.totalConnections < CLIENT_COUNTER) {
			server.acceptClient();
			// This returns newest client in queue
			Client newestClient = server.getClient(server.getClientCount() - 1);
			newestClient.writeMessage(server.totalConnections);
			server.removeClient(newestClient);
		}

		// Exercise 5
		// Assuming no active connection at this time
		assert (server.getClientCount() == 0);
		while (server.getSum() < 100) {
			server.acceptClient();
			client = server.getClient(0);
			String number;
			while (!(number = client.readMsg()).equals("exit")) {
				server.addToInternal(number);
			}
			server.removeClient(client);
			System.out.println("Current sum : " + server.getSum());
		}

		// Exercise 6
		while (server.getClientCount() < 2) {
			server.acceptClient();
		}
		Client first = server.getClient(0);
		Client second = server.getClient(1);
		String nameFirst;
		String nameSecond;
		NameCollector nameCollectorFirst = new NameCollector(first);
		NameCollector nameCollectorSecond = new NameCollector(second);

		Thread t1 = new Thread(nameCollectorFirst);
		Thread t2 = new Thread(nameCollectorSecond);
		t1.start();
		t2.start();
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// Application will wait until both threads have collected name from client

		System.out.println("First name: " + nameCollectorFirst.getName());
		System.out.println("Second name: " + nameCollectorSecond.getName());
		
	}
}
