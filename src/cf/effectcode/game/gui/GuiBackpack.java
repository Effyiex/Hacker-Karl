package cf.effectcode.game.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import cf.effectcode.game.Game;
import cf.effectcode.game.client.Controllable;
import cf.effectcode.game.client.Controller;
import cf.effectcode.game.client.InputEvent;
import cf.effectcode.game.client.InputEvent.EventType;
import cf.effectcode.game.comp.Button;
import cf.effectcode.game.comp.Item;
import cf.effectcode.game.render.Gui;

public class GuiBackpack extends Gui implements Controllable {

	public static ArrayList<Item> items = new ArrayList<Item>();
	
	public static Item usingItem = null;
	
	public static boolean checkForItem(String name) {
		try {
			return usingItem.getName().equalsIgnoreCase(name);
		} catch (NullPointerException e) {
			return false;
		}
	}
	
	public static void removeItem(String name) {
		ArrayList<Item> dispose = new ArrayList<Item>();
		for(Item i : items) {
			if(i.getName().equalsIgnoreCase(name)) {
				dispose.add(i);
			}
		}
		for(Item i : dispose) {
			items.remove(i);
		}
	}
	
	public GuiBackpack() {
		super("Backpack");
		Controller.in.add(this);
	}

	public void init() {
		add(new Button("Back", 10, 10, client.getWidth() / 6, client.getHeight() / 10) {
			public void onClick() {
				Game.getInstance().displayGui(new GuiIngame());
			}
		});
	}
	
	public void draw(Graphics g) {
		super.draw(g);
		int k = client.getHeight() / 5;
		int j = (client.getHeight() - k) / 2;
		g.setColor(new Color(0, 0,0, 120));
		g.fillRect(0, j, client.getWidth(), k);
		g.setColor(new Color(255, 255, 255));
		int count = 0;
		for(Item i : items) {
			g.drawImage(i.getSprite().get(), 3 + count * k, j, k, k, null);
			g.drawString(i.getName(), 3 + count * k, j - Game.font().getSize() / 2);
			if(usingItem == i) {
				g.drawRect(3 + count * k, j, k, k);
			}
			count++;
		}
	}

	public void handle(InputEvent e) {
		if(e.TYPE == EventType.MOUSE_DOWN) {
			Point p = Game.getInstance().getMousePosition();
			int k = client.getHeight() / 5;
			int j = (client.getHeight() - k) / 2;
			int count = 0;
			for(Item i : items) {
				int x = 3 + count * k;
				if(p.x > x && p.x < x + k && p.y > j && p.y < j + k) {
					usingItem = i;
					break;
				}
				count++;
			}
		}
	}

	public void dispose() {
		super.dispose();
		Controller.in.remove(this);
	}
	
}
