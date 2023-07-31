package Game;

public class Hosts extends Person {
	
	//There was an error if there were no constructors
	  public Hosts(String firstName) {
	        super(firstName);
	    }
	  
	public void randomizeNum() {
        Numbers numbers = new Numbers();
        numbers.generateNumber();
    }
}