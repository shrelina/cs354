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
            printTest(scenarioName + "_testAddAtIndex0", testAddAtIndex(scenario, 0, ELEMENT_X, Result.NoException));
            printTest(scenarioName + "_testAddAtIndex1", testAddAtIndex(scenario, 1, ELEMENT_X, Result.IndexOutOfBounds));
            printTest(scenarioName + "_testSetNeg1", testSet(scenario, -1, ELEMENT_X, Result.IndexOutOfBounds));
            printTest(scenarioName + "_testSet0", testSet(scenario, 0, ELEMENT_X, Result.IndexOutOfBounds));
            printTest(scenarioName + "_testAddX", testAdd(scenario, ELEMENT_X, Result.NoException));
            printTest(scenarioName + "_testGetNeg1", testGet(scenario, -1, null, Result.IndexOutOfBounds));
            printTest(scenarioName + "_testGet0", testGet(scenario, 0, null, Result.IndexOutOfBounds));
            printTest(scenarioName + "_testIndexOfX", testIndexOf(scenario, ELEMENT_X, -1));
            printTest(scenarioName + "_testRemoveAtNeg1", testRemoveAt(scenario, -1, null, Result.IndexOutOfBounds));
            printTest(scenarioName + "_testRemoveAt0", testRemoveAt(scenario, 0, null, Result.IndexOutOfBounds));
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


}