
public class HiLowData {

	public int number;
	public int guess;
	public int numGuesses=0; 
	public boolean correct;
	public String name;
	public String message;
	
	public HiLowData(String n, int number, String message){
		this.name = n;
		this.number = number;
		this.message=message;
	}
	
	public HiLowData(){
		this.name="";
		this.correct=false;
	}
	
	public void guess(){
		numGuesses++;
	}
}
