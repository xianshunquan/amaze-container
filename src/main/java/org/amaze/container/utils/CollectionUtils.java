package org.amaze.container.utils;

import java.util.HashSet;
import java.util.Set;

/**
 * @author : shunqxian
 * @date : 2019-10-05
 */
public class CollectionUtils {
    public static <T> Set<T> toSet(T[] array) {
        if (array == null) {
            return null;
        }
        Set<T> set = new HashSet<>();
        for (T obj : array) {
            set.add(obj);
        }
        return set;
    }
}
