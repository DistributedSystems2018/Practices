package main.java;

import java.util.ArrayList;

public class Main {

	public static final String END_TOKEN = "end";
	public static final int CLIENT_COUNTER = 5;

	public static void main(String[] args) {
		int exNum = 0;
		boolean extended = false;
		boolean manyClients = false;

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
			case "-2e":
				exNum = 2;
				extended = true;
				break;
			case "-3":
				exNum = 3;
				break;
			case "-4":
				exNum = 4;
				break;
			case "-5":
				exNum = 5;
				break;
			case "-6":
				exNum = 6;
				break;
			case "-6e":
				exNum = 6;
				manyClients = true;
				break;
			default:
				System.out.println("Error understanding commandline argument, exiting...");
				return;
			}

		}

		String tmp;
		Server server = new Server(12345);

		if (exNum == 0) {
			server.acceptClient();
			Client client = server.getClient(0);
			client.echoFunction();
			return;
		}
		if (exNum == 1) {
			server.acceptClient();
			Client client = server.getClient(0);
			String manyPrints = "ThisManyTimes";
			tmp = client.readMsg();
			int n = Integer.parseInt(tmp);
			client.sendNtimes(manyPrints, n);
			return;
		}
		if (exNum == 2) {
			server.acceptClient();
			Client client = server.getClient(0);
			boolean happened = false;
			while (extended || !happened) {
				if (extended && server.getClientCount() == 0) {
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

				if (extended) {
					server.removeClient(client);
				} else {
					happened = true;
				}
			}
			return;
		}
		if (exNum == 3) {
			server.acceptClient();
			Client client = server.getClient(0);
			String filename = client.readMsg();
			FileIO serverFile = new FileIO("main/resources/" + filename);
			if (serverFile.exists()) {
				String toSend = serverFile.getFileContent();
				client.writeMessage(toSend);
			} else {
				client.writeMessage("ERROR: Could not find file");
			}
			return;
		}
		if (exNum == 4) {
			while (server.totalConnections < CLIENT_COUNTER) {
				server.acceptClient();
				// This returns newest client in queue
				Client newestClient = server.getClient(server.getClientCount() - 1);
				newestClient.writeMessage(server.totalConnections);
				server.removeClient(newestClient);
			}
			return;
		}
		if (exNum == 5) {
			while (server.getSum() < 100) {
				server.acceptClient();
				Client client = server.getClient(0);
				String number;
				while (!(number = client.readMsg()).equals("exit")) {
					server.addToInternal(number);
				}
				server.removeClient(client);
				System.out.println("Current sum : " + server.getSum());
			}
			return;
		}
		if (exNum == 6 && !manyClients) {
			while (server.getClientCount() < 2) {
				server.acceptClient();
			}
			Client first = server.getClient(0);
			Client second = server.getClient(1);

			Forwarder fw1 = new Forwarder(first, second);
			Forwarder fw2 = new Forwarder(second, first);

			Thread t1 = new Thread(fw1);
			Thread t2 = new Thread(fw2);
			t1.start();
			t2.start();
			try {
				System.out.println("Sleeping thread while clients talk");
				Thread.sleep(120000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		if (exNum == 6 && manyClients) {
			// client number
			int n = 5;
			System.out.println("Waiting until " + n + " clients has connected");
			while (server.getClientCount() < n) {
				server.acceptClient();
			}

			for (Client connClient : server.getClientList()) {
				Listener list = new Listener(connClient);
				Thread clientListener = new Thread(list);
				clientListener.start();
			}

			Sharer sharer = new Sharer(server);
			Thread broadcaster = new Thread(sharer);
			broadcaster.start();
			try {
				System.out.println("Started multi client broadcast");
				Thread.sleep(120000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
