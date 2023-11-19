package ru.demo.libraryforyandex.controller.book.dto.response;

import java.time.LocalDate;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorResponseDto {

	private Long id;

	private String fullName;

	private LocalDate birthDate;

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		AuthorResponseDto author = (AuthorResponseDto) o;
		return id.equals(author.id) && fullName.equals(author.fullName) && Objects.equals(
				birthDate,
				author.birthDate
		);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, fullName, birthDate);
	}

}
