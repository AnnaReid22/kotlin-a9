import org.junit.Test
import org.junit.jupiter.api.Assertions.*

internal class MainKtTest {

    val myMain = Main()
    @Test
    fun testAdd() {
        assertEquals(42, (myMain.myAdd(NumV(40), NumV(2)) as NumV).n)
    }

    @Test
    fun testSub() {
        assertEquals(38, (myMain.mySub(NumV(40), NumV(2)) as NumV).n)
    }

    @Test
    fun testMult() {
        assertEquals(80, (myMain.myMult(NumV(40), NumV(2)) as NumV).n)
    }

    @Test
    fun testDiv() {
        assertEquals(20, (myMain.myDiv(NumV(40), NumV(2)) as NumV).n)
    }

//    @Test
//    fun testLessThanOrEqualTo() {
//        assertEquals(FalseV(), myMain.myLessThanOrEqual(NumV(40), NumV(2)))
//    }
//
//    @Test
//    fun testEqualTo() {
//        assertEquals(20, myMain.myEqual(NumV(40), NumV(2)))
//    }

//    @Test
//    fun testLookUpEnv() {
//        assertEquals(20, (myMain.lookupEnv(NumV(40), NumV(2)) as NumV).n)
//    }
}


