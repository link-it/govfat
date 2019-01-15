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

import org.govmix.proxy.fatturapa.orm.TracciaSDI;

import org.openspcoop2.generic_project.beans.AbstractModel;
import org.openspcoop2.generic_project.beans.IField;
import org.openspcoop2.generic_project.beans.Field;
import org.openspcoop2.generic_project.beans.ComplexField;


/**     
 * Model TracciaSDI 
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class TracciaSDIModel extends AbstractModel<TracciaSDI> {

	public TracciaSDIModel(){
	
		super();
	
		this.IDENTIFICATIVO_SDI = new Field("identificativoSdi",java.lang.Integer.class,"TracciaSDI",TracciaSDI.class);
		this.POSIZIONE = new Field("posizione",java.lang.Integer.class,"TracciaSDI",TracciaSDI.class);
		this.TIPO_COMUNICAZIONE = new Field("tipoComunicazione",java.lang.String.class,"TracciaSDI",TracciaSDI.class);
		this.NOME_FILE = new Field("nomeFile",java.lang.String.class,"TracciaSDI",TracciaSDI.class);
		this.CODICE_DIPARTIMENTO = new Field("codiceDipartimento",java.lang.String.class,"TracciaSDI",TracciaSDI.class);
		this.DATA = new Field("data",java.util.Date.class,"TracciaSDI",TracciaSDI.class);
		this.ID_EGOV = new Field("idEgov",java.lang.String.class,"TracciaSDI",TracciaSDI.class);
		this.CONTENT_TYPE = new Field("contentType",java.lang.String.class,"TracciaSDI",TracciaSDI.class);
		this.RAW_DATA = new Field("rawData",byte[].class,"TracciaSDI",TracciaSDI.class);
		this.STATO_PROTOCOLLAZIONE = new Field("statoProtocollazione",java.lang.String.class,"TracciaSDI",TracciaSDI.class);
		this.DATA_PROTOCOLLAZIONE = new Field("dataProtocollazione",java.util.Date.class,"TracciaSDI",TracciaSDI.class);
		this.DATA_PROSSIMA_PROTOCOLLAZIONE = new Field("dataProssimaProtocollazione",java.util.Date.class,"TracciaSDI",TracciaSDI.class);
		this.TENTATIVI_PROTOCOLLAZIONE = new Field("tentativiProtocollazione",java.lang.Integer.class,"TracciaSDI",TracciaSDI.class);
		this.DETTAGLIO_PROTOCOLLAZIONE = new Field("dettaglioProtocollazione",java.lang.String.class,"TracciaSDI",TracciaSDI.class);
		this.METADATO = new org.govmix.proxy.fatturapa.orm.model.MetadatoModel(new Field("Metadato",org.govmix.proxy.fatturapa.orm.Metadato.class,"TracciaSDI",TracciaSDI.class));
		this.DIPARTIMENTO = new org.govmix.proxy.fatturapa.orm.model.DipartimentoModel(new Field("Dipartimento",org.govmix.proxy.fatturapa.orm.Dipartimento.class,"TracciaSDI",TracciaSDI.class));
	
	}
	
	public TracciaSDIModel(IField father){
	
		super(father);
	
		this.IDENTIFICATIVO_SDI = new ComplexField(father,"identificativoSdi",java.lang.Integer.class,"TracciaSDI",TracciaSDI.class);
		this.POSIZIONE = new ComplexField(father,"posizione",java.lang.Integer.class,"TracciaSDI",TracciaSDI.class);
		this.TIPO_COMUNICAZIONE = new ComplexField(father,"tipoComunicazione",java.lang.String.class,"TracciaSDI",TracciaSDI.class);
		this.NOME_FILE = new ComplexField(father,"nomeFile",java.lang.String.class,"TracciaSDI",TracciaSDI.class);
		this.CODICE_DIPARTIMENTO = new ComplexField(father,"codiceDipartimento",java.lang.String.class,"TracciaSDI",TracciaSDI.class);
		this.DATA = new ComplexField(father,"data",java.util.Date.class,"TracciaSDI",TracciaSDI.class);
		this.ID_EGOV = new ComplexField(father,"idEgov",java.lang.String.class,"TracciaSDI",TracciaSDI.class);
		this.CONTENT_TYPE = new ComplexField(father,"contentType",java.lang.String.class,"TracciaSDI",TracciaSDI.class);
		this.RAW_DATA = new ComplexField(father,"rawData",byte[].class,"TracciaSDI",TracciaSDI.class);
		this.STATO_PROTOCOLLAZIONE = new ComplexField(father,"statoProtocollazione",java.lang.String.class,"TracciaSDI",TracciaSDI.class);
		this.DATA_PROTOCOLLAZIONE = new ComplexField(father,"dataProtocollazione",java.util.Date.class,"TracciaSDI",TracciaSDI.class);
		this.DATA_PROSSIMA_PROTOCOLLAZIONE = new ComplexField(father,"dataProssimaProtocollazione",java.util.Date.class,"TracciaSDI",TracciaSDI.class);
		this.TENTATIVI_PROTOCOLLAZIONE = new ComplexField(father,"tentativiProtocollazione",java.lang.Integer.class,"TracciaSDI",TracciaSDI.class);
		this.DETTAGLIO_PROTOCOLLAZIONE = new ComplexField(father,"dettaglioProtocollazione",java.lang.String.class,"TracciaSDI",TracciaSDI.class);
		this.METADATO = new org.govmix.proxy.fatturapa.orm.model.MetadatoModel(new ComplexField(father,"Metadato",org.govmix.proxy.fatturapa.orm.Metadato.class,"TracciaSDI",TracciaSDI.class));
		this.DIPARTIMENTO = new org.govmix.proxy.fatturapa.orm.model.DipartimentoModel(new ComplexField(father,"Dipartimento",org.govmix.proxy.fatturapa.orm.Dipartimento.class,"TracciaSDI",TracciaSDI.class));
	
	}
	
	

	public IField IDENTIFICATIVO_SDI = null;
	 
	public IField POSIZIONE = null;
	 
	public IField TIPO_COMUNICAZIONE = null;
	 
	public IField NOME_FILE = null;
	 
	public IField CODICE_DIPARTIMENTO = null;
	 
	public IField DATA = null;
	 
	public IField ID_EGOV = null;
	 
	public IField CONTENT_TYPE = null;
	 
	public IField RAW_DATA = null;
	 
	public IField STATO_PROTOCOLLAZIONE = null;
	 
	public IField DATA_PROTOCOLLAZIONE = null;
	 
	public IField DATA_PROSSIMA_PROTOCOLLAZIONE = null;
	 
	public IField TENTATIVI_PROTOCOLLAZIONE = null;
	 
	public IField DETTAGLIO_PROTOCOLLAZIONE = null;
	 
	public org.govmix.proxy.fatturapa.orm.model.MetadatoModel METADATO = null;
	 
	public org.govmix.proxy.fatturapa.orm.model.DipartimentoModel DIPARTIMENTO = null;
	 

	@Override
	public Class<TracciaSDI> getModeledClass(){
		return TracciaSDI.class;
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