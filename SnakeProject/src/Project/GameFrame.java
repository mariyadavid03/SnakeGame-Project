package Project;

import javax.swing.JFrame;

public class GameFrame extends JFrame{
	
	GameFrame(){
		//Setting Up the Game Frame
		this.add(new GamePanel());
		this.setTitle("Snake Game");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		
	}

}
