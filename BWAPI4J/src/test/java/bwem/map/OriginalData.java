package bwem.map;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class OriginalData {

    public int[] walkabilityInfo_ORIGINAL;
    public int[] groundInfo_ORIGINAL;
    public boolean[] buildableInfo_ORIGINAL;
    
    private int index;
    
    public OriginalData() throws IOException, URISyntaxException {
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
    	URI fileURI = OriginalData.class.getResource("BWMapMock_walkabilityInfo.txt").toURI();
    	
    		Stream<String> stream1 = Files.lines(Paths.get(fileURI));
    	
    		stream1.forEach(l -> { 
        	for (String s : l.split(" ")) {
        		
        		this.walkabilityInfo_ORIGINAL[index++] = Integer.valueOf(s);
        	}
        });
        stream1.close();
    	
    	System.out.println("added " + index + " values.");
        
    	this.index = 0;
    	fileURI = OriginalData.class.getResource("BWMapMock_groundInfo.txt").toURI();
    	Stream<String> stream2 = Files.lines(Paths.get(fileURI));
        stream2.forEach(l -> { 
        	for (String s : l.split(",")) {
        		
        		this.groundInfo_ORIGINAL[index++] = Integer.valueOf(s.trim());
        	}
        });
    	stream2.close();
        System.out.println("added " + index + " values.");
        
        this.index = 0;
    	fileURI = OriginalData.class.getResource("BWMapMock_buildableInfo.txt").toURI();
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
