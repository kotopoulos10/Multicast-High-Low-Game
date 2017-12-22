import java.util.ArrayList;

public class MiddleWareGameController {

	ArrayList<PairData> games; 
	
	public MiddleWareGameController(ArrayList<PairData> games){
		this.games=games; 
	}
	
	public synchronized PairData nextPair(){
		for(int i=0; i<games.size(); i++)
			if(games.get(i).game.players<=1){
				games.get(i).game.players++; 
				return games.get(i); 
			}
		return games.get(4); 
	}
	
	public PairData getChatRoom(int index){
		PairData pair=games.get(index);
		pair.chat.chatters++;
		return pair;
	}
	public synchronized void updateGames(PairData game, int index){
		games.set(index, game); 
	}
	
	public synchronized void updateGame(PairData game){
	}
}
