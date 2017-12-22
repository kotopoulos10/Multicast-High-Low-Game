import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

public class PlayerUtilities extends GameUtilities{

	
	private MulticastSocket gameSocket;
	private int gamePORT;
	private InetAddress gameGroup; 
	private String gameIPadrress;
	private int ttl = 64; /* time to live */
	private String name;
	public HiLowData gameData;
	public int playerNum=0;
	
	public PlayerUtilities(PairData data) throws IOException{
		//Set up the game Multicast socket
		this.gameIPadrress =data.game.IpAddress; 
		this.gamePORT = data.game.port;
		/* instantiate a MulticastSocket */
		System.setProperty("java.net.preferIPv4Stack", "true");  //IMPORTANT IMPORTANT IMPORTANT
		this.gameSocket = new MulticastSocket(this.gamePORT);
		/* set the time to live */
		gameSocket.setTimeToLive(ttl);
		this.gameGroup = InetAddress.getByName(gameIPadrress);
		playerNum=data.game.players; 
		gameData=new HiLowData();
	}
	
	
	//Multicast Methods
	public void leaveGroup() throws IOException{
		gameSocket.leaveGroup(gameGroup);
		gameSocket.close();
	}
	
	public void joinGroup() throws IOException{
		InetAddress IP=InetAddress.getLocalHost();  //now this is what is called the nat address, and should work for us since we are only talking to machines on our segment
		gameSocket.setInterface(IP);
		gameGroup = InetAddress.getByName(gameIPadrress);
		gameSocket.joinGroup(gameGroup);
	}
	
	public void setName(String screenName){
		name= screenName;
	}
	
	public String getName(){
		return name; 
	}
	
	// Read Methods
	public String readKeyboard() throws IOException{
		BufferedReader stdin; /* input from keyboard */
		String sendString; /* string to be sent */
		stdin = new BufferedReader(new InputStreamReader(System.in));
//		if(this.playerNum==1)
//		System.out.println("Enter a guess: ");
		sendString = stdin.readLine();
		return sendString;
	}
	
	public String readFromGameSocket() throws IOException, ClassNotFoundException{
		String gameSocketString = null; /* string from socket */
		// get their responses!
		//byte[] buf is a byte array from the socket
		byte[] gameBuf = new byte[1000];
		DatagramPacket gameRecv = new DatagramPacket(gameBuf, gameBuf.length);
		gameSocket.receive(gameRecv);
//		ByteArrayInputStream byteStream2 = new ByteArrayInputStream(gameBuf);
//		ObjectInputStream is = new ObjectInputStream(new BufferedInputStream(byteStream2));
//		gameData=(HiLowData) is.readObject(); 
//		return gameData;
		gameSocketString = new String(gameRecv.getData(), 0, gameRecv.getLength());
		return 	gameSocketString;	
	}
	
	
	//Write Methods
	public void writeToTerminal(String msg){
		//System.out.println("Multicast text: " + msg);
		System.out.println(msg);
	}
	
	//Writing to the game socket
	
	public void writeToSocket(String msg) throws IOException{
		DatagramPacket gameSendPacket = new DatagramPacket(msg.getBytes(), msg.length(),gameGroup, gamePORT);
		gameSocket.send(gameSendPacket);
	}
	
	public void writeToSocket(HiLowData gameData) throws IOException{
//		
		
		
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream(1024);
    	ObjectOutputStream os = new ObjectOutputStream(new BufferedOutputStream(byteStream));
    	os.flush();
    	os.writeObject(gameData);
    	os.flush();
        //retrieves byte array
    	byte[] sendBuf = byteStream.toByteArray();  //send an Object
    	DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length, gameGroup, gamePORT);
    	gameSocket.send(sendPacket);
	}
}
