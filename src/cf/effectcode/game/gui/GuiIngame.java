package cf.effectcode.game.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.Random;

import cf.effectcode.game.Game;
import cf.effectcode.game.client.Controllable;
import cf.effectcode.game.client.Controller;
import cf.effectcode.game.client.FileHandler;
import cf.effectcode.game.client.InputEvent;
import cf.effectcode.game.client.InputEvent.EventType;
import cf.effectcode.game.comp.Button;
import cf.effectcode.game.comp.Item;
import cf.effectcode.game.render.Gui;
import cf.effectcode.game.render.Sprites;

public class GuiIngame extends Gui implements Controllable {
	
	public GuiIngame() {
		super("Ingame");
	}
	
	public void init() {
		Controller.in.add(this);
		add(new Button("Exit", 10, 10, client.getWidth() / 6, client.getHeight() / 10) {
			public void onClick() {
				Game.getInstance().displayGui(new GuiMainMenu());
			}
		});
		add(new Button("Reset", 10, 10 + client.getHeight() / 9, client.getWidth() / 6, client.getHeight() / 10) {
			public void onClick() {
				Game.getInstance().displayGui(new GuiIngame());
				Game.getInstance().displayGui(new GuiYesNo() {
					public void onYes() {
						GuiBackpack.items.clear();
						GuiKarlChat.chat.clear();
						GuiPersonal.setUsedBefore(false);
						level = 0;
						onNo();
					}
					public void onNo() {
						Game.getInstance().displayGui(new GuiIngame());
					}
				});
			}
		});
		add(new Button("Last", 10, 10 + client.getHeight() / 9 * 2, client.getWidth() / 6, client.getHeight() / 10) {
			public void onClick() {
				if(level > 0) {
					level--;
				}
			}
		});
		add(new Button("Backpack", 10, 10 + client.getHeight() / 9 * 3, client.getWidth() / 6, client.getHeight() / 10) {
			public void onClick() {
				Game.getInstance().displayGui(new GuiBackpack());
			}
		});
		add(new Button("Phone", client.getWidth() / 5 * 4, client.getHeight() / 32 * 27, client.getWidth() / 6, client.getHeight() / 10) {
			public void onClick() {
				Game.getInstance().displayGui(new GuiKarlChat());
			}
			
			public void draw(Graphics g) {
				super.draw(g);
				if(GuiKarlChat.notReadMessages()) {
					setText("*Phone");
				}else{
					setText("Phone");
				}
			}
			
		});
	}
	
	private static int level = 0;
	
	private int booting = 0;
	
	private String welcomeMsg = 
			"Hallo. Ich bin Karl. Ich bin ein Böser,\n"
			+ "Böser Hacker.\n"
			+ "Dies ist der PC eines Freundes von mir.\n"
			+ "Aber weil ich böse bin,\n"
			+ "klau ich ihm seine Daten. Muhahaha....\n\n"
			+ "Und du! Ja du! Du bist mein Lehrling :3\n\n"
			+ "(Unbezahlt, um das klar zu stellen) ...";
	
	private int welcomeMsgAni = 0;
	
	private Button skip0, stick;
	
	private String ageQuest = "Wie alt bist du?";
	
	private int ageAni = 0, ageLoad = 0;
	
	private String ageInput = "";
	
	private boolean gaveAge = false;
	
	private int blink = 0, blinkMax = 10;
	
	private String webQuest = "Wie lautet meine Website?";
	
	private String webInput = "";
	
	private int webAni = 0, webLoad = 0;
	
	private boolean gaveWeb = false;
	
	private String wellDoneMsg = "Gut gemacht. Du bist jetzt eingestellt,\nbei der Karl GMBH!\n\nDein Betriebssystem wird\njetzt installiert";
	
	private int wellDoneAni = 0, osInstall = 0;
	
	private int bootUp = 0;
	
	private boolean sticked = false, mouseDown = false;
	
	private int bruteAni = 0;
	
	private int accAni = 0;
	
	private int files = 5;
	
	public void draw(Graphics g) {
		super.draw(g);
		Point mouse = client.getMousePosition();
		if(mouse == null) {
			mouse = new Point(0, 0);
		}else {
			mouse.y -= 30;
		}
		g.setColor(new Color(0, 255, 140));
		g.drawImage(Sprites.monitor.get(), 0, 0, client.getWidth(), client.getHeight() - (client.isUndecorated() ? 0 : 30), null);
		int x = client.getWidth() / 5;
		int y = client.getHeight() / 10;
		int width = client.getWidth() / 8 * 5;
		int height = client.getHeight() / 16 * 9;
		g.setColor(new Color(0, 0, 0));
		g.fillRect(x, y, width, height);
		if(blink < blinkMax) {
			blink++;
		}else{
			blink = 0;
		}
		switch(level) {
		
		case 0:
			int tone = 255 - booting;
			if(booting < 254)booting += 2;
			g.setColor(new Color(tone, tone, tone, 255));
			g.fillRect(x, y, width, height);
			if(welcomeMsgAni < welcomeMsg.length() && booting > 250) {
				welcomeMsgAni++;
			}
			g.setColor(new Color(255, 255, 255));
			String msg = welcomeMsg.substring(0, welcomeMsgAni);
			String[] lines = msg.split("\n");
			int count = 1;
			for(String line : lines) {
				g.drawString(line, x + 3, y + 3 + Game.font().getSize() * count);
				count++;
			}
			if(welcomeMsgAni == welcomeMsg.length()) {
				skip0 = new Button(">", x + width - 40, y + height - 40, 32, 32) {
					public void onClick() {
						if(level == 0) {
							level++;
						}
					}
				};
				skip0.draw(g);
			}
			break;
			
		case 1:
			if(ageAni < ageQuest.length()) {
				ageAni++;
			}
			String msg1 = ageQuest.substring(0, ageAni);
			g.setColor(new Color(255, 255, 255));
			g.drawString(msg1, x + 3, y + 3 + Game.font().getSize());
			g.drawString(ageInput + (!gaveAge ? blink > blinkMax / 2 ? "/" : "\\" : ""), x + 3, y + 3 + Game.font().getSize() * 2);
			if(gaveAge) {
				boolean success = ageInput.isEmpty();
				g.drawString(success ? "Richtige Antwort. Bleib Anonym." : Integer.parseInt(ageInput) < 10 ? "Oh wie süß ein " + ageInput +"-jähriger" : "Falsche Antwort!", x + 3, y + 3 + Game.font().getSize() * 3);
				g.drawString("Loading" + (success ? " Next One:" : ":"), x + 3, y + Game.font().getSize() * 5);
				g.drawString(" " + ageLoad + "%", x + 3, y + Game.font().getSize() * 6);
				if(ageLoad < 100) {
					ageLoad += new Random().nextInt(3);
				}else{
					ageLoad = 0;
					if(success) {
						level++;
					}else{
						gaveAge = false;
					}
				}
			}
			break;
		
		case 2:
			if(webAni < webQuest.length()) {
				webAni++;
			}
			String msg2 = webQuest.substring(0, webAni);
			g.setColor(new Color(255, 255, 255));
			g.drawString(msg2, x + 3, y + 3 + Game.font().getSize());
			g.drawString(webInput + (!gaveWeb ? blink > blinkMax / 2 ? "/" : "\\" : ""), x + 3, y + 3 + Game.font().getSize() * 2);
			if(gaveWeb) {
				boolean success = webInput.contains("effectcode.cf");
				g.drawString(success ? "Richtige Antwort. Schönes MainMenu ne?" : "Falsche Antwort!", x + 3, y + 3 + Game.font().getSize() * 3);
				g.drawString("Loading" + (success ? " Next One:" : ":"), x + 3, y + Game.font().getSize() * 5);
				g.drawString(" " + webLoad + "%", x + 3, y + Game.font().getSize() * 6);
				if(webLoad < 100) {
					webLoad += new Random().nextInt(3);
				}else{
					webLoad = 0;
					if(success) {
						level++;
					}else{
						gaveWeb = false;
					}
				}
			}
			break;
			
		case 3:
			String msg3 = wellDoneMsg.substring(0, wellDoneAni);
			if(wellDoneAni < wellDoneMsg.length()) {
				wellDoneAni++;
			}else if(osInstall < 100) {
				if(new Random().nextInt(4) > 2) {
					osInstall += new Random().nextInt(2);
				}
				msg3 += ": " + osInstall + "%";
			}else{
				level++;
			}
			if(wellDoneAni > 57) {
				g.drawImage(Sprites.icon.get(), x + width / 2 - width / 40, y + Game.font().getSize() / 4 * 7, width / 16, width / 16, null);
			}
			g.setColor(new Color(255, 255, 255));
			int count1 = 1;
			for(String s : msg3.split("\n")) {
				g.drawString(s, x + 3, y + 3 + count1 * Game.font().getSize());
				count1++;
			}
			break;
			
		case 4:
			g.setColor(new Color(255, 255, 255));
			if(bootUp < width / 4 * 3) {
				bootUp += width / 40;
			}else{
				level++;
				Game.getInstance().displayGui(new GuiPersonal());
			}
			if(bootUp > width)bootUp = 0;
			g.drawString("Loading KarlOS", x + width / 2 - Game.font().getSize() * 4, y + Game.font().getSize() * 4);
			g.fillRect(x + width / 8, y + height / 2, bootUp, height / 10);
			break;
		
		case 5:
			g.setColor(new Color(255, 255, 255));
			g.drawString("KarlOS", x  + width - Game.font().getSize() * 4, y + 3 + Game.font().getSize());
			int j = x + width / 2 - width / 15;
			int k = y + height / 2 - width / 10;
			int s = width / 10;
			g.drawImage(Sprites.skyBg.get(), x, y, width, height, null);
			g.setColor(new Color(0, 0, 0, 120));
			g.fillRoundRect(j - width / 20, k - height / 10, width / 5, height / 2, 8, 8);
			g.setColor(new Color(255, 255, 255));
			g.drawImage(Sprites.osPB.get(), j, k, s, s, null);
			g.drawRect(j, k, s, s);
			g.drawRoundRect(j - width / 30, k + width / 8, width / 6, height / 30, 5, 5);
			g.drawRoundRect(j - width / 30, k + width / 6, width / 6, height / 30, 5, 5);
			if(GuiBackpack.checkForItem("USB-Stick")) {
				stick = new Button("Stick in", x + s / 2 + 2, y + height + 2, s, s) {
					public void onClick() {
						GuiBackpack.removeItem("USB-Stick");
						sticked = true;
					}
					public void draw(Graphics g) {
						g.setColor(new Color(0, 0, 0, 120));
						g.fillRect(x, y + height / 10, width / 8 * 15, height / 4 * 7);
						g.setColor(new Color(255, 255, 255));
						g.drawString(getText(), x + 3, y + (height + Game.font().getSize()) / 2);
						g.drawString("v", x + width / 4 * 3, y + height / 3 + Game.font().getSize() * 2);
					}
				};
				stick.draw(g);
			}
			if(sticked) {
				Random r = new Random();
				bruteAni++;
				if(bruteAni > 200) {
					level++;
				}
				String alph = "abcdefghijklmnopqrstuvwxyz";
				int userLength = r.nextInt(alph.length());
				int passLength = r.nextInt(alph.length());
				String user = "", pass = "";
				for(int i = 0; i < userLength; i++) {
					char c = alph.charAt(r.nextInt(alph.length()));
					if(r.nextBoolean()) {
						Character.toUpperCase(c);
					}
					user += c;
				}
				for(int i = 0; i < passLength; i++) {
					pass += "*";
				}
				g.setFont(new Font(Game.font().getFontName(), Game.font().getStyle(), Game.font().getSize() / 3));
				g.drawString(user, 2 + j - width / 30, k + width / 8 + g.getFont().getSize());
				g.drawString(pass, 2 + j - width / 30, k + width / 6 + g.getFont().getSize());
				g.setFont(Game.font());
			}
			
			break;
			
		case 6:
			g.setFont(new Font(Game.font().getFontName(), Game.font().getStyle(), Game.font().getSize() * 2));
			g.setColor(new Color(0, 255, 120));
			String msg4 = "Access granted!";
			g.drawString(msg4, x + width / 2 - msg4.length() * Game.font().getSize() / 2, y + height / 2 + Game.font().getSize() / 2);
			g.setFont(Game.font());
			g.drawRect(x + width / 2 - msg4.length() * Game.font().getSize() / 2 - width / 80, y + height / 2 - Game.font().getSize() - height / 50, msg4.length() * Game.font().getSize(), Game.font().getSize() * 2);
			g.setColor(new Color(0, 255, 120, 120));
			g.fillRect(x + width / 2 - msg4.length() * Game.font().getSize() / 2 - width / 80, y + height / 2 - Game.font().getSize() - height / 50, accAni, Game.font().getSize() * 2);
			if(accAni < msg4.length() * Game.font().getSize()) {
				accAni += 5;
			}else{
				level++;
				files = new Random().nextInt(10);
				GuiBackpack.items.add(new Item("Mouse", Sprites.mouse));
			}
			break;
			
		case 7:
			j = height / 15;
			g.setColor(new Color(0, 130, 250));
			g.fillRect(x, y, width, height);
			g.setColor(new Color(0, 0, 0));
			g.fillRect(x, y + height - j, width, j);
			g.drawImage(Sprites.winicon.get(), x + 1, y + height - j + 1, j, j, null);
			for(int i = 0; i < files; i++) {
				g.setFont(new Font(Game.font().getFontName(), Game.font().getStyle(), Game.font().getSize() / 2));
				k = y + 3 + (13 + j) * i;
				int h = g.getFont().getSize();
				if(mouse.x > x + 3 && mouse.x < x + 3 + j * 2 && mouse.y > k && mouse.y < k + j) {
					g.setColor(new Color(255, 255, 255, 150));
					g.fillRoundRect(x + 3, k, j, j, 5, 5);
					g.setColor(new Color(0, 0, 0));
				}
				g.drawImage(Sprites.file.get(), x + 3, k, j, j, null);
				g.drawString((new String[] {
				"Nice Music List from 1986", "1337", "EVEST AntiVirus log",
				"Browser Chronic", "i wanna kms", "WhutsUpp Messages 3k19",
				"Geschichtsvortrage -1905", "Ich mag Kekse", "VIRUS.EXE", "Hello World"
				})[i] + ".txt",  x + 3, k + j + h);
			}
			break;
		
		}
		g.setColor(new Color(0, 0, 0));
		g.drawRect(x, y, width, height);
		g.setFont(new Font(Game.font().getFontName(), Game.font().getStyle(), Game.font().getSize() / 4 * 3));
		g.setColor(new Color(255, 255, 255));
		g.drawString("Level: " + level, 3, client.getHeight() - 33 - Game.font().getSize() / 8 * 3);
	}

	public void handle(InputEvent e) {
		if(e.TYPE == EventType.MOUSE_DOWN) {
			mouseDown = true;
		}else if(e.TYPE == EventType.MOUSE_UP) {
			mouseDown = false;
		}
		if(e.TYPE == EventType.KEYBOARD_UP) {
			if(level == 1) {
				int code = e.CODE - 48;
				if(code > -1 && code < 10) {
					ageInput += code;
				}else if(e.CODE == KeyEvent.VK_ENTER) {
					gaveAge = true;
				}else if(e.CODE == KeyEvent.VK_BACK_SPACE) {
					ageInput = ageInput.substring(0, ageInput.length() > 0 ? ageInput.length() - 1 : 0);
				}
			}else if(level == 2) {
				if(e.CODE == KeyEvent.VK_ENTER) {
					gaveWeb = true;
				}else if(e.CODE == KeyEvent.VK_BACK_SPACE) {
					webInput = webInput.substring(0, webInput.length() > 0 ? webInput.length() - 1 : 0);
				}else{
					webInput += Character.toLowerCase((char)e.CODE);
				}
			}
		}
	}
	
	public void dispose() {
		super.dispose();
		Controller.in.remove(this);
		FileHandler.writeIntoTextFile("save", new String[] {("") + ((level * 1337) * (level * 1337))});
	}

	public static int getLevel() {
		return level;
	}

	public static void setLevel(int level) {
		GuiIngame.level = level;
	}
	
}
