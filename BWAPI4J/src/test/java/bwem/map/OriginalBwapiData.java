package bwem.map;

import org.openbw.bwapi4j.TilePosition;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class OriginalBwapiData {

    public final int[] walkabilityInfo_ORIGINAL;
    public final int[] groundInfo_ORIGINAL;
    public final boolean[] buildableInfo_ORIGINAL;
    public final TilePosition[] startLocations_FightingSpirit_ORIGINAL = {new TilePosition(117, 7), new TilePosition(7, 6), new TilePosition(7, 116), new TilePosition(117, 117)};
    
    private int index;
    
    public OriginalBwapiData() throws IOException, URISyntaxException {
        int width = 128;
        int height = width;
        int walkWidth = width * 4;
        int walkHeight = walkWidth;

        this.walkabilityInfo_ORIGINAL = new int[walkWidth * walkHeight];
        this.groundInfo_ORIGINAL = new int[width * height];
        this.buildableInfo_ORIGINAL = new boolean[width * height];

        populateArrays();
    }

    private void populateArrays() throws IOException, URISyntaxException {
    	
    	this.index = 0;
    	URI fileURI = OriginalBwapiData.class.getResource("walkabilityInfo_FightingSpirit_ORIGINAL.txt").toURI();
    	
    		Stream<String> stream1 = Files.lines(Paths.get(fileURI));
    	
    		stream1.forEach(l -> { 
        	for (String s : l.split(" ")) {
        		
        		this.walkabilityInfo_ORIGINAL[index++] = Integer.valueOf(s);
        	}
        });
        stream1.close();
    	
    	System.out.println("added " + index + " values.");
        
    	this.index = 0;
    	fileURI = OriginalBwapiData.class.getResource("groundInfo_FightingSpirit_ORIGINAL.txt").toURI();
    	Stream<String> stream2 = Files.lines(Paths.get(fileURI));
        stream2.forEach(l -> { 
        	for (String s : l.split(",")) {
        		
        		this.groundInfo_ORIGINAL[index++] = Integer.valueOf(s.trim());
        	}
        });
    	stream2.close();
        System.out.println("added " + index + " values.");
        
        this.index = 0;
    	fileURI = OriginalBwapiData.class.getResource("buildableInfo_FightingSpirit_ORIGINAL.txt").toURI();
    	Stream<String> stream3 = Files.lines(Paths.get(fileURI));
        stream3.forEach(l -> {
        	for (String s : l.split(",")) {
        		
        		this.buildableInfo_ORIGINAL[index++] = Boolean.valueOf(s.trim());
        	}
        });
    	stream3.close();
        System.out.println("added " + index + " values.");
    }

}
