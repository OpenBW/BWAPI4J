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

package bwapi;

/** Contains all map-drawing-related bwapi functionality. */
public final class MapDrawer {
  public enum CoordinateType {
    None,
    Screen,
    Map,
    Mouse
  }

  public enum Shape {
    None,
    Text,
    Box,
    Triangle,
    Circle,
    Ellipse,
    Dot,
    Line
  }

  public enum TextSize {
    Small,
    Default,
    Large,
    Huge
  }

  private native void setTextSize_native(int textSize);

  public void setTextSize(final TextSize textSize) {
    setTextSize_native(textSize.ordinal());
  }

  private native void drawText_native(int coordinateType, int x, int y, String text);

  private native void drawBox_native(
      int coordinateType, int left, int top, int right, int bottom, int color, boolean isSolid);

  private native void drawTriangle_native(
      int coordinateType,
      int ax,
      int ay,
      int bx,
      int by,
      int cx,
      int cy,
      int color,
      boolean isSolid);

  private native void drawCircle_native(
      int coordinateType, int x, int y, int radius, int color, boolean isSolid);

  private native void drawEllipse_native(
      int coordinateType, int x, int y, int xrad, int yrad, int color, boolean isSolid);

  private native void drawDot_native(int coordinateType, int x, int y, int color);

  private native void drawLine_native(
      int coordinateType, int ax, int ay, int bx, int by, int color);

  public void drawText(CoordinateType ctype, int x, int y, String cstr_format) {
    drawText_native(ctype.ordinal(), x, y, cstr_format);
  }

  public void drawTextMap(int x, int y, String cstr_format) {
    drawText_native(CoordinateType.Map.ordinal(), x, y, cstr_format);
  }

  public void drawTextMap(Position p, String cstr_format) {
    drawText_native(CoordinateType.Map.ordinal(), p.getX(), p.getY(), cstr_format);
  }

  public void drawTextMouse(int x, int y, String cstr_format) {
    drawText_native(CoordinateType.Mouse.ordinal(), x, y, cstr_format);
  }

  public void drawTextMouse(Position p, String cstr_format) {
    drawText_native(CoordinateType.Mouse.ordinal(), p.getX(), p.getY(), cstr_format);
  }

  public void drawTextScreen(int x, int y, String cstr_format) {
    drawText_native(CoordinateType.Screen.ordinal(), x, y, cstr_format);
  }

  public void drawTextScreen(Position p, String cstr_format) {
    drawText_native(CoordinateType.Screen.ordinal(), p.getX(), p.getY(), cstr_format);
  }

  public void drawBox(CoordinateType ctype, int left, int top, int right, int bottom, Color color) {
    drawBox_native(ctype.ordinal(), left, top, right, bottom, color.getValue(), false);
  }

  public void drawBox(
      CoordinateType ctype,
      int left,
      int top,
      int right,
      int bottom,
      Color color,
      boolean isSolid) {
    drawBox_native(ctype.ordinal(), left, top, right, bottom, color.getValue(), isSolid);
  }

  public void drawBoxMap(int left, int top, int right, int bottom, Color color) {
    drawBox_native(CoordinateType.Map.ordinal(), left, top, right, bottom, color.getValue(), false);
  }

  public void drawBoxMap(int left, int top, int right, int bottom, Color color, boolean isSolid) {
    drawBox_native(
        CoordinateType.Map.ordinal(), left, top, right, bottom, color.getValue(), isSolid);
  }

  public void drawBoxMap(Position leftTop, Position rightBottom, Color color) {
    drawBox_native(
        CoordinateType.Map.ordinal(),
        leftTop.getX(),
        leftTop.getY(),
        rightBottom.getX(),
        rightBottom.getY(),
        color.getValue(),
        false);
  }

  public void drawBoxMap(Position leftTop, Position rightBottom, Color color, boolean isSolid) {
    drawBox_native(
        CoordinateType.Map.ordinal(),
        leftTop.getX(),
        leftTop.getY(),
        rightBottom.getX(),
        rightBottom.getY(),
        color.getValue(),
        isSolid);
  }

  public void drawBoxMouse(int left, int top, int right, int bottom, Color color) {
    drawBox_native(
        CoordinateType.Mouse.ordinal(), left, top, right, bottom, color.getValue(), false);
  }

  public void drawBoxMouse(int left, int top, int right, int bottom, Color color, boolean isSolid) {
    drawBox_native(
        CoordinateType.Mouse.ordinal(), left, top, right, bottom, color.getValue(), isSolid);
  }

  public void drawBoxMouse(Position leftTop, Position rightBottom, Color color) {
    drawBox_native(
        CoordinateType.Mouse.ordinal(),
        leftTop.getX(),
        leftTop.getY(),
        rightBottom.getX(),
        rightBottom.getY(),
        color.getValue(),
        false);
  }

  public void drawBoxMouse(Position leftTop, Position rightBottom, Color color, boolean isSolid) {
    drawBox_native(
        CoordinateType.Mouse.ordinal(),
        leftTop.getX(),
        leftTop.getY(),
        rightBottom.getX(),
        rightBottom.getY(),
        color.getValue(),
        isSolid);
  }

  public void drawBoxScreen(int left, int top, int right, int bottom, Color color) {
    drawBox_native(
        CoordinateType.Screen.ordinal(), left, top, right, bottom, color.getValue(), false);
  }

  public void drawBoxScreen(
      int left, int top, int right, int bottom, Color color, boolean isSolid) {
    drawBox_native(
        CoordinateType.Screen.ordinal(), left, top, right, bottom, color.getValue(), isSolid);
  }

  public void drawBoxScreen(Position leftTop, Position rightBottom, Color color) {
    drawBox_native(
        CoordinateType.Screen.ordinal(),
        leftTop.getX(),
        leftTop.getY(),
        rightBottom.getX(),
        rightBottom.getY(),
        color.getValue(),
        false);
  }

  public void drawBoxScreen(Position leftTop, Position rightBottom, Color color, boolean isSolid) {
    drawBox_native(
        CoordinateType.Screen.ordinal(),
        leftTop.getX(),
        leftTop.getY(),
        rightBottom.getX(),
        rightBottom.getY(),
        color.getValue(),
        isSolid);
  }

  public void drawTriangle(
      CoordinateType ctype, int ax, int ay, int bx, int by, int cx, int cy, Color color) {
    drawTriangle_native(ctype.ordinal(), ax, ay, bx, by, cx, cy, color.getValue(), false);
  }

  public void drawTriangle(
      CoordinateType ctype,
      int ax,
      int ay,
      int bx,
      int by,
      int cx,
      int cy,
      Color color,
      boolean isSolid) {
    drawTriangle_native(ctype.ordinal(), ax, ay, bx, by, cx, cy, color.getValue(), isSolid);
  }

  public void drawTriangleMap(int ax, int ay, int bx, int by, int cx, int cy, Color color) {
    drawTriangle_native(
        CoordinateType.Map.ordinal(), ax, ay, bx, by, cx, cy, color.getValue(), false);
  }

  public void drawTriangleMap(
      int ax, int ay, int bx, int by, int cx, int cy, Color color, boolean isSolid) {
    drawTriangle_native(
        CoordinateType.Map.ordinal(), ax, ay, bx, by, cx, cy, color.getValue(), isSolid);
  }

  public void drawTriangleMap(Position a, Position b, Position c, Color color) {
    drawTriangle_native(
        CoordinateType.Map.ordinal(),
        a.getX(),
        a.getY(),
        b.getX(),
        b.getY(),
        c.getX(),
        c.getY(),
        color.getValue(),
        false);
  }

  public void drawTriangleMap(Position a, Position b, Position c, Color color, boolean isSolid) {
    drawTriangle_native(
        CoordinateType.Map.ordinal(),
        a.getX(),
        a.getY(),
        b.getX(),
        b.getY(),
        c.getX(),
        c.getY(),
        color.getValue(),
        isSolid);
  }

  public void drawTriangleMouse(int ax, int ay, int bx, int by, int cx, int cy, Color color) {
    drawTriangle_native(
        CoordinateType.Mouse.ordinal(), ax, ay, bx, by, cx, cy, color.getValue(), false);
  }

  public void drawTriangleMouse(
      int ax, int ay, int bx, int by, int cx, int cy, Color color, boolean isSolid) {
    drawTriangle_native(
        CoordinateType.Mouse.ordinal(), ax, ay, bx, by, cx, cy, color.getValue(), isSolid);
  }

  public void drawTriangleMouse(Position a, Position b, Position c, Color color) {
    drawTriangle_native(
        CoordinateType.Mouse.ordinal(),
        a.getX(),
        a.getY(),
        b.getX(),
        b.getY(),
        c.getX(),
        c.getY(),
        color.getValue(),
        false);
  }

  public void drawTriangleMouse(Position a, Position b, Position c, Color color, boolean isSolid) {
    drawTriangle_native(
        CoordinateType.Mouse.ordinal(),
        a.getX(),
        a.getY(),
        b.getX(),
        b.getY(),
        c.getX(),
        c.getY(),
        color.getValue(),
        isSolid);
  }

  public void drawTriangleScreen(int ax, int ay, int bx, int by, int cx, int cy, Color color) {
    drawTriangle_native(
        CoordinateType.Screen.ordinal(), ax, ay, bx, by, cx, cy, color.getValue(), false);
  }

  public void drawTriangleScreen(
      int ax, int ay, int bx, int by, int cx, int cy, Color color, boolean isSolid) {
    drawTriangle_native(
        CoordinateType.Screen.ordinal(), ax, ay, bx, by, cx, cy, color.getValue(), isSolid);
  }

  public void drawTriangleScreen(Position a, Position b, Position c, Color color) {
    drawTriangle_native(
        CoordinateType.Screen.ordinal(),
        a.getX(),
        a.getY(),
        b.getX(),
        b.getY(),
        c.getX(),
        c.getY(),
        color.getValue(),
        false);
  }

  public void drawTriangleScreen(Position a, Position b, Position c, Color color, boolean isSolid) {
    drawTriangle_native(
        CoordinateType.Screen.ordinal(),
        a.getX(),
        a.getY(),
        b.getX(),
        b.getY(),
        c.getX(),
        c.getY(),
        color.getValue(),
        isSolid);
  }

  public void drawCircle(CoordinateType ctype, int x, int y, int radius, Color color) {
    drawCircle_native(ctype.ordinal(), x, y, radius, color.getValue(), false);
  }

  public void drawCircle(
      CoordinateType ctype, int x, int y, int radius, Color color, boolean isSolid) {
    drawCircle_native(ctype.ordinal(), x, y, radius, color.getValue(), isSolid);
  }

  public void drawCircleMap(int x, int y, int radius, Color color) {
    drawCircle_native(CoordinateType.Map.ordinal(), x, y, radius, color.getValue(), false);
  }

  public void drawCircleMap(int x, int y, int radius, Color color, boolean isSolid) {
    drawCircle_native(CoordinateType.Map.ordinal(), x, y, radius, color.getValue(), isSolid);
  }

  public void drawCircleMap(Position p, int radius, Color color) {
    drawCircle_native(
        CoordinateType.Map.ordinal(), p.getX(), p.getY(), radius, color.getValue(), false);
  }

  public void drawCircleMap(Position p, int radius, Color color, boolean isSolid) {
    drawCircle_native(
        CoordinateType.Map.ordinal(), p.getX(), p.getY(), radius, color.getValue(), isSolid);
  }

  public void drawCircleMouse(int x, int y, int radius, Color color) {
    drawCircle_native(CoordinateType.Mouse.ordinal(), x, y, radius, color.getValue(), false);
  }

  public void drawCircleMouse(int x, int y, int radius, Color color, boolean isSolid) {
    drawCircle_native(CoordinateType.Mouse.ordinal(), x, y, radius, color.getValue(), isSolid);
  }

  public void drawCircleMouse(Position p, int radius, Color color) {
    drawCircle_native(
        CoordinateType.Mouse.ordinal(), p.getX(), p.getY(), radius, color.getValue(), false);
  }

  public void drawCircleMouse(Position p, int radius, Color color, boolean isSolid) {
    drawCircle_native(
        CoordinateType.Mouse.ordinal(), p.getX(), p.getY(), radius, color.getValue(), isSolid);
  }

  public void drawCircleScreen(int x, int y, int radius, Color color) {
    drawCircle_native(CoordinateType.Screen.ordinal(), x, y, radius, color.getValue(), false);
  }

  public void drawCircleScreen(int x, int y, int radius, Color color, boolean isSolid) {
    drawCircle_native(CoordinateType.Screen.ordinal(), x, y, radius, color.getValue(), isSolid);
  }

  public void drawCircleScreen(Position p, int radius, Color color) {
    drawCircle_native(
        CoordinateType.Screen.ordinal(), p.getX(), p.getY(), radius, color.getValue(), false);
  }

  public void drawCircleScreen(Position p, int radius, Color color, boolean isSolid) {
    drawCircle_native(
        CoordinateType.Screen.ordinal(), p.getX(), p.getY(), radius, color.getValue(), isSolid);
  }

  public void drawEllipse(CoordinateType ctype, int x, int y, int xrad, int yrad, Color color) {
    drawEllipse_native(ctype.ordinal(), x, y, xrad, yrad, color.getValue(), false);
  }

  public void drawEllipse(
      CoordinateType ctype, int x, int y, int xrad, int yrad, Color color, boolean isSolid) {
    drawEllipse_native(ctype.ordinal(), x, y, xrad, yrad, color.getValue(), isSolid);
  }

  public void drawEllipseMap(int x, int y, int xrad, int yrad, Color color) {
    drawEllipse_native(CoordinateType.Map.ordinal(), x, y, xrad, yrad, color.getValue(), false);
  }

  public void drawEllipseMap(int x, int y, int xrad, int yrad, Color color, boolean isSolid) {
    drawEllipse_native(CoordinateType.Map.ordinal(), x, y, xrad, yrad, color.getValue(), isSolid);
  }

  public void drawEllipseMap(Position p, int xrad, int yrad, Color color) {
    drawEllipse_native(
        CoordinateType.Map.ordinal(), p.getX(), p.getY(), xrad, yrad, color.getValue(), false);
  }

  public void drawEllipseMap(Position p, int xrad, int yrad, Color color, boolean isSolid) {
    drawEllipse_native(
        CoordinateType.Map.ordinal(), p.getX(), p.getY(), xrad, yrad, color.getValue(), isSolid);
  }

  public void drawEllipseMouse(int x, int y, int xrad, int yrad, Color color) {
    drawEllipse_native(CoordinateType.Mouse.ordinal(), x, y, xrad, yrad, color.getValue(), false);
  }

  public void drawEllipseMouse(int x, int y, int xrad, int yrad, Color color, boolean isSolid) {
    drawEllipse_native(CoordinateType.Mouse.ordinal(), x, y, xrad, yrad, color.getValue(), isSolid);
  }

  public void drawEllipseMouse(Position p, int xrad, int yrad, Color color) {
    drawEllipse_native(
        CoordinateType.Mouse.ordinal(), p.getX(), p.getY(), xrad, yrad, color.getValue(), false);
  }

  public void drawEllipseMouse(Position p, int xrad, int yrad, Color color, boolean isSolid) {
    drawEllipse_native(
        CoordinateType.Mouse.ordinal(), p.getX(), p.getY(), xrad, yrad, color.getValue(), isSolid);
  }

  public void drawEllipseScreen(int x, int y, int xrad, int yrad, Color color) {
    drawEllipse_native(CoordinateType.Screen.ordinal(), x, y, xrad, yrad, color.getValue(), false);
  }

  public void drawEllipseScreen(int x, int y, int xrad, int yrad, Color color, boolean isSolid) {
    drawEllipse_native(
        CoordinateType.Screen.ordinal(), x, y, xrad, yrad, color.getValue(), isSolid);
  }

  public void drawEllipseScreen(Position p, int xrad, int yrad, Color color) {
    drawEllipse_native(
        CoordinateType.Screen.ordinal(), p.getX(), p.getY(), xrad, yrad, color.getValue(), false);
  }

  public void drawEllipseScreen(Position p, int xrad, int yrad, Color color, boolean isSolid) {
    drawEllipse_native(
        CoordinateType.Screen.ordinal(), p.getX(), p.getY(), xrad, yrad, color.getValue(), isSolid);
  }

  public void drawDot(CoordinateType ctype, int x, int y, Color color) {
    drawDot_native(ctype.ordinal(), x, y, color.getValue());
  }

  public void drawDotMap(int x, int y, Color color) {
    drawDot_native(CoordinateType.Map.ordinal(), x, y, color.getValue());
  }

  public void drawDotMap(Position p, Color color) {
    drawDot_native(CoordinateType.Map.ordinal(), p.getX(), p.getY(), color.getValue());
  }

  public void drawDotMouse(int x, int y, Color color) {
    drawDot_native(CoordinateType.Mouse.ordinal(), x, y, color.getValue());
  }

  public void drawDotMouse(Position p, Color color) {
    drawDot_native(CoordinateType.Mouse.ordinal(), p.getX(), p.getY(), color.getValue());
  }

  public void drawDotScreen(int x, int y, Color color) {
    drawDot_native(CoordinateType.Screen.ordinal(), x, y, color.getValue());
  }

  public void drawDotScreen(Position p, Color color) {
    drawDot_native(CoordinateType.Screen.ordinal(), p.getX(), p.getY(), color.getValue());
  }

  public void drawLine(CoordinateType ctype, int x1, int y1, int x2, int y2, Color color) {
    drawLine_native(ctype.ordinal(), x1, y1, x2, y2, color.getValue());
  }

  public void drawLineMap(int x1, int y1, int x2, int y2, Color color) {
    drawLine_native(CoordinateType.Map.ordinal(), x1, y1, x2, y2, color.getValue());
  }

  public void drawLineMap(Position a, Position b, Color color) {
    drawLine_native(
        CoordinateType.Map.ordinal(), a.getX(), a.getY(), b.getX(), b.getY(), color.getValue());
  }

  public void drawLineMouse(int x1, int y1, int x2, int y2, Color color) {
    drawLine_native(CoordinateType.Mouse.ordinal(), x1, y1, x2, y2, color.getValue());
  }

  public void drawLineMouse(Position a, Position b, Color color) {
    drawLine_native(
        CoordinateType.Mouse.ordinal(), a.getX(), a.getY(), b.getX(), b.getY(), color.getValue());
  }

  public void drawLineScreen(int x1, int y1, int x2, int y2, Color color) {
    drawLine_native(CoordinateType.Screen.ordinal(), x1, y1, x2, y2, color.getValue());
  }

  public void drawLineScreen(Position a, Position b, Color color) {
    drawLine_native(
        CoordinateType.Screen.ordinal(), a.getX(), a.getY(), b.getX(), b.getY(), color.getValue());
  }
}
