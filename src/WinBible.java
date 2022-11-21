import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class WinBible extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JComboBox cbBook;
	private JComboBox cbChapter;
	private JComboBox cbVerse;
	JTextArea taContent;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			WinBible dialog = new WinBible();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public WinBible() {
		setTitle("Bible Project");
		setBounds(100, 100, 646, 723);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		cbBook = new JComboBox();
		cbBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
		            Class.forName("com.mysql.cj.jdbc.Driver");
		            Connection conn = DriverManager.getConnection
		                  ("jdbc:mysql://localhost:3306/bibledb", "root","12345");
		            
		            Statement stmt = conn.createStatement();
		            String sql = "SELECT MAX(chapter) FROM bibletbl";
		            sql = sql + " WHERE book='"+cbBook.getSelectedItem()+"'";
		            			//where 앞에 한칸 띄기!!!
		            
		            ResultSet rs = stmt.executeQuery(sql);
		            
		            if(rs.next()) {
		            	int cnt=rs.getInt(1);
		            	cbChapter.removeAllItems();
		            	for(int i=1; i<=cnt; i++)
		            		
		            		cbChapter.addItem(Integer.toString(i));
		            }
		         
		            } catch (Exception e1) {
		            e1.printStackTrace();
		            }
			}
		});
		cbBook.setBounds(29, 25, 205, 23);
		contentPanel.add(cbBook);
		
		cbChapter = new JComboBox();
		cbChapter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
		            Class.forName("com.mysql.cj.jdbc.Driver");
		            Connection conn = DriverManager.getConnection
		                  ("jdbc:mysql://localhost:3306/bibledb", "root","12345");
		            
		            Statement stmt = conn.createStatement();
		            String sql = "SELECT MAX(verse) FROM bibletbl";
		            sql = sql + " WHERE book='"+cbBook.getSelectedItem()+"' AND chapter='"+cbChapter.getSelectedItem()+"'";
		            			//where 앞에 한칸 띄기!!!
		            
		            ResultSet rs = stmt.executeQuery(sql);
		            
		            if(rs.next()) {
		            	int cnt=rs.getInt(1);
		            	cbVerse.removeAllItems();
		            	
		            	for(int i=1; i<=cnt; i++) 
		            		
		            		cbVerse.addItem(Integer.toString(i));
		            	
		            }
		            } catch (Exception e1) {
		            e1.printStackTrace();
		            }
			}
		});
		cbChapter.setBounds(257, 25, 91, 23);
		contentPanel.add(cbChapter);
		
		cbVerse = new JComboBox();
		cbVerse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					//bibleTBL로 부터 해당하는 책,장,절을 가져와 출력한다.
				try {
		            Class.forName("com.mysql.cj.jdbc.Driver");
		            Connection conn = DriverManager.getConnection
		                  ("jdbc:mysql://localhost:3306/bibledb", "root","12345");
		            
		            Statement stmt = conn.createStatement();
		            String sql = "SELECT content FROM bibletbl";
		            sql = sql + " WHERE book='"+cbBook.getSelectedItem()+"' AND chapter='"+cbChapter.getSelectedItem()+"'";
		            			//where 앞에 한칸 띄기!!!
		            sql = sql + " AND verse='"+cbVerse.getSelectedItem()+"'";
		            ResultSet rs = stmt.executeQuery(sql);
		            
		            if(rs.next()) {
		            	taContent.setText(rs.getString("content"));
		            }
		            } catch (Exception e1) {
		            e1.printStackTrace();
		            }
			}
		});
		
	
		cbVerse.setBounds(371, 25, 57, 23);
		contentPanel.add(cbVerse);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(29, 70, 550, 423);
		contentPanel.add(scrollPane);
		
		taContent = new JTextArea();
		taContent.setLineWrap(true);
		scrollPane.setViewportView(taContent);
		
		ShowBibleBook();
	}

	private void ShowBibleBook() {
		// DB 연동
		
		try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection
                  ("jdbc:mysql://localhost:3306/bibledb", "root","12345");
            
            Statement stmt = conn.createStatement();
            String sql = "SELECT DISTINCT book FROM bibletbl";
            
            ResultSet rs = stmt.executeQuery(sql);
            
            while(rs.next()) {
            	cbBook.addItem(rs.getString(1));
            }
            } catch (Exception e1) {
            e1.printStackTrace();
            }
	}
}
