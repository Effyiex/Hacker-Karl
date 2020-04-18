package cf.effectcode.game.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import cf.effectcode.game.Game;
import cf.effectcode.game.comp.Button;
import cf.effectcode.game.render.Gui;

public class GuiMainMenu extends Gui {

	public GuiMainMenu() {
		super("MainMenu");
		this.reload();
	}

	private int ani = 0;
	
	private ArrayList<int[]> stacks = new ArrayList<int[]>();
	
	public void draw(Graphics g) {
		g.setColor(new Color(0, 0, 0));
		g.fillRect(0, 0, client.getWidth(), client.getHeight());
		g.setColor(new Color(0, 255, 150, 80));
		Random r = new Random();
		for(int[] a : stacks) {
			int x = client.getWidth() / 100 * a[0];
			for(int i = 0; i < a[2]; i++) {
				g.drawString(("" + r.nextInt(9)), x, a[1] + i * Game.font().getSize());
			}
			a[1] += client.getHeight() / 100 * a[3];
			if(a[1] > client.getHeight()) {
				a[1] = 0 - a[1] / 3;
				a[0] = r.nextInt(100);
			}
		}
		super.draw(g);
		int j = - client.getHeight() / 5 + (client.getHeight() / 100 * ani);
		int alpha = ani * 2;
		if(j < client.getHeight() / 6)ani++;
		g.setColor(new Color(0, 250, 140, alpha));
		g.fillRect(client.getWidth() / 4, j, client.getWidth() / 2, client.getHeight() / 5);
		g.setColor(new Color(0, 255, 140, 205 + ani < 255 ? 205 + ani : 255));
		Font font = new Font(Game.font().getFontName(), Game.font().getStyle(), Game.font().getSize() * 3);
		g.setFont(font);
		g.drawString(Game.gameName, client.getWidth() / 2 - 3 * font.getSize() + client.getWidth() / 27, j + client.getHeight() / 10 + font.getSize() / 12 * 5);
		g.setFont(Game.font());
		g.setColor(new Color(0, 255, 150, alpha));
		g.fillRect(0, client.getHeight() - Game.font().getSize() * 2 - 33 + 44 - ani, Game.font().getSize() * 12, 100);
		g.setColor(new Color(0, 255, 140));
		g.drawString("http://www.effectcode.cf", 2, client.getHeight() - Game.font().getSize() / 2 - 33);
	}
	
	public void init() {
		stacks = new ArrayList<int[]>();
		stacks.clear();
		Random r = new Random();
		for(int i = 0; i < 20; i++) {
			stacks.add(new int[] {
					r.nextInt(100), r.nextInt(300), 3 + r.nextInt(6), 1 + r.nextInt(2)
			});
		}
		int j = client.getWidth() / 5;
		int k = client.getHeight() / 10;
		add(new Button("Play", client.getWidth() / 2 - j / 2, client.getHeight() / 2, j, k) {

			public void onClick() {
				Game.getInstance().displayGui(new GuiIngame());
			}
			
		});
		add(new Button("Options", client.getWidth() / 2 - j / 2, client.getHeight() / 2 + k + 10, j, k) {

			public void onClick() {
				System.out.println("Wanna go to the options!");
			}
			
		});
		add(new Button("Exit", client.getWidth() / 2 - j / 2, client.getHeight() / 2 + k * 2 + 20, j, k) {

			public void onClick() {
				System.exit(0);
			}
			
		});
	}

}
