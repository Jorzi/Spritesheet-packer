/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import spritesheetpacker.Quad;

/**
 *
 * @author Maconi
 */
public class MergeSort {

    
    public static Quad[] mergeSort(Quad[] array){
        Quad[] output = new Quad[array.length];
        if (array.length == 2){
            if (array[0].compareTo(array[1]) > 0){
                output[0] = array[0];
                output[1] = array[1];
            }else{
                output[0] = array[1];
                output[1] = array[0];
            }
            return output;
        }
        if(array.length == 1){
            output[0] = array[0];
            return output;
        }
        Quad[] subArray1 = new Quad[array.length/2]; //round down
        System.arraycopy(array, 0, subArray1, 0, subArray1.length);
        Quad[] subArray2 = new Quad[array.length - subArray1.length]; //round up
        System.arraycopy(array, array.length/2, subArray2, 0, subArray2.length);
        subArray1 = mergeSort(subArray1);
        subArray2 = mergeSort(subArray2);
        int i = 0;
        int j = 0;
        for (int k = 0; k < array.length; k++){
            if (i < subArray1.length && j < subArray2.length){
                if(subArray2[j].compareTo(subArray1[i]) > 0){
                    output[k]= subArray2[j++];
                }else{
                    output[k]= subArray1[i++];
                }
                
            }else if(j==subArray2.length){
                output[k]= subArray1[i++];
            }else if(i==subArray1.length){
                output[k]= subArray2[j++];
            }
        }
        return output;
    }
}