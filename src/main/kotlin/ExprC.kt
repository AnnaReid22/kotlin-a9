abstract class ExprC {

}

public class NumC(val num: Int) : ExprC(){

}
public class IdC(val id: String) : ExprC(){}
public class AppC(val fn: ExprC, val args: ArrayList<ExprC>) : ExprC(){}
public class LamC(val params: ArrayList<IdC>, val body: ExprC) : ExprC(){}
public class StringC(val s: String) : ExprC(){}
public class IfC(val self: ExprC, val then: ExprC, val otherwise: ExprC) : ExprC(){}
