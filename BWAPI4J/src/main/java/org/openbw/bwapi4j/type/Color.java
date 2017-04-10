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

    /**
     * Gets the Color for a given RGB value, if it exists.
     * @param rgb integer value of the color
     * @return Color, if it exists for given RGB value, <code>null</code> else.
     */
    public static Color valueOf(int rgb) {
        for (Color color : Color.values()) {
            if (color.rgb == rgb) {
                return color;
            }
        }
        return null;
    }
}
