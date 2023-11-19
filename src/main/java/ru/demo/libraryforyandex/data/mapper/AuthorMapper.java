package ru.demo.libraryforyandex.data.mapper;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import ru.demo.libraryforyandex.data.dto.AuthorData;
import ru.demo.libraryforyandex.data.dto.AuthorDto;

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

	@Select("select * from authors order by id")
	@Results(value = {
			@Result(property = "fullName", column = "full_name"),
			@Result(property = "birthDate", column = "birth_date"),
	})
	List<AuthorDto> findAll();

	@Insert("insert into authors(full_name, birth_date) values (#{fullName}, #{birthDate})")
	@SelectKey(statement = "select currval('authors_id_seq')", keyProperty = "id", before = false, resultType = long.class)
	Long save(AuthorDto dto);

	@Select("select * from authors where id = #{id}")
	@Results(value = {
			@Result(property = "fullName", column = "full_name"),
			@Result(property = "birthDate", column = "birth_date"),
	})
	Optional<AuthorDto> findById(Long id);

	@Update("update authors set full_name = #{dto.fullName}, birth_date = #{dto.birthDate} where id=#{id}")
	void update(@Param("id") Long id, AuthorDto dto);

	@Delete("delete from authors where id = #{id}")
	void delete(Long id);

	@Select("""
			<script>
			select id from authors 
			where id in 
			  <foreach item='item' index='index' collection='ids' open='(' separator=',' close=')'>
				  #{item}
			  </foreach>
			</script>
			""")
	Set<Long> getAuthorsIdsByIdSet(@Param("ids") Set<Long> ids);

	@Select("""
			select count(*) > 0 
			from author_book
			where author_id = #{id}
			""")
	boolean hasBookRelation(@Param("id") Long id);

}
