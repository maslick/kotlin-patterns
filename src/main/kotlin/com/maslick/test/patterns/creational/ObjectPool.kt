package com.maslick.test.patterns.creational


import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.util.*


abstract class ObjectPool<T> {
    private val expirationTime: Long

    private val locked: Hashtable<T, Long>
    private val unlocked: Hashtable<T, Long>

    init {
        expirationTime = 30000 // 30 seconds
        locked = Hashtable<T, Long>()
        unlocked = Hashtable<T, Long>()
    }

    protected abstract fun create(): T

    abstract fun validate(o: T): Boolean

    abstract fun expire(o: T)

    @Synchronized fun checkOut(): T {
        val now = System.currentTimeMillis()
        var t: T?
        if (unlocked.size > 0) {
            val e = unlocked.keys()
            while (e.hasMoreElements()) {
                t = e.nextElement()
                if (now - unlocked.get(t)!! > expirationTime) {
                    // object has expired
                    unlocked.remove(t)
                    expire(t)
                    t = null
                } else {
                    if (validate(t)) {
                        unlocked.remove(t)
                        locked.put(t, now)
                        return t
                    } else {
                        // object failed validation
                        unlocked.remove(t)
                        expire(t)
                        t = null
                    }
                }
            }
        }
        // no objects available, create a new one
        t = create()
        locked.put(t, now)
        return t
    }

    @Synchronized fun checkIn(t: T) {
        locked.remove(t)
        unlocked.put(t, System.currentTimeMillis())
    }
}

//The three remaining methods are abstract
//and therefore must be implemented by the subclass

internal class JDBCConnectionPool(driver: String, private val dsn: String, private val usr: String, private val pwd: String) : ObjectPool<Connection>() {

    init {
        try {
            Class.forName(driver).newInstance()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun create(): Connection {
        return DriverManager.getConnection(dsn, usr, pwd)
    }


    override fun expire(o: Connection) {
        try {
            o.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        }

    }

    override fun validate(o: Connection): Boolean {
        try {
            return !o.isClosed()
        } catch (e: SQLException) {
            e.printStackTrace()
            return false
        }

    }
}


fun main(args: Array<String>) {
    // Do something...

    // Create the ConnectionPool:
    val pool = JDBCConnectionPool("org.hsqldb.jdbcDriver", "jdbc:hsqldb://localhost/mydb", "sa", "secret")

    // Get a connection:
    val con = pool.checkOut()

    // Use the connection
    // Return the connection:
    pool.checkIn(con)

}