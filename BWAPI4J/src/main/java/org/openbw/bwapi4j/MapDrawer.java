////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (C) 2017-2018 OpenBW Team
//
//    This file is part of BWAPI4J.
//
//    BWAPI4J is free software: you can redistribute it and/or modify
//    it under the terms of the Lesser GNU General Public License as published 
//    by the Free Software Foundation, version 3 only.
//
//    BWAPI4J is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with BWAPI4J.  If not, see <http://www.gnu.org/licenses/>.
//
////////////////////////////////////////////////////////////////////////////////

package org.openbw.bwapi4j;

import org.openbw.bwapi4j.type.Color;

/**
 * Contains all map-drawing-related bwapi functionality.
 */
public final class MapDrawer {

    public enum TextSize {
        Small,
        Default,
        Large,
        Huge
    }


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

    public void setTextSize(TextSize textSize) {

        setTextSize(textSize.ordinal());
    }

    private native void setTextSize(int textSize);

    private native void drawLineScreen_native(final int x1, final int y1, final int x2, final int y2, final int colorValue);

    public void drawLineScreen(final int x1, final int y1, final int x2, final int y2, final Color color) {
        drawLineScreen_native(x1, y1, x2, y2, color.getValue());
    }

    public void drawLineScreen(final Position a, final Position b, final Color color) {
        drawLineScreen(a.getX(), a.getY(), b.getX(), b.getY(), color);
    }
}
