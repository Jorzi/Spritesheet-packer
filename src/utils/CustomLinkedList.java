/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 * Minimalist implementation of a linked data structure
 *
 * @author Maconi
 */
public class CustomLinkedList<T> {

    private class Node<T> {

        T data;
        Node<T> next;
        Node<T> prev;

        public Node(T data) {
            this.data = data;
            this.next = null;
            this.prev = null;
        }

    }

    private Node<T> first;
    private Node<T> active;
    private Node<T> last;
    public int size;

    public CustomLinkedList() {
        first = null;
        active = null;
        last = null;
        size = 0;
    }
    

    public void addLast(T item) {
        Node<T> node = new Node<>(item);
        if (size == 0) {
            first = node;
            active = node;
            last = node;
            size = 1;
        } else {
            node.prev = last;
            last.next = node;
            last = node;
            size++;
        }
    }

    public void addFirst(T item) {
        Node<T> node = new Node<>(item);
        if (size == 0) {
            first = node;
            active = node;
            last = node;
            size = 1;
        } else {
            node.next = first;
            first.prev = node;
            first = node;
            size++;
        }
    }

    public void insertNext(T item) {
        Node<T> node = new Node<>(item);
        if (size == 0) {
            first = node;
            active = node;
            last = node;
            size = 1;
        } else {
            node.next = active.next;
            node.prev = active;
            active.next.prev = node;
            active.next = node;
            size++;
        }
    }

    public void removeActive() {
        if(size > 0){
            active.prev.next = active.next;
            active.next.prev = active.prev;
            active = active.next;
            size--;
        }
    }

    public T getFirst() {
        return first.data;
    }

    public T getLast() {
        return last.data;
    }

    public T getActive() {
        return active.data;
    }

    public T getNext() {
        active = active.next;
        return active.data;
    }

    public void goToFirst() {
        active = first;
    }

    public void goToLast() {
        active = last;
    }

    public void goToNext() {
        active = active.next;
    }
    
    public T[] toArray(){
        Object[] array = new Object[size];
        Node<T> node = this.first;
        for (int i = 0; i < size; i++){
            array[i] = node.data;
            node = node.next;
        }
        return (T[])array;
    }
}
