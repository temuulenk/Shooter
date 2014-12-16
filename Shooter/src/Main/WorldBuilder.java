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

public class WorldBuilder extends BasicGameState {
	public WorldBuilder(int state){}
	TrueTypeFont font;
	TrueTypeFont font1;
	
	
	String mapName;
	int mapCount;
	String[][] map;
	
	int xSkew = 100;
	int ySkew = 100;
	int cols;
	int rows;
	int size = 2;

	static ArrayList<Maps> maps = new ArrayList<Maps>(0);
	
	public static boolean selecting = true;
	int selected = -1;
	
	int currentlyEditing;
	int lookingAt;
	
	boolean moveMap = true;
	
	Image platform;
	Image toolbar;
	Image button;
	
	String[][] edit;
	
	int mx;
	int my;
	int pensize = 1;
	
	int choose;
	Image upArrow;
	
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		font = Menu.font;
		font1 = Menu.font1;
		
		platform = new Image("lib/res/Misc/Shop_platform.png");
		toolbar = new Image("lib/res/Misc/toolbar.png");
		button = new Image("lib/res/Misc/button.png");
		upArrow = new Image("lib/res/Misc/upArrow.png");
		
		rows = 200;
		cols = 200;
		
		map = new String[rows][cols];
		
		
		for(int row=0; row<rows; row++){
			for(int col=0; col<cols; col++){
				if(row == 0) map[row][col] = "b";
				else if(row == rows - 1) map[row][col] = "b";
				
				else if(col == 0) map[row][col] = "b";
				else if(col == rows - 1) map[row][col] = "b";
				
				else map[row][col] = "w";
			}
		}
		
		
		try {
			Load();
		} catch (JDOMException | IOException e) {
			e.printStackTrace();
		}
		
		
	}
	

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.setBackground(Color.decode("#3B496B"));
		gc.setAlwaysRender(true);
		Input input = gc.getInput();
		Rectangle mouse = new Rectangle(input.getMouseX(), input.getMouseY(), 1, 1);
		
		if(selecting == true){
			
			if(input.isKeyPressed(Input.KEY_BACK)) sbg.enterState(1);
			
			String[][] temp = maps.get(choose).map;
			for(int row=0; row<maps.get(choose).getRows(); row++){
				for(int col=0; col<maps.get(choose).getCols(); col++){
					if(temp[row][col].equals("b")) g.setColor(Color.black);
					if(temp[row][col].equals("a")) g.setColor(Color.pink);
					else if(temp[row][col].equals("w")) g.setColor(Color.decode("#3B496B"));
					g.fillRect((704 - maps.get(choose).getCols())/2 + col, 100 + row, 1, 1);
				}
			}
			
			String name = maps.get(choose).getName();
			font.drawString((704 - font.getWidth("Creator " + name))/2, 75, "Creator " + name);
			
			font.drawString((704 - font.getWidth("D - Right"))/2, 10, "D - Right");
			font.drawString((704 - font.getWidth("A - Left"))/2, 25, "A - Left");
			
			if(choose < maps.size() - 1){
				upArrow.setRotation(90);
				upArrow.draw((704 - maps.get(choose).getCols())/2 + maps.get(choose).getCols() + 20, 100 + (maps.get(choose).getRows() - upArrow.getHeight())/2);
			}
			if(choose > 0){
				upArrow.setRotation(-90);
				upArrow.draw((704 - maps.get(choose).getCols())/2 - 35, 100 + (maps.get(choose).getRows() - upArrow.getHeight())/2);
			}
			
		
			if(input.isKeyPressed(Input.KEY_D) && choose < maps.size() - 1) choose ++;
			if(input.isKeyPressed(Input.KEY_A) && choose > 0) choose --;
			

			platform.draw(10, 10);
			write(10 + (width(platform) - width("" + maps.size()))/2,
				10 + (height(platform) - height("" + maps.size()))/2, 
				"" + maps.size(),
				Color.white
			);
			
			if(maps.size() == 1) selected = 0;
			
	
			Rectangle buttonr = new Rectangle(center(button), 400, width(button), height(button));
			
			selected = choose;
			
			if(selected >= 0){
				if(!mouse.intersects(buttonr)){
					button.draw(center(button), 400);
					write(center("Choose"), 404, "Choose", Color.decode("#C1C3CE"));
				}
				if(mouse.intersects(buttonr)){
					button.draw(center(button), 399);
					write(center("Choose"), 403, "Choose", Color.white);
					if(input.isMousePressed(0)){
						edit = maps.get(selected).map;
						selecting = false;
						moveMap = true;
					}
				}
			}
		}
		
		
		
		
		if(selecting == false){
			resize();
			move(input);
			g.draw(mouse);
			int totalRows = maps.get(selected).getRows();
			int totalCols = maps.get(selected).getCols();
			
			
			mx = input.getMouseX() - xSkew;
			my = input.getMouseY() - ySkew;
			
			
			Rectangle pen = new Rectangle((mx + xSkew) - (size * pensize)/2, (my + ySkew) - (size * pensize)/2, size * pensize, size * pensize);
			
			input.enableKeyRepeat();
			
			if(input.isKeyPressed(Input.KEY_W)) pensize ++;
			if(input.isKeyPressed(Input.KEY_S) && pensize > 1) pensize --;
			
			
			for(int row=0; row<totalRows; row++){
				for(int col=0; col<totalCols; col++){
					Rectangle b = new Rectangle(xSkew + col * size, ySkew + row * size, size, size);
					if(pen.intersects(b) && !moveMap){
						if(row > 0 && row < totalRows - 1 && col > 0 && col < totalCols - 1){
							if(input.isMouseButtonDown(0)){
								edit[row][col] = "a";
							}
							if(input.isMouseButtonDown(1)){
								edit[row][col] = "w";
							}
						}
					}
					
					if(edit[row][col].equals("b")) g.setColor(Color.black);
					else if(edit[row][col].equals("a")) g.setColor(Color.pink);
					else if(edit[row][col].equals("w")) g.setColor(Color.decode("#3B496B"));
					
					g.fillRect(xSkew + col * size, ySkew + row * size, size, size);
				}
			}
			
			
			
			g.setColor(Color.white);
			g.draw(pen);
			
			font.drawString(10, 10, "" + (size));
			
			if(mx/size < 0) font.drawString(10, 25, "0");
			else font.drawString(10, 25, "" + (mx/size));
			if(my/size < 0) font.drawString(10, 40, "0");
			else font.drawString(10, 40, "" + (my/size));
			
			if(input.isKeyPressed(Input.KEY_BACK)){
				selecting = true;
			}
			
			if(input.isKeyPressed(Input.KEY_ENTER)){
				Client.send(maps.get(choose).content());
			}
			
		}
		
		
		
		
	}
	
	
	
	public void resize(){
	    int dWheel = Mouse.getDWheel();
	    if (dWheel < 0 && size > 1) {
	    	size --;
	    } else if (dWheel > 0 && size < 50){
	        size ++;
	    }
	}
	
	public void move(Input input){
		if(input.isKeyPressed(Input.KEY_SPACE)){
			moveMap = !moveMap;
		}
		if(moveMap && input.isMouseButtonDown(0)){
			xSkew += Mouse.getDX();
			ySkew -= Mouse.getDY();
		}
	}
	
	public int width(String text){
		return font.getWidth(text);
	}
	public int height(String text){
		return font.getHeight(text);
	}
	public int width(Image img){
		return img.getWidth();
	}
	public int height(Image img){
		return img.getHeight();
	}
	
	public int width(int pos, int num) {
		int cols = maps.get(pos).totalCols;
		return num * cols;
	}
	public int height(int pos, int num){
		int rows = maps.get(pos).totalRows;
		return num * rows;
	}
	public int center(String text){
		return (704 - width(text))/2;
	}
	public int center(Image img){
		return (704 - width(img))/2;
	}
	
	
	public void write(int x, int y, String text, Color color){
		font.drawString(x, y, text, color);
	}
	
	public void write(int y, String text, Color color){
		font.drawString((704 - font.getWidth(text))/2, y, text, color);
	}
	
	

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		
	}
	
	public void Load() throws JDOMException, IOException{
    	SAXBuilder reader = new SAXBuilder();
    	Document document = reader.build(new File("lib/res/Custom Maps.xml"));
    	Element root = document.getRootElement();
    	
    	int k = 0;
    	for(Object str: root.getChildren()){
    		Element e = (Element) str;
    		String user = e.getText();
    		String[][] temp;
    		String[] split = user.split(",");
    		temp = new String[Integer.parseInt(split[1])][Integer.parseInt(split[2])];
    		String value = split[3];
    		String[] s = value.split(":");
    		
    		for(int i=0;i<s.length;i++){
    			String ss = s[i];
    			for(int j=0;j<ss.length();j++){
    				temp[i][j] = ss.substring(j, j+1);
    			}
    		}
    		maps.add(new Maps(split[0], Integer.parseInt(split[1]), Integer.parseInt(split[2])));
    		maps.get(k).setMap(temp);
    		k++;
    	}
	}
	
	
	public static void Write() {
    	Document document = new Document();
    	Element root = new Element("document");
    	document.setRootElement(root);
    	XMLOutputter outputter = new XMLOutputter();

    	
    	for(int i=0;i<maps.size();i++){
    		root.addContent(new Element("Map"+i).setText(maps.get(i).content()));
    	}

    	try {
			outputter.output(document , new FileOutputStream(new File("lib/res/Custom Maps.xml")));
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
	}
	
	
//	public void minimap(Graphics g, Rectangle mouse, int mx, int my, int SX, int SY){
//		int skewy = 544 - 10 - 50;
//		g.setColor(new Color(0, 0, 0, .7f));
//		g.fillRect(10, skewy, 50, 50);
//		int mini_size = 1;
//		for(int row=0; row<totalRows; row++){
//			for(int col=0; col<totalCols; col++){
//				int x = col * mini_size;
//				int y = row * mini_size;
//				if(map[row][col].equals("b")){
//					g.setColor(Color.white);
//					g.fillRect(x + 10, skewy + y , mini_size, mini_size);
//				}
//				
//				else if(map[row][col].equals("a")){
//					g.setColor(Color.blue);
//					g.fillRect(x + 10, skewy + y, mini_size, mini_size);
//				}
//			}
//		}
//		
//		int ww = 704 / size;
//		int hh = 544 / size;
//		if(ww > 50) ww = 50;
//		if(hh > 50) hh = 50;
//		Rectangle view = new Rectangle(10 + (SX/32), skewy + 1 + (SY/32), ww, hh);
//		g.draw(view);
//		
//	}
	

	public int getID() {
		return 3;
	}

}
