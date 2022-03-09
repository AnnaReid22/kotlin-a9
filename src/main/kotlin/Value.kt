abstract class Value

class CloV(val params: ArrayList<IdC>, val body: ExprC, val env: Env): Value()
class TrueV : Value()
class FalseV: Value()
class StringV(val s: String): Value()
class NumV(val n: Int): Value()
class PrimV(val fn: (Value, Value) -> Value): Value()