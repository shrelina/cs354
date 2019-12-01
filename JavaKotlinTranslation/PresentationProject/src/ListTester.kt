import java.lang.Exception
import java.lang.StringBuilder
import java.text.DecimalFormat

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

    private enum class IterAction{
        Next, HasNext, Previous, HasPrevious,
        NextIndex, PreviousIndex, Add, Remove,
        Set
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

        testEmptyList(::newList, "newList")
    }

    private fun testEmptyList(scenario: () -> IndexedUnsortedList<Int?>, scenarioName: String){
        print("\nSCENARIO: $scenarioName\n\n")

        try {
            printTest(scenarioName + "_testRemoveFirst", testRemoveFirst(scenario, null, Result.NoSuchElement))
            printTest(scenarioName + "_testRemoveLast", testRemoveLast(scenario, null, Result.NoSuchElement))
            printTest(scenarioName + "_testRemoveX", testRemoveElement(scenario, null, Result.NoSuchElement))
            printTest(scenarioName + "_testFirst", testFirst(scenario, null, Result.NoSuchElement))
            printTest(scenarioName + "_testLast", testLast(scenario, null, Result.NoSuchElement))
            printTest(scenarioName + "_testContainsX", testContains(scenario, ELEMENT_X, Result.False))
            printTest(scenarioName + "_testIsEmpty", testIsEmpty(scenario, Result.True))
            printTest(scenarioName + "_testSize", testSize(scenario, 0))
            printTest(scenarioName + "_testToString", testToString(scenario, Result.ValidString))
            printTest(scenarioName + "_testAddToFront", testAddToFront(scenario, ELEMENT_X, Result.NoException))
            printTest(scenarioName + "_testAddToRear", testAddToRear(scenario, ELEMENT_X, Result.NoException))
            printTest(scenarioName + "_testAddAfterX", testAddAfter(scenario, ELEMENT_X, ELEMENT_Z, Result.NoSuchElement))
            printTest(scenarioName + "_testAddAtIndexNeg1", testAddAtIndex(scenario, -1, ELEMENT_X, Result.IndexOutOfBounds))
            printTest(scenarioName + "_testAddAtIndex0", testAddAtIndex(scenario, 0, ELEMENT_X, Result.NoException))
            printTest(scenarioName + "_testAddAtIndex1", testAddAtIndex(scenario, 1, ELEMENT_X, Result.IndexOutOfBounds))
            printTest(scenarioName + "_testSetNeg1", testSet(scenario, -1, ELEMENT_X, Result.IndexOutOfBounds))
            printTest(scenarioName + "_testSet0", testSet(scenario, 0, ELEMENT_X, Result.IndexOutOfBounds))
            printTest(scenarioName + "_testAddX", testAdd(scenario, ELEMENT_X, Result.NoException))
            printTest(scenarioName + "_testGetNeg1", testGet(scenario, -1, null, Result.IndexOutOfBounds))
            printTest(scenarioName + "_testGet0", testGet(scenario, 0, null, Result.IndexOutOfBounds))
            printTest(scenarioName + "_testIndexOfX", testIndexOf(scenario, ELEMENT_X, -1))
            printTest(scenarioName + "_testRemoveAtNeg1", testRemoveAt(scenario, -1, null, Result.IndexOutOfBounds))
            printTest(scenarioName + "_testRemoveAt0", testRemoveAt(scenario, 0, null, Result.IndexOutOfBounds))

            // Iterator
            printTest(scenarioName + "_testIter", testIter(scenario, Result.NoException))
            printTest(scenarioName + "_testIterHasNext", testIterHasNext(scenario, ArrayList<ListTester.IterAction>(), Result.False))
            printTest(scenarioName + "_testIterNext", testIterNext(scenario, ArrayList<ListTester.IterAction>(), null, Result.NoSuchElement))
            printTest(scenarioName + "_testIterRemove", testIterRemove(scenario, ArrayList<ListTester.IterAction>(), Result.IllegalState))

            //ListIterator
            printTest(scenarioName + "_testListIter", testListIter(scenario, null, ArrayList<ListTester.IterAction>(), Result.NoException));
            printTest(scenarioName + "_testListIterNeg1", testListIter(scenario, -1, ArrayList<ListTester.IterAction>(), Result.IndexOutOfBounds))
            printTest(scenarioName + "_testListIter0", testListIter(scenario, 0, ArrayList<ListTester.IterAction>(), Result.NoException))
            printTest(scenarioName + "_testListIter1", testListIter(scenario, 1, ArrayList<ListTester.IterAction>(), Result.IndexOutOfBounds))
            printTest(scenarioName + "_testListIterHasNext", testListIterHasNext(scenario, null, ArrayList<ListTester.IterAction>(), Result.False))
            printTest(scenarioName + "_testListIterNext", testListIterNext(scenario, null, ArrayList<ListTester.IterAction>(), null, Result.NoSuchElement))
            printTest(scenarioName + "_testListIterRemove", testListIterRemove(scenario, null, ArrayList<ListTester.IterAction>(), Result.IllegalState))
            printTest(scenarioName + "_testListIterHasPrevious", testListIterHasPrevious(scenario, null, ArrayList<ListTester.IterAction>(), Result.False))
            printTest(scenarioName + "_testListIterPrevious", testListIterPrevious(scenario, null, ArrayList<ListTester.IterAction>(), null, Result.NoSuchElement))
            printTest(scenarioName + "_testListIterAdd", testListIterAdd(scenario, null, ArrayList<ListTester.IterAction>(), ELEMENT_X, Result.NoException))
            printTest(scenarioName + "_testListIterSet", testListIterSet(scenario, null, ArrayList<ListTester.IterAction>(), ELEMENT_X, Result.IllegalState))
            printTest(scenarioName + "_testListIterNextIndex", testListIterNextIndex(scenario, null, ArrayList<ListTester.IterAction>(), 0, Result.MatchingValue))
            printTest(scenarioName + "_testListIter0NextIndex", testListIterNextIndex(scenario, 0, ArrayList<ListTester.IterAction>(), 0, Result.MatchingValue))
            printTest(scenarioName + "_testListIterPreviousIndex", testListIterPreviousIndex(scenario, null, ArrayList<ListTester.IterAction>(),-1, Result.MatchingValue))
            printTest(scenarioName + "_testListIter0PreviousIndex", testListIterPreviousIndex(scenario, 0, ArrayList<ListTester.IterAction>(), -1, Result.MatchingValue))

        }
        catch(e: Exception){
            print("***UNABLE TO RUN/COMPLETE %s*** \n $scenarioName TESTS\n")
        }
        finally{
            if (printSectionSummaries){
                printSectionSummary()
            }
        }
    }

    private fun newList(): IndexedUnsortedList<Int?>{
        return IUDoubleLinkedList<Int?>(null, null, 0)
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

    private fun printSectionSummary(){
        secTotal = totalRun - secTotal
        secPasses = passes - secPasses
        secFails = failures - secFails
        print("Section Tests: $secTotal, Passed: $secPasses, Failed: $secFails\n")
        secPasses = passes
        secFails = failures
        val percentPassed = passes * 100.0 / totalRun
        val decimal = DecimalFormat("#.00")
        val percentPassedStr = decimal.format(percentPassed)
        print("Tests run so far: $totalRun, Passed: $passes ($percentPassedStr%), Failed: $failures\n")
    }

    private fun doIterActions(iter: MutableIterator<Int?>, actions: ArrayList<IterAction>){
        for (item in actions){
            when (item){
                IterAction.Next -> iter.next()
                IterAction.HasNext -> iter.hasNext()
                IterAction.Remove -> iter.remove()
                else -> throw NoSuchMethodError()
            }
        }
    }

    private fun doListIterActions(iter: MutableListIterator<Int?>, actions: ArrayList<IterAction>){
        var pass: Int
        for (item in actions){
            when (item){
                IterAction.Next -> iter.next()
                IterAction.HasNext -> iter.hasNext()
                IterAction.Remove -> iter.remove()
                IterAction.NextIndex -> iter.nextIndex()
                IterAction.HasPrevious -> iter.hasPrevious()
                IterAction.Add -> pass = 0
                IterAction.Previous -> iter.previous()
                IterAction.PreviousIndex -> iter.previousIndex()
                IterAction.Set -> pass = 0
            }
        }
    }

    private fun testRemoveFirst(listToBuild: () -> IndexedUnsortedList<Int?>, expectedElement: Int?, expectedResult: Result) : Boolean{
        var result: ListTester.Result
        try{
            var list = listToBuild()
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

    private fun testRemoveLast(listToBuild: () -> IndexedUnsortedList<Int?>, expectedElement: Int?, expectedResult: Result) : Boolean{
        var result: ListTester.Result
        try{
            var list = listToBuild()
            val retVal = list.removeLast()
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
            sb.append("testRemoveLast")

            result = Result.UnexpectedException
        }

        return result == expectedResult
    }

    private fun testRemoveElement(listToBuild: () -> IndexedUnsortedList<Int?>, element: Int?, expectedResult: Result) : Boolean{
        var result: ListTester.Result
        try{
            var list = listToBuild()
            val retVal = list.remove(element)
            if (retVal!!.equals(element)){
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
            sb.append("testRemoveElement")

            result = Result.UnexpectedException
        }

        return result == expectedResult
    }

    private fun testFirst(listToBuild: () -> IndexedUnsortedList<Int?>, expectedElement: Int?, expectedResult: Result) : Boolean{
        var result: ListTester.Result
        try{
            var list = listToBuild()
            val retVal = list.first()
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
            sb.append("testFirst")

            result = Result.UnexpectedException
        }

        return result == expectedResult
    }

    private fun testLast(listToBuild: () -> IndexedUnsortedList<Int?>, expectedElement: Int?, expectedResult: Result) : Boolean{
        var result: ListTester.Result
        try{
            var list = listToBuild()
            val retVal = list.last()
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
            sb.append("testLast")

            result = Result.UnexpectedException
        }

        return result == expectedResult
    }

    private fun testContains(listToBuild: () -> IndexedUnsortedList<Int?>, element: Int?, expectedResult: Result) : Boolean{
        var result: ListTester.Result
        try{
            var list = listToBuild()
            if (list.contains(element)){
                result = Result.True
            }
            else{
                result = Result.False
            }
        }
        catch (e: Exception){
            val sb = StringBuilder()
            sb.append(e.toString())
            sb.append(" caught unexpected ")
            sb.append(e.printStackTrace())
            sb.append("\n")
            sb.append("testContains")

            result = Result.UnexpectedException
        }

        return result == expectedResult
    }

    private fun testIsEmpty(listToBuild: () -> IndexedUnsortedList<Int?>, expectedResult: Result) : Boolean{
        var result: ListTester.Result
        try{
            var list = listToBuild()
            if (list.isEmpty()){
                result = Result.True
            }
            else{
                result = Result.False
            }
        }
        catch (e: Exception){
            val sb = StringBuilder()
            sb.append(e.toString())
            sb.append(" caught unexpected ")
            sb.append(e.printStackTrace())
            sb.append("\n")
            sb.append("testIsEmpty")

            result = Result.UnexpectedException
        }

        return result == expectedResult
    }

    private fun testSize(listToBuild: () -> IndexedUnsortedList<Int?>, expectedSize: Int) : Boolean{
        try{
            var list = listToBuild()
            return (list.size() == expectedSize)
        }
        catch (e: Exception){
            val sb = StringBuilder()
            sb.append(e.toString())
            sb.append(" caught unexpected ")
            sb.append(e.printStackTrace())
            sb.append("\n")
            sb.append("testIsEmpty")

            return false
        }
    }

    private fun testToString(listToBuild: () -> IndexedUnsortedList<Int?>, expectedResult: Result) : Boolean{
        var result: Result
        try{
            var list = listToBuild()
            var str = list.toString().trim()
            if (showToString){
                print("toString() output: $str\n")
            }
            if (str.length < (list.size() + list.size() / 2 + 2)){
                result = Result.Fail
            }
            else{
                val lastChar = str.last()
                val firstChar = str.first()
                if (firstChar != '[' || lastChar != ']'){
                    result = Result.Fail
                }
                else if (str.contains("@")
                        && !str.contains(" ")
                        && Character.isLetter(str.first())
                        && (Character.isDigit(str.last()) || (lastChar >= 'a' && lastChar <= 'f'))){
                    result = Result.Fail
                }
                else{
                    result = Result.ValidString
                }
            }
        }
        catch (e: Exception){
            val sb = StringBuilder()
            sb.append(e.toString())
            sb.append(" caught unexpected ")
            sb.append(e.printStackTrace())
            sb.append("\n")
            sb.append("testIsEmpty")

            result = Result.UnexpectedException
        }

        return result == expectedResult
    }

    private fun testAddToFront(listToBuild: () -> IndexedUnsortedList<Int?>, element: Int?, expectedResult: Result) : Boolean{
        var result: ListTester.Result
        try{
            var list = listToBuild()
            list.addToFront(element)
            result = Result.NoException
        }
        catch (e: Exception){
            val sb = StringBuilder()
            sb.append(e.toString())
            sb.append(" caught unexpected ")
            sb.append(e.printStackTrace())
            sb.append("\n")
            sb.append("testAddToFront")

            result = Result.UnexpectedException
        }

        return result == expectedResult
    }

    private fun testAddToRear(listToBuild: () -> IndexedUnsortedList<Int?>, element: Int?, expectedResult: Result) : Boolean{
        var result: ListTester.Result
        try{
            var list = listToBuild()
            list.addToRear(element)
            result = Result.NoException
        }
        catch (e: Exception){
            val sb = StringBuilder()
            sb.append(e.toString())
            sb.append(" caught unexpected ")
            sb.append(e.printStackTrace())
            sb.append("\n")
            sb.append("testAddToRear")

            result = Result.UnexpectedException
        }

        return result == expectedResult
    }

    private fun testAddAfter(listToBuild: () -> IndexedUnsortedList<Int?>, target: Int?, element: Int?, expectedResult: Result) : Boolean{
        var result: ListTester.Result
        try{
            var list = listToBuild()
            list.addAfter(element, target)
            result = Result.NoException
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
            sb.append("testAddAfter")

            result = Result.UnexpectedException
        }

        return result == expectedResult
    }

    private fun testAddAtIndex(listToBuild: () -> IndexedUnsortedList<Int?>, index: Int, element: Int?, expectedResult: Result) : Boolean{
        var result: ListTester.Result
        try{
            var list = listToBuild()
            list.add(index, element)
            result = Result.NoException
        }
        catch(e: IndexOutOfBoundsException){
            result = Result.IndexOutOfBounds
        }
        catch (e: Exception){
            val sb = StringBuilder()
            sb.append(e.toString())
            sb.append(" caught unexpected ")
            sb.append(e.printStackTrace())
            sb.append("\n")
            sb.append("testAddAtIndex")

            result = Result.UnexpectedException
        }

        return result == expectedResult
    }

    private fun testSet(listToBuild: () -> IndexedUnsortedList<Int?>, index: Int, element: Int?, expectedResult: Result) : Boolean{
        var result: ListTester.Result
        try{
            var list = listToBuild()
            list.set(index, element)
            result = Result.NoException
        }
        catch(e: IndexOutOfBoundsException){
            result = Result.IndexOutOfBounds
        }
        catch (e: Exception){
            val sb = StringBuilder()
            sb.append(e.toString())
            sb.append(" caught unexpected ")
            sb.append(e.printStackTrace())
            sb.append("\n")
            sb.append("testSet")

            result = Result.UnexpectedException
        }

        return result == expectedResult
    }

    private fun testAdd(listToBuild: () -> IndexedUnsortedList<Int?>, element: Int?, expectedResult: Result) : Boolean{
        var result: ListTester.Result
        try{
            var list = listToBuild()
            list.add(element)
            result = Result.NoException
        }
        catch(e: IndexOutOfBoundsException){
            result = Result.IndexOutOfBounds
        }
        catch (e: Exception){
            val sb = StringBuilder()
            sb.append(e.toString())
            sb.append(" caught unexpected ")
            sb.append(e.printStackTrace())
            sb.append("\n")
            sb.append("testSet")

            result = Result.UnexpectedException
        }

        return result == expectedResult
    }

    private fun testGet(listToBuild: () -> IndexedUnsortedList<Int?>, index: Int, expectedElement: Int?, expectedResult: Result) : Boolean{
        var result: ListTester.Result
        try{
            var list = listToBuild()
            val retVal = list.get(index)
            if (retVal!!.equals(expectedElement)){
                result = Result.MatchingValue
            }
            else{
                result = Result.Fail
            }

        }
        catch(e: IndexOutOfBoundsException){
            result = Result.IndexOutOfBounds
        }
        catch (e: Exception){
            val sb = StringBuilder()
            sb.append(e.toString())
            sb.append(" caught unexpected ")
            sb.append(e.printStackTrace())
            sb.append("\n")
            sb.append("testSet")

            result = Result.UnexpectedException
        }

        return result == expectedResult
    }

    private fun testIndexOf(listToBuild: () -> IndexedUnsortedList<Int?>, element: Int?, expectedIndex: Int) : Boolean{
        try{
            var list = listToBuild()
            return list.indexOf(element) == expectedIndex
        }
        catch (e: Exception){
            val sb = StringBuilder()
            sb.append(e.toString())
            sb.append(" caught unexpected ")
            sb.append(e.printStackTrace())
            sb.append("\n")
            sb.append("testSet")

            return false
        }
    }

    private fun testRemoveAt(listToBuild: () -> IndexedUnsortedList<Int?>, index: Int, expectedElement: Int?, expectedResult: Result) : Boolean{
        var result: Result
        try{
            var list = listToBuild()
            val retVal = list.removeAt(index)
            if (retVal!!.equals(expectedElement)){
                result = Result.MatchingValue
            }
            else{
                result = Result.Fail
            }
        }
        catch(e: IndexOutOfBoundsException){
            result = Result.IndexOutOfBounds
        }
        catch (e: Exception){
            val sb = StringBuilder()
            sb.append(e.toString())
            sb.append(" caught unexpected ")
            sb.append(e.printStackTrace())
            sb.append("\n")
            sb.append("testSet")

            result = Result.UnexpectedException
        }

        return result == expectedResult
    }

    private fun testIter(listToBuild: () -> IndexedUnsortedList<Int?>, expectedResult: Result) : Boolean {
        var result: Result
        try {
            var list = listToBuild()
            val it = list.iterator()
            result = Result.NoException;
        }
        catch (e: Exception) {
            val sb = StringBuilder()
            sb.append(e.toString())
            sb.append(" caught unexpected ")
            sb.append(e.printStackTrace())
            sb.append("\n")
            sb.append("testIter")

            result = Result.UnexpectedException
        }
        return result == expectedResult;
    }

    private fun testIterHasNext(listToBuild: () -> IndexedUnsortedList<Int?>, performIterActions: ArrayList<IterAction>,
                                expectedResult: Result) : Boolean {
        var result: Result
        try {
            var list = listToBuild()
            var it = list.iterator()
            doIterActions(it, performIterActions)

            if (it.hasNext()){
                result = Result.True
            }
            else{
                result = Result.False
            }
        }
        catch(e: ConcurrentModificationException){
            result = Result.ConcurrentModification
        }
        catch (e: Exception) {
            val sb = StringBuilder()
            sb.append(e.toString())
            sb.append(" caught unexpected ")
            sb.append(e.printStackTrace())
            sb.append("\n")
            sb.append("testIterHasNext")

            result = Result.UnexpectedException
        }
        return result == expectedResult;
    }

    private fun testIterNext(listToBuild: () -> IndexedUnsortedList<Int?>, performIterActions: ArrayList<IterAction>,
                             expectedValue: Int?, expectedResult: Result) : Boolean {
        var result: Result
        try {
            var list = listToBuild()
            val it = list.iterator()
            doIterActions(it, performIterActions)

            val retVal = it.next()

            if (retVal!!.equals(expectedValue)){
                result = Result.True
            }
            else{
                result = Result.False
            }
        }
        catch(e: NoSuchElementException){
            result = Result.NoSuchElement
        }
        catch(e: ConcurrentModificationException){
            result = Result.ConcurrentModification
        }
        catch (e: Exception) {
            val sb = StringBuilder()
            sb.append(e.toString())
            sb.append(" caught unexpected ")
            sb.append(e.printStackTrace())
            sb.append("\n")
            sb.append("testIterHasNext")

            result = Result.UnexpectedException
        }
        return result == expectedResult;
    }

    private fun testIterRemove(listToBuild: () -> IndexedUnsortedList<Int?>, performIterActions: ArrayList<IterAction>,
                               expectedResult: Result) : Boolean {
        var result: Result
        try {
            var list = listToBuild()
            val it = list.iterator()
            doIterActions(it, performIterActions)

            it.remove()
            result = Result.NoException
        }
        catch(e: IllegalStateException){
            result = Result.IllegalState
        }
        catch(e: ConcurrentModificationException){
            result = Result.ConcurrentModification
        }
        catch (e: Exception) {
            val sb = StringBuilder()
            sb.append(e.toString())
            sb.append(" caught unexpected ")
            sb.append(e.printStackTrace())
            sb.append("\n")
            sb.append("testIterHasNext")

            result = Result.UnexpectedException
        }
        return result == expectedResult;
    }

    private fun testListIterRemove(listToBuild: () -> IndexedUnsortedList<Int?>, startingIndex: Int?, performIterActions: ArrayList<IterAction>,
                               expectedResult: Result) : Boolean {
        var result: Result
        try {
            var list = listToBuild()
            val it: MutableListIterator<Int?>
            if (startingIndex == null) {
                it = list.listIterator()
            }
            else{
                it = list.listIterator(startingIndex)
            }
            doListIterActions(it, performIterActions)

            it.remove()
            result = Result.NoException
        }
        catch(e: IllegalStateException){
            result = Result.IllegalState
        }
        catch(e: ConcurrentModificationException){
            result = Result.ConcurrentModification
        }
        catch (e: Exception) {
            val sb = StringBuilder()
            sb.append(e.toString())
            sb.append(" caught unexpected ")
            sb.append(e.printStackTrace())
            sb.append("\n")
            sb.append("testIterHasNext")

            result = Result.UnexpectedException
        }
        return result == expectedResult;
    }

    private fun testListIter(listToBuild: () -> IndexedUnsortedList<Int?>, startingIndex: Int?, performIterActions: ArrayList<IterAction>,
                                   expectedResult: Result) : Boolean {
        var result: Result
        try {
            var list = listToBuild()
            val it: MutableListIterator<Int?>
            if (startingIndex == null) {
                it = list.listIterator()
            }
            else{
                it = list.listIterator(startingIndex)
            }
            doListIterActions(it, performIterActions)

            result = Result.NoException
        }
        catch(e: IllegalStateException){
            result = Result.IllegalState
        }
        catch(e: IndexOutOfBoundsException){
            result = Result.IndexOutOfBounds
        }
        catch(e: ConcurrentModificationException){
            result = Result.ConcurrentModification
        }
        catch (e: Exception) {
            val sb = StringBuilder()
            sb.append(e.toString())
            sb.append(" caught unexpected ")
            sb.append(e.printStackTrace())
            sb.append("\n")
            sb.append("testIterHasNext")

            result = Result.UnexpectedException
        }
        return result == expectedResult;
    }

    private fun testListIterHastNext(listToBuild: () -> IndexedUnsortedList<Int?>, startingIndex: Int?, performIterActions: ArrayList<IterAction>,
                             expectedResult: Result) : Boolean {
        var result: Result
        try {
            var list = listToBuild()
            val it: MutableListIterator<Int?>
            if (startingIndex == null) {
                it = list.listIterator()
            }
            else{
                it = list.listIterator(startingIndex)
            }
            doListIterActions(it, performIterActions)

            if (it.hasNext()){
                result = Result.True
            }
            else{
                result = Result.False
            }

            result = Result.NoException
        }
        catch(e: ConcurrentModificationException){
            result = Result.ConcurrentModification
        }
        catch (e: Exception) {
            val sb = StringBuilder()
            sb.append(e.toString())
            sb.append(" caught unexpected ")
            sb.append(e.printStackTrace())
            sb.append("\n")
            sb.append("testIterHasNext")

            result = Result.UnexpectedException
        }
        return result == expectedResult;
    }

    private fun testListIterNext(listToBuild: () -> IndexedUnsortedList<Int?>, startingIndex: Int?, performIterActions: ArrayList<IterAction>,
                                   expectedValue: Int?, expectedResult: Result) : Boolean {
        var result: Result
        try {
            var list = listToBuild()
            val it: MutableListIterator<Int?>
            if (startingIndex == null) {
                it = list.listIterator()
            }
            else{
                it = list.listIterator(startingIndex)
            }
            doListIterActions(it, performIterActions)

            val retVal = it.next()
            if (retVal!!.equals(expectedValue)){
                result = Result.MatchingValue
            }
            else{
                result = Result.Fail
            }
        }
        catch(e: NoSuchElementException){
            result = Result.NoSuchElement
        }
        catch(e: ConcurrentModificationException){
            result = Result.ConcurrentModification
        }
        catch (e: Exception) {
            val sb = StringBuilder()
            sb.append(e.toString())
            sb.append(" caught unexpected ")
            sb.append(e.printStackTrace())
            sb.append("\n")
            sb.append("testIterHasNext")

            result = Result.UnexpectedException
        }
        return result == expectedResult;
    }

    private fun testListIterHastPrevious(listToBuild: () -> IndexedUnsortedList<Int?>, startingIndex: Int?, performIterActions: ArrayList<IterAction>,
                                     expectedResult: Result) : Boolean {
        var result: Result
        try {
            var list = listToBuild()
            val it: MutableListIterator<Int?>
            if (startingIndex == null) {
                it = list.listIterator()
            }
            else{
                it = list.listIterator(startingIndex)
            }
            doListIterActions(it, performIterActions)

            if (it.hasPrevious()){
                result = Result.True
            }
            else{
                result = Result.False
            }

            result = Result.NoException
        }
        catch(e: ConcurrentModificationException){
            result = Result.ConcurrentModification
        }
        catch (e: Exception) {
            val sb = StringBuilder()
            sb.append(e.toString())
            sb.append(" caught unexpected ")
            sb.append(e.printStackTrace())
            sb.append("\n")
            sb.append("testIterHasNext")

            result = Result.UnexpectedException
        }
        return result == expectedResult
    }

    private fun testListIterPrevious(listToBuild: () -> IndexedUnsortedList<Int?>, startingIndex: Int?, performIterActions: ArrayList<IterAction>,
                                 expectedValue: Int?, expectedResult: Result) : Boolean {
        var result: Result
        try {
            var list = listToBuild()
            val it: MutableListIterator<Int?>
            if (startingIndex == null) {
                it = list.listIterator()
            }
            else{
                it = list.listIterator(startingIndex)
            }
            doListIterActions(it, performIterActions)

            val retVal = it.previous()
            if (retVal!!.equals(expectedValue)){
                result = Result.MatchingValue
            }
            else{
                result = Result.Fail
            }
        }
        catch(e: NoSuchElementException){
            result = Result.NoSuchElement
        }
        catch(e: ConcurrentModificationException){
            result = Result.ConcurrentModification
        }
        catch (e: Exception) {
            val sb = StringBuilder()
            sb.append(e.toString())
            sb.append(" caught unexpected ")
            sb.append(e.printStackTrace())
            sb.append("\n")
            sb.append("testIterHasNext")

            result = Result.UnexpectedException
        }
        return result == expectedResult;
    }

    private fun testListIterAdd(listToBuild: () -> IndexedUnsortedList<Int?>, startingIndex: Int?, performIterActions: ArrayList<IterAction>,
                                     element: Int?, expectedResult: Result) : Boolean {
        var result: Result
        try {
            var list = listToBuild()
            val it: MutableListIterator<Int?>
            if (startingIndex == null) {
                it = list.listIterator()
            }
            else{
                it = list.listIterator(startingIndex)
            }
            doListIterActions(it, performIterActions)

            it.add(element)
            result = Result.NoException
        }
        catch(e: ConcurrentModificationException){
            result = Result.ConcurrentModification
        }
        catch (e: Exception) {
            val sb = StringBuilder()
            sb.append(e.toString())
            sb.append(" caught unexpected ")
            sb.append(e.printStackTrace())
            sb.append("\n")
            sb.append("testIterHasNext")

            result = Result.UnexpectedException
        }
        return result == expectedResult;
    }

    private fun testListIterSet(listToBuild: () -> IndexedUnsortedList<Int?>, startingIndex: Int?, performIterActions: ArrayList<IterAction>,
                                element: Int?, expectedResult: Result) : Boolean {
        var result: Result
        try {
            var list = listToBuild()
            val it: MutableListIterator<Int?>
            if (startingIndex == null) {
                it = list.listIterator()
            }
            else{
                it = list.listIterator(startingIndex)
            }
            doListIterActions(it, performIterActions)

            it.set(element)
            result = Result.NoException
        }
        catch(e: ConcurrentModificationException){
            result = Result.ConcurrentModification
        }
        catch(e: IllegalStateException){
            result = Result.IllegalState
        }
        catch (e: Exception) {
            val sb = StringBuilder()
            sb.append(e.toString())
            sb.append(" caught unexpected ")
            sb.append(e.printStackTrace())
            sb.append("\n")
            sb.append("testIterHasNext")

            result = Result.UnexpectedException
        }
        return result == expectedResult;
    }

    private fun testListIterNextIndex(listToBuild: () -> IndexedUnsortedList<Int?>, startingIndex: Int?, performIterActions: ArrayList<IterAction>,
                                expectedIndex: Int, expectedResult: Result) : Boolean {
        var result: Result
        try {
            var list = listToBuild()
            val it: MutableListIterator<Int?>
            if (startingIndex == null) {
                it = list.listIterator()
            }
            else{
                it = list.listIterator(startingIndex)
            }
            doListIterActions(it, performIterActions)

            val idx = it.nextIndex()
            if (idx == expectedIndex){
                result = Result.MatchingValue
            }
            else{
                result = Result.Fail
            }
        }
        catch(e: ConcurrentModificationException){
            result = Result.ConcurrentModification
        }
        catch (e: Exception) {
            val sb = StringBuilder()
            sb.append(e.toString())
            sb.append(" caught unexpected ")
            sb.append(e.printStackTrace())
            sb.append("\n")
            sb.append("testIterHasNext")

            result = Result.UnexpectedException
        }
        return result == expectedResult;
    }

    private fun testListIterPreviousIndex(listToBuild: () -> IndexedUnsortedList<Int?>, startingIndex: Int?, performIterActions: ArrayList<IterAction>,
                                      expectedIndex: Int, expectedResult: Result) : Boolean {
        var result: Result
        try {
            var list = listToBuild()
            val it: MutableListIterator<Int?>
            if (startingIndex == null) {
                it = list.listIterator()
            }
            else{
                it = list.listIterator(startingIndex)
            }
            doListIterActions(it, performIterActions)

            val idx = it.previousIndex()
            if (idx == expectedIndex){
                result = Result.MatchingValue
            }
            else{
                result = Result.Fail
            }
        }
        catch(e: ConcurrentModificationException){
            result = Result.ConcurrentModification
        }
        catch (e: Exception) {
            val sb = StringBuilder()
            sb.append(e.toString())
            sb.append(" caught unexpected ")
            sb.append(e.printStackTrace())
            sb.append("\n")
            sb.append("testIterHasNext")

            result = Result.UnexpectedException
        }
        return result == expectedResult;
    }

    private fun testListIterHasNext(listToBuild: () -> IndexedUnsortedList<Int?>, startingIndex: Int?, performIterActions: ArrayList<IterAction>,
                                          expectedResult: Result) : Boolean {
        var result: Result
        try {
            var list = listToBuild()
            val it: MutableListIterator<Int?>
            if (startingIndex == null) {
                it = list.listIterator()
            }
            else{
                it = list.listIterator(startingIndex)
            }
            doListIterActions(it, performIterActions)

            if (it.hasNext()){
                result = Result.True
            }
            else{
                result = Result.False
            }
        }
        catch(e: ConcurrentModificationException){
            result = Result.ConcurrentModification
        }
        catch (e: Exception) {
            val sb = StringBuilder()
            sb.append(e.toString())
            sb.append(" caught unexpected ")
            sb.append(e.printStackTrace())
            sb.append("\n")
            sb.append("testIterHasNext")

            result = Result.UnexpectedException
        }
        return result == expectedResult;
    }

    private fun testListIterHasPrevious(listToBuild: () -> IndexedUnsortedList<Int?>, startingIndex: Int?, performIterActions: ArrayList<IterAction>,
                                    expectedResult: Result) : Boolean {
        var result: Result
        try {
            var list = listToBuild()
            val it: MutableListIterator<Int?>
            if (startingIndex == null) {
                it = list.listIterator()
            }
            else{
                it = list.listIterator(startingIndex)
            }
            doListIterActions(it, performIterActions)

            if (it.hasPrevious()){
                result = Result.True
            }
            else{
                result = Result.False
            }
        }
        catch(e: ConcurrentModificationException){
            result = Result.ConcurrentModification
        }
        catch (e: Exception) {
            val sb = StringBuilder()
            sb.append(e.toString())
            sb.append(" caught unexpected ")
            sb.append(e.printStackTrace())
            sb.append("\n")
            sb.append("testIterHasNext")

            result = Result.UnexpectedException
        }
        return result == expectedResult;
    }
}
