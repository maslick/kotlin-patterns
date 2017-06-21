package com.maslick.test.patterns

/**
 * Created by maslick on 20/06/17.
 */


interface Command {
    fun execute()
}

class SendCommand(val str: String): Command {
    override fun execute() = println("Sending: $str")
}

class RecvCommand(val str: String): Command {
    override fun execute() = println("Receiving: $str")
}

class CommandProcessor {
    private val queue = ArrayList<Command>()

    fun addToQueue(command: Command):CommandProcessor {
        queue.add(command)
        return this
    }

    fun processCommands(): CommandProcessor {
        queue.forEach { it.execute() }
        queue.clear()
        return this
    }
}

fun main(args: Array<String>) {
    CommandProcessor()
            .addToQueue(SendCommand("haha"))
            .addToQueue(RecvCommand("world"))
            .processCommands()
}