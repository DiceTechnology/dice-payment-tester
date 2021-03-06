/*
 *
 *  * Copyright (C) 2018 - present by Dice Technology Ltd.
 *  *
 *  * Please see distribution for license.
 *
 */

package technology.dice.paymenttester.stripe.util;

import java.util.List;
import java.util.Map;

public class CollectionUtil {

	public static boolean isEmpty(String val) {
		return val == null || val.isEmpty();
	}

	public static boolean isNotEmpty(String val) {
		return !isEmpty(val);
	}

	public static <K, V> boolean isEmpty(Map<K, V> map) {
		return map == null || map.isEmpty();
	}

	public static <T> boolean isEmpty(List<T> list) {
		return list == null || list.isEmpty();
	}

	public static <K, V> boolean isNotEmpty(Map<K, V> map) {
		return !isEmpty(map);
	}

	public static <T> boolean isNotEmpty(List<T> list) {
		return !isEmpty(list);
	}
}
