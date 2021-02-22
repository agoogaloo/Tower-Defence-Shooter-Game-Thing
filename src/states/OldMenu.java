package states;

public class OldMenu{
	private String[] options;
	private String[] values;//this lets the options have values attached to them 
	private int selected;
	
	public OldMenu(String[] options, String[] values, int selected) {
		this.options = options;
		this.selected = selected;
		this.values=values;
	}
	
	public OldMenu(String[] options, String[] values) {
		this(options, values, 0);
	}
	
	public OldMenu(String[] options) {
		this(options,new String[options.length], 0);
	}
	
	public String getSelectedoption() {
		return options[selected];
	}
	
	public String getValue(int index) {
		String value=values[index];
		if(value==null) {
			return "";
		}
		return values[index];
	}
	public boolean setValue(int index, String newValue) {
		//this returns false if the index invalid and true it can change 
		if(index<0||index>=values.length) {
			return false;
		}
		values[index]=newValue;
		return true;
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
			return;
		}
		this.selected = selected;
	}
	
	public String[] getOptions() {
		return options;
	}
	
}
