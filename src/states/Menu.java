package states;

public class Menu {
	String[] options;
	int selected;
	
	public Menu(String[] options, int selected) {
		this.options = options;
		this.selected = selected;
	}
	public Menu(String[] options) {
		this(options, 0);
	}
	
	public String getSelectedValue() {
		return options[selected];
	}
	
	public int getSelected() {
		return selected;
	}
	
	public void setSelected(int selected) {
		if(selected<0) {
			this.selected=options.length-1;
			return;
		}
		if(selected>=options.length) {
			this.selected=0;
		}
		this.selected = selected;
	}
	
	public String[] getOptions() {
		return options;
	}
	
}
