import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class RequeteDNS {

	public static void main(String[] args) throws Exception {
		String adresse = "www.lifl.fr";

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
		
		byte[]adresseIP = extractIp(recu);
		System.out.println("\n");
		System.out.println("Adresse ip : "+InetAddress.getByAddress(adresseIP));
		System.out.println("Adresse en entier : "+ipToInt(adresseIP));
		
		
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