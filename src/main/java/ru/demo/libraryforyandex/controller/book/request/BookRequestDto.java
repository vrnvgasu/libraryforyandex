package ru.demo.libraryforyandex.controller.book.request;

import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import ru.demo.libraryforyandex.data.RequestDto;

@Getter
@Setter
public class BookRequestDto implements RequestDto {

	private String title;

	private Integer publishedYear;

	private Set<Long> genreIds = new HashSet<>();

	private Set<Long> authorIds = new HashSet<>();

}
