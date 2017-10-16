import java.net.*;
import java.io.*;

public class ServerChat {

	private ServerSocket sSocket;
	private Socket connessione;
	private int port = 4500;
	private InputStreamReader in;
	private BufferedReader sIN;
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

				while (true)// termina con FINE
				// Stampa messaggio ricevuto
				{
					msgRicevuto = sIN.readLine();
					if (msgRicevuto == null) {
						System.out.println("Il client ha chiuso la Chat");
						break; // esce dal while interno e non essendoci connessione esce anche da quello
								// esterno
					}
					System.out.println("# Operazione: " + msgRicevuto);					
					
					msgDaInviare = elaboraOperazione(msgRicevuto);
					System.out.println("> Risultato: " + msgDaInviare);
//					// invia il messaggio
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
	
	public String elaboraOperazione(String op) {
		String operazione;
		String ris = "";
		
		if(op.contains("+")) operazione = "\\+";
		else if(op.contains("-")) operazione = "-";
		else if(op.contains("*")) operazione = "\\*";
		else if(op.contains("/")) operazione = "/";
		else return "Formato non valido";
//		
//		op = op.replaceAll("\\" + operazione, " " + operazione);
		
		String parts[] = op.split(operazione);
		int a = Integer.parseInt(parts[0]);
		int b = Integer.parseInt(parts[1]);
		
		if(operazione.equals("\\+")) ris = Integer.toString(a + b);
		else if(operazione.equals("-")) ris = Integer.toString(a - b);
		else if(operazione.equals("\\*")) ris = Integer.toString(a * b);
		else if(operazione.equals("/")) ris = Integer.toString(a / b);
		return ris;
	}
	
	public static void main(String[] args) {
		new ServerChat();
	}
}