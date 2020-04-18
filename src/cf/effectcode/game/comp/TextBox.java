package cf.effectcode.game.comp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import cf.effectcode.game.Game;
import cf.effectcode.game.client.Controllable;
import cf.effectcode.game.client.Controller;
import cf.effectcode.game.client.InputEvent;
import cf.effectcode.game.client.InputEvent.EventType;
import cf.effectcode.game.render.Component;

public abstract class TextBox extends Component implements Controllable {

	private String text, standard;
	
	public TextBox(String text, int x, int y, int width, int height) {
		super(x, y, width, height);
		this.setText(text);
		standard = text;
		Controller.in.add(this);
	}
	
	private boolean focused = false;
	
	private boolean shift = false;
	
	public void handle(InputEvent e) {
		if(e.TYPE == EventType.MOUSE_UP) {
			focused = isHovered();
			if(focused) {
				if(text.equalsIgnoreCase(standard)) {
					text = "";
				}
			}else if(text.isEmpty()) {
				text = standard;
			}
			if(!focused) {
				onGiveInput();
			}
		}else if(focused && e.TYPE == EventType.KEYBOARD_UP) {
			if(e.CODE == KeyEvent.VK_ENTER) {
				onGiveInput();
				focused = false;
			}else if(e.CODE == KeyEvent.VK_BACK_SPACE) {
				text = text.substring(0, text.length() > 0 ? text.length() - 1 : 0);
			}else if(e.CODE != KeyEvent.VK_SHIFT){
				text += !shift ? Character.toLowerCase((char)e.CODE) : (char)e.CODE;
			}
		}
		if(e.CODE == KeyEvent.VK_SHIFT) {
			shift = e.TYPE == EventType.KEYBOARD_DOWN;
		}
	}

	private int blink = 0, blinkMax = 10;
	
	public void draw(Graphics g) {
		if(blink < blinkMax) blink++; else blink = 0;
		g.setColor(focused ? new Color(60, 60, 70) : new Color(50, 50, 60));
		g.fillRect(getX(), getY(), getWidth(), getHeight());
		g.setColor(new Color(0, 0, 0));
		g.drawRect(getX(), getY(), getWidth(), getHeight());
		g.setColor(new Color(0, 255, 140));
		String text = this.text.isEmpty() && !focused ? standard : focused || this.text == standard ? this.text : standard.replace("<", "").replace(">", "") + ": " + this.text;
		g.drawString(text + (focused ? blink > blinkMax / 2 ? "/" : "\\" : ""), getX() + getWidth() / 2 - text.length() * Game.font().getSize() / 3, getY() + getHeight() / 2 + Game.font().getSize() / 2);
	}
	
	public void dispose() {
		super.dispose();
		Controller.in.remove(this);
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}

	public abstract void onGiveInput();
	
}
