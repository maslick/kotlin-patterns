package com.maslick.test.patterns.behavioral

/**
 * Created by maslick on 29/06/17.
 */

interface IObserver {
    fun onActivityChanged(activity: String)
    fun onEEChanged(ee: Double)
    fun onLocationChanged(location: String)
}

interface IObservable {
    fun addObserver(observer: IObserver)
    fun removeObserver(observer: IObserver)
    fun removeObservers()

    fun notifyActivityChanged(activity: String)
    fun notifyEEChanged(ee: Double)
    fun notifyLocationChanged(location: String)
}

class ActivityRecognizer: IObservable {
    val observers: MutableList<IObserver> = ArrayList()
    var ee: Double? = 1.0
        set(value) { notifyEEChanged(value!!) }
    var location: String? = "n/a"
        set(value) { notifyLocationChanged(value!!) }
    var activity: String? = "other"
        set(value) { notifyActivityChanged(value!!) }

    override fun addObserver(observer: IObserver) {
        if (!observers.contains(observer)) observers.add(observer)
    }

    override fun removeObserver(observer: IObserver) {
        if (observers.contains(observer)) observers.remove(observer)
    }

    override fun removeObservers() {
        observers.removeAll { true }
    }

    override fun notifyActivityChanged(activity: String) {
        observers.forEach { it.onActivityChanged(activity) }
    }

    override fun notifyEEChanged(ee: Double) {
        observers.forEach { it.onEEChanged(ee) }
    }

    override fun notifyLocationChanged(location: String) {
        observers.forEach { it.onLocationChanged(location) }
    }
}

class Observer: IObserver {
    override fun onActivityChanged(activity: String) {
        println("New activity: $activity")
    }

    override fun onEEChanged(ee: Double) {
        println("New ee: $ee")
    }

    override fun onLocationChanged(location: String) {
        println("New location: $location")
    }
}

fun main(args: Array<String>) {
    val recognizer = ActivityRecognizer()
    val observer = Observer()

    recognizer.addObserver(observer)

    recognizer.activity = "ahahaha"
    recognizer.ee = 12.0
    recognizer.location = "nono"

    recognizer.removeObservers()
}