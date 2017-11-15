package bwem;

import bwem.map.Map;
import bwem.util.BwemExt;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import javax.imageio.ImageIO;
import org.openbw.bwapi4j.BW;
import org.openbw.bwapi4j.WalkPosition;

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

        private CustomColor(Color color) {
            this.color = color;
        }

        public Color color() {
            return this.color;
        }

    }

    public enum dashed_t { not_dashed, dashed };
    public enum fill_t { do_not_fill, fill };

    private BW m_pBW;
    private Map m_pMap;
    private BufferedImage m_pImage;
    private Graphics2D m_pBMP;
    public final boolean showAltitude = true;				// renders the Altitude() value for each MiniTile
    public final boolean showAreas = true;					// renders Areas with distinct colors
    public final boolean showContinents = !showAreas && true;				// renders continents with distinct colors, unless showAreas == true
    public final boolean showLakes = true;					// prints unwalkable MiniTiles in Areas
    public final boolean showSeaSide = true;				// highlights Sea-MiniTiles next to Terrain-MiniTiles
    public final boolean showUnbuildable = false;			// renders Tiles that are not Buildable()
    public final boolean showGroundHeight = true;			// renders Tiles for which GroundHeight() > 0
    public final boolean showChokePoints = true;			// renders the ChokePoints suggested by BWEM
    public final boolean showResources = true;
    public final boolean showMinerals = showResources && true;				// prints the Minerals, unless showResources == false
    public final boolean showGeysers = showResources && true;				// prints the Geysers, unless showResources == false
    public final boolean showStaticBuildings = true;		// prints the StaticBuildings
    public final boolean showBlockingBuildings = true;		// renders the StaticBuildings and the Minerals that are blocking some ChokePoint
    public final boolean showStackedNeutrals = true;		// renders the StaticBuildings and the Minerals that are stacked
    public final boolean showStartingLocations = true;		// renders the Bases that are at starting locations
    public final boolean showBases = true;					// prints the Bases suggested by BWEM
    public final boolean showAssignedRessources = showBases && true;		// renders the Ressources assigned to each Base, unless showBases == false
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

    public static Color Color() {
        return new Color(0, 0, 0);
    }

    public void Initialize(BW pBW, Map pMap) {
//        bwem_assert_throw(pMap->Initialized());
        if (!(pMap.Initialized())) {
            throw new IllegalStateException();
        }
        //TODO:
//        bwem_assert_throw_plus(canWrite(m_fileName), "MapPrinter could not create the file " + m_fileName);

        m_pBW = pBW;
        m_pMap = pMap;
        m_pImage = new BufferedImage(pBW.getBWMap().mapWidth() * 4, pBW.getBWMap().mapHeight() * 4, BufferedImage.TYPE_INT_RGB);
        m_pBMP = m_pImage.createGraphics();
    }

    public void Point(int x, int y, Color col) {
//    	bwem_assert((0 <= x) && (x < m_pBMP->TellWidth()));
        if (!((0 <= x) && (x < m_pImage.getWidth()))) {
            throw new IllegalArgumentException();
        }
//    	bwem_assert((0 <= y) && (y < m_pBMP->TellHeight()));
        if (!((0 <= y) && (y < m_pImage.getHeight()))) {
            throw new IllegalArgumentException();
        }

        m_pBMP.setColor(col);
    	m_pBMP.drawLine(x, y, x, y);
    }

    public void Point(WalkPosition p, Color col) {
        Point(p.getX(), p.getY(), col);
    }

    public void Line(WalkPosition A, WalkPosition B, Color col, dashed_t dashedMode) {
    	int N = Math.max(1, BwemExt.roundedDist(A, B));
    	if ((dashedMode == dashed_t.dashed) && (N >= 4)) N /= 2;

    	for (int i = 0 ; i <= N ; ++i) {
            int x = (A.getX() * i + B.getX() * (N - i)) / N;
            int y = (A.getY() * i + B.getY() * (N - i)) / N;
    		Point(new WalkPosition(x, y), col);
        }
    }

    public void Line(WalkPosition A, WalkPosition B, Color col) {
        Line(A, B, col, dashed_t.not_dashed);
    }

    public void Rectangle(WalkPosition TopLeft, WalkPosition BottomRight, Color col, fill_t fillMode, dashed_t dashedMode) {
    	for (int y = TopLeft.getY() ; y <= BottomRight.getY() ; ++y)
    	for (int x = TopLeft.getX() ; x <= BottomRight.getX() ; ++x)
    		if ((fillMode == fill_t.fill) || (y == TopLeft.getY()) || (y == BottomRight.getY()) || (x == TopLeft.getX()) || (x == BottomRight.getX()))
    			if ((dashedMode == dashed_t.not_dashed) || ((x + y) & 1) != 0)
    				Point(x, y, col);
    }

    public void Rectangle(WalkPosition TopLeft, WalkPosition BottomRight, Color col, fill_t fillMode) {
        Rectangle(TopLeft, BottomRight, col, fillMode, dashed_t.not_dashed);
    }

    public void Rectangle(WalkPosition TopLeft, WalkPosition BottomRight, Color col) {
        Rectangle(TopLeft, BottomRight, col, fill_t.do_not_fill);
    }


	// This function will automatically crop the square so that it fits in the Map.
    public void Square(WalkPosition Center, int radius, Color col, fill_t fillMode) {
    	for (int y = Center.getY() - radius; y <= Center.getY() + radius; ++y)
    	for (int x = Center.getX() - radius; x <= Center.getX() + radius; ++x)
    		if ((fillMode == fill_t.fill) || (y == Center.getY() - radius) || (y == Center.getY() + radius) || (x == Center.getX() - radius) || (x == Center.getX() + radius))
    			if (m_pMap.getMapData().isValid(new WalkPosition(x, y)))
    				Point(x, y, col);
    }

    public void Square(WalkPosition Center, int radius, Color col) {
        Square(Center, radius, col, fill_t.do_not_fill);
    }

	// This function will automatically crop the circle so that it fits in the Map.
    public void Circle(WalkPosition Center, int radius, Color col, fill_t fillMode) {
        for (int y = Center.getY() - radius; y <= Center.getY() + radius; ++y)
        for (int x = Center.getX() - radius; x <= Center.getX() + radius; ++x) {
            WalkPosition w = new WalkPosition(x, y);
            if (BwemExt.dist(w, Center) <= radius)
            if ((fillMode == fill_t.fill) || (BwemExt.dist(w, Center) >= radius - 1))
                if (m_pMap.getMapData().isValid(w))
                    Point(x, y, col);
        }
    }

    public void Circle(WalkPosition Center, int radius, Color col) {
        Circle(Center, radius, col, fill_t.do_not_fill);
    }

    public void writeImageToFile(Path file) throws IOException {
        ImageIO.write(m_pImage, "bmp", file.toFile());
    }

}
