import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.*;


public class scoreTable implements ActionListener{

	JButton closeButton;
	JDialog scoretable;
	JLabel[] scorelabel;
	File[] file;
	String[] scoreString;
	
	
	
	public scoreTable(JFrame MainFrame){
		scoretable=new JDialog(MainFrame,"Score Table",false);
		Container dialogtable=scoretable.getContentPane();
		JPanel UpperPanel = new JPanel();
		
		
		dialogtable.setLayout(new BorderLayout());
		dialogtable.setPreferredSize(new Dimension(300,100));
		UpperPanel.setLayout(new GridLayout(4,4));
		dialogtable.add(UpperPanel,BorderLayout.NORTH);
		
		scorelabel= new JLabel[16];
		
		
		for(int i=0;i<16;i++)
			scorelabel[i]=new JLabel("               ",JLabel.CENTER);
		scorelabel[1].setText("Square");
		scorelabel[2].setText("Hexagon");
		scorelabel[3].setText("Triangle");
		scorelabel[4].setText("Beginner");
		scorelabel[8].setText("Advanced");
		scorelabel[12].setText("Expert");
	
		for(int i=0;i<16;i++)
			scorelabel[i].setBorder(BorderFactory.createLineBorder(Color.gray));
		
		
		scoreString=new String[9];
		scoreString[0]="scoreSB.txt";
		scoreString[1]="scoreSA.txt";
		scoreString[2]="scoreSE.txt";
		scoreString[3]="scoreHB.txt";
		scoreString[4]="scoreHA.txt";
		scoreString[5]="scoreHE.txt";
		scoreString[6]="scoreTB.txt";
		scoreString[7]="scoreTA.txt";
		scoreString[8]="scoreTE.txt";
		
		file=new File[9];
		for(int i=0;i<9;i++)
			file[i]=new File(scoreString[i]);
		
		for(int i=0;i<16;i++)
			UpperPanel.add(scorelabel[i]);
	
		
		closeButton = new JButton("Close");
		closeButton.setPreferredSize(new Dimension(80,25));
		JPanel LowerPanel = new JPanel(new FlowLayout());
		LowerPanel.add(closeButton);
		//LowerPanel.
		dialogtable.add(LowerPanel,BorderLayout.SOUTH);
		closeButton.addActionListener(this);
		scoretable.pack();
		scoretable.setLocationRelativeTo(null);
		
	}
	
	
	void run(){
		for(int i=0;i<16;i++){
			switch(i){
				case 5:
				case 6:
				case 7:
				case 9:
				case 10:
				case 11:
				case 13:
				case 14:
				case 15:
					scorelabel[i].setText(GetText(i));
					break;
				default:
					break;
			}
		}
		scoretable.show();
	}
	
	public String GetText(int i){
		Scanner s;
		String tmpname=null;
		String tmpscore=null;
		int indicate=0;
		if(i<8)
			indicate=i-5;
		else if(i<12)
			indicate=i-6;
		else if(i<16)
			indicate=i-7;
		
		try{
			
			s = new Scanner(file[indicate]);
			while(s.hasNext()){
				tmpname=s.next();
				tmpscore=s.next();
			}
			s.close();
			if(tmpname=="aaa")
				return "          ";
			else
				return tmpname+" "+tmpscore;
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
			return "      ";
		}
		
	}
	
	
	public void actionPerformed(ActionEvent e){
		//System.out.println("haha");
		//if(e.getSource().equals(closeButton)){
			scoretable.dispose();
		//}
	}
}
