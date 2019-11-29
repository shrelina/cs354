import javax.sound.sampled.Line

class IUDoubleLinkedList<T> (var head: LinearNode<T>?, var tail: LinearNode<T>?, val size: Int = 0,
                             val modCount: Int = 0) : IndexedUnsortedList<T>{
    override fun addToFront(element: T) {
        var newHead = LinearNode<T>(null, null, null)
        newHead.setNodeElement(element)
        newHead.setNextNode(head)
        head = newHead

        if (){

        }
    }

    override fun addToRear(element: T) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun add(element: T) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addAfter(element: T, target: T) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun add(index: Int, element: T) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeFirst(): T {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeLast(): T {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun remove(element: T): T {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun remove(index: Int): T {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun set(index: Int, element: T) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun get(index: Int): T {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun indexOf(element: T) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun first(): T {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun last(): T {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun contains(target: T): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isEmpty(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun size(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun toString(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun iterator(): Iterator<T> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun listIterator(startingIndex: Int): ListIterator<T> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}