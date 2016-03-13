/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 * Minimalist implementation of a doubly linked data structure.
 *
 * @author Maconi
 * @param <T>
 */
public class CustomLinkedStructure<T> {

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

    /**
     * Number of elements in the "list"
     */
    public int size;

    /**
     * Create a new empty structure
     */
    public CustomLinkedStructure() {
        first = null;
        active = null;
        last = null;
        size = 0;
    }

    /**
     * Add an element to the end of the chain
     *
     * @param item
     */
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

    /**
     * Add an element to the start of the chain
     *
     * @param item
     */
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

    /**
     * Insert an element between the active node and the next.
     *
     * @param item
     */
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
            if (active.next != null) {
                active.next.prev = node;
            } else {
                last = node;
            }
            active.next = node;
            size++;
        }
    }

    private void removeNode(Node<T> node) {
        if (size > 0) {
            if (node.prev != null) {
                node.prev.next = node.next;
            }
            if (node.next != null) {
                node.next.prev = node.prev;
            }
            if (first == node) {
                first = node.next;
            }
            if (last == node) {
                last = node.prev;
            }
            size--;
        }
    }

    /**
     * Remove the active element and move one step forward
     */
    public void removeActive() {
        if (size > 0) {
            removeNode(active);
            active = active.next;
        }
    }

    /**
     * Searches for the first occurrence of the specified element and removes it
     * from the chain if found.
     *
     * @param item
     */
    public void remove(T item) {
        Node<T> node = this.first;
        for (int i = 0; i < this.size; i++) {
            if (node.data == item) {
                removeNode(node);
                return;
            }
            node = node.next;
        }
    }

    /**
     * Get the first element in the chain
     *
     * @return
     */
    public T getFirst() {
        if (first != null) {
            return first.data;
        }
        return null;
    }

    /**
     * Get the last element in the chain
     *
     * @return
     */
    public T getLast() {
        if (last != null) {
            return last.data;
        }
        return null;
    }

    /**
     * Get the active element
     *
     * @return
     */
    public T getActive() {
        if (active != null) {
            return active.data;
        }
        return null;
    }

    /**
     * Move to the element next to the active one and return it
     *
     * @return
     */
    public T getNext() {
        active = active.next;
        if (active != null) {
            return active.data;
        }
        return null;
    }

    /**
     * Move the "cursor" to the start, making it the active node
     */
    public void goToFirst() {
        active = first;
    }

    /**
     * Move the "cursor" to the end, making it the active node
     */
    public void goToLast() {
        active = last;
    }

    /**
     * Move the "cursor" to the next node, making it the active node
     */
    public void goToNext() {
        active = active.next;
    }

    /**
     * Return all elements in the structure as an array.
     *
     * @return A new array object, containing all the elements of the structure.
     */
    public Object[] toArray() {
        Object[] array = new Object[size];
        Node<T> node = this.first;
        for (int i = 0; i < size; i++) {
            array[i] = node.data;
            node = node.next;
        }
        return array;
    }
}
