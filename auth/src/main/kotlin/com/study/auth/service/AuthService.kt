package com.study.auth.service

import com.study.auth.dao.ServiceDao
import org.springframework.stereotype.Service

@Service
class AuthService(private val serviceDao: ServiceDao) {

    fun getAllowedServices(serviceName : String) : List<String> {
        return serviceDao.getAllowedServices(serviceName)
    }
}