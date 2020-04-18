package cf.effectcode.game.gui;

import java.awt.Graphics;

import cf.effectcode.game.Game;
import cf.effectcode.game.comp.Button;
import cf.effectcode.game.comp.Item;
import cf.effectcode.game.comp.TextBox;
import cf.effectcode.game.render.Gui;
import cf.effectcode.game.render.Sprites;

public class GuiPersonal extends Gui {

	private static boolean usedBefore = false;

	public GuiPersonal() {
		super("Personal");
	}

	private static String name = "", gender = "";
	
	private static Button g1, g2;
	
	public void init() {
		int k = client.getWidth() / 4 * 3;
		int j = (client.getWidth() - k) / 2;
		int b = client.getHeight() / 4;
		int n = client.getHeight() / 10;
		int s = client.getHeight() / 40;
		add(new TextBox("<Synonym>", j, b, k, n) {
			public void onGiveInput() {
				setName(getText());
			}
		});
		final String male = "Male", female = "Female";
		g1 = new Button("Male", j, b + n + s, k / 2 - s / 2, n) {
			public void onClick() {
				setGender(male);
				setText("Gender: " + male);
				g2.setText(female);
			}
		};
		g2 = new Button("Female", j + k / 2 + s / 2, b + n + s, k / 2 - s / 2, n) {
			public void onClick() {
				setGender(female);
				setText("Gender: " + female);
				g1.setText(male);
			}
		};
		add(g1);
		add(g2);
		add(new Button("Go on", j, b + n * 3, k, n) {
			public void onClick() {
				if(name != null && gender != null && name.length() > 3 && !gender.isEmpty()) {
					GuiBackpack.items.add(new Item("USB-Stick", Sprites.usbStick));
					Game.getInstance().displayGui(new GuiIngame());
					GuiKarlChat.chat.add("Hi " + name + "!\nDu musst jetzt\nden USB-Stick\nnutzen, denn dort\nist ein brute-\nforce Programm\ndrauf.");
					usedBefore = true;
				}else{
					setText("Go on : Failed");
				}
			}
		});
	}
	
	public void draw(Graphics g) {
		super.draw(g);
		if(usedBefore && GuiIngame.getLevel() > 3) {
			Game.getInstance().displayGui(new GuiIngame());
		}else{
			usedBefore = false;
		}
	}
	
	public static String getName() {
		return name;
	}

	public static void setName(String name) {
		GuiPersonal.name = name;
	}

	public static String getGender() {
		return gender;
	}

	public static void setGender(String gender) {
		GuiPersonal.gender = gender;
	}
	
	public static boolean isUsedBefore() {
		return usedBefore;
	}

	public static void setUsedBefore(boolean usedBefore) {
		GuiPersonal.usedBefore = usedBefore;
	}
	
}
