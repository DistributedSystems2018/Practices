package main.java.application;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import main.java.models.Human;

public class Server {

	private ServerSocket serverSock;
	private BlockingDeque<Human> players;

	public Server(int port) {
		try {
			serverSock = new ServerSocket(port);
			players = new LinkedBlockingDeque<>();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void waitForPlayers() {
		int playerCount = 2;
		int curPlayerIndex = 1;
		while (players.size() < playerCount) {
			try {
				Socket sock = serverSock.accept();
				players.add(new Human(curPlayerIndex++, sock));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
