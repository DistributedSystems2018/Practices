package main.java.models;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Logger;

public class Human extends Player {

	Logger logger = Logger.getLogger(this.getClass().getName());

	public Scanner input;
	private PrintWriter out;

	public Human(int player, Socket socket) {
		super(player);
		this.player = player;
		try {
			this.out = new PrintWriter(socket.getOutputStream());
			this.input = new Scanner(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Player 'Human' created!");
	}

	public Human() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void play(Board board) {
		Try(board);
		board.setPosition(attempt, player);
	}

	@Override
	public void Try(Board board) {
		int[] attempt;
		do {
			attempt = nextMove();
			if (!checkTry(attempt, board)) {
				logger.info("Place already marked. Try another.");
				sendMsg("ERROR: 1, target already marked");
			}
		} while (!checkTry(attempt, board));
	}

	public String readMsg() {
		while (!input.hasNext()) {

		}
		String in = input.nextLine();
		logger.info("From client: " + in);
		return in;
	}

	public void sendMsg(String msg) {
		logger.info("To client: " + msg);
		out.println(msg);
		out.flush();
	}

	public int[] nextMove() {
		String move = readMsg();
		Optional<int[]> boardCoords = Optional.empty();
		boolean acceptedMove = false;
		while (!acceptedMove) {
			boardCoords = parseMove(move);

			if (!boardCoords.isPresent()) {
				logger.severe("Got empty coords!");
				sendMsg("ERROR: 2, bad coordinates");
				logger.info("Waiting for next coordinate msg");
				move = readMsg();
			} else {
				acceptedMove = true;
			}
		}

		return boardCoords.get();
	}

	public Optional<int[]> parseMove(String strMove) {
		// accept move in format (x,y) || x,y || x y
		int[] move = new int[2];
		if (strMove.contains(",")) {
			if (strMove.contains("(")) {
				strMove = strMove.replaceAll("\\(", "");
				strMove = strMove.replaceAll("\\)", "");
			}
			String[] tokens = strMove.split(",");

			if (tokens.length != 2) {
				logger.severe("Coordinates not following format! Try again...");
				return Optional.empty();
			} else {
				move[0] = Integer.parseInt(tokens[0]);
				move[1] = Integer.parseInt(tokens[1]);
				return Optional.of(move);
			}
		} else {
			String[] tokens = strMove.split(" ");
			move[0] = Integer.parseInt(tokens[0]);
			move[1] = Integer.parseInt(tokens[1]);
			return Optional.of(move);
		}

	}

}
