package ru.demo.libraryforyandex.data.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookData {

	private Long id;

	private String title;

	private Integer publishedYear;

	private List<GenreData> genres;

	private List<AuthorData> authors;

}
