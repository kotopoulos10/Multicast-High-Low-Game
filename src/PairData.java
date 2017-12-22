import java.io.Serializable;

public class PairData implements Serializable{
	
	GameData game;
	ChatData chat;
	
	public PairData(int index){
		game=new GameData(index);
		chat=new ChatData(index); 
	}
}
