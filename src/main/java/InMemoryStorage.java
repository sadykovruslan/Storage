import exceptions.ItemNotFoundException;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class InMemoryStorage implements Storage{

    private final Map <String, Wheel> items = new HashMap<>();
    @Override
    public void putItem(Wheel wheel) {
        items.put(wheel.id(), wheel);

    }

    @Override
    public Wheel getItem(String id) throws ItemNotFoundException {
        Wheel wheel = items.get(id);
        if (wheel == null) {
            throw new ItemNotFoundException(id);
        }
        return wheel;
    }

    @Override
    public boolean containsItem(String id) {
//        Wheel wheel1 = new Wheel("1", "Hakkapelita", "winter", "A", 10);
//        Wheel wheel2 = new Wheel("2", "Hakkapelita", "winter", "A", 10);
//        Wheel wheel3 = new Wheel("3", "Hakkapelita", "summer", "A", 10);
        return items.containsKey(id);

    }

    @Override
    public Wheel removeItem(String id) throws ItemNotFoundException {
        Wheel remove = items.remove(id);
        if (remove == null) {
            throw new ItemNotFoundException(id);
        }
        return remove;
    }

    @Override
    public void putAllItems(List<Wheel> items) {
        for (Wheel item: items) {
            putItem(item);
        }
    }

    @Override
    public Map<String, Wheel> getAllItems() {
        return new HashMap<>(items);
    }

    @Override
    public List<Wheel> getAllItemsSorted(Predicate<Wheel> predicate) {
        return items.values().stream().filter(predicate)
                .sorted(Comparator.comparing(Wheel::model)
                        .thenComparing(Wheel::category)
                        .thenComparing(Wheel::place)
                        .thenComparing(Wheel::id))
                 .collect(Collectors.toList());
    }
}
