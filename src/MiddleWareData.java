import java.io.Serializable;

public class MiddleWareData implements Serializable{
	
	public String name;
	public String message;
	public PairData pairData;
	public int playerNum;
	public boolean gameOver=false;
	public int chatRoomNum=-1;
	
	public MiddleWareData(String name){
		this.name=name; 
		this.message="Starting game"; 
	}
	
	public void setPairData(PairData pair){
		this.pairData=pair;
	}
	
	public void setPlayerNum(int num){
		this.playerNum=num; 
	}
	
	public String toString(){
		return "There are " + pairData.game.players + " players in this game right now.";
	}
	

}
