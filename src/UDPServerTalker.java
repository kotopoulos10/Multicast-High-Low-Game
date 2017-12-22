import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPServerTalker {
	private static final int ECHOMAX = 1024; // Maximum size of echo datagram
    
    public DatagramSocket socket;  
    public DatagramPacket packet;
    public byte[] recvBuf = new byte[ECHOMAX];
    public MiddleWareData data;
    
    
	public UDPServerTalker(int serverPort) throws SocketException{
		
		this.socket=new DatagramSocket(serverPort);
		this.packet= new DatagramPacket(recvBuf, recvBuf.length);
	}
	
	public void recieve() throws IOException{
		socket.receive(packet); // Receive packet from client
	}
	
	public void printClientInfo(){
		System.out.println("Handling client at " + packet.getAddress().getHostAddress() + " on port " + packet.getPort());
	}
	
	public void readObject() throws IOException, ClassNotFoundException{
	    int byteCount2 = packet.getLength();
	    ByteArrayInputStream byteStream2 = new ByteArrayInputStream(recvBuf);
	    ObjectInputStream is = new ObjectInputStream(new BufferedInputStream(byteStream2));
	    data = (MiddleWareData) is.readObject();
	}
	
	public void printData(){
		System.out.println("Client name: " + data.name + " Sent: " + data.message);
	}
	
	public void send(MiddleWareData toSend ) throws IOException{
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream(ECHOMAX);
    	ObjectOutputStream os = new ObjectOutputStream(new BufferedOutputStream(byteStream));
    	os.flush();
    	os.writeObject(toSend);
    	os.flush();
        //retrieves byte array
    	byte[] sendBuf = byteStream.toByteArray();  //send an Object
    	DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length, packet.getAddress(), packet.getPort());
    	socket.send(sendPacket);
    	packet.setLength(ECHOMAX); // Reset length to avoid shrinking buffer
		
		
//		byte[] buf=data.data.getBytes();  //byte array for data to SEND to server
//	    DatagramPacket sendPacket = new DatagramPacket(buf,buf.length, packet.getAddress(), packet.getPort());
//	    socket.send(sendPacket);  //Send the input line back to client
//	    packet.setLength(ECHOMAX); // Reset length to avoid shrinking buffer
	}
}
