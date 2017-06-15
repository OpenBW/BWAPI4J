package bwem;

public class MiniTile {

    private Area.Id id;

    public Area.Id getAreaId() {
        return new Area.Id(this.id);
    }

}
