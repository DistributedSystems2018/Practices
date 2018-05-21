package main.java.application;

import java.util.Optional;

import main.java.models.Human;

public class Main {

	public static void main(String[] args) {
			//Game game = new Game();
		Human h = new Human();
		
		Optional<int[]> opt = h.parseMove("1 2");
		if(opt.isPresent()) {
			System.out.println(opt.get()[0]);
			System.out.println(opt.get()[1]);
		} else {
			System.out.println("No opt present");
		}
	}

}
