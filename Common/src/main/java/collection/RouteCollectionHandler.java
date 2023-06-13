package collection;

import com.google.gson.*;
import com.google.gson.reflect.*;
import commands.ExceptionWrapper;
import data.Location;
import data.Route;
import exceptions.InvalidDateFormatException;
import json.*;
import utils.DateConverter;

import java.util.*;
import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * class for dealing with collection
 */
public class RouteCollectionHandler {
    private LinkedHashSet<Route> collection;
    private final HashSet<UUID> uniqueIds;
    private java.time.LocalDateTime initDate;

    /**
     * constructor for class
     */
    public RouteCollectionHandler(){
        collection = new LinkedHashSet<>();
        uniqueIds = new HashSet<>();
        initDate = java.time.LocalDateTime.now();
    }

    /**
     * method for getting collection from json file
     * @param json
     */
    public void deserializeCollection(String json){
        try {
            if (json == null || json.equals("")){
                collection =  new LinkedHashSet<>();
            } else {
                Pattern pattern = Pattern.compile("\"inittime\": \".{23}\",");
                Matcher matcher = pattern.matcher(json);
                if(matcher.find()){
                    initDate = DateConverter.parseLocalDateTime(json
                            .substring(matcher.start(), matcher.end())
                            .substring(13, 36));
                }
                pattern = Pattern.compile("\"collection\": ");
                matcher = pattern.matcher(json);
                if(matcher.find()){
                    json = json.trim().substring(matcher.end(), json.length() - 2);
                }
                Type collectionType = new TypeToken<LinkedHashSet<Route>>(){}.getType();
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(Date.class, new DateDeserializer())
                        .registerTypeAdapter(collectionType, new CollectionDeserializer(uniqueIds))
                        .registerTypeAdapter(java.time.LocalDate.class, new LocalDateDeserializer())
                .create();
                collection = gson.fromJson(json, collectionType);
            }
        } catch (JsonParseException | InvalidDateFormatException e){
            ExceptionWrapper.outException(e.getMessage());
        }

    }

    /**
     * method for serializing collection into json
     * @return String
     */
    public String serializeCollection(){
        if(collection == null || collection.isEmpty()){
            return "";
        }
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(java.time.LocalDate.class, new LocalDateSerializer())
                .registerTypeAdapter(Date.class, new DateSerializer())
                .setPrettyPrinting()
                .create();
        String json = gson.toJson(collection);
        json = "{\"inittime\": \"" + DateConverter.dateToString(initDate) +
                "\",\n\t\"collection\": " + json + "\n}";
        return json;
    }

    /**
     * clears collection
     */
    public String clear(){
        collection.clear();
        uniqueIds.clear();
        return ("Cleared collection");
    }

    /**
     * sorts collection via RouteComparator
     */
    public void sort(){
        ArrayList<Route> list = new ArrayList<>(collection);
        list.sort(new RouteComparator());
        collection = new LinkedHashSet<>(list);
    }

    /**
     * adds element to collection
     * @param route
     */
    public String add(Route route){
        uniqueIds.add(route.getId());
        collection.add(route);
        return "added element: " + route;
    }

    /**
     * method for checking if given ID is present in collection
     * @param id
     * @return boolean
     */
    public boolean checkID(UUID id){
        for(Route route : collection){
            if(route.getId().equals(id)){
                return false;
            }
        }
        return true;
    }

    /**
     * method for validating input id string
     * @param id
     * @return boolean
     */
    public boolean validateID(String id){
        if (id == null || id.length() != 36){
            return false;
        }
        try {
           UUID uuid = UUID.fromString(id);
        }
        catch (IllegalArgumentException e){
            return false;
        }
        return true;
    }
    /**
     * removes object from collection with given id
     * @param id
     */
    public String removeByID(UUID id){
        for(Route route : collection){
            if(route.getId().equals(id)){
                collection.remove(route);
                uniqueIds.remove(id);
                return "successfully removed route with id: " + id;
            }
        }
        return "";
    }

    /**
     * updates object with given id in collection with fields of given object
     * @param id
     * @param route
     */
    public String updateByID(UUID id, Route route){
        for(Route cRoute : collection){
            if(cRoute.getId().equals(id)){
                collection.remove(cRoute);
                collection.add(route);
                return "successfully updated route with id: " + id;
            }
        }
        return "";
    }

    /**
     * Groups objects by field "from" and outputs it into standard out
     */
    public String groupCountingByFrom(){
        HashMap<Location, AtomicInteger> map = new HashMap<>();
        StringBuilder ans = new StringBuilder();
        for (Route route : collection){
            Location from = route.getFrom();
            if (map.containsKey(from)){
                map.get(from).incrementAndGet();
            } else{
                map.put(from, new AtomicInteger(1));
            }
        }
        for (Map.Entry<Location, AtomicInteger> pair : map.entrySet()) {
            Location location = pair.getKey();
            int quantity = map.get(location).intValue();
            ans.append(location).append(": ").append(quantity).append('\n');
        }
        return ans.toString();
    }

    /**
     * gives info about collection
     */
    public String info(){
        return "Collection type: LinkedHashSet of Routes\n Creation date: " + DateConverter.dateToString(initDate) + "\nElements in collection: " + collection.size();
    }

    /**
     * getter for collection
     * @return LinkedHashSet
     */
    public LinkedHashSet<Route> getCollection() {
        return collection;
    }
}
