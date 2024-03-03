package com.example.springtest1.homework.task1;


import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedList;

public class MyArrayList<T>
{
    private final int START_CAPACITY = 10;
    private Object[] elements;
    private int currentSize;
    private int capacity;

    public MyArrayList()
    {
        this.elements = new Object[START_CAPACITY];
        this.currentSize = 0;
        this.capacity = this.elements.length;

        elements = new Object[START_CAPACITY];
    }

    public MyArrayList(T...srcArray)
    {
        this.elements = new Object[srcArray.length << 1];
        this.currentSize = srcArray.length;
        this.capacity = this.elements.length;
        System.arraycopy(srcArray,0,this.elements,0,srcArray.length);
    }

    //add element into tail
    public void add(T newElement)
    {
        if((currentSize + 1) > capacity)
            increaseCapacity(currentSize + 1);
        elements[currentSize] = newElement;
        currentSize++;
    }


    public void add(int index,T...newElements)
    {
        if(newElements == null)
            throw new IllegalArgumentException("Передан массив равный null");

        if(index != currentSize)
            checkIndex(index);

        if((currentSize + newElements.length) > capacity)
            increaseCapacity(currentSize + newElements.length);

        System.arraycopy(elements,index,elements,index + newElements.length,currentSize - index);

        System.arraycopy(newElements,0,elements,index,newElements.length);

        currentSize = currentSize + newElements.length;
    }

    //replace element at index
    public void set(int index, T element)
    {
        checkIndex(index);
        this.elements[index] = element;
    }

    public boolean deleteElement(T element)
    {
        Integer index = null;
        boolean elementIsPresentInArray = false;
        for(int i = 0;i < currentSize;i++)
        {
            if(elements[i].equals(element))
            {
                index = i;
                elementIsPresentInArray = true;
                break;
            }
        }

        if(elementIsPresentInArray)
        {
            deleteElementByIndex(index);
        }

        return elementIsPresentInArray;
    }

    public void deleteElementByIndex(int index) throws ArrayIndexOutOfBoundsException
    {
        checkIndex(index);
        currentSize--;
        //if(!lastIndexIs(index))
        System.arraycopy(this.elements, index + 1,this.elements,index,currentSize - index);
        this.elements[currentSize] = null;
    }

    public T get(int index) throws ArrayIndexOutOfBoundsException
    {
        checkIndex(index);
        return (T) elements[index];
    }

    private boolean lastIndexIs(int index)
    {
        return currentSize == (index + 1);
    }

    private void checkIndex(int index)
    {
        if(this.currentSize <= index || index < 0)
            throw new ArrayIndexOutOfBoundsException(index);
    }

    private void increaseCapacity()
    {
        int newCapacity = this.capacity << 1;
        Object[] newArray = new Object[newCapacity];
        System.arraycopy(this.elements,0,newArray,0,currentSize);
        this.capacity = newCapacity;
    }

    private void increaseCapacity(int newMinCapacity)
    {
        int newCapacity = this.capacity << 1;
        while(newMinCapacity > newCapacity)
            newCapacity = newCapacity << 1;
        Object[] newArray = new Object[newCapacity];
        System.arraycopy(this.elements,0,newArray,0,currentSize);
        this.capacity = newCapacity;
    }

    @Override
    public String toString() {
        return Arrays.toString(Arrays.stream(elements).limit(currentSize).toArray());
    }

    public int getSize()
    {
        return currentSize;
    }
}
