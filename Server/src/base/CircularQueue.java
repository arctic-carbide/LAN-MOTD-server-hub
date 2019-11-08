package base;

import java.util.concurrent.LinkedBlockingQueue;

public class CircularQueue <Type> extends LinkedBlockingQueue<Type> {

    public CircularQueue(int size) {
        super(size);
    }

    public Type next() {
        Type t = super.poll(); // get the next element and remove it from the queue
        super.offer(t); // append that element to the back of the queue, if you can
        return t; // return the reference that element
    }

}
