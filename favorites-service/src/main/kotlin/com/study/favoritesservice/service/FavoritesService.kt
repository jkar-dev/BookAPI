package com.study.favoritesservice.service

import com.study.favoritesservice.extentions.mapToDto
import com.study.favoritesservice.dao.FavoritesDao
import com.study.favoritesservice.dto.Book
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class FavoritesService(private val favoritesDao: FavoritesDao) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    fun getFavorites(): List<Book> {
        val favorites = favoritesDao.getFavorites().map { it.mapToDto() }
        logger.info("Favorites count: ${favorites.size}")
        return favorites
    }

    fun tryToAddToFavorites(bookId: Int): Boolean {
        val count = favoritesDao.checkIfBookExists(bookId)
        if (count < 1) {
            logger.warn("There is no book with id = $bookId")
            return false
        }
        val insertedRows = favoritesDao.addToFavorites(bookId)
        return insertedRows >= 1
    }

    fun tryToDeleteFromFavorites(bookId: Int) : Boolean {
        val deletedRows = favoritesDao.deleteFromFavorites(bookId)
        return deletedRows >= 1
    }

}