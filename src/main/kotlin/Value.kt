abstract class Value {
}
//(define-type Value (U TrueV FalseV NumV PrimV CloV StringV))
//(struct CloV ([params : (Listof Symbol)] [body : ExprC] [env : Env]) #:transparent)
//(struct TrueV () #:transparent)
//(struct FalseV () #:transparent)
//(struct StringV ([s : String]) #:transparent)
//(struct NumV ([n : Real]) #:transparent)
//(struct PrimV ([f : (-> (Listof Value) Value)])  #:transparent)

public class CloV(params: ArrayList<String>, body: ExprC, env: Env): Value(){}
public class TrueV(): Value(){}
public class False(): Value(){}
public class StringV(s: String): Value(){}
public class NumV(n: Int): Value(){}
public class PrimV(f: (ArrayList<Value>) -> Value): Value(){}