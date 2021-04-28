package ui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import interfaces.IMenu;
import thread.ThreadA;

public class Menu extends JFrame implements ActionListener, IMenu{
	private JButton bopen;
	private String dir;
	private ThreadA t;
	private JPanel panel;
	public Menu () {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.bopen = new JButton("Open");
		this.bopen.addActionListener(this);
		this.bopen.setPreferredSize(new Dimension(100,30));
		this.panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		this.panel.add(this.bopen);
		this.setTitle("Document Opener");
		this.add(this.panel);
		
		this.setPreferredSize(new Dimension(400,100));
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
	public void setThread(ThreadA t) {
		this.t=t;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == this.bopen) {
			JFileChooser fileChooser = new JFileChooser();
			int response = fileChooser.showOpenDialog(null);
			if(response  == JFileChooser.APPROVE_OPTION) { 
				this.dir= fileChooser.getSelectedFile().getAbsolutePath();
				dispose();
			}
			this.t.release();
		}	
	}
	public String getAbsoluteDir() {
		return this.dir;
	}
}
