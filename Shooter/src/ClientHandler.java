import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;


public class ClientHandler {
	
	Random random = new Random();
	public static String[] players = new String[10];
	public String[] screen_name = new String[10];
	public ArrayList<String> names = new ArrayList<String>(0);
	public ArrayList<Integer> health = new ArrayList<Integer>(0);
	
	public static ArrayList<Bullet> bullets = new ArrayList<Bullet>(0);
	
	private ArrayList<Circles> circles = new ArrayList<Circles>(0);
	
	private boolean show_players = true;
	
	public ClientHandler() { 
		
	}
	
	
	public void draw(Graphics g, GameContainer gc, Input input, TrueTypeFont font){
		
		g.setColor(Color.red);
		
		for(int i=0;i<bullets.size();i++){
			if(bullets.get(i).hit) bullets.remove(i);
			else{
				bullets.get(i).draw(g);
			}
		}
		
		// Handle player positions..
		try{
				
			
			int count = 0;
			for(String s: players){
				if(s != null) {
					Enemies e = new Enemies();
					e.logic(g, gc, s);
					Rectangle enemy_rect = new Rectangle(e.x, e.y, 24, 27);
					screen_name[count] = e.name;
					healthbar(g, font, e.x - (30 - 24)/2, e.y - 20, e.health, Player.max_health, 30);
					
					try{
						// Handle if bullets hits any of the clients
						for(int i=0;i<bullets.size();i++){
							if(bullets.get(i).rect.intersects(enemy_rect)){
								bullets.get(i).hit = true;
							}
							if(bullets.get(i).rect.intersects(Player.player_rect)) {
								Player.health --;
								bullets.get(i).hit = true;
							}
						}
					}catch(Exception t){
						t.printStackTrace();
					}
					
					for(int i=0;i<Gun.bullets.size();i++){
						if(Gun.bullets.get(i).rect.intersects(enemy_rect)) Gun.bullets.get(i).hit = true;
					}
					
				}
				count ++;
			}
			
			
			
			// Handle flying animation..
			for(String s: players){
				if(s != null){
					String[] split = s.split(":");
					int green = random.nextInt(100);
					int size = random.nextInt(12 - 3) + 3;
					float x = Float.parseFloat(split[7]);
					float y = Float.parseFloat(split[8]);
					if(Boolean.parseBoolean(split[2])){
						if(Boolean.parseBoolean(split[5]))
							circles.add(new Circles(x, y + 13, green, size));
						else
							circles.add(new Circles(x + (24 - 4), y + 13, green, size));
					}
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		for(int j=0;j<circles.size();j++){
			if(circles.get(j).remove){
				circles.remove(j);
			}else{
			circles.get(j).draw(g, gc);
			}
		}
		
		
		

		
	}
	
	public static void addBullet(float x, float y, float toX, float toY){
		bullets.add(new Bullet(Bullet.bullet, x, y, toX, toY, 5));
	}
	
	
	public void names(TrueTypeFont font, Input input){
		// Show player information..
		try{
			if(input.isKeyPressed(Input.KEY_TAB)){
				show_players = !show_players;
			}

			if(show_players){
				int height = font.getHeight("F");
				font.drawString(10, 30, "Connected players < " + names.size() + " >", new Color(255, 255, 255, .9f));
				font.drawString(20, 35 + height, Menu.name, new Color(255, 255, 255, .8f));
				
				for(int i=1;i<screen_name.length;i++){
					if(screen_name[i] != null){
						font.drawString(20, height + 35 + (i * height), screen_name[i], new Color(255, 255, 255, .8f));
						
					}
				}
			}
		}catch(Exception e){
		}
	}
	
	
	public void healthbar(Graphics g, TrueTypeFont font, float x, float y, int current, int max, int total){
		float scale = total/max;
		
		int bar_height = 7;
		
		Rectangle bar = new Rectangle(x, y, total, bar_height);
		g.fillRect(x, y, scale * current, bar_height);
		g.draw(bar);
		
		int w = font.getWidth("" + current);
		int h = font.getHeight("" + current);
		
		font.drawString(
			x + (total - w)/2, 
			y + (bar_height - h)/2,
			"" + current
		);
		
		
	}
	
	
	
	
	
	
	
	
	

}
