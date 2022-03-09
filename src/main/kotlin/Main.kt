
class Main {

    fun topInterp(s: String): String {
    return serialize(interp(parse(s), defineTopEnv()))
}

    fun defineTopEnv(): Env {
    val bindingList = ArrayList<Binding>()
    bindingList.add(Binding("true", TrueV()))
    bindingList.add(Binding("false", FalseV()))
    bindingList.add(Binding("+", PrimV({ x: Value, y: Value -> myAdd(x, y) })))
    bindingList.add(Binding("*", PrimV({ x: Value, y: Value -> myMult(x, y) })))
    bindingList.add(Binding("-", PrimV({ x: Value, y: Value -> mySub(x, y) })))
    bindingList.add(Binding("/", PrimV({ x: Value, y: Value -> myDiv(x, y) })))
    bindingList.add(Binding("<=", PrimV({ x: Value, y: Value -> myLessThanOrEqual(x, y) })))
    bindingList.add(Binding("equal?", PrimV({ x: Value, y: Value -> myLessThanOrEqual(x, y) })))
    bindingList.add(Binding("error", PrimV({ x: Value, y: Value -> myLessThanOrEqual(x, y) })))
    return Env(bindingList)
}

    fun interp(expr: ExprC, env: Env): Value {
    when(expr){
        is NumC -> return NumV(expr.num)
        is StringC -> return StringV(expr.s)
        is LamC -> return CloV(expr.params, expr.body, env)
        is IfC -> when(interp(expr.self, env)) {
            is TrueV -> return (interp(expr.then, env))
            is FalseV -> return (interp(expr.otherwise, env))
            else -> (throw Exception("TULI: If statement requires boolean condition."))
        }
        is AppC -> when(val appc = interp(expr.fn, env)){
            is CloV -> {
                val argVals = expr.args.map { arg -> (interp(arg, env)) }
                val bodyEnv = extendEnv(appc.env, appc.params, argVals as ArrayList<Value>)
                return interp(appc.body, bodyEnv)
            }
            is PrimV -> {
                return appc.fn(interp(expr.args[0], env), interp(expr.args[1], env))
            }
            else -> (throw Exception("TULI: attempt to call non-function"))
        }
        is IdC -> return lookupEnv(env, expr.id)
    }
    return TrueV()
}

    fun lookupEnv(env: Env, id: String): Value{
    for (i in env.bindings.indices){
        if (env.bindings[i].name==id){
            return env.bindings[i].value
        }
    }
    throw Exception("TULI: Symbol Not Found.")
}

    fun extendEnv(env: Env, s: ArrayList<IdC>, n: ArrayList<Value>): Env {
    if (s.size != n.size) {
        throw Exception ("TULI: Unequal Args and Params.")
    }
    for(i in s.indices) {
        env.bindings.add(Binding(s[i].id, n[i]))
    }
    return env
}

    fun myAdd(Val1: Value, Val2: Value): Value {
    if(Val1 is NumV && Val2 is NumV){
        return NumV(Val1.n + Val2.n)
    }
    else{
        throw Exception("TULI: Invalid usage of + operator")
    }
}

    fun mySub(Val1: Value, Val2: Value): Value {
    if(Val1 is NumV && Val2 is NumV){
        return NumV(Val1.n - Val2.n)
    }
    else{
        throw Exception("TULI: Invalid usage of - operator")
    }
}

    fun myMult(Val1: Value, Val2: Value): Value {
    if(Val1 is NumV && Val2 is NumV){
        return NumV(Val1.n * Val2.n)
    }
    else{
        throw Exception("TULI: Invalid usage of * operator")
    }
}

    fun myDiv(Val1: Value, Val2: Value): Value {
    if(Val1 is NumV && Val2 is NumV && Val2.n != 0){
        return NumV(Val1.n / Val2.n)
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
            return TrueV()
        }
        else{
            return FalseV()
        }
    }
    else{
        throw Exception("TULI: Invalid usage of  <=  operator")
    }
}

    fun serialize(val1 : Value): String {
    when(val1) {
        is TrueV -> return "true"
        is FalseV -> return "false"
        is NumV -> return val1.n.toString()
        is StringV -> return val1.s
        is CloV -> return "#procedure"
    }
    throw Exception("TULI: not a return type.")
}

    fun myEqual(Val1: Value, Val2: Value): Value {
        if(Val1 is NumV && Val2 is NumV) {
            if (Val1.n == Val2.n) {
                return TrueV()
            } else {
                return FalseV()
            }
        }
        else if(Val1 is StringV && Val2 is StringV){
            if(Val1.s == Val2.s){
                return TrueV()
            }
            else{
                return FalseV()
            }
        }
        else if(Val1 is TrueV || Val2 is FalseV){
            return FalseV()
        }
        else if(Val1 is TrueV && Val2 is TrueV){
            return TrueV()
        }
        else{
            throw Exception("TULI: Invalid usage of  =  operator")
        }
    }
}