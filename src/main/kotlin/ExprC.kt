abstract class ExprC

class NumC(val num: Int) : ExprC(){
    override fun toString(): String { return "(NumC $num)" }
}
class IdC(val id: String) : ExprC(){
    override fun toString(): String { return "(IdC $id)" }
}
class StringC(val s: String) : ExprC(){
    override fun toString(): String { return "(StringC \"$s\")" }
}
class AppC(val fn: ExprC, val args: ArrayList<ExprC>) : ExprC(){
    override fun toString(): String{
        var sb = StringBuilder("(AppC ${fn.toString()} (list");
        for (arg in args){
            sb.append(" ");
            sb.append(arg.toString());
        }
        sb.append("))")
        return sb.toString();
    }
}
class LamC(val params: ArrayList<IdC>, val body: ExprC) : ExprC(){
    override fun toString(): String{
        var sb = StringBuilder("(LamC (");
        sb.append("list");
        for (id in params){
            sb.append(" ");
            sb.append(id.toString());
        }
        sb.append(") ");
        sb.append(body.toString());
        sb.append(")");
        return sb.toString();
    }
}
class IfC(val self: ExprC, val then: ExprC, val otherwise: ExprC) : ExprC(){
    override fun toString(): String{
        return "(IfC ${self.toString()} ${then.toString()} ${otherwise.toString()})";
    }
}