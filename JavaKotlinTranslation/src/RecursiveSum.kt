fun main(args: Array<String>){
    val nums = mutableListOf<Int>();
    for (i in args){
        nums.add(i.toInt())
    }
    println(RecursiveSum.sum(nums))
}

object RecursiveSum{
    public fun sum(nums: List<Int>): Int{
        if (nums.size == 0){
            return 0;
        }
        else{
            val first = nums.get(0)
            return sum(nums.drop(1)) + first
        }
    }
}