package ru.demo.libraryforyandex.data.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import ru.demo.libraryforyandex.data.dto.AuthorData;

@Mapper
public interface AuthorMapper {

	@Select("""
			select count(*) > 0 from authors 
			where lower(full_name) like '%' || lower(#{fullName} || '%')
			""")
	boolean isExist(@Param("fullName") String fullName);

	@Select("select distinct a.* from authors a"
			+ " join author_book ab on ab.author_id = a.id"
			+ " where ab.book_id = #{bookId}")
	AuthorData findByBookId(Integer bookId);

}
