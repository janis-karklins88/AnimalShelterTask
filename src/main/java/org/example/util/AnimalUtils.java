package org.example.util;

import org.example.model.Animal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class AnimalUtils {
    private AnimalUtils(){}

    public static double averageAnimalAge(List<? extends Animal> animals) {
        if (animals.isEmpty()) {
            return 0;
        }

        int totalAge = 0;
        for (Animal animal : animals) {
            totalAge += animal.getAge();
        }

        return (double) totalAge / animals.size();
    }

    public static Animal oldestAnimal(List<? extends Animal> animals) {
        if (animals.isEmpty()) {
            return null;
        }

        Animal oldestAnimal = animals.get(0);
        for (Animal animal : animals) {
            if (animal.getAge() > oldestAnimal.getAge()) {
                oldestAnimal = animal;
            }
        }

        return oldestAnimal;
    }

    public static Map<String, Integer> countAnimalsBySpecies(List<? extends Animal> animals) {
        Map<String, Integer> speciesCount = new HashMap<>();

        for (Animal animal : animals) {
            String species = animal.getSpecies();
            speciesCount.put(species, speciesCount.getOrDefault(species, 0) + 1);
        }

        return speciesCount;
    }
}
