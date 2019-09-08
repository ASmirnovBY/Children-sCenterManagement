package com.smirnovsoft.centerclientapp.utils.dbutils;

import java.io.IOException;
import java.util.List;

public interface RequestCommand<T> {
     T insert(T object) throws IOException;
     List<T> getAll();
     boolean delete(long ID);
     long nextID();
}
