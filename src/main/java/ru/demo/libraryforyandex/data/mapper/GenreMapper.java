package ru.demo.libraryforyandex.data.mapper;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import ru.demo.libraryforyandex.data.dto.GenreData;
import ru.demo.libraryforyandex.data.dto.GenreDto;

@Mapper
public interface GenreMapper {

	@Select("select count(*) > 0 from genres where lower(title) = lower(#{title})")
	boolean isExist(@Param("title") String title);

	@Select("select distinct g.* from genres g"
			+ " join book_genre bg on bg.genre_id = g.id"
			+ " where bg.book_id = #{bookId}")
	GenreData findByBookId(Integer bookId);

	@Select("select * from genres order by id")
	List<GenreDto> findAll();

	@Insert("insert into genres(title) values (#{title})")
	@SelectKey(statement="select currval('genres_id_seq')", keyProperty="id", before = false, resultType= long.class)
	Long save(GenreDto dto);

	@Select("select * from genres where id = #{id}")
	Optional<GenreDto> findById(Long id);

	@Update("update genres set title = #{dto.title} where id=#{id}")
	void update(@Param("id") Long id, GenreDto dto);

	@Delete("delete from genres where id = #{id}")
	void delete(Long id);

	@Select("""
			<script>
			select id from genres 
			where id in 
			  <foreach item='item' index='index' collection='ids' open='(' separator=',' close=')'>
				  #{item}
			  </foreach>
			</script>
			""")
	Set<Long> getGenresIdsByIdSet(@Param("ids") Set<Long> ids);

	@Select("""
			select count(*) > 0 
			from book_genre
			where genre_id = #{id}
			""")
	boolean hasBookRelation(@Param("id") Long id);

}
