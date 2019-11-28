interface IndexedUnsortedList<T> : Iterable<T>{
    public fun addToFront(element: T)
    public fun addToRear(element: T)
    public fun add(element: T)
    public fun addAfter(element: T, target: T)
    public fun add(index: Int, element: T)
    public fun removeFirst(): T
    public fun removeLast(): T
    public fun remove(element: T): T
    public fun remove(index: Int): T
    public fun set(index: Int, element: T)
    public fun get(index: Int): T
    public fun indexOf(element: T)
    public fun first(): T
    public fun last(): T
    public fun contains(target: T): Boolean
    public fun isEmpty(): Boolean
    public fun size(): Int
    public override fun toString(): String
    public override fun iterator(): Iterator<T>
    public fun listIterator(startingIndex: Int): ListIterator<T>
}