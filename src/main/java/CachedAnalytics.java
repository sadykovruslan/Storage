import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class CachedAnalytics implements Analytics{
    private final Map<CategoryAndPlace, Integer> cache = new LRUCache<>(2);
    private final BasicAnalytics basicAnalytics;

    public CachedAnalytics(BasicAnalytics basicAnalytics) {

        this.basicAnalytics = basicAnalytics;
    }

    @Override
    public Set<String> getCategory() {
        return basicAnalytics.getCategory();
    }

    @Override
    public Map<CategoryAndPlace, Integer> getAgregationByCategoryAndPlace() {
        return basicAnalytics.getAgregationByCategoryAndPlace();
    }

    @Override
    public Integer getAgregationByCategoryAndPlace(CategoryAndPlace categoryAndPlace) {
        Integer integer = cache.get(categoryAndPlace);
        if (integer==null){
            integer = basicAnalytics.getAgregationByCategoryAndPlace(categoryAndPlace);
        }
        return integer;
//        return cache.computeIfPresent(categoryAndPlace, basicAnalytics::getAgregationByCategoryAndPlace);
    }

    @Override
    public Integer getTotalCount() {
        return basicAnalytics.getTotalCount();
    }
}

class LRUCache <KEY, VALUE> extends LinkedHashMap<KEY, VALUE>{
    private final int capacity;
    public LRUCache(int capacity) {
        super (capacity, 0.75f, true);
        this.capacity = capacity;
    }
    protected boolean removeEldestEntry (Map.Entry<KEY, VALUE> eldest){
        return size() > capacity;
    }

}
