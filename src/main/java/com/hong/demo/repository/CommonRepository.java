package com.hong.demo.repository;

import java.util.Collection;

public interface  CommonRepository<T> {

    public Iterable<T> findAll();
    public T findById(String id);

    public T save(T domain);
    public Iterable<T> save(Collection<T> domains);

    public void delete(T domain);
}