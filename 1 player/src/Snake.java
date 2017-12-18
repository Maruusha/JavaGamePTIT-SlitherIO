package SlitherIOjava;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;
import javax.swing.JOptionPane;

public class Snake {
	int length = 3;
	int[] x;
	int[] y;
	
	public static int GO_UP = 1;
    public static int GO_DOWN = -1;
    public static int GO_LEFT = 2;
    public static int GO_RIGHT = -2;
    
    int vector = GO_DOWN;
    
    int spd = 200; // khoang tg cho moi lan` update 
    
	long timeMove = 0;
	
	boolean allowChangeVector = true;
	
	public Snake() {
		x = new int[100];
		y = new int[100];
		 
		x[0]=2;y[0]=4;
		x[1]=2;y[1]=3;
		x[2]=2;y[2]=2;
	}
	// bat dau lai game
	public void resetGame(){
        x = new int[100];
        y = new int[100];
        
        x[0]=2;y[0]=4;
		x[1]=2;y[1]=3;
		x[2]=2;y[2]=2;
       
        length = 3;
        vector = Snake.GO_DOWN;
    }
	
	public void setVector(int v) {
		if (vector != -v && allowChangeVector) {
			vector = v;
			allowChangeVector = false;
		}
	}
	
	//Ktra food
	public boolean checkFood(int x1, int y1) {
		for(int i=0;i<length;i++)
			if (x[i]==x1 && y[i]==y1) return true;
		return false;	
	}
		
	//Tao food moi'
	public Point createNewFood() {
		Random r = new Random(); 
		int x;
		int y;
		
		do {
			x = r.nextInt(24);
			y = r.nextInt(24);
		} while (checkFood(x, y));
		
		return new Point(x, y);
	}
	
	//di chuyen
	public void update() {
		
		for(int i=2;i<length;i++) {
			if (x[0]==x[i] && y[0]==y[i]) {
				
				//Nhap du lieu de luu tru
				String name = JOptionPane.showInputDialog("Moi ban nhap ten: ");
                SlitherIOjava.users.add(new User(name, String.valueOf(GameScreen.diem)));
				
                GameScreen.isPlaying=false;
				GameScreen.isGameOver=true;
			}
		}
		
		GameScreen.diem = length-3;
		
		//System.out.println(System.currentTimeMillis());
		if (System.currentTimeMillis()-timeMove > spd-length*5) {// moi lan` update
			
			allowChangeVector = true;  // Cho phep chuyen huong' 
			
			if (GameScreen.mapArea [x[0]][y[0]]==2) {
				length++;
				GameScreen.mapArea [x[0]][y[0]] = 0;
				GameScreen.mapArea[createNewFood().x][createNewFood().y] = 2;
			}
			
			for(int i=length-1;i>0;i--) { //tao di chuyen cho ca con ran
				x[i]=x[i-1];
				y[i]=y[i-1]; 
			}
			
			if (vector==Snake.GO_UP) y[0]--;	// tao huong di chuyen
			if (vector==Snake.GO_DOWN) y[0]++;
			if (vector==Snake.GO_LEFT) x[0]--;
			if (vector==Snake.GO_RIGHT) x[0]++;
//			System.out.println("asd");
//			System.out.println(System.currentTimeMillis());
			
			if(x[0]<0) x[0]=24;
			if(x[0]>24) x[0]=0;
			if(y[0]<0) y[0]=24;
			if(y[0]>24) y[0]=0;
			
			timeMove = System.currentTimeMillis();
		}
	}
	
	public void paintSnake(Graphics g) {
		g.setColor(Color.red);
		for (int i=0;i<length;i++) {
			g.fillRect(x[i]*20+1+GameScreen.padding, y[i]*20+1+GameScreen.padding, 18, 18);
		}
	}
}
