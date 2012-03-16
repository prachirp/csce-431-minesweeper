import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class SMinesweeper extends JFrame {
	
	private JLabel statusBar;
	
	public SMinesweeper()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setLocationRelativeTo(null);
		setTitle("Square Minesweeper");
		
		statusBar = new JLabel("");
		add( statusBar, BorderLayout.SOUTH );
		
		add(new BeginnerGrid(statusBar));
		
		setResizable(false);
		setVisible(true);
		
	}
	
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		//System.out.println("Creating Grid...");
		//BeginnerGrid gameGrid = new BeginnerGrid();
		//gameGrid.testGrid();
		new SMinesweeper();
	}

}
