package Main;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;


public class Server {
	
	public static ServerSocket serverSocket;
	public static Socket clientSocket;
	
	private static final int max = 10;
	private static final clientThread[] threads = new clientThread[max];
	public static int count = 0;
	
	public static String[] names = new String[max]; 
	
	public static boolean startMatch = false;
	
	
	public static void main(String[] args){
		
		int port = 6789;
		
		//Open server socket
		try{
			serverSocket = new ServerSocket(port);
			
			System.out.println("Server has been successfully beet initialized..");
		}catch(IOException e){
			e.printStackTrace();
		}
		
		
		//Create a client socket for each connection and pass it to a new client thread
		while(true){
			try{
				clientSocket = serverSocket.accept();
				
				if(count >= max){
					PrintStream os = new PrintStream(clientSocket.getOutputStream());
					os.println("The server has reached it's maximum capacity..");
					os.close();
					clientSocket.close();
				}else{
					for(int i = 0; i < max; i++){
						if(threads[i] == null) {
							(threads[i] = new clientThread(clientSocket, threads)).start();
							count ++;
							break;
						}
					}
				}
				
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
}


class clientThread extends Thread {
	
	private DataInputStream is;
	private PrintStream os;
	private Socket clientSocket;
	private static clientThread[] threads;
	Random random = new Random();
	String line;
	
	public clientThread(Socket clientSocket, clientThread[] threads){
		this.clientSocket = clientSocket;
		clientThread.threads = threads;
	}
	
	public void run() {
		
		try {
			
			is = new DataInputStream(clientSocket.getInputStream());
			os = new PrintStream(clientSocket.getOutputStream());
			BufferedReader lines = new BufferedReader(new InputStreamReader(is));
//			Server.names[getPosition()] = lines.readLine();
			
			
			
			System.out.println(getPosition() + ": Person has joined, " + Server.names[getPosition()]);
			
			
			
			while(true){
				try{
					line = lines.readLine();
				}catch(Exception e){}
				
				
				if(line != null){
					
					
					if(line.startsWith("QUIT")){
						for(int i=0;i<Server.count;i++){
							if(threads[i] != null && threads[i] != this){
								send(i, "QUIT:" + getPosition() + ":" + Server.names[getPosition()]);
							}
						}
						break;
					}
					
					else if(line.startsWith("MAP")){
						System.out.println(line);
					}

					else if(line.startsWith("bullet")){
						for(int i=0;i<Server.count;i++){
							if(threads[i] != null && threads[i] != this){
								if(line != null){
									send(i, "bullet:" + getPosition() + ":" + line);
								}
							}
						}
					}
					
					else if(line.startsWith("DEATH_TIMER")){
						for(int i=0;i<Server.count;i++){
							if(threads[i] != null && threads[i] != this){
								if(line != null){
									send(i, "DEATH_TIMER:" + getPosition() + ":" + line);
								}
							}
						}
					}
					
					
					else{
						for(int i=0;i<Server.count;i++){
							if(threads[i] != null && threads[i] != this){
								if(line != null){
//									System.out.println("ECHO: " + line);
									try{
										send(i, getPosition() + ":" + line);
									}catch(Exception e){}
								}
							}
						}
					}

				
				
				}
				

				
				
				
				
			}
			
			// quit command
//			os.println("You have successfully disconnected from the server.");
//			for(int i=0;i<Server.count;i++){
//				if(threads[i] != null && threads[i] != this){
////					threads[i].os.println("A user has left the room.");
//					send(i, Server.names[getPosition()] + " has left the room.");
//				}
//			}
			
			//Clean up, Set the current thread variable to null so that a new client can be accepted
			System.out.println(getPosition() + ": has quit.");
			
			for(int i=0;i<Server.count;i++){
				if(threads[i] == this){
					threads[i] = null;
					Server.names[i] = null;
				}
			}
			
			Server.count --;
			is.close();
			os.close();
			clientSocket.close();
			
			
		
		
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	

	public int getPosition(){
		int pos = 0;
		for(int i=0;i<Server.count;i++){
			if(threads[i] == this){
				pos = i;
				break;
			}
		}
		return pos;
	}
	
	public static void send(int num, String str){
		threads[num].os.println(str);
	}
	
	
	
	
	
}




















