package yata;

import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;

public class MapPrinter {

    private final BufferedImage image;
    private final Graphics2D g2d;

    public MapPrinter(final TilePosition mapSize) {
        this.image = new BufferedImage(mapSize.getX() * 4, mapSize.getY() * 4, BufferedImage.TYPE_INT_RGB);
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
