package com.maslick.test.patterns.behavioral

import java.util.*

/**
 * Created by maslick on 22/06/17.
 */

open class Currency(var amount: Int)

interface DispenseChain {
    fun setNextChain(nextChain: DispenseChain)
    fun dispense(currency: Currency)
}

class DollarDispenser(val dollars: Int): DispenseChain {
    private var chain: DispenseChain? = null

    override fun setNextChain(nextChain: DispenseChain) {
        chain = nextChain
    }

    override fun dispense(currency: Currency) {
        when (currency.amount >= dollars) {
            // dispense and delegate to the next
            true -> {
                val num = currency.amount / dollars
                val remainder = currency.amount % dollars
                println("Dispensing $num $dollars$ note(s)")
                if (remainder != 0) chain?.dispense(Currency(remainder)) ?: println("    couldn't dispense $remainder$")
            }
            // dispense
            false -> chain?.dispense(currency)
        }
    }
}

fun main(args: Array<String>) {
    val c1: DispenseChain = DollarDispenser(50)
    val c2: DispenseChain = DollarDispenser(20)
    val c3: DispenseChain = DollarDispenser(10)

    c1.setNextChain(c2)
    c2.setNextChain(c3)

    while(true) {
        println("Enter amount to dispense")
        val input = Scanner(System.`in`)
        val amount = input.nextInt()
        c1.dispense(Currency(amount))
    }
}