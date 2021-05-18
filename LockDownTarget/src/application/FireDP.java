package application;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.*;


public class FireDP implements ActionListener{
	JFrame frame=new JFrame("Fire");
	JButton reset=new JButton("Restart");
	JButton hint=new JButton("Hint");
	Container container=new Container();
	
	final int row=15;//You can change this for length of the map
	final int col=15;//You can change this for width of the map
	final int shipNum=1;
	int length=3;
	JButton [][] buttons=new JButton[row][col];
	int [][] counts=new int[row][col];
	final int SHIPCODE=10;
	int [][] shipVal=new int[row][col];
	static ArrayList<HashMap<Integer,Integer>> listAll = new ArrayList<HashMap<Integer,Integer>>();

	

		
	
	
	
	
	
	
	
	
	
	
	public FireDP(){
		
		frame.setSize(800, 700);
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		
		addResetButton();
		addHintButton();
		addButton();
		addShip();

		frame.setVisible(true);
	}
	
	public void addResetButton(){
		reset.setBackground(Color.green);
		reset.setOpaque(true);
		reset.addActionListener(this);
		frame.add(reset,BorderLayout.NORTH);
	}
	public void addHintButton(){
		hint.setBackground(Color.yellow);
		hint.setOpaque(true);
		hint.addActionListener(this);
		frame.add(hint,BorderLayout.SOUTH);
	}
	
	public void addShip() {
		
		Random rand=new Random();
		int randRow,randCol,trend,n,collaps;
		collaps=0;
		
		
		
		for(int i=0;i<shipNum;i++) {
			randRow=0;
			randCol=0;
			length=rand.nextInt(2);//set size of the ship
			length+=3;
			
			
			//length=5; <---//You can change this for length of ship, or delete this line to generate a random value of ship length. Make sure that ship length cannot exceed length or width of map!!
			
			
			
			
			
			trend=rand.nextInt(2);//0 means horizontal, 1 means vertical
			
			if(trend==0) {
				randRow=rand.nextInt(row-length+1);
				randCol=rand.nextInt(col);
			}else if(trend==1) {
				randRow=rand.nextInt(row);
				randCol=rand.nextInt(col-length+1);				
			}
			
			//System.out.println(randRow+","+randCol);
			
			if(counts[randRow][randCol]==SHIPCODE){
				i--;
			}else{
				HashMap<Integer,Integer> list=new HashMap<>();
				list.clear();
				if(trend==0){
					for(n=randRow;n<=randRow+length-1;n++) {
						if(counts[n][randCol]==SHIPCODE) {
							i--;
							collaps=1;
							break;
						}
						
					}
					if(collaps==1) {
						collaps=0;
						continue;
					}
					
					for(n=randRow;n<=randRow+length-1;n++) {
						counts[n][randCol]=SHIPCODE;
						list.put(n*10+randCol, randCol);
					}
					
				}else if (trend==1) {
					
					for(n=randCol;n<=randCol+length-1;n++) {
						if(counts[randRow][n]==SHIPCODE) {
							i--;
							collaps=1;
							break;
						}						
						
					}
					
					if(collaps==1) {
						collaps=0;
						continue;
					}
					
					for(n=randCol;n<=randCol+length-1;n++) {				
						counts[randRow][n]=SHIPCODE;
						list.put(randRow*10+n, n);
					}	
				}
				listAll.add(list);
				
				//System.out.println(listAll);

				
				

			
			
			
			
			}
			
			
			
		}
		
		
		
	}
	
	
	public void addButton() {
		frame.add(container,BorderLayout.CENTER);
		container.setLayout(new GridLayout(row,col));
		
		for(int i=0;i<row;i++){
			for(int j=0;j<col;j++){
				JButton button=new JButton();
				button.setBackground(Color.white);
				button.setOpaque(true);
				button.addActionListener(this);
				buttons[i][j]=button;
				container.add(button);
			}
		}		
		
		
	}
	
	void allCell() {
		for(int i=0;i<row;i++) {
			for(int j=0;j<col;j++) {
				buttons[i][j].setText(shipVal[i][j]+"");
			}
		}
	}
	
	void cellValue() {
		int n;
		for(int x=0;x<row;x++) {
			for(int y=0;y<col;y++) {
				shipVal[x][y]=2+Math.min(length, Math.min(x, row-1-x))+Math.min(length, Math.min(y, col-1-y));
				
				
			}
		}
		
		
	}
	
	int shipCount(int x1,int x2, int y1, int y2) {
		if(x1>length) {
			x1=length;
		}
		if(x2>length) {
			x2=length;
		}
		x1=x1+x2-1;
		if(x1<length) {
			x1=0;
		}else {
			x1=x1-length+1;
		}
		
		if(y1>length) {
			y1=length;
		}
		if(y2>length) {
			y2=length;
		}
		y1=y1+y2-1;
		
		if(y1<length) {
			y1=0;
		}else {
			y1=y1-length+1;
		}		
		
		return x1+y1;
	}
	
	
	void openCell(int i,int j) {
		if(buttons[i][j].isEnabled()==false) return;
		
		buttons[i][j].setEnabled(false);
		
		HashMap<Integer,Integer> ship=new HashMap<>();
		Set<Integer> set = new HashSet<>();
		
		if(counts[i][j]==10) {

			for(int n=0;n<listAll.size();n++) {
				ship=listAll.get(n);
				if(ship.containsKey(i*10+j)) {
					if(j==ship.get(i*10+j)) {
						set = ship.keySet();
						for(int key:set) {
							buttons[key/10][ship.get(key)].setText("Hit!!");
							buttons[key/10][ship.get(key)].setBackground(Color.blue);
						}
						set.clear();
						break;
					}					
				}
				
			}
			
			
			
		}else{
			buttons[i][j].setText("Miss");
			buttons[i][j].setBackground(Color.black);
			
			shipVal[i][j]=0;
			buttons[i][j].setText(shipVal[i][j]+"");
			buttons[i][j].setBackground(Color.green);
			int conti=0;
			int hor1=0,ver1=0,hor2=0,ver2=0;//horizontal, vertical
			//int s=0;
			int s=0;
			
			for(int x=1;x<length;x++) {
				conti=0;
				
				//right-down
				if(i+x<col) {
					
					hor1=checkLength('a',i+x,j);//j+1;
					hor2=checkLength('d',i+x,j);//row-j;
					ver1=checkLength('s',i+x,j);//col-i-x;
					ver2=checkLength('w',i+x,j);//x;
					
					hor1=j+1;
					hor2=row-j;
					ver1=col-i-x;
					ver2=x;
					
					
					shipVal[i+x][j]=shipCount(hor1,hor2,ver1,ver2);
					conti=1;
					buttons[i+x][j].setText(shipVal[i+x][j]+"");
					buttons[i+x][j].setBackground(Color.yellow);
				}
				//left-up
				if(i-x>=0) {
					

					hor1=checkLength('a',i-x,j);
					hor2=checkLength('d',i-x,j);
					ver1=checkLength('s',i-x,j);
					ver2=checkLength('w',i-x,j);
					
					hor1=j+1;
					hor2=row-j;
					ver1=i-x+1;
					ver2=x;
					
					shipVal[i-x][j]=shipCount(hor1,hor2,ver1,ver2);
					conti=1;
					buttons[i-x][j].setText(shipVal[i-x][j]+"");
					buttons[i-x][j].setBackground(Color.pink);
					
				}
				//down-right
				if(j+x<row) {
					hor1=checkLength('a',i,j+x);
					hor2=checkLength('d',i,j+x);
					ver1=checkLength('s',i,j+x);
					ver2=checkLength('w',i,j+x);
					
					hor1=row-j-x;
					hor2=x;
					ver1=i+1;
					ver2=col-i;
					
					
					//System.out.println(hor1+","+ver1+","+ver2);				
					
					shipVal[i][j+x]=shipCount(hor1,hor2,ver1,ver2);
					conti=1;
					buttons[i][j+x].setText(shipVal[i][j+x]+"");
					buttons[i][j+x].setBackground(Color.green);
					
				}
				//up-left
				if(j-x>=0) {
					
					hor1=checkLength('a',i,j-x);
					hor2=checkLength('d',i,j-x);
					ver1=checkLength('s',i,j-x);
					ver2=checkLength('w',i,j-x);
					
					hor1=j-x+1;
					hor2=x;
					ver1=i+1;
					ver2=col-i;
					
					
					//System.out.println(hor1+","+ver1+","+ver2+".");
					
					shipVal[i][j-x]=shipCount(hor1,hor2,ver1,ver2);
					conti=1;
					buttons[i][j-x].setText(shipVal[i][j-x]+"");
					buttons[i][j-x].setBackground(Color.orange);
				}
				
				if(conti==0) break;
				conti=0;
				s++;

				
			}
			
			
		}
		




	}
	
	
	int checkLength(char trend,int x, int y) {
		//trend:w,a,s,d
		int n;
		int last=0;
		n=1;
		switch(trend){
		case 'w':
			while(x-n>=0) {
				if(counts[y][x-n]==10) {
					return n;
				}
				n++;
			}
			break;
		case 'a':
			while(y-n>=0) {
				if(counts[y-n][x]==10) {
					return n;
				}
				n++;
			}

			
			
			break;
		case 's':
			while(x+n<col) {
				if(counts[y][x+n]==10) {
					return n;
				}
				n++;
			}
			break;
		case 'd':
			while(y+n<row) {
				if(counts[y+n][x]==10) {
					return n;
				}
				n++;
			}
			
			
			
			break;
		default:last=0;
		
		}
		
		return last;
		
		
		
	}
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		
		JButton button=(JButton)e.getSource();
		if(button.equals(reset)){
			for(int i=0;i<row;i++){
				for(int j=0;j<col;j++){
					buttons[i][j].setText("");
					buttons[i][j].setEnabled(true);
					buttons[i][j].setBackground(Color.white);
					counts[i][j]=0;
				}
			}
			


			
			
			
			addShip();
			
		}else if(button.equals(hint)) {
			cellValue();
			allCell();
		}else{


			//int count=0;
			for(int i=0;i<row;i++){
				for(int j=0;j<col;j++){
					if(button.equals(buttons[i][j])){
						openCell(i,j);
						
						return;
				}
			}
		}
			
			
		}
		
	 }
	
	
	
	
	
	
	public static void main(String[] args) {
		FireDP fire=new FireDP();

		
		System.out.println(listAll);
		
	}


	

}
