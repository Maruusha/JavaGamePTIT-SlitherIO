package SlitherIOjava;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFrame;

public class SlitherIOjava extends JFrame {

	GameScreen game;
	
	public static ArrayList<User> users;
	
	public SlitherIOjava() {
		setSize(800,600); // Dat do lon man` hinh`
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Tat khi an X
		//Doc du lieu tu file save
		users = new ArrayList<User>();
		ReadData();
		
		game = new GameScreen();
		add(game);
		
		this.addKeyListener(new handler());
		// Update du lieu khi tat chuong trinh
		this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e) {
                UpdateData();
            }
        });
		
		setVisible(true);
	}
	public static void main(String[] args) {
		SlitherIOjava f = new SlitherIOjava();
	}

	//Kiem tra ban` phim'
	private class handler implements KeyListener {
		@Override
        public void keyTyped(KeyEvent e) {}
		@Override
        public void keyPressed(KeyEvent e) {
			// pause game = space
			if(e.getKeyCode()==KeyEvent.VK_SPACE) {
				GameScreen.isPlaying=!GameScreen.isPlaying;
				if (GameScreen.isGameOver) {
					GameScreen.isGameOver=false;
					game.s1.resetGame();
					game.s2.resetGame();
				}
			}
			//di chuyen len xuong trai phai
				//Ran1
			if(e.getKeyCode()==KeyEvent.VK_UP) game.s2.setVector(Snake.GO_UP);
            if(e.getKeyCode()==KeyEvent.VK_DOWN) game.s2.setVector(Snake.GO_DOWN);
            if(e.getKeyCode()==KeyEvent.VK_LEFT) game.s2.setVector(Snake.GO_LEFT);
            if(e.getKeyCode()==KeyEvent.VK_RIGHT) game.s2.setVector(Snake.GO_RIGHT);
            	//Ran2
            if(e.getKeyCode()==KeyEvent.VK_W) game.s1.setVector(Snake.GO_UP);
            if(e.getKeyCode()==KeyEvent.VK_S) game.s1.setVector(Snake.GO_DOWN);
            if(e.getKeyCode()==KeyEvent.VK_A) game.s1.setVector(Snake.GO_LEFT);
            if(e.getKeyCode()==KeyEvent.VK_D) game.s1.setVector(Snake.GO_RIGHT);
            
		}
		@Override
        public void keyReleased(KeyEvent e) {}
	}
	
	
	public static void UpdateData(){
        
        BufferedWriter bw = null;
        try {
        	//mo file
            FileWriter fw = new FileWriter("data/data.txt");
            bw = new BufferedWriter(fw);
            // in ra file
            for(User u: users) {
            	bw.write(u.getName() + " " + u.getPoint());
                bw.newLine();
          	}
            
        } catch (IOException ex) {}
        finally{
            try {
                bw.close();
            } catch (IOException ex) {}
        }
        
    }
	// Doc data tu file txt
	public static void ReadData(){
        
        try {
        	//mo file
            FileReader fr = new FileReader("data/data.txt");
            BufferedReader br = new BufferedReader(fr);
            // Doc data
            String line = null;
            while((line = br.readLine())!=null){
                String[] str = line.split(" ");
                users.add(new User(str[0], str[1]));
            }
            
            br.close();
        } catch (IOException ex) {}
        
    }
}
