package main.java;

public class NameCollector implements Runnable {

	private String name;
	private Client connectedClient;
	private Client outgoingClient;
	private boolean isStopped = false;

	public NameCollector(Client client) {
		connectedClient = client;
	}

	@Override
	public void run() {
		name = connectedClient.readMsg();
		while (!isStopped) {
			String toForward = connectedClient.readMsg();
			outgoingClient.writeMessage(toForward);
		}

	}

	public void setOut(Client out) {
		outgoingClient = out;
	}

	public String getName() {
		return name;
	}

}
