package de.core23.othello.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import de.core23.othello.helper.LanguageManager;

public class GameFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel _jContentPane = null;

	private GamePanel _gamePanel = null;

	private JMenuBar _jMenuBar = null;

	private JMenu _jMenuFile = null;

	private JMenuItem _jMenuItemExit = null;

	private JMenuItem _jMenuItemNewGame = null;

	private JMenuItem _jMenuItemAbout = null;

	private JMenu _jMenuHelp = null;

	public GameFrame() {
		super();
		initialize();
	}

	private void initialize() {
		this.setJMenuBar(getJMenuBar());
		this.setContentPane(getJContentPane());
		this.setTitle("Othello"); //$NON-NLS-1$
		this.setResizable(false);
		getJContentPane().setPreferredSize(new Dimension(getGamePanel().getWidth(), getGamePanel().getHeight()));
		this.pack();
	}

	public JPanel getJContentPane() {
		if (_jContentPane == null) {
			_jContentPane = new JPanel();
			_jContentPane.setLayout(new BorderLayout());
			_jContentPane.add(getGamePanel(), null);
		}
		return _jContentPane;
	}

	public GamePanel getGamePanel() {
		if (_gamePanel == null) {
			_gamePanel = new GamePanel();
			_gamePanel.setLocation(0, 0);
		}
		return _gamePanel;
	}

	public JMenuBar getJMenuBar() {
		if (_jMenuBar == null) {
			_jMenuBar = new JMenuBar();
			_jMenuBar.add(getJMenuFile());
			_jMenuBar.add(getJMenuHelp());
		}
		return _jMenuBar;
	}

	public JMenu getJMenuFile() {
		if (_jMenuFile == null) {
			_jMenuFile = new JMenu();
			_jMenuFile.setText(LanguageManager.getString("menu.file")); //$NON-NLS-1$
			_jMenuFile.add(getJMenuItemNewGame());
			_jMenuFile.addSeparator();
			_jMenuFile.add(getJMenuItemExit());
		}
		return _jMenuFile;
	}

	public JMenuItem getJMenuItemNewGame() {
		if (_jMenuItemNewGame == null) {
			_jMenuItemNewGame = new JMenuItem();
			_jMenuItemNewGame.setText(LanguageManager.getString("menu.newgame")); //$NON-NLS-1$
		}
		return _jMenuItemNewGame;
	}

	public JMenuItem getJMenuItemExit() {
		if (_jMenuItemExit == null) {
			_jMenuItemExit = new JMenuItem();
			_jMenuItemExit.setText(LanguageManager.getString("menu.exit")); //$NON-NLS-1$
		}
		return _jMenuItemExit;
	}

	public JMenu getJMenuHelp() {
		if (_jMenuHelp == null) {
			_jMenuHelp = new JMenu();
			_jMenuHelp.setText(LanguageManager.getString("menu.help")); //$NON-NLS-1$
			_jMenuHelp.add(getJMenuItemAbout());
		}
		return _jMenuHelp;
	}

	public JMenuItem getJMenuItemAbout() {
		if (_jMenuItemAbout == null) {
			_jMenuItemAbout = new JMenuItem();
			_jMenuItemAbout.setText(LanguageManager.getString("menu.about")); //$NON-NLS-1$
		}
		return _jMenuItemAbout;
	}
}
