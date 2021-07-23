package entity.mobs.enemy;

public class StatusEffect {
	private StatusType type;
	private double level;
	private int length;
	
	public StatusEffect(StatusType type, double level, int length) {
		this.type = type;
		this.level = level;
		this.length = length;
	}
	public void update() {
		length--;
	}
	
	public StatusType getType() {
		return type;
	}
	public double getLevel() {
		return level;
	}
	public int getTimeLeft() {
		return length;
	}
	public boolean isActive() {
		return length>0;
	}
	public StatusEffect copy() {
		return new StatusEffect(type, level, length);
	}
	
	
	
}
