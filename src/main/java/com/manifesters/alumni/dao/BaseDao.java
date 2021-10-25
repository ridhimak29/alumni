package com.manifesters.alumni.dao;

public class BaseDao<T> implements Repository<T> {
    @Override
    public T insert(T t) {
        return null;
    }

    @Override
    public T getById(Integer id) {
        return null;
    }

    @Override
    public boolean update(T t) {
        return false;
    }

    @Override
    public boolean deleteById(Integer id) {
        return false;
    }
}
