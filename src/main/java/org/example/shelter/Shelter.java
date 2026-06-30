package org.example.shelter;

import org.example.model.AdoptionStatus;
import org.example.model.AdoptionRecord;
import org.example.model.Animal;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Shelter <T extends Animal>{
    private final List<T> animals = new ArrayList<>();
    private final List<AdoptionRecord> adoptionHistory = new ArrayList<>();

    public void addAnimal(T animal){
        animals.add(animal);
    }

    public List<T> getAllAnimals(){
        return animals;
    }

    public List<T> findBySpecies(String species){
        List<T> speciesList = new ArrayList<>();
        for(T animal : this.animals){
            if(animal.getSpecies().equalsIgnoreCase(species)){
                speciesList.add(animal);
            }
        }
        return speciesList;
    }

    public List<T> findAvailableAnimals(){
        List<T> availableAnimals = new ArrayList<>();
        for(T animal : this.animals){
            if(animal.getAdoptionStatus() == AdoptionStatus.AVAILABLE){
                availableAnimals.add(animal);
            }
        }
        return availableAnimals;
    }

    public List<T> sortByName(){
        List<T> sortedAnimals = new ArrayList<>(animals);
        sortedAnimals.sort((animal1, animal2) -> animal1.getName().compareToIgnoreCase(animal2.getName()));
        return sortedAnimals;
    }

    public List<T> sortByAge(){
        List<T> sortedAnimals = new ArrayList<>(animals);
        sortedAnimals.sort((animal1, animal2) -> Integer.compare(animal1.getAge(), animal2.getAge()));
        return sortedAnimals;
    }

    public T findById(String id){
        for(T animal : this.animals){
            String animalId = animal.getId().toString();
            if(animalId.equalsIgnoreCase(id)){
                return animal;
            }
        }

        return null;
    }

    public List<AdoptionRecord> getAdoptionHistory(){
        return new ArrayList<>(adoptionHistory);
    }

    public void markAsAdopted(String id, String adopterName){
        T animal = findById(id);
        if(animal != null && animal.getAdoptionStatus() == AdoptionStatus.AVAILABLE){
            animal.markAsAdopted();
            adoptionHistory.add(new AdoptionRecord(animal, LocalDate.now(), adopterName));
        }
    }
}
