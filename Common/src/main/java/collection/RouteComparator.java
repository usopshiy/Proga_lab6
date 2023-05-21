package collection;

import data.Route;

import java.util.Comparator;

/**
 * comparator for Routes
 */
public class RouteComparator implements Comparator<Route> {
    @Override
    public int compare(Route route, Route t1) {
        return route.compareTo(t1);
    }
}
