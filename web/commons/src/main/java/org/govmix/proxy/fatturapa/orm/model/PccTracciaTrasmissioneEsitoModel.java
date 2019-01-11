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
package org.govmix.proxy.fatturapa.orm.model;

import org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissioneEsito;

import org.openspcoop2.generic_project.beans.AbstractModel;
import org.openspcoop2.generic_project.beans.IField;
import org.openspcoop2.generic_project.beans.Field;
import org.openspcoop2.generic_project.beans.ComplexField;


/**     
 * Model PccTracciaTrasmissioneEsito 
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class PccTracciaTrasmissioneEsitoModel extends AbstractModel<PccTracciaTrasmissioneEsito> {

	public PccTracciaTrasmissioneEsitoModel(){
	
		super();
	
		this.ID_TRASMISSIONE = new org.govmix.proxy.fatturapa.orm.model.IdTrasmissioneModel(new Field("idTrasmissione",org.govmix.proxy.fatturapa.orm.IdTrasmissione.class,"PccTracciaTrasmissioneEsito",PccTracciaTrasmissioneEsito.class));
		this.ESITO_ELABORAZIONE = new Field("esitoElaborazione",java.lang.String.class,"PccTracciaTrasmissioneEsito",PccTracciaTrasmissioneEsito.class);
		this.DESCRIZIONE_ELABORAZIONE = new Field("descrizioneElaborazione",java.lang.String.class,"PccTracciaTrasmissioneEsito",PccTracciaTrasmissioneEsito.class);
		this.DATA_FINE_ELABORAZIONE = new Field("dataFineElaborazione",java.util.Date.class,"PccTracciaTrasmissioneEsito",PccTracciaTrasmissioneEsito.class);
		this.GDO = new Field("gdo",java.util.Date.class,"PccTracciaTrasmissioneEsito",PccTracciaTrasmissioneEsito.class);
		this.ESITO_TRASMISSIONE = new Field("esitoTrasmissione",java.lang.String.class,"PccTracciaTrasmissioneEsito",PccTracciaTrasmissioneEsito.class);
		this.DETTAGLIO_ERRORE_TRASMISSIONE = new Field("dettaglioErroreTrasmissione",java.lang.String.class,"PccTracciaTrasmissioneEsito",PccTracciaTrasmissioneEsito.class);
		this.ID_EGOV_RICHIESTA = new Field("idEgovRichiesta",java.lang.String.class,"PccTracciaTrasmissioneEsito",PccTracciaTrasmissioneEsito.class);
		this.PCC_ERRORE_ELABORAZIONE = new org.govmix.proxy.fatturapa.orm.model.PccErroreElaborazioneModel(new Field("PccErroreElaborazione",org.govmix.proxy.fatturapa.orm.PccErroreElaborazione.class,"PccTracciaTrasmissioneEsito",PccTracciaTrasmissioneEsito.class));
	
	}
	
	public PccTracciaTrasmissioneEsitoModel(IField father){
	
		super(father);
	
		this.ID_TRASMISSIONE = new org.govmix.proxy.fatturapa.orm.model.IdTrasmissioneModel(new ComplexField(father,"idTrasmissione",org.govmix.proxy.fatturapa.orm.IdTrasmissione.class,"PccTracciaTrasmissioneEsito",PccTracciaTrasmissioneEsito.class));
		this.ESITO_ELABORAZIONE = new ComplexField(father,"esitoElaborazione",java.lang.String.class,"PccTracciaTrasmissioneEsito",PccTracciaTrasmissioneEsito.class);
		this.DESCRIZIONE_ELABORAZIONE = new ComplexField(father,"descrizioneElaborazione",java.lang.String.class,"PccTracciaTrasmissioneEsito",PccTracciaTrasmissioneEsito.class);
		this.DATA_FINE_ELABORAZIONE = new ComplexField(father,"dataFineElaborazione",java.util.Date.class,"PccTracciaTrasmissioneEsito",PccTracciaTrasmissioneEsito.class);
		this.GDO = new ComplexField(father,"gdo",java.util.Date.class,"PccTracciaTrasmissioneEsito",PccTracciaTrasmissioneEsito.class);
		this.ESITO_TRASMISSIONE = new ComplexField(father,"esitoTrasmissione",java.lang.String.class,"PccTracciaTrasmissioneEsito",PccTracciaTrasmissioneEsito.class);
		this.DETTAGLIO_ERRORE_TRASMISSIONE = new ComplexField(father,"dettaglioErroreTrasmissione",java.lang.String.class,"PccTracciaTrasmissioneEsito",PccTracciaTrasmissioneEsito.class);
		this.ID_EGOV_RICHIESTA = new ComplexField(father,"idEgovRichiesta",java.lang.String.class,"PccTracciaTrasmissioneEsito",PccTracciaTrasmissioneEsito.class);
		this.PCC_ERRORE_ELABORAZIONE = new org.govmix.proxy.fatturapa.orm.model.PccErroreElaborazioneModel(new ComplexField(father,"PccErroreElaborazione",org.govmix.proxy.fatturapa.orm.PccErroreElaborazione.class,"PccTracciaTrasmissioneEsito",PccTracciaTrasmissioneEsito.class));
	
	}
	
	

	public org.govmix.proxy.fatturapa.orm.model.IdTrasmissioneModel ID_TRASMISSIONE = null;
	 
	public IField ESITO_ELABORAZIONE = null;
	 
	public IField DESCRIZIONE_ELABORAZIONE = null;
	 
	public IField DATA_FINE_ELABORAZIONE = null;
	 
	public IField GDO = null;
	 
	public IField ESITO_TRASMISSIONE = null;
	 
	public IField DETTAGLIO_ERRORE_TRASMISSIONE = null;
	 
	public IField ID_EGOV_RICHIESTA = null;
	 
	public org.govmix.proxy.fatturapa.orm.model.PccErroreElaborazioneModel PCC_ERRORE_ELABORAZIONE = null;
	 

	@Override
	public Class<PccTracciaTrasmissioneEsito> getModeledClass(){
		return PccTracciaTrasmissioneEsito.class;
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