package main.java;

public class Forwarder implements Runnable {

	private String name;
	private Client fromClient;
	private Client toClient;
	private boolean isStopped = false;

	public Forwarder(Client from, Client to) {
		fromClient = from;
		toClient = to;
	}

	@Override
	public void run() {
		name = fromClient.readMsg();
		while (!isStopped) {
			String toForward = fromClient.readMsg();
			toClient.writeMessage(name + ":" + toForward);
		}

	}

	public void setOut(Client out) {
		fromClient = out;
	}

	public String getName() {
		return name;
	}

}
