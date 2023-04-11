public class ArrayQueue<E> implements QueueADT<E> {

    private static int DEFAULT_CAPACITY = 100;
    private int front, rear, count;
    private E[] queue;


    public ArrayQueue() {
        this(DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public ArrayQueue(int capacity) {
        front = rear = count = 0;
        queue = (E[])(new Object[capacity]);
    }

    public void enqueue(E element) {
        if (!(count < queue.length)) {this.expandArray();}
        queue[rear] = element;
        rear = increment(rear);
        count++;
    }

    public E dequeue() {
        if (isEmpty()) {throw new EmptyQueueException("ArrayQueue");}
        E element;
        element = queue[front];
        queue[front] = null;
        front = increment(front);
        count--;
        return element;
    }

    public E first() {
        return queue[front];
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return count;
    }

    public String toString() {
        int index = front;
        String result = "";
        for (int i = 0; i < count; i++) {
            result += queue[index];
            index++;
        }
        return result;
    }

    private int increment(int index) {
        index = index + 1 % queue.length;
        return index;
    }

    @SuppressWarnings("unchecked")
    private void expandArray() {
        E[] larger = (E[])(new Object[queue.length * 2]);

        for (int i = 0; i < size(); i++) {
            larger[i] = queue[i];
        }
        front = 0;
        rear = count;
        queue = larger;
    }
}