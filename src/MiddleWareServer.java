import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MiddleWareServer {
	
	private static final int ECHOMAX = 255; // Maximum size of echo datagram
    public static void main(String[] args) throws IOException, ClassNotFoundException {
    	
//      Instansiate ArrayList for all 5 of the games
    	ArrayList<PairData> games= new ArrayList<PairData>(); 
    	for(int i=0; i<5; i++)
    		games.add(new PairData(i)); 
    	MiddleWareGameController mwgc= new MiddleWareGameController(games);
    	
    	UDPServerTalker talker=new UDPServerTalker(Integer.parseInt(args[0]));
        System.out.println("This is the server used by clients to connect to a specific multicast game or chat room. "
        		+ "\nI am waiting...... ");
        while (true) { // Run forever, receiving and echoing datagrams from any client
//        	System.out.println("Waiting to recieve..."); 
        	talker.recieve();
        	talker.printClientInfo();
        	talker.readObject();
        	
        	//Request Chatroom
        	if(talker.data.message.equals("Requesting chatroom")){
        		talker.data.pairData=mwgc.getChatRoom(talker.data.chatRoomNum);
        		talker.send(talker.data); 
        	}
        	else if(talker.data.message.equals("Change chatroom")){
        		games.get(talker.data.pairData.game.index).chat.chatters--;
        		talker.data.pairData=mwgc.getChatRoom(talker.data.chatRoomNum);
        		talker.send(talker.data);
        	}
        	else{
        		//Telling the server that the game has ended
        		if(talker.data.gameOver){
            		System.out.println("Changing the game data!"); 
            		games.get(talker.data.pairData.game.index).game.players=0;
            		games.get(talker.data.pairData.game.index).chat.chatters=0;
            		//make sure the game gets updated here
            	}
        		//Asking the server to dispatch a new game to the player client
            	else{
            		talker.printData();
                	talker.data.setPairData(mwgc.nextPair());
                	talker.send(talker.data); 
            	}
        	}
        	
        	
        	
        	for(int i=0; i<games.size(); i++)
        		System.out.println("Game #" + games.get(i).game.index + " has " + games.get(i).game.players + " players. It has " + (games.get(i).chat.chatters) + " people in the chatroom...");
        }
    	
    }
}
