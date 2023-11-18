package ru.demo.libraryforyandex.mapper;

import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import ru.demo.libraryforyandex.model.Book;

@Mapper
public interface BookMapper {
	@Select("select * from books where id = #{id}")
	Optional<Book> getBookBy(@Param("id") Long id);
}
