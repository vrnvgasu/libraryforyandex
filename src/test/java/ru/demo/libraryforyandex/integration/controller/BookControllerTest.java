package ru.demo.libraryforyandex.integration.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import ru.demo.libraryforyandex.controller.book.dto.response.BookResponseDto;
import ru.demo.libraryforyandex.controller.book.request.BookRequestDto;
import ru.demo.libraryforyandex.exception.dto.ErrorResponse;

@AutoConfigureMockMvc
public class BookControllerTest extends BaseControllerTest {

	private static final String GENRE = "Fantasy";
	private static final String AUTHOR = "Tolstoy";

	private static final Long ID = 5L;

	private static final String URL = "/api/v1/books";

	@Nested
	class FindAll {

		@Test
		void testFindAll() throws Exception {
			MvcResult mvcResult = mockMvc.perform(get(URL)
							.contentType(MediaType.APPLICATION_JSON)
							.accept(MediaType.APPLICATION_JSON)
					)
					.andExpect(status().isOk())
					.andReturn();

			var response = objectMapper.readValue(
					mvcResult.getResponse().getContentAsString(),
					new TypeReference<List<BookResponseDto>>() {
					}
			);

			assertThat(response).hasSize(5);
		}

		@Test
		void testFindAllByGenreAndAuthorSuccess() throws Exception {
			MvcResult mvcResult = mockMvc.perform(get(URL)
							.param("genre", GENRE)
							.param("author", AUTHOR)
							.contentType(MediaType.APPLICATION_JSON)
							.accept(MediaType.APPLICATION_JSON)
					)
					.andExpect(status().isOk())
					.andReturn();

			var response = objectMapper.readValue(
					mvcResult.getResponse().getContentAsString(),
					new TypeReference<List<BookResponseDto>>() {
					}
			);

			assertThat(response).hasSize(1);
		}

		@Test
		void testFindAllByGenreAndAuthorFailedOnGenre() throws Exception {
			MvcResult mvcResult = mockMvc.perform(get(URL)
							.param("genre", "dummy")
							.param("author", AUTHOR)
							.contentType(MediaType.APPLICATION_JSON)
							.accept(MediaType.APPLICATION_JSON)
					)
					.andExpect(status().isNotFound())
					.andReturn();

			var response = objectMapper.readValue(
					mvcResult.getResponse().getContentAsString(),
					ErrorResponse.class
			);

			assertThat(response.message()).isEqualTo("Can't found genre with title dummy");
		}

		@Test
		void testFindAllByGenreAndAuthorFailedOnAuthor() throws Exception {
			MvcResult mvcResult = mockMvc.perform(get(URL)
							.param("genre", GENRE)
							.param("author", "dummy")
							.contentType(MediaType.APPLICATION_JSON)
							.accept(MediaType.APPLICATION_JSON)
					)
					.andExpect(status().isNotFound())
					.andReturn();

			var response = objectMapper.readValue(
					mvcResult.getResponse().getContentAsString(),
					ErrorResponse.class
			);

			assertThat(response.message()).isEqualTo("Can't found author with full_name like dummy");
		}

	}

	@Nested
	class FindOne {

		@Test
		void testFindById() throws Exception {
			MvcResult mvcResult = mockMvc.perform(get(URL + "/" + ID)
							.contentType(MediaType.APPLICATION_JSON)
							.accept(MediaType.APPLICATION_JSON)
					)
					.andExpect(status().isOk())
					.andReturn();

			var response = objectMapper.readValue(
					mvcResult.getResponse().getContentAsString(),
					BookResponseDto.class
			);

			assertThat(response.getId()).isEqualTo(ID);
		}

		@Test
		void testFindByIdNotFound() throws Exception {
			MvcResult mvcResult = mockMvc.perform(get(URL + "/" + 1000L)
							.contentType(MediaType.APPLICATION_JSON)
							.accept(MediaType.APPLICATION_JSON)
					)
					.andExpect(status().isNotFound())
					.andReturn();

			var response = objectMapper.readValue(
					mvcResult.getResponse().getContentAsString(),
					ErrorResponse.class
			);

			assertThat(response.message()).isEqualTo("Can't found entity with id 1000");
		}

	}

	@Nested
	class Create {

		@Test
		void testCreateSuccess() throws Exception {
			MvcResult mvcResult = mockMvc.perform(post(URL)
							.content(asJsonString(BookRequestDto.builder()
									.title("dummy")
									.publishedYear(2000)
									.genreIds(Set.of(1L, 2L, 3L))
									.authorIds(Set.of(1L, 2L, 3L))
									.build()))
							.contentType(MediaType.APPLICATION_JSON)
							.accept(MediaType.APPLICATION_JSON)
					)
					.andExpect(status().isOk())
					.andReturn();

			var response = objectMapper.readValue(
					mvcResult.getResponse().getContentAsString(),
					BookResponseDto.class
			);

			assertThat(response.getId()).isEqualTo(6L);
			assertThat(response.getTitle()).isEqualTo("dummy");
			assertThat(response.getPublishedYear()).isEqualTo(2000);
			assertThat(response.getGenres()).hasSize(3);
			assertThat(response.getAuthors()).hasSize(3);
		}

		@Test
		void testCreateFailedOnGenre() throws Exception {
			MvcResult mvcResult = mockMvc.perform(post(URL)
							.content(asJsonString(BookRequestDto.builder()
									.title("dummy")
									.publishedYear(2000)
									.genreIds(Set.of(1L, 2L, 3L, 100L, 101L))
									.authorIds(Set.of(1L, 2L, 3L))
									.build()))
							.contentType(MediaType.APPLICATION_JSON)
							.accept(MediaType.APPLICATION_JSON)
					)
					.andExpect(status().isNotFound())
					.andReturn();

			var response = objectMapper.readValue(
					mvcResult.getResponse().getContentAsString(),
					ErrorResponse.class
			);

			assertThat(response.message()).isEqualTo("Can't found genres with id in [100, 101]");
		}

		@Test
		void testCreateFailedOnAuthor() throws Exception {
			MvcResult mvcResult = mockMvc.perform(post(URL)
							.content(asJsonString(BookRequestDto.builder()
									.title("dummy")
									.publishedYear(2000)
									.genreIds(Set.of(1L, 2L, 3L))
									.authorIds(Set.of(1L, 2L, 3L, 100L, 101L))
									.build()))
							.contentType(MediaType.APPLICATION_JSON)
							.accept(MediaType.APPLICATION_JSON)
					)
					.andExpect(status().isNotFound())
					.andReturn();

			var response = objectMapper.readValue(
					mvcResult.getResponse().getContentAsString(),
					ErrorResponse.class
			);

			assertThat(response.message()).isEqualTo("Can't found authors with id in [100, 101]");
		}

	}

	@Nested
	class Update {

		@Test
		void testUpdateSuccess() throws Exception {
			MvcResult mvcResult = mockMvc.perform(put(URL + "/" + ID)
							.content(asJsonString(BookRequestDto.builder()
									.title("dummy")
									.publishedYear(2000)
									.genreIds(Set.of(1L, 2L, 3L))
									.authorIds(Set.of(1L, 2L, 3L))
									.build()))
							.contentType(MediaType.APPLICATION_JSON)
							.accept(MediaType.APPLICATION_JSON)
					)
					.andExpect(status().isOk())
					.andReturn();

			var response = objectMapper.readValue(
					mvcResult.getResponse().getContentAsString(),
					BookResponseDto.class
			);

			assertThat(response.getId()).isEqualTo(5L);
			assertThat(response.getTitle()).isEqualTo("dummy");
			assertThat(response.getPublishedYear()).isEqualTo(2000);
			assertThat(response.getGenres()).hasSize(3);
			assertThat(response.getAuthors()).hasSize(3);
		}

		@Test
		void testUpdateFailedOnGenre() throws Exception {
			MvcResult mvcResult = mockMvc.perform(put(URL + "/" + ID)
							.content(asJsonString(BookRequestDto.builder()
									.title("dummy")
									.publishedYear(2000)
									.genreIds(Set.of(1L, 2L, 3L, 100L, 101L))
									.authorIds(Set.of(1L, 2L, 3L))
									.build()))
							.contentType(MediaType.APPLICATION_JSON)
							.accept(MediaType.APPLICATION_JSON)
					)
					.andExpect(status().isNotFound())
					.andReturn();

			var response = objectMapper.readValue(
					mvcResult.getResponse().getContentAsString(),
					ErrorResponse.class
			);

			assertThat(response.message()).isEqualTo("Can't found genres with id in [100, 101]");
		}

		@Test
		void testUpdateFailedOnAuthor() throws Exception {
			MvcResult mvcResult = mockMvc.perform(put(URL + "/" + ID)
							.content(asJsonString(BookRequestDto.builder()
									.title("dummy")
									.publishedYear(2000)
									.genreIds(Set.of(1L, 2L, 3L))
									.authorIds(Set.of(1L, 2L, 3L, 100L, 101L))
									.build()))
							.contentType(MediaType.APPLICATION_JSON)
							.accept(MediaType.APPLICATION_JSON)
					)
					.andExpect(status().isNotFound())
					.andReturn();

			var response = objectMapper.readValue(
					mvcResult.getResponse().getContentAsString(),
					ErrorResponse.class
			);

			assertThat(response.message()).isEqualTo("Can't found authors with id in [100, 101]");
		}

	}

	@Nested
	class Delete {

		@Test
		void testDelete() throws Exception {
			mockMvc.perform(delete(URL + "/" + ID)
							.contentType(MediaType.APPLICATION_JSON)
							.accept(MediaType.APPLICATION_JSON)
					)
					.andExpect(status().isOk())
					.andReturn();
		}

	}

}
