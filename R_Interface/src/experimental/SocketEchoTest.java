package experimental;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class SocketEchoTest
{
	 public static void main( String[] args )
	  {
	    Socket t = null;
	    try
	    {
	      t = new Socket( "localhost", 7 );
	      PrintStream os = new PrintStream( t.getOutputStream() );
	      String test = "test";
	      os.println( test );
	      BufferedReader in = new BufferedReader(
	          new InputStreamReader( t.getInputStream()) );
	      String s = in.readLine();
	      if ( s.equals(test) )
	        System.out.println( "Hurra, er lebt!" ) ;
	    }
	    catch ( /* UnknownHostException is a */ IOException e ) {
	      e.printStackTrace();
	    }
	    finally
	    {
	      if ( t != null )
	        try { t.close(); } catch ( IOException e ) { e.printStackTrace(); }
	    }
	  }
}
