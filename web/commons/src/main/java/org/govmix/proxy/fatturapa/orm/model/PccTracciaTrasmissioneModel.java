/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2017 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2017 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
package org.govmix.proxy.fatturapa.orm.model;

import org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissione;

import org.openspcoop2.generic_project.beans.AbstractModel;
import org.openspcoop2.generic_project.beans.IField;
import org.openspcoop2.generic_project.beans.Field;
import org.openspcoop2.generic_project.beans.ComplexField;


/**     
 * Model PccTracciaTrasmissione 
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class PccTracciaTrasmissioneModel extends AbstractModel<PccTracciaTrasmissione> {

	public PccTracciaTrasmissioneModel(){
	
		super();
	
		this.ID_TRACCIA = new org.govmix.proxy.fatturapa.orm.model.IdTracciaModel(new Field("idTraccia",org.govmix.proxy.fatturapa.orm.IdTraccia.class,"PccTracciaTrasmissione",PccTracciaTrasmissione.class));
		this.TS_TRASMISSIONE = new Field("tsTrasmissione",java.util.Date.class,"PccTracciaTrasmissione",PccTracciaTrasmissione.class);
		this.ID_PCC_TRANSAZIONE = new Field("idPccTransazione",java.lang.String.class,"PccTracciaTrasmissione",PccTracciaTrasmissione.class);
		this.ESITO_TRASMISSIONE = new Field("esitoTrasmissione",java.lang.String.class,"PccTracciaTrasmissione",PccTracciaTrasmissione.class);
		this.STATO_ESITO = new Field("statoEsito",java.lang.String.class,"PccTracciaTrasmissione",PccTracciaTrasmissione.class);
		this.GDO = new Field("gdo",java.util.Date.class,"PccTracciaTrasmissione",PccTracciaTrasmissione.class);
		this.DATA_FINE_ELABORAZIONE = new Field("dataFineElaborazione",java.util.Date.class,"PccTracciaTrasmissione",PccTracciaTrasmissione.class);
		this.DETTAGLIO_ERRORE_TRASMISSIONE = new Field("dettaglioErroreTrasmissione",java.lang.String.class,"PccTracciaTrasmissione",PccTracciaTrasmissione.class);
		this.ID_EGOV_RICHIESTA = new Field("idEgovRichiesta",java.lang.String.class,"PccTracciaTrasmissione",PccTracciaTrasmissione.class);
		this.PCC_TRACCIA_TRASMISSIONE_ESITO = new org.govmix.proxy.fatturapa.orm.model.PccTracciaTrasmissioneEsitoModel(new Field("PccTracciaTrasmissioneEsito",org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissioneEsito.class,"PccTracciaTrasmissione",PccTracciaTrasmissione.class));
	
	}
	
	public PccTracciaTrasmissioneModel(IField father){
	
		super(father);
	
		this.ID_TRACCIA = new org.govmix.proxy.fatturapa.orm.model.IdTracciaModel(new ComplexField(father,"idTraccia",org.govmix.proxy.fatturapa.orm.IdTraccia.class,"PccTracciaTrasmissione",PccTracciaTrasmissione.class));
		this.TS_TRASMISSIONE = new ComplexField(father,"tsTrasmissione",java.util.Date.class,"PccTracciaTrasmissione",PccTracciaTrasmissione.class);
		this.ID_PCC_TRANSAZIONE = new ComplexField(father,"idPccTransazione",java.lang.String.class,"PccTracciaTrasmissione",PccTracciaTrasmissione.class);
		this.ESITO_TRASMISSIONE = new ComplexField(father,"esitoTrasmissione",java.lang.String.class,"PccTracciaTrasmissione",PccTracciaTrasmissione.class);
		this.STATO_ESITO = new ComplexField(father,"statoEsito",java.lang.String.class,"PccTracciaTrasmissione",PccTracciaTrasmissione.class);
		this.GDO = new ComplexField(father,"gdo",java.util.Date.class,"PccTracciaTrasmissione",PccTracciaTrasmissione.class);
		this.DATA_FINE_ELABORAZIONE = new ComplexField(father,"dataFineElaborazione",java.util.Date.class,"PccTracciaTrasmissione",PccTracciaTrasmissione.class);
		this.DETTAGLIO_ERRORE_TRASMISSIONE = new ComplexField(father,"dettaglioErroreTrasmissione",java.lang.String.class,"PccTracciaTrasmissione",PccTracciaTrasmissione.class);
		this.ID_EGOV_RICHIESTA = new ComplexField(father,"idEgovRichiesta",java.lang.String.class,"PccTracciaTrasmissione",PccTracciaTrasmissione.class);
		this.PCC_TRACCIA_TRASMISSIONE_ESITO = new org.govmix.proxy.fatturapa.orm.model.PccTracciaTrasmissioneEsitoModel(new ComplexField(father,"PccTracciaTrasmissioneEsito",org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissioneEsito.class,"PccTracciaTrasmissione",PccTracciaTrasmissione.class));
	
	}
	
	

	public org.govmix.proxy.fatturapa.orm.model.IdTracciaModel ID_TRACCIA = null;
	 
	public IField TS_TRASMISSIONE = null;
	 
	public IField ID_PCC_TRANSAZIONE = null;
	 
	public IField ESITO_TRASMISSIONE = null;
	 
	public IField STATO_ESITO = null;
	 
	public IField GDO = null;
	 
	public IField DATA_FINE_ELABORAZIONE = null;
	 
	public IField DETTAGLIO_ERRORE_TRASMISSIONE = null;
	 
	public IField ID_EGOV_RICHIESTA = null;
	 
	public org.govmix.proxy.fatturapa.orm.model.PccTracciaTrasmissioneEsitoModel PCC_TRACCIA_TRASMISSIONE_ESITO = null;
	 

	@Override
	public Class<PccTracciaTrasmissione> getModeledClass(){
		return PccTracciaTrasmissione.class;
	}
	
	@Override
	public String toString(){
		if(this.getModeledClass()!=null){
			return this.getModeledClass().getName();
		}else{
			return "N.D.";
		}
	}

}