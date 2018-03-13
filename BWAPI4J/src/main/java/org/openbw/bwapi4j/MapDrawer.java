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
     * 
     * @param enabled
     *            true if drawing is enabled, false else.
     */
    public void setEnabled(boolean enabled) {
        this.drawingEnabled = enabled;
    }

    public boolean isEnabled() {
        return drawingEnabled;
    }

    private native void drawCircleMap_native(int x, int y, int radius, int color);

    private native void drawCircleMap_native(int x, int y, int radius, int color, boolean isSolid);

    private native void drawBoxMap_native(int left, int top, int right, int bottom, int color);

    private native void drawBoxMap_native(int left, int top, int right, int bottom, int color, boolean isSolid);

    private native void drawBoxScreen_native(int left, int top, int right, int bottom, int color, boolean isSolid);

    private native void drawLineMap_native(int x1, int y1, int x2, int y2, int color);

    private native void drawTextMap_native(int x, int y, String cstr_format);

    private native void drawTextScreen_native(int x, int y, String cstrFormat);

    public void drawCircleMap(Position p, int radius, Color color) {

        drawCircleMap(p.getX(), p.getY(), radius, color, false);
    }

    public void drawCircleMap(Position p, int radius, Color color, boolean isSolid) {

        drawCircleMap(p.getX(), p.getY(), radius, color, isSolid);
    }

    public void drawCircleMap(int x, int y, int radius, Color color) {

        drawCircleMap(x, y, radius, color, false);
    }

    public void drawCircleMap(int x, int y, int radius, Color color, boolean isSolid) {

        if (drawingEnabled) {
            drawCircleMap_native(x, y, radius, color.getValue(), isSolid);
        }
    }

    public void drawBoxMap(Position topLeft, Position bottomRight, Color color) {

        drawBoxMap(topLeft, bottomRight, color, false);
    }

    public void drawBoxMap(Position topLeft, Position bottomRight, Color color, boolean isSolid) {

        drawBoxMap(topLeft.getX(), topLeft.getY(), bottomRight.getX(), bottomRight.getY(), color, isSolid);
    }

    public void drawBoxMap(int left, int top, int right, int bottom, Color color) {

        drawBoxMap(left, top, right, bottom, color, false);
    }

    public void drawBoxMap(int left, int top, int right, int bottom, Color color, boolean isSolid) {

        if (drawingEnabled) {
            drawBoxMap_native(left, top, right, bottom, color.getValue(), isSolid);
        }
    }

    public void drawBoxScreen(Position topLeft, Position bottomRight, Color color) {

        drawBoxScreen(topLeft.getX(), topLeft.getY(), bottomRight.getX(), bottomRight.getY(), color, false);
    }

    public void drawBoxScreen(Position topLeft, Position bottomRight, Color color, boolean isSolid) {

        drawBoxScreen(topLeft.getX(), topLeft.getY(), bottomRight.getX(), bottomRight.getY(), color, isSolid);
    }

    public void drawBoxScreen(int left, int top, int right, int bottom, Color color) {

        drawBoxScreen(left, top, right, bottom, color, false);
    }

    public void drawBoxScreen(int left, int top, int right, int bottom, Color color, boolean isSolid) {

        if (drawingEnabled) {
            drawBoxScreen_native(left, top, right, bottom, color.getValue(), isSolid);
        }
    }

    public void drawLineMap(Position a, Position b, Color color) {

        drawLineMap(a.getX(), a.getY(), b.getX(), b.getY(), color);
    }

    public void drawLineMap(int x1, int y1, int x2, int y2, Color color) {

        if (drawingEnabled) {
            drawLineMap_native(x1, y1, x2, y2, color.getValue());
        }
    }

    public void drawTextMap(Position position, String text) {

        drawTextMap(position.getX(), position.getY(), text);
    }

    public void drawTextMap(int x, int y, String text) {

        if (drawingEnabled) {
            drawTextMap_native(x, y, text);
        }
    }

    public void drawTextScreen(Position position, String text) {

        drawTextScreen(position.getX(), position.getY(), text);
    }

    public void drawTextScreen(int x, int y, String text) {

        if (drawingEnabled) {
            drawTextScreen_native(x, y, text);
        }
    }
}
