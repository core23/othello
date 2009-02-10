package de.core23.othello.model;

import java.awt.Color;

import de.core23.othello.helper.LanguageManager;

public enum PlayerType {
	WHITE, BLACK;

	public Color color() {
		switch (this) {
			case BLACK:
				return new Color(0x000000);
			case WHITE:
				return new Color(0xFFFFFF);
		}
		return null;
	}
	
	@Override
	public String toString() {
		switch (this) {
			case BLACK:
				return LanguageManager.getString("player.black"); //$NON-NLS-1$
			case WHITE:
				return LanguageManager.getString("player.white"); //$NON-NLS-1$
		}
		return LanguageManager.getString("player.unknown"); //$NON-NLS-1$
	}
}
