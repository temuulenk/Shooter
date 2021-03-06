package Main;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements Runnable {
	private static Socket clientSocket = null;
	private static PrintStream os = null;
	private static DataInputStream is = null;
	private static boolean closed = false;
	
//	private ArrayList<String> added = new ArrayList<String>(0);
	
  
	public static void running(){

//	    int portNumber = 6789;
//	    String host = "localhost";
//	    String host = "127.0.0.1";
	    String host = "76.21.140.65";
	    
//	    System.out.println("Attempting to establish a connection..");
	    
	
	    try {
	    	clientSocket = new Socket(host, 6789);
		    new BufferedReader(new InputStreamReader(System.in));
		    os = new PrintStream(clientSocket.getOutputStream());
		    is = new DataInputStream(clientSocket.getInputStream());
	    } catch (UnknownHostException e) {
	    	System.err.println("Cannot identify the host: " + host);
	    } catch (IOException e) {
	    	System.err.println("Couldn't get I/O for the connection to the host "+ host);
	    }
	

	    
	    if (clientSocket != null && os != null && is != null) {
	    	try {
		        new Thread(new Client()).start();
		        while (!closed) {
		        	
		        	if(Play.quit == false){
		        		send(Player.player_line);
		        	}else{
		        		send("QUIT");
//		        		break;
		        	}
		        	
		        	
		        	try {
						Thread.sleep(5);
					} catch (InterruptedException e) {
//						e.printStackTrace();
					}
		        }
		        
		        os.close();
		        is.close();
		        clientSocket.close();
		    } catch (IOException e) {
//		    	  System.err.println("IOException: " + e);
		    }
	    }	
	}
	
	public static void send(String str) {
		os.println(str);
	}
	
	public void run() {

		String line = "";
		BufferedReader lines = new BufferedReader(new InputStreamReader(is));
	    try {
	    	while ((line = lines.readLine()) != null) {
	    		
	    		
	    		if(line.startsWith("QUIT")){
	    			System.out.println("QUITTING");
	    			String[] split = line.split(":");
	    			ClientHandler.players[Integer.parseInt(split[1])] = null;
	    			ClientHandler.screen_name[to_int(split[1])] = null;
	    		}
	    		
	    		else if(line.startsWith("DEATH_TIMER")){
	    			String[] split = line.split(":");
	    			int value = to_int(split[3]);
	    			int pos = to_int(split[1]);
	    			ClientHandler.times[pos] = value;
	    		}
	    		
	    		else if(line.startsWith("TILE")){
	    			try{
	    				System.out.println(line);
		    			String[] split = line.split(":");
		    			int posx = Integer.parseInt(split[2]);
		    			int posy = Integer.parseInt(split[3]);
//		    			System.out.println(posx + " : " + posy);
		    			String type = split[4];
	//	    			PlayState.tilemap.setTile(posx, posy, type);
		    			TileMap.map[posy][posx] = type;
	    			}catch(Exception e){
	    				e.printStackTrace();
	    			}
	    		}
	    		
	    		
	    		else if(line.startsWith("bullet")){
	    			String[] split = line.split(":");
//	    			Client.send("bullet:" + Menu.name + ":" + xSpawn + ":" + ySpawn + ":" + toX + ":" + (toY + r));
	    			
	    			float bx = to_float(split[4]);
	    			float by = to_float(split[5]);
	    			float b_toX = to_float(split[6]);
	    			float b_toY = to_float(split[7]);
	    			String name = split[3];
	    			ClientHandler.addBullet(bx, by, b_toX, b_toY, name);
	    		}
	    		
	    		
	    		else{
	    			String split[] = line.split(":");
//	    			if(!added.contains(split[10])){
//	    				Alerts.alerts.add(split[10] + " has joined the server!");
//	    				added.add(split[10]);
//	    			}
	    			ClientHandler.players[to_int(split[0])] = line;
//	    			ClientHandler.health[to_int(split[0])] = to_int(split[9]);
	    		}

	    	
	    	}
	    	closed = true;
	    } catch (IOException e) {
	    	System.err.println("IOException:  " + e);
	    }
	}
	
	public Integer to_int(String str){
		return Integer.parseInt(str);
	}
	public float to_float(String str){
		return Float.parseFloat(str);
	}
	
	
	
	
	
	
	
	
	
	
	
}