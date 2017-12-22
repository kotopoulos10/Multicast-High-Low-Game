
public class readKeyWriteSocketGameThread implements Runnable{

	public PlayerUtilities playerUtilities;
	public int player;
	public int count;
	int number; 
	
	public readKeyWriteSocketGameThread(PlayerUtilities playerUtilities, int player){
		this.playerUtilities=playerUtilities;
		this.player=player;
		number=playerUtilities.playerNum; 
	}
	
	public void run(){
		String fromKeyboard = null;
		String message = null;
		try {
			if(player==1){
				System.out.println("Waiting for a client to connect");
			}
			
			while (!(fromKeyboard = playerUtilities.readKeyboard()).equalsIgnoreCase("bye") ) {
				playerUtilities.writeToSocket(playerUtilities.getName() + ":" + fromKeyboard);
			}
			System.out.println("END GAME Read Thread"); 

		} catch (Exception E) {
		}
	}
}
