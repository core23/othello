package de.core23.othello.model;

import java.awt.Point;
import java.util.LinkedList;

import de.core23.othello.misc.Style;

public class Map implements Style {
	public static final int[][] MOVES = { {0, -1}, {1, -1}, {1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}};

	public static final int[][] MATRIX_POINTS = { {50, -1, 5, 2, 2, 5, -1, 50}, {-1, -10, 1, 1, 1, 1, -10, -1}, {5, 1, 1, 1, 1, 1, 1, 5},
		{2, 1, 1, 0, 0, 1, 1, 2}, {2, 1, 1, 0, 0, 1, 1, 2}, {5, 1, 1, 1, 1, 1, 1, 5}, {-1, -10, 1, 1, 1, 1, -10, -1}, {50, -1, 5, 2, 2, 5, -1, 50}};

	private Player[][] _map = new Player[SIZE][SIZE];

	private Player _activePlayer = null;

	private LinkedList<Player> _playerList = new LinkedList<Player>();

	private LinkedList<Point> _activeMoves = new LinkedList<Point>();

	public Map(LinkedList<Player> playerList) {
		// Clear Map
		for (int x = 0; x < SIZE; x++) {
			for (int y = 0; y < SIZE; y++) {
				_map[x][y] = null;
			}
		}

		// Player
		_playerList = playerList;

		// Put Stones
		int mid = Style.SIZE / 2;
		_map[mid][mid] = playerList.get(0);
		_map[mid - 1][mid - 1] = playerList.get(0);
		_map[mid - 1][mid] = playerList.get(1);
		_map[mid][mid - 1] = playerList.get(1);

		// Moves
		_activePlayer = playerList.get(0);
		_activeMoves.clear();
		_activeMoves.addAll(getPossibleMoves(_activePlayer));
	}

	public Player getActivePlayer() {
		return _activePlayer;
	}

	public LinkedList<Player> getPlayerList() {
		return _playerList;
	}

	public LinkedList<Point> getActiveMoves() {
		return _activeMoves;
	}

	public Player[][] getMap() {
		return _map;
	}

	public Player getMap(int x, int y) {
		return _map[x][y];
	}

	public boolean nextPlayer() {
		if (isGameOver())
			return false;

		for (int i = 0; i < _playerList.size(); i++) {
			int next = _playerList.indexOf(_activePlayer) + 1;
			if (next >= _playerList.size())
				next = 0;
			_activePlayer = _playerList.get(next);
			_activeMoves = getPossibleMoves(_activePlayer);
			if (_activeMoves.size() > 0)
				return true;
		}

		return true;
	}

	public boolean isGameOver() {
		for (Player player : _playerList) {
			if (getPossibleMoves(player).size() > 0)
				return false;
		}
		return true;
	}

	public LinkedList<Point> putStone(int x, int y) {
		return putStone(x, y, _activePlayer);
	}

	private LinkedList<Point> putStone(int x, int y, Player player) {
		LinkedList<Point> stones = new LinkedList<Point>();
		
		if (!isMove(x, y))
			return stones;

		for (int i = 0; i < 8; i++) {
			for (Point point : getStones(x, y, MOVES[i][0], MOVES[i][1], player)) {
				player.incStones();
				if (_map[(int) point.getX()][(int) point.getY()] != null)
					_map[(int) point.getX()][(int) point.getY()].decStones();
				_map[(int) point.getX()][(int) point.getY()] = player;
				stones.add(point);
			}
//			stones.add(new Point(x, y));
		}

		return stones;
	}

	public LinkedList<Point> getPossibleMoves(Player player) {
		LinkedList<Point> possibleMoves = new LinkedList<Point>();

		for (int x = 0; x < SIZE; x++) {
			for (int y = 0; y < SIZE; y++) {
				if (_map[x][y] != player)
					continue;

				for (int i = 0; i < 8; i++) {
					Point p = getNewLine(x, y, MOVES[i][0], MOVES[i][1]);
					if (p != null)
						possibleMoves.add(p);
				}
			}
		}
		return possibleMoves;
	}

	private Point getNewLine(int x, int y, int moveX, int moveY) {
		Player player = _map[x][y];

		for (int i = 1; i < SIZE; i++) {
			// Out of Bounds
			if (x + moveX * i < 0 || y + moveY * i < 0 || x + moveX * i >= SIZE || y + moveY * i >= SIZE)
				return null;

			// Empty Field
			if (_map[x + moveX * i][y + moveY * i] == null) {
				if (i == 1)
					return null;
				else
					return new Point(x + moveX * i, y + moveY * i);

				// Existing Field
			} else if (_map[x + moveX * i][y + moveY * i] == player) {
				return null;
			}
		}
		return null;
	}

	public LinkedList<Point> getStones(int x, int y, int moveX, int moveY, Player player) {
		LinkedList<Point> stones = new LinkedList<Point>();

		for (int i = 0; i < SIZE; i++) {
			// Out of Bounds
			if (x + moveX * i < 0 || y + moveY * i < 0 || x + moveX * i >= SIZE || y + moveY * i >= SIZE)
				return new LinkedList<Point>();

			// Hole
			if (_map[x + moveX * i][y + moveY * i] == null && i > 0)
				return new LinkedList<Point>();

			// End
			if (_map[x + moveX * i][y + moveY * i] == player && i > 0)
				break;

			stones.add(new Point(x + moveX * i, y + moveY * i));
		}
		return stones;
	}

	private boolean isMove(int x, int y) {
		for (Point point : _activeMoves) {
			if (point.getX() == x && point.getY() == y)
				return true;
		}
		return false;
	}
}
