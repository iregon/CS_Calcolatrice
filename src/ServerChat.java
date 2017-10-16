import java.net.*;
import java.io.*;

public class ServerChat {

	private ServerSocket sSocket;
	private Socket connessione;
	private int port = 4500;
	private InputStreamReader in, input;
	private BufferedReader sIN, tastiera;
	private OutputStream out;
	private PrintWriter sOUT;
	private String msgDaInviare, msgRicevuto;

	public ServerChat() {
		try {
			sSocket = new ServerSocket(port);
			System.out.println("In attesa ... ");
			// Ciclo infinito termina con null.
			while (true) {
				connessione = sSocket.accept();
				// Le istruzioni successive mandano il flusso in uscita sul socket
				// restituisce lo stream associato al socket
				out = connessione.getOutputStream();
				sOUT = new PrintWriter(out); // trasforma in byte lo stream da inviare printwriter/stream
				// flusso in ingresso dalla socket
				in = new InputStreamReader(connessione.getInputStream());// legge lo stream
				sIN = new BufferedReader(in); // riceve i dati
				
				input = new InputStreamReader(System.in);
				tastiera = new BufferedReader(input);

				while (true)// termina con FINE
				// Stampa messaggio ricevuto
				{
					msgRicevuto = sIN.readLine();
					if (msgRicevuto == null) {
						System.out.println("Il client ha chiuso la Chat");
						break; // esce dal while interno e non essendoci connessione esce anche da quello
								// esterno
					}
					System.out.println("# " + msgRicevuto);
					// legge messaggio da tastiera

					msgDaInviare = tastiera.readLine();
					msgDaInviare = "Server>> " + msgDaInviare;
					// invia il messaggio
					sOUT.println(msgDaInviare);
					sOUT.flush(); // svuota il buffer

				}
			}
		} catch (Exception e) {
			System.out.println("Errore 1 :" + e);
		}

		try {
			connessione.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}
	public static void main(String[] args) {
		new ServerChat();
	}
}