/******************************************************************************
 * Donya Moxley
 * CIST 2371 Fall 2018
 *  Major Project
 ******************************************************************************/
package client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.geometry.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
/**
 *
 * @author dmoxl
 */
public class Client extends Application {
 // Global viriables 
    TextArea txtArea = new TextArea(); // display area
    TextField tf = new TextField(); // capture text
    Button sendBT = new Button("Send"); // send captured across network
    DataInputStream input; // input stream for data
    DataOutputStream output; // output stream 
    String textOut, textIn; // captures read and write data
    
    
    @Override
    public void start(Stage primaryStage) {
        GridPane root = new GridPane();
        root.setVgap(100);
        root.add(txtArea, 0, 0,3,1);
        root.add(tf, 0,1,3,1);
        GridPane.setHalignment(sendBT, HPos.RIGHT);
        root.add(sendBT, 2,1 );
        txtArea.setText("Please enter port number:" +'\n');
        
       
        
        
        Scene scene = new Scene(root, 500, 350);
        
        primaryStage.setTitle("Client");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // Action event for send button to send text from 
        // textfield across socket and append string to textarea
        sendBT.setOnAction((e)-> {
         
            textOut = tf.getText().trim();
            Text text1 = new Text(textOut);
            text1.setTextAlignment(TextAlignment.RIGHT);
            try{
            output.writeUTF(text1.getText());
            txtArea.appendText("Client: "+text1.getText()+ '\n');
            output.flush();
            
            }
            catch(Exception ex){
                
                System.out.println(ex);
            }
            
        
        });
        
        // Networking  thread allowing gui to render completely
        //try catch block for catching networking exceptions
        
        new Thread(() ->{
        try
        {
            Socket socket = new Socket("localhost", 8000);
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
            
          while(true)
          {
            textIn = input.readUTF();
            Text text = new Text(textIn);
            text.setTextAlignment(TextAlignment.LEFT);
            
            txtArea.appendText("Server: "+ text.getText()+ '\n');
          
          }
        }
        catch(Exception ex)
        {
           System.out.println(ex);
        }
        }).start();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
