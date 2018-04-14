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

import org.openbw.bwapi4j.BWMap;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;
import org.xguzm.pathfinding.NavigationGraph;
import org.xguzm.pathfinding.finders.AStarFinder;
import org.xguzm.pathfinding.grid.GridCell;
import org.xguzm.pathfinding.grid.NavigationGrid;
import org.xguzm.pathfinding.grid.finders.AStarGridFinder;
import org.xguzm.pathfinding.grid.finders.GridFinderOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Yet Another Terrain Analyzer
 */
public final class YATA {

    private final int width;
    private final int height;
    private final GridCell[][] nodes;
    private final GridFinderOptions finderOptions;
    private final NavigationGraph<GridCell> navigationGrid;
    private final AStarFinder<GridCell> finder;

    public YATA(final BWMap bwMap) {
        this.width = bwMap.mapWidth();
        this.height = bwMap.mapHeight();

        this.nodes = new GridCell[getWidth()][getHeight()];
        for (int y = 0; y < getHeight(); ++y) {
            for (int x = 0; x < getWidth(); ++x) {
                getNodes()[x][y] = new GridCell();
            }
        }

        // Set walkability info in tiles.
        for (int y = 0; y < getHeight(); ++y) {
            for (int x = 0; x < getWidth(); ++x) {
                final WalkPosition walkPosition = new TilePosition(x, y).toWalkPosition();
                boolean isTileWalkable = true;
                // Check each of the 4 mini tiles within a tile to determine the tile's walkability.
                // A tile is unwalkable if any of the 4 mini tiles are unwalkable.
                final WalkPosition deltas[] = {walkPosition, walkPosition.add(new WalkPosition(1, 0)), walkPosition.add(new WalkPosition(0, 1)), walkPosition.add(new WalkPosition(1, 1))};
                for (final WalkPosition delta : deltas) {
                    if (!bwMap.isWalkable(delta.getX(), delta.getY())) {
                        isTileWalkable = false;
                        break;
                    }
                }
                getNodes()[x][y].setWalkable(isTileWalkable);
            }
        }

        this.finderOptions = new GridFinderOptions();
        this.finderOptions.allowDiagonal = true;

        this.navigationGrid = new NavigationGrid<>(this.nodes, true);

        this.finder = new AStarGridFinder<>(GridCell.class, this.finderOptions);
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public GridCell[][] getNodes() {
        return this.nodes;
    }

    public List<TilePosition> getPath(final TilePosition start, final TilePosition end) {
        final GridCell startNode = this.nodes[start.getX()][start.getY()];
        final GridCell endNode = this.nodes[end.getX()][end.getY()];
        final List<GridCell> gridCellPath = this.finder.findPath(startNode, endNode, this.navigationGrid);

        final List<TilePosition> tilePath = new ArrayList<>();
        if (gridCellPath != null) {
            for (final GridCell cell : gridCellPath) {
                tilePath.add(new TilePosition(cell.x, cell.y));
            }
        }
        return tilePath;
    }

}
