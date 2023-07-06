import exceptions.ItemNotFoundException;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public interface Storage {
    void putItem (Wheel wheel);
    Wheel getItem (String id) throws ItemNotFoundException;
    boolean containsItem (String id);
    Wheel removeItem(String id) throws ItemNotFoundException;

    void putAllItems(List<Wheel> items);

    Map<String,Wheel> getAllItems();
    List<Wheel> getAllItemsSorted(Predicate<Wheel> predicate);
}
