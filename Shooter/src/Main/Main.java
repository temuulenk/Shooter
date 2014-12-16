package Main;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class Main extends StateBasedGame {
	
	public static int WIDTH = 704;
	public static int HEIGHT = 544;
	
	
	public Main(String gamename){
        super(gamename);
//        this.addState(new Play(playState));
        addState(new Menu(1));
        addState(new Play(2));
        addState(new WorldBuilder(3));
        enterState(1);

    }

	public void initStatesList(GameContainer gc) throws SlickException {
		
	}
	
	public boolean closeRequested() {
		if(Menu.enteredWorldBuilder)
			WorldBuilder.Write();
		if(Play.inMultiplayer == true)
			Client.send("QUIT");
		System.exit(0);
		return false;
	}
	
	
	
	public static void main(String[] args){
		
		
		Thread main_thread = new Thread(new Window());
		main_thread.start();
		
		
	}
	
	

}

class Window implements Runnable {

	AppGameContainer app;
	public void run() {
		try {
			app = new AppGameContainer(new Main("8Bit Shooter"));
			app.setDisplayMode(Main.WIDTH, Main.HEIGHT, false);
			app.setTargetFrameRate(60);
			app.setIcon("lib/res/Misc/logo.png");
			app.setShowFPS(false);
			app.start();
		} catch (SlickException e) {
		}
	}
	
}
