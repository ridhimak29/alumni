package com.manifesters.alumni.dao;

public interface Repository<T> {
    T insert(T t);
    T getById(Integer id);
    boolean update(T t);
    boolean deleteById(Integer id);
}
