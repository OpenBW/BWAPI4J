package org.openbw.bwapi4j.type;

public enum Color {

    RED(111),
    BLUE(165),
    TEAL(159),
    PURPLE(164),
    ORANGE(179),
    BROWN(19),
    WHITE(255),
    YELLOW(135),
    GREEN(117),
    CYAN(128),
    BLACK(0),
    GREY(74);

    private int rgb;

    private Color(int rgb) {

    }

    public int getValue() {
        return this.rgb;
    }

    public static Color valueOf(int ordinal) {
        
    	return Color.values()[ordinal];
    }
}
