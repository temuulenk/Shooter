package Main;
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
	public static String[] screen_name = new String[10];
	public int[] health = new int[10];
	public static int[] times = new int[10];
	
	public static ArrayList<Bullet> bullets = new ArrayList<Bullet>(0);
	
	
	private boolean show_players = true;
	
	public ClientHandler() { 
		
		
	}
	
	
	public void draw(Graphics g, GameContainer gc, Input input, TrueTypeFont font){
		
		g.setColor(Color.red);
		
		// Handle player positions..
		try{
			
			int count = 0;
			for(String s: players){
				if(s != null) {
					Enemies e = new Enemies();
					e.logic(g, gc, s);
					screen_name[count] = e.name;
					health[count] = e.health;
					if(e.health > 0){
						healthbar(g, font, e.x - (30 - 24)/2, e.y - 20, e.health, Player.max_health, 30);
					}
				}
				count ++;
			}
			
			for(int i=0;i<bullets.size();i++){
				if(bullets.get(i).hit) bullets.remove(i);
				else{
					bullets.get(i).draw(g);
					
					if(bullets.get(i).rect.intersects(Player.player_rect) && Player.health > 0){
						Player.lastHitBy = bullets.get(i).owner;
						bullets.get(i).hit = true;
						Player.health --;
						Play.text.add(new FloatingText(Play.player.getX() +  + (16 - Menu.font1.getWidth("" + bullets.get(i).damage))/2, Play.player.getY(), "" + bullets.get(i).damage, Color.red));
					}
					
					for(int j=0;j<ClientHandler.players.length;j++){
						if(ClientHandler.players[j] != null){
							Enemies e = new Enemies();
							e.logic(g, gc, ClientHandler.players[j]);
							Rectangle enemy_rect = new Rectangle(e.x, e.y, 24, 27);
							
							if(bullets.get(i).rect.intersects(enemy_rect) && e.health > 0){
								bullets.get(i).hit = true;
								Play.text.add(new FloatingText(e.x +  + (16 - Menu.font1.getWidth("" + bullets.get(i).damage))/2, e.y, "" + bullets.get(i).damage, Color.red));
							}
							
						}
					}
				}
			}
			
		}catch(Exception e){
//			e.printStackTrace();
		}
		
		
		
		

		
	}
	
	public static void addBullet(float x, float y, float toX, float toY, String name){
		bullets.add(new Bullet(Bullet.bullet, x, y, toX, toY, name));
	}
	
	
	public void names(TrueTypeFont font, Input input){
		// Show player information..
		try{
			if(input.isKeyPressed(Input.KEY_TAB)){
				show_players = !show_players;
			}
			
			
			if(show_players){
				int height = font.getHeight("F");
				int pos = 0;
				int count = 0;
				for(String str: screen_name){
					if(str != null){
						if(health[count] > 0){
							font.drawString(10, 45 + (pos * height), str, Color.white);
							font.drawString(100, 45 + (pos * height), "" + health[count], Color.white);
						}else{
							font.drawString(10, 45 + (pos * height), str, Color.red);
							font.drawString(100, 45 + (pos * height), "" + times[count], Color.red);
						}
						pos ++;
					}
					count ++;
				}
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	public void healthbar(Graphics g, TrueTypeFont font, float x, float y, int current, int max, int total){
		g.setColor(Color.red);
		float scale = total/max;
		
		int bar_height = 7;
		
		Rectangle bar = new Rectangle(x, y, total, bar_height);
		g.fillRect(x, y, scale * current, bar_height);
		g.draw(bar);
		
		int w = font.getWidth("" + current);
		int h = font.getHeight("" + current);
		
		font.drawString(
			(int) (x + (total - w)/2), 
			(int) (y + (bar_height - h)/2),
			"" + current
		);
		
		
	}
	
	
	
	
	
	
	
	
	

}
