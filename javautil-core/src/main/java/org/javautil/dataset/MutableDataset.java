package org.javautil.dataset;

import java.util.List;

import org.javautil.dataset.filter.MutableDatasetFilter;
import org.javautil.dataset.math.CollectionMathOperation;

/**
 * <T>
 * 
 * @author jjs@dbexperts.com
 * 
 */
public interface MutableDataset<T> extends Dataset<T> {

	public void addColumn(ColumnMetadata columnMeta);

	// todo resolve if we want this, I believe this should be handled by the
	// renderer
	public void addFooter(int columnIndex,
			CollectionMathOperation footerMathOper);

	public void appendRow(List<Object> arrayList);

	public void appendToRow(Integer rownum, List<T> values);

	public void appendToRow(Integer rownum, T[] values);

	public void applyFilters(MutableDatasetFilter... filters);

	public void applySorts(SortColumn... sorts);

	public void appendFooter();
	// TOdo this is very difficult
	// and should be left to a renderer to figure out whether
	// the result is a formula or a value
	// public void insertColumn(String columnName, int firstIndex,
	// int secondIndex, int insertIndex, MathOperation mathOper);
}
