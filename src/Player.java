import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Player {
	private static final int ECHOMAX = 255; // Maximum size of echo datagram
	public static void main(String[] args) throws Exception {
		
		
		if ((args.length != 2) ) { // Test for correct # of args
		      throw new IllegalArgumentException("Parameter(s): <Server> <Port>");
		    }
		int target=-1;
		UDPClientTalker talker =new UDPClientTalker(InetAddress.getByName(args[0]), Integer.parseInt(args[1]));
		
		BufferedReader fromKeyboard = new BufferedReader(new InputStreamReader(System.in)); //from keyboard
	    String clientName;
	    
	    //Step 1 --> Get the users name to send over to the server
        System.out.print("Enter your name: ");
        clientName = fromKeyboard.readLine(); 
        MiddleWareData toSend = new MiddleWareData(clientName);
        
        //Setp 2 --> Send Data
        talker.sendData(toSend);
        talker.recieve();
        talker.printClientInfo();
    	talker.readObject();
    	MiddleWareData data = talker.data;
    	
    	System.out.println("You are player #" + (data.pairData.game.players) + " in game # " + data.pairData.game.index); 
//    	System.out.println("We are playing on IP address and port " + data.pairData.game.IpAddress + ":" + data.pairData.game.port);
    	
    	
    	readGameWriteTerminalGameThread read;
    	readKeyWriteSocketGameThread write;
    	
		
		boolean again=true;
		boolean player1;
		int playerNum;
		
		if(data.pairData.game.players==1)
			player1=true;
		else
			player1=false;
		
		while(again){
			PlayerUtilities playerU = new PlayerUtilities(data.pairData); 
			playerU.setName(data.name);
			playerU.joinGroup();
			
			if(player1){
				System.out.println("Enter a number between 1 and 100 for player 2 to guess:");
				target=Integer.parseInt(playerU.readKeyboard()); 
				playerU.gameData.number=target;
				playerNum=1;
				System.out.println("Target number set..."); 
			}
			else{
				System.out.println("Guess the number I'm thinking of between between 1 and 100: ");
				playerNum=2;
			}
			
			read = new readGameWriteTerminalGameThread(playerU, playerNum); 
			Thread T = new Thread(read);
			T.setDaemon(true);
			T.start();
			
			write = new readKeyWriteSocketGameThread(playerU, playerNum); 
			Thread TT = new Thread(write);
			TT.setDaemon(true);
			TT.start();
			
			
			T.join();
			TT.stop();
			
			System.out.println("Would you like to play again? (y/n)");
			BufferedReader stdin; /* input from keyboard */
			String agains; /* string to be sent */
			stdin = new BufferedReader(new InputStreamReader(System.in));
			agains = stdin.readLine();
			if(agains.equals("y") || agains.equals("Y") || agains.equals("yes") || agains.equals("Yes")){
				player1=!player1;
			}
			else{
				again=false;
//				System.out.println("TRYING TO LEAVE");
//				playerU.writeToSocket("I am leaving the game. See you later! (Chatters you should switch rooms.)");
				playerU.leaveGroup();
			}
		}
		
		
//		TT.join();
//		playerU.leaveGroup();
		
		data.gameOver=true;
		data.message="Game over";
		talker.sendData(data);
		System.out.println("Thanks for playing leaving the game room..."); 
		talker.close();
	}

}
