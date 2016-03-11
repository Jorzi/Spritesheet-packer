/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author Maconi
 */
public class CustomArrayList<T> {
    
    private T[] array;
    public int size;
    private static final int defaultsize = 8;

    public CustomArrayList() {
        size = 0;
        array = (T[]) new Object[defaultsize];
    }
    
    
}
