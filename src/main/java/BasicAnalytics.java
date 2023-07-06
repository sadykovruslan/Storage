import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class BasicAnalytics implements Analytics{
    private Storage storage;

    public BasicAnalytics(Storage storage) {
        this.storage = storage;
    }

    @Override
    public Set<String> getCategory() {
        Set<String> categories = new HashSet<>();
        for(Wheel wheel: storage.getAllItems().values()) {
            categories.add(wheel.category());
        }
        return categories;
    }

    @Override
    public Map<CategoryAndPlace, Integer> getAgregationByCategoryAndPlace () {

        Map<CategoryAndPlace, Integer> agregations = new HashMap<>();
        Map<String, Wheel> storedItems = storage.getAllItems();
        for (Wheel wheel: storedItems.values()){
           CategoryAndPlace categoryAndPlace = new CategoryAndPlace(wheel.category(), wheel.place());
           Integer agregation = agregations.getOrDefault(categoryAndPlace,0);
           if (agregation==null){
               agregation = 0;
           }
           agregation += wheel.quantity();
           agregations.put(categoryAndPlace,agregation);
        }
        return agregations;
    }

    @Override
    public Integer getAgregationByCategoryAndPlace(CategoryAndPlace categoryAndPlace) {
        Integer quantity = 0;
        Map<String, Wheel> storedItems = storage.getAllItems();
        for (Wheel wheel: storedItems.values()){
            if (wheel.category().equals(categoryAndPlace.category()) && wheel.place().equals(categoryAndPlace.place()) ) {
                quantity += wheel.quantity();
            }
        }
        return quantity;
    }

    @Override
    public Integer getTotalCount() {
        return storage.getAllItems().values().stream().map(Wheel::quantity).mapToInt(Integer::intValue).sum();
    }
}
