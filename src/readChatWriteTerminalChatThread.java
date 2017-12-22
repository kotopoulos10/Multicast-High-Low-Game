
public class readChatWriteTerminalChatThread implements Runnable{

	public ChatterUtilities chatUtilities; 
	public readChatWriteTerminalChatThread(ChatterUtilities chatUtilities){
		this.chatUtilities=chatUtilities;
	}
	
	public void run(){
		String fromSocket = null;
		try {
			while (true) {
//				System.out.println("THREAD New Chat= " + chatUtilities.chatIPadrress+":" + chatUtilities.chatPORT + " New Game= "+ chatUtilities.gameIPadrress+":"+ chatUtilities.gamePORT);
				fromSocket = chatUtilities.readFromChatSocket();
				//System.out.println(Thread.currentThread().getName() + " receiving");
//				System.out.println(chatUtilities.getName() + " receiving from port # " + chatUtilities.chatPORT);
				chatUtilities.writeToTerminal(fromSocket); 
				
			}

		} catch (Exception E) {
		}
	}
}

//fromSocket = chatUtilities.readFromChatSocket();
//if(fromSocket.contains("@CHANGE")){
//	String newIndex=fromSocket.substring(7);
//	System.out.println("WANTING TO GO TO THE ROOM # " + newIndex); 
//	int index=Integer.parseInt(newIndex); 
//	PairData pair=new PairData(index);
//	chatUtilities.changeGroup(pair);
//	
//}else{
//	//System.out.println(Thread.currentThread().getName() + " receiving");
//	System.out.println(chatUtilities.getName() + " receiving from port # " + chatUtilities.chatPORT);
//	chatUtilities.writeToTerminal(fromSocket); 
//}

