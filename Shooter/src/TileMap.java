import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;



public class TileMap {
	
	private int x;
	private int y;
	
	private int tileSize;
	public static String[][] map;
	private int mapWidth;
	private int mapHeight;
	
	SpriteSheet wall;
	Image background;
	
	boolean inBound = false;
	public static float mapX;
	public static float mapY;
	
	
	public TileMap() throws SlickException {
		wall = new SpriteSheet("lib/res/Map/wall.png", 32, 32);
		background = new Image("lib/res/Map/background.png");
		
		tileSize = 32;
		
	    try{
	    	InputStream in = getClass().getResourceAsStream("/Maps/testmap.txt");
		    BufferedReader br = new BufferedReader(new InputStreamReader(in));
			mapWidth = Integer.parseInt(br.readLine());
			mapHeight = Integer.parseInt(br.readLine());
	//		    mapWidth = 66;
	//		    mapHeight = 50;
			map = new String[mapHeight][mapWidth];
		    
	//		    map = new String[mapHeight][mapWidth];
		    String delimiters = " ";
		    for (int row = 0; row < mapHeight; row++){
		    	String line = br.readLine();
		        String[] tokens = line.split(delimiters);
		        for (int col = 0; col < mapWidth; col++) {
		        	map[row][col] = tokens[col];
		        }
		    }
		}catch (Exception e){
			System.out.println("Error loading map");
		}
	}
	
	
	
	public int getX() { return x; }
	public int getY() { return y; }
	public int getColTile(int x) { return x / tileSize; }
	public int getRowTile(int y) { return y / tileSize; }
	public String getTile(int row, int col) { return map[row][col]; }
	public int getTileSize() { return tileSize; }
	
	
	
	
	
	public void save(){
		try{
			URL resourceUrl = getClass().getResource("/Maps/testmap.txt");
			File file = new File(resourceUrl.toURI());
			OutputStream output = new FileOutputStream(file);
		    Writer writer1 = new OutputStreamWriter(output);
		    BufferedWriter writer = new BufferedWriter(writer1);
		    writer.write("50");
		    writer.newLine();
		    writer.write("50");
		    writer.newLine();
			for(int row=0;row<mapHeight;row++){
				for(int col=0;col<mapWidth;col++){
					String rc = map[row][col];
					String r = "" + rc;
					writer.write(r + " ");
				}
				writer.newLine();
			}
			writer.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	public void draw(Graphics g, GameContainer gc, Input input) {
		
		
		for(int row = 0; row < mapHeight; row++) {
			for(int col = 0; col < mapWidth; col++) {
				
				String rc = map[row][col];
				
				inBound = (Math.abs((int) mapX - (col*tileSize)) < (704 / 2) + 40)
						  && (Math.abs((int) mapY - (row*tileSize)) < (544 / 2) + 40);	
				
				if(rc.equals("w")){
//					if(inBound)
//						background.draw(col * 32, row * 32);
				}
				
				blocks(g, "w", row, col, wall);

				
				
			}
		}
		
	}
	
	public void blocks(Graphics g, String str, int row, int col, SpriteSheet sprites){
		if(map[row][col].equals(str)){
			String n = map[row-1][col];
			String s = map[row+1][col];
			String w = map[row][col-1];
			String e = map[row][col+1];
//			if(box.intersects(Play.b)){
			if(inBound){
//				Play.drawNewWall(g, col * tileSize, row * tileSize, tileSize, tileSize);
				if(!n.equals(str) && !w.equals(str) && e.equals(str) && s.equals(str)){
					g.drawImage(sprites.getSubImage(0, 0), col*32, row*32);
				}else if(!n.equals(str) && w.equals(str) && !e.equals(str) && s.equals(str)){
					g.drawImage(sprites.getSubImage(1, 0), col*32, row*32);
				}else if(n.equals(str) && !w.equals(str) && !e.equals(str) && s.equals(str)){
					g.drawImage(sprites.getSubImage(2, 0), col*32, row*32);
				}else if(!n.equals(str) && w.equals(str) && e.equals(str) && s.equals(str)){
					g.drawImage(sprites.getSubImage(3, 0), col*32, row*32);
				}else if(n.equals(str) && !w.equals(str) && e.equals(str) && !s.equals(str)){
					g.drawImage(sprites.getSubImage(0, 1), col*32, row*32);
				}else if(n.equals(str) && w.equals(str) && !e.equals(str) && !s.equals(str)){
					g.drawImage(sprites.getSubImage(1, 1), col*32, row*32);
				}else if(!n.equals(str) && !w.equals(str) && e.equals(str) && !s.equals(str)){
					g.drawImage(sprites.getSubImage(2, 1), col*32, row*32);
				}else if(n.equals(str) && w.equals(str) && e.equals(str) && !s.equals(str)){
					g.drawImage(sprites.getSubImage(3, 1), col*32, row*32);
				}else if(n.equals(str) && w.equals(str) && e.equals(str) && s.equals(str)){
					g.drawImage(sprites.getSubImage(0, 2), col*32, row*32);
				}else if(!n.equals(str) && !w.equals(str) && !e.equals(str) && !s.equals(str)){
					g.drawImage(sprites.getSubImage(1, 2), col*32, row*32);
				}else if(!n.equals(str) && w.equals(str) && !e.equals(str) && !s.equals(str)){
					g.drawImage(sprites.getSubImage(2, 2), col*32, row*32);
				}else if(!n.equals(str) && w.equals(str) && e.equals(str) && !s.equals(str)){
					g.drawImage(sprites.getSubImage(3, 2), col*32, row*32);
				}else if(n.equals(str) && !w.equals(str) && e.equals(str) && s.equals(str)){
					g.drawImage(sprites.getSubImage(0, 3), col*32, row*32);
				}else if(n.equals(str) && w.equals(str) && !e.equals(str) && s.equals(str)){
					g.drawImage(sprites.getSubImage(1, 3), col*32, row*32);
				}else if(n.equals(str) && !w.equals(str) && !e.equals(str) && !s.equals(str)){
					g.drawImage(sprites.getSubImage(2, 3), col*32, row*32);
				}else if(!n.equals(str) && !w.equals(str) && !e.equals(str) && s.equals(str)){
					g.drawImage(sprites.getSubImage(3, 3), col*32, row*32);
				}
			}
		}
		
	}
	
	
	
	
}









