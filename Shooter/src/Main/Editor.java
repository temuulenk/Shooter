package Main;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class Editor extends BasicGameState {
	public Editor(int state) {}
	
	TrueTypeFont font;
	TrueTypeFont font1;
	
	
	int totalCols = 50;
	int totalRows = 50;
	int size = 32;
	
	int dx = 0;
	int dy = 0;
	
	String map[][] = new String[totalRows][totalCols];
	boolean moveMode = true;
	float hoverX, hoverY;
	
	
	public static ArrayList<String> string = new ArrayList<String>(0);
	
	private int currently_editing = 0;
	private int selected = -1;
	public static boolean chose_map = false;
	
	Image turret;
	Image bb;
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		font = Menu.font;
		font1 = Menu.font1;
		
		for(int row=0; row<totalRows; row++){
			for(int col=0; col<totalCols; col++){
				if(row == 0) map[row][col] = "b";
				else if(row == totalRows - 1) map[row][col] = "b";
				
				else if(col == 0) map[row][col] = "b";
				else if(col == totalRows - 1) map[row][col] = "b";
				
				else map[row][col] = "w";
			}
		}
		
		try {
			Load();
		} catch (JDOMException | IOException e) {
			e.printStackTrace();
		}
		
		currently_editing = string.size() - 1;
		
		
		turret = new Image("lib/res/Misc/turret.png");
		bb = new Image("lib/res/Misc/bb.png");
		
//		Thread t1 = new Thread(new Runnable() {
//		     public void run() {
//		    	 Client.running();
//		     }
//		});  
//		t1.start();
		
		
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.setBackground(new Color(220, 220, 220));
//		g.setBackground(Color.black);
		g.setBackground(Color.decode("#003366"));
		gc.setAlwaysRender(true);
		
		Input input = gc.getInput();
		int mx = input.getMouseX();
		int my = input.getMouseY();
		Rectangle mouse = new Rectangle(mx, my, 1, 1);
		
		
		
		
		
		if(string.size() == 0){
			for(int row=0; row<totalRows; row++){
				for(int col=0; col<totalCols; col++){
					if(row == 0) map[row][col] = "b";
					else if(row == totalRows - 1) map[row][col] = "b";
					
					else if(col == 0) map[row][col] = "b";
					else if(col == totalRows - 1) map[row][col] = "b";
				}
			}
			chose_map = true;
		}
		
		try {
			if(!chose_map){
				font.drawString((704 - font.getWidth("Please choose a map"))/2, 10, "Please choose a map", Color.black);
//				if(input.isKeyPressed(Input.KEY_BACK)) sbg.enterState(1);
				font.drawString(10, 544 - 20, "Back", Color.black);
				int s_size = 2;
				for(int i=0;i<string.size();i++){
					int x = 50 + i * ((60) * s_size);
					g.setColor(Color.orange);
					Rectangle box = new Rectangle(x - 4, 60 - 4, s_size * 50 + 8, s_size * 50 + 8);
					
					Rectangle t = new Rectangle(x, 60 + (s_size * 50) + 10, 50, 20);
					if(mouse.intersects(t)){
						font.drawString(x + (s_size * 50 - font.getWidth("Remove"))/2, 60 + (s_size * 50) + 5, "Remove", Color.red);
						if(input.isMousePressed(0))
							string.remove(i);
					}else{
						font.drawString(x + (s_size * 50 - font.getWidth("Remove"))/2, 60 + (s_size * 50) + 5, "Remove", Color.black);
					}
					
					if(mouse.intersects(box)){
						if(input.isMousePressed(0)) selected = i;
					}
					if(mouse.intersects(box) || selected == i) g.fill(box);
					font.drawString(x + ((50 * s_size) - font.getWidth("Custom Map " + i))/2, 30, "Custom Map " + (i+1), Color.black);
					String[] split = string.get(i).split(":");
					for(int row=0;row<50;row++){
						String str = split[row];
						for(int col=0;col<50;col++){
							String s = str.substring(col, col + 1);
							if(s.equals("b")) g.setColor(Color.gray);
							if(s.equals("w")) g.setColor(Color.black);
							if(s.equals("a")) {
								g.setColor(Color.blue);
							}
								
							g.fillRect(x + col * s_size, 60 + row * s_size, s_size, s_size);
						}
					}
					
					
				}
				
				
				Rectangle b = new Rectangle((704 - font.getWidth("Select"))/2, 400, font.getWidth("Select"), font.getHeight("Select"));
				if(mouse.intersects(b)){
					font.drawString((704 - font.getWidth("Select"))/2, 400, "Select", Color.orange);
					if(input.isMousePressed(0)){
						String[] split = string.get(selected).split(":");
						for(int i=0;i<split.length;i++){
							for(int row=0;row<50;row++){
								String str = split[row];
								for(int col=0;col<50;col++){
									String s = str.substring(col, col + 1);
									map[row][col] = s;
								}
							}
						}
						currently_editing = selected;
						chose_map = true;
						moveMode = true;
					}
				}else{
					font.drawString((704 - font.getWidth("Select"))/2, 400, "Select", Color.black);
				}
			}
		}catch(Exception e){}
		
		if(!chose_map) return;
		
		if(input.isKeyPressed(Input.KEY_SPACE)){
			moveMode = !moveMode;
		}
		
		int sx = mx + dx;
		int sy = my + dy;
		
		hoverX = (sx) / size;
		hoverY = (sy) / size;
		
		
		for(int row=0; row<totalRows; row++){
			for(int col=0; col<totalCols; col++){
				int x = col * size;
				int y = row * size;
				Rectangle box = new Rectangle(x - dx, y - dy, size, size);
				try{
					if(!moveMode && mouse.intersects(box) && input.isMouseButtonDown(0) && !map[sy/size][sx/size].equals("b")){ 
						map[sy / size][sx / size] = "a";
					}
					if(!moveMode && mouse.intersects(box) && input.isMouseButtonDown(1) && !map[sy/size][sx/size].equals("b")){
						map[sy / size][sx / size] = "w";
					}
				}catch(Exception e){
					e.printStackTrace();
				}
				
				if(map[row][col].equals("b")){
					g.setColor(Color.gray);
				}
				
				else if(map[row][col].equals("a")){
					g.setColor(Color.blue);
//					Play.wall.getScaledCopy(size, size).draw(col * size, row * size); // TODO
				}
				
				else if(map[row][col].equals("w")){
					g.setColor(Color.black);
				}
				
				g.fillRect(x - dx, y - dy, size, size);
				
			}
		}
		
		if(sx/size > 0 && sy/size > 0 && sx/size < 50 && sy/size < 50){
			g.drawRect(hoverX * size - dx, hoverY * size - dy, size, size);
		}
		
		
	    int dWheel = Mouse.getDWheel();
	    if (dWheel < 0) {
	    	if(size > 1){
	    		size -= 1;
	    	}
	    } else if (dWheel > 0){
	    	if(size < 50){
	    		size += 1;
	    		dx --;
	    	}
	    }
		
		minimap(g, mouse, mx, my, dx, dy);
		
		if(moveMode){
			String s = "Click and drag mouse to move map";
			int w = font.getWidth(s);
			if(input.isMouseButtonDown(0)){
				dx -= Mouse.getDX();
				dy += Mouse.getDY();
			}
			g.setColor(new Color(0, 0, 0, .8f));
			g.fillRect((704 - w)/2 - 10, 5, w + 20, 25);
			font.drawString((704 - w)/2, 10, s);
		}
		
		if(input.isKeyPressed(Input.KEY_C)){
			clear();
		}
		if(input.isKeyPressed(Input.KEY_F)){
			fill();
		}
		if(input.isKeyPressed(Input.KEY_BACK)){
			chose_map = false;
		}
		
		int centerx = (704 - totalCols * size) / 2;
		int centery = (544 - totalRows * size) / 2;
		
		if(input.isKeyPressed(Input.KEY_P)){
			dx = centerx - centerx * 2;
			dy = centery - centery * 2;
		}

//		g.fillRect(5, 5, 100, 100);
		font.drawString(10, 10, "Size: " + size);
		int disX = sx/size;
		int disY = sy/size;
		if(disX < 0) disX = 0;
		if(disX > 50) disX = 50;
		if(disY < 0) disY = 0;
		if(disY > 50) disY = 50;
		font.drawString(10, 25, "X: " + disX);
		font.drawString(10, 40, "Y: " + disY);
		
		if(input.isKeyPressed(Input.KEY_ENTER)){
			//TODO Send map to server
			System.out.println(save());
			Client.send("MAP:" + save());
		}
		
		
		
		font.drawString(10, 100, "Legend:");
		font.drawString(10, 120, "[C] Clear.");
		font.drawString(10, 140, "[F] Fill.");
		font.drawString(10, 160, "[Space] Change map drag.");
		font.drawString(10, 180, "[ENTER] Send map to server.");
		font.drawString(10, 200, "[Right Click] Add unblocked tile.");
		font.drawString(10, 220, "[Left Click] Add blocked tile.");
		font.drawString(10, 240, "[Back] Map selection.");
		font.drawString(10, 260, "[S] Save map.");
		
		
		if(input.isKeyPressed(Input.KEY_S)){
			if(string.size() == 0) string.add(save());
			else string.set(currently_editing, save());
			Write();
			try {
				Load();
			} catch (JDOMException | IOException e) {
				e.printStackTrace();
			}
		}
		
		
		
		
		
		
	}
	
	public String save(){
		String str = "";
		
		for(int row=0; row<totalRows; row++){
			for(int col=0; col<totalCols; col++){
				String s = map[row][col];
				str += s;
			}
			str += ":";
		}
		return str;
	}
	
	public void minimap(Graphics g, Rectangle mouse, int mx, int my, int SX, int SY){
		int skewy = 544 - 10 - 50;
		g.setColor(new Color(0, 0, 0, .7f));
		g.fillRect(10, skewy, 50, 50);
		int mini_size = 1;
		for(int row=0; row<totalRows; row++){
			for(int col=0; col<totalCols; col++){
				int x = col * mini_size;
				int y = row * mini_size;
				if(map[row][col].equals("b")){
					g.setColor(Color.white);
					g.fillRect(x + 10, skewy + y , mini_size, mini_size);
				}
				
				else if(map[row][col].equals("a")){
					g.setColor(Color.blue);
					g.fillRect(x + 10, skewy + y, mini_size, mini_size);
				}
			}
		}
		
		int ww = 704 / size;
		int hh = 544 / size;
		if(ww > 50) ww = 50;
		if(hh > 50) hh = 50;
		Rectangle view = new Rectangle(10 + (SX/32), skewy + 1 + (SY/32), ww, hh);
		g.draw(view);
		
	}
	
	public void clear(){
		for(int row=0; row<totalRows; row++){
			for(int col=0; col<totalCols; col++){
				if(!map[row][col].equals("b")){
					map[row][col] = "w";
				}
			}
		}
	}
	
	public void fill(){
		for(int row=0; row<totalRows; row++){
			for(int col=0; col<totalCols; col++){
				if(!map[row][col].equals("b")){
					map[row][col] = "a";
				}
			}
		}
	}
	
	public float center(float x, float y){
		return (int) (x + y)/2;
	}
	
	public float to_float(String str){ return Float.parseFloat(str); }
	public int to_int(String str) { return Integer.parseInt(str); }
	
	public void displayFPS(TrueTypeFont font, int fps){
		font.drawString(10, 10, "FPS: " + fps);
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
	}

	
	public void Load() throws JDOMException, IOException{
    	SAXBuilder reader = new SAXBuilder();
    	Document document = reader.build(new File("lib/res/Custom Maps.xml"));
    	Element root = document.getRootElement();
    	
    	for(Object str: root.getChildren()){
    		Element e = (Element) str;
    		String user = e.getText();
    		boolean found = true;
    		for(String s: string){
    			if(s.equals(user)){
    				found = false;
    				break;
    			}
    		}
    		if(found)
    			string.add(user);
//    		System.out.println(user);
    	}
    	
    	
	}
	


	public static void Write() {
    	Document document = new Document();
    	Element root = new Element("document");
    	document.setRootElement(root);
    	XMLOutputter outputter = new XMLOutputter();

    	
    	for(int i=0;i<string.size();i++){
    		root.addContent(new Element("Map").setText(string.get(i)));
    	}
    	

    	try {
			outputter.output(document , new FileOutputStream(new File("lib/res/Custom Maps.xml")));
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
	}
	
	
	
	
	
	
	public int getID() {
		return 3;
	}

}
