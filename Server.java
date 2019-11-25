//Server portion of a client/server stream-socket connection.
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Server extends JFrame
{
    private JTextField enterField; // inputs message from user
    private JTextArea displayArea; // display information to user
    private ObjectOutputStream output; // output stream to client
    private ObjectInputStream input; // input stream from client
    private ServerSocket server; // server socket
    private Socket connection; // connection to client
    private int counter = 1; // counter of number of connections

    public Server()
    {
        super( "Server" );

        enterField = new JTextField(); // create enterField
        enterField.setEditable( false );
        enterField.addActionListener(
          new ActionListener()
          {
              //send message to client
              public void acitonPerformed( ActionEvent event)
              {
                  sendData( event.getActionCommand() );
                  enterField.setText( "" );
              } // end method actionPerformed
          } // end anonymous inner class
        ); // end call to addActionListener

        add( enterField, BorderLayout.NORTH );

        displayArea = new JTextArea(); // create displayarea
        add( new JScrollPane(displayArea), BorderLayout.CENTER );

        setSize( 300, 150 ); // set size of window
        setVisible( true ); // show window
    } // end server constructor

    //set up and run server
    public void runServer()
    {
        try // set up server to receive connections and process connections
        {
            server = new ServerSocket(12345, 100); //create serversocket at port 12345 with a queue of 100?

            while (true)
            {
                try
                {
                    waitForConnection(); // wait for connection
                    getStreams(); // get input and output streams
                    processConnection(); //process connection
                } // end try
                catch ( EOFException eofException)
                {
                    displayMessage( "/nServer terminated connection" );
                } // end catch
                finally
                {
                    closeConnection(); // close connection
                    ++counter;
                } // end finally
            } // end while
        } //end try
        catch (IOException ioException)
        {
            ioException.printStackTrace();
        } //end catch
    }// end method run server
}