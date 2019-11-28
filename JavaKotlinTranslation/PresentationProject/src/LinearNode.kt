

class LinearNode<E> constructor(var element: E?, var next: LinearNode<E>?, var previous: LinearNode<E>?) {
    public fun setNextNode(newNextNode: LinearNode<E>?){
        next = newNextNode
    }

    public fun getNextNode(): LinearNode<E>?{
        return next
    }

    public fun setPreviousNode(newPreviousNode: LinearNode<E>?){
        previous = newPreviousNode
    }

    public fun getPreviousNode(): LinearNode<E>?{
        return previous
    }

    public fun setNodeElement(newElement: E?){
        element = newElement
    }

    public fun getNodeElement(): E?{
        return element
    }
}