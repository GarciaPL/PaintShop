package ie.garciapl.colors.model;

public class ColorPick {

    private final Integer colorNumber;
    private final ColorType colorType;

    public ColorPick(Integer colorNumber, ColorType colorType) {
        this.colorNumber = colorNumber;
        this.colorType = colorType;
    }

    public Integer getColorNumber() {
        return colorNumber;
    }

    public ColorType getColorType() {
        return colorType;
    }

    @Override
    public String toString() {
        return "ColorPick{" +
              "colorNumber=" + colorNumber +
              ", colorType=" + colorType +
              '}';
    }
}
