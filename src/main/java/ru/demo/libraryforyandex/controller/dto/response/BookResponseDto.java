package ru.demo.libraryforyandex.controller.dto.response;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BookResponseDto {

	private Long id;

	private String title;

	private Integer publishedYear;

	@Default
	private Set<GenreResponseDto> genres = new HashSet<>();

	@Default
	private Set<AuthorResponseDto> authors = new HashSet<>();

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		BookResponseDto book = (BookResponseDto) o;
		return id.equals(book.id) && title.equals(book.title) && publishedYear.equals(book.publishedYear);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, title, publishedYear);
	}

}
