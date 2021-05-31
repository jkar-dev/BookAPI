package com.study.auth.dao

import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select
import org.springframework.stereotype.Repository

@Mapper
@Repository
interface ServiceDao {

    @Select("SELECT name_from FROM \"auth\".permissions WHERE name_to = #{serviceName}")
    fun getAllowedServices(serviceName : String) : List<String>
}