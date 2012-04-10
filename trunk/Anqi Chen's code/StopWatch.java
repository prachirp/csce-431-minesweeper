import java.awt.*;
import java.awt.event.*;
import javax.swing.*;



public class StopWatch implements ActionListener{
	/**
	 * 
	 */
	JLabel timeArea;
	JFrame gameWindow;
	//JButton startBtn,pauseBtn,resetBtn;
	int sec=0;
	Timer timer;
	
	public StopWatch(JFrame mainWindow){
		
		//super("Time Spent");
		gameWindow = mainWindow;
		Container contentPane=gameWindow.getContentPane();
		contentPane.setLayout(new BorderLayout()); //Initial BorderLayout
		
		//The time presenting area are in the North
		timeArea=new JLabel("0",JLabel.CENTER);
		timeArea.setForeground(Color.white);
		timeArea.setOpaque(true);			//opaque
		timeArea.setBackground(Color.black);
		timeArea.setFont(new Font("Arial",Font.ITALIC,24));
		timeArea.setText("0");
		contentPane.add(timeArea,BorderLayout.SOUTH);
		
		//The start and pause area are in the middle
		//JPanel pCenter=new JPanel(new GridLayout(1,2));
		//startBtn=new JButton("Start");
		//pCenter.add(startBtn);
		//startBtn.addActionListener(this);
		//pauseBtn=new JButton("Pause");
		//pCenter.add(pauseBtn);
		//pauseBtn.addActionListener(this);
		//contentPane.add(pCenter,BorderLayout.CENTER);
		
		//Southern part reset
		//resetBtn = new JButton("Reset");
		//resetBtn.addActionListener(this);
		//contentPane.add(resetBtn,BorderLayout.SOUTH);
		
		timer=new Timer(1000,this);//Listen to the action listener
		
		gameWindow.pack();
		gameWindow.setVisible(true);
	}

	void start(){
		timer.start();
	}
	
	void pause(){
		timer.stop();
	}
	
	
	void reset(){
		sec=0;
		timer.stop();
		timeArea.setText("0");
	}
	
	int getTime(){
		return sec;
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		String timeStr;
			if(e.getSource()==timer){
			sec++;
			if (sec==1000)
				sec--;
			timeStr=Integer.toString(sec);
			timeArea.setText(timeStr);
		}
	}
		
		//if(e.getSource()==startBtn){
			//timer.start();
		//}
		
		//if(e.getSource()==pauseBtn){
			//timer.stop();
		//}
		
		//if(e.getSource()==resetBtn){
			//sec=0;
			//timer.stop();
			//timeArea.setText("0");
		//}
		
	//}
	
	
}
