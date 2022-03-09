class Sexp(val value: String?){
    var vals: ArrayList<Sexp> = ArrayList();
    fun toIdC(): IdC{
        if (value == null ||
            value.toIntOrNull() != null ||
            (value.startsWith('"') && value.endsWith('"'))){
            throw Exception("TULI: Parameters must be a list 0 or more symbols")
        }
        return IdC(value);
    }
    fun toExprC() : ExprC{
        if(value==null){
            when{
                vals.size == 4 && vals[0].value=="if" ->
                    return IfC(vals[1].toExprC(), vals[2].toExprC(), vals[3].toExprC());
                vals.size >= 3 && vals[0].value=="let" && vals[1].value==null && vals[vals.size-2].value=="in" -> {
                    var letParams = ArrayList<IdC>();
                    var letArgs = ArrayList<ExprC>();
                    for (i in 1..(vals.size-3)){
                        if(vals[i].value==null && vals[i].vals.size==3 && vals[i].vals[1].value=="="){
                            letParams.add(vals[i].vals[0].toIdC())
                            letArgs.add(vals[i].vals[2].toExprC())
                        }else{
                            throw Exception("Tuli: Invalid usage of let");
                        }
                    }
                    return AppC(LamC(letParams, vals[vals.size-1].toExprC()), letArgs);
                }
                vals.size == 3 && vals[0].value=="fn" && vals[1].value==null ->{
                    var parms = ArrayList<IdC>();
                    vals[1].vals.map{ it.toIdC() }.toCollection(parms);

                    return LamC(parms, vals[2].toExprC());
                }
                vals.size >= 1 ->{
                    val first = vals.removeFirst().toExprC();
                    var args = ArrayList<ExprC>();
                    vals.map { it.toExprC() }.toCollection(args);
                    return AppC(first, args);
                }
                else ->
                    throw Exception("TULI: Not an expression");
            }
        } else {
            val num = value.toIntOrNull();
            if (num != null) return NumC(num);
            if (value.startsWith('"') && value.endsWith('"'))
                return StringC(value.substring(1, value.length-1))
            return IdC(value)
        }
    }
    fun add(sexp: Sexp): Sexp{
        vals.add(sexp);
        return sexp;
    }
    override fun toString(): String{
        if(value!=null) return value;
        var sb = StringBuilder("(");
        if(vals.size > 0){
            sb.append(vals[0].toString());
            for (i in 1..(vals.size-1)){
                sb.append(" ");
                sb.append(vals[i].toString());
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
fun parseString(input : String) : Sexp{
    val bell: String = 0x7.toChar().toString();
    var strings: ArrayList<String> = ArrayList();

    val iter = input.replace(Regex("\".*?\"")) {
        strings.add(it.value);
        bell
    }.replace(Regex("[{\\[\\(]"), " ( ")
        .replace(Regex("[}\\]\\)]"), " ) ")
        .trim()
        .split(Regex("\\s\\s*"));
    var stack = arrayListOf(Sexp(null));
    try {
        for (str in iter) {
            when (str) {
                "(" -> stack.add(stack.last().add(Sexp(null)));
                ")" -> stack.removeLast();
                bell -> stack.last().add(Sexp(strings.removeFirst()));
                else -> stack.last().add(Sexp(str));
            }
        }
    }catch (e: NoSuchElementException){
        throw Exception("TULI: Mismatched parens in input string (${iter.joinToString(" ")}).")
    }
    if(stack.size != 1){
        throw Exception("TULI: Mismatched parens in input string (${iter.joinToString(" ")}).")
    }
    return stack.first().vals[0];
}
public fun parse(input: String): ExprC{
    return parseString(input).toExprC();
}