package com.maslick.test.patterns

import com.maslick.test.patterns.AuthorizationState.Authorized
import com.maslick.test.patterns.AuthorizationState.Unauthorized


/**
 * Created by maslick on 20/06/17.
 */

sealed class AuthorizationState {
    class Unauthorized : AuthorizationState()
    class Authorized(val userName: String) : AuthorizationState()
}

class Authorization {
    private var state:AuthorizationState = Unauthorized()

    fun login(user: String) {
        state = Authorized(user)
    }

    fun logout() {
        state = Unauthorized()
    }

    val isAuthorized: Boolean
        get() {
            when (state) {
                is Authorized -> return true
                else -> return false
            }
        }

    val userLogin: String
        get() {
            when (state) {
                is Authorized   -> return (state as Authorized).userName
                is Unauthorized -> return "Unknown"
            }
        }

    override fun toString() = "User '$userLogin' is logged in: $isAuthorized"
}

fun main(args: Array<String>) {
    val authorization = Authorization()
    println(authorization)

    authorization.login("admin")
    println(authorization)

    authorization.logout()
    println(authorization)
}
