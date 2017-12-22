import java.io.Serializable;

public class GameData implements Serializable{

	int players;
	int index;
	String IpAddress;
	int port;
	
	public GameData(int index){
		this.players=0; 
		this.index=index; 
		this.IpAddress="225.0.0."+(index+1);
		this.port=4540+index; 
	}
}
