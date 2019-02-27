import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class ServeurUDP {
	
	private DatagramSocket socket;
	private byte[] buf = new byte[256];
	
	public ServeurUDP(int port){
		try {
			socket = new DatagramSocket(port);
			
		} catch (SocketException e) {
			System.out.println(e.getCause());
		}
		
	}
	
	public void run(){
		while(true){
			try {
				DatagramPacket dp = new DatagramPacket(buf,buf.length);
				socket.receive(dp);
				InetAddress address = dp.getAddress();
				int port = dp.getPort();
				dp = new DatagramPacket(buf,buf.length,address,port);
				String received = new String(dp.getData(),0,dp.getLength());
				System.out.println(received);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	public static void main(String arg[]){
		ServeurUDP serveur = new ServeurUDP(Integer.valueOf(arg[0]));
		serveur.run();
	}
	
	
}
