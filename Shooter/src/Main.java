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
        addState(new Editor(3));
        enterState(2);

    }

	public void initStatesList(GameContainer gc) throws SlickException {
		
	}
	
	public boolean closeRequested() {
		Editor.Write();
		System.exit(0);
		return false;
	}
	
	
	
	public static void main(String[] args){
		
		System.out.println("Hello World");
		
		Thread main_thread = new Thread(new Window());
		main_thread.start();

		
	}
	
	

}

class Window implements Runnable {

	AppGameContainer app;
	public void run() {
		try {
			app = new AppGameContainer(new Main("- Shooter -"));
			app.setDisplayMode(Main.WIDTH, Main.HEIGHT, false);
			app.setTargetFrameRate(60);
			app.setIcon("lib/res/Misc/logo.png");
			app.setShowFPS(false);
			app.start();
		} catch (SlickException e) {
		}
	}
	
}
