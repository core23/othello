package de.core23.othello.model;

import java.awt.Point;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

import de.core23.othello.helper.LanguageManager;

public enum AI {
	HUMAN, SIMPLE, MEDIUM, HARD;

	private Random rnd = new Random();

	public Point getMove(Map map, Player player) {
		switch (this) {
			case SIMPLE:
				return getRandomMove(map, player);
			case MEDIUM:
				return getBestMove(map, player);
			case HARD:
				return getBestScoreMove(map, player);
		}
		return new Point(-1, -1);
	}

	private Point getRandomMove(Map map, Player player) {
		LinkedList<Point> possibleMoves = map.getPossibleMoves(player);
		Collections.shuffle(possibleMoves);
		return (possibleMoves.size() == 1) ? possibleMoves.getFirst() : possibleMoves.get(rnd.nextInt(possibleMoves.size()));
	}

	private Point getBestMove(Map map, Player player) {
		LinkedList<Point> possibleMoves = map.getPossibleMoves(player);
		Collections.shuffle(possibleMoves);
		int bestIndex = 0;
		int bestCount = 0;

		for (int i = 0; i < possibleMoves.size(); i++) {
			int x = (int) possibleMoves.get(i).getX();
			int y = (int) possibleMoves.get(i).getY();
			int count = 0;

			for (int j = 0; j < 8; j++) {
				LinkedList<Point> stones = map.getStones(x, y, Map.MOVES[j][0], Map.MOVES[j][1], player);
				count += stones.size();
			}

			if (count > bestCount) {
				bestIndex = i;
				bestCount = count;
			}
		}
		return possibleMoves.get(bestIndex);
	}

	private Point getBestScoreMove(Map map, Player player) {
		LinkedList<Point> possibleMoves = map.getPossibleMoves(player);
		Collections.shuffle(possibleMoves);

		int bestIndex = 0;
		int bestScore = 0;

		for (int i = 0; i < possibleMoves.size(); i++) {
			int x = (int) possibleMoves.get(i).getX();
			int y = (int) possibleMoves.get(i).getY();
			int score = 0;

			for (int j = 0; j < 8; j++) {
				LinkedList<Point> stones = map.getStones(x, y, Map.MOVES[j][0], Map.MOVES[j][1], player);
				for (Point stone : stones)
					score += Map.MATRIX_POINTS[(int) stone.getX()][(int) stone.getY()];
			}

			if (score > bestScore) {
				bestIndex = i;
				bestScore = score;
			}
		}
		return possibleMoves.get(bestIndex);
	}

	@Override
	public String toString() {
		switch (this) {
			case HUMAN:
				return LanguageManager.getString("ai.human"); //$NON-NLS-1$
			case SIMPLE:
				return LanguageManager.getString("ai.cpu1"); //$NON-NLS-1$
			case MEDIUM:
				return LanguageManager.getString("ai.cpu2"); //$NON-NLS-1$
			case HARD:
				return LanguageManager.getString("ai.cpu3"); //$NON-NLS-1$
		}
		return LanguageManager.getString("ai.unknown"); //$NON-NLS-1$
	}
}
