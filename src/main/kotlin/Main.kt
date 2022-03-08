fun main() {



//    val x = isEven(5)
//    println(x)
//    print10Numbers()
//    val y = 3
//    println(y.isOdd())
//    val dog2 = Dog()
//    dog2.bark()
}
fun interp(expr: ExprC, env: Env): Value {
    when(expr){
        is NumC -> return NumV(expr.num)
        is StringC -> return StringV(expr.s)
        is LamC -> return CloV(expr.params, expr.body, env)
        is IfC -> when(interp(expr.self, env)) {
            is TrueV -> (interp(expr.then, env))
            is FalseV -> (interp(expr.otherwise, env))
            else -> (throw Exception("TULI: If statement requires boolean condition."))
        }
        is AppC -> when(val appc = interp(expr.fn, env)){
            is CloV -> {
                val argVals = expr.args.map { arg -> (interp(arg, env)) }
                val bodyEnv = extendEnv(appc.env, appc.params, argVals as ArrayList<Value>)
                interp(appc.body, bodyEnv)
            }
            is PrimV -> {
                appc.fn(expr.args.map{ arg -> interp(arg, env)} as ArrayList<Value>)
            }
            else -> (throw Exception("TULI: attempt to call non-function"))
        }
        is IdC -> lookupEnv(env, expr.id)
    }
    return TrueV();
}

fun lookupEnvRec(env: Env, id: String) : Value{
    if (env.bindings.isEmpty()){
        throw Exception("TULI: Symbol Not Found.")
    }
    else if (id == env.bindings[0].name){
        return env.bindings[0].value
    }
    return lookupEnvRec(Env(ArrayList(env.bindings.drop(1))), id)
}
fun lookupEnv(env: Env, id: String): Value{
    for (i in env.bindings.indices){
        if (env.bindings[i].name==id){

        }
    }
}

fun extendEnv(env: Env, s: ArrayList<IdC>, n: ArrayList<Value>): Env {
    if (s.size != n.size) {
        throw Exception ("TULI: Unequal Args and Params.")
    }
    for(i in env.bindings.indices) {
        env.bindings.add(Binding(s[i].id, n[i]))
    }
    return env
}


fun Int.isOdd(): Boolean {
    return this % 2 == 1
}

fun isEven(number: Int): Boolean {
    return number % 2 == 0
}

fun print10Numbers() {
    for(i in 1..10) {
        println(i)
    }
}

fun myAdd(Val1: Value, Val2: Value): Value {
    if(Val1 is NumV && Val2 is NumV){
        NumV(Val1.n + Val2.n)
    }
    else{
        throw Exception("TULI: Invalid usage of + operator")
    }
}

fun mySub(Val1: Value, Val2: Value): Value {
    if(Val1 is NumV && Val2 is NumV){
        NumV(Val1.n - Val2.n)
    }
    else{
        throw Exception("TULI: Invalid usage of - operator")
    }
}

fun myMult(Val1: Value, Val2: Value): Value {
    if(Val1 is NumV && Val2 is NumV){
        NumV(Val1.n * Val2.n)
    }
    else{
        throw Exception("TULI: Invalid usage of * operator")
    }
}

fun myDiv(Val1: Value, Val2: Value): Value {
    if(Val1 is NumV && Val2 is NumV && Val2.n != 0){
        NumV(Val1 / Val2)
    }
    else if(Val1 is NumV && Val2 is NumV && Val2.n == 0){
        throw Exception("TULI: Divide by 0.")
    }
    else{
        throw Exception("TULI: Invalid usage of / operator")
    }
}

fun myLessThanOrEqual(Val1: Value, Val2: Value): Value {
    if(Val1 is NumV && Val2 is NumV){
       if(Val1.n <= Val2.n){
            (TrueV)
        }
        else{
           (FalseV)
        }
    }
    else{
        throw Exception("TULI: Invalid usage of  <=  operator")
    }
}

fun serialize(val1 : Value): String {
    when(val1){
        (TrueV) -> "true"
        (FalseV) -> "false"
        (NumV) -> val1.n.toString()
        (StringV) -> val1.s
        (CloV) -> "#procedure"
    }
}

