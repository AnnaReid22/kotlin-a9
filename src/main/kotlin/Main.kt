fun main() {
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
    for(i in env.bindings.indices) {
        env.bindings.add(Binding(s[i].id, n[i]))
    }
    return env
}