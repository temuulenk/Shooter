import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;


public class Chat {

	TrueTypeFont font;
	
	float HEIGHT = 200;
	float WIDTH = 400;
	
	float posY;
	float posX;
	
	public Chat(TrueTypeFont _font) {
		font = _font;
		
		posY = 544 - HEIGHT - 10;
		posX = 10;
	}
	
	
	public void draw(Graphics g, GameContainer gc){
		g.setLineWidth(2);
		g.setColor(new Color(0, 0, 0));
		Rectangle chatbox = new Rectangle(posX, posY, WIDTH, HEIGHT);
		g.draw(chatbox);
		
		g.setColor(new Color(0, 0, 0, .8f));
		g.fill(chatbox);
		
		font.drawString(center(posX, WIDTH, width("Chat")), posY - height("Chat"), "Chat", Color.black);
		
		font.drawString(10, 10, "$20 - Hello World", Color.decode("#00952E"));
		
		
		
	}
	
	public int height(String str){
		return font.getHeight(str);
	}
	public int width(String str){
		return font.getWidth(str);
	}
	
	public float center(float skew, float x, float y){
		return skew + (x - y)/2;
	}
	
	
	
	
	
}
