import java.lang.Exception
import java.lang.StringBuilder

fun main(args: Array<String>){
    val tester = ListTester()
    tester.runTests()
}

class ListTester{
    private enum class ListToUse{
        GoodList, BadList, ArrayList, SingleLinkedList, DoubleLinkedList
    }

    private enum class Result{
        IndexOutOfBounds, IllegalState, NoSuchElement,
        ConcurrentModification, UnsupportedOperation,
        NoException, UnexpectedException,
        True, False, Pass, Fail,
        MatchingValue,
        ValidString
    }

    // private static final varName
    private companion object {
        val LIST_TO_USE = ListToUse.DoubleLinkedList
        val ELEMENT_A = 1
        val ELEMENT_B = 2
        val ELEMENT_C = 3
        val ELEMENT_D = 4
        val ELEMENT_X = -1
        val ELEMENT_Z = -2
    }

    private var passes = 0
    private var failures = 0
    private var totalRun = 0

    private var secTotal = 0
    private var secPasses = 0
    private var secFails = 0

    private var printFailuresOnly = false
    private var showToString = true
    private var printSectionSummaries = true

    private val SUPPORT_LIST_ITERATOR : Boolean = when (LIST_TO_USE){
        ListToUse.DoubleLinkedList -> true
        else -> false
    }

    fun runTests(){
        val LIST_A = { ELEMENT_A }
        val STRING_A = "A"
        val LIST_B = { ELEMENT_B}
        val STRING_B = "B"

        testEmptyList(newList(), "newList")
    }

    private fun testEmptyList(scenario: IndexedUnsortedList<Int>, scenarioName: String){

        printTest(scenarioName + "_testRemoveFirst", testRemoveFirst(scenario, null, Result.NoSuchElement))
    }

    private fun newList(): IndexedUnsortedList<Int>{
        return IUDoubleLinkedList<Int>(null, null, 0)
    }

    private fun printTest(testDesc: String, result: Boolean){
        val sb = StringBuilder()
        totalRun++
        if (result){
            passes++
        }
        else{
            failures++
        }

        sb.append(testDesc)
        sb.append("\t\t")
        if (result){
            sb.append("   PASS")
        }
        else{
            sb.append("***FAIL***")
        }
        sb.append("\n")
        if (!result || !printFailuresOnly){
            print(sb.toString())
        }
    }

    private fun testRemoveFirst(list: IndexedUnsortedList<Int>, expectedElement: Int?, expectedResult: Result) : Boolean{
        var result: ListTester.Result
        try{
            val retVal = list.removeFirst()
            if (retVal!!.equals(expectedElement)){
                result = Result.MatchingValue
            }
            else{
                result = Result.Fail
            }
        }
        catch(e: NoSuchElementException){
            result = Result.NoSuchElement
        }
        catch (e: Exception){
            val sb = StringBuilder()
            sb.append(e.toString())
            sb.append(" caught unexpected ")
            sb.append(e.printStackTrace())
            sb.append("\n")
            sb.append("testRemoveFirst")

            result = Result.UnexpectedException
        }

        return result == expectedResult
    }

}