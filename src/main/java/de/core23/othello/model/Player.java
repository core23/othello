package de.core23.othello.model;

import java.awt.Color;

import de.core23.othello.misc.Style;

public class Player implements Style {
	private PlayerType _type;

	private AI _ai;

	private int _stones = 2;

	public Player(PlayerType type, AI ai) {
		_type = type;
		if (ai == null)
			ai = AI.HUMAN;
		_ai = ai;
	}

	public Color getColor() {
		return _type.color();
	}

	public int getStones() {
		return _stones;
	}

	public void decStones() {
		_stones--;
	}

	public void incStones() {
		_stones++;
	}

	public void resetStones() {
		_stones = 0;
	}

	public PlayerType getType() {
		return _type;
	}

	public void setStones(int stones) {
		this._stones = stones;
	}

	public boolean isCPU() {
		return _ai != AI.HUMAN;
	}

	public AI getKI() {
		return _ai;
	}

	public String getName() {
		return _type.toString();
	}
}
