package bwta;

import java.util.ArrayList;
import java.util.List;

import org.openbw.bwapi4j.Position;

public class Polygon {

    private long id;
    
    private double area;
    private double perimeter;
    private Position center;
    private ArrayList<Polygon> holes;
    private ArrayList<Position> points;
    
    /**
     * Creates a new Polygon.
     */
    public Polygon(long id) {
        
        this.id = id;
        
        this.holes = new ArrayList<Polygon>();
        this.points = new ArrayList<Position>();
    }
    
    public double getArea() {

        return this.area;
    }

    public double getPerimeter() {

        return this.perimeter;
    }

    public Position getCenter() {

        return this.center;
    }

    public Position getNearestPosition(Position p) {
        
        int[] coordinates = getNearestPoint(this.id, p.getX(), p.getY());
        return new Position(coordinates[0], coordinates[1]);
    }
    
    private native int[] getNearestPoint(long polygonId, int x, int y);

    public List<Polygon> getHoles() {
        
        return this.holes;
    }
    
    public List<Position> getPoints() {
        
        return this.points;
    }
    
    @Override
    public int hashCode() {
        
        return (int)id;
    }

    @Override
    public boolean equals(Object obj) {
        
        if (obj == null || !(obj instanceof Polygon)) {
            return false;
        } else {
            
            return this.id == ((Polygon) obj).id;
        }
    }
}