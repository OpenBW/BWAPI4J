package bwem;

import bwem.map.Map;
import bwem.util.BwemExt;
import org.openbw.bwapi4j.BW;
import org.openbw.bwapi4j.WalkPosition;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;

//////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                          //
//                                  class MapPrinter
//                                                                                          //
//////////////////////////////////////////////////////////////////////////////////////////////

public class MapPrinter {

    public enum CustomColor {

        HIGHER_GROUND(new Color(0, 0, 0)),
        UNBUILDABLE(new Color(0, 0, 0)),
        TERRAIN(new Color(164, 164, 164)),
        SEA(new Color(0, 0, 192)),
        SEA_SIDE(new Color(0, 0, 100)),
        LAKE(new Color(0, 100, 200)),
        TINY_AREA(new Color(50, 50, 255)),
        CHOKE_POINTS_SHOW_AREAS(new Color(255, 255, 255)),
        CHOKE_POINTS_SHOW_CONTINENTS(new Color(0, 0, 0)),
        MINERALS(new Color(0, 255, 255)),
        GEYSERS(new Color(0, 255, 0)),
        STATIC_BUILDINGS(new Color(255, 0, 255)),
        BLOCKING_NEUTRALS(new Color(0, 0, 0)),
        STARTING_LOCATIONS(new Color(255, 255, 0)),
        BASES(new Color(0, 0, 255));

        private final Color color;

        CustomColor(Color color) {
            this.color = color;
        }

        public Color color() {
            return this.color;
        }

    }

    public enum dashed_t { not_dashed, dashed }
    public enum fill_t { do_not_fill, fill }

    private Map map;
    private BufferedImage image;
    private Graphics2D BMP;
    public final boolean showAltitude = true;				// renders the Altitude() value for each MiniTile
    public final boolean showAreas = true;					// renders areas with distinct colors
    public final boolean showContinents = !showAreas;				// renders continents with distinct colors, unless showAreas == true
    public final boolean showLakes = true;					// prints unwalkable miniTiles in areas
    public final boolean showSeaSide = true;				// highlights Sea-miniTiles next to Terrain-miniTiles
    public final boolean showUnbuildable = false;			// renders Tiles that are not Buildable()
    public final boolean showGroundHeight = true;			// renders Tiles for which GroundHeight() > 0
    public final boolean showChokePoints = true;			// renders the getChokePoints suggested by BWEM
    public final boolean showResources = true;
    public final boolean showMinerals = showResources;				// prints the minerals, unless showResources == false
    public final boolean showGeysers = showResources;				// prints the geysers, unless showResources == false
    public final boolean showStaticBuildings = true;		// prints the StaticBuildings
    public final boolean showBlockingBuildings = true;		// renders the StaticBuildings and the minerals that are blocking some ChokePoint
    public final boolean showStackedNeutrals = true;		// renders the StaticBuildings and the minerals that are stacked
    public final boolean showStartingLocations = true;		// renders the bases that are at starting locations
    public final boolean showBases = true;					// prints the bases suggested by BWEM
    public final boolean showAssignedResources = showBases;		// renders the Resources assigned to each Base, unless showBases == false
    public final boolean showData = false;					// renders the Data() value for each MiniTile

    public MapPrinter() {
        /* Do nothing. */
    }

    //MapPrinter::~MapPrinter()
    //{
    //	if (canWrite(m_fileName)) m_pBMP->WriteToFile(m_fileName.c_str());
    //
    ////	Uncomment the 2 lines below to write one more copy of the bitmap, in the folder of the map used, with the same name.
    ////	string twinFileName = bw->mapPathName().substr(0, bw->mapPathName().size()-3) + "bmp";
    ////	if (canWrite(twinFileName)) m_pBMP->WriteToFile(twinFileName.c_str());
    //}

    public static Color color() {
        return new Color(0, 0, 0);
    }

    public void initialize(BW pBW, Map pMap) {
//        bwem_assert_throw(pMap->Initialized());
        if (!(pMap.isInitialized())) {
            throw new IllegalStateException();
        }
        //TODO:
//        bwem_assert_throw_plus(canWrite(m_fileName), "MapPrinter could not create the file " + m_fileName);

        this.map = pMap;
        image = new BufferedImage(pBW.getBWMap().mapWidth() * 4, pBW.getBWMap().mapHeight() * 4, BufferedImage.TYPE_INT_RGB);
        BMP = image.createGraphics();
    }

    public void point(int x, int y, Color col) {
//    	bwem_assert((0 <= x) && (x < pBMP->TellWidth()));
        if (!((0 <= x) && (x < image.getWidth()))) {
            throw new IllegalArgumentException();
        }
//    	bwem_assert((0 <= y) && (y < pBMP->TellHeight()));
        if (!((0 <= y) && (y < image.getHeight()))) {
            throw new IllegalArgumentException();
        }

        BMP.setColor(col);
    	BMP.drawLine(x, y, x, y);
    }

    public void point(WalkPosition p, Color col) {
        point(p.getX(), p.getY(), col);
    }

    public void line(WalkPosition a, WalkPosition b, Color col, dashed_t dashedMode) {
    	int n = Math.max(1, BwemExt.roundedDist(a, b));
    	if ((dashedMode == dashed_t.dashed) && (n >= 4)) n /= 2;

    	for (int i = 0; i <= n; ++i) {
            int x = (a.getX() * i + b.getX() * (n - i)) / n;
            int y = (a.getY() * i + b.getY() * (n - i)) / n;
    		point(new WalkPosition(x, y), col);
        }
    }

    public void line(WalkPosition a, WalkPosition b, Color col) {
        line(a, b, col, dashed_t.not_dashed);
    }

    public void rectangle(WalkPosition topLeft, WalkPosition bottomRight, Color col, fill_t fillMode, dashed_t dashedMode) {
    	for (int y = topLeft.getY(); y <= bottomRight.getY() ; ++y)
    	for (int x = topLeft.getX(); x <= bottomRight.getX() ; ++x)
    		if ((fillMode == fill_t.fill) || (y == topLeft.getY()) || (y == bottomRight.getY()) || (x == topLeft.getX()) || (x == bottomRight.getX()))
    			if ((dashedMode == dashed_t.not_dashed) || ((x + y) & 1) != 0)
    				point(x, y, col);
    }

    public void rectangle(WalkPosition topLeft, WalkPosition bottomRight, Color col, fill_t fillMode) {
        rectangle(topLeft, bottomRight, col, fillMode, dashed_t.not_dashed);
    }

    public void rectangle(WalkPosition topLeft, WalkPosition bottomRight, Color col) {
        rectangle(topLeft, bottomRight, col, fill_t.do_not_fill);
    }


	// This function will automatically crop the square so that it fits in the Map.
    public void square(WalkPosition center, int radius, Color col, fill_t fillMode) {
    	for (int y = center.getY() - radius; y <= center.getY() + radius; ++y)
    	for (int x = center.getX() - radius; x <= center.getX() + radius; ++x)
    		if ((fillMode == fill_t.fill) || (y == center.getY() - radius) || (y == center.getY() + radius) || (x == center.getX() - radius) || (x == center.getX() + radius))
    			if  (map.getData().getMapData().isValid(new WalkPosition(x, y)))
    				point(x, y, col);
    }

    public void square(WalkPosition center, int radius, Color col) {
        square(center, radius, col, fill_t.do_not_fill);
    }

	// This function will automatically crop the circle so that it fits in the Map.
    public void circle(WalkPosition center, int radius, Color col, fill_t fillMode) {
        for (int y = center.getY() - radius; y <= center.getY() + radius; ++y)
        for (int x = center.getX() - radius; x <= center.getX() + radius; ++x) {
            WalkPosition w = new WalkPosition(x, y);
            if (BwemExt.dist(w, center) <= radius)
            if ((fillMode == fill_t.fill) || (BwemExt.dist(w, center) >= radius - 1))
                if  (map.getData().getMapData().isValid(w))
                    point(x, y, col);
        }
    }

    public void circle(WalkPosition center, int radius, Color col) {
        circle(center, radius, col, fill_t.do_not_fill);
    }

    public void writeImageToFile(Path file, String imageFormat) throws IOException {
        ImageIO.write (image, imageFormat, file.toFile());
    }

}
