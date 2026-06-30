package org.example.shelter;

import org.example.model.AdoptionStatus;
import org.example.model.Animal;

import java.util.ArrayList;
import java.util.List;

public class Shelter <T extends Animal>{
    private final List<T> animals = new ArrayList<>();

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

    public void markAsAdopted(String id){
        for(T animal : this.animals){
            String animalId = animal.getId().toString();
            if(animalId.equalsIgnoreCase(id)){
                animal.markAsAdopted();
                break;
            }
        }
    }
}
