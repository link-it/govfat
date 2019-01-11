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

import org.govmix.proxy.fatturapa.orm.PccContabilizzazione;

import org.openspcoop2.generic_project.beans.AbstractModel;
import org.openspcoop2.generic_project.beans.IField;
import org.openspcoop2.generic_project.beans.Field;
import org.openspcoop2.generic_project.beans.ComplexField;


/**     
 * Model PccContabilizzazione 
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class PccContabilizzazioneModel extends AbstractModel<PccContabilizzazione> {

	public PccContabilizzazioneModel(){
	
		super();
	
		this.IMPORTO_MOVIMENTO = new Field("importoMovimento",double.class,"PccContabilizzazione",PccContabilizzazione.class);
		this.NATURA_SPESA = new Field("naturaSpesa",java.lang.String.class,"PccContabilizzazione",PccContabilizzazione.class);
		this.CAPITOLI_SPESA = new Field("capitoliSpesa",java.lang.String.class,"PccContabilizzazione",PccContabilizzazione.class);
		this.STATO_DEBITO = new Field("statoDebito",java.lang.String.class,"PccContabilizzazione",PccContabilizzazione.class);
		this.CAUSALE = new Field("causale",java.lang.String.class,"PccContabilizzazione",PccContabilizzazione.class);
		this.DESCRIZIONE = new Field("descrizione",java.lang.String.class,"PccContabilizzazione",PccContabilizzazione.class);
		this.ESTREMI_IMPEGNO = new Field("estremiImpegno",java.lang.String.class,"PccContabilizzazione",PccContabilizzazione.class);
		this.CODICE_CIG = new Field("CodiceCig",java.lang.String.class,"PccContabilizzazione",PccContabilizzazione.class);
		this.CODICE_CUP = new Field("CodiceCup",java.lang.String.class,"PccContabilizzazione",PccContabilizzazione.class);
		this.ID_IMPORTO = new Field("idImporto",java.lang.String.class,"PccContabilizzazione",PccContabilizzazione.class);
		this.ID_FATTURA = new org.govmix.proxy.fatturapa.orm.model.IdFatturaModel(new Field("idFattura",org.govmix.proxy.fatturapa.orm.IdFattura.class,"PccContabilizzazione",PccContabilizzazione.class));
		this.SISTEMA_RICHIEDENTE = new Field("sistemaRichiedente",java.lang.String.class,"PccContabilizzazione",PccContabilizzazione.class);
		this.UTENTE_RICHIEDENTE = new Field("utenteRichiedente",java.lang.String.class,"PccContabilizzazione",PccContabilizzazione.class);
		this.DATA_RICHIESTA = new Field("dataRichiesta",java.util.Date.class,"PccContabilizzazione",PccContabilizzazione.class);
		this.DATA_QUERY = new Field("dataQuery",java.util.Date.class,"PccContabilizzazione",PccContabilizzazione.class);
	
	}
	
	public PccContabilizzazioneModel(IField father){
	
		super(father);
	
		this.IMPORTO_MOVIMENTO = new ComplexField(father,"importoMovimento",double.class,"PccContabilizzazione",PccContabilizzazione.class);
		this.NATURA_SPESA = new ComplexField(father,"naturaSpesa",java.lang.String.class,"PccContabilizzazione",PccContabilizzazione.class);
		this.CAPITOLI_SPESA = new ComplexField(father,"capitoliSpesa",java.lang.String.class,"PccContabilizzazione",PccContabilizzazione.class);
		this.STATO_DEBITO = new ComplexField(father,"statoDebito",java.lang.String.class,"PccContabilizzazione",PccContabilizzazione.class);
		this.CAUSALE = new ComplexField(father,"causale",java.lang.String.class,"PccContabilizzazione",PccContabilizzazione.class);
		this.DESCRIZIONE = new ComplexField(father,"descrizione",java.lang.String.class,"PccContabilizzazione",PccContabilizzazione.class);
		this.ESTREMI_IMPEGNO = new ComplexField(father,"estremiImpegno",java.lang.String.class,"PccContabilizzazione",PccContabilizzazione.class);
		this.CODICE_CIG = new ComplexField(father,"CodiceCig",java.lang.String.class,"PccContabilizzazione",PccContabilizzazione.class);
		this.CODICE_CUP = new ComplexField(father,"CodiceCup",java.lang.String.class,"PccContabilizzazione",PccContabilizzazione.class);
		this.ID_IMPORTO = new ComplexField(father,"idImporto",java.lang.String.class,"PccContabilizzazione",PccContabilizzazione.class);
		this.ID_FATTURA = new org.govmix.proxy.fatturapa.orm.model.IdFatturaModel(new ComplexField(father,"idFattura",org.govmix.proxy.fatturapa.orm.IdFattura.class,"PccContabilizzazione",PccContabilizzazione.class));
		this.SISTEMA_RICHIEDENTE = new ComplexField(father,"sistemaRichiedente",java.lang.String.class,"PccContabilizzazione",PccContabilizzazione.class);
		this.UTENTE_RICHIEDENTE = new ComplexField(father,"utenteRichiedente",java.lang.String.class,"PccContabilizzazione",PccContabilizzazione.class);
		this.DATA_RICHIESTA = new ComplexField(father,"dataRichiesta",java.util.Date.class,"PccContabilizzazione",PccContabilizzazione.class);
		this.DATA_QUERY = new ComplexField(father,"dataQuery",java.util.Date.class,"PccContabilizzazione",PccContabilizzazione.class);
	
	}
	
	

	public IField IMPORTO_MOVIMENTO = null;
	 
	public IField NATURA_SPESA = null;
	 
	public IField CAPITOLI_SPESA = null;
	 
	public IField STATO_DEBITO = null;
	 
	public IField CAUSALE = null;
	 
	public IField DESCRIZIONE = null;
	 
	public IField ESTREMI_IMPEGNO = null;
	 
	public IField CODICE_CIG = null;
	 
	public IField CODICE_CUP = null;
	 
	public IField ID_IMPORTO = null;
	 
	public org.govmix.proxy.fatturapa.orm.model.IdFatturaModel ID_FATTURA = null;
	 
	public IField SISTEMA_RICHIEDENTE = null;
	 
	public IField UTENTE_RICHIEDENTE = null;
	 
	public IField DATA_RICHIESTA = null;
	 
	public IField DATA_QUERY = null;
	 

	@Override
	public Class<PccContabilizzazione> getModeledClass(){
		return PccContabilizzazione.class;
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