package Main;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class WorldBuilderGui extends JFrame {
	
	public static int mapRows;
	public static int mapCols;
	public static String mapName;
	
	
	JLabel nameField;
	JLabel rowField;
	JLabel colField;
	JTextField name;
	JTextField rows;
	JTextField cols;
	JButton finish;
	
	public WorldBuilderGui() {
		setLayout(null);
		setName("World Builder");
		
		nameField = new JLabel("Map name");
		nameField.setBounds(10, 10, nameField.getPreferredSize().width, nameField.getPreferredSize().height);
		add(nameField);
		
		name = new JTextField(5);
		name.setBounds(10 + nameField.getWidth() + 10, 10, name.getPreferredSize().width, name.getPreferredSize().height);
		add(name);
		
		rowField = new JLabel("Row count");
		rowField.setBounds(10, 40, width(rowField), height(rowField));
		add(rowField);
		
		colField = new JLabel("Columns count");
		colField.setBounds(10, 70, width(colField), height(rowField));
		add(colField);
		
		
		rows = new JTextField(5);
		rows.setBounds(10 + rowField.getWidth() + 10, 40, rows.getPreferredSize().width, rows.getPreferredSize().height);
		add(rows);
		
		cols = new JTextField(5);
		cols.setBounds(10 + colField.getWidth() + 10, 70, cols.getPreferredSize().width, cols.getPreferredSize().height);
		add(cols);
		
		
		finish = new JButton("Finish");
		finish.setBounds(65, 120, finish.getPreferredSize().width, finish.getPreferredSize().height);
		add(finish);
		
		
		
	}
	
	public int width(JLabel l){
		return l.getPreferredSize().width;
	}
	
	public int height(JLabel l){
		return l.getPreferredSize().height;
	}
	
	
	public static void main(String[] args){
		WorldBuilderGui gui = new WorldBuilderGui();
		gui.setSize(200,200);
		gui.setResizable(false);
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setVisible(true);
	}
	
	
	

}
