package org.govmix.fatturapa.parer.beans;
import java.util.Map;

import org.govmix.fatturapa.parer.versamento.request.UnitaDocumentaria;


public class UnitaDocumentariaBean {

	private UnitaDocumentaria unitaDocumentaria;
	private Map<String, byte[]> multiparts;
	private AbstractUnitaDocumentariaInput input;

	public UnitaDocumentaria getUnitaDocumentaria() {
		return unitaDocumentaria;
	}
	public void setUnitaDocumentaria(UnitaDocumentaria unitaDocumentaria) {
		this.unitaDocumentaria = unitaDocumentaria;
	}
	public Map<String, byte[]> getMultiparts() {
		return multiparts;
	}
	public void setMultiparts(Map<String, byte[]> multiparts) {
		this.multiparts = multiparts;
	}
	public AbstractUnitaDocumentariaInput getInput() {
		return input;
	}
	public void setInput(AbstractUnitaDocumentariaInput input) {
		this.input = input;
	}
	
}
