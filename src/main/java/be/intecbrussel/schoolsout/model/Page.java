package be.intecbrussel.schoolsout.model;

import java.util.Collections;
import java.util.List;

public interface Page<T> {

    /**
     * returns a view (not a new list) of the sourceList for the range based on page and pageSize
     * @param sourceList
     * @param page, page number should start from 1
     * @param pageSize
     * @return
     */
    static <T> List<T> of(List<T> sourceList, int page, int pageSize) {
        if(pageSize <= 0 || page <= 0) {
            throw new IllegalArgumentException("invalid page size: " + pageSize);
        }

        int fromIndex = (page - 1) * pageSize;
        if(sourceList == null || sourceList.size() < fromIndex){
            return Collections.emptyList();
        }

        // toIndex exclusive
        return sourceList.subList(fromIndex, Math.min(fromIndex + pageSize, sourceList.size()));
    }
}
