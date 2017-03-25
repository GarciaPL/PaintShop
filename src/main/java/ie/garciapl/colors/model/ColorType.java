package ie.garciapl.colors.model;

public enum ColorType {

    MATTE("M"),
    GLOSS("G");

    private String shortName;

    ColorType(String shortName) {
        this.shortName = shortName;
    }

    public static ColorType safeFromString(String shortName) {
        if (shortName == null || shortName.isEmpty())
            return null;

        for (ColorType colorType : values()) {
            if (colorType.getShortName().equals(shortName)) {
                return colorType;
            }
        }
        return null;
    }

    public String getShortName() {
        return shortName;
    }
}
