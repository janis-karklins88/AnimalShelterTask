package org.example.menu;

import org.example.model.Animal;
import org.example.model.AnimalId;
import org.example.model.Bird;
import org.example.model.Cat;
import org.example.model.Dog;
import org.example.shelter.Shelter;

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
            String input = scanner.nextLine();
            int option = Integer.parseInt(input);
            switch (option){
                case 1 -> addAnimalMenu();
                case 2 -> listAllAnimals();
                case 3 -> findAnimalBySpecies();
                case 4 -> listAllAvailableAnimals();
                case 5 -> markAnimalAsAdopted();
                case 0 -> running = false;
                default -> System.out.println("Unknown option");
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
                0. Exit
                """);
    }

    public void addAnimalMenu(){
        System.out.println("Choose animal species: 1 - Bird, 2 - Cat, 3 - Dog");
        String input = scanner.nextLine();
        int option = Integer.parseInt(input);

        if(option != 1 && option != 2 && option != 3){
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

        shelter.markAsAdopted(id);
    }
}
