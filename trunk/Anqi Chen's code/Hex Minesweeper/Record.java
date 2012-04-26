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


public class Record extends JFrame implements ActionListener{

	int score=0;
	String user;
	JLabel Message;
	JTextField Username;
	JButton Conf;
	Container contentpane;
	File file;

	
	public Record(){
		super("Score");
		contentpane=this.getContentPane();
		contentpane.setLayout(new BorderLayout());
		
		Message=new JLabel("0",JLabel.CENTER);
		Message.setFont(new Font("Arial",Font.PLAIN,16));
		contentpane.add(Message,BorderLayout.NORTH);
		
		Username=new JTextField();
		Username.setFont(new Font("Arial",Font.PLAIN,16));
		contentpane.add(Username,BorderLayout.CENTER);
		
		Conf=new JButton("Confirm");
		contentpane.add(Conf,BorderLayout.SOUTH);
		
		Username.addActionListener(this);
		Conf.addActionListener(this);
		
		//file = new File("F:\\Programming\\Java\\ANQI CHEN 117\\score.txt");
		file = new File("score.txt");
		
		
	}
	
	public void run(int pscore) {
		
		
		score = pscore;
	
		Formatter newfile;
		if(!file.exists()){	
			try {
				newfile = new Formatter("score.txt");
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
			x = new Scanner(file);
			String pastscore = null;
			while(x.hasNext()){
				pastscore = x.next();
			}
			x.close();
			if(Integer.parseInt(pastscore)<=score){
				
				return;
			}
			else{
				Message.setText("You Get the highest score! Please input your name:");
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
		this.pack();
		this.setLocation(new Point(200, 400));
		this.setVisible(true);
	}
	
	
	//Get username
	String getUsername(){
		return user;
	}
	
	//Action performed when the user input their name
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==Username){
			user=Username.getText();
			while(user.equals("")){
				Message.setText("User Name should not be blank, please input:");
				this.setVisible(true);
				return;
			}
			this.setVisible(false);
			this.RecordInput();
			
			return;
		}
		
		if(e.getSource()==Conf){
			user=Username.getText();
			while(user.equals("")){
				Message.setText("User Name should not be blank, please input:");
				this.setVisible(true);
				return;
			}
			this.setVisible(false);
			RecordInput();
			
			return;
		}	
	}
	
	
	//The function was used to input the score
	void RecordInput(){
		Formatter inputfile;
		try {
			inputfile = new Formatter("score.txt");
			inputfile.format("%s %s",getUsername(),Integer.toString(score));
			inputfile.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Record r=new Record();
	    r.run(32);
	    r.run(18);
		
	}


}
