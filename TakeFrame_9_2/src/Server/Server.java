package Server;

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



public class Server implements Runnable {

    //from showlabelimage
    
   
    
    
    ServerSocket server;
    Socket s;
    DataOutputStream dos;
    DataInputStream dis;
    BufferedReader br;
    Thread th;
    String usr;
    String usr1;
    String lastClient="ok";
    int firstRun=1;
    String currentReceived;
    String store;
    boolean sending=false;
    boolean sendingtext=false;
    int frame=0;
    boolean finish=true;
    String previous;
    int mem=0;
    boolean stop=true;
    boolean text=false;
    
    
    
    
    
    
     private JLabel label;

    private List<BufferedImage> images;
    private int currentPic = 0;
int firstrun=0;
    
    
    void Show(){
        try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }

                images = new ArrayList<>(1);
                try {
                    images.add(ImageIO.read(new File("firstCapture.jpg")));
                   // images.add(ImageIO.read(new File("received.jpg")));
                } catch (IOException exp) {
                    exp.printStackTrace();
                }
                if(firstrun==0){
                    
    label = new JLabel();
                label.setHorizontalAlignment(JLabel.CENTER);
                label.setVerticalAlignment(JLabel.CENTER);
                }
        label.setIcon(new ImageIcon(images.get(currentPic))); // display imagine
    JButton switchPic = new JButton("STOP/RESTART");
                switchPic.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        /////code to start connection
                        stop=!stop;
                            
                        System.out.println("PRESSED + stop:" + stop);
                    }
                });
         if(firstrun==0){
             firstrun=1;
             System.out.println("setting firstrun=1");
        JFrame frame = new JFrame("Server_Webcam");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                frame.add(label);
              frame.add(switchPic, BorderLayout.SOUTH);
                switchPic.doClick();
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);}
         
         
    
    }
    
    void iStopYou() throws InterruptedException, IOException{
         /*
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
        currentReceived=dis.readUTF();
        if(currentReceived.equals("sure")){
            stop=false;
        }else{
            if(currentReceived.equals("no")){
                stop=true;
                currentReceived=dis.readUTF();
                if(currentReceived.equals("resume"))
                {
                    stop=false;
                }
            }
        }
         
         */

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
            currentReceived=dis.readUTF();
        if(currentReceived.equals("stop")){
            stop=true;
            System.out.println("Server whispered to stop.. waiting response");
            currentReceived=dis.readUTF();
        }
        
        if(currentReceived.equals("resume")){
                stop=false;
                }
    }
    
    
    Server() throws IOException, InterruptedException
    {   Show();
    stop=true; ////stopping everything to let user decide his fate
        server=new ServerSocket(5000);
        System.out.println("waiting for connection with client");
        s=server.accept();
       System.out.println("Connected with client!!!");
        dos=new DataOutputStream(s.getOutputStream());
        dis=new DataInputStream(s.getInputStream());
        br=new BufferedReader(new InputStreamReader(System.in));
        
        th=new Thread(this);
        System.out.println("new Thread");
        th.start();
        System.out.println("TH start!");
      
        
                
        while(true){
        System.out.println("i'm in while,entering stop");
            
            iStopYou();
           
             System.out.println("finished stop,waiting for client to send received.");
            /*if(stop==false){
                
          currentReceived=dis.readUTF();
          System.out.println("but stop is false
            }*/
            
            if(currentReceived.equals("received")&&sending==false)
            {finish=false;
                System.out.println("picture received by client,entering sendframe again() "+sending);
                sendFrame();
                decide();
                System.out.println("Total: "+frame);
               
                finish=true;
                
            }
             
            Thread.sleep(50);// try 50 
            
        }
    }
     
    
    @Override
    public void run()  {
        System.out.println("Entered run()");
        try { while(true)
        {
            
                
            System.out.println("entered while,waiting user input");
          
                 
                usr1=br.readLine();
                sending=true;
                System.out.println("User input detected!!!!");
                store=usr1;
                
                System.out.println("value saved,commenging waiting for frame to send");
                while(finish==false){ sleep(10);}
                System.out.println("waiting done,looks like sending frame is finished");
                
                dos.writeUTF(store);
                System.out.println("dos.writeutf(sur) SENDING COMPLETE!!!  mem:" + mem);
           
                sending=false;
                
              
            
        } } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    void sendFrame() throws IOException, InterruptedException{
        frame++;
        
         System.out.println("started send Frame"+sending);
        //tanking and displaying picture to client jpanel
        usr="sending";
        System.out.println("usr takes value sending"+sending);
        dos.writeUTF(usr);
        System.out.println("dos.writeUTF(usr) aka sending"+ sending);
       
 
   Webcam webcam = Webcam.getDefault();
        webcam.open();
        ImageIO.write(webcam.getImage(),"JPG", new File("firstCapture.jpg"));

  System.out.println("creating Myfile "+sending);
    File myFile = new File("firstCapture.jpg");

      System.out.println("Picture taken");
    try{
      
     System.out.println("accept servsock.accept(); "+sending);
     byte[] mybytearray = new byte[(int) myFile.length()];
      BufferedInputStream bis = new BufferedInputStream(new FileInputStream(myFile));
      bis.read(mybytearray, 0, mybytearray.length);
      OutputStream os = s.getOutputStream();
      os.write(mybytearray, 0, mybytearray.length);
      os.flush();
      System.out.println("picture sent,exiting sendFrame"+sending);
      
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
    Show();
    if(sending==true)
            usr="done+text";
    else usr="done";
            dos.writeUTF(usr);
            
            currentReceived=dis.readUTF();
            
            System.out.println("after sending usr:" + usr+ " i have currentR:"+ currentReceived); 
            if("ok+text".equals(currentReceived))
            { 
                System.out.println("i have ok+text from client because currentR:"+ currentReceived); 
                text=true;
            }
            else
            {
                text=false;
            }
            System.out.println("SUMMARY: Text:"+ text+ "  sending:"+ sending);
         // sleep(500);
    }
    
   // void startSending(String text) throws IOException{ 
    //    usr="text";
    //    dos.writeUTF(usr);
    //    System.out.println("inside startSending  usr is: " + text);
   // }
    
    
    void decide() throws IOException, InterruptedException
    {System.out.println("deciding... text:" + text + " finish"+ finish ); 
        if(text==true){
            //a reply from client
            System.out.println("i am ready to receave from client, value currentR: "+currentReceived);
            currentReceived=dis.readUTF();
            System.out.println("From Client i have receaved:"+currentReceived);
            text=false;
        }
        if(sending==true)//i need to send to client
        {
            finish=true;
            while(sending==true)
            {
                sleep(50);
                System.out.println("sleeping,waiting thread for message to be sent in run()");
            }
        }
            
    }
    
    public static void main(String arg[]) throws IOException, InterruptedException
    {
        System.out.println("commencing new Server();");
        
        
        new Server();
         
        
    }
}





 class Take implements Runnable{

     
     
    @Override
    public void run() {
        
        
        
        
        
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

};