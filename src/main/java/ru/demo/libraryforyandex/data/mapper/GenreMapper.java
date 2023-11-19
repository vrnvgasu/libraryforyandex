package ru.demo.libraryforyandex.data.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import ru.demo.libraryforyandex.data.dto.GenreData;

@Mapper
public interface GenreMapper {

	@Select("select count(*) > 0 from genres where lower(title) = lower(#{title})")
	boolean isExist(@Param("title") String title);

	@Select("select distinct g.* from genres g"
			+ " join book_genre bg on bg.genre_id = g.id"
			+ " where bg.book_id = #{bookId}")
	GenreData findByBookId(Integer bookId);

}
