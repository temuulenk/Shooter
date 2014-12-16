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

import Weapons.*;

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
	Image buy;
	Image close;
	Image shoptab;
	Image shoptab1;
	Image platform;
	
	
	
	public static Image M16_icon;
	public static Image Pistol_icon;
	
	public static Image M16_white;
	public static Image Pistol_white;
	public static Image Python_white;
	public static Image Shotgun_white;
	
	
	public static Image Python;
	public static Image Python_icon;
	
	public static Image Uzi;
	public static Image Uzi_icon;
	
	public static Image Shotgun;
	public static Image Shotgun_icon;
	
	
	public static Image special_uzi;
	
	
	
	float x;
	float y;
	
	int skewX = 32;
	int skewY = 31;
	int textSkew = 69;
	int box_width = 72;
	int selection = -1;
	int invSelection = -1;
	
	
	public static int money = 50;
	
	public static boolean inShoptab = true;
	public static boolean inBagtab;
	
	
	public static boolean open = false;
	
	public static ArrayList<Weapons> guns = new ArrayList<Weapons>(0);
	
	public Shop(TrueTypeFont BitFont) throws SlickException {
		font = BitFont;
		
		buy = new Image("lib/res/Misc/Shop_buy.png");
		close = new Image("lib/res/Misc/Shop_close.png");
		shop = new Image("lib/res/Misc/Shop.png");
		box = new Image("lib/res/Misc/shop_box.png");
		selected = new Image("lib/res/Misc/shop_selected.png");
		highlight = new Image("lib/res/Misc/shop_highlight.png");
		closeHover = new Image("lib/res/Misc/Shop_closeHover.png");
		xHover = new Image("lib/res/Misc/Shop_xHover.png");
		buyHover = new Image("lib/res/Misc/Shop_buyHover.png");
		toolbar = new Image("lib/res/Misc/toolbar.png");
		shoptab = new Image("lib/res/Misc/Shop_shoptab.png");
		shoptab1 = new Image("lib/res/Misc/Shop_shoptab1.png");
		platform = new Image("lib/res/Misc/Shop_platform.png");
		
		
		M16_icon = new Image("lib/res/Shop/M16_icon.png");
		Pistol_icon = new Image("lib/res/Shop/Pistol_icon.png");
		Shotgun_icon = new Image("lib/res/Shop/Shotgun_icon.png");
		
		
    	M16_white = new Image("lib/res/Misc/m16_white.png");
    	Pistol_white = new Image("lib/res/Misc/pistol_white.png");
    	Python_white = new Image("lib/res/Misc/python_white.png");
    	Shotgun_white = new Image("lib/res/Misc/shotgun_white.png");
    	
    	Python = new Image("lib/res/Misc/python.png");
    	Python_icon = new Image("lib/res/Shop/python_icon.png");
    	
    	Uzi = new Image("lib/res/Misc/uzi.png");
    	Uzi_icon = new Image("lib/res/Shop/uzi_icon.png");
    	
    	Shotgun = new Image("lib/res/Misc/shotgun.png");
    	
    	special_uzi = new Image("lib/res/Misc/special_uzi.png");
    	
    	
		
		x = (704 - shop.getWidth())/2;
		y = (544 - shop.getHeight())/2;
		
		
		guns.add(new Pistol(Menu.Pistol, Pistol_icon, Pistol_white));
		guns.add(new Python(Python, Python_icon, M16_white));
		guns.add(new Uzi(Uzi, Uzi_icon, M16_white));
		guns.add(new Shotgun(Shotgun, Shotgun_icon, M16_white));
		guns.add(new M16(Menu.M16, M16_icon, M16_white));
		
		
	}
	
	
	public void draw(Graphics g, GameContainer gc) throws SlickException{
		
		Input input = gc.getInput();
		int mx = input.getMouseX();
		int my = input.getMouseY();
		Rectangle mouse = new Rectangle(mx, my, 1, 1);
		
		money = 1000;
		
		if(input.isKeyPressed(Input.KEY_P)){
			if(open && inBagtab){
				inBagtab = false;
				inShoptab = true;
			}else{
				open = !open;
				inBagtab = false;
				inShoptab = true;
			}
		}
		
		if(input.isKeyPressed(Input.KEY_B)){
			if(open && inShoptab){
				inShoptab = false;
				inBagtab = true;
			}else{
				open = !open;
				inShoptab = false;
				inBagtab = true;
			}
		}
		
		if(open) Gun.allowedToShoot = false;
		else Gun.allowedToShoot = true;
		
		
		
		
		if(open){
			g.setColor(new Color(0, 0, 0, .4f));
			g.fillRect(0, 0, gc.getWidth(), gc.getHeight());
			shop.draw(x, y);
			
			if(inShoptab){
				drawBoxes(mouse, input);
				if(selection >= 0)
					highlight.draw(placex_box(selection), y + skewY);
				
				
				for(int i=0;i<guns.size();i++){
					guns.get(i).icon().draw(placex_box(i) + (72 - width(guns.get(i).icon()))/2, y + skewY + (40 - height(guns.get(i).icon()))/2);
					font.drawString(placex_box(i) + (int) (72 - width(guns.get(i).name()))/2, y + skewY - height(guns.get(i).name()), guns.get(i).name(), Color.decode("#C1C3CE"));
					
					
					if(Gun.wielding.equals(guns.get(i))){
						font.drawString(placex_box(i) + (int) (72 - width("Using"))/2, y + skewY + 42, "Using", Color.white);
					}
					else if(money < guns.get(i).price()){
						font.drawString(placex_box(i) + (int) (72 - width("$" + guns.get(i).price()))/2, y + skewY + 42, "$" + guns.get(i).price(), Color.decode("#C30000"));
					}else{
						font.drawString(placex_box(i) + (int) (72 - width("$" + guns.get(i).price()))/2, y + skewY + 42, "$" + guns.get(i).price(), Color.decode("#00942D"));
					}
				}
				
				// grayed out strings C1C3CE
				// nice red color C30000
				
				font.drawString((704 - width("Shop"))/2, y - height("S"), "Shop");
				
				Rectangle buyRect = new Rectangle(x + 380, y + 190, width(buyHover), height(buyHover));

				if(mouse.intersects(buyRect)){
					buyHover.draw(x + 380, y + 189);
					if(input.isMousePressed(0) && money >= guns.get(selection).price()){
						boolean has = false;
						for(Weapons w: Gun.inventory){
							if(w.equals(guns.get(selection))){
								has = true;
								break;
							}
						}
						if(has == false){
							Gun.inventory.add(guns.get(selection));
							money -= guns.get(selection).price();
						}
					}
		
					
					
				}else{
					buy.draw(x + 380, y + 190);
				}
				
//				float invSizeY = y + (shop.getHeight() - height(platform)) - skewX;
				float invSizeY = y + 190;
				float invSizeX = x + skewX;
				platform.draw(invSizeX, invSizeY);
				
				font.drawString(
						(int) (invSizeX + (width(platform) - width("$" + money))/2), 
						(int) (invSizeY + (height(platform) - height("$" + money))/2),
						"$" + money, Color.decode("#00942D"));
				
				
			}
			
			
			else if(inBagtab){
				font.drawString((int)(704 - width("Bag"))/2,(int) (y - height("B")), "Bag");
				drawInv(mouse, input);
				
				float invSizeY = y + 190;
				float invSizeX = x + skewX;
				platform.draw(invSizeX, y + 190);
				
				font.drawString(
						(int) (invSizeX + (width(platform) - width("" + Gun.inventory.size()))/2), 
						(int) (invSizeY + (height(platform) - height("" + Gun.inventory.size()))/2),
						"" + Gun.inventory.size());
				
				
				
			}
			
			Rectangle closeRect = new Rectangle(x + 380, y + 240, width(closeHover), height(closeHover));
			if(mouse.intersects(closeRect)){
				closeHover.draw(x + 380, y + 240);
				if(input.isMousePressed(0)){
					open = false;
				}
			}else{
				close.draw(x + 380, y + 241);
			}
			
			
			Rectangle xRect = new Rectangle(x + width(shop) - width(xHover), y, width(xHover), height(xHover));
			
			if(mouse.intersects(xRect)){
				xHover.draw(x + width(shop) - width(xHover), y);
				if(input.isMousePressed(0)){
					open = false;
				}
			}
			
			
			
			Rectangle shoptabRect = new Rectangle(x + 42, y + shop.getHeight() - 4, width(shoptab), height(shoptab));
			Rectangle bagtabRect = new Rectangle(width(shoptab) + x + 42, y + shop.getHeight() - 4, width(shoptab), height(shoptab));
			
			if(mouse.intersects(shoptabRect) && input.isMousePressed(0)){
				inBagtab = false;
				inShoptab = true;
			}
			else if(mouse.intersects(bagtabRect) && input.isMousePressed(0)){
				inShoptab = false;
				inBagtab = true;
			}
			
			if(inShoptab){
				shoptab.draw(x + 42, y + shop.getHeight() - 4);
				font.drawString((int) (x + 42 + (shoptab.getWidth() - width("Shop"))/2), (int) (y + shop.getHeight() - 4 + (shoptab.getHeight() - height("Shop"))/2), "Shop");
			}else{
				shoptab1.draw(x + 42, y + shop.getHeight() - 4);
				font.drawString((int) (x + 42 + (shoptab.getWidth() - width("Shop"))/2), (int) (y + shop.getHeight() - 4 + (shoptab.getHeight() - height("Shop"))/2), "Shop", Color.decode("#C1C3CE"));
			}
			
			if(inBagtab){
				shoptab.draw(x + 42 + shoptab.getWidth() - 2, y + shop.getHeight() - 4);
				font.drawString((int) (shoptab.getWidth() + x + 42 + (shoptab.getWidth() - width("bag"))/2), (int) (y + shop.getHeight() - 4 + (shoptab.getHeight() - height("bag"))/2), "bag");
			}else{
				shoptab1.draw(x + 42 + shoptab.getWidth() - 2, y + shop.getHeight() - 4);
				font.drawString((int) (shoptab.getWidth() + x + 42 + (shoptab.getWidth() - width("bag"))/2), (int) (y + shop.getHeight() - 4 + (shoptab.getHeight() - height("bag"))/2), "bag", Color.decode("#C1C3CE"));
			}
			
			
			
		}
		
		
		
	}
	
	public void drawBoxes(Rectangle mouse, Input input){
		for(int i=0;i<guns.size();i++){
			Rectangle temp = new Rectangle(placex_box(i), y + skewY, width(box), height(box));
			if(Gun.wielding != guns.get(i)){
				box.draw(placex_box(i), y + skewY);
			}else{
				selected.draw(placex_box(i), y + skewY);
			}
			if(mouse.intersects(temp) && input.isMousePressed(0)){
				selection = i;
			}
		}
	}
	
	
	private float width(String text){ return font.getWidth(text); }
	private float width(Image img){ return img.getWidth(); }
	
	private float height(String text){ return font.getHeight(text); }
	private float height(Image img){ return img.getHeight(); }
	

	private float placex_box(int pos){
		return x + skewX + (width(selected) + 4) * pos;
	}
	
	
	public static Weapons gun(String name){
		Weapons temp = null;
		for(Weapons gun: guns){
			if(gun.name().equalsIgnoreCase(name)){
				temp = gun;
				break;
			}
		}
		return temp;
	}
	
	public static Weapons gun(int pos){
		return guns.get(pos);
	}
	
	public void drawInv(Rectangle mouse, Input input){
		
		for(int i=0;i<Gun.inventory.size();i++){
			Rectangle temp = new Rectangle(placex_box(i), y + skewY, width(box), height(box));
			if(Gun.wielding != Gun.inventory.get(i)){
				box.draw(placex_box(i), y + skewY);
			}else{
				selected.draw(placex_box(i), y + skewY);
			}
			if(invSelection >= 0)
				highlight.draw(placex_box(invSelection), y + skewY);
			
			if(mouse.intersects(temp) && input.isMousePressed(0)){
				if(invSelection != i){
					invSelection = i;
				}else
					Gun.wielding = Gun.inventory.get(i);
			}
			
			Gun.inventory.get(i).icon().draw(placex_box(i) + (72 - width(guns.get(i).icon()))/2, y + skewY + (40 - height(guns.get(i).icon()))/2);
			font.drawString(placex_box(i) + (int) (72 - width(Gun.inventory.get(i).name()))/2, y + skewY - height(Gun.inventory.get(i).name()), Gun.inventory.get(i).name(), Color.decode("#C1C3CE"));
			
			if(Gun.wielding.equals(Gun.inventory.get(i))){
				font.drawString(placex_box(i) + (int) (72 - width("Using"))/2, y + skewY + 42, "Using", Color.white);
			}else{
				font.drawString(placex_box(i) + (int) (72 - width("Equip"))/2, y + skewY + 42, "Equip", Color.decode("#C1C3CE"));
			}
			
			
		}
		
		
		
		
	}
	
	
	
	
}
