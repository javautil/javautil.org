package org.javautil.document.renderer;

import java.util.List;

import org.javautil.dataset.ColumnMetadata;

public interface ReorderColumnsRendererRequest {

	public List<String> getColumnOrder();

	public List<ColumnMetadata> getColumnMetadata();

}
