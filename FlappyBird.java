package birdGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Timer;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class FlappyBird extends JPanel implements ActionListener,KeyListener{
	int framewidth=360;
	int frameheight=640;
	
	//image
	Image backgroundimg;
	Image birdimg;
	Image toppipeimg;
	Image bottompipeImg;
	
	//bird  //4
	int birdx=framewidth/8;//location of bird
	int birdy=frameheight/2;
	int birdwidth=34;
	int birdheight=24;
	
	class Bird{  //5
		int x =birdx;
		int y=birdy;
		int width=birdwidth;
		int height=birdheight;
		private Image img;
		
		Bird(Image img){  //6
			this.img=img;
		}
	}
	
	//pipes
	int pipeX=framewidth;
	int pipeY=0;
	int pipewidth=64;
	int pipeheight=512;
	
	class Pipe{
		int x=pipeX;
		int y=pipeY;
        int width= pipewidth;
        int height=pipeheight;
        Image img;
        boolean passed;
        
        Pipe(Image img){
        	this.img = img;
        }
	}
	
	
	//game logic
	Bird bird; //7
	int velocityX=-4;
	int velocityY=0;
	int gravity=1;
	
	ArrayList<Pipe> pipes;
	Random random=new Random();
	
	Timer gameloop;
	Timer placepipeTimer;
	boolean gameOver=false;
	double score=0;
	
	
    FlappyBird(){  //1
    	setPreferredSize(new Dimension(framewidth,frameheight));
    	setFocusable(true);
    	addKeyListener(this);
    	//    	setBackground(Color.blue);
    	
    	backgroundimg = new ImageIcon("birdimage/birdimage/flappybirdbg.png").getImage();
    	bottompipeImg = new ImageIcon("birdimage/birdimage/bottompipe.png").getImage();
    	toppipeimg = new ImageIcon("birdimage/birdimage/toppipe.png").getImage();
    	birdimg = new ImageIcon("birdimage/birdimage/flappybird.png").getImage();
    	
    	bird =new Bird(birdimg);
    	pipes=new ArrayList<Pipe>();
    	
    	placepipeTimer=new Timer(1500,new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent e) {
    			placePipe();
    		}
    	});
    	
    	placepipeTimer.start();
    	
    	//gameloop
    	gameloop = new Timer(1000/60,this);
    	gameloop.start(); 
    	//now we need to make the bird move
    }
    
    
    public void placePipe() {
    	int randomPipeY=(int) (pipeY-pipeheight/4-Math.random()*pipeheight/2);
    	int openingSpace=frameheight/4;
    	
        Pipe topPipe=new Pipe(toppipeimg);
        topPipe.y=randomPipeY;
        pipes.add(topPipe);
        
        Pipe bottomPipe=new Pipe(bottompipeImg);
        bottomPipe.y=topPipe.y+pipeheight+openingSpace;
        pipes.add(bottomPipe);
    }
    
    public void paintComponent(Graphics g) {  //2
    	super.paintComponent(g);
    	draw(g);
    }
    
    public void draw(Graphics g) { //3
    	g.drawImage(backgroundimg,0,0,framewidth,frameheight,null);
    	g.drawImage(birdimg, bird.x, bird.y, birdwidth, birdheight, null);
    	
    	for(int i=0;i<pipes.size();i++) {
    		Pipe pipe=pipes.get(i);
    		g.drawImage(toppipeimg, pipe.x, pipe.y, pipe.width, pipe.height, null);
    	}
    	for (Pipe pipe : pipes) {
    	    g.drawImage(bottompipeImg, pipe.x, pipe.y, pipe.width, pipe.height, null);
    	}
    	
    	g.setColor(Color.white);
    	g.setFont(new Font("Arial",Font.PLAIN,32));
    	if(gameOver) {
    		g.drawString("GameOver:" + String.valueOf((int)score), 10, 35);
    	}else {
    		g.drawString(String.valueOf((int)score), 10, 35);
    	}
    	
    }
    
    public void move() {
    	velocityY+=gravity;
    	bird.y+=velocityY;
    	bird.y=Math.max(bird.y, 0);
    	
    	
    	for(int i=0;i<pipes.size();i++) {
    		Pipe pipe=pipes.get(i);
    		pipe.x+=velocityX;
    	
    	    if(!pipe.passed && bird.x > pipe.x + pipe.width) {
    		    pipe.passed=true;
    		    score+=0.5;
    	    }
    	    if(collision(bird,pipe)) {
    		    gameOver=true;
    	    }
    	 }
    	
    	if(bird.y>frameheight) {
    		gameOver=true;
    	}
    }
    
    public boolean collision(Bird a, Pipe b) {
    	return a.x < b.x + b.width &&
    			a.x + a.width >b.x &&
    			a.y < b.y + b.height &&
    			a.y + a.height > b.y;
    }
    
    public void actionPerformed(ActionEvent ae) { //8
    	move();
    	repaint();
    	if(gameOver) {
    		placepipeTimer.stop();
    		gameloop.stop();
    	}
    }

	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_SPACE) {
			velocityY=-10;
		}
		if(gameOver) {
			bird.y=birdy;
			velocityY=0;
			pipes.clear();
			score=0;
			gameOver=false;
			gameloop.start();
			placepipeTimer.start();
		}
		
	}
	
	
    @Override
	public void keyTyped(KeyEvent e) {
		
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		
	}
}

//
// implements ActionListener,KeyAdapter