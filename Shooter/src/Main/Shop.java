package Main;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;

public class Shop {
	TrueTypeFont font;
	
	Image shop;
	Image box;
	Image selected;
	Image highlight;
	Image closeHover;
	Image xHover;
	Image buyHover;
	
	
	float x;
	float y;
	
	int skewX = 32;
	int skewY = 31;
	int textSkew = 69;
	int box_width = 72;
	
	int selection = -1;
	
	public Shop(TrueTypeFont f) throws SlickException {
		font = f;
		
		shop = new Image("lib/res/Misc/Shop.png");
		box = new Image("lib/res/Misc/shop_box.png");
		selected = new Image("lib/res/Misc/shop_selected.png");
		highlight = new Image("lib/res/Misc/shop_highlight.png");
		closeHover = new Image("lib/res/Misc/Shop_closeHover.png");
		xHover = new Image("lib/res/Misc/Shop_xHover.png");
		buyHover = new Image("lib/res/Misc/Shop_buyHover.png");
		
		x = (704 - shop.getWidth())/2;
		y = (544 - shop.getHeight())/2;
	}
	
	
	public void draw(Graphics g, GameContainer gc) throws SlickException{
		
		Input input = gc.getInput();
		int mx = input.getMouseX();
		int my = input.getMouseY();
		Rectangle mouse = new Rectangle(mx, my, 1, 1);
		
		shop.draw(x, y);
		
		for(int i=0;i<5;i++){
			Rectangle temp = new Rectangle(placex_box(i), y + skewY, width(box), height(box));
			selected.draw(placex_box(i), y + skewY);
			if(mouse.intersects(temp) && input.isMousePressed(0)){
				selection = i;
			}
			font.drawString(placex_box(i) + 10, y + skewY + 42, "$500", Color.decode("#C30000"));
		}
		
		for(int i=0;i<5;i++){
			font.drawString(placementX(i), y + skewY - height("P") - 5, "Pistol", Color.decode("#C1C3CE"));
		}
		
		if(selection >= 0)
			highlight.draw(placex_box(selection) - 2, y + skewY - 2);
		
		font.drawString(10, 10, "$150", Color.decode("#00942D"));
		
		
		// grayed out strings C1C3CE
		// nice red color C30000
		
		font.drawString((704 - width("Shop"))/2, y - height("S"), "Shop");
		
		Rectangle buyRect = new Rectangle(x + 380, y + 190, width(buyHover), height(buyHover));
		Rectangle closeRect = new Rectangle(x + 380, y + 240, width(closeHover), height(closeHover));
		Rectangle xRect = new Rectangle(x + width(shop) - width(xHover), y, width(xHover), height(xHover));
		
		if(mouse.intersects(xRect)){
			xHover.draw(x + width(shop) - width(xHover), y);
		}
		if(mouse.intersects(buyRect)){
			buyHover.draw(x + 380, y + 189);
		}
		if(mouse.intersects(closeRect)){
			closeHover.draw(x + 380, y + 240);
		}
		
		
		
	}
	
	
	private float width(String text){ return font.getWidth(text); }
	private float width(Image img){ return img.getWidth(); }
	
	private float height(String text){ return font.getHeight(text); }
	private float height(Image img){ return img.getHeight(); }
	
	
	
	private float placementX(int pos){
		return x + skewX + (box_width - width("Bought"))/2 + (87 * pos);
	}
	
	private float placex_box(int pos){
		return x + skewX + (width(selected) + 4) * pos;
	}
	
	
	
	
	
	
	
	
	
}
