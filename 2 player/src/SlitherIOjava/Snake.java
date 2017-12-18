package SlitherIOjava;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;
import javax.swing.JOptionPane;

public class Snake {
	public static int length = 2;
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
	
	int x1,x2,y1,y2,type_snake;
	
	public Snake(int x1, int y1, int x2, int y2, int type_snake) {
		x = new int[200];
		y = new int[200];
		
		//Luu lai vi tri bat dau`
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.type_snake = type_snake;
		
		//Tao ran tren mapArea
		x[0]=x1;y[0]=y1;   
		x[1]=x2;y[1]=y2;
//		GameScreen.mapArea[x1][y1] = 2;  // dau` ran' k danh' dau'
		GameScreen.mapArea[x2][y2] = 2;
	}
	// bat dau lai game
	public void resetGame(){
        x = new int[100];
        y = new int[100];
        
        x[0]=x1;y[0]=y1;
		x[1]=x2;y[1]=y2;
//		GameScreen.mapArea[x1][y1] = 2;   // dau` ran' k danh' dau'
		GameScreen.mapArea[x2][y2] = 2;
		GameScreen.diem = 0;
		//xoa du lieu toan map
		for (int i=0;i<25;i++)
			for (int j=0;j<25;j++) 
				GameScreen.mapArea[i][j] = 0;
		
		//Tao da' (lao vao` chet luon)
		GameScreen.mapArea[10][10] = 3;
		GameScreen.mapArea[20][20] = 3;
		GameScreen.mapArea[10][20] = 3;
		GameScreen.mapArea[20][10] = 3;
		GameScreen.mapArea[5][10] = 3;
		GameScreen.mapArea[5][5] = 3;
		
		//Tao thuc an
		Point cord = createNewFood();
		GameScreen.mapArea[cord.x][cord.y] = 1; // Tao thuc an 1
		cord = createNewFood();
		GameScreen.mapArea[cord.x][cord.y] = 1; // Tao thuc an 2
		
        length = 2;
        vector = Snake.GO_DOWN;
    }
	
	public void setVector(int v) {
		if (vector != -v && allowChangeVector) {
			vector = v;
			allowChangeVector = false;
		}
	}
	
	//Ktra food
	public boolean checkFood(int xx, int yy) {
//		for(int i=0;i<length;i++)
			if (GameScreen.mapArea[xx][yy] > 0) return true; // toa do co ran hoac da'
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
		
//		for(int i=2;i<length;i++) { //ktra chet chua
			if (GameScreen.mapArea[x[0]][y[0]] > 1) {  //chet cmnr
					
				//Nhap du lieu de luu tru
				String name = JOptionPane.showInputDialog("Awwww thua mat roi. Moi ban nhap ten Team (k co' dau' cach'): ");
				System.out.println("ten la` :"+name);
				if (name != null) {		// neu ngdung` k nhap ten
					SlitherIOjava.users.add(new User(name, String.valueOf(GameScreen.diem)));
				}
				// xoa du~ lieu cua mapArea
//				for (int i=0;i<25;i++)
//					for (int j=0;j<25;j++) 
//						GameScreen.mapArea[i][j] = 0;
				
				// tam dung va` ket thuc game
                GameScreen.isPlaying=false;
				GameScreen.isGameOver=true;
			}
//		}
		
//		GameScreen.diem = length-3;
		
		//System.out.println(System.currentTimeMillis());
		if (System.currentTimeMillis()-timeMove > spd-GameScreen.diem*3) {// moi lan` update
			
			allowChangeVector = true;  // Cho phep chuyen huong' 
			
			if (GameScreen.mapArea [x[0]][y[0]]==1) { //an dc moi`
				length++;
				GameScreen.diem++;
//				GameScreen.mapArea [x[0]][y[0]] = 0;
				Point cord = createNewFood(); //Tao thuc an moi
				GameScreen.mapArea[cord.x][cord.y] = 1;
			} else GameScreen.mapArea[x[length-1]][y[length-1]] = 0;
			
			GameScreen.mapArea [x[0]][y[0]] = 2;  // danh dau' dau`
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
		//Set color cho ran'
		if (type_snake == 1) g.setColor(Color.red);
		if (type_snake == 2) g.setColor(Color.cyan);
		
		for (int i=0;i<length;i++) {
			g.fillRect(x[i]*20+1+GameScreen.padding, y[i]*20+1+GameScreen.padding, 18, 18);
		}
	}
}
