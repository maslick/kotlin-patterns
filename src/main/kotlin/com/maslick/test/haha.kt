package com.maslick.test

class Config(var buildType: String?, var version: String?)

var map = HashMap<String, Config?>().apply {
    put("hello", Config(null, null))
}

fun configurationFor(id: String) = map[id]?.apply {
    buildType = "DEBUG"
    version = "1.2"
}


fun main(args: Array<String>) {
    val conf = configurationFor("hello1")
    conf?.let {
        println("${conf.buildType} + ${conf.version}")
    }
}