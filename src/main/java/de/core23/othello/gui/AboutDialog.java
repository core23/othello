package de.core23.othello.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Window;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import de.core23.othello.component.JHyperlink;
import de.core23.othello.helper.LanguageManager;

public class AboutDialog extends JDialog {
	private static final long serialVersionUID = 1L;

	private JPanel _jContentPane = null;

	private JHyperlink _jHyperlinkGripp = null;

	private JHyperlink _jHyperlinkPaypal = null;

	public AboutDialog(Window window) {
		super(window);
		initialize();
	}

	private void initialize() {
		this.setSize(new Dimension(240, 180));
		this.setContentPane(getJContentPane());
		this.setTitle(String.format(LanguageManager.getString("about.title"), "Othello")); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public JPanel getJContentPane() {
		if (_jContentPane == null) {
			_jContentPane = new JPanel();

			JLabel labelVersion = new JLabel("Version 1.01"); //$NON-NLS-1$
			labelVersion.setAlignmentX(Component.CENTER_ALIGNMENT);

			JLabel labelCopyright = new JLabel("Copyright 2010 Christian Gripp"); //$NON-NLS-1$
			labelCopyright.setAlignmentX(Component.CENTER_ALIGNMENT);

			_jContentPane.setLayout(new BoxLayout(_jContentPane, BoxLayout.Y_AXIS));
			_jContentPane.add(Box.createVerticalStrut(15));
			_jContentPane.add(labelVersion);
			_jContentPane.add(Box.createVerticalStrut(10));
			_jContentPane.add(labelCopyright);
			_jContentPane.add(Box.createVerticalStrut(10));
			_jContentPane.add(getJHyperlinkGripp());
			_jContentPane.add(Box.createVerticalStrut(20));
			_jContentPane.add(getJHyperlinkPaypal());
		}
		return _jContentPane;
	}

	public JHyperlink getJHyperlinkGripp() {
		if (_jHyperlinkGripp == null) {
			_jHyperlinkGripp = new JHyperlink("https://core23.de", "https://core23.de"); //$NON-NLS-1$ //$NON-NLS-2$
			_jHyperlinkGripp.setHorizontalTextPosition(SwingConstants.CENTER);
			_jHyperlinkGripp.setAlignmentX(Component.CENTER_ALIGNMENT);
		}
		return _jHyperlinkGripp;
	}

	public JHyperlink getJHyperlinkPaypal() {
		if (_jHyperlinkPaypal == null) {
			_jHyperlinkPaypal = new JHyperlink(new ImageIcon(AboutDialog.class.getClassLoader().getResource("paypal.gif")), //$NON-NLS-1$
				"https://donate.core23.de"); //$NON-NLS-1$
			_jHyperlinkPaypal.setHorizontalTextPosition(SwingConstants.CENTER);
			_jHyperlinkPaypal.setAlignmentX(Component.CENTER_ALIGNMENT);
		}
		return _jHyperlinkPaypal;
	}
}
