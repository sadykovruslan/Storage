import jdk.jfr.Category;

import java.util.Map;
import java.util.Set;
record CategoryAndPlace (String category, String place){

}

public interface Analytics {
    Set<String> getCategory();
    Map<CategoryAndPlace, Integer> getAgregationByCategoryAndPlace ();
    Integer getAgregationByCategoryAndPlace(CategoryAndPlace categoryAndPlace);
    Integer getTotalCount ();
}
