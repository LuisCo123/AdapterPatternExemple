package pdfdocument;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import interfaces.IDocument;

public class PdfDocument implements IDocument{
	private PDDocument document;
	private int currentPage = 0;

	@Override
	public void open(File file) throws IOException {
		// TODO Auto-generated method stub
		this.document = PDDocument.load(file);
	}
	
	@Override
	public JFrame getEditor() throws IOException {
		// TODO Auto-generated method stub
		JFrame page = new JFrame("PDF Reader");
		JPanel principalPanel = new JPanel(new BorderLayout());
		JPanel[] contentPanel = new JPanel[document.getNumberOfPages()];
		JPanel navegatePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 100,10));
		JButton bPreview = new JButton("Preview");
		JButton bNext = new JButton("Next");
		JButton bExit = new JButton("Exit");
		
		PDFRenderer render = new PDFRenderer(this.document);
	
		bExit.setBackground(new Color(255,102,102));
		bExit.setForeground(Color.white);
		navegatePanel.setBackground(new Color(128,128,128));
		page.setBackground(new Color(128,128,128));
		for(int i = 0; i< document.getNumberOfPages(); i++) {
			BufferedImage bim = render.renderImage(i);
			contentPanel[i] = new JPanel();
			contentPanel[i].add(new JLabel(new ImageIcon(bim)),BorderLayout.CENTER);
		}
		navegatePanel.add(bPreview);
		navegatePanel.add(bExit);
		navegatePanel.add(bNext);
		bPreview.setVisible(false);
		if(this.document.getNumberOfPages() == 1)
			bNext.setVisible(false);
		bPreview.addActionListener(new ActionListener(){
	
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(currentPage>0) {
					principalPanel.remove(contentPanel[currentPage]);
					currentPage--;
					principalPanel.add(contentPanel[currentPage], BorderLayout.CENTER);
					principalPanel.revalidate();
					principalPanel.repaint();
					bPreview.setVisible(true);
					if(currentPage==0)
						bPreview.setVisible(false);
				}	
				if(!bNext.isVisible())
					bNext.setVisible(true);
			}
		});
		bNext.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(currentPage<document.getNumberOfPages()) {
					principalPanel.remove(contentPanel[currentPage]);
					currentPage++;
					principalPanel.add(contentPanel[currentPage], BorderLayout.CENTER);
					principalPanel.revalidate();
					principalPanel.repaint();
					bNext.setVisible(true);
					if(currentPage==document.getNumberOfPages() -1 )
						bNext.setVisible(false);
				}
					
				if(!bPreview.isVisible())
					bPreview.setVisible(true);
				
			}
		});
		
		bExit.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				page.dispose();
			}
		});
		
		principalPanel.add(navegatePanel,BorderLayout.PAGE_END);
		principalPanel.add(contentPanel[this.currentPage], BorderLayout.CENTER);
		page.add(principalPanel);
		page.setDefaultCloseOperation(page.EXIT_ON_CLOSE);
		page.pack();
		page.setLocationRelativeTo(null);
		return page;
	}
	public void close() throws IOException {
		this.document.close();
	}

}
