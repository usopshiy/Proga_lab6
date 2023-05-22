package data;

import java.io.Serializable;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

public class Route implements Comparable<Route>, Serializable {
    private UUID id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private final String name; //Поле не может быть null, Строка не может быть пустой
    private final Coordinates coordinates; //Поле не может быть null
    private java.util.Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private final Location from; //Поле не может быть null
    private final Location to; //Поле не может быть null
    private final Double distance; //Поле может быть null, Значение поля должно быть больше 1

    public Route(String name, Coordinates coord, Location from, Location to, Double distance){
        this.id = UUID.randomUUID();
        this.name = name;
        this.coordinates = coord;
        this.creationDate = Date.from(java.time.LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        this.from = from;
        this.to = to;
        this.distance = distance;
    }
    public UUID getId() {
        return id;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setId(UUID id){
        this.id = id;
    }

    public Double getDistance(){
        return distance;
    }

    public Location getFrom(){
        return this.from;
    }
    public boolean validate(){
        return (coordinates!=null && coordinates.validate() && creationDate!=null && from!=null && from.validate() && to!=null && to.validate() && distance!=null && distance>1);
    }

    @Override
    public String toString() {
        return "id: " + id +
                ",\n name: " + name +
                ",\n coordinates: " + coordinates +
                ",\n creationDate: " + creationDate +
                ",\n from: " + from +
                ",\n to: " + to +
                ",\n distance: " + distance;
    }

    @Override
    public int compareTo(Route route) {
        Double comparable1 = 0.0;
        Double comparable2 = 0.0;
        if (this.distance != null){comparable1 = this.distance;}
        if (route.distance != null){comparable2 = route.distance;}
        if(comparable1 > comparable2) return 1;
        if(comparable1.equals(comparable2)) return 0;
        else return  -1;
    }
}
