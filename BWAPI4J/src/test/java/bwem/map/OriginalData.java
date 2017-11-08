package bwem.map;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class OriginalData {

    private static final Path WALKABILITY_INFO_FILE = Paths.get("BWMapMock_groundInfo.txt");
    private static final Path GROUND_INFO_FILE = Paths.get("BWMapMock_groundInfo.txt");

    public int[] walkabilityInfo_ORIGINAL;
    public int[] groundInfo_ORIGINAL;

    public OriginalData() throws FileNotFoundException {
        int width = 128;
        int height = width;
        int walkWidth = width * 4;
        int walkHeight = walkWidth;

        this.walkabilityInfo_ORIGINAL = new int[walkWidth * walkHeight];
        this.groundInfo_ORIGINAL = new int[width * height];

        populateArrays();
    }

    private void populateArrays() throws FileNotFoundException {
        Scanner scanner;
        int index;

        scanner = new Scanner(WALKABILITY_INFO_FILE.toFile());
        index = 0;
        while (scanner.hasNext()) {
            try {
                this.walkabilityInfo_ORIGINAL[index] = scanner.nextInt();
                ++index;
            } catch (Exception ex1) {
                try {
                    this.walkabilityInfo_ORIGINAL[index] = (int) scanner.nextLong();
                    ++index;
                } catch (Exception ex2) {
                    break;
                }
            }
        }

        scanner = new Scanner(GROUND_INFO_FILE.toFile());
        index = 0;
        while (scanner.hasNext()) {
            try {
                this.groundInfo_ORIGINAL[index] = scanner.nextInt();
                ++index;
            } catch (Exception ex1) {
                try {
                    this.groundInfo_ORIGINAL[index] = (int) scanner.nextLong();
                    ++index;
                } catch (Exception ex2) {
                    break;
                }
            }
        }
    }

}
