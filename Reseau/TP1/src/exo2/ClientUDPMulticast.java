package exo2;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Scanner;

public class ClientUDPMulticast {
	
	private MulticastSocket socket;
	private byte[] buf = new byte[256];
	private InetAddress adresse;
	private int port;
	
	public ClientUDPMulticast(){
		try {
			
			this.port = 7654;
			socket = new MulticastSocket(port);
			adresse = InetAddress.getByName("224.0.0.1");
			socket.joinGroup(adresse);
		} catch (IOException e) {
			System.out.println(e.getCause());
		}
		
	}
	
	public void run(){
		
		while(true){
			Scanner sc = new Scanner(System.in);
			
			buf = sc.nextLine().getBytes();
			try {
				
				DatagramPacket dp = new DatagramPacket(buf,buf.length,adresse,port);
				socket.send(dp);		
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] arg){
		ClientUDPMulticast client = new ClientUDPMulticast() ;
		client.run();
	}

}
