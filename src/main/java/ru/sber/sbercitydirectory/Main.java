package ru.sber.sbercitydirectory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<City> cities= new ArrayList<>();
        try (FileReader fileReader = new FileReader(Objects.requireNonNull(Main.class.getResource("/cityDirectory.csv")).getFile());
             Scanner scanner = new Scanner(new BufferedReader(fileReader))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String name = "";
                String region = "";
                String district = "";
                int population = 0;
                String foundation = "";
                String[] split = line.split(";");
                for (int i = 0; i < split.length; i++) {
                    switch (i) {
                        case 1 -> name = split[i];
                        case 2 -> region = split[i];
                        case 3 -> district = split[i];
                        case 4 -> population = Integer.parseInt(split[i]);
                        case 5 -> foundation = split[i];
                    }
                }
                cities.add(new City(name, region, district, population, foundation));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        cities.forEach(System.out::println);
    }
}
