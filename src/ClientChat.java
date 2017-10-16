import java.net.*;
import java.io.*;
public class ClientChat {
	
	private Socket connessione=null;
	private String server= "localhost";
	private int porta=4500;
	private InputStreamReader in, input;
	private BufferedReader sIN, tastiera;
	private OutputStream out;
	private PrintWriter sOUT;
	private String msgDaInviare, msgRicevuto;
	
	public ClientChat() {
		try {
			connessione= new Socket(server, porta);
			System.out.println("Connessione eseguita");
		}catch (Exception e) {
			System.out.println("1 " + e);
			System.exit(-1);
		}
		
		try {
			// Invertito rispetto al server
		
			// flusso in ingresso dalla socket
			in = new InputStreamReader(connessione.getInputStream());// legge lo stream
			sIN = new BufferedReader(in); // riceve i dati
			
			input = new InputStreamReader(System.in);
			tastiera = new BufferedReader(input);
			
			// restituisce lo stream associato al socket
			out = connessione.getOutputStream();
			sOUT = new PrintWriter(out); // trasforma in byte lo stream da inviare printwriter/stream
			
			System.out.println("Inserisci l'operazione da eseguire (es.: 5+4, 5-4, 8*2, 6/3)\n"
					   	   	 + "Iserisci 'FINE' per terminare il programma");
			//ciclo infinito termina con fine
			while (true)
			{
				System.out.print("> ");
				msgDaInviare = tastiera.readLine();
				if (msgDaInviare.equals("FINE."))
					break;
				
				//Invia messaggio al server
				sOUT.println(msgDaInviare);
				sOUT.flush(); //Svuota il buffer
				
				//Ricezione messaggio dal server
				msgRicevuto = sIN.readLine();
				System.out.println("# Risultato: " + msgRicevuto);
			}

		} catch (Exception e) {
			System.out.println("2 " + e);
		}
		
		//Gestisce l'eccezione di chiusura socket
		try {
			connessione.close();
		} 
		catch (Exception e) 
		{
			System.out.println("3 " + e);
		}
	
	}
	public static void main(String[] args) {
		new ClientChat();
	}

}
