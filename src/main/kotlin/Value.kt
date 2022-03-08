abstract class Value {
}
//(define-type Value (U TrueV FalseV NumV PrimV CloV StringV))
//(struct CloV ([params : (Listof Symbol)] [body : ExprC] [env : Env]) #:transparent)
//(struct TrueV () #:transparent)
//(struct FalseV () #:transparent)
//(struct StringV ([s : String]) #:transparent)
//(struct NumV ([n : Real]) #:transparent)
//(struct PrimV ([f : (-> (Listof Value) Value)])  #:transparent)

public class CloV(val params: ArrayList<IdC>, val body: ExprC, val env: Env): Value(){}
public class TrueV(): Value(){}
public class FalseV(): Value(){}
public class StringV(val s: String): Value(){}
public class NumV(val n: Int): Value(){}
public class PrimV(val fn: (ArrayList<Value>) -> Value): Value(){}