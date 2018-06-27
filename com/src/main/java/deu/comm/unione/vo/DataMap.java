package deu.comm.unione.vo;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import org.apache.commons.collections.FastHashMap;

public class DataMap<K, V> implements Map {
	FastHashMap map;

	public DataMap() {
		this.map = new FastHashMap();
	}

	public void clear() {
		this.map.clear();
	}

	public boolean containsKey(Object key) {
		return this.map.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return this.map.containsValue(value);
	}

	public Set entrySet() {
		return this.map.entrySet();
	}

	public Object get(Object key) {
		return this.map.get(key);
	}

	public Object get() {
		return this.map.keySet();
	}

	public boolean isEmpty() {
		return this.map.isEmpty();
	}

	public Set keySet() {
		return this.map.keySet();
	}

	public Object put(Object key, Object value) {
		if (value == null) {
			value = "";
		}

		return this.map.put(key.toString().toLowerCase(), value);
	}

	public Object putNoLowercase(Object key, Object value) {
		if (value == null) {
			value = "";
		}
		return this.map.put(key.toString(), value);
	}

	public void putAll(Map map) {
		map.putAll(map);
	}

	public Object remove(Object key) {
		return this.map.remove(key);
	}

	public int size() {
		return this.map.size();
	}

	public Collection values() {
		return this.map.values();
	}
}