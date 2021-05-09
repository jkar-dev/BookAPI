package com.study.bookservice.dao

import com.study.bookservice.entity.BookEntity
import com.study.bookservice.entity.BookDetailsEntity
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select
import org.springframework.stereotype.Repository

@Mapper
@Repository
interface BookDao {

    @Select("SELECT COUNT(id) from books")
    fun getCountOfAllBooks() : Int

    @Select("SELECT id, title, author FROM books LIMIT #{limit} OFFSET #{offset}")
    fun getAllBooksShort(offset : Int, limit : Int): List<BookEntity>

    @Insert("INSERT INTO books (title, author, description, page_count, publisher, publish_date) " +
                "VALUES (#{title}, #{author}, #{description}, #{pageCount}, #{publisher}, #{publishDate})" )
    fun insertBook(book: BookDetailsEntity)

    @Select("SELECT * FROM books WHERE id = #{id}")
    fun getBookById(id : Int) : BookDetailsEntity?
}



