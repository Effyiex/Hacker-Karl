package cf.effectcode.game.render;

import java.awt.Graphics;
import java.util.ArrayList;

import cf.effectcode.game.Game;

public abstract class Gui extends ArrayList<Component> {

	protected static Game client = Game.getInstance();
	
	private String title;
	
	public Gui(String title) {
		this.title = title;
		this.reload();
	}
	
	public void reload() {
		this.dispose();
		this.clear();
		this.init();
	}
	
	public abstract void init();
	
	public void dispose() {
		for(Component c : this) {
			c.dispose();
		}
	}
	
	public void draw(Graphics g) {
		for(Component c : this) {
			c.draw(g);
		}
	}
	
	public String getTitle() {
		return title;
	}
	
}
