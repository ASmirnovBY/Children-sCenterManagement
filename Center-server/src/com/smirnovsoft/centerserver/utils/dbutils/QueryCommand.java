package com.smirnovsoft.centerserver.utils.dbutils;

import java.io.IOException;
import java.util.List;

public interface QueryCommand<T> {
     void insert() throws IOException;
     List<T> getAll();
     boolean delete();
}
