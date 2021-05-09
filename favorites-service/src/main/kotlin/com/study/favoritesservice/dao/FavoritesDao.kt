package com.study.favoritesservice.dao

import com.study.favoritesservice.entity.BookEntity
import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select
import org.springframework.stereotype.Repository

@Mapper
@Repository
interface FavoritesDao {

    @Select("SELECT books.id, title, author FROM books " +
            "JOIN favorites ON books.id = favorites.book_id")
    fun getFavorites(): List<BookEntity>

    @Insert("INSERT INTO favorites (book_id) VALUES (#{bookId}) ON CONFLICT (book_id) DO NOTHING")
    fun addToFavorites(bookId: Int) : Int

    @Delete("DELETE FROM favorites WHERE book_id = #{bookId}")
    fun deleteFromFavorites(bookId: Int) : Int

    @Select("SELECT COUNT(id) FROM books WHERE id = #{bookId}")
    fun checkIfBookExists(bookId: Int) : Int
}