// @author QUENTIN Dylan -- Dépommier Thibaut
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Scanner;

public class RequeteDNS {

	public static void main(String[] args) throws Exception {
		
		while(true){
			Scanner sc = new Scanner(System.in);
			
			System.out.print("Adresse internet : ");
			String adresse = sc.nextLine();
			System.out.println();
			if (args.length == 1) {
				adresse = args[0];
			}
	
			// On cree le paquet que l'on veut envoyer
			byte[] envoie = creerPaquet(adresse);
			byte[]recu = envoyerPaquet(envoie);
			
			System.out.println("Trame reçue : ");
			for (int i = 0; i < recu.length; i++) {
				if (i % 16 != 0)
					System.out.print(", " + Integer.toHexString(recu[i] & 0xff));
				else
					System.out.print("\n" + Integer.toHexString(recu[i] & 0xff));
			}
			System.out.println();
			byte[]adresseIP = extractIp(recu);
			showInfo(recu);
			System.out.println("\n");
			System.out.println("Adresse ip : "+InetAddress.getByAddress(adresseIP));
		}
		
	}

	
	/** Affiche le détail de la réponse*/
	private static void showInfo(byte[] recu) {
		// TODO Auto-generated method stub
		for (int i = 0; i < recu.length; i++) {
	        if (recu[i] == (byte) 0xC0) {
	            	
	          int RDDLength = (((recu[i+10] & 0xFF) << 8)  + (recu[i+11] & 0xFF));

	          System.out.println("Type........: " + (((recu[i+2]  & 0xFF) << 8)  + (recu[i+3] & 0xFF)));
	          System.out.println("Classe......: " + (((recu[i+4]  & 0xFF) << 8)  + (recu[i+5] & 0xFF)));
	          System.out.println("TTL.........: " + (((recu[i+6]  & 0xFF) << 24) + ((recu[i+7] & 0xFF) << 16) + ((recu[i+8] & 0xFF) << 8) + (recu[i+9] & 0xFF)));
	          System.out.println("RDDLength...: " + RDDLength);

	          if (RDDLength == 4) {
	            System.out.print(String.format("%d.", recu[i+12] & 0xFF));
	            System.out.print(String.format("%d.", recu[i+13] & 0xFF));
	            System.out.print(String.format("%d.", recu[i+14] & 0xFF));
	            System.out.print(String.format("%d ", recu[i+15] & 0xFF));
	            System.out.println(" (IPv4)");
	          }
	          System.out.println();
	          i += 11 + RDDLength;
	        }
		}
	}

	/**
	 * Envoie le paquet et retourne la reponse reçue
	 */
	private static byte[] envoyerPaquet(byte[] envoie) throws Exception{
		// 172.18.12.9 a la fac
		InetAddress adresse = InetAddress.getByName("reserv1.univ-lille1.fr");
		DatagramSocket socket = new DatagramSocket();
		DatagramPacket paquet = new DatagramPacket(envoie, envoie.length, adresse,
				53);
		DatagramPacket paquetR = new DatagramPacket(new byte[512], 512);
		socket.send(paquet);
		socket.receive(paquetR);
		socket.close();
		
		return paquetR.getData();
	}

	/**
	 * Cree une requete DNS pour l'adresse donnee
	 */
	public static byte[] creerPaquet(String adresse) {

		int taille = 18 + adresse.length();
		byte[] requete = new byte[taille];

		String[] adresseSplit = adresse.split("\\.");

		// On creer la requete
		requete[0] = (byte) 0x08;
		requete[1] = (byte) 0xbb;
		requete[2] = (byte) 0x01;
		requete[3] = (byte) 0;
		requete[4] = (byte) 0;
		requete[5] = (byte) 0x01;
		
		requete[taille- 1] = (byte) 0x01;
		requete[taille - 3] = (byte) 0x01;
		requete[taille - 5] = (byte) 0;
		requete[taille - 4] = (byte) 0;
		requete[taille- 2] = (byte) 0;
		
		
		int pointeur;
		for (pointeur = 6; pointeur < 12; pointeur++)
			requete[pointeur] = (byte) 0;
		
		// On ajoute l'adresse avec les points
		for (String s : adresseSplit) {
			requete[pointeur++] = (byte) (s.length() & 0xff);
			for (char c : s.toCharArray()) {
				requete[pointeur++] = (byte) c;
			}
		}
		return requete;
	}
	
	/**
	 * Extrait l'adresse ip contenue dans le paquet
	 * @param paquet
	 * @return
	 */
	public static byte[]extractIp (byte[]paquet) {
		int i, offsetRDLength, rdlength;
		byte[] ip = new byte[4];
		
		// TRAITEMENT DU PAQUET DATA
		// on va jusqu'au début des byte de réponse
		i = getEndOfString(paquet, 12) + 6;

		// on cherche la réponse qui contient l'ip
		while (getShortValue(paquet, i) != 1) {
			offsetRDLength = i + 8;
			rdlength = getShortValue(paquet, offsetRDLength);
			i = offsetRDLength + 4 + rdlength;
		}

		// CODAGE DES 4 OCTETS DE L'IP DANS UN TABLEAU DE 4 OCTETS
		ip[0] = paquet[i + 10];
		ip[1] = paquet[i + 11];
		ip[2] = paquet[i + 12];
		ip[3] = paquet[i + 13];
		return ip;
	}
	
	/**
	 * Transforme une adresse IP en INT
	 */
	public static int ipToInt(byte[]ip) {
		return (ip[0] & 0xff) * 16777216 + (ip[1] & 0xff) * 65536
				 + (ip[2] & 0xff) * 256 + (ip[3] & 0xff);
	}
	
	
	/**
	 * Renvoie la valeur en entier de 2 octect
	 */
	public static int getShortValue(byte[] t, int i) {// valeur déc de 2 octets
		return (t[i] & 0xff) * 256 + (t[i + 1] & 0xff);
	}
	
	/** Calcule la fin d'une chaine a partir d'un offset
	 */
	public static int getEndOfString(byte[] t, int i) {
		while (t[i] != 0) {
			int c = t[i] & 0xff;
			if (c >= 192)
				return i + 2;
			else
				i += c + 1;
		}
		return i + 1;
	}

}