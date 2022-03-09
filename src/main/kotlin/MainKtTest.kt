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

    @Test
    fun testLookUpEnv() {
        val expected = mutableListOf<Binding>()
        val myActual = mutableListOf<Binding>()
        expected.add(Binding("hello", NumV(1)))
        myActual.add(Binding("hello", NumV(1)))
        assertEquals(1, (myMain.lookupEnv(Env(expected), "hello") as NumV).n)
    }

    @Test
    fun testExtendEnv() {
        val empty = mutableListOf<Binding>()
        val listof_idc = arrayListOf((IdC("hello")))
        val listof_val = arrayListOf<Value>(NumV(1))
        assertEquals(1, ((((myMain.extendEnv(Env(empty), listof_idc, listof_val)) as Env).bindings[0]).value as NumV).n)
    }

    @Test
    fun testInterp() {
        assertEquals(2, (myMain.interp(NumC(2), myMain.defineTopEnv()) as NumV).n)
        assertEquals("hello", (myMain.interp(StringC("hello"), myMain.defineTopEnv()) as StringV).s)
        assertEquals(3, (myMain.interp(IfC(IdC("true"), NumC(3), NumC(4)), myMain.defineTopEnv()) as NumV).n)
        assertEquals(4, (myMain.interp(IfC(IdC("false"), NumC(3), NumC(4)), myMain.defineTopEnv()) as NumV).n)
    }
}


