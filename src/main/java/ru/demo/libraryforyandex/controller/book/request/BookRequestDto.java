package ru.demo.libraryforyandex.controller.book.request;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import ru.demo.libraryforyandex.data.RequestDto;

@Getter
@Setter
public class BookRequestDto implements RequestDto {

	private String title;

	private Integer publishedYear;

	private List<Long> genreIds;

	private List<Long> authorIds;

}
