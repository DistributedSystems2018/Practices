package main.java;

import javafx.util.Pair;

public class Sharer implements Runnable {

	private Server server;

	public Sharer(Server serv) {
		server = serv;
	}

	@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			if (Listener.messageBox.size() > 0) {
				Pair<Integer, String> oldestMessage = Listener.messageBox.remove();
				server.broadcastMessage(oldestMessage.getKey(), oldestMessage.getValue());
			}
		}
	}

}
