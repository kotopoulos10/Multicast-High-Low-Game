import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Chatter {

	public static void main(String[] args) throws NumberFormatException, IOException, ClassNotFoundException, InterruptedException {
		
		if ((args.length != 2) ) { // Test for correct # of args
		      throw new IllegalArgumentException("Parameter(s): <Server> <Port>");
		}
		
		UDPClientTalker talker = new UDPClientTalker(InetAddress.getByName(args[0]), Integer.parseInt(args[1]));

		BufferedReader fromKeyboard = new BufferedReader(new InputStreamReader(System.in)); //from keyboard
	    String clientName;
	    
	    //Step 1 --> Get the users name to send over to the server
        System.out.print("Enter your name: ");
        clientName = fromKeyboard.readLine(); 
        
    	
    	while(true){
    		System.out.println("***type bye or @CHANGE if you would like to leave the room***\n"
    				+ "Enter the Chat Room Number you want to join (0-4):");
            MiddleWareData toSend = new MiddleWareData(clientName);
            toSend.chatRoomNum=Integer.parseInt(fromKeyboard.readLine()); 
            toSend.message="Requesting chatroom";
            
            //Setp 2 --> Send Data
            talker.sendData(toSend);
            talker.recieve();
            talker.printClientInfo();
        	talker.readObject();
        	MiddleWareData data = talker.data;
        	
    		System.out.println("You are chatter #" + (data.pairData.chat.chatters) + " in chat room # " + data.pairData.chat.Index); 
        	System.out.println("We are playing on IP address and port " + data.pairData.game.IpAddress + ":" + data.pairData.game.port);
        	
        	readGameWriteTerminalChatThread readGameThread;
        	readChatWriteTerminalChatThread readChatThread;
        	readKeyWriteSocketChatThread write;
        	
//    		DatagramSocket clientSocket = new DatagramSocket(); //for network
    		ChatterUtilities chatU = new ChatterUtilities(data.pairData);
    		
    		chatU.setName(data.name);
    		chatU.joinGroup();
    		
    		readGameThread = new readGameWriteTerminalChatThread(chatU); 
    		Thread readGame = new Thread(readGameThread);
    		readGame.setDaemon(true);
    		readGame.start();

    		
    		readChatThread = new readChatWriteTerminalChatThread(chatU);
    		Thread readChat = new Thread(readChatThread);
    		readChat.setDaemon(true);
    		readChat.start();
    		
    		
    		chatU.setThreads(readGameThread, readChatThread);
    		
    		write = new readKeyWriteSocketChatThread(chatU, talker, readGameThread, readChatThread); 
    		Thread TT = new Thread(write);
    		TT.setDaemon(true);
    		TT.start();
    		
    		
    		TT.join();
    		readChat.stop();
    		readGame.stop();    		
    	}
    	
	}

}
