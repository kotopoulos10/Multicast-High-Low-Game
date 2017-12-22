import java.io.Serializable;

public class ChatData implements Serializable{

	
	int chatters;
	int Index;
	String IpAddress;
	int port;
	
	public ChatData(int index){
		this.chatters=0; 
		this.Index=index; 
		this.IpAddress="225.0.1."+(index+1);
		this.port=5540+index; 
	}
}
