package Main;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;


public class Alerts {
	
	
	public static ArrayList<String> alerts = new ArrayList<String>(0);
	int count = 0;
	Timer timer;
	boolean next;
	
	public class TimerListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			count ++;
		}
	}
	
	
	private Image skull;
	private Image crown;
	
	public Alerts() throws SlickException {
		timer = new Timer(1000, new TimerListener());
		
		skull = new Image("res/skull.png");
		crown = new Image("res/crown.png");
	}
	
	public void append(String str){
		boolean found = true;
		for(String s: alerts){
			if(str.equals(s)){
				found = false;
				break;
			}
		}
		if(found) alerts.add(str);
	}
	
	
	public void logic(Graphics g, GameContainer gc, TrueTypeFont font, TrueTypeFont font1){
		
		if(alerts.size() == 0){
			timer.stop();
			count = 0;
			next = false;
		}
		
//		font.drawString(100, 100, "" + alerts.size());
		
		if(alerts.size() == 0) return;
		
//		int w = font.getWidth("< Important Messages >");
//		font.drawString((gc.getWidth() - w)/2, 10, "< Important Messages >");

		
		if(alerts.get(0).startsWith("kill")){
			String[] split = alerts.get(0).split(":");
			death(font1, split[1], split[2]);
		}else if(alerts.get(0).startsWith("winner")){
			String[] split = alerts.get(0).split(":");
			win(font1, split[1]);
		}
		
		else{
			font.drawString(
				(gc.getWidth() - font.getWidth(alerts.get(0)))/2,
				35,
				alerts.get(0),
				Color.white
			);
		}
		
		
		if(alerts.size() > 0 && count <= 2){
			timer.start();
		}
		if(count >= 2){
			alerts.remove(0);
			count = 0;
		}
		
		
		
		
	}
	
	
	public void death(TrueTypeFont font, String str, String str1){
		int w = skull.getWidth();

		skull.draw((Main.WIDTH - w)/2, 10);

		String s = "- " + str + " has slain " + str1 + " -";
		int w1 = font.getWidth(s);
		
		font.drawString((Main.WIDTH - w1)/2, 75, s);
		
	}
	
	public void win(TrueTypeFont font, String str){
		int w = crown.getWidth();

		crown.draw((Main.WIDTH - w)/2, 10);

		String s = "- " + str + " has won -";
		int w1 = font.getWidth(s);
		
		font.drawString((Main.WIDTH - w1)/2, 75, s);
		
	}
	
	
	
	
	
}
