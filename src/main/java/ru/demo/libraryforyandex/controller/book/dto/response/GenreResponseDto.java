package ru.demo.libraryforyandex.controller.book.dto.response;

import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.demo.libraryforyandex.data.RequestDto;

@Getter
@Setter
@Builder
public class GenreResponseDto implements RequestDto {

	private Long id;

	private String title;

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		GenreResponseDto genre = (GenreResponseDto) o;
		return id.equals(genre.id) && title.equals(genre.title);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, title);
	}

}
