package com.everymusic.app.mapper

import com.everymusic.app.model.Member
import org.apache.ibatis.annotations.*

@Mapper
interface MemberMapper {

    @Select("SELECT * FROM member WHERE mail_address = #{mailAddress}")
    fun findByMail(@Param("mailAddress") mailAddress: String): Member?

    @Select("SELECT * FROM member WHERE id = #{id}")
    fun findById(@Param("id") id: Long): Member?

    @Insert("""
        INSERT INTO member (
            member_name,
            mail_address,
            password,
            status
        ) VALUES (
            #{name},
            #{mailAddress},
            #{password},
            #{status}
        )
    """)
    fun insert(
        @Param("name") name: String,
        @Param("mailAddress") mailAddress: String,
        @Param("password") password: String,
        @Param("status") status: Boolean,
    ): Int

    @Update("""
        UPDATE member
        SET member_name = #{name},
            password = #{password}
        WHERE id = #{id}
    """)
    fun update(
        @Param("id") id: Long,
        @Param("name") name: String,
        @Param("password") password: String
    ): Int
}