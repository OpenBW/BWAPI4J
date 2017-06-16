package bwem;

public class MiniTile {

    public static final int SIZE_IN_PIXELS = 8;

    private Area.Id id;

    public Area.Id getAreaId() {
        return new Area.Id(this.id);
    }

}
