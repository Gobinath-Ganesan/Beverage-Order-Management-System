package de.uniba.dsg.jaxrs.Controller;

import de.uniba.dsg.jaxrs.Exceptions.DataNotFoundExceptions;
import de.uniba.dsg.jaxrs.db.DB;
import de.uniba.dsg.jaxrs.model.logic.Bottle;

import java.util.*;
import java.util.stream.Collectors;

public class BottleService {

    DB fromDB = new DB();

    public Bottle getByID(int id){

        boolean toContinue = true;
        Bottle bottle = null;
        // Getting an iterator
        Iterator bottleIterator = DB.bottleInfo.entrySet().iterator();

        // Iterate through the hashmap
        while (bottleIterator.hasNext() && toContinue ) {
            Map.Entry mapElement = (Map.Entry)bottleIterator.next();
            if(mapElement.getKey().equals(id)){
                toContinue = false;
                bottle = (Bottle) mapElement.getValue();
                return bottle;

            }
        }
        return  bottle;
        //return  (Bottle) listBottle.stream().filter(bottle -> bottle.getId() == id).findFirst().orElse(null);
        //return  (listBottle.stream().filter(bottle -> bottle.getId() == id).collect(Collectors.toList())).get(0);

//        System.out.println("___________________");
//        System.out.println(bottle1.getName());
//        System.out.println("adding bottle");
//        bottleToSend.add(0,bottle1);
//        System.out.println(bottleToSend.size());
//        if(){
//            System.out.println("Inside if "+bottleToSend.size());
//            throw  new DataNotFoundExceptions("Bottle id not found");
//        }
//
//        else{
//            System.out.println("Inside else "+bottleToSend.size());
//            return bottleToSend;
//        }

    }

    //public List<Bottle> getByMaxPrice() {
    public List<Bottle> getByMaxPrice() {
        List<Bottle> listBottleToReturn = new ArrayList<>();

        // Getting an iterator
        Iterator bottleIterator = DB.bottleInfo.entrySet().iterator();

        // Iterate through the hashmap
        while (bottleIterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry)bottleIterator.next();
            listBottleToReturn.add((Bottle)(mapElement.getValue()));
        }

        //Sort the bottle
        listBottleToReturn.sort((Bottle obj1, Bottle obj2)-> Double.compare(obj2.getPrice(),obj1.getPrice()));

        //int ind = listBottle.size();
        Bottle bot = listBottleToReturn.get(0);
        Double price = bot.getPrice();

        //returns the list of bottles which has the same max price
        return listBottleToReturn.stream().filter(obj-> obj.getPrice() == price).collect(Collectors.toList());
        //return listBottle.get(0);
    }

    public List<Bottle> getByMinPrice() {
        List<Bottle> listBottleToReturn = new ArrayList<>();

        // Getting an iterator
        Iterator bottleIterator = DB.bottleInfo.entrySet().iterator();

        // Iterate through the hashmap
        while (bottleIterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry)bottleIterator.next();
            listBottleToReturn.add((Bottle)(mapElement.getValue()));
        }

        listBottleToReturn.sort((Bottle obj1, Bottle obj2)-> Double.compare(obj2.getPrice(),obj1.getPrice()));
        int ind = listBottleToReturn.size()-1;
        Bottle bot = listBottleToReturn.get(ind);
        Double price = bot.getPrice();
        return listBottleToReturn.stream().filter(obj-> obj.getPrice() == price).collect(Collectors.toList());
    }

    public List<Bottle> getAllBottles(){
        List<Bottle> listBottleToReturn = new ArrayList<>();

        // Getting an iterator
        Iterator bottleIterator = DB.bottleInfo.entrySet().iterator();

        // Iterate through the hashmap
        while (bottleIterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry)bottleIterator.next();
            listBottleToReturn.add((Bottle)(mapElement.getValue()));
        }
        return listBottleToReturn;
    }

    public List<Bottle> getAllBottlesPaginated(int argStart,int argEnd){
        List<Bottle> listBottle = fromDB.getBottles();
        if(argStart+argEnd <= listBottle.size())
            return  listBottle.subList(argStart,argStart+argEnd);
        return listBottle;
    }

    public Bottle addBottle(Bottle bottle){
        //List<Bottle> listBottle = fromDB.getBottles();
        List<Bottle> listBottleToReturn = new ArrayList<>();

        // Getting an iterator
        Iterator bottleIterator = DB.bottleInfo.entrySet().iterator();

        // Iterate through the hashmap
        while (bottleIterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry)bottleIterator.next();
            listBottleToReturn.add((Bottle)(mapElement.getValue()));
        }

        int id = fromDB.bootleId(listBottleToReturn);
        bottle.setId(id);
        DB.bottleInfo.put(bottle.getId(),bottle);
        //listBottle.add(bottle);
        //Collections.reverse(listBottle);
        return bottle;
    }

    public boolean removeBottle(int id){

        boolean isBottlePresent = false;

        if(DB.bottleInfo.containsKey(id)){
            isBottlePresent = DB.bottleInfo.remove(id,getByID(id));

        }
//        // Getting an iterator
//        Iterator bottleIterator = DB.bottleInfo.entrySet().iterator();
//
//        // Iterate through the hashmap
//        while (bottleIterator.hasNext() && !(isBottlePresent)) {
//            Map.Entry mapElement = (Map.Entry)bottleIterator.next();
//            if(mapElement.getKey().equals(id)){
//                isBottlePresent = true;
//                DB.bottleInfo.remove(mapElement.getKey(),mapElement.getValue());
//                DB.bottleInfo.
//                break;
//            }
//        }

        return isBottlePresent;
    }

    public Bottle updateBottle(int id, Bottle bottleToUpdate){
        Bottle retrivedBottle = getByID(id);

        if(retrivedBottle == null){
            return null;
        }

        Optional.of(bottleToUpdate.getId()).ifPresent(retrivedBottle::setId);
        Optional.of(bottleToUpdate.getName()).ifPresent(retrivedBottle::setName);
        Optional.of(bottleToUpdate.getVolume()).ifPresent(retrivedBottle::setVolume);
        Optional.of(bottleToUpdate.isAlcoholic()).ifPresent(retrivedBottle::setAlcoholic);
        Optional.of(bottleToUpdate.getVolumePercent()).ifPresent(retrivedBottle::setVolumePercent);
        Optional.of(bottleToUpdate.getPrice()).ifPresent(retrivedBottle::setPrice);
        Optional.of(bottleToUpdate.getSupplier()).ifPresent(retrivedBottle::setSupplier);
        Optional.of(bottleToUpdate.getInStock()).ifPresent(retrivedBottle::setInStock);
        return retrivedBottle;

    }
}
