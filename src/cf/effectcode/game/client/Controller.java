package cf.effectcode.game.client;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import javax.swing.JFrame;

import cf.effectcode.game.Game;
import cf.effectcode.game.client.InputEvent.EventType;


public class Controller extends ArrayList<Controllable> implements KeyListener, MouseListener {

	public static Controller in = new Controller();
	
	private static ArrayList<Integer> KEYS = new ArrayList<Integer>();
	private static ArrayList<Integer> BUTTONS = new ArrayList<Integer>();
	
	public static void update() {
		for(int CODE : KEYS) {
			EventType TYPE = EventType.KEYBOARD_DOWN;
			for(Controllable c : in) {
				c.handle(new InputEvent(TYPE, CODE));
			}
		}
		for(int CODE : BUTTONS) {
			EventType TYPE = EventType.MOUSE_DOWN;
			for(Controllable c : in) {
				c.handle(new InputEvent(TYPE, CODE));
			}
		}
	}
	
	public static void clearInput() {
		 KEYS.clear();
		 BUTTONS.clear();
	}
	
	public static void addToFrame(JFrame frame) {
		frame.addKeyListener(in);
		frame.addMouseListener(in);
	}

	public void mousePressed(MouseEvent e) {
		if(!BUTTONS.contains((Object)e.getButton())) {
			BUTTONS.add(e.getButton());
		}
	}

	public void mouseReleased(MouseEvent e) {
		if(BUTTONS.contains((Object)e.getButton())) {
			BUTTONS.remove((Object)e.getButton());
			try {
				for(Controllable c : in) {
					c.handle(new InputEvent(EventType.MOUSE_UP, e.getButton()));
				}
			} catch (ConcurrentModificationException ex) {
				// NOTHING
			}
		}
	}

	public void keyPressed(KeyEvent e) {
		if(!KEYS.contains((Object)e.getKeyCode())) {
			KEYS.add(e.getKeyCode());
		}
	}

	public void keyReleased(KeyEvent e) {
		if(KEYS.contains((Object)e.getKeyCode())) {
			KEYS.remove((Object)e.getKeyCode());
			try {
				for(Controllable c : in) {
					c.handle(new InputEvent(EventType.KEYBOARD_UP, e.getKeyCode()));
				}
			} catch (ConcurrentModificationException e2) {
				// NOTHING
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_F5) {
			Game.getInstance().getGui().reload();
		}
	}

	
	// Useless
	public void keyTyped(KeyEvent e) {}
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}

}
