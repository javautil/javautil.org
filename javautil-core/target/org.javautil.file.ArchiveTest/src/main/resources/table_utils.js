function toggleTableColumn(table, columnIndex, state) {
	table = $(table);
	var tbody = table.getElement('tbody');
	var trs = tbody.getChildren();
	var row = null;
	while (row = trs.shift()) {
		var tds = row.getElements('td');
		tds[columnIndex].setStyle('display', state ? '' : 'none');
	}
	var thead = table.getElement('thead');
	if (!thead) {
		var outerTable = getParentTable(table.getParent());
		if (outerTable.get('tag') == 'table') {
			thead = outerTable.getElement('thead'); 
		}
	}
	trs = thead.getChildren();
	while (row = trs.shift()) {
		var ths = row.getElements('th');
		ths[columnIndex].setStyle('display', state ? '' : 'none');
	}
}

function getParentTable(el) {
	var parent = el.getParent();
	while (parent != null && parent != document.body) {
		if (parent.get('tag') == 'table') {
			return parent;
		}
		parent = parent.getParent();
	}
	return null;
}

// adapted from http://www.cssplay.co.uk/menu/tablescroll.html
function makeScrollable(table, scrollHeight) {
	table = $(table);
	var widths = fixTableColumnWidths(table, true);
	var outerTable = new Element('table', {
		'cellpadding': 0,
		'cellspacing': 0,
		'class': 'dataset-scroller'
	});
	var thead = table.getElement('thead');
	var tfoot = table.getElement('tfoot');
	if (thead != null) {
		outerTable.adopt(thead);
	}
	if (tfoot != null) {
		outerTable.adopt(tfoot);
	}
	var tbody = table.getElement('tbody');
	var outerTbody = new Element('tbody');
	outerTable.adopt(outerTbody);
	var outerTr = new Element('tr');
	var numberOfColumns = thead.getElements('th').length;
	var outerTd = new Element('td', {
		'colspan': numberOfColumns + 1
	});
	outerTbody.adopt(outerTr);
	outerTr.adopt(outerTd);
	var outerDiv = new Element('div', {		
		'styles': {
			'overflow': 'none',
			'overflow-y': 'scroll',
			'height': scrollHeight
		}
	});
	outerTd.adopt(outerDiv);
	outerTable.inject(table, 'after');
	outerDiv.adopt(table);
	applyFixedWidths(tbody, widths);
}

function applyFixedWidths(rowElement, widths) {
	var cellElements = $extend(rowElement.getElements('th'), 
		rowElement.getElements('td'));
	cellElements.each(function(cellElement, index) {
		cellElement.setStyle('width', widths[index]);
	});
}

function fixTableColumnWidths(table, hasScrollbar) {
	table = $(table);
	var thead = table.getElement('thead');
	var trs = thead.getChildren();
	var row = null;
	var widths = new Array();
	while (row = trs.shift()) {
		var scrollbarWidth = 14;
		var ths = row.getElements('th');
		ths.each(function (th, index) {			
			// TODO add extra room for the sort arrows
			var width = th.getComputedSize().width;			
			widths.push(width + 'px');
			th.setStyle('width', width + 'px');
		});
		// add room for the scrollbar
		if (hasScrollbar) {
			row.adopt(new Element('th', {
				'styles': {
					'padding': 0,
					'margin': 0,
					'width': scrollbarWidth + 'px'
				}
			}));
		}
	}
	return widths;
}