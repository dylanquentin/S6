import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ClientUDP {
	
	private DatagramSocket socket;
	private byte[] buf = new byte[256];
	private InetAddress adresse;
	private int port;
	
	public ClientUDP(int port, String host, String msg){
		try {
			
			this.port = port;
			socket = new DatagramSocket();
			adresse = InetAddress.getByName(host);
		} catch (SocketException | UnknownHostException e) {
			System.out.println(e.getCause());
		}
		buf = msg.getBytes();
		
	}
	
	public void run(){
		
		try {
			
			DatagramPacket dp = new DatagramPacket(buf,buf.length,adresse,port);
			socket.send(dp);		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] arg){
		ClientUDP client = new ClientUDP(Integer.valueOf(arg[0]), arg[1], arg[2]);
		client.run();
	}

}
