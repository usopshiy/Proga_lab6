package data;

import java.io.Serializable;

public class Coordinates implements Serializable {
    private final float x; //Максимальное значение поля: 162
    private final Integer y; //Максимальное значение поля: 57, Поле не может быть null

    public Coordinates(Float x, Integer y){
        this.x = x;
        this.y = y;
    }
    public  boolean validate(){
        return (y!=null && x<=162 && y<=57);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
