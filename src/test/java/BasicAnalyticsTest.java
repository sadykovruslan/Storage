import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BasicAnalyticsTest {
    private Analytics analytics;
    private Storage storage;
    @BeforeEach
    void setUp(){
        storage = new InMemoryStorage();
        analytics = new BasicAnalytics(storage);
    }

    @Test
    void getCategory() {
        Wheel wheel = new Wheel("1", "Hakkapelita", "summer", "A", 5);
        Wheel winter = new Wheel("2", "Hakkapelita", "winter", "A", 5);
        Wheel winter2 = new Wheel("21", "Hakkapelita", "winter", "A", 5);
        Wheel winter3 = new Wheel("22", "Hakkapelita", "winter", "A", 5);
        Wheel allSeasons = new Wheel("3", "Hakkapelita", "allSeasons", "A", 5);
        storage.putAllItems(List.of(wheel, winter, winter2, winter3, allSeasons));

        Set<String> categories = analytics.getCategory();
        Assertions.assertEquals(3, categories.size());
        Assertions.assertTrue(categories.contains("summer"));
        Assertions.assertTrue(categories.contains("winter"));
        Assertions.assertTrue(categories.contains("allSeasons"));
    }

    @Test
    void getAgregationByCategoryAndPlaceSingleReauest() {
        Wheel wheel = new Wheel("1", "Hakkapelita", "summer", "A", 5);
        Wheel winter = new Wheel("2", "Hakkapelita", "winter", "A", 5);
        Wheel winter2 = new Wheel("21", "Hakkapelita", "winter", "A", 5);
        Wheel winter3 = new Wheel("22", "Hakkapelita", "winter", "B", 5);
        Wheel allSeasons = new Wheel("3", "Hakkapelita", "allSeasons", "A", 5);
        storage.putAllItems(List.of(wheel, winter, winter2, winter3, allSeasons));

      Integer quantity = analytics.getAgregationByCategoryAndPlace(new CategoryAndPlace("winter", "A"));

        Assertions.assertEquals(10, quantity);
    }

    @Test
    void getTotalCount() {
        Wheel wheel = new Wheel("1", "Hakkapelita", "summer", "A", 5);
        Wheel winter = new Wheel("2", "Hakkapelita", "winter", "A", 5);
        Wheel winter2 = new Wheel("21", "Hakkapelita", "winter", "A", 5);
        Wheel winter3 = new Wheel("22", "Hakkapelita", "winter", "A", 5);
        Wheel allSeasons = new Wheel("3", "Hakkapelita", "allSeasons", "A", 5);
        storage.putAllItems(List.of(wheel, winter, winter2, winter3, allSeasons));

        Integer totalCount = analytics.getTotalCount();

        Assertions.assertEquals(25, totalCount);
    }
}