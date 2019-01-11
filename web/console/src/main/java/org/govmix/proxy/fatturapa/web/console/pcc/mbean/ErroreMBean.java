/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2019 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2019 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 3, as published by
 * the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.govmix.proxy.fatturapa.web.console.pcc.mbean;

import java.util.List;

import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.govmix.proxy.fatturapa.web.console.bean.FatturaElettronicaBean;
import org.govmix.proxy.fatturapa.web.console.pcc.bean.ErroreElaborazionePccBean;
import org.govmix.proxy.fatturapa.web.console.pcc.bean.EsitoPccBean;
import org.govmix.proxy.fatturapa.web.console.pcc.bean.TracciaPccEstesaBean;
import org.govmix.proxy.fatturapa.web.console.pcc.bean.TracciaTrasmissionePCCBean;
import org.govmix.proxy.fatturapa.web.console.pcc.form.ErroreForm;
import org.govmix.proxy.fatturapa.web.console.pcc.search.ErroreSearchForm;
import org.govmix.proxy.fatturapa.web.console.pcc.search.EsitoSearchForm;
import org.openspcoop2.generic_project.web.impl.jsf1.mbean.BaseMBean;
import org.openspcoop2.generic_project.web.table.PagedDataTable;

public class ErroreMBean extends BaseMBean<ErroreElaborazionePccBean, Long, ErroreSearchForm>{


	private PagedDataTable<List<EsitoPccBean>, ErroreForm, EsitoSearchForm> table;


	private FatturaElettronicaBean fattura = null;

	private String paginaFrom = null; // menu oppure dettaglioFattura

	private TracciaPccEstesaBean traccia = null;
	
	private TracciaTrasmissionePCCBean trasmissione = null;
	
	public ErroreMBean(){
		super(LoggerManager.getConsoleLogger());
		this.initTables();
		this.setOutcomes();

		this.log.debug("ErroreElaborazionePcc MBean");
	}

	public void initTables() {
		try{
			this.table = this.factory.getTableFactory().createPagedDataTable();
			this.getTable().setId("panelErrori"); 
			this.getTable().setEnableDelete(false);
			this.getTable().setShowAddButton(false);
			this.getTable().setShowDetailColumn(false);
			this.getTable().setShowSelectAll(true);
			this.getTable().setHeaderText("erroreElaborazione.label.ricercaErrori.tabellaRisultati");
			//	this.table.setMBean(this);
			this.getTable().setMetadata(this.getMetadata()); 

		}catch (Exception e) {

		}
	}

	private void setOutcomes(){
		this.getNavigationManager().setAnnullaOutcome(null);
		this.getNavigationManager().setDeleteOutcome(null);
		this.getNavigationManager().setDettaglioOutcome("dettaglioEsito");
		this.getNavigationManager().setFiltraOutcome(null);
		this.getNavigationManager().setInviaOutcome(null);
		this.getNavigationManager().setMenuActionOutcome(null);
		this.getNavigationManager().setModificaOutcome(null);
		this.getNavigationManager().setNuovoOutcome(null);
		this.getNavigationManager().setResetOutcome(null);
		this.getNavigationManager().setRestoreSearchOutcome(null);
	}
 
	@Override
	public void setSelectedElement(ErroreElaborazionePccBean selectedElement) {
		this.selectedElement = selectedElement;
	}
	
	public PagedDataTable<List<EsitoPccBean>, ErroreForm, EsitoSearchForm> getTable() {
		return table;
	}

	public void setTable(PagedDataTable<List<EsitoPccBean>, ErroreForm, EsitoSearchForm> table) {
		this.table = table;
	}

	public TracciaTrasmissionePCCBean getTrasmissione() {
		return trasmissione;
	}

	public void setTrasmissione(TracciaTrasmissionePCCBean trasmissione) {
		this.trasmissione = trasmissione;
	}

	public FatturaElettronicaBean getFattura() {
		return fattura;
	}

	public void setFattura(FatturaElettronicaBean fattura) {
		this.fattura = fattura;
	}

	public String getPaginaFrom() {
		return paginaFrom;
	}

	public void setPaginaFrom(String paginaFrom) {
		this.paginaFrom = paginaFrom;
	}

	public TracciaPccEstesaBean getTraccia() {
		return traccia;
	}

	public void setTraccia(TracciaPccEstesaBean traccia) {
		this.traccia = traccia;
	}
}
