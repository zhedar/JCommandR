package de.hsl.rinterface.experimental;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class ProcessBuilderStreamTest
{
	public static void main(String[] args) throws IOException
	{
		/*
		 * funktioniert auch mit neuester Version, 
		 * wenn diese nicht unter Program Files... installiert wurde
		 */
		ProcessBuilder builder = new ProcessBuilder(
				"/Library/Frameworks/R.framework/Versions/2.15/Resources/bin/R64", "--save");
		final Process rProc = builder.start();
		final BufferedReader err = new BufferedReader(new InputStreamReader(
				rProc.getErrorStream()));
		Writer wr = new OutputStreamWriter(new BufferedOutputStream(
				rProc.getOutputStream()));
		final BufferedWriter bufWr = new BufferedWriter(wr);
		InputStreamReader ipsRead = new InputStreamReader(
				rProc.getInputStream());
		final BufferedReader bufRead = new BufferedReader(ipsRead);

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
					bufWr.newLine();
					bufWr.flush();
					ProcessBuilderStreamTest.plotExample(bufWr);
					ProcessBuilderStreamTest.max(bufWr);
					Thread.sleep(1000);
					//Gebe Befehl zum Schlie�en der Anwendung
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
	
	
	static private void plotExample(BufferedWriter bufWr) throws IOException
	{
		bufWr.write("x <- c(42)");
		bufWr.newLine();
		bufWr.write("jpeg(\"testplot.jpg\")");
		bufWr.newLine();
		bufWr.write("plot(x)");
		bufWr.newLine();
//		bufWr.write("dev.off()");
//		bufWr.newLine();
		bufWr.flush();
	}
	
	static private void max(BufferedWriter bufWr) throws IOException
	{
		bufWr.write("max(c(1,4,80,18,42,100,0))");
		bufWr.newLine();
		bufWr.flush();
	}
}
