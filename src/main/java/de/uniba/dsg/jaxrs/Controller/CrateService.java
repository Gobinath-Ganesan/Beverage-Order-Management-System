package de.uniba.dsg.jaxrs.Controller;

import de.uniba.dsg.jaxrs.db.DB;
import de.uniba.dsg.jaxrs.model.logic.Bottle;
import de.uniba.dsg.jaxrs.model.logic.Crate;

import java.util.*;
import java.util.stream.Collectors;

public class CrateService {

    DB fromDB = new DB();

//    public Object getByID(int id) {
//        List<Crate> listCrate;
//        listCrate = fromDB.getCrates();
//        //Crate tex = (listCrate.stream().filter(crate -> crate.getId() == id).collect(Collectors.toList())).get(0);
//        return (listCrate.stream().filter(crate -> crate.getId() == id).collect(Collectors.toList())).get(0);
//        //return tex;
   // }

    public Crate getByID(int id) {

        boolean toContinue = true;
        Crate crate = null;
        // Getting an iterator
        Iterator crateIterator = DB.crateInfo.entrySet().iterator();

        // Iterate through the hashmap
        while (crateIterator.hasNext() && toContinue ) {
            Map.Entry mapElement = (Map.Entry)crateIterator.next();
            if(mapElement.getKey().equals(id)){
                toContinue = false;
                crate = (Crate) mapElement.getValue();
                return crate;

            }
        }
        return  crate;

        //List<Crate> listCrate = fromDB.getCrates();
        //return (Crate) listCrate.stream().filter(crate -> crate.getId() == id).findFirst().orElse(null);
        //return (listCrate.stream().filter(crate -> crate.getId() == id).collect(Collectors.toList())).get(0);
    }

    public List<Crate> getByMaxPrice() {
        List<Crate> listCrate = new ArrayList<>();

        // Getting an iterator
        Iterator bottleIterator = DB.crateInfo.entrySet().iterator();

        // Iterate through the hashmap
        while (bottleIterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry)bottleIterator.next();
            listCrate.add((Crate)(mapElement.getValue()));
        }

        listCrate.sort((Crate obj1, Crate obj2)-> Double.compare(obj2.getPrice(),obj1.getPrice()));
        //int ind = listBottle.size();
        Crate crate = listCrate.get(0);
        Double price = crate.getPrice();
        return listCrate.stream().filter(obj-> obj.getPrice() == price).collect(Collectors.toList());
    }


    public List<Crate> getByMinPrice() {
        List<Crate> listCrate = new ArrayList<>();

        // Getting an iterator
        Iterator bottleIterator = DB.crateInfo.entrySet().iterator();

        // Iterate through the hashmap
        while (bottleIterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry)bottleIterator.next();
            listCrate.add((Crate)(mapElement.getValue()));
        }

        listCrate.sort((Crate obj1, Crate obj2)-> Double.compare(obj2.getPrice(),obj1.getPrice()));
        int ind = listCrate.size()-1;
        Crate crate = listCrate.get(ind);
        Double price = crate.getPrice();
        return listCrate.stream().filter(obj-> obj.getPrice() == price).collect(Collectors.toList());
    }

    public List<Crate> getAllCrates(){
        List<Crate> listCrates = new ArrayList<>();
        // Getting an iterator
        Iterator bottleIterator = DB.crateInfo.entrySet().iterator();

        // Iterate through the hashmap
        while (bottleIterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry)bottleIterator.next();
            listCrates.add((Crate)(mapElement.getValue()));
        }

        return listCrates;
    }

    public List<Crate> getAllCratesPaginated(int argStart,int argEnd){
        List<Crate> listCrate = new ArrayList<>();
        // Getting an iterator
        Iterator bottleIterator = DB.crateInfo.entrySet().iterator();

        // Iterate through the hashmap
        while (bottleIterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry)bottleIterator.next();
            listCrate.add((Crate)(mapElement.getValue()));
        }
        if(argStart+argEnd <= listCrate.size())
            return  listCrate.subList(argStart,argStart+argEnd);
        return listCrate;
    }

    public Crate addCrate(Crate crate){
        List<Crate> listCrate = new ArrayList<>();

        // Getting an iterator
        Iterator bottleIterator = DB.crateInfo.entrySet().iterator();

        // Iterate through the hashmap
        while (bottleIterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry)bottleIterator.next();
            listCrate.add((Crate)(mapElement.getValue()));
        }

        int id = fromDB.crateId(listCrate);
        crate.setID(id);

        DB.crateInfo.put(crate.getId(),crate);

        //listCrate.add(crate);
        //Collections.reverse(listCrate);
        return crate;
    }

    public boolean removeCrate(int id){
        boolean isCratePresent = false;

        // Getting an iterator
        Iterator crateIterator = DB.crateInfo.entrySet().iterator();

        // Iterate through the hashmap
        while (crateIterator.hasNext() && !(isCratePresent)) {
            Map.Entry mapElement = (Map.Entry)crateIterator.next();
            if(mapElement.getKey().equals(id)){
                isCratePresent = true;
                DB.crateInfo.remove(mapElement.getKey(),mapElement.getValue());
                break;
            }
        }

        return isCratePresent;
    }

    public Crate updateCrate(int id, Crate crateToUpdate){

        Crate retrivedCrate = getByID(id);

        if(retrivedCrate == null){
            return null;
        }
        Optional.of(crateToUpdate.getId()).ifPresent(retrivedCrate::setID);
        Optional.of(crateToUpdate.getBottle()).ifPresent(retrivedCrate::setBottle);
        Optional.of(crateToUpdate.getNoOfBottles()).ifPresent(retrivedCrate::setNoOfBottles);
        Optional.of(crateToUpdate.getPrice()).ifPresent(retrivedCrate::setPrice);
        Optional.of(crateToUpdate.getInStock()).ifPresent(retrivedCrate::setInStock);
        return retrivedCrate;

    }
}
