package project;

import java.awt.Color;

public class History {

	private static final String NONE = "none";
	private static final String CROSS= "cross";
	private static final String FILL = "fill";
	
	private int marker = 0;
	private Cell[] cell;
	private Color[] color;
	private String[] state;
	
	
	public History(int lenght){
		cell = new Cell[lenght];
		color = new Color[lenght];
		state = new String[lenght];
	}

	public void write(Cell cell){
		this.cell[marker] = cell;
		color[marker] = cell.getColor();
		
		if(cell.isCrossed()){
			state[marker] = CROSS;
		}else if (cell.isFilled()) {
			state[marker] = FILL;
		}else {
			state[marker] = NONE;
		}
		
		marker++;
	}
	
	public void previousState(){
		marker--;
		if (marker<0) {
			marker = 0;
		}else {
			if (state[marker].equals(FILL)){
				cell[marker].setFill(color[marker]);
			}else if (state[marker].equals(CROSS)) {
				cell[marker].setDefaults();
				cell[marker].setCross(color[marker]);
			}else if (state[marker].equals(NONE)) {
				cell[marker].setDefaults();
			};
		};
	}

	public void clear(){
		marker = 0;
	}
	
}
