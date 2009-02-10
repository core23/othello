package de.core23.othello.gui;

import java.awt.Rectangle;
import java.awt.Window;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.core23.othello.helper.LanguageManager;

public class SettingsDialog extends JDialog {
	private static final long serialVersionUID = 1L;

	private JPanel _jContentPane = null;

	private JLabel _jLabelPlayer = null;

	private JLabel _jLabelCPU = null;

	private JButton _jButtonNewGame = null;

	private JComboBox _jComboBoxPlayer1 = null;

	private JComboBox _jComboBoxPlayer2 = null;

	public SettingsDialog(Window window) {
		super(window);

		initialize();
	}

	private void initialize() {
		this.setSize(320, 180);
		this.setContentPane(getJContentPane());
		this.setTitle(LanguageManager.getString("newgame.title")); //$NON-NLS-1$
		this.setResizable(false);
	}

	public JPanel getJContentPane() {
		if (_jContentPane == null) {
			_jLabelCPU = new JLabel();
			_jLabelCPU.setBounds(new Rectangle(40, 60, 60, 25));
			_jLabelCPU.setText(LanguageManager.getString("newgame.player1")); //$NON-NLS-1$
			_jLabelPlayer = new JLabel();
			_jLabelPlayer.setBounds(new Rectangle(40, 20, 60, 25));
			_jLabelPlayer.setText(LanguageManager.getString("newgame.player2")); //$NON-NLS-1$
			_jContentPane = new JPanel();
			_jContentPane.setLayout(null);
			_jContentPane.add(_jLabelPlayer, null);
			_jContentPane.add(_jLabelCPU, null);
			_jContentPane.add(getJComboBoxPlayer1(), null);
			_jContentPane.add(getJComboBoxPlayer2(), null);
			_jContentPane.add(getJButtonNewGame(), null);
		}
		return _jContentPane;
	}

	public JComboBox getJComboBoxPlayer1() {
		if (_jComboBoxPlayer1 == null) {
			_jComboBoxPlayer1 = new JComboBox();
			_jComboBoxPlayer1.setBounds(new Rectangle(120, 20, 150, 25));
		}
		return _jComboBoxPlayer1;
	}

	public JComboBox getJComboBoxPlayer2() {
		if (_jComboBoxPlayer2 == null) {
			_jComboBoxPlayer2 = new JComboBox();
			_jComboBoxPlayer2.setBounds(new Rectangle(120, 60, 150, 25));
		}
		return _jComboBoxPlayer2;
	}

	public JButton getJButtonNewGame() {
		if (_jButtonNewGame == null) {
			_jButtonNewGame = new JButton();
			_jButtonNewGame.setBounds(new Rectangle(120, 100, 100, 25));
			_jButtonNewGame.setText(LanguageManager.getString("newgame.start")); //$NON-NLS-1$
		}
		return _jButtonNewGame;
	}
}
