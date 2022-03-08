abstract class ExprC

class NumC(val num: Int) : ExprC()
class IdC(val id: String) : ExprC()
class AppC(val fn: ExprC, val args: ArrayList<ExprC>) : ExprC()
class LamC(val params: ArrayList<IdC>, val body: ExprC) : ExprC()
class StringC(val s: String) : ExprC()
class IfC(val self: ExprC, val then: ExprC, val otherwise: ExprC) : ExprC()
