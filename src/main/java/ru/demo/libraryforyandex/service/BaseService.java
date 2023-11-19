package ru.demo.libraryforyandex.service;

import java.util.List;

public abstract class BaseService<T, E> {

	public abstract List<T> getAll();

	public abstract T create(E el);

	public abstract T update(Long id, E el);

	public abstract T findById(Long id);

	public abstract void delete(Long id);

}
