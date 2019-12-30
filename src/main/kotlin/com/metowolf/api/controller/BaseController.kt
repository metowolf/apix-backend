package com.metowolf.api.controller

import com.metowolf.api.entity.Response
import com.metowolf.api.loggerFor

open class BaseController {

    fun success(data: Any?): Response {
        return Response(0, "success", data)
    }

    fun error(message: String): Response {
        return error(1, message)
    }

    fun error(code: Int, message: String): Response {
        return Response(code, message, null)
    }

    companion object {
        val logger = loggerFor<BaseController>()
    }

}