package Client;

import java.io.*;
import java.net.*;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import static java.lang.Thread.sleep;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.github.sarxos.webcam.Webcam;


import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
 


import java.io.*;
import java.net.*;
    import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import static java.lang.Thread.sleep;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

////for jpanel
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;



public final class Client implements Runnable{


    int firstRun=1;
    String currentResponse;
    int total=0;
    
    int sleep=0;
    
    ServerSocket client; 
    Socket s;
    DataOutputStream dos;
    DataInputStream dis;
    BufferedReader br;
    Thread th;
    String usr;
    String usr1;
    String previous;
    boolean sending=false;
    boolean finish=true;
    String store;
    boolean text=false;
    
    
    
    
     private JLabel label;

    private List<BufferedImage> images;
    private int currentPic = 0;
int firstrun=0;
    boolean stop=false;
    void Show(){
        try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }

                images = new ArrayList<>(1);
                try {
                    images.add(ImageIO.read(new File("received.jpg")));
                  // images.add(ImageIO.read(new File("received.jpg")));
                } catch (IOException exp) {
                    exp.printStackTrace();
                }
                if(firstrun==0){
                    
    label = new JLabel();
                label.setHorizontalAlignment(JLabel.CENTER);
                label.setVerticalAlignment(JLabel.CENTER);
                }
        label.setIcon(new ImageIcon(images.get(currentPic)));
    
         if(firstrun==0){
             firstrun=1;
        JFrame frame = new JFrame("CL_Webcam");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                frame.add(label);
             // frame.add(switchPic, BorderLayout.SOUTH);  // only with button
             //  switchPic.doClick();
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);}
    
    }
    
    void stopMe() throws IOException, InterruptedException{
        
        
        /*currentResponse=dis.readUTF();
        if(currentResponse.equals("stop")){
//||stop==true){
        
            stop=true;
            System.out.println("Server whispered to stop.. waiting response");
            currentResponse=dis.readUTF();
          
            
            
        }
        
        if(currentResponse.equals("resume")){
                
                stop=false;
                }
        if(stop==true)
        {
            dos.writeUTF("no");
            while(stop==true)
            {
                Thread.sleep(100);
                
            }
            stop=false;
            dos.writeUTF("rusume");
        }else {
            dos.writeUTF("sure");
        }
        
        */
        currentResponse=dis.readUTF();
        if(currentResponse.equals("stop")){
//||stop==true){
        
            stop=true;
            System.out.println("Server whispered to stop.. waiting response");
            currentResponse=dis.readUTF();
   
        }
        if(currentResponse.equals("resume")){
                
                stop=false;
                }
        if(stop==false){
                usr="resume";
                System.out.println("sending resume");
                dos.writeUTF(usr);   
            }
                else {
                usr="stop";
                System.out.println("sending stop");
                dos.writeUTF(usr);
                while(stop==true)
                {
                    Thread.sleep(100);
                }
                System.out.println("sending resuming..");
                 usr="resume";
                dos.writeUTF(usr);
            }
    }
    
    
    
    Client() throws IOException, InterruptedException
    {
        Show();
        s=new Socket("localhost",5000);
        System.out.println("Connected to server!");
        
        dos=new DataOutputStream(s.getOutputStream());
        dis=new DataInputStream(s.getInputStream());
        br=new BufferedReader(new InputStreamReader(System.in));
        th=new Thread(this);
        th.start();
        
       
        
        
        while(true){
            System.out.println("im still inside while true)!!!!");
           //  previous=currentResponse;
             stopMe();
           
            
           // System.out.println("From server: " + currentResponse + " sending:"+sending + " finish:"+ finish);
            
            
            
            if(currentResponse.equals("sending")&&sending==false)
                    {
                        finish=false;
                        
                        System.out.println("starting Receive Frame()"); 
                    total++;
                    System.out.println("Total: "+total);
                        receiveFrame();
                        decide();
                        
                        
                        
                        finish=true;
                    }
              
                if(stop==false) {
                    
                    dos.writeUTF("received");
                    
                }
            
            System.out.println("finished while,retrying");
            
            
        }
    }
    
    
    @Override
    public void run() {
        while(true){
         try {
            
           
                System.out.println("Waiting user input....");
                usr1=br.readLine();
                store=usr1;
            sending=true;
            System.out.println("USER INPUT DETECTED! WAITING FOR RECEIVE FRAME TO FINISH!;");
            while(finish==false){ try {
                sleep(100);
                 //sending=true;
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                    }
}
           System.out.println("finiss is true so i can send store):" +store);
                dos.writeUTF(store);
                
                System.out.println("sending store complete)");
                sending=false;
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("finished while run()   ");
            
            
        }
}
    
    
    void receiveFrame() throws IOException, InterruptedException
{ 
    byte[] mybytearray = new byte[8000];
    InputStream is = s.getInputStream();
    FileOutputStream fos = new FileOutputStream("received.jpg");
    BufferedOutputStream bos = new BufferedOutputStream(fos);
    int bytesRead = is.read(mybytearray, 0, mybytearray.length);
    bos.write(mybytearray, 0, bytesRead);
    bos.close();
   
   
    
       Show();
        System.out.println("finished receive frame()");
        
        currentResponse=dis.readUTF();
        System.out.println("read response in receive frame():" + currentResponse);
        if(currentResponse=="done+text"){ text=true ;}
        else{ text=false;}
        sleep(30);
        if(sending==true)
        {
            dos.writeUTF("ok+text");
            System.out.println("usr is ok+text because sending is true");
        }
        else 
        {
           dos.writeUTF("ok");
        }
       
        System.out.println("SUMMARY: Text:"+ text+ "  sending:"+ sending);
      // sleep(500);
}
 
    
    
    
     void decide() throws IOException, InterruptedException
    {
        
        if(sending==true)//i need to send to Server
        {
            finish=true;
            while(sending==true)
            {
                sleep(50);
                System.out.println("sleeping,waiting thread for message to be sent in run()");
            }
        }
        
        if(text==true){
            //a reply from Server
            currentResponse=dis.readUTF();
            System.out.println("From Server i have receaved:"+currentResponse);
            text=false;
            
        }
        
    }  
    
    public static void main(String arg[]) throws IOException, InterruptedException
    {
        new Client();
    }
}


