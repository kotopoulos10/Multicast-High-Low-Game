
public class readGameWriteTerminalGameThread implements Runnable{

	public PlayerUtilities playerUtilities;
	public HiLowData gameData;
	public int player;
	public int number=22;
	public boolean hasGuessed=false;
	
	public readGameWriteTerminalGameThread(PlayerUtilities playerUtilities, int player){
		this.playerUtilities=playerUtilities;
		this.player=player;
		number =playerUtilities.gameData.number; 
//		this.number=target;
	}
	public void run (){
		String fromSocket = null;
		try {
			while (!playerUtilities.gameData.correct) {
				fromSocket = playerUtilities.readFromGameSocket();
				playerUtilities.writeToTerminal(fromSocket);
				if(player==1){
					if(fromSocket.contains("Starting the game")){
						playerUtilities.writeToSocket(playerUtilities.getName()+ ": Guess a number between 1 and 50:");
					}else{
						int guess= Integer.parseInt(fromSocket.substring(fromSocket.indexOf(':')+1)); //(playerUtilities.getName().length())+3)); 
						if(guess>number)
							playerUtilities.writeToSocket(playerUtilities.getName()+ ":Too High");
						else if(guess<number)
							playerUtilities.writeToSocket(playerUtilities.getName()+ ":Too low");
						else{
							playerUtilities.writeToSocket(playerUtilities.getName()+ ":Correct!");
							playerUtilities.gameData.correct=true;
						}
					}
				}
				
				
				
				fromSocket = playerUtilities.readFromGameSocket();
				playerUtilities.writeToTerminal(fromSocket); 
				if(player==2){
					
					if(fromSocket.contains("Correct!")){
						playerUtilities.gameData.correct=true;
						break;
					}
				}
			}

		} catch (Exception E) {
		}
	}
}
