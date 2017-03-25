package ie.garciapl.colors.service;

import ie.garciapl.colors.model.Clients;
import ie.garciapl.colors.model.ColorPick;
import ie.garciapl.colors.model.ColorType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class FileReader {

    public Clients readFile(String filePath) throws IOException {

        if (!Files.exists(Paths.get(filePath))) {
            throw new IOException("Error occurred during reading a file. File does not exist");
        }

        Integer colorsAmount = Files.lines(Paths.get(filePath))
              .filter(FileReader::isNumeric)
              .map(Integer::parseInt)
              .findFirst()
              .orElseThrow(() -> new IOException("Error occurred during reading a file. First line is empty or malformed"));

        Map<Integer, List<ColorPick>> colorPicks = new HashMap<>();
        List<String> lines = Files.readAllLines(Paths.get(filePath)).stream().skip(1).collect(Collectors.toList());
        for (int i = 0; i < lines.size(); i++) {
            colorPicks.put((i + 1), getColorsPicks(lines.get(i)));
        }

        if (colorPicks.isEmpty()) {
            System.err.println("No valid entries found in file. Exiting");
            System.exit(0);
        }

        return new Clients(colorsAmount, colorPicks);
    }

    private static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    private static boolean isGlossOrMatte(String str) {
        return str != null && (str.equals(ColorType.GLOSS.getShortName()) || str.equals(ColorType.MATTE.getShortName()));
    }

    private List<ColorPick> getColorsPicks(String line) {
        return Pattern.compile("(?<!\\G\\d{1})\\s{1}")
              .splitAsStream(line)
              .map(s -> s.split(" "))
              .filter(s -> isNumeric(s[0]) && isGlossOrMatte(s[1]))
              .map(s -> new ColorPick(Integer.parseInt(s[0]),
                    ColorType.safeFromString(s[1]))).collect(Collectors.toList());
    }

}
