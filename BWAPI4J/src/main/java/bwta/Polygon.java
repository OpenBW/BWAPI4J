package bwta;

import java.util.ArrayList;
import java.util.List;

import org.openbw.bwapi4j.Position;

public class Polygon {

    private int id;
    private BWTA bwta;
    
    private double area;
    private double perimeter;
    private Position center;
    private List<Polygon> holes;
    
    /**
     * Creates a new Polygon.
     */
    public Polygon(int id, BWTA bwta) {
        
        this.id = id;
        this.bwta = bwta;
        
        this.holes = new ArrayList<Polygon>();
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

    public native Position getNearestPoint(Position p);

    public List<Polygon> getHoles() {
        
        return this.holes;
    }
    
    @Override
    public int hashCode() {
        
        return id;
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