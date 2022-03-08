abstract class ExprC {

}

public class NumC(num: Int) : ExprC(){}
public class IdC(id: String) : ExprC(){}
public class AppC(fn: ExprC, arg: ArrayList<ExprC>) : ExprC(){}
public class LamC(params: ArrayList<IdC>, body: ExprC) : ExprC(){}
public class StringC(s: String) : ExprC(){}
public class IfC(self: ExprC, then: ExprC, otherwise: ExprC) : ExprC(){}
