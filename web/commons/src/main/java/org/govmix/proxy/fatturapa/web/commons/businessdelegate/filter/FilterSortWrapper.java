package org.govmix.proxy.fatturapa.web.commons.businessdelegate.filter;

import org.openspcoop2.generic_project.beans.IField;
import org.openspcoop2.generic_project.expression.SortOrder;

public class FilterSortWrapper {

	private IField field;
	private SortOrder sortOrder;

	public IField getField() {
		return field;
	}
	public void setField(IField field) {
		this.field = field;
	}
	public SortOrder getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(SortOrder sortOrder) {
		this.sortOrder = sortOrder;
	}
	
}
