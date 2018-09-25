package com.maslick.test

import kotlin.properties.Delegates

interface ValueChangedListener {
    fun onValueChanged()
}

class Observabbble(listener: ValueChangedListener) {
    var haha: String by Delegates.observable(
            initialValue = "",
            onChange = { property, oldValue, newValue ->
                if (oldValue!=newValue) {
                    listener.onValueChanged()
                }
            }
    )
}

class Notifier : ValueChangedListener {
    override fun onValueChanged() {
        println("text changed")
    }
}

fun main(args: Array<String>) {
    val data = Observabbble(Notifier())
    data.haha = "asd"
    data.haha = "asd"
    val a = 3
    println("a = ${a}")
    println("data = ${data.haha}")

    a.apply {
        println(this)
    }
}