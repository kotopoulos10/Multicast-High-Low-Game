
public class readKeyWriteSocketChatThread implements Runnable {

	public ChatterUtilities chatUtilities; 
	public UDPClientTalker talker; 
	public readGameWriteTerminalChatThread readGame;
	public readChatWriteTerminalChatThread readChat;
	
	public readKeyWriteSocketChatThread(ChatterUtilities chatUtilities, UDPClientTalker talker, readGameWriteTerminalChatThread readGame, readChatWriteTerminalChatThread readChat){
		this.chatUtilities=chatUtilities;
		this.talker=talker;
		this.readGame=readGame;
		this.readChat=readChat;
	}
	
	public void run(){
		String fromKeyboard = null;
		String message = null;
		boolean change=false; 
		try {
			while (!(fromKeyboard = chatUtilities.readKeyboard()).equalsIgnoreCase("bye") ) {
				//System.out.println(Thread.currentThread().getName() + " sending");
				message = chatUtilities.getName() + ": " + fromKeyboard;
				if(fromKeyboard.contains("@CHANGE")){
					break;
////					System.out.println("CUT DOWN:" + fromKeyboard.substring(fromKeyboard.indexOf('E'))); 
//					int index=Integer.parseInt(fromKeyboard.substring(fromKeyboard.indexOf('E')+1));
////					System.out.println("THE NEW INDEX " + index); 
//					chatUtilities.changeGroup(new PairData(index));
//					chatUtilities.readChatThread.chatUtilities.changeGroup(new PairData(index));
//					chatUtilities.readGameThread.chatUtilities.changeGroup(new PairData(index));
//					readGame.chatUtilities.changeGroup(new PairData(index));
//					readChat.chatUtilities.changeGroup(new PairData(index));
//					System.out.println("Done changing");
				}
//				System.out.println(chatUtilities.getName() + " sending");
				chatUtilities.writeToSocket(message);
				//fromSocket = peerU.readFromSocket();
				//peerU.sendToTerminal(fromSocket);
			}
			
			System.out.println("You just left the chatroom...");
		} catch (Exception E) {
		}
	}
}



////System.out.println(Thread.currentThread().getName() + " sending");
//message = chatUtilities.getName() + " sent: " + fromKeyboard;
//if(fromKeyboard.equals("@CHANGE")){
//	System.out.println("Enter the Chat Room Number you want to join (0-4):");
//	change=true; 
//}else {
//	if(change){
//		System.out.println("in the change statement trying to go into room # " + fromKeyboard);
//		chatUtilities.writeToSocket("@CHANGE"+fromKeyboard);
//		talker.data.chatRoomNum=Integer.parseInt(fromKeyboard);
//		talker.data.message="Change chatroom"; 
//		talker.sendData(talker.data);
//		System.out.println("SENDING STUFF OVER DONE READING"); 
//		talker.recieve();
//		talker.readObject();
//		//leave the old group
////		chatUtilities.leaveGroup();
//		//instansiate new object with new chatroom and game IP/Port data
//		chatUtilities.changeGroup(talker.data.pairData);
////		readGame.chatUtilities.changeGroup(talker.data.pairData);
////		readChat.chatUtilities.changeGroup(talker.data.pairData);
//		chatUtilities.setName(talker.data.name);
//		System.out.println("NEW Chat Port: "+ chatUtilities.chatPORT + "NEW Game Port: " + chatUtilities.gamePORT);
//		change=false; 
//	}
//	else{
//		System.out.println("Regular send");
//		//System.out.println(peerU.getName() + " sending");
//		chatUtilities.writeToSocket(message);
//		//fromSocket = peerU.readFromSocket();
//		//peerU.sendToTerminal(fromSocket);
//	}
//	