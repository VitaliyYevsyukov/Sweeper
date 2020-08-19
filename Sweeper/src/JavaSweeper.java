import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import sweeper.Box;
import sweeper.Coord;
import sweeper.Game;
import sweeper.Ranges;

public class JavaSweeper extends JFrame {
	
	private Game game;
	
	private JPanel panel;
	private JLabel label;
	
	private final int COLS = 9;
	private final int ROWS = 9;
	private final int BOMBS = 10;
	private final int IMAGE_SIZE = 50;
	
	public static void main(String[] args) {
		
		new JavaSweeper().setVisible(true);
		
	}
	
	private JavaSweeper() {
		game = new Game(COLS, ROWS, BOMBS);
		game.start();
		setImages();
		initLabel();
		initPanel();
		initFrame();
	}
	
	private void initLabel() {
		label = new JLabel("Welcome!");
		add(label, BorderLayout.SOUTH);
	}
	
	private void initPanel() {
		panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				for(Coord coord : Ranges.getAllCoords()) {
					
					g.drawImage((Image)game.getBox(coord).image, coord.x * IMAGE_SIZE, coord.y * IMAGE_SIZE, this);
				}
				
			}
		};
		
		panel.addMouseListener(new MouseListener() {
						
			@Override
			public void mousePressed(MouseEvent e) {
				
				int x = e.getX() / IMAGE_SIZE;
				int y = e.getY() / IMAGE_SIZE;
				Coord coord = new Coord(x, y);
				if(e.getButton() == MouseEvent.BUTTON1)
					game.pressLeftButton(coord);
				panel.repaint();
				if(e.getButton() == MouseEvent.BUTTON3)
					game.pressRightButton(coord);
				panel.repaint();
				if(e.getButton() == MouseEvent.BUTTON2)
					game.start();
				label.setText(getMessage());
				panel.repaint();
				
			}

			private String getMessage() {
				switch(game.getState()) {
				case PLAYED : return "Think twice!";
				case BOMBED : return "YOU LOSE! Booooooooom......";
				case WINNER : return "CONGRATULATIONS!";
				default : return "Welcome!";
				}
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		panel.setPreferredSize(new Dimension(Ranges.getSize().x * IMAGE_SIZE, Ranges.getSize().y * IMAGE_SIZE));
		add(panel);
	}
	
	private void initFrame() {		
		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Sweeper");
		setResizable(false);
		setVisible(true);
		setIconImage(getImage("icon"));
		pack();
		setLocationRelativeTo(null);
	}
	
	private void setImages() {
		for(Box box : Box.values()) {
			box.image = getImage(box.name().toLowerCase());
		}
	}
	
	private Image getImage(String name) {
		String fileName = "img/" + name + ".png";
		ImageIcon icon = new ImageIcon(getClass().getResource(fileName));
		return icon.getImage();
	}
	
}
