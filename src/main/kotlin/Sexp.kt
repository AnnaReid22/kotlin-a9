class Sexp(val value: String?){
    var vals: ArrayList<Sexp> = ArrayList()
    fun add(sexp: Sexp): Sexp{
        vals.add(sexp)
        return sexp
    }
    fun displayLine(){
        display()
        print("\n")
    }
    fun displayVals(){
        print("(")
        if(vals.size > 0){
            vals[0].display()
            for (i in 1..(vals.size-1)){
                print(" ")
                vals[i].display()
            }
        }
        print(")")
    }
    fun display(){
        when(value){
            null -> displayVals()
            else -> print(value)
        }
    }
}

fun parseString(input : String) : Sexp?{
    val bell: String = 0x7.toChar().toString()
    val strings: ArrayList<String> = ArrayList()

    val iter = input.replace(Regex("\".*?\"")) {
        strings.add(it.value)
        bell
    }.replace(Regex("[{\\[\\(]"), " ( ")
        .replace(Regex("[}\\]\\)]"), " ) ")
        .trim()
        .split(Regex("\\s\\s*"))
    val stack = arrayListOf(Sexp(null))
    try {
        for (str in iter) {
            when (str) {
                "(" -> stack.add(stack.last().add(Sexp(null)))
                ")" -> stack.removeLast()
                bell -> stack.last().add(Sexp(strings.removeFirst()))
                else -> stack.last().add(Sexp(str))
            }
        }
    }catch (e: NoSuchElementException){
        throw Exception("TULI: Mismatched parens in input string (${iter.joinToString(" ")}).")
    }
    if(stack.size != 1){
        throw Exception("TULI: Mismatched parens in input string (${iter.joinToString(" ")}).")
    }
    return stack.first()
}
