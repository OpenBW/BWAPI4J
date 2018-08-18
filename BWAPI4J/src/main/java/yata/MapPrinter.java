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

package yata;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import javax.imageio.ImageIO;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;

public class MapPrinter {

  private final BufferedImage image;
  private final Graphics2D g2d;

  public MapPrinter(final TilePosition mapSize) {
    this.image =
        new BufferedImage(mapSize.getX() * 4, mapSize.getY() * 4, BufferedImage.TYPE_INT_RGB);
    this.g2d = image.createGraphics();
  }

  public void Point(final WalkPosition position, final Color color) {
    g2d.setColor(color);
    g2d.fillRect(position.getX(), position.getY(), 5, 5);
  }

  public void writeImageToFile(Path file) throws IOException {
    ImageIO.write(this.image, "png", file.toFile());
  }
}
