package de.core23.othello;

import java.io.IOException;

import javax.swing.SwingUtilities;

import de.core23.othello.controller.MainController;
import de.core23.othello.helper.LanguageManager;

public class OthelloAppDelegate {
	public static void main (String args[]) {
		// Load Language File
		try {
			LanguageManager.load("de"); //$NON-NLS-1$
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MainController().show();			
			}
		});
	}
}
