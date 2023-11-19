package ru.demo.libraryforyandex.data.mapper;

import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import ru.demo.libraryforyandex.data.dto.BookData;
import ru.demo.libraryforyandex.data.dto.BookGenreAuthorData;

@Mapper
public interface BookMapper {

	@Select("select *, b.id as bookId from books b where id = #{id}")
	@Results(value = {
			@Result(property = "genres", javaType = List.class,
					column = "bookId", many = @Many(select = "ru.demo.libraryforyandex.data.mapper.GenreMapper.findByBookId")),
			@Result(property = "authors", javaType = List.class,
					column = "bookId", many = @Many(select = "ru.demo.libraryforyandex.data.mapper.AuthorMapper.findByBookId")),
	})
	Optional<BookData> getBookBy(@Param("id") Long id);

	@Select("""
			select
			  b.id as bookId,
			  b.title as bookTitle,
			  b.published_year as bookPublishedYear,
			  g.id as genreId,
			  g.title as genreTitle,
			  a.id as authorId,
			  a.full_name as authorFullName,
			  a.birth_date as authorBirthDate
			from books b
			  left join author_book ab on ab.book_id = b.id
			  left join authors a on a.id = ab.author_id
			  left join book_genre bg on bg.book_id = b.id
			  left join genres g on g.id = bg.genre_id
			where
			  lower(a.full_name) like '%' || COALESCE(lower(#{authorName}) || '%', '%')
			  and lower(g.title) like COALESCE(lower(#{genre}), '%')
			order by b.id
			""")
	List<BookGenreAuthorData> getAllBookGenreAuthor(@Param("authorName") String authorName, @Param("genre") String genre);

	@Insert("insert into books(title, published_year) values (#{title}, #{publishedYear})")
	@SelectKey(statement = "select currval('books_id_seq')", keyProperty = "id", before = false, resultType = long.class)
	Long save(BookData dto);

	@Update("update books set title = #{dto.title}, published_year = #{dto.publishedYear} where id=#{id}")
	void update(@Param("id") Long id, BookData dto);

	@Delete("""
			delete from book_genre where book_id = #{id};
			delete from author_book where book_id = #{id};
			delete from books where id = #{id};
			""")
	void delete(Long id);

}
