package de.core23.othello.controller;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import de.core23.othello.gui.AboutDialog;
import de.core23.othello.gui.GameFrame;
import de.core23.othello.gui.SettingsDialog;
import de.core23.othello.helper.LanguageManager;
import de.core23.othello.misc.Style;
import de.core23.othello.model.AI;
import de.core23.othello.model.Map;
import de.core23.othello.model.Player;
import de.core23.othello.model.PlayerType;

public class MainController {
	private GameFrame _frame;

	private Map _map;

	private boolean _start;

	private Timer _timer;

	private AI _playerAI1;

	private AI _playerAI2;

	private Thread _cpuActionThread = null;

	public MainController() {
		_start = false;

		_timer = new Timer(100, new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (_map.getActivePlayer().isCPU())
					putCPUStone();
			}
		});
	}

	public void startNewGame(AI ai1, AI ai2) {
		_start = false;
		_timer.stop();

		_playerAI1 = ai1;
		_playerAI2 = ai2;

		// Player
		LinkedList<Player> playerList = new LinkedList<Player>();
		playerList.add(new Player(PlayerType.BLACK, ai1));
		playerList.add(new Player(PlayerType.WHITE, ai2));

		// Create Map
		_map = new Map(playerList);

		// Pass To GUI
		_frame.getGamePanel().reset();
		_frame.getGamePanel().setPlayers(playerList);

		for (int x = 0; x < Style.SIZE; x++) {
			for (int y = 0; y < Style.SIZE; y++) {
				_frame.getGamePanel().setStone(x, y, _map.getMap(x, y) == null ? null : _map.getMap(x, y).getType());
			}
		}

		// Show Moves
		Player player = _map.getActivePlayer();
		_frame.getGamePanel().setPossibleMoves(player, _map.getPossibleMoves(player));

		// Start
		_start = true;
		_timer.start();

		_frame.getGamePanel().repaint();
	}

	@SuppressWarnings("deprecation")
	public void exit() {
		if (_cpuActionThread != null)
			_cpuActionThread.stop();
		System.exit(0);
	}

	private void putCPUStone() {
		if (!_start)
			return;
		if (_map.isGameOver())
			return;

		_start = false;

		_cpuActionThread = new Thread() {
			@Override
			public void run() {
				try {
					sleep(800);
				} catch (InterruptedException e) {
					return;
				}

				// Show Moves
				Player player = _map.getActivePlayer();

				Point point = player.getKI().getMove(_map, player);
				putStone(point.x, point.y);

				_frame.getGamePanel().repaint();
			}
		};
		_cpuActionThread.start();
	}

	public void putUserStone(int x, int y) {
		if (_map.getActivePlayer().isCPU())
			return;

		putStone(x, y);
	}

	private void putStone(int x, int y) {
		LinkedList<Point> stones = _map.putStone(x, y);
		if (stones.size() == 0)
			return;

		PlayerType type = _map.getActivePlayer().getType();
		for (Point p : stones)
			_frame.getGamePanel().setStone(p.x, p.y, type);
		_frame.getGamePanel().updateScore();

		// Next Player
		_map.nextPlayer();

		// Show Moves
		Player player = _map.getActivePlayer();
		_frame.getGamePanel().setPossibleMoves(player, _map.getPossibleMoves(player));

		_frame.getGamePanel().repaint();

		_start = true;

		if (_map.isGameOver())
			endGame();
	}

	private void endGame() {
		if (!_start)
			return;

		_start = false;

		boolean patt = false;
		Player winner = _map.getPlayerList().get(0);
		for (int i = 1; i < _map.getPlayerList().size(); i++) {
			if (_map.getPlayerList().get(i).getStones() > winner.getStones())
				winner = _map.getPlayerList().get(i);
			if (_map.getPlayerList().get(i).getStones() == _map.getPlayerList().get(i - 1).getStones())
				patt = true;
		}

		if (patt)
			JOptionPane.showMessageDialog(_frame, LanguageManager.getString("end.patt"), "Othello", JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
		else
			JOptionPane.showMessageDialog(_frame, String.format(LanguageManager.getString("end.win"), winner.getName()), "Othello", //$NON-NLS-1$ //$NON-NLS-2$
				JOptionPane.INFORMATION_MESSAGE);
	}

	public void show() {
		_frame = new GameFrame();

		_frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				exit();
			}
		});
		_frame.getJMenuItemNewGame().addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				showNewGame();
			}
		});
		_frame.getJMenuItemExit().addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				System.exit(0);
			}
		});
		_frame.getJMenuItemAbout().addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				showAbout();
			}
		});

		_frame.getGamePanel().addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseReleased(java.awt.event.MouseEvent e) {
				Point p = _frame.getGamePanel().getPoint(e.getPoint());
				putUserStone(p.x, p.y);
			}
		});

		startNewGame(AI.HUMAN, AI.SIMPLE);

		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		_frame.setLocationRelativeTo(null);
		_frame.setVisible(true);
	}

	public void showNewGame() {
		final SettingsDialog dialog = new SettingsDialog(_frame);
		dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		DefaultComboBoxModel aiPlayerModel1 = new DefaultComboBoxModel();
		for (AI ki : AI.values())
			aiPlayerModel1.addElement(ki);
		dialog.getJComboBoxPlayer1().setModel(aiPlayerModel1);
		dialog.getJComboBoxPlayer1().setSelectedItem(getPlayerAI1());

		DefaultComboBoxModel aiPlayerModel2 = new DefaultComboBoxModel();
		for (AI ki : AI.values())
			aiPlayerModel2.addElement(ki);
		dialog.getJComboBoxPlayer2().setModel(aiPlayerModel2);
		dialog.getJComboBoxPlayer2().setSelectedItem(getPlayerAI2());

		dialog.getJButtonNewGame().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AI player1 = (AI) dialog.getJComboBoxPlayer1().getSelectedItem();
				AI player2 = (AI) dialog.getJComboBoxPlayer2().getSelectedItem();

				if (player1 == null || player2 == null)
					return;

				startNewGame(player1, player2);
				dialog.dispose();
			}
		});
		dialog.setLocationRelativeTo(_frame);
		dialog.setModal(true);
		dialog.setVisible(true);
	}

	public void showAbout() {
		AboutDialog dialog = new AboutDialog(_frame);
		dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		dialog.setModal(true);
		dialog.setLocationRelativeTo(_frame);
		dialog.setVisible(true);
	}

	public AI getPlayerAI1() {
		return _playerAI1;
	}

	public AI getPlayerAI2() {
		return _playerAI2;
	}
}
