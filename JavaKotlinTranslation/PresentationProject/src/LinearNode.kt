

class LinearNode<E>constructor(var element: E?, var next: LinearNode<E>?, var previous: LinearNode<E>?) {
    public fun setNextNode(nextNode: LinearNode<E>?){
        next = nextNode
    }

    public fun setPreviousNode(previousNode: LinearNode<E>?){

    }
}