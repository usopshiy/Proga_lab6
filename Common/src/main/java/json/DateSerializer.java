package json;

import java.lang.reflect.Type;
import java.util.Date;
import com.google.gson.*;
import static utils.DateConverter.*;

/**
 * Custom serializer for Date for gson library
 */
public class DateSerializer implements JsonSerializer<Date>{
    @Override
    public JsonElement serialize(Date Date, Type srcType, JsonSerializationContext context) {
        return new JsonPrimitive(dateToString(Date));
    }
}