package json;

import com.google.gson.*;
import data.Route;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.UUID;

/**
 * Custom deserializer for LinkedHashSet of routes for gson library
 */
public class CollectionDeserializer implements JsonDeserializer<LinkedHashSet<Route>> {
    private final HashSet<UUID> uniqueIds;

    public CollectionDeserializer(HashSet<UUID> uniqueIds) {
        this.uniqueIds = uniqueIds;
    }

    @Override
    public LinkedHashSet<Route> deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        LinkedHashSet<Route> collection = new LinkedHashSet<>();
        JsonArray routes = json.getAsJsonArray();
        int damagedElements = 0;
        for (JsonElement jsonRoute : routes) {
            try {
                if (jsonRoute.getAsJsonObject().entrySet().isEmpty()) {
                    System.err.print("found empty route object");
                    throw new JsonParseException("empty route object");
                }
                if (!jsonRoute.getAsJsonObject().has("id")) {
                    System.err.print("found route without id");
                    throw new JsonParseException("route has no id");
                }
                Route route = context.deserialize(jsonRoute, Route.class);
                UUID routeId = route.getId();

                if (uniqueIds.contains(routeId)) {
                    System.err.println("route with id #" + routeId + " already exist in collection");
                    throw new JsonParseException("id isn't unique");
                }

                if (!route.validate()) {
                    System.err.println("route with id #" + routeId + " doesn't natch specific conditions");
                    throw new JsonParseException("doesn't met specific conditions");
                }
                uniqueIds.add(routeId);
                collection.add(route);
            } catch (JsonParseException e) {
                damagedElements++;
            }
        }
        if (collection.size() == 0) {
            if (damagedElements == 0) System.err.print("database is empty");
            else System.err.print("all elements of database are damaged");
            throw new JsonParseException("no valid data");
        }
        if (damagedElements != 0) System.out.print(damagedElements + " elements are damaged");
        return collection;
    }
}

