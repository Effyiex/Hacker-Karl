package cf.effectcode.game.client;

public class InputEvent {
	
	public static enum EventType {
		KEYBOARD_DOWN, MOUSE_DOWN, KEYBOARD_UP, MOUSE_UP;
	}
	
	public int CODE;
	
	public EventType TYPE;
	
	public InputEvent(EventType type, int code) {
		CODE = code;
		TYPE = type;
	}

}
