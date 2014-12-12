package Main;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;

import Weapons.M16;
import Weapons.Pistol;
import Weapons.Shotgun;
import Weapons.Weapons;

public class Shop {
	TrueTypeFont font;
	
	Image shop;
	Image box;
	Image selected;
	Image highlight;
	Image closeHover;
	Image xHover;
	Image buyHover;
	Image toolbar;
	
	public static Image M16_icon;
	public static Image Pistol_icon;
	public static Image Shotgun_icon;
	
	public static Image M16_white;
	public static Image Pistol_white;
	public static Image Python_white;
	public static Image Shotgun_white;
	
	
	
	
	float x;
	float y;
	
	int skewX = 32;
	int skewY = 31;
	int textSkew = 69;
	int box_width = 72;
	int selection = -1;
	
	int money = 150;
	
	
	public boolean open = false;
	
	public static ArrayList<Weapons> guns = new ArrayList<Weapons>(0);
	
	public Shop(TrueTypeFont BitFont) throws SlickException {
		font = BitFont;
		
		shop = new Image("lib/res/Misc/Shop.png");
		box = new Image("lib/res/Misc/shop_box.png");
		selected = new Image("lib/res/Misc/shop_selected.png");
		highlight = new Image("lib/res/Misc/shop_highlight.png");
		closeHover = new Image("lib/res/Misc/Shop_closeHover.png");
		xHover = new Image("lib/res/Misc/Shop_xHover.png");
		buyHover = new Image("lib/res/Misc/Shop_buyHover.png");
		toolbar = new Image("lib/res/Misc/toolbar.png");
		
		M16_icon = new Image("lib/res/Shop/M16_icon.png");
		Pistol_icon = new Image("lib/res/Shop/Pistol_icon.png");
		Shotgun_icon = new Image("lib/res/Shop/Shotgun_icon.png");
		
    	M16_white = new Image("lib/res/Misc/m16_white.png");
    	Pistol_white = new Image("lib/res/Misc/pistol_white.png");
    	Python_white = new Image("lib/res/Misc/python_white.png");
    	Shotgun_white = new Image("lib/res/Misc/shotgun_white.png");
		
		x = (704 - shop.getWidth())/2;
		y = (544 - shop.getHeight())/2;
		
		
		guns.add(new Pistol(Menu.Pistol, Pistol_icon, Pistol_white));
		guns.add(new M16(Menu.M16, M16_icon, M16_white));
//		guns.add(new Shotgun(Menu.Shotgun, Shotgun_icon, Shotgun_white));
		
		
	}
	
	
	public void draw(Graphics g, GameContainer gc) throws SlickException{
		
		Input input = gc.getInput();
		int mx = input.getMouseX();
		int my = input.getMouseY();
		Rectangle mouse = new Rectangle(mx, my, 1, 1);
		
		font.drawString(10, 10, "$" + money, Color.decode("#00942D"));
		
		if(input.isKeyPressed(Input.KEY_P)){
			open = !open;
		}
		
		if(!open) return;
		
		
		shop.draw(x, y);
		
		drawBoxes(mouse, input);
		
		
		if(selection >= 0)
			highlight.draw(placex_box(selection), y + skewY);
		
		
//		M16_icon1.draw(placex_box(0) + (72 - width(M16_icon1))/2, y + skewY + (40 - height(M16_icon1))/2);
		
		for(int i=0;i<guns.size();i++){
			guns.get(i).icon().draw(placex_box(i) + (72 - width(guns.get(i).icon()))/2, y + skewY + (40 - height(guns.get(i).icon()))/2);
			font.drawString(placex_box(i) + (int) (72 - width(guns.get(i).name()))/2, y + skewY - height(guns.get(i).name()), guns.get(i).name(), Color.decode("#C1C3CE"));
			
			if(money < guns.get(i).price()){
				font.drawString(placex_box(i) + (int) (72 - width("$" + guns.get(i).price()))/2, y + skewY + 42, "$" + guns.get(i).price(), Color.decode("#C30000"));
			}else{
				font.drawString(placex_box(i) + (int) (72 - width("$" + guns.get(i).price()))/2, y + skewY + 42, "$" + guns.get(i).price(), Color.decode("#00942D"));
			}
			
		
		}
		
		
		// grayed out strings C1C3CE
		// nice red color C30000
		
		font.drawString((704 - width("Shop"))/2, y - height("S"), "Shop");
		
		Rectangle buyRect = new Rectangle(x + 380, y + 190, width(buyHover), height(buyHover));
		Rectangle closeRect = new Rectangle(x + 380, y + 240, width(closeHover), height(closeHover));
		Rectangle xRect = new Rectangle(x + width(shop) - width(xHover), y, width(xHover), height(xHover));
		
		if(mouse.intersects(xRect)){
			xHover.draw(x + width(shop) - width(xHover), y);
			if(input.isMousePressed(0)){
				open = false;
			}
		}
		if(mouse.intersects(buyRect)){
			buyHover.draw(x + 380, y + 189);
		}
		if(mouse.intersects(closeRect)){
			closeHover.draw(x + 380, y + 240);
			if(input.isMousePressed(0)){
				open = false;
			}
		}
		
		
		
	}
	
	public void drawBoxes(Rectangle mouse, Input input){
		for(int i=0;i<guns.size();i++){
			Rectangle temp = new Rectangle(placex_box(i), y + skewY, width(box), height(box));
			box.draw(placex_box(i), y + skewY);
			if(mouse.intersects(temp) && input.isMousePressed(0)){
				selection = i;
			}
		}
	}
	
	
	private float width(String text){ return font.getWidth(text); }
	private float width(Image img){ return img.getWidth(); }
	
	private float height(String text){ return font.getHeight(text); }
	private float height(Image img){ return img.getHeight(); }
	
	
	
//	private float placementX(int pos){
//		return x + skewX + (box_width - width("Bought"))/2 + (87 * pos);
//	}
	
	private float placex_box(int pos){
		return x + skewX + (width(selected) + 4) * pos;
	}
	
	
	
	
	
	
	
	
	
}
