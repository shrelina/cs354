import java.lang.IndexOutOfBoundsException
import java.lang.StringBuilder

class IUDoubleLinkedList<T> (var head: LinearNode<T>?, var tail: LinearNode<T>?, var size: Int = 0,
                             var modCount: Int = 0) : IndexedUnsortedList<T>{
    override fun addToFront(element: T) {
        var newHead = LinearNode<T>(null, null, null)
        newHead.setNodeElement(element)
        newHead.setNextNode(head)
        head = newHead

        if (head!!.getNextNode() == null){
            tail = newHead
        }
        else{
            head!!.getNextNode()!!.setPreviousNode(newHead)
        }

        size++
        modCount++
    }

    override fun addToRear(element: T) {
        var newTail = LinearNode<T>(null, null, null)
        newTail.setNodeElement(element)
        newTail.setPreviousNode(tail)
        tail = newTail

        if (tail!!.getPreviousNode() == null){
            head = newTail
        }
        else{
            tail!!.getPreviousNode()!!.setNextNode(tail)
        }

        size++
        modCount++
    }

    override fun add(element: T) {
        addToRear(element)
    }

    override fun addAfter(element: T, target: T) {
        var current = head

        while(current != null && !current.getNodeElement()!!.equals(target)){
            current = current.getNextNode()
        }

        current ?: throw NoSuchElementException()

        var newNode = LinearNode<T>(null, null, null)
        newNode.setNodeElement(element)

        newNode.setNextNode(current.getNextNode())
        newNode.setPreviousNode(current)
        current.setNextNode(newNode)

        if (current == tail){
            tail = newNode
        }
        else{
            newNode.getNextNode()!!.setPreviousNode(newNode)
        }


    }

    override fun add(index: Int, element: T) {
        if (index < 0 || index > size){
            throw IndexOutOfBoundsException()
        }

        var newNode = LinearNode<T>(null, null, null)
        newNode.setNodeElement(element)

        if (index == 0){
            newNode.setNextNode(head)
            head = newNode

            if (tail == null){
                tail = newNode
            }
            else{
                newNode.getNextNode()?.setPreviousNode(newNode)
            }
        }
        else{
            var current = head
            for (i in 0..index){
                current = current?.getNextNode()
            }

            newNode.setNextNode(current?.getNextNode())
            newNode.setPreviousNode(current)

            if (newNode.getNextNode() == null){
                tail = newNode
            }
            else{
                newNode.getNextNode()!!.setPreviousNode(newNode)
            }

            current?.setNextNode(newNode)
        }

        size++
        modCount++
    }

    override fun removeFirst(): T? {
        if (size == 0){
            throw NoSuchElementException()
        }

        var retVal = head?.getNodeElement()
        head = head?.getNextNode()
        if (head == null){
            tail = null
        }
        else{
            head?.setPreviousNode(null)
        }

        size--
        modCount++

        return retVal
    }

    override fun removeLast(): T? {
        if (size == 0){
            throw NoSuchElementException()
        }

        var retVal = tail?.getNodeElement()
        tail = tail?.getPreviousNode()

        if (tail == null){
            head = null
        }
        else{
            tail?.setNextNode(null)
        }

        size--
        modCount++

        return retVal
    }

    override fun remove(element: T): T? {
        if (isEmpty()){
            throw NoSuchElementException()
        }

        var current = head

        while (current != null && !current.getNodeElement()!!.equals(element)){
            current = current.getNextNode()
        }

        current ?: throw NoSuchElementException()

        if (size == 1){
            head = null
            tail = null
        }
        else if (current == head){
            head = current.getNextNode()
            head?.setPreviousNode(null)
        }
        else if (current == tail){
            tail = current.getPreviousNode()
            tail?.setNextNode(null)
        }
        else{
            current.getPreviousNode()?.setNextNode(current.getNextNode())
            current.getNextNode()?.setPreviousNode(current.getPreviousNode())
        }

        size--
        modCount++

        return current.getNodeElement()
    }

    override fun remove(index: Int): T? {
        if (index < 0 || index >= size){
            throw IndexOutOfBoundsException()
        }

        var current = head

        for (i in 0..index){
            current = current?.getNextNode()
        }

        if (size() == 1){
            head = null
            tail = null
        }
        else if (current == head){
            head = current?.getNextNode()
            head?.setPreviousNode(null)
        }
        else if (current == tail){
            tail = current?.getPreviousNode()
            tail?.setNextNode(null)
        }
        else{
            current?.getPreviousNode()?.setNextNode(current.getNextNode())
            current?.getNextNode()?.setPreviousNode(current.getPreviousNode())
        }

        size--
        modCount++

        return current?.getNodeElement()
    }

    override fun set(index: Int, element: T) {
        if (index < 0 || index >= size){
            throw IndexOutOfBoundsException()
        }

        var current = head
        for (i in 0..index){
            current = current?.getNextNode()
        }

        current?.setNodeElement(element)
        modCount++
    }

    override fun get(index: Int): T? {
        if (index < 0 || index >= size){
            throw NoSuchElementException()
        }

        var current = head
        for (i in 0..index){
            current = current?.getNextNode()
        }

        return current?.getNodeElement()
    }

    override fun indexOf(element: T): Int {
        if (size == 0){
            return -1
        }

        var current = head
        var i = 0

        while (current != null && !current.getNodeElement()!!.equals(element)){
            current = current.getNextNode()
            i++
        }

        if (current == null){
            return -1
        }

        return i
    }

    override fun first(): T? {
        head ?: throw NoSuchElementException()

        return head?.getNodeElement()
    }

    override fun last(): T? {
        tail ?: throw NoSuchElementException()

        return tail?.getNodeElement()
    }

    override fun contains(target: T): Boolean {
        return (indexOf(target) != -1)
    }

    override fun isEmpty(): Boolean {
        return size == 0
    }

    override fun size(): Int {
        return size
    }

    override fun toString(): String {
        var sb = StringBuilder()
        var current = head

        sb.append("[")
        for (i in 0..size){
            sb.append(current?.getNodeElement().toString())
            if (current != tail){
                sb.append(", ")
            }

            current = current?.getNextNode()
        }

        sb.append("]")

        return sb.toString()
    }

    override fun iterator(): Iterator<T> {
        @Suppress("UNCHECKED_CAST")
        return listIterator(0) as Iterator<T>
    }

    override fun listIterator(): ListIterator<T?> {
        return listIterator(0)
    }
    override fun listIterator(startingIndex: Int): ListIterator<T?> {
        return DLLIterator(startingIndex)
    }



    private inner class DLLIterator(startingIndex: Int) : ListIterator<T?>{
        private var nextNode: LinearNode<T>? = head
        private var lastReturnedNode: LinearNode<T>? = null
        private var iterModCount: Int = modCount
        private var canRemove: Boolean = false
        private var canSet: Boolean = false
        private var forwardMovement: Boolean = false
        private var nextIndex: Int = 0

        init{
            if (startingIndex < 0 || startingIndex > size){
                throw IndexOutOfBoundsException()
            }

            for (i in 0..startingIndex){
                nextNode = nextNode?.getNextNode()
                nextIndex++
            }
        }

        override fun hasNext(): Boolean {
            checkModCount()

            return nextNode != null
        }

        override fun hasPrevious(): Boolean {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun next(): T? {
            if (!hasNext()){
                throw NoSuchElementException()
            }

            var retVal = nextNode?.getNodeElement()
            lastReturnedNode = nextNode
            nextNode = nextNode?.getNextNode()
            nextIndex++
            canRemove = true
            canSet = true
            forwardMovement = true

            return retVal
        }

        override fun nextIndex(): Int {
            checkModCount()

            return nextIndex
        }

        override fun previous(): T? {
            if (!hasPrevious()){
                throw NoSuchElementException()
            }

            var retVal: T?

            if (nextNode == null){
                var current = head
                for (i in 0..nextIndex){
                    current = current?.getNextNode()
                }

                retVal = current?.getNodeElement()
                lastReturnedNode = current
                nextNode = current
            }
            else{
                retVal = nextNode?.getPreviousNode()?.getNodeElement()
                lastReturnedNode = nextNode?.getNextNode()
                nextNode = nextNode?.getPreviousNode()
            }

            nextIndex--
            canRemove = true
            canSet = true
            forwardMovement = false

            return retVal
        }

        override fun previousIndex(): Int {
            checkModCount()

            return nextIndex - 1
        }

        private fun checkModCount(){
            if (iterModCount != modCount){
                throw ConcurrentModificationException()
            }
        }

    }
}
