package main.java;

import java.util.Iterator;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Logger;
import java.io.*;
import java.net.*;

class Server {

	private ServerSocket clientListener;
	private BlockingDeque<Client> clientList;
	public Logger logger = Logger.getLogger(this.getClass().getName());
	public int totalConnections = 0;
	private int internalSum = 0;

	public Server(int port) {
		try {
			clientListener = new ServerSocket(port);
			clientList = new LinkedBlockingDeque<>();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void acceptClient() {
		try {
			logger.info("Started listening for client");
			Socket newClient = clientListener.accept();
			clientList.add(new Client(newClient));
			logger.info("Client connected from " + newClient.getInetAddress());
			totalConnections++;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getMessageFromClient(int index) {
		if (index > clientList.size() - 1 || index < 0) {
			throw new RuntimeException("Client list index out of bounds");
		}

		Iterator<Client> clientIterator = clientList.iterator();
		int i = 0;
		Client toRead = null;

		while (clientIterator.hasNext()) {
			toRead = clientIterator.next();
			if (i == index) {
				break;
			}
			i++;
		}

		if (toRead != null) {
			return toRead.readMsg();
		} else {
			logger.severe("Error accessing client in queue");
			return null;
		}

	}

	public void sendMessageToClient(int index, String message) {
		if (index > clientList.size() - 1 || index < 0) {
			throw new RuntimeException("Client list index out of bounds");
		}

		Iterator<Client> clientIterator = clientList.iterator();
		int i = 0;
		Client toSend = null;

		while (clientIterator.hasNext()) {
			toSend = clientIterator.next();
			if (i == index) {
				break;
			}
			i++;
		}

		if (toSend != null) {
			toSend.writeMessage(message);
		} else {
			logger.severe("Error accessing client in queue");
			return;
		}

	}

	public void echoClient(int index) {
		if (index > clientList.size() - 1 || index < 0) {
			throw new RuntimeException("Client list index out of bounds");
		}

		Iterator<Client> clientIterator = clientList.iterator();
		int i = 0;
		Client toUse = null;

		while (clientIterator.hasNext()) {
			toUse = clientIterator.next();
			if (i == index) {
				break;
			}
			i++;
		}

		if (toUse != null) {
			toUse.echoFunction();
		} else {
			logger.severe("Error accessing client in queue");
			return;
		}
	}

	public void broadcastMessage(String message) {
		for (Client client : clientList) {
			client.writeMessage(message);
		}
	}

	public Client getClient(int index) {
		Iterator<Client> clientIterator = clientList.iterator();
		int i = 0;
		Client ret = null;

		while (clientIterator.hasNext()) {
			ret = clientIterator.next();
			if (i == index) {
				break;
			}
			i++;
		}
		return ret;
	}

	public void removeClient(Client client) {
		client.closeClient();
		logger.info("Removing client from queue");
		clientList.remove(client);
	}

	public int getClientCount() {
		return clientList.size();
	}

	public void addToInternal(String num) {
		try {
			internalSum += Integer.parseInt(num);
		} catch (NumberFormatException e) {
			System.err.println("ERROR: expected integer");
		}
	}

	public int getSum() {
		return internalSum;
	}
	
}
