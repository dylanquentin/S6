import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;


public class ReceiveUDP {
	
	private MulticastSocket socket;
	private InetAddress adresse;
	
	public ReceiveUDP(){
		
		try {
			socket = new MulticastSocket(7654);
			adresse = InetAddress.getByName("224.0.0.1");
			socket.joinGroup(adresse);

		} catch (IOException e) {
			System.out.println(e.getCause());
		}
		
	}
	
	public void run(){
		while(true){
			try {
				DatagramPacket dp = new DatagramPacket(new byte[999999],999999);
				dp.setAddress(dp.getAddress());
				socket.receive(dp);
				System.out.println( new String(dp.getData()));
			//	socket.send(dp);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	public static void main(String arg[]){
		ReceiveUDP re = new ReceiveUDP();
		re.run();
	}
	
	
}
