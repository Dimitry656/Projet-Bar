package dao;


import java.util.List;

public interface IDao<T> {
    T findById(int id);
    List<T> findAll();
    boolean insert(T obj);
    boolean update(T obj);
    boolean delete(int id);
}