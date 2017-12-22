import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ChatterUtilities {

	
	private MulticastSocket gameSocket;
	public int gamePORT;
	private InetAddress gameGroup; 
	public String gameIPadrress;
	
	private MulticastSocket chatSocket;
	public int chatPORT;
	private InetAddress chatGroup; 
	public String chatIPadrress;
	
	public readGameWriteTerminalChatThread readGameThread;
	public readChatWriteTerminalChatThread readChatThread;
	
	private int ttl = 64; /* time to live */
	private String name;
	
	public ChatterUtilities(PairData data) throws IOException{
		this.gameIPadrress =data.game.IpAddress; 
		this.gamePORT = data.game.port;
		/* instantiate a MulticastSocket */
		System.setProperty("java.net.preferIPv4Stack", "true");  //IMPORTANT IMPORTANT IMPORTANT
		this.gameSocket = new MulticastSocket(this.gamePORT);
		/* set the time to live */
		gameSocket.setTimeToLive(ttl);
		this.gameGroup = InetAddress.getByName(gameIPadrress);
		
		//Set up the Chat Multicast socket
		this.chatIPadrress =data.chat.IpAddress;
		this.chatPORT = data.chat.port;
		/* instantiate a MulticastSocket */
		System.setProperty("java.net.preferIPv4Stack", "true");  //IMPORTANT IMPORTANT IMPORTANT
		this.chatSocket = new MulticastSocket(this.chatPORT);
		/* set the time to live */
		chatSocket.setTimeToLive(ttl);
		this.chatGroup = InetAddress.getByName(chatIPadrress);
	}
	
	public void setThreads(readGameWriteTerminalChatThread readGameThread,
	readChatWriteTerminalChatThread readChatThread){
		this.readGameThread=readGameThread;
		this.readChatThread=readChatThread; 
	}
	
	//Multicast Methods
	public void leaveGroup() throws IOException{
		gameSocket.leaveGroup(gameGroup);
		gameSocket.close();
		
		chatSocket.leaveGroup(chatGroup);
		chatSocket.close();
	}
	
	public void changeGroup(PairData data) throws IOException{
		this.leaveGroup();
		
		
		this.gameIPadrress =data.game.IpAddress; 
		this.gamePORT = data.game.port;
		/* instantiate a MulticastSocket */
		System.setProperty("java.net.preferIPv4Stack", "true");  //IMPORTANT IMPORTANT IMPORTANT
		this.gameSocket = new MulticastSocket(this.gamePORT);
		/* set the time to live */
		gameSocket.setTimeToLive(ttl);
		this.gameGroup = InetAddress.getByName(gameIPadrress);
		
		//Set up the Chat Multicast socket
		this.chatIPadrress =data.chat.IpAddress;
		this.chatPORT = data.chat.port;
		/* instantiate a MulticastSocket */
		System.setProperty("java.net.preferIPv4Stack", "true");  //IMPORTANT IMPORTANT IMPORTANT
		this.chatSocket = new MulticastSocket(this.chatPORT);
		/* set the time to live */
		chatSocket.setTimeToLive(ttl);
		this.chatGroup = InetAddress.getByName(chatIPadrress);
		
		System.out.println("New Chat= " + this.chatIPadrress+":" + this.chatPORT + " New Game= "+ this.gameIPadrress+":"+ this.gamePORT);
		
		this.joinGroup();
	}
	
	public void joinGroup() throws IOException{
		//IMPORTANT IMPORTANT IMPORTANT
		//the following two lines are needed for MACs and BC
		InetAddress IP=InetAddress.getLocalHost();  //now this is what is called the nat address, and should work for us since we are only talking to machines on our segment
		gameSocket.setInterface(IP);
		gameGroup = InetAddress.getByName(gameIPadrress);
		gameSocket.joinGroup(gameGroup);
		
		chatSocket.setInterface(IP);
		chatGroup = InetAddress.getByName(chatIPadrress);
		chatSocket.joinGroup(chatGroup);
	}
	
	public void setName(String screenName){
		name=screenName;
	}
	
	public String getName(){
		return name;
	}
	
	// Read Methods
	public String readKeyboard() throws IOException{
		BufferedReader stdin; /* input from keyboard */
		String sendString; /* string to be sent */
		stdin = new BufferedReader(new InputStreamReader(System.in));
//		System.out.println("Enter a guess: ");
		sendString = stdin.readLine();
		return sendString;
	}
	
	public String readFromGameSocket() throws IOException{
		String gameSocketString = null; /* string from socket */
		// get their responses!
		//byte[] buf is a byte array from the socket
		byte[] gameBuf = new byte[1000];
		DatagramPacket gameRecv = new DatagramPacket(gameBuf, gameBuf.length);
		gameSocket.receive(gameRecv);
		gameSocketString = new String(gameRecv.getData(), 0, gameRecv.getLength());
		return 	gameSocketString;	
	}
	
	public String readFromChatSocket() throws IOException{
		String chatSocketString = null; /* string from socket */
		// get their responses!
		//byte[] buf is a byte array from the socket
		byte[] chatBuf = new byte[1000];
		DatagramPacket gameRecv = new DatagramPacket(chatBuf, chatBuf.length);
		chatSocket.receive(gameRecv);
		chatSocketString = new String(gameRecv.getData(), 0, gameRecv.getLength());
		return 	chatSocketString;	
	}
	
	
	//Write Methods
	public void writeToTerminal(String msg){
		//System.out.println("Multicast text: " + msg);
		System.out.println(msg);
	}
	
	
	//writing to the chat socket
	public void writeToSocket(String msg) throws IOException{
		/* remember to convert keyboard input (in msg) to bytes */
		 DatagramPacket sendPacket = new DatagramPacket(msg.getBytes(), msg.length(),chatGroup, chatPORT);
		 chatSocket.send(sendPacket);
	}
}
