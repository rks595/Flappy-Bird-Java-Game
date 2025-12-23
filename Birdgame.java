package birdGame;

import javax.swing.JFrame;

public class Birdgame {
    Birdgame(){
    	int width=360;
    	int height=640;
    	
    	JFrame frame = new JFrame("FLAPPY BIRD");
    	frame.setSize(width,height);
    	frame.setLocationRelativeTo(null);
    	frame.setResizable(false);
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	
    	FlappyBird flappybird = new FlappyBird();
    	frame.add(flappybird);
    	frame.pack();
    	flappybird.requestFocus();
    	frame.setVisible(true);
    	
    }
    public static void main(String args[]) {
    	new Birdgame();
    }
}