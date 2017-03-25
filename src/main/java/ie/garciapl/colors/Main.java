package ie.garciapl.colors;

import ie.garciapl.colors.model.Clients;
import ie.garciapl.colors.service.ColorsAlgorithm;
import ie.garciapl.colors.service.FileReader;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.err.println("No arguments. Exiting");
            System.exit(0);
        }

        if (args.length > 1) {
            System.err.println("Too much arguments. Please provide only file path. Exiting");
            System.exit(0);
        }

        Clients clients = new FileReader().readFile(args[0]);
        ColorsAlgorithm colorsAlgorithm = new ColorsAlgorithm(clients);
        System.out.println(colorsAlgorithm.findColors());
    }

}
