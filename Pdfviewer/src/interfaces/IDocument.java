package interfaces;

import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;

public interface IDocument {
	public void open(File file) throws IOException;
	public JFrame getEditor()throws IOException;
	public void close() throws IOException;
}
