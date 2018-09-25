package com.maslick.test.patterns.behavioral

/**
 * Created by maslick on 20/06/17.
 */

interface PrintingStrategy {
    fun invoke(str: String): String
}

class Printer(val strategy: PrintingStrategy) {
    fun printPage(string: String) {
        println(strategy.invoke(string))
    }
}

class LowerCaseStrategy: PrintingStrategy {
    override fun invoke(str: String) = str.toLowerCase()
}

class UpperCaseStrategy: PrintingStrategy {
    override fun invoke(str: String) = str.toUpperCase()
}

fun main(args: Array<String>) {
    val printerLow = Printer(LowerCaseStrategy())
    val printerUp  = Printer(UpperCaseStrategy())

    printerLow.printPage("Hi")
    printerUp.printPage("World")
}