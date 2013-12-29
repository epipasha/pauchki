package project;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Game {
	
	private static final int POLE_WIDTH = 20;
	private static final int POLE_HEIGHT = 20;

	private static final int LEFT_COLOM_WIDTH = 100;
	private static final int LABEL_HEIGHT = 20;
	private static final int CELL_SIZE = 20;
	private static final int PADDING = 15;
	
	private static final int WINDOW_WIDTH = POLE_WIDTH*CELL_SIZE+LEFT_COLOM_WIDTH+3*PADDING;
	private static final int WINDOW_HEIGHT = POLE_WIDTH*CELL_SIZE+2*PADDING+30;
	
	private static final int NUMBER_OF_HOD = 5;
	
	private PlayerColor playerColor = new PlayerColor();
	private History history = new History(NUMBER_OF_HOD);
	private HodCount hodCount = new HodCount();
	private JFrame frame;
	private JLabel countLabel;
	private JButton backButton;
	private JButton hodButton;

	private Cell[][] pole = new Cell[POLE_WIDTH][POLE_HEIGHT];
	
	private ActionListener cellClick = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Cell cell = (Cell)e.getSource();
			if (avaliableMark(cell)){
				history.write(cell);
				cell.mark(playerColor.getCurrentColor());
				hodCount.dec();
				refresh();
			}
		}
	};

	private ActionListener backButtonListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			history.previousState();
			hodCount.inc();
			refresh();
		}
	};

	private ActionListener hodButtonListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			playerColor.nextPlayer();
			hodCount.number = NUMBER_OF_HOD;
			history.clear();
			refresh();
		}
	};
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Game window = new Game();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Game() {
		initialize();
		refresh();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Паучки");
		frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);

		for (int i = 0; i < POLE_WIDTH; i++) {
			for (int j = 0; j < POLE_HEIGHT; j++) {
				pole[i][j] = new Cell();
				pole[i][j].setBounds(PADDING+i*CELL_SIZE, PADDING+j*CELL_SIZE, CELL_SIZE, CELL_SIZE);
				pole[i][j].setCoordinats(i, j);
				pole[i][j].addActionListener(cellClick);
				frame.getContentPane().add(pole[i][j]);
			}	
		}
		pole[0][0].setStartCell(playerColor.getColorOfNextPlayer());
		pole[POLE_WIDTH-1][POLE_HEIGHT-1].setStartCell(playerColor.getColorOfNextPlayer());
		
		JLabel newLabel = new JLabel("Текущий ход");
		newLabel.setBounds(WINDOW_WIDTH-PADDING-LEFT_COLOM_WIDTH, PADDING, LEFT_COLOM_WIDTH, LABEL_HEIGHT);
		newLabel.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(newLabel);
		
		countLabel = new JLabel();
		countLabel.setBounds(WINDOW_WIDTH-PADDING-LEFT_COLOM_WIDTH, PADDING+LABEL_HEIGHT, LEFT_COLOM_WIDTH, LABEL_HEIGHT);
		countLabel.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(countLabel);
		
		backButton = new JButton("Back");
		backButton.setBounds(WINDOW_WIDTH-PADDING-LEFT_COLOM_WIDTH, PADDING+LABEL_HEIGHT*2, LEFT_COLOM_WIDTH, LABEL_HEIGHT);
		backButton.addActionListener(backButtonListener);
		frame.add(backButton);
		
		hodButton = new JButton("Сделать ход");
		hodButton.setBounds(WINDOW_WIDTH-PADDING-LEFT_COLOM_WIDTH, PADDING+LABEL_HEIGHT*3, LEFT_COLOM_WIDTH, LABEL_HEIGHT);
		hodButton.setMargin(new Insets(0, 0, 0, 0));
		hodButton.addActionListener(hodButtonListener);
		frame.add(hodButton);
	}

	private void refresh(){
		countLabel.setText(Integer.toString(hodCount.number));
		countLabel.setForeground(playerColor.getCurrentColor());
		
		if (hodCount.number == 0) {
			hodButton.setEnabled(true);
		}else {
			hodButton.setEnabled(false);
		}
	}

	private boolean avaliableMark(Cell cell){
		
		return (hodCount.number>0)&&
				(nearCell(cell)&&
				cell.canMark(playerColor.getCurrentColor()));

	}
	
	private boolean nearCell(Cell cell){
		boolean nearCell = false;
		int x = cell.getXCoordinate();
		int y = cell.getYCoordinate();
		
		
		for (int i = x-1; i < x+2; i++) {
			for (int j = y-1; j < y+2; j++) {
				if (	i<0
						||
						j<0
						||
						i>POLE_WIDTH-1
						||
						j>POLE_HEIGHT-1
						||
						(i==x&&j==y)){
					continue;
				}
				
				
				if (pole[i][j].getColor() == playerColor.getCurrentColor()) {
					nearCell = true;
					break;
				}
			}
		}
		return nearCell;
	}
	
	class HodCount{
		private int number = NUMBER_OF_HOD;
		
		public void dec(){
			number--;
			if (number < 0) {
				playerColor.nextPlayer();
				number = 0;
				history.clear();
			}
		}
		
		public void inc(){
			number++;
			if (number>NUMBER_OF_HOD){
				number = NUMBER_OF_HOD;
			}
		}

	}
	
}
	
