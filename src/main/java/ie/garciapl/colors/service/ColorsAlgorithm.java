package ie.garciapl.colors.service;

import ie.garciapl.colors.model.Clients;
import ie.garciapl.colors.model.ColorPick;
import ie.garciapl.colors.model.ColorType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ColorsAlgorithm {

    private Clients clients;
    private Map<Integer, ColorType> mixture = new HashMap<>();

    public ColorsAlgorithm(Clients clients) {
        this.clients = clients;
    }

    public String findColors() {

        Map<Integer, List<ColorPick>> clientsOnePick = clients.getColorPicks().entrySet().stream()
                .filter(integerListEntry -> integerListEntry.getValue().size() == 1)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        Map<Integer, List<ColorPick>> clientsManyPicks = clients.getColorPicks().entrySet().stream()
                .filter(integerListEntry -> integerListEntry.getValue().size() > 1)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        for (Map.Entry<Integer, List<ColorPick>> clientEntry : clientsOnePick.entrySet()) {
            for (ColorPick colorPick : clientEntry.getValue()) {
                ColorType colorTypeHistory = checkMixture(colorPick);
                if (colorTypeHistory == null) {
                    enrichMixture(colorPick);
                } else if (!colorTypeHistory.equals(colorPick.getColorType())) {
                    throw new NoSolutionException();
                }
            }
        }

        for (Map.Entry<Integer, List<ColorPick>> clientEntry : clientsManyPicks.entrySet()) {
            ColorPick colorPick = null;
            Boolean noColorMatch = false;
            for (ColorPick paint : clientEntry.getValue()) {
                ColorType colorTypeHistory = checkMixture(paint);
                if (colorTypeHistory == null) {
                    if (paint.getColorType().equals(ColorType.GLOSS)) {
                        colorPick = paint;
                        break;
                    } else {
                        colorPick = paint;
                    }
                } else if (colorTypeHistory.equals(paint.getColorType())) {
                    break;
                }
                noColorMatch = true;
            }
            if (noColorMatch) {
                if (colorPick == null) {
                    throw new NoSolutionException();
                } else {
                    enrichMixture(colorPick);
                }
            }
        }

        return IntStream.rangeClosed(1, clients.getColorsAmount()).mapToObj(i -> {
            if (!mixture.containsKey(i)) {
                return ColorType.GLOSS.getShortName();
            } else {
                return mixture.get(i).getShortName();
            }
        }).collect(Collectors.joining(" "));
    }

    private ColorType checkMixture(ColorPick colorPick) {
        return mixture.get(colorPick.getColorNumber());
    }

    private void enrichMixture(ColorPick colorPick) {
        mixture.put(colorPick.getColorNumber(), colorPick.getColorType());
    }
}
