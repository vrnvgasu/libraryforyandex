package ru.demo.libraryforyandex.controller.book.request;

import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.demo.libraryforyandex.data.RequestDto;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookRequestDto implements RequestDto {

	private String title;

	private Integer publishedYear;

	private Set<Long> genreIds = new HashSet<>();

	private Set<Long> authorIds = new HashSet<>();

}
