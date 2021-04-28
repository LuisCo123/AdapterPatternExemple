package app;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import interfaces.IDocument;
import interfaces.IMenu;
import thread.ThreadA;

public class Main {
	private static final String menuName = "ui.Menu";
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException {
		menu();
	}
	
	public static void menu() throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException {
		String dir;
		ThreadA tA= new ThreadA();
		IMenu menu = (IMenu) Class.forName(menuName).newInstance();
		menu.setThread(tA);
		do {
			synchronized (tA) {
				try {
					System.out.println("Aguardando escolha do arquivo");
					tA.wait();
				}catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
			dir = menu.getAbsoluteDir();
		}while(dir==null);
		run(dir);
	}
	
	public static void run(String dir) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException {
		boolean sucess = false;
		String extension = dir.split("\\.")[1];
		File currentDir = new File ("./src/plugins");
		String[] pluginsF = currentDir.list();
		URL[] jars = new URL[pluginsF.length];
		for (int i = 0; i < pluginsF.length; i++)
			jars[i] = (new File ("./src/plugins" + pluginsF[i]).toURL());
		URLClassLoader urlc = new URLClassLoader(jars);
		
		for (int i = 0; i < pluginsF.length; i++) {
			if(pluginsF[i].toLowerCase().contains(extension.toLowerCase())) {
				String documentName = pluginsF[i].split("\\.")[0];
				IDocument documentOpener = (IDocument) Class.forName(documentName.toLowerCase() + "." + documentName,true, urlc).newInstance();
				openDocument(documentOpener,dir);
				sucess = true;
			}
		}
		
		if(!sucess) {
			System.out.println("Não existe plugin que suporte este arquivo");
		}
	}
	
	private static void openDocument(IDocument documentOpener, String dir) throws IOException {
		
		documentOpener.open(new File(dir));
		documentOpener.getEditor().setVisible(true);
		documentOpener.close();
	}
}


