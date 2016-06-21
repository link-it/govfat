/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2016 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2016 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
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

import org.govmix.proxy.fatturapa.orm.ComunicazioneSdi;

import org.openspcoop2.generic_project.beans.AbstractModel;
import org.openspcoop2.generic_project.beans.IField;
import org.openspcoop2.generic_project.beans.Field;
import org.openspcoop2.generic_project.beans.ComplexField;


/**     
 * Model ComunicazioneSdi 
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class ComunicazioneSdiModel extends AbstractModel<ComunicazioneSdi> {

	public ComunicazioneSdiModel(){
	
		super();
	
		this.IDENTIFICATIVO_SDI = new Field("identificativoSdi",java.lang.Integer.class,"ComunicazioneSdi",ComunicazioneSdi.class);
		this.TIPO_COMUNICAZIONE = new Field("tipoComunicazione",java.lang.String.class,"ComunicazioneSdi",ComunicazioneSdi.class);
		this.PROGRESSIVO = new Field("progressivo",java.lang.Integer.class,"ComunicazioneSdi",ComunicazioneSdi.class);
		this.DATA_RICEZIONE = new Field("dataRicezione",java.util.Date.class,"ComunicazioneSdi",ComunicazioneSdi.class);
		this.NOME_FILE = new Field("nomeFile",java.lang.String.class,"ComunicazioneSdi",ComunicazioneSdi.class);
		this.CONTENT_TYPE = new Field("contentType",java.lang.String.class,"ComunicazioneSdi",ComunicazioneSdi.class);
		this.RAW_DATA = new Field("rawData",byte[].class,"ComunicazioneSdi",ComunicazioneSdi.class);
		this.STATO_CONSEGNA = new Field("statoConsegna",java.lang.String.class,"ComunicazioneSdi",ComunicazioneSdi.class);
		this.DATA_CONSEGNA = new Field("dataConsegna",java.util.Date.class,"ComunicazioneSdi",ComunicazioneSdi.class);
		this.DETTAGLIO_CONSEGNA = new Field("dettaglioConsegna",java.lang.String.class,"ComunicazioneSdi",ComunicazioneSdi.class);
		this.METADATO = new org.govmix.proxy.fatturapa.orm.model.MetadatoModel(new Field("Metadato",org.govmix.proxy.fatturapa.orm.Metadato.class,"ComunicazioneSdi",ComunicazioneSdi.class));
	
	}
	
	public ComunicazioneSdiModel(IField father){
	
		super(father);
	
		this.IDENTIFICATIVO_SDI = new ComplexField(father,"identificativoSdi",java.lang.Integer.class,"ComunicazioneSdi",ComunicazioneSdi.class);
		this.TIPO_COMUNICAZIONE = new ComplexField(father,"tipoComunicazione",java.lang.String.class,"ComunicazioneSdi",ComunicazioneSdi.class);
		this.PROGRESSIVO = new ComplexField(father,"progressivo",java.lang.Integer.class,"ComunicazioneSdi",ComunicazioneSdi.class);
		this.DATA_RICEZIONE = new ComplexField(father,"dataRicezione",java.util.Date.class,"ComunicazioneSdi",ComunicazioneSdi.class);
		this.NOME_FILE = new ComplexField(father,"nomeFile",java.lang.String.class,"ComunicazioneSdi",ComunicazioneSdi.class);
		this.CONTENT_TYPE = new ComplexField(father,"contentType",java.lang.String.class,"ComunicazioneSdi",ComunicazioneSdi.class);
		this.RAW_DATA = new ComplexField(father,"rawData",byte[].class,"ComunicazioneSdi",ComunicazioneSdi.class);
		this.STATO_CONSEGNA = new ComplexField(father,"statoConsegna",java.lang.String.class,"ComunicazioneSdi",ComunicazioneSdi.class);
		this.DATA_CONSEGNA = new ComplexField(father,"dataConsegna",java.util.Date.class,"ComunicazioneSdi",ComunicazioneSdi.class);
		this.DETTAGLIO_CONSEGNA = new ComplexField(father,"dettaglioConsegna",java.lang.String.class,"ComunicazioneSdi",ComunicazioneSdi.class);
		this.METADATO = new org.govmix.proxy.fatturapa.orm.model.MetadatoModel(new ComplexField(father,"Metadato",org.govmix.proxy.fatturapa.orm.Metadato.class,"ComunicazioneSdi",ComunicazioneSdi.class));
	
	}
	
	

	public IField IDENTIFICATIVO_SDI = null;
	 
	public IField TIPO_COMUNICAZIONE = null;
	 
	public IField PROGRESSIVO = null;
	 
	public IField DATA_RICEZIONE = null;
	 
	public IField NOME_FILE = null;
	 
	public IField CONTENT_TYPE = null;
	 
	public IField RAW_DATA = null;
	 
	public IField STATO_CONSEGNA = null;
	 
	public IField DATA_CONSEGNA = null;
	 
	public IField DETTAGLIO_CONSEGNA = null;
	 
	public org.govmix.proxy.fatturapa.orm.model.MetadatoModel METADATO = null;
	 

	@Override
	public Class<ComunicazioneSdi> getModeledClass(){
		return ComunicazioneSdi.class;
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