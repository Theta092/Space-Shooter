/*
 * Nicholas Rempel
 * Dec 19 2022
 * An infinite space shooter game
 */
package animation;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game {

	JFrame frame;
	JPanel panel;
	
	public Game()
	{
		frame = new JFrame("Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 500);
		frame.setLocationRelativeTo(null);
		
		panel = new MyPanel();
		frame.add(panel);
		
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Game();
	}

}
