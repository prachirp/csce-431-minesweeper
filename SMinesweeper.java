import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JPopupMenu;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.ButtonGroup;
import javax.swing.JMenuBar;
import javax.swing.KeyStroke;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JFrame;

public class SMinesweeper extends JFrame {
        
        private JLabel statusBar;
        
        public JMenuBar createMenuBar() {
            JMenuBar optionsMenuBar;
            JMenu menu, submenu;
            JMenuItem menuItem;
            JRadioButtonMenuItem rbMenuItem;
            JTextField mineTextField, xTextField, yTextField;

            //Create the menu bar.
            optionsMenuBar = new JMenuBar();

            //Build the first menu.
            menu = new JMenu("Options");
            menu.getAccessibleContext().setAccessibleDescription(
                    "this is menu");
            optionsMenuBar.add(menu);

            //a group of JMenuItems
            menuItem = new JMenuItem("Load");
            //menuItem.addActionListener(this); //this is where we react
            menu.add(menuItem);

            //a group of JMenuItems
            menuItem = new JMenuItem("Save");
           //menuItem.addActionListener(this);
            menu.add(menuItem);

            //a group of radio button menu items
            menu.addSeparator();
            ButtonGroup group = new ButtonGroup();

            rbMenuItem = new JRadioButtonMenuItem("beginner");
            rbMenuItem.setSelected(true);
            group.add(rbMenuItem);
            //rbMenuItem.addActionListener(this);
            menu.add(rbMenuItem);

            rbMenuItem = new JRadioButtonMenuItem("advanced");
            group.add(rbMenuItem);
            //rbMenuItem.addActionListener(this);
            menu.add(rbMenuItem);

            rbMenuItem = new JRadioButtonMenuItem("expert");
            group.add(rbMenuItem);
            //rbMenuItem.addActionListener(this);
            menu.add(rbMenuItem);

            rbMenuItem = new JRadioButtonMenuItem("custom");
            group.add(rbMenuItem);
            //rbMenuItem.addActionListener(this);
            menu.add(rbMenuItem);

            //a submenu
            menu.addSeparator();
            submenu = new JMenu("Custom level settings");

            menuItem = new JMenuItem("Number of mines:");
            //menuItem.addActionListener(this);
            submenu.add(menuItem);

            menuItem = new JMenuItem("X size:");
            //menuItem.addActionListener(this);
            submenu.add(menuItem);

            menuItem = new JMenuItem("Y size:");
            //menuItem.addActionListener(this);
            submenu.add(menuItem);
            menu.add(submenu);
    		
            //Build second menu in the menu bar.
            menu = new JMenu("Help");
            optionsMenuBar.add(menu);
    		
    		menuItem = new JMenuItem("About");
    		//menuItem.addActionListener(this);
    		menu.add(menuItem);

            return optionsMenuBar;
        }
        
        
        
        
        public SMinesweeper()
        {
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setSize(800, 600);
                setLocationRelativeTo(null);
                setTitle("Square Minesweeper");
                
                this.setJMenuBar(this.createMenuBar());
                
                statusBar = new JLabel("");				
				
                add( statusBar, BorderLayout.SOUTH );
                
                //add(new BeginnerGrid(statusBar));
                
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
