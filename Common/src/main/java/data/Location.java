package data;

import java.io.Serializable;
import java.util.Objects;

public class Location implements Serializable {
    private final Integer x; //Поле не может быть null
    private final Double y; //Поле не может быть null

    private final Long z; //Поле не может быть null
    private final String name; //Длина строки не должна быть больше 457, Поле не может быть null

    public Location(Integer x, Double y, Long z, String name){
        this.x =x;
        this.y = y;
        this.z = z;
        this.name = name;
    }
    public boolean validate(){
        return (x!=null && y!=null && z!=null && name!=null && name.length()<=457);
    }

    @Override
    public String toString() {
        return (this.name +"(" + x.toString() + ", " + y.toString() + ", " + z.toString() + ")");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Objects.equals(x, location.x) && Objects.equals(y, location.y) && Objects.equals(z, location.z) && Objects.equals(name, location.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z, name);
    }
}
