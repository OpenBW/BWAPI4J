package org.openbw.bwapi4j;

import org.openbw.bwapi4j.type.Color;

/**
 * Contains all map-drawing-related bwapi functionality.
 */
public final class MapDrawer {

	private boolean drawingEnabled;
	
	/* default */ MapDrawer() {
		this.drawingEnabled = true;
	}
	
	/**
	 * Globally enable or disable drawing on the map.
	 * @param enabled
	 */
	public void setEnabled(boolean enabled) {
		this.drawingEnabled = enabled;
	}
	
	public boolean isEnabled() {
		return drawingEnabled;
	}
	
	private native void drawCircleMap_native(int x, int y, int radius, int color);
	private native void drawCircleMap_native(int x, int y, int radius, int color, boolean isSolid);
	private native void drawTextScreen_native(int x, int y, String cstr_format);
	private native void drawBoxMap_native(int left, int top, int right, int bottom, int color);
	private native void drawBoxMap_native(int left, int top, int right, int bottom, int color, boolean isSolid);
	private native void drawBoxScreen_native(int left, int top, int right, int bottom, int color, boolean isSolid);
	private native void drawLineMap_native(int x1, int y1, int x2, int y2, int color);
	private native void drawTextMap_native(int x, int y, String cstr_format);

	public void drawTextScreen(int x, int y, String text) {
		if (!drawingEnabled) return;
		drawTextScreen_native(x, y, text);
	}

	public void drawCircleMap(int x, int y, int radius, Color color) {
		if (!drawingEnabled) return;
		drawCircleMap_native(x, y, radius, color.getValue());
	}
	
	public void drawCircleMap(int x, int y, int radius, Color color, boolean isSolid) {
		if (!drawingEnabled) return;
		drawCircleMap_native(x, y, radius, color.getValue(), isSolid);
	}

	public void drawBoxMap(int left, int top, int right, int bottom, Color color) {
		if (!drawingEnabled) return;
		drawBoxMap_native(left, top, right, bottom, color.getValue());
	}
	
	public void drawBoxMap(int left, int top, int right, int bottom, Color color, boolean isSolid) {
		if (!drawingEnabled) return;
		drawBoxMap_native(left, top, right, bottom, color.getValue(), isSolid);
	}
	
	public void drawBoxScreen(int left, int top, int right, int bottom, Color color, boolean isSolid) {
		if (!drawingEnabled) return;
		drawBoxScreen_native(left, top, right, bottom, color.getValue(), isSolid);
	}

	public void drawLineMap(int x1, int y1, int x2, int y2, Color color) {
		if (!drawingEnabled) return;
		drawLineMap_native(x1, y1, x2, y2, color.getValue());
	}

	public void drawTextMap(int x, int y, String text) {
		if (!drawingEnabled) return;
		drawTextMap_native(x, y, text);
	}

	public void drawTextMap(Position p, String text) {
		if (!drawingEnabled) return;
		drawTextMap_native(p.getX(), p.getY(), text);
	}

	public void drawLineMap(Position a, Position b, Color color) {
		if (!drawingEnabled) return;
		drawLineMap_native(a.getX(), a.getY(), b.getX(), b.getY(), color.getValue());
	}

	public void drawCircleMap(Position p, int radius, Color color) {
		if (!drawingEnabled) return;
		drawCircleMap_native(p.getX(), p.getY(), radius, color.getValue());
	}
	
	public void drawCircleMap(Position p, int radius, Color color, boolean isSolid) {
		if (!drawingEnabled) return;
		drawCircleMap_native(p.getX(), p.getY(), radius, color.getValue(), isSolid);
	}
}
