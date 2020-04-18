package cf.effectcode.game.render;

import java.awt.Graphics;

import cf.effectcode.game.Game;

public abstract class Component {

	public static int mouseX = 0, mouseY = 0;
	
	protected int x, y;
	
	protected int width, height;
	
	public Component(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public static int getHorizontalMod() {
		return Game.getInstance().getWidth() - Game.WIDTH;
	}
	
	public static int getVerticalMod() {
		return Game.getInstance().getHeight() - Game.HEIGHT;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public abstract void draw(Graphics g);
	
	public void dispose() {}
	
	public boolean isHovered() {
		return mouseX > getX() && mouseY > getY() 
				   && mouseX < getX() + getWidth() && mouseY < getY() + getHeight();
	}
	
}
