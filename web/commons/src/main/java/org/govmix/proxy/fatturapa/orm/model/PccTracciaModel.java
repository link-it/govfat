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

import org.govmix.proxy.fatturapa.orm.PccTraccia;

import org.openspcoop2.generic_project.beans.AbstractModel;
import org.openspcoop2.generic_project.beans.IField;
import org.openspcoop2.generic_project.beans.Field;
import org.openspcoop2.generic_project.beans.ComplexField;


/**     
 * Model PccTraccia 
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class PccTracciaModel extends AbstractModel<PccTraccia> {

	public PccTracciaModel(){
	
		super();
	
		this.DATA_CREAZIONE = new Field("dataCreazione",java.util.Date.class,"PccTraccia",PccTraccia.class);
		this.CF_TRASMITTENTE = new Field("cfTrasmittente",java.lang.String.class,"PccTraccia",PccTraccia.class);
		this.VERSIONE_APPLICATIVA = new Field("versioneApplicativa",java.lang.String.class,"PccTraccia",PccTraccia.class);
		this.ID_PCC_AMMINISTRAZIONE = new Field("idPccAmministrazione",int.class,"PccTraccia",PccTraccia.class);
		this.ID_PA_TRANSAZIONE = new Field("idPaTransazione",java.lang.String.class,"PccTraccia",PccTraccia.class);
		this.ID_PA_TRANSAZIONE_RISPEDIZIONE = new Field("idPaTransazioneRispedizione",java.lang.String.class,"PccTraccia",PccTraccia.class);
		this.SISTEMA_RICHIEDENTE = new Field("sistemaRichiedente",java.lang.String.class,"PccTraccia",PccTraccia.class);
		this.UTENTE_RICHIEDENTE = new Field("utenteRichiedente",java.lang.String.class,"PccTraccia",PccTraccia.class);
		this.ID_FATTURA = new Field("idFattura",long.class,"PccTraccia",PccTraccia.class);
		this.CODICE_DIPARTIMENTO = new Field("codiceDipartimento",java.lang.String.class,"PccTraccia",PccTraccia.class);
		this.RICHIESTA_XML = new Field("richiestaXml",byte[].class,"PccTraccia",PccTraccia.class);
		this.RISPOSTA_XML = new Field("rispostaXml",byte[].class,"PccTraccia",PccTraccia.class);
		this.OPERAZIONE = new Field("operazione",java.lang.String.class,"PccTraccia",PccTraccia.class);
		this.TIPO_OPERAZIONE = new Field("tipoOperazione",java.lang.String.class,"PccTraccia",PccTraccia.class);
		this.STATO = new Field("stato",java.lang.String.class,"PccTraccia",PccTraccia.class);
		this.DATA_ULTIMA_TRASMISSIONE = new Field("dataUltimaTrasmissione",java.util.Date.class,"PccTraccia",PccTraccia.class);
		this.DATA_ULTIMO_TENTATIVO_ESITO = new Field("dataUltimoTentativoEsito",java.util.Date.class,"PccTraccia",PccTraccia.class);
		this.CODICI_ERRORE = new Field("codiciErrore",java.lang.String.class,"PccTraccia",PccTraccia.class);
		this.RISPEDIZIONE = new Field("rispedizione",boolean.class,"PccTraccia",PccTraccia.class);
		this.RISPEDIZIONE_DOPO_QUERY = new Field("rispedizioneDopoQuery",boolean.class,"PccTraccia",PccTraccia.class);
		this.RISPEDIZIONE_MAX_TENTATIVI = new Field("rispedizioneMaxTentativi",int.class,"PccTraccia",PccTraccia.class);
		this.RISPEDIZIONE_PROSSIMO_TENTATIVO = new Field("rispedizioneProssimoTentativo",java.util.Date.class,"PccTraccia",PccTraccia.class);
		this.RISPEDIZIONE_NUMERO_TENTATIVI = new Field("rispedizioneNumeroTentativi",int.class,"PccTraccia",PccTraccia.class);
		this.RISPEDIZIONE_ULTIMO_TENTATIVO = new Field("rispedizioneUltimoTentativo",java.util.Date.class,"PccTraccia",PccTraccia.class);
		this.PCC_TRACCIA_TRASMISSIONE = new org.govmix.proxy.fatturapa.orm.model.PccTracciaTrasmissioneModel(new Field("PccTracciaTrasmissione",org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissione.class,"PccTraccia",PccTraccia.class));
		this.FATTURA_ELETTRONICA = new org.govmix.proxy.fatturapa.orm.model.FatturaElettronicaModel(new Field("FatturaElettronica",org.govmix.proxy.fatturapa.orm.FatturaElettronica.class,"PccTraccia",PccTraccia.class));
	
	}
	
	public PccTracciaModel(IField father){
	
		super(father);
	
		this.DATA_CREAZIONE = new ComplexField(father,"dataCreazione",java.util.Date.class,"PccTraccia",PccTraccia.class);
		this.CF_TRASMITTENTE = new ComplexField(father,"cfTrasmittente",java.lang.String.class,"PccTraccia",PccTraccia.class);
		this.VERSIONE_APPLICATIVA = new ComplexField(father,"versioneApplicativa",java.lang.String.class,"PccTraccia",PccTraccia.class);
		this.ID_PCC_AMMINISTRAZIONE = new ComplexField(father,"idPccAmministrazione",int.class,"PccTraccia",PccTraccia.class);
		this.ID_PA_TRANSAZIONE = new ComplexField(father,"idPaTransazione",java.lang.String.class,"PccTraccia",PccTraccia.class);
		this.ID_PA_TRANSAZIONE_RISPEDIZIONE = new ComplexField(father,"idPaTransazioneRispedizione",java.lang.String.class,"PccTraccia",PccTraccia.class);
		this.SISTEMA_RICHIEDENTE = new ComplexField(father,"sistemaRichiedente",java.lang.String.class,"PccTraccia",PccTraccia.class);
		this.UTENTE_RICHIEDENTE = new ComplexField(father,"utenteRichiedente",java.lang.String.class,"PccTraccia",PccTraccia.class);
		this.ID_FATTURA = new ComplexField(father,"idFattura",long.class,"PccTraccia",PccTraccia.class);
		this.CODICE_DIPARTIMENTO = new ComplexField(father,"codiceDipartimento",java.lang.String.class,"PccTraccia",PccTraccia.class);
		this.RICHIESTA_XML = new ComplexField(father,"richiestaXml",byte[].class,"PccTraccia",PccTraccia.class);
		this.RISPOSTA_XML = new ComplexField(father,"rispostaXml",byte[].class,"PccTraccia",PccTraccia.class);
		this.OPERAZIONE = new ComplexField(father,"operazione",java.lang.String.class,"PccTraccia",PccTraccia.class);
		this.TIPO_OPERAZIONE = new ComplexField(father,"tipoOperazione",java.lang.String.class,"PccTraccia",PccTraccia.class);
		this.STATO = new ComplexField(father,"stato",java.lang.String.class,"PccTraccia",PccTraccia.class);
		this.DATA_ULTIMA_TRASMISSIONE = new ComplexField(father,"dataUltimaTrasmissione",java.util.Date.class,"PccTraccia",PccTraccia.class);
		this.DATA_ULTIMO_TENTATIVO_ESITO = new ComplexField(father,"dataUltimoTentativoEsito",java.util.Date.class,"PccTraccia",PccTraccia.class);
		this.CODICI_ERRORE = new ComplexField(father,"codiciErrore",java.lang.String.class,"PccTraccia",PccTraccia.class);
		this.RISPEDIZIONE = new ComplexField(father,"rispedizione",boolean.class,"PccTraccia",PccTraccia.class);
		this.RISPEDIZIONE_DOPO_QUERY = new ComplexField(father,"rispedizioneDopoQuery",boolean.class,"PccTraccia",PccTraccia.class);
		this.RISPEDIZIONE_MAX_TENTATIVI = new ComplexField(father,"rispedizioneMaxTentativi",int.class,"PccTraccia",PccTraccia.class);
		this.RISPEDIZIONE_PROSSIMO_TENTATIVO = new ComplexField(father,"rispedizioneProssimoTentativo",java.util.Date.class,"PccTraccia",PccTraccia.class);
		this.RISPEDIZIONE_NUMERO_TENTATIVI = new ComplexField(father,"rispedizioneNumeroTentativi",int.class,"PccTraccia",PccTraccia.class);
		this.RISPEDIZIONE_ULTIMO_TENTATIVO = new ComplexField(father,"rispedizioneUltimoTentativo",java.util.Date.class,"PccTraccia",PccTraccia.class);
		this.PCC_TRACCIA_TRASMISSIONE = new org.govmix.proxy.fatturapa.orm.model.PccTracciaTrasmissioneModel(new ComplexField(father,"PccTracciaTrasmissione",org.govmix.proxy.fatturapa.orm.PccTracciaTrasmissione.class,"PccTraccia",PccTraccia.class));
		this.FATTURA_ELETTRONICA = new org.govmix.proxy.fatturapa.orm.model.FatturaElettronicaModel(new ComplexField(father,"FatturaElettronica",org.govmix.proxy.fatturapa.orm.FatturaElettronica.class,"PccTraccia",PccTraccia.class));
	
	}
	
	

	public IField DATA_CREAZIONE = null;
	 
	public IField CF_TRASMITTENTE = null;
	 
	public IField VERSIONE_APPLICATIVA = null;
	 
	public IField ID_PCC_AMMINISTRAZIONE = null;
	 
	public IField ID_PA_TRANSAZIONE = null;
	 
	public IField ID_PA_TRANSAZIONE_RISPEDIZIONE = null;
	 
	public IField SISTEMA_RICHIEDENTE = null;
	 
	public IField UTENTE_RICHIEDENTE = null;
	 
	public IField ID_FATTURA = null;
	 
	public IField CODICE_DIPARTIMENTO = null;
	 
	public IField RICHIESTA_XML = null;
	 
	public IField RISPOSTA_XML = null;
	 
	public IField OPERAZIONE = null;
	 
	public IField TIPO_OPERAZIONE = null;
	 
	public IField STATO = null;
	 
	public IField DATA_ULTIMA_TRASMISSIONE = null;
	 
	public IField DATA_ULTIMO_TENTATIVO_ESITO = null;
	 
	public IField CODICI_ERRORE = null;
	 
	public IField RISPEDIZIONE = null;
	 
	public IField RISPEDIZIONE_DOPO_QUERY = null;
	 
	public IField RISPEDIZIONE_MAX_TENTATIVI = null;
	 
	public IField RISPEDIZIONE_PROSSIMO_TENTATIVO = null;
	 
	public IField RISPEDIZIONE_NUMERO_TENTATIVI = null;
	 
	public IField RISPEDIZIONE_ULTIMO_TENTATIVO = null;
	 
	public org.govmix.proxy.fatturapa.orm.model.PccTracciaTrasmissioneModel PCC_TRACCIA_TRASMISSIONE = null;
	 
	public org.govmix.proxy.fatturapa.orm.model.FatturaElettronicaModel FATTURA_ELETTRONICA = null;
	 

	@Override
	public Class<PccTraccia> getModeledClass(){
		return PccTraccia.class;
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