package ru.sber.sbercitydirectory;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<City> cities = new ArrayList<>();
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
        System.out.println("Список городов:");
        cities.forEach(System.out::println);

        System.out.println("Список городов, отсортированные по наименованию в алфавитном порядке по убыванию без учета регистра:");
        cities.stream().sorted((o1, o2) -> o2.getName().toLowerCase().compareTo(o1.getName().toLowerCase())).toList().forEach(System.out::println);

        System.out.println("по федеральному округу и наименованию города внутри каждого федерального округа в алфавитном порядке по убыванию с учетом регистра:");
        cities.stream().sorted((o1, o2) -> !Objects.equals(o2.getDistrict(), o1.getDistrict()) ?
                o2.getDistrict().compareTo(o1.getDistrict()) :
                o2.getName().compareTo(o1.getName())).toList().forEach(System.out::println);

        printPopulation(cities);

    }

    private static void printPopulation(List<City> citiesList) {
        City[] cities = new City[citiesList.size()];
        for (int i = 0; i < citiesList.size(); i++) {
            cities[i] = citiesList.get(i);
        }
        int id = 0;
        int countPopulation = 0;
        for (int i = 1; i < cities.length; i++) {
            if (cities[i].getPopulation() > countPopulation) {
                countPopulation = cities[i].getPopulation();
                id = i;
            }
        }
        System.out.println("Индекс элемента и значение с наибольшим количеством жителей города");
        System.out.println("[" + id + "] = " + countPopulation);
    }
}
