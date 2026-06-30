package org.example.menu;

import org.example.model.AdoptionRecord;
import org.example.model.Animal;
import org.example.model.AnimalId;
import org.example.model.Bird;
import org.example.model.Cat;
import org.example.model.Dog;
import org.example.model.Rabbit;
import org.example.shelter.Shelter;
import org.example.util.AnimalUtils;

import java.util.Map;
import java.util.Scanner;

public class ConsoleMenu {
    private final Shelter<Animal> shelter;
    private final Scanner scanner =  new Scanner(System.in);
    public ConsoleMenu(Shelter<Animal> shelter) {
        this.shelter = shelter;
    }

    public void start(){
        boolean running = true;

        while(running) {
            printMenu();
            System.out.println("Choose option: ");
            Integer option = readInteger();
            if(option == null){
                System.out.println("Option must be a number.");
                continue;
            }

            switch (option){
                case 1 -> addAnimalMenu();
                case 2 -> listAllAnimals();
                case 3 -> findAnimalBySpecies();
                case 4 -> listAllAvailableAnimals();
                case 5 -> markAnimalAsAdopted();
                case 6 -> listAnimalsSortedByName();
                case 7 -> listAnimalsSortedByAge();
                case 8 -> listAdoptionHistory();
                case 9 -> showAverageAnimalAge();
                case 10 -> showOldestAnimal();
                case 11 -> showAnimalCountBySpecies();
                case 0 -> {
                    System.out.println("Goodbye!");
                    running = false;
                }
                default -> System.out.println("Unknown option. Choose a number from 0 to 11.");
            }

        }
    }

    private void printMenu(){
        System.out.println("""
                1. Add animal
                2. List all animals
                3. Find animals by species
                4. List available animals
                5. Mark animal as adopted
                6. List animals sorted by name
                7. List animals sorted by age
                8. List adoption history
                9. Show average animal age
                10. Show oldest animal
                11. Show animal count by species
                0. Exit
                """);
    }

    public void addAnimalMenu(){
        System.out.println("Choose animal species: 1 - Bird, 2 - Cat, 3 - Dog, 4 - Rabbit");
        Integer option = readInteger();
        if(option == null){
            System.out.println("Species option must be a number.");
            return;
        }

        if(option < 1 || option > 4){
            System.out.println("Incorrect species!");
            return;
        }

        System.out.println("Enter name: ");
        String name = scanner.nextLine();
        if(name.isBlank()){
            System.out.println("Name cannot be empty.");
            return;
        }

        System.out.println("Enter age: ");
        int age;
        try {
            age = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Age must be a number.");
            return;
        }
        if(age <= 0){
            System.out.println("Age must be a positive number.");
            return;
        }

        Animal animal = switch (option) {
            case 1 -> new Bird(new AnimalId(), name, age);
            case 2 -> new Cat(new AnimalId(), name, age);
            case 3 -> new Dog(new AnimalId(), name, age);
            case 4 -> new Rabbit(new AnimalId(), name, age);
            default -> throw new IllegalStateException("Unexpected value: " + option);
        };

        shelter.addAnimal(animal);
        System.out.println("Animal added: " + animal);
    }

    private void listAllAnimals(){
        for(Animal animal : shelter.getAllAnimals()){
            System.out.println(animal);
        }
    }

    private void findAnimalBySpecies(){
        System.out.println("Enter species: ");
        String species = scanner.nextLine();
        if (species.isBlank()) {
            System.out.println("Species cannot be empty.");
            return;
        }
        for(Animal animal : shelter.findBySpecies(species)){
            System.out.println(animal);
        }
    }

    private void listAllAvailableAnimals(){
        for(Animal animal : shelter.findAvailableAnimals()){
            System.out.println(animal);
        }
    }

    private void markAnimalAsAdopted(){
        System.out.println("Enter animal id: ");
        String id = scanner.nextLine();
        if (id.isBlank()) {
            System.out.println("Id cannot be empty.");
            return;
        }

        Animal animal = shelter.findById(id);
        if(animal == null){
            System.out.println("Animal not found.");
            return;
        }

        System.out.println("Enter adopter name: ");
        String adopterName = scanner.nextLine();
        if(adopterName.isBlank()){
            System.out.println("Adopter name cannot be empty.");
            return;
        }

        shelter.markAsAdopted(id, adopterName);
        System.out.println("Animal adopted: " + animal);
    }

    private void listAnimalsSortedByName(){
        for(Animal animal : shelter.sortByName()){
            System.out.println(animal);
        }
    }

    private void listAnimalsSortedByAge(){
        for(Animal animal : shelter.sortByAge()){
            System.out.println(animal);
        }
    }

    private void listAdoptionHistory(){
        for(AdoptionRecord record : shelter.getAdoptionHistory()){
            System.out.println(record.animal() + " | Adopted on " + record.adoptionDate() + " | Adopter: " + record.adopterName());
        }
    }

    private void showAverageAnimalAge(){
        double averageAge = AnimalUtils.averageAnimalAge(shelter.getAllAnimals());
        System.out.println("Average animal age: " + averageAge);
    }

    private void showOldestAnimal(){
        Animal oldestAnimal = AnimalUtils.oldestAnimal(shelter.getAllAnimals());
        if(oldestAnimal == null){
            System.out.println("No animals found.");
            return;
        }

        System.out.println("Oldest animal: " + oldestAnimal);
    }

    private void showAnimalCountBySpecies(){
        Map<String, Integer> speciesCount = AnimalUtils.countAnimalsBySpecies(shelter.getAllAnimals());

        for(Map.Entry<String, Integer> entry : speciesCount.entrySet()){
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    private Integer readInteger(){
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
