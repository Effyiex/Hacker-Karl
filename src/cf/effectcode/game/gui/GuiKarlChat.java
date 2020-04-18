package cf.effectcode.game.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import cf.effectcode.game.Game;
import cf.effectcode.game.comp.Button;
import cf.effectcode.game.render.Gui;
import cf.effectcode.game.render.Sprites;

public class GuiKarlChat extends Gui {

	public static ArrayList<String> chat = new ArrayList<String>();
	
	private static int lastOpenedChatSize = 0;
	
	public GuiKarlChat() {
		super("Karl-Chat");
	}
	
	public void init() {
		lastOpenedChatSize = chat.size();
		add(new Button("Back", 10, 10, client.getWidth() / 6, client.getHeight() / 10) {
			public void onClick() {
				Game.getInstance().displayGui(new GuiIngame());
			}
		});
	}

	private int ani = 0;
	
	public void draw(Graphics g) {
		super.draw(g);
		int j = client.getHeight() - 20 - ani * (client.getHeight() / 20);
		if(ani < 20) {
			ani++;
		}
		g.drawImage(Sprites.smartphone.get(), 0, j, client.getWidth(), client.getHeight(), null);
		int y = j + client.getHeight() / 9 + client.getHeight() / 15;
		int x = client.getWidth() / 9 + client.getWidth() / 4;
		int count = 0;
		for(String raw : chat) {
			String[] splitted = ("Karl: " + raw).split("\n");
			String biggest = "";
			for(String line : splitted) {
				if(line.length() > biggest.length()) {
					biggest = line;
				}
			}
			if(biggest.length() > 15) {
				g.setFont(new Font(Game.fontBase.getFontName(), Game.fontBase.getStyle(), Game.font().getSize() / 4 * 3));
			}else{
				g.setFont(Game.font());
			}
			g.setColor(new Color(40, 40, 50));
			g.fillRoundRect(x + 1, y + g.getFont().getSize() * count + 9, g.getFont().getSize() / 2 * biggest.length() + 2, g.getFont().getSize() * splitted.length, 8, 8);
			g.setColor(new Color(255, 255, 255));
			for(String line : splitted) {
				int k = y + g.getFont().getSize() * (count + 1) + 6;
				g.drawString(line, x + 3, k);
				count++;
			}
			count++;
		}
	}
	

	public static boolean notReadMessages() {
		return lastOpenedChatSize < chat.size();
	}
	
}
