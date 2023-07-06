import exceptions.ItemNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

class InMemoryStorageTest {
    private Storage storage;

    @BeforeEach
    void setUp(){
        storage = new InMemoryStorage();
    }

    @Test
    void putAndGetItem() throws ItemNotFoundException {
        Wheel wheel = new Wheel(UUID.randomUUID().toString(), "Hakkapelita", "winter", "A", 10);
        storage.putItem(wheel);
        Wheel actual = storage.getItem(wheel.id());

        Assertions.assertEquals(wheel, actual);
    }

    @Test
    void getItem() throws ItemNotFoundException {
        Assertions.assertThrows(ItemNotFoundException.class, () -> storage.getItem("123"));
    }

    @Test
    void containsItem() {
        Wheel wheel1 = new Wheel("1", "Hakkapelita", "winter", "A", 10);
        Wheel wheel2 = new Wheel("2", "Hakkapelita", "winter", "A", 10);
        Wheel wheel3 = new Wheel("3", "Hakkapelita", "summer", "A", 10);

        storage.putItem(wheel1);
//        storage.putItem(wheel2);
        storage.putItem(wheel3);

        Assertions.assertTrue(storage.containsItem("1"));
        Assertions.assertTrue(storage.containsItem("3"));
        Assertions.assertFalse(storage.containsItem("2"));
    }

    @Test
    void removeItem() throws ItemNotFoundException {
        Wheel wheel1 = new Wheel("1", "Hakkapelita", "winter", "A", 10);
        Wheel wheel3 = new Wheel("3", "Hakkapelita", "summer", "A", 10);

        storage.putItem(wheel1);
        storage.putItem(wheel3);

        Wheel removeItem = storage.removeItem("1");

        Assertions.assertTrue(storage.containsItem("3"));
        Assertions.assertFalse(storage.containsItem("1"));
        Assertions.assertEquals(removeItem, wheel1);

        Assertions.assertThrows(ItemNotFoundException.class, () -> storage.removeItem("2"));
    }

    @Test
    void addListOfItems() {
        Wheel wheel1 = new Wheel("1", "Hakkapelita", "winter", "A", 10);
        Wheel wheel2 = new Wheel("2", "Hakkapelita", "winter", "B", 10);
        Wheel wheel3 = new Wheel("3", "Hakkapelita", "summer", "A", 10);

        storage.putAllItems(List.of(wheel1,wheel2,wheel3));

        Assertions.assertTrue(storage.containsItem("3"));
        Assertions.assertTrue(storage.containsItem("2"));
        Assertions.assertTrue(storage.containsItem("1"));
    }

    @Test
    void getAll() {
        Wheel hakka = new Wheel(UUID.randomUUID().toString(), "Hakkapelita", "winter", "A", 10);
        Wheel michelin = new Wheel(UUID.randomUUID().toString(), "Michelin", "winter", "B", 10);
        Wheel hakkaSummer = new Wheel(UUID.randomUUID().toString(), "Hakkapelita", "summer", "A", 10);
        Wheel nordman = new Wheel(UUID.randomUUID().toString(), "Nordman", "winter", "A", 10);
        Wheel noname = new Wheel(UUID.randomUUID().toString(), "noname", "winter", "A", 10);

        storage.putAllItems(List.of(hakka, michelin, hakkaSummer, nordman, noname));

        Map<String, Wheel> allItems = storage.getAllItems();
        Assertions.assertEquals(5, allItems.size());
        allItems.put("1", new Wheel("1", "Hakkapelita", "winter", "A", 10 ));
        Assertions.assertEquals(5, storage.getAllItems().size());

    }

    @Test
    void getAllSortedByModel() {
        Wheel hakka = new Wheel("1", "Hakkapelita", "winter", "A", 10);
        Wheel michelin = new Wheel("2", "Michelin", "winter", "B", 10);
        Wheel hakkaSummer = new Wheel("3", "Hakkapelita", "summer", "A", 10);
        Wheel nordman = new Wheel("4", "Nordman", "winter", "A", 10);
        Wheel noname = new Wheel("5", "noname", "winter", "A", 10);
        List<Wheel> items = List.of(hakkaSummer, hakka, michelin, noname, nordman);
        storage.putAllItems(items);

        Set<String> expectedModels = new HashSet<>(List.of("Hakkapelita", "Michelin", "noname"));
        List<Wheel> allItemsSorted = storage.getAllItemsSorted(wheel -> expectedModels.contains(wheel.model()));
        List<Wheel> sortedOrder = List.of(hakkaSummer, hakka, michelin, noname);

        Assertions.assertEquals(4, allItemsSorted.size());
        Assertions.assertEquals(sortedOrder, allItemsSorted);

    }
}