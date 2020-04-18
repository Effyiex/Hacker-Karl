package cf.effectcode.game.comp;

import java.awt.Color;
import java.awt.Graphics;

import cf.effectcode.game.Game;
import cf.effectcode.game.client.Controllable;
import cf.effectcode.game.client.Controller;
import cf.effectcode.game.client.InputEvent;
import cf.effectcode.game.client.InputEvent.EventType;
import cf.effectcode.game.render.Component;

public abstract class Button extends Component implements Controllable {

	private String text;
	
	public Button(String text, int x, int y, int width, int height) {
		super(x, y, width, height);
		this.setText(text);
		Controller.in.add(this);
	}

	public void drawBackground(Graphics g) {
		g.setColor(isHovered() ? new Color(60, 60, 70) : new Color(50, 50, 60));
		g.fillRect(getX(), getY(), getWidth(), getHeight());
		g.setColor(new Color(0, 0, 0));
		g.drawRect(getX(), getY(), getWidth(), getHeight());
	}
	
	public void drawText(Graphics g) {
		g.setColor(new Color(0, 255, 140));
		g.drawString(text, getX() + getWidth() / 2 - text.length() * Game.font().getSize() / 4, getY() + getHeight() / 2 + Game.font().getSize() / 2);
	}
	
	public void draw(Graphics g) {
		drawBackground(g);
		drawText(g);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public void dispose() {
		super.dispose();
		Controller.in.remove(this);
	}
	
	public void handle(InputEvent e) {
		if(e.TYPE == EventType.MOUSE_UP && isHovered()) {
			onClick();
		}
	}
	
	public abstract void onClick();
	
}
