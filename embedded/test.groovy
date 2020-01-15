class Foo {

    // read only property
    final String name = "Pranit"// read only property with public getter and protected setter


    String language // A name declared with no access modifier generates a private field with
    // public getter and setter (i.e. a property).


    protected void setLanguage(String language) { this.language = language } // dynamically typed property

    def lastName
}


/*
 Logical Branching and Looping
*///Groovy supports the usual if — else syntax
def x = 3

if(x==1) {
    println "One"
} else if(x==2) {
    println "Two"
} else {
    println "X greater than Two"
}


//Groovy also supports the ternary operator:
def y = 10
def z = (y > 1) ? "worked" : "failed"
assert z == "worked"

//Groovy supports ‘The Elvis Operator’ too!
//Instead of using the ternary operator:displayName = user.name ? user.name : ‘Anonymous’
// We can write it:
displayName = user.name ?: "Anonymous"