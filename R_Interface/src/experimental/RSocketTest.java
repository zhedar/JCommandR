package experimental;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;

public class RSocketTest
{
	public static void main(String[] args) throws IOException
	{
		/*
		 * funktioniert auch mit neuester Version, 
		 * wenn diese nicht unter Program Files... installiert wurde
		 */
		ProcessBuilder builder = new ProcessBuilder(
				"c:\\r\\R-2.15.0\\bin\\x64\\r.exe", "--save");
		final Process rProc = builder.start();
		final BufferedReader err = new BufferedReader(new InputStreamReader(
				rProc.getErrorStream()));
		Writer wr = new OutputStreamWriter(new BufferedOutputStream(
				rProc.getOutputStream()));
		final BufferedWriter bufWr = new BufferedWriter(wr);
		InputStreamReader ipsRead = new InputStreamReader(
				rProc.getInputStream());
		final BufferedReader bufRead = new BufferedReader(ipsRead);

		//Socket starten
	
			
			Thread socketThread = new Thread(new Runnable() {
				
				@Override
				public void run()
				{
					ServerSocket javaSocket;
					try
					{
						javaSocket = new ServerSocket(458);
						Socket client = javaSocket.accept();
						client.setSoTimeout(10000);
						javaSocket.setSoTimeout(10000);
//						client.getOutputStream().write('s');
						PrintWriter out = new PrintWriter(client.getOutputStream(), true);
						out.println("bla");
						BufferedReader in = 
						    new BufferedReader(new InputStreamReader(client.getInputStream()));
						System.out.println("b read");
						System.out.println(client.isConnected() + " " + client.getInetAddress());
						System.out.println(in.readLine());
						
						System.out.println("n read");
						javaSocket.close();
					}
					catch (IOException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			socketThread.start();
			
			
//			InputStreamReader ipsRead = new InputStreamReader(javaSocket.getInputStream());
//			System.out.println(new BufferedReader(ipsRead).readLine());
		
		
		Thread errThread = new Thread(new Runnable() {

			@Override
			public void run()
			{
				while (true)
				{
					try
					{
						//blockt, wenn Stream leer, null wenn stream geschlossen
						//liest stderr aus
						String errline = err.readLine();
						if (errline != null)
							System.err.println(errline);
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
				}
			}
		});
		errThread.start();

		Thread writeThread = new Thread(new Runnable() {

			@Override
			public void run()
			{
				try
				{
					//warte auf Initialisierung
					Thread.sleep(1000);
					//Schreibe in stdin
					//Beispielberechnung
					
					bufWr.write("pnorm(1.70)-pnorm(-1.35)");
					bufWr.newLine();
					bufWr.flush();
					
					bufWr.write("socket <- make.socket(host = \"localhost\", port=458, fail = TRUE, server = FALSE)");
					bufWr.newLine();
					bufWr.write("on.exit(close.socket(a))");
					bufWr.newLine();
					bufWr.flush();
//					bufWr.write("write.socket(socket, \"test\")");
//					bufWr.write("read.socket(socket)");
//					bufWr.newLine();
//					bufWr.flush();
					bufWr.write("write.socket(socket, \"test\")");
					bufWr.newLine();
					bufWr.flush();
					Thread.sleep(500);
					Thread.sleep(10000);
					//Gebe Befehl zum Schließen der Anwendung
					bufWr.write("q()");
					bufWr.newLine();
					bufWr.flush();
					//Warten bis Prozess terminiert hat
					rProc.waitFor();
					System.out.println(rProc.exitValue());
					System.exit(0);
				}
				catch (IOException | InterruptedException e)
				{
					e.printStackTrace();
				}

			}
		});
		writeThread.start();

		Thread readThread = new Thread(new Runnable() {

			@Override
			public void run()
			{
				while (bufRead != null)
				{
					try
					{
						//blockt, wenn Stream leer, null wenn stream geschlossen
						//liest stdout aus
						String readline = bufRead.readLine();
						if(readline != null)
							System.out.println(readline);
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
				}
			}
		});
		readThread.start();
		
		
	}
	
	

}
