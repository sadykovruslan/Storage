import exceptions.ItemNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

class CachedAnalyticsTest {

    private final MockStorage storage = new MockStorage();

    private final Analytics analytics = new CachedAnalytics(new BasicAnalytics(storage));

    @Test
    void callOnceForRepeatRequests() {
        CategoryAndPlace request = new CategoryAndPlace("winter", "A");
        Integer agregationByCategoryAndPlace = analytics.getAgregationByCategoryAndPlace(request);

        Assertions.assertEquals(10, agregationByCategoryAndPlace);

        analytics.getAgregationByCategoryAndPlace(request);
        analytics.getAgregationByCategoryAndPlace(request);
        analytics.getAgregationByCategoryAndPlace(request);

        Assertions.assertEquals(4 , storage.calls);


    }

    @Test
    void callManyTimesForRepeatRequests() {
        CategoryAndPlace request1 = new CategoryAndPlace("winter", "A");
        CategoryAndPlace request2 = new CategoryAndPlace("winter", "B");
        CategoryAndPlace request3 = new CategoryAndPlace("summer", "A");



        analytics.getAgregationByCategoryAndPlace(request1);
        analytics.getAgregationByCategoryAndPlace(request2);
        analytics.getAgregationByCategoryAndPlace(request1);

        Assertions.assertEquals(2 , storage.calls);


    }

    private class MockStorage implements Storage {
        private int calls = 0;

        @Override
        public void putItem(Wheel wheel) {

        }

        @Override
        public Wheel getItem(String id) throws ItemNotFoundException {
            return null;
        }

        @Override
        public boolean containsItem(String id) {
            return false;
        }

        @Override
        public Wheel removeItem(String id) throws ItemNotFoundException {
            return null;
        }

        @Override
        public void putAllItems(List<Wheel> items) {

        }

        @Override
        public Map<String, Wheel> getAllItems() {
            calls++;
            Wheel wheel = new Wheel("1", "Hakkapelita", "summer", "A", 5);
            Wheel winter = new Wheel("2", "Hakkapelita", "winter", "A", 5);
            Wheel winter2 = new Wheel("21", "Hakkapelita", "winter", "A", 5);
            Wheel winter3 = new Wheel("22", "Hakkapelita", "winter", "B", 5);
            Wheel allSeasons = new Wheel("3", "Hakkapelita", "allSeasons", "A", 5);
            return Map.of(wheel.id(), wheel, winter.id(), winter, winter2.id(), winter2, winter3.id(), winter3, allSeasons.id(), allSeasons);
        }

        @Override
        public List<Wheel> getAllItemsSorted(Predicate<Wheel> predicate) {
            return null;
        }
    }
}

