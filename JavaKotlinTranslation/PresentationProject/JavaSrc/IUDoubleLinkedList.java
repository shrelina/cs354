import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class IUDoubleLinkedList<T> implements IndexedUnsortedList<T>{
    private LinearNode<T> head, tail;
    private int size;
    private int modCount;

    public IUDoubleLinkedList(){
        head = tail = null;
        size = 0;
        modCount = 0;
    }

    @Override
    public void addToFront(T element) {
        LinearNode<T> newHead = new LinearNode<T>();
        newHead.setElement(element);
        newHead.setNext(head);
        head = newHead;
        if (head.getNext() == null){
            tail = newHead;
        }
        else{
            head.getNext().setPrevious(head);
        }
        size++;
        modCount++;
    }

    @Override
    public void addToRear(T element) {
        LinearNode<T> newTail = new LinearNode<T>();
        newTail.setElement(element);
        newTail.setPrevious(tail);
        tail = newTail;
        if (tail.getPrevious() == null){
            head = newTail;
        }
        else{
            tail.getPrevious().setNext(tail);
        }
        size++;
        modCount++;

    }

    @Override
    public void add(T element) {
        addToRear(element);
    }

    @Override
    public void addAfter(T element, T target) {
        LinearNode<T> current = head;

        while(current != null && !current.getElement().equals(target)){
            current = current.getNext();
        }

        if (current == null){
            throw new NoSuchElementException();
        }

        LinearNode<T> newNode = new LinearNode<T>();
        newNode.setElement(element);

        newNode.setNext(current.getNext());
        newNode.setPrevious(current);
        current.setNext(newNode);
        if (current == tail){
            tail = newNode;
        }
        else{
            newNode.getNext().setPrevious(newNode);
        }

        modCount++;
        size++;
    }

    @Override
    public void add(int index, T element) {
        if (index < 0 || index > size){
            throw new IndexOutOfBoundsException();
        }

        LinearNode<T> newNode = new LinearNode<T>();
        newNode.setElement(element);
        if (index == 0){ // Case 1: the node to be inserted is to be the new head
            newNode.setNext(head);
            head = newNode;
            if (tail == null){ // Case 1, part 2: the node to be inserted is the only node in the list
                tail = newNode;
            }
            else{ // Case 1, else: set the previous head connects to the new head
                newNode.getNext().setPrevious(newNode);
            }
        }
        else{ // Case 2: the node to be inserted is not to be the head
            LinearNode<T> current = head;
            for (int i = 0; i < index - 1; i++){
                current = current.getNext();
            }

            newNode.setNext(current.getNext());
            newNode.setPrevious(current);

            if (newNode.getNext() == null){
                tail = newNode;
            }
            else{
                newNode.getNext().setPrevious(newNode);
            }

            current.setNext(newNode);
        }

        size++;
        modCount++;
    }

    @Override
    public T removeFirst() {
        if (size == 0){
            throw new NoSuchElementException();
        }

        T retVal = head.getElement();
        head = head.getNext();
        if (head == null){
            tail = null;
        }
        else{
            head.setPrevious(null);
        }

        size--;
        modCount++;

        return retVal;
    }

    @Override
    public T removeLast() {
        if (size == 0){
            throw new NoSuchElementException();
        }

        T retVal = tail.getElement();
        tail = tail.getPrevious();
        if (tail == null){
            head = null;
        }
        else{
            tail.setNext(null);
        }

        size--;
        modCount++;

        return retVal;
    }

    @Override
    public T remove(T element) {
        if(isEmpty()){
            throw new NoSuchElementException();
        }

        LinearNode<T> current = head;

        while (current != null && !current.getElement().equals(element)){
            current = current.getNext();
        }

        if (current == null){
            throw new NoSuchElementException();
        }

        if (size == 1){
            head = tail = null;
        }
        else if (current == head){
            head = current.getNext();
            head.setPrevious(null);
        }
        else if (current == tail){
            tail = current.getPrevious();
            tail.setNext(null);
        }
        else{
            current.getPrevious().setNext(current.getNext());
            current.getNext().setPrevious(current.getPrevious());
        }

        size--;
        modCount++;

        return current.getElement();

    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size){
            throw new IndexOutOfBoundsException();
        }

        LinearNode<T> current = head;

        for (int i = 0; i < index; i++){
            current = current.getNext();
        }

        if (size() == 1){
            head = tail = null;
        }
        else if (current == head){
            head = current.getNext();
            head.setPrevious(null);
        }
        else if (current == tail){
            tail = current.getPrevious();
            tail.setNext(null);
        }
        else{
            current.getPrevious().setNext(current.getNext());
            current.getNext().setPrevious(current.getPrevious());
        }

        size--;
        modCount++;

        return current.getElement();
    }

    @Override
    public void set(int index, T element) {
        if (index < 0 || index >= size){
            throw new IndexOutOfBoundsException();
        }

        LinearNode<T> current = head;
        for (int i = 0; i < index; i++){
            current = current.getNext();
        }

        current.setElement(element);
        modCount++;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size){
            throw new IndexOutOfBoundsException();
        }

        LinearNode<T> current = head;
        for (int i = 0; i < index; i++){
            current = current.getNext();
        }

        return current.getElement();
    }

    @Override
    public int indexOf(T element) {
        if (size() == 0){
            return -1;
        }

        LinearNode<T> current = head;
        int i = 0;
        while (current != null && !current.getElement().equals(element)){
            current = current.getNext();
            i++;
        }

        if (current == null){
            return -1;
        }

        return i;
    }

    @Override
    public T first() {
        if (head == null){
            throw new NoSuchElementException();
        }

        return head.getElement();
    }

    @Override
    public T last() {
        if (tail == null){
            throw new NoSuchElementException();
        }
        return tail.getElement();
    }

    @Override
    public boolean contains(T target) {
        return !(indexOf(target) == -1);
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        return listIterator(0);
    }

    @Override
    public ListIterator<T> listIterator() {
        return listIterator(0);
    }

    @Override
    public ListIterator<T> listIterator(int startingIndex) {
        return new DLLIterator(startingIndex);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        LinearNode<T> current = head;

        sb.append("[");
        for (int i = 0; i < size; i++){
            sb.append(current.getElement().toString());
            if (current != tail){
                sb.append(", ");
            }

            current = current.getNext();
        }

        sb.append("]");

        return sb.toString();
    }

    private class DLLIterator implements ListIterator<T>{
        private LinearNode<T> nextNode;
        private LinearNode<T> lastReturnedNode;
        private int iterModCount;
        private boolean canRemove;
        private boolean canSet;
        private boolean forwardMovement;

        private int nextIndex;

        public DLLIterator(){
            this(0);
        }

        @SuppressWarnings("unchecked")
        public DLLIterator(int startingIndex){
            if (startingIndex < 0 || startingIndex > size){
                throw new IndexOutOfBoundsException();
            }
            iterModCount = modCount;
            nextNode = head;
            nextIndex = 0;
            for (int i = 0; i < startingIndex; i++){
                nextNode = nextNode.getNext();
                nextIndex++;
            }
            lastReturnedNode = null;

            canRemove = false;
            canSet = false;
            forwardMovement = false;
        }

        @Override
        public boolean hasNext() {
            checkModCount();

            return nextNode != null;
        }

        @Override
        public T next() {
            if (!hasNext()){
                throw new NoSuchElementException();
            }

            T retVal = nextNode.getElement();
            lastReturnedNode = nextNode;
            nextNode = nextNode.getNext();
            nextIndex++;
            canRemove = true;
            canSet = true;
            forwardMovement = true;

            return retVal;
        }

        @Override
        public boolean hasPrevious() {
            checkModCount();

            if (nextNode == null){
                LinearNode current = head;
                for (int i = 0; i < nextIndex - 1; i++){
                    current = current.getNext();
                }
                return current != null;

            }
            else {
                return nextNode.getPrevious() != null;
            }
        }

        @Override
        public T previous() {
            if (!hasPrevious()){
                throw new NoSuchElementException();
            }

            T retVal;
            if (nextNode == null){
                LinearNode<T> current = head;
                for (int i = 0; i < nextIndex - 1; i++){
                    current = current.getNext();
                }

                retVal = current.getElement();
                lastReturnedNode = current;
                nextNode = current;
            }
            else {
                retVal = nextNode.getPrevious().getElement();
                lastReturnedNode = nextNode.getPrevious();
                nextNode = nextNode.getPrevious();
            }
            nextIndex--;
            canRemove = true;
            canSet = true;
            forwardMovement = false;

            return retVal;
        }

        @Override
        public int nextIndex() {
            checkModCount();

            return nextIndex;
        }

        @Override
        public int previousIndex() {
            checkModCount();

            return nextIndex - 1;
        }

        @Override
        public void remove() {
            checkModCount();
            if (!canRemove){
                throw new IllegalStateException();
            }
            if (size() == 1){
                head = tail = null;
            }
            else if (lastReturnedNode == head){
                lastReturnedNode.getNext().setPrevious(null);
                head = lastReturnedNode.getNext();
                nextNode = head;
            }
            else if (lastReturnedNode == tail){
                lastReturnedNode.getPrevious().setNext(null);
                tail = lastReturnedNode.getPrevious();
                nextNode = null;
            }
            else{
                lastReturnedNode.getNext().setPrevious(lastReturnedNode.getPrevious());
                lastReturnedNode.getPrevious().setNext(lastReturnedNode.getNext());
                nextNode = lastReturnedNode.getNext();
            }

            lastReturnedNode = null;
            canRemove = false;
            canSet = false;
            size--;
            if (forwardMovement) {
                nextIndex--;
            }
            modCount++;
            iterModCount++;
        }

        @Override
        public void set(T t) {
            checkModCount();
            if (!canSet){
                throw new IllegalStateException();
            }

            if (lastReturnedNode == null){
                IUDoubleLinkedList.this.set(nextIndex, t);
            }
            else {
                lastReturnedNode.setElement(t);
                modCount++;
            }
            iterModCount++;
        }

        @Override
        public void add(T t) {
            checkModCount();

            LinearNode<T> newNode = new LinearNode<T>();
            newNode.setElement(t);
            if (size() == 0){
                head = tail = newNode;
            }
            else{
                // step 1a: check if nextNode is null
                if (nextNode == null){
                    LinearNode<T> current = head;
                    for (int i = 0; i < nextIndex - 1; i++){
                        current = current.getNext();
                    }
                    newNode.setPrevious(current);
                }
                // step 1b: nextNode is not null: connect newNode's previous to nextNode's previous
                else {
                    newNode.setPrevious(nextNode.getPrevious());
                    newNode.setNext(nextNode);
                }

                // step 2a: check if newNode's recently set previous is null; if it is, then newNode is at the beginning of the list
                // set newNode to head if true
                if (newNode.getPrevious() == null){
                    head = newNode;
                }
                // step 2b: sever the link between newNode's, previous node's, next node, then reconnect that next node to newNode
                else{
                    newNode.getPrevious().setNext(newNode);
                }

                // step 3a: check if newNode's recently set next is null; if it is, then newNode is at the end of the list
                // set newNode to tail if true
                if (newNode.getNext() == null){
                    tail = newNode;
                }
                // step 3b: sever the link between newNode's, next node's, previous node, then reconnect that previous node to newNode
                else{
                    nextNode.setPrevious(newNode);
                }
            }

            size++;
            nextIndex++;
            modCount++;
            iterModCount++;
            canRemove = false;
            canSet = false;
        }

        private void checkModCount(){
            if (iterModCount != modCount){
                throw new ConcurrentModificationException();
            }
        }
    }
}
