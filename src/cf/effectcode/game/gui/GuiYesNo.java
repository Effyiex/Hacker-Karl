package cf.effectcode.game.gui;

import java.awt.Color;
import java.awt.Graphics;

import cf.effectcode.game.comp.Button;
import cf.effectcode.game.render.Gui;

public abstract class GuiYesNo extends Gui {

	public GuiYesNo() {
		super("Do you really wanna do this?");
	}

	public void init() {
		int k = client.getWidth() / 10;
		int j = client.getHeight() / 2;
		int w = client.getWidth() / 5;
		int h = client.getHeight() / 10;
		add(new Button("Yes", k, j, w, h) {
			public void onClick() {
				onYes();
			}
		});
		add(new Button("No", client.getWidth() - w * 8 / 5, j, w, h) {
			public void onClick() {
				onNo();
			}
		});
	}
	
	public void draw(Graphics g) {
		super.draw(g);
		g.setColor(new Color(255, 255, 255));
		g.drawString("Are you sure?", client.getWidth() / 2 - 90, client.getHeight() / 4);
	}
	
	public abstract void onYes();
	public abstract void onNo();

}
