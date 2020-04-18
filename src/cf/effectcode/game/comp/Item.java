package cf.effectcode.game.comp;

import cf.effectcode.game.render.Sprite;

public class Item {

	private Sprite sprite;
	
	private String name;
	
	public Item(String name, Sprite sprite) {
		this.name = name;
		this.sprite = sprite;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
