import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Writer;
import javax.swing.*;
import java.io.*;
import java.util.*;
import java.lang.*;


public class Record implements ActionListener{

	JDialog dialog;
	int score=0;
	String user;
	JLabel Message;
	JTextField Username;
	JButton Conf;
	//Container contentpane;
	File[] file;
	String[] scoreString;
	int indicator;

	
	public Record(JFrame Mainwindow){
		dialog = new JDialog(Mainwindow,"Highest Score",false);
		Container dialogPane=dialog.getContentPane();				
		dialogPane.setLayout(new GridLayout(3,1));
	
		Message=new JLabel("                                                                   ",JLabel.CENTER);
		Message.setFont(new Font("Arial",Font.PLAIN,15));
		Message.setPreferredSize(new Dimension(180,35));
		dialogPane.add(Message);
		
		
		Username=new JTextField();
		Username.setFont(new Font("Arial",Font.PLAIN,20));
		
		dialogPane.add(Username);
		
		
		Conf=new JButton("Confirm");
		
		dialogPane.add(Conf);
		
		Username.addActionListener(this);////
		Conf.addActionListener(this);
		
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

		
		dialog.setLocationRelativeTo(null);
		//dialog.pack();
	}
	
	public void run(int pscore,int num) {
		
		score = pscore;
		indicator= num;
		System.out.println(num);
	
		Formatter newfile;
		if(!file[num].exists()){	
			try {
				newfile = new Formatter(scoreString[num]);
				Message.setText("<html>You Get the highest score!<br/>Please input your name:<html>");
				PanelInput(score);
				newfile.format("%s %s",getUsername(),Integer.toString(score));
				newfile.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
			return;
		}
		
		Scanner x;
		try {
			x = new Scanner(file[num]);
			String pastscore = null;
			while(x.hasNext()){
				pastscore = x.next();
			}
			x.close();
			if(Integer.parseInt(pastscore)<=score){
				
				return;
			}
			else{
				Message.setText("<html>You Get the highest score!<br/>Please input your name:<html>");
				PanelInput(score);
				return;
				}
			} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		
			
	}
	
	//Show Panel
	public void PanelInput(int score){
		Username.setText("");
		//this.pack();
		//this.setLocation(new Point(200, 400));
		
		dialog.show();
		//this.setVisible(true);
	}
	
	
	//Get username
	String getUsername(){
		return user;
	}
	
	//Action performed when the user input their name
	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==Username){
			user=Username.getText();
			while(user.equals("")){
				Message.setText("<html>User Name should not be blank<br/>Please input:</html>");
				dialog.show();
				//this.setVisible(true);
				return;
			}
			//dialog.show();
			dialog.dispose();////
			//this.setVisible(false);/////
			this.RecordInput(indicator);
			
			return;
		}
		
		if(e.getSource()==Conf){
			user=Username.getText();
			while(user.equals("")){
				Message.setText("<html>User Name should not be blank<br/>Please input:</html>");
				dialog.show();
				//this.setVisible(true);
				return;
			}
			//dialog.show();
			dialog.dispose();
			//this.setVisible(false);////
			RecordInput(indicator);
			
			return;
		}	
	}
	
	
	//The function was used to input the score
	void RecordInput(int num){
		Formatter inputfile;
		try {
			inputfile = new Formatter(scoreString[num]);
			inputfile.format("%s %s",getUsername(),Integer.toString(score));
			inputfile.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Record r=new Record();
	    r.run(32);
	    r.run(18);
	*/	
		
	


}
