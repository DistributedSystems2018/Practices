import java.io.*;

public class Person implements Serializable{
	private String name;
	private String familyName;
	private String message;

	public Person(){

	}

	// My constructor with all arguments
	public Person(String name, String familyName ) {
		this.name = name;
		this.familyName = familyName;
	}

  // Getters and Setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setFamilyName(String name) {
		this.familyName = familyName;
	}

	public String getFamilyName(){
			return this.familyName;
	}

	public String getMessage(){
			return this.message;
	}

}
