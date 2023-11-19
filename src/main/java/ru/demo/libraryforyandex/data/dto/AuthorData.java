package ru.demo.libraryforyandex.data.dto;

import java.time.LocalDate;

public record AuthorData(Long id, String fullName, LocalDate birthDate) {

}
