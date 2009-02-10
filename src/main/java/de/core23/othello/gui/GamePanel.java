package de.core23.othello.gui;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import javax.swing.JPanel;

import de.core23.othello.misc.Style;
import de.core23.othello.model.Player;
import de.core23.othello.model.PlayerType;

public class GamePanel extends JPanel implements Style {
	private BufferedImage _bufferImage;

	private Graphics _bufferGraphics;

	private FontMetrics _fontMetrics;

	private static final long serialVersionUID = 1L;

	private PlayerType[][] _map = new PlayerType[SIZE][SIZE];

	private LinkedList<Player> _playerList;

	private Player _currentPlayer;

	private LinkedList<Point> _possibleMoves;

	public GamePanel() {
		super();
		setSize((Style.BLOCK_SIZE + Style.BLOCK_PADDING * 2) * Style.SIZE + Style.PANEL_PADDING * 2, 
			
			(Style.BLOCK_SIZE + Style.BLOCK_PADDING * 2) * Style.SIZE	+ Style.PANEL_PADDING * 3);

		initBuffer();

		reset();
	}

	private void initBuffer() {
		_bufferImage = new BufferedImage((Style.BLOCK_SIZE + Style.BLOCK_PADDING * 2) * Style.SIZE + Style.PANEL_PADDING * 2,
			(Style.BLOCK_SIZE + Style.BLOCK_PADDING * 2) * Style.SIZE + Style.PANEL_PADDING * 4, BufferedImage.TYPE_INT_RGB);
		_bufferGraphics = _bufferImage.getGraphics();
		Graphics2D g = _bufferImage.createGraphics();

		g.setColor(COLOR_BG);
		g.fillRect(0, 0, getWidth(), getHeight());

		_bufferGraphics.setFont(new Font("Helvetica", Font.BOLD, 13)); //$NON-NLS-1$
		_fontMetrics = _bufferGraphics.getFontMetrics();
	}

	public void reset() {
		for (int x = 0; x < SIZE; x++) {
			for (int y = 0; y < SIZE; y++) {
				_map[x][y] = null;
			}
		}

		drawAll();
	}

	public void setPlayers(LinkedList<Player> playerList) {
		_playerList = playerList;

		drawPlayerNames();
	}

	public void updateScore() {
		drawPlayerNames();
	}

	public void setStone(int x, int y, PlayerType type) {
		_map[x][y] = type;

		drawStone(x, y);
	}

	public void setPossibleMoves(Player player, LinkedList<Point> possibleMoves) {
		clearPossibleMoves();

		_currentPlayer = player;
		_possibleMoves = possibleMoves;

		drawPossibleMoves();
	}

	private void drawGrid() {
		// Lines
		_bufferGraphics.setColor(COLOR_LINE);
		for (int x = 1; x < SIZE; x++)
			_bufferGraphics.drawLine(PANEL_PADDING + x * (BLOCK_SIZE + BLOCK_PADDING * 2), PANEL_PADDING, PANEL_PADDING + x * (BLOCK_SIZE + BLOCK_PADDING * 2),
				PANEL_PADDING + (BLOCK_SIZE + BLOCK_PADDING * 2) * SIZE);
		for (int y = 1; y < SIZE; y++)
			_bufferGraphics.drawLine(PANEL_PADDING + 0, PANEL_PADDING + y * (BLOCK_SIZE + BLOCK_PADDING * 2), PANEL_PADDING + (BLOCK_SIZE + BLOCK_PADDING * 2)
				* SIZE, PANEL_PADDING + y * (BLOCK_SIZE + BLOCK_PADDING * 2));
	}

	private void drawStone(int x, int y) {
		if (_map[x][y] != null) {
			_bufferGraphics.setColor(_map[x][y].color());
		} else {
			_bufferGraphics.setColor(COLOR_BG);
		}
		_bufferGraphics.fillOval(PANEL_PADDING + x * (BLOCK_SIZE + BLOCK_PADDING * 2) + BLOCK_PADDING, PANEL_PADDING + y * (BLOCK_SIZE + BLOCK_PADDING * 2)
			+ BLOCK_PADDING, BLOCK_SIZE, BLOCK_SIZE);
	}

	private void drawPlayerNames() {
		// Clear
		_bufferGraphics.setColor(Style.COLOR_BG);
		_bufferGraphics.fillRect(PANEL_PADDING, PANEL_PADDING + (BLOCK_SIZE + BLOCK_PADDING * 2) * SIZE, getWidth() - PANEL_PADDING * 2, _fontMetrics
			.getHeight());

		// Player Score
		FontRenderContext frc = _fontMetrics.getFontRenderContext();

		if (_playerList != null) {
			int posX = 0;
			int posY = PANEL_PADDING + (BLOCK_SIZE + BLOCK_PADDING * 2) * SIZE + (int) _fontMetrics.getHeight();
			int width = (int) (BLOCK_SIZE + BLOCK_PADDING * 2) * SIZE / _playerList.size();

			for (int i = 0; i < _playerList.size(); i++) {
				Player player = _playerList.get(i);
				_bufferGraphics.setColor(player.getColor());

				if (i == _playerList.size() - 1)
					posX = PANEL_PADDING + (BLOCK_SIZE + BLOCK_PADDING * 2) * SIZE - Style.PLAYER_TEXT_SIZE;
				else
					posX = PANEL_PADDING + width * i;

				String text = player.getName() + ":"; //$NON-NLS-1$
				Rectangle2D rectFont = _bufferGraphics.getFont().getStringBounds(text, frc);
				_bufferGraphics.drawString(text, posX, posY);

				text = player.getStones() + ""; //$NON-NLS-1$
				rectFont = _bufferGraphics.getFont().getStringBounds(text, frc);
				_bufferGraphics.drawString(text, posX + Style.PLAYER_TEXT_SIZE - (int) rectFont.getWidth(), posY);
			}
		}
	}

	private void clearPossibleMoves() {
		if (_currentPlayer != null) {
			_bufferGraphics.setColor(Style.COLOR_BG);
			for (Point point : _possibleMoves)
				_bufferGraphics.drawOval(PANEL_PADDING + (int) point.getX() * (BLOCK_SIZE + BLOCK_PADDING * 2) + BLOCK_PADDING, PANEL_PADDING
					+ (int) point.getY() * (BLOCK_SIZE + BLOCK_PADDING * 2) + BLOCK_PADDING, BLOCK_SIZE, BLOCK_SIZE);
		}

	}

	private void drawPossibleMoves() {
		if (_currentPlayer != null) {
			_bufferGraphics.setColor(_currentPlayer.getColor());
			for (Point point : _possibleMoves)
				_bufferGraphics.drawOval(PANEL_PADDING + (int) point.getX() * (BLOCK_SIZE + BLOCK_PADDING * 2) + BLOCK_PADDING, PANEL_PADDING
					+ (int) point.getY() * (BLOCK_SIZE + BLOCK_PADDING * 2) + BLOCK_PADDING, BLOCK_SIZE, BLOCK_SIZE);
		}
	}

	private void drawAll() {
		drawGrid();

		// Stones
		for (int x = 0; x < SIZE; x++) {
			for (int y = 0; y < SIZE; y++) {
				drawStone(x, y);
			}
		}

		drawPossibleMoves();
		drawPlayerNames();

		repaint();
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(_bufferImage, 0, 0, this);
	}

	public Point getPoint(Point p) {
		int x = (p.x - Style.PANEL_PADDING) / (BLOCK_SIZE + BLOCK_PADDING * 2);
		int y = (p.y - PANEL_PADDING) / (BLOCK_SIZE + BLOCK_PADDING * 2);
		return new Point(x, y);
	}
}
