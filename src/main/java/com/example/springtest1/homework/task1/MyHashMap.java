package com.example.springtest1.homework.task1;

import java.util.*;
import java.util.stream.Collectors;

public class MyHashMap<K,V>
{
    private final static float LOAD_FACTOR_DEFAULT = 0.7f;
    private final static int CAPACITY_DEFAULT = 10;
    private static class MyEntry<K,V>
    {
        private K key;
        private V value;

        public MyEntry(K key, V value)
        {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return String.format("{%s : %s}", key.toString(),value.toString());
        }
    }
    private LinkedList<MyEntry<K,V>>[] data;
    private int currentSize;
    private int capacity;

    private int countUseBacket;
    private float loadFactor;

    //private Set<MyEntry<K,V>> dataSet = new HashSet<>();

    public MyHashMap()
    {
        this.capacity = CAPACITY_DEFAULT;
        this.loadFactor = LOAD_FACTOR_DEFAULT;
        this.currentSize = 0;
        this.countUseBacket = 0;
        this.data = new LinkedList[capacity];
        for(int i = 0;i < data.length;i++)
        {
            data[i] = new LinkedList<>();
        }
    }

    public void put(K key, V value)
    {
        if((countUseBacket / Double.valueOf(capacity)) > loadFactor)
        {
            increaseCapacity();
        }

        int targetBacket = key.hashCode() % capacity;
        LinkedList<MyEntry<K,V>> backet = this.data[targetBacket];
        MyEntry<K,V> myEntry = new MyEntry<>(key,value);
        boolean elementWithSameKeyIsPresent = false;
        if(backet.isEmpty())
        {
            backet.add(myEntry);
            this.countUseBacket++;
        }
        else
        {
            for(var element : backet)
            {
                if(element.key.hashCode() == key.hashCode() && element.key.equals(key))
                {
                    element.setValue(value);
                    elementWithSameKeyIsPresent = true;
                    break;
                }
            }
            if(!elementWithSameKeyIsPresent)
            {
                backet.add(myEntry);
            }
        }
        if(!elementWithSameKeyIsPresent)
            this.currentSize++;
    }

    public boolean remove(K key)
    {
        int targetBacket = key.hashCode() % capacity;
        LinkedList<MyEntry<K,V>> backet = this.data[targetBacket];
        boolean elementIsRemoved = false;
        Iterator<MyEntry<K,V>> iter = backet.iterator();
        while(iter.hasNext())
        {
            var element = iter.next();
            if(element.key.hashCode() == key.hashCode() && element.key.equals(key))
            {
                iter.remove();
                elementIsRemoved = true;
                break;
            }
        }
        if(elementIsRemoved)
            this.currentSize--;
        if(backet.isEmpty())
            this.countUseBacket--;
        return elementIsRemoved;
    }

    public V get(K key)
    {
        int targetBacket = key.hashCode() % capacity;
        LinkedList<MyEntry<K,V>> backet = this.data[targetBacket];
        Iterator<MyEntry<K,V>> iter = backet.iterator();
        V value = null;
        while(iter.hasNext())
        {
            var element = iter.next();
            if(element.key.hashCode() == key.hashCode() && element.key.equals(key))
            {
                value = element.value;
                break;
            }
        }
        return value;
    }

    private void increaseCapacity()
    {
        LinkedList<MyEntry<K,V>>[] previousDataMap = this.data;

        this.data = new LinkedList[capacity << 1];
        for(int i = 0;i < data.length;i++)
        {
            data[i] = new LinkedList<>();
        }
        this.capacity = this.data.length;
        this.currentSize = 0;
        this.countUseBacket = 0;

        restructuredMap(previousDataMap);
    }

    private void restructuredMap(LinkedList<MyEntry<K,V>>[] previousDataMap)
    {
        for(var element : previousDataMap)
        {
            for(var innerElement : element)
            {
                put(innerElement.key,innerElement.value);
            }
        }
    }

    @Override
    public String toString() {
        return Arrays.stream(data).flatMap((backet) -> backet.stream()).collect(Collectors.toSet()).toString();
    }
}
