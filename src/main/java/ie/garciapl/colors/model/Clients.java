package ie.garciapl.colors.model;

import java.util.List;
import java.util.Map;

public class Clients {

    private final Integer colorsAmount;
    private final Map<Integer, List<ColorPick>> colorPicks;

    public Clients(Integer colorsAmount, Map<Integer, List<ColorPick>> colorPicks) {
        this.colorsAmount = colorsAmount;
        this.colorPicks = colorPicks;
    }

    public Integer getColorsAmount() {
        return colorsAmount;
    }

    public Map<Integer, List<ColorPick>> getColorPicks() {
        return colorPicks;
    }
}