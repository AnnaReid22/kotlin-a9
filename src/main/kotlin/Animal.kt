abstract class Animal(
    val name: String,
    val legs: Int = 4
) {
    init {
        println("Hello, my name is $name")
    }
}