package com.smirnovsoft.centerclientapp.utils.report;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.File;

public interface Reporteble<T> {

    XWPFDocument createDocument(T t);

    void save(File file);
}
