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

import org.govmix.proxy.fatturapa.orm.NotificaEsitoCommittente;

import org.openspcoop2.generic_project.beans.AbstractModel;
import org.openspcoop2.generic_project.beans.IField;
import org.openspcoop2.generic_project.beans.Field;
import org.openspcoop2.generic_project.beans.ComplexField;


/**     
 * Model NotificaEsitoCommittente 
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class NotificaEsitoCommittenteModel extends AbstractModel<NotificaEsitoCommittente> {

	public NotificaEsitoCommittenteModel(){
	
		super();
	
		this.ID_FATTURA = new org.govmix.proxy.fatturapa.orm.model.IdFatturaModel(new Field("idFattura",org.govmix.proxy.fatturapa.orm.IdFattura.class,"NotificaEsitoCommittente",NotificaEsitoCommittente.class));
		this.IDENTIFICATIVO_SDI = new Field("identificativoSdi",java.lang.Integer.class,"NotificaEsitoCommittente",NotificaEsitoCommittente.class);
		this.NUMERO_FATTURA = new Field("numeroFattura",java.lang.String.class,"NotificaEsitoCommittente",NotificaEsitoCommittente.class);
		this.ANNO = new Field("anno",java.lang.Integer.class,"NotificaEsitoCommittente",NotificaEsitoCommittente.class);
		this.POSIZIONE = new Field("posizione",java.lang.Integer.class,"NotificaEsitoCommittente",NotificaEsitoCommittente.class);
		this.ESITO = new Field("esito",java.lang.String.class,"NotificaEsitoCommittente",NotificaEsitoCommittente.class);
		this.DESCRIZIONE = new Field("descrizione",java.lang.String.class,"NotificaEsitoCommittente",NotificaEsitoCommittente.class);
		this.MESSAGE_ID_COMMITTENTE = new Field("messageIdCommittente",java.lang.String.class,"NotificaEsitoCommittente",NotificaEsitoCommittente.class);
		this.NOME_FILE = new Field("nomeFile",java.lang.String.class,"NotificaEsitoCommittente",NotificaEsitoCommittente.class);
		this.UTENTE = new org.govmix.proxy.fatturapa.orm.model.IdUtenteModel(new Field("utente",org.govmix.proxy.fatturapa.orm.IdUtente.class,"NotificaEsitoCommittente",NotificaEsitoCommittente.class));
		this.MODALITA_BATCH = new Field("modalita-batch",boolean.class,"NotificaEsitoCommittente",NotificaEsitoCommittente.class);
		this.DATA_INVIO_ENTE = new Field("dataInvioEnte",java.util.Date.class,"NotificaEsitoCommittente",NotificaEsitoCommittente.class);
		this.DATA_INVIO_SDI = new Field("dataInvioSdi",java.util.Date.class,"NotificaEsitoCommittente",NotificaEsitoCommittente.class);
		this.STATO_CONSEGNA_SDI = new Field("statoConsegnaSdi",java.lang.String.class,"NotificaEsitoCommittente",NotificaEsitoCommittente.class);
		this.DATA_ULTIMA_CONSEGNA_SDI = new Field("dataUltimaConsegnaSdi",java.util.Date.class,"NotificaEsitoCommittente",NotificaEsitoCommittente.class);
		this.DATA_PROSSIMA_CONSEGNA_SDI = new Field("dataProssimaConsegnaSdi",java.util.Date.class,"NotificaEsitoCommittente",NotificaEsitoCommittente.class);
		this.TENTATIVI_CONSEGNA_SDI = new Field("tentativiConsegnaSdi",int.class,"NotificaEsitoCommittente",NotificaEsitoCommittente.class);
		this.SCARTO = new Field("scarto",java.lang.String.class,"NotificaEsitoCommittente",NotificaEsitoCommittente.class);
		this.SCARTO_NOTE = new Field("scartoNote",java.lang.String.class,"NotificaEsitoCommittente",NotificaEsitoCommittente.class);
		this.SCARTO_XML = new Field("scartoXml",byte[].class,"NotificaEsitoCommittente",NotificaEsitoCommittente.class);
		this.XML = new Field("xml",byte[].class,"NotificaEsitoCommittente",NotificaEsitoCommittente.class);
	
	}
	
	public NotificaEsitoCommittenteModel(IField father){
	
		super(father);
	
		this.ID_FATTURA = new org.govmix.proxy.fatturapa.orm.model.IdFatturaModel(new ComplexField(father,"idFattura",org.govmix.proxy.fatturapa.orm.IdFattura.class,"NotificaEsitoCommittente",NotificaEsitoCommittente.class));
		this.IDENTIFICATIVO_SDI = new ComplexField(father,"identificativoSdi",java.lang.Integer.class,"NotificaEsitoCommittente",NotificaEsitoCommittente.class);
		this.NUMERO_FATTURA = new ComplexField(father,"numeroFattura",java.lang.String.class,"NotificaEsitoCommittente",NotificaEsitoCommittente.class);
		this.ANNO = new ComplexField(father,"anno",java.lang.Integer.class,"NotificaEsitoCommittente",NotificaEsitoCommittente.class);
		this.POSIZIONE = new ComplexField(father,"posizione",java.lang.Integer.class,"NotificaEsitoCommittente",NotificaEsitoCommittente.class);
		this.ESITO = new ComplexField(father,"esito",java.lang.String.class,"NotificaEsitoCommittente",NotificaEsitoCommittente.class);
		this.DESCRIZIONE = new ComplexField(father,"descrizione",java.lang.String.class,"NotificaEsitoCommittente",NotificaEsitoCommittente.class);
		this.MESSAGE_ID_COMMITTENTE = new ComplexField(father,"messageIdCommittente",java.lang.String.class,"NotificaEsitoCommittente",NotificaEsitoCommittente.class);
		this.NOME_FILE = new ComplexField(father,"nomeFile",java.lang.String.class,"NotificaEsitoCommittente",NotificaEsitoCommittente.class);
		this.UTENTE = new org.govmix.proxy.fatturapa.orm.model.IdUtenteModel(new ComplexField(father,"utente",org.govmix.proxy.fatturapa.orm.IdUtente.class,"NotificaEsitoCommittente",NotificaEsitoCommittente.class));
		this.MODALITA_BATCH = new ComplexField(father,"modalita-batch",boolean.class,"NotificaEsitoCommittente",NotificaEsitoCommittente.class);
		this.DATA_INVIO_ENTE = new ComplexField(father,"dataInvioEnte",java.util.Date.class,"NotificaEsitoCommittente",NotificaEsitoCommittente.class);
		this.DATA_INVIO_SDI = new ComplexField(father,"dataInvioSdi",java.util.Date.class,"NotificaEsitoCommittente",NotificaEsitoCommittente.class);
		this.STATO_CONSEGNA_SDI = new ComplexField(father,"statoConsegnaSdi",java.lang.String.class,"NotificaEsitoCommittente",NotificaEsitoCommittente.class);
		this.DATA_ULTIMA_CONSEGNA_SDI = new ComplexField(father,"dataUltimaConsegnaSdi",java.util.Date.class,"NotificaEsitoCommittente",NotificaEsitoCommittente.class);
		this.DATA_PROSSIMA_CONSEGNA_SDI = new ComplexField(father,"dataProssimaConsegnaSdi",java.util.Date.class,"NotificaEsitoCommittente",NotificaEsitoCommittente.class);
		this.TENTATIVI_CONSEGNA_SDI = new ComplexField(father,"tentativiConsegnaSdi",int.class,"NotificaEsitoCommittente",NotificaEsitoCommittente.class);
		this.SCARTO = new ComplexField(father,"scarto",java.lang.String.class,"NotificaEsitoCommittente",NotificaEsitoCommittente.class);
		this.SCARTO_NOTE = new ComplexField(father,"scartoNote",java.lang.String.class,"NotificaEsitoCommittente",NotificaEsitoCommittente.class);
		this.SCARTO_XML = new ComplexField(father,"scartoXml",byte[].class,"NotificaEsitoCommittente",NotificaEsitoCommittente.class);
		this.XML = new ComplexField(father,"xml",byte[].class,"NotificaEsitoCommittente",NotificaEsitoCommittente.class);
	
	}
	
	

	public org.govmix.proxy.fatturapa.orm.model.IdFatturaModel ID_FATTURA = null;
	 
	public IField IDENTIFICATIVO_SDI = null;
	 
	public IField NUMERO_FATTURA = null;
	 
	public IField ANNO = null;
	 
	public IField POSIZIONE = null;
	 
	public IField ESITO = null;
	 
	public IField DESCRIZIONE = null;
	 
	public IField MESSAGE_ID_COMMITTENTE = null;
	 
	public IField NOME_FILE = null;
	 
	public org.govmix.proxy.fatturapa.orm.model.IdUtenteModel UTENTE = null;
	 
	public IField MODALITA_BATCH = null;
	 
	public IField DATA_INVIO_ENTE = null;
	 
	public IField DATA_INVIO_SDI = null;
	 
	public IField STATO_CONSEGNA_SDI = null;
	 
	public IField DATA_ULTIMA_CONSEGNA_SDI = null;
	 
	public IField DATA_PROSSIMA_CONSEGNA_SDI = null;
	 
	public IField TENTATIVI_CONSEGNA_SDI = null;
	 
	public IField SCARTO = null;
	 
	public IField SCARTO_NOTE = null;
	 
	public IField SCARTO_XML = null;
	 
	public IField XML = null;
	 

	@Override
	public Class<NotificaEsitoCommittente> getModeledClass(){
		return NotificaEsitoCommittente.class;
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