package SlitherIOjava;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import static java.lang.Thread.sleep;


public class GameScreen extends JPanel implements Runnable {
	//area
	static int[][] mapArea = new int[25][25];
	
	static int padding = 10;
    static int WIDTH = 500;
    static int HEIGHT = 500;
    
    static boolean isPlaying = true;
    static boolean textBlinking = true; //Text nhap nhay'
    static boolean isGameOver = false;
	
    static int diem = 0;
    
	//snake
	Snake s1; 
	Snake s2;
	// update 
	Thread thread;
	
	public GameScreen() {
		s1 = new Snake(0, 2, 0, 1, 1); 
		s2 = new Snake(24, 2, 24, 1, 2);
		
		//tao food dau` game
		mapArea[s1.createNewFood().x][s1.createNewFood().y] = 1;
		mapArea[s2.createNewFood().x][s2.createNewFood().y] = 1;
		
		//Tao da' (lao vao` chet luon)
		mapArea[10][10] = 3;
		mapArea[20][20] = 3;
		mapArea[10][20] = 3;
		mapArea[20][10] = 3;
		mapArea[5][10] = 3;
		mapArea[5][5] = 3;
		
		thread = new Thread(this);
		thread.start();
		
	}
	public void run() {
		long t = 0;
		
		while (true) {
			if (isPlaying) { // neu chuong trinh dang chay
				s1.update(); // Tdn doan nay` k chay
				s2.update();
			}
			
			if (System.currentTimeMillis()-t>500) {
				textBlinking = !textBlinking;
				t = System.currentTimeMillis();
			}
			
			repaint();
			//System.out.println(System.currentTimeMillis());
			try {
				sleep(20); // ko de game update qua nhanh
			} catch (InterruptedException ex) { }
		}
	}
	// ve map
	public void paintMap(Graphics g) {
		g.setColor(Color.gray);
		for (int i=0;i<25;i++)
			for (int j=0;j<25;j++) {
				if (mapArea[i][j]==0) g.setColor(Color.gray);   // o trong'
				if (mapArea[i][j]==1) g.setColor(Color.green);	// o co' thuc an
//				if (mapArea[i][j]==2) g.setColor(Color.cyan); 	// o co' ran'
				if (mapArea[i][j]==3) g.setColor(Color.black); 	// o co' da', di vao` la` chet'
				g.fillRect(i*20+1+padding, j*20+1+padding, 18, 18);
			}
	}
	//ve vien` xung quanh
	private void paintAround(Graphics g) {
		g.setColor(Color.pink); // mau nen`
		
		//play area
		g.drawRect(0, 0, WIDTH+padding*2, HEIGHT+padding*2);
        g.drawRect(1, 1, WIDTH+padding*2-2, HEIGHT+padding*2-2);
        g.drawRect(2, 2, WIDTH+padding*2-4, HEIGHT+padding*2-4);
        
        //score area
        g.drawRect(WIDTH+padding*2, 0, 230+padding*2, HEIGHT+padding*2);
        g.drawRect(WIDTH+padding*2+1, 1, 230+padding*2-2, HEIGHT+padding*2-2);
        g.drawRect(WIDTH+padding*2+2, 2, 230+padding*2-4, HEIGHT+padding*2-4);
        
	}
	
	public void paint(Graphics g) {
		// lam` trang' toan` man` hinh` game
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, WIDTH+padding*2, HEIGHT+padding*2);
		g.fillRect(0, 0, 800, 600);
		
		//ve map
		paintMap(g);
		//ve 2 con ran
		s1.paintSnake(g);
		s2.paintSnake(g);
		
		paintAround(g);
//		System.out.println("hey");
		//Neu nhu dang k choi 
		if (!isPlaying) {
			if (textBlinking) { // text nhap nhay'
				g.setColor(Color.black);
	            g.setFont(g.getFont().deriveFont(20.0f));
				g.drawString("PRESS SPACE TO PLAY GAME!", 100, 220);	
			}
		}
		// Neu nhu thua r
		if (isGameOver) {
			g.setColor(Color.black);
            g.setFont(g.getFont().deriveFont(28.0f));
			g.drawString("Game over", 180, 120);
		}
		
		// Hien thi Point
		g.setColor(Color.black);
        g.setFont(g.getFont().deriveFont(32.0f));
		g.drawString("Point: "+diem, 590, 120);
		
		
//		 Show high score
		g.drawString("HIGH SCORE:", 530, 200);
		for (int i=0; i<SlitherIOjava.users.size() ;i++) {
			g.setColor(Color.black);
	        g.setFont(g.getFont().deriveFont(20.0f));
			g.drawString(SlitherIOjava.users.get(i).toString(), 530, i * 20 + 240);
		}
	}
}
