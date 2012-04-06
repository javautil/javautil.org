package org.javautil.dataset.render;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;
import org.javautil.dataset.ColumnMetadata;
import org.javautil.dataset.Dataset;
import org.javautil.document.style.HorizontalAlignment;
import org.javautil.io.ResourceUtils;
import org.javautil.util.HTMLUtils;
import org.javautil.util.JavaScriptUtils;
import org.javautil.util.JavaScriptWriter;
import org.javautil.xml.ElementHelper;

public class MootoolsTableRenderer<T> extends HtmlTableDatasetRenderer<T> {
	// TODO has potential documents
	public static final String MOOTOOLS_CORE_SCRIPT = "classpath:/mootools-1.2.3-core-yc.js";
	// "http://ajax.googleapis.com/ajax/libs/mootools/1.2.3/mootools.js";

	public static final String MOOTOOLS_MORE_SCRIPT = "classpath:/mootools-1.2.3.1-more-yc.js";

	public static final String MOOTOOLS_TABLE_CSS = "classpath:/mootools_table.css";

	public static final String MOOTOOLS_TABLE_UTILS_SCRIPT = "classpath:/table_utils.js";

	public static final String MOOTOOLS_SORTING_SCRIPT = "classpath:/sorting_table.js";
	// "http://github.com/mixonic/tables_on_cows/raw/master/sorting_table.js";

	public static final String MOOTOOLS_PAGINATED_SCRIPT = "classpath:/paginating_table.js";
	// "http://github.com/mixonic/tables_on_cows/raw/master/paginating_table.js";

	private boolean sortable = true;

	private boolean paginating = false;

	private int pageSize = 20;

	private boolean embedJavaScript = true;

	private boolean embedCssStyles = false;

	private boolean toggleColumns = false;

	private boolean scrollable = false;

	private String scrollHeight = "10em";

	private boolean includeMootoolsScript = false;

	private boolean includeSortingTableScript = false;

	private boolean includePaginatingTableScript = false;

	private boolean includeTableUtilsScript = false;

	private boolean generateTableIdWhenUnspecified = true;

	private String sortAscendingColumnCssClassName = null;

	private String sortDescendingColumnCssClassName = null;

	private static long nextTableIdSequence = 1;

	private String paginatingListElementId = null;

	private final StringWriter domready = new StringWriter();

	private List<Long> unsortableColumnIndexes = new ArrayList<Long>();

	private final Log logger = LogFactory.getLog(getClass());

	public MootoolsTableRenderer() {
		super();
	}

	@Override
	protected void renderHeadRowData(final Long columnIndex, final Object data,
			final HorizontalAlignment ha) {
		super.renderHeadRowData(columnIndex, data, ha);
		final Element cell = getTable().getCellElement();
		if (unsortableColumnIndexes != null
				& unsortableColumnIndexes.contains(columnIndex)) {
			final String addCssClassName = "nosort";
			String cssClassName = cell.attributeValue("class", "");
			cssClassName = cssClassName.length() == 0 ? addCssClassName
					: cssClassName + " " + addCssClassName;
			ElementHelper.setAttribute(cell, "class", cssClassName);
		}
	}

	@Override
	protected void renderTableStart(final Dataset<T> dataset) throws Exception {
		String tableElementId = getTable().getTableTagAttributes().get("id");
		final boolean requiresId = isSortable() || isPaginating()
				|| isToggleColumns();
		if (tableElementId == null && requiresId) {
			if (isGenerateTableIdWhenUnspecified()) {
				tableElementId = getNextGeneratedTableId();
				getTable().getTableTagAttributes().put("id", tableElementId);
				if (logger.isDebugEnabled()) {
					logger.debug("assigned table id of " + tableElementId
							+ " automatically as one was not assigned");
				}
			} else {
				throw new IllegalStateException("no id attribute is specified "
						+ "in the tableTagAttributes");
			}
		}
		if (isScrollable()) {
			final Map<String, String> bodyAttributes = getTable()
					.getTableBodyTagAttributes();
			final Map<String, String> styles = new LinkedHashMap<String, String>();
			styles.put("visibility", "hidden");
			styles.put("overflow", "hidden");
			final String style = ElementHelper.asAttributeValue(styles, ';',
					':');
			bodyAttributes.put("style", style);
			bodyAttributes.put("height", getScrollHeight());
		}
		super.renderTableStart(dataset);
	}

	@Override
	protected void renderTableEnd(final Dataset<T> dataset,
			final List<ColumnMetadata> columns, final PrintWriter writer)
			throws Exception {
		if (isEmbedCssStyles()) {
			writer.println(getStyles());
		}
		if (isToggleColumns()) {
			writer.println(getToggleColumnsHTML(columns));
		}
		super.renderTableEnd(dataset, columns, writer);
		writer.println();
		if (isEmbedJavaScript()) {
			writer.println(getJavaScript());
		}
		writer.flush();
	}

	protected String getToggleColumnsHTML(final List<ColumnMetadata> columns)
			throws IOException {
		final StringWriter buffer = new StringWriter();
		final XMLWriter writer = getTable().getXMLWriter(buffer,
				isPrettyPrint());
		final Element container = DocumentHelper.createElement("ul");
		ElementHelper.setAttribute(container, "class", "datasetColumnTogglers");
		final String tableId = getTable().getTableTagAttributes().get("id");
		if (tableId == null) {
			throw new IllegalStateException("tableId is null");
		}
		int columnIndex = 0;
		for (final ColumnMetadata column : columns) {
			String heading = column.getHeading();
			if (heading == null) {
				heading = column.getColumnName();
			}
			final Element listItem = DocumentHelper.createElement("li");
			container.add(listItem);

			// checkbox
			final Element checkbox = DocumentHelper.createElement("input");
			final String checkboxId = tableId + "_column" + columnIndex;
			ElementHelper.setAttribute(checkbox, "id", checkboxId);
			ElementHelper.setAttribute(checkbox, "type", "checkbox");
			ElementHelper.setAttribute(checkbox, "checked", "checked");
			ElementHelper.setAttribute(checkbox, "onchange", "javascript: "
					+ "toggleTableColumn('" + tableId + "', " + columnIndex
					+ ", this.checked);");
			listItem.add(checkbox);

			// label
			final Element label = DocumentHelper.createElement("label");
			ElementHelper.setAttribute(label, "for", checkboxId);
			label.setText(heading);
			listItem.add(label);

			columnIndex++;
		}
		writer.write(container);
		return buffer.toString();
	}

	public boolean isSortable() {
		return sortable;
	}

	public void setSortable(final boolean sortable) {
		this.sortable = sortable;
	}

	public boolean isIncludeMootoolsScript() {
		return includeMootoolsScript;
	}

	public void setIncludeMootoolsScript(final boolean includeMootoolsScript) {
		this.includeMootoolsScript = includeMootoolsScript;
	}

	protected static synchronized String getNextGeneratedTableId() {
		if (nextTableIdSequence == Long.MAX_VALUE) {
			nextTableIdSequence = 1;
		}
		return "dataset_table" + nextTableIdSequence++;
	}

	protected void renderDomready() {
		final String tableElId = getTable().getTableTagAttributes().get("id");
		final StringBuilder paginator = new StringBuilder();
		final JavaScriptWriter js = JavaScriptWriter.getInstance(domready);
		if (isPaginating()) {
			final String paginatingListElementId = getPaginatingListElementId();
			if (paginatingListElementId == null) {
				throw new IllegalStateException("no paginatingListElementId "
						+ "property value was specified");
			}
			final List<Object> args = new ArrayList<Object>();
			args.add(JavaScriptUtils.asString(tableElId));
			args.add(JavaScriptUtils.asString(paginatingListElementId));
			final Map<String, String> paginatingTableOptions = new LinkedHashMap<String, String>();
			// paginatingTableOptions.put("details", "false");
			paginatingTableOptions.put("per_page",
					String.valueOf(getPageSize()));
			args.add(paginatingTableOptions);
			paginator.append(JavaScriptUtils.asConstructor("PaginatingTable",
					args));
		}
		if (isSortable()) {
			final Map<String, String> sortOptions = new LinkedHashMap<String, String>();
			// sortingTableOptions.put("details", "false");
			if (getSortAscendingColumnCssClassName() != null) {
				sortOptions.put("forward_sort_class",
						getSortAscendingColumnCssClassName());
			}
			if (getSortDescendingColumnCssClassName() != null) {
				sortOptions.put("reverse_sort_class",
						getSortDescendingColumnCssClassName());
			}
			if (paginator.length() > 0) {
				sortOptions.put("paginator", paginator.toString());
			}
			final List<Object> args = new ArrayList<Object>();
			args.add(JavaScriptUtils.asString(tableElId));
			if (sortOptions.size() > 0) {
				args.add(sortOptions);
			}
			js.printstmt(JavaScriptUtils.asConstructor("SortingTable", args));
		} else if (paginator.length() > 0) {
			js.printstmt(paginator.toString());
		}
		if (isScrollable()) {
			js.printstmt("makeScrollable('" + tableElId + "', '"
					+ getScrollHeight() + "')");
			js.printstmt("var table = $('" + tableElId + "')");
			js.printstmt("var tbody = table.getElement('tbody')");
			js.printstmt("tbody.setStyle('display', '')");
			js.printstmt("tbody.setStyle('overflow', '')");
			js.printstmt("tbody.setStyle('visibility', 'visible')");
		}
	}

	public String getStyles() {
		final StringBuilder s = new StringBuilder();
		s.append(HTMLUtils.asStylesheetTag(ResourceUtils
				.asResource(MOOTOOLS_TABLE_CSS)));
		return s.toString();
	}

	public String getJavaScript() {
		final StringWriter s = new StringWriter();
		final PrintWriter p = new PrintWriter(s);
		if (isIncludeMootoolsScript()) {
			p.println(HTMLUtils.asJavascriptTag(ResourceUtils
					.asResource(MOOTOOLS_CORE_SCRIPT)));
			p.println(HTMLUtils.asJavascriptTag(ResourceUtils
					.asResource(MOOTOOLS_MORE_SCRIPT)));
		}
		if (isIncludeSortingTableScript()) {
			p.println(HTMLUtils.asJavascriptTag(ResourceUtils
					.asResource(MOOTOOLS_SORTING_SCRIPT)));
		}
		if (isIncludePaginatingTableScript()) {
			p.println(HTMLUtils.asJavascriptTag(ResourceUtils
					.asResource(MOOTOOLS_PAGINATED_SCRIPT)));
		}
		if (isIncludeTableUtilsScript()) {
			p.println(HTMLUtils.asJavascriptTag(ResourceUtils
					.asResource(MOOTOOLS_TABLE_UTILS_SCRIPT)));
		}
		renderDomready();
		final String script = domready.toString();
		if (script.length() > 0) {
			p.println("<script type=\"text/javascript\">");
			JavaScriptWriter.getInstance(s).printclosure(
					"window.addEvent('domready', function() ", script, ");");
			p.println("</script>");
		}
		return s.toString();
	}

	public boolean isGenerateTableIdWhenUnspecified() {
		return generateTableIdWhenUnspecified;
	}

	public void setGenerateTableIdWhenUnspecified(
			final boolean generateTableIdWhenUnspecified) {
		this.generateTableIdWhenUnspecified = generateTableIdWhenUnspecified;
	}

	public boolean isPaginating() {
		return paginating;
	}

	public void setPaginating(final boolean paginating) {
		this.paginating = paginating;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(final int pageSize) {
		this.pageSize = pageSize;
	}

	public String getPaginatingListElementId() {
		return paginatingListElementId;
	}

	public void setPaginatingListElementId(final String paginatingListElementId) {
		this.paginatingListElementId = paginatingListElementId;
	}

	public boolean isIncludeSortingTableScript() {
		return includeSortingTableScript;
	}

	public void setIncludeSortingTableScript(
			final boolean includeSortingTableScript) {
		this.includeSortingTableScript = includeSortingTableScript;
	}

	public boolean isIncludePaginatingTableScript() {
		return includePaginatingTableScript;
	}

	public void setIncludePaginatingTableScript(
			final boolean includePaginatingTableScript) {
		this.includePaginatingTableScript = includePaginatingTableScript;
	}

	public boolean isEmbedCssStyles() {
		return embedCssStyles;
	}

	public void setEmbedCssStyles(final boolean embedCssStyles) {
		this.embedCssStyles = embedCssStyles;
	}

	public boolean isEmbedJavaScript() {
		return embedJavaScript;
	}

	public void setEmbedJavaScript(final boolean embedJavaScript) {
		this.embedJavaScript = embedJavaScript;
	}

	public String getSortAscendingColumnCssClassName() {
		return sortAscendingColumnCssClassName;
	}

	public void setSortAscendingColumnCssClassName(
			final String sortAscendingColumnCssClassName) {
		this.sortAscendingColumnCssClassName = sortAscendingColumnCssClassName;
	}

	public String getSortDescendingColumnCssClassName() {
		return sortDescendingColumnCssClassName;
	}

	public void setSortDescendingColumnCssClassName(
			final String sortDescendingColumnCssClassName) {
		this.sortDescendingColumnCssClassName = sortDescendingColumnCssClassName;
	}

	public List<Long> getUnsortableColumnIndexes() {
		return unsortableColumnIndexes;
	}

	public void setUnsortableColumnIndexes(
			final List<Long> unsortableColumnIndexes) {
		this.unsortableColumnIndexes = unsortableColumnIndexes;
	}

	public boolean isToggleColumns() {
		return toggleColumns;
	}

	public void setToggleColumns(final boolean toggleColumns) {
		this.toggleColumns = toggleColumns;
	}

	public boolean isIncludeTableUtilsScript() {
		return includeTableUtilsScript;
	}

	public void setIncludeTableUtilsScript(final boolean includeTableUtilsScript) {
		this.includeTableUtilsScript = includeTableUtilsScript;
	}

	public boolean isScrollable() {
		return scrollable;
	}

	public void setScrollable(final boolean scrollable) {
		this.scrollable = scrollable;
	}

	public String getScrollHeight() {
		return scrollHeight;
	}

	public void setScrollHeight(final String scrollHeight) {
		this.scrollHeight = scrollHeight;
	}
}