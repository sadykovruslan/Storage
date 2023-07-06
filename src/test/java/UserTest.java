import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

public class UserTest {

    private final Storage storage = new InMemoryStorage();
    @Test
    void getAllModelsSorted(){
        Wheel wheel1 = new Wheel("1", "model1", "winter", "A", 10);
        Wheel wheel2 = new Wheel("2", "model2", "winter", "A", 10);
        Wheel wheel3 = new Wheel("3", "model3", "winter", "A", 10);
        Wheel wheel4 = new Wheel("4", "model2", "summer", "A", 10);
        Wheel wheel5 = new Wheel("5", "model3", "summer", "B", 10);
        List<String> allModelsSorted = List.of("model1","model2", "model3");
        storage.putAllItems(List.of(wheel1,wheel2,wheel3,wheel4,wheel5));

        Map<String, Wheel> allItems = storage.getAllItems();

        Set<String> sortedOrNot = allItems.values().stream()
                .map(Wheel::model)
                .sorted()
                .collect(Collectors.toCollection(LinkedHashSet::new));


        Assertions.assertEquals(allModelsSorted, List.copyOf(sortedOrNot));

    }
}
