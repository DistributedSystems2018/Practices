import java.io.*;

public class Person implements Serializable{
	private String name;

	public Person(){

	}

	// My constructor with all arguments
	public Person(String name) {
		this.name = name;
	}

  // Getters and Setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
