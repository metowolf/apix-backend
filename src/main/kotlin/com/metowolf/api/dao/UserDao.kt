package com.metowolf.api.dao

import com.metowolf.api.entity.User
import org.apache.ibatis.annotations.*
import org.springframework.stereotype.Repository

@Mapper
@Repository
interface UserDao {

    @Results(id = "user", value = [
        Result(property = "id", column = "id"),
        Result(property = "username", column = "username"),
        Result(property = "email", column = "email"),
        Result(property = "password", column = "password"),
        Result(property = "createTime", column = "create_time"),
        Result(property = "createUser", column = "create_user"),
        Result(property = "updateTime", column = "update_time"),
        Result(property = "updateUser", column = "update_user")
    ])
    @Select("SELECT * FROM users WHERE deleted = 0")
    fun getAll(): List<User>

    @ResultMap("user")
    @Select(
        "SELECT id, username, email, create_time, update_time",
        "FROM users",
        "WHERE username = #{username} AND deleted = 0"
    )
    fun get(@Param("username") username: String): User?

    @Select(
            "SELECT password",
            "FROM users",
            "WHERE username = #{username} AND deleted = 0"
    )
    fun getPassword(@Param("username") username: String): String?
}