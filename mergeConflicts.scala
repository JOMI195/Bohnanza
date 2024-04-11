def foo() = {
    val x = 3;
    print("Very important function.\n");
    x;
}

def foo2() = {
    var y = 2;
    print("Bad implementation - var is bad");
    y;
}

def main(args: Array[String]): Unit = {
    val x = foo();
    print(s"Value of x is: ${x}.\n")
}