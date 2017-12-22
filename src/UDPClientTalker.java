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
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPClientTalker {
	private static final int ECHOMAX = 1024; // Maximum size of echo datagram
	public DatagramSocket socket; 
	public InetAddress serverAddress;
	public int servPort;
	public DatagramPacket packet; 
	public byte[] recvBuf = new byte[ECHOMAX];
	public MiddleWareData data; 
	
	public UDPClientTalker(InetAddress servAddress, int serverPort) throws SocketException{
		
		socket = new DatagramSocket(); //for network
	    serverAddress = servAddress;  // Server address
	    servPort = serverPort; //get port number
	    this.packet= new DatagramPacket(recvBuf, recvBuf.length);
	}
	
	public void sendData(MiddleWareData toSend) throws IOException{
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream(ECHOMAX);
    	ObjectOutputStream os = new ObjectOutputStream(new BufferedOutputStream(byteStream));
    	os.flush();
    	os.writeObject(toSend);
    	os.flush();
        //retrieves byte array
    	byte[] sendBuf = byteStream.toByteArray();  //send an Object
    	DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length, serverAddress, servPort);
    	socket.send(sendPacket);
    	packet.setLength(ECHOMAX); // Reset length to avoid shrinking buffer
	}
	
	public void recieve() throws IOException{
		socket.receive(packet);
	}
	
	public void printClientInfo(){
//		System.out.println("Talking with server at " + packet.getAddress().getHostAddress() + " on port " + packet.getPort());
	}
	
	public void printData(){
		System.out.println("The server sent back --> name: " + data.name + " Sent: " + data);
	}
	
	public void readObject() throws IOException, ClassNotFoundException{
	    int byteCount2 = packet.getLength();
	    ByteArrayInputStream byteStream2 = new ByteArrayInputStream(recvBuf);
	    ObjectInputStream is = new ObjectInputStream(new BufferedInputStream(byteStream2));
	    data = (MiddleWareData) is.readObject();
	}
	
	public void close(){
		socket.close();
	}

}
