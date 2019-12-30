package com.metowolf.api.controller

import com.alibaba.fastjson.JSONObject
import com.metowolf.api.dao.UserDao
import com.metowolf.api.entity.Response
import com.metowolf.api.entity.User
import com.metowolf.api.service.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping(value = ["/auth"])
class AuthController: BaseController() {

    @Autowired
    lateinit var authService: AuthService

    @Autowired
    lateinit var userDao: UserDao

    @RequestMapping(value = ["/token"], method = [RequestMethod.POST])
    fun getToken(@Valid @RequestBody query: User): Response {
        try {
            val username = query.username ?: throw IllegalArgumentException("用户名不能为空")
            val password = query.password ?: throw IllegalArgumentException("密码不能为空")
            val crypted = userDao.getPassword(username) ?: return error("用户不存在")
            val status = BCrypt.checkpw(password, crypted)
            if (!status) {
                return error("密码错误")
            }
            val info = userDao.get(username) ?: return error("获取用户信息失败")
            val result = JSONObject()
            result.put("id", info.id)
            result.put("username", info.username)
            result.put("email", info.email)
            val token = authService.sign(result.toJSONString())
            return success(token)
        } catch (e: Exception) {
            logger.error("获取令牌失败", e)
            return error("获取令牌失败")
        }
    }

    @RequestMapping(value = ["/me"], method = [RequestMethod.POST])
    fun renewToken(@RequestAttribute("username") username: String): Response {
        try {
            val info = userDao.get(username)
            return success(info)
        } catch (e: Exception) {
            logger.error("获取信息失败", e)
            return error("获取信息失败")
        }
    }

}