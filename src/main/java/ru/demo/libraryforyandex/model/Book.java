package ru.demo.libraryforyandex.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Book {

	private Long id;

	private String title;

	private Integer publishedYear;

}
