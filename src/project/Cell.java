package project;

import java.awt.*;
import javax.swing.*;

public class Cell extends JButton{
	
	private static final Color defaultColor = Color.white;
	
	private int x;
	private int y;
	
	private boolean isCrossed = false;
	private boolean isFilled = false;
	private boolean	startCell = false;
	private Color currentColor = defaultColor;
	
	public Cell(){
		setDefaults();
		setMargin(new Insets(0, 0, 0, 0));
	}
	
	public void setDefaults(){
		isCrossed = false;
		isFilled = false;
		currentColor = defaultColor;
		setText("");
		setBackground(currentColor);
	}
	
	public void setStartCell(Color color){
		setFill(color);
		startCell = true;
	}	
	
	public boolean isStartCell(){
		return startCell;
	}
	
	public void setCoordinats(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public int getXCoordinate(){
		return this.x;
	}
	
	public int getYCoordinate(){
		return this.y;
	}
	
	public void setCross(Color color){
		setText("X");
		setForeground(color);
		isCrossed = true;
		isFilled = false;
		currentColor = color;
	}
	
	public void setFill(Color color){
		setText("");
		setBackground(color);
		isCrossed = false;
		isFilled = true;
		currentColor = color;
	}
	
	public Color getColor(){
		return currentColor;
	}
	
	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		super.setFont(new Font("TimesRoman", Font.BOLD, height));
	}

	public boolean isCrossed(){
		return isCrossed;
	}
	
	public boolean isFilled(){
		return isFilled;
	}

	public void mark(Color color){
		if (startCell) {
			JOptionPane.showMessageDialog(null, "Победа!");
		}else if (!this.isCrossed&&!this.isFilled){
			this.setCross(color);
		}else if (this.isCrossed) {
			this.setFill(color);	
		}
	}

	public boolean canMark(Color color){
		return (startCell&&(color!=currentColor)) ||
				(!this.isCrossed&&!this.isFilled) ||
				(this.isCrossed&&(this.getColor()!=color)); 
	}
	
}
