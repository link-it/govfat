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
package org.govmix.proxy.fatturapa.web.commons.converter.fattura;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.govmix.proxy.fatturapa.orm.AllegatoFattura;
import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.orm.constants.StatoConsegnaType;
import org.govmix.proxy.fatturapa.orm.constants.StatoProtocollazioneType;
import org.govmix.proxy.fatturapa.web.commons.consegnaFattura.ConsegnaFatturaParameters;

public abstract class AbstractFatturaConverter<T> {

	private T fattura;
	protected ConsegnaFatturaParameters params;
	protected byte[] fatturaAsByte;
	protected SimpleDateFormat sdfYear;
	
	public AbstractFatturaConverter(T t, byte[] fatturaAsString, ConsegnaFatturaParameters params) throws Exception {
		this.fattura = t;
		this.fatturaAsByte = fatturaAsString;
		this.params = params;
		this.sdfYear = new SimpleDateFormat("yyyy");
		this.validate();
	}
	
	public abstract void validate() throws Exception;
	
	public abstract List<String> getCausali();
	public abstract double getImportoTotaleDocumento();
	public abstract double getImportoTotaleRiepilogo();
	
	public String getCausale() {
		StringBuffer sbCausale = new StringBuffer();
		List<String> causali = this.getCausali(); 
		if(causali != null) {
			for(String causale: causali) {
				if(sbCausale.length() > 0) {
					sbCausale.append('|');
				}
				sbCausale.append(causale);
			}
		}
		if(sbCausale.length() > 0) {
			return sbCausale.toString();
		} else return null;
	}

	public FatturaElettronica getFatturaElettronica() throws Exception {
		
		FatturaElettronica fatturaElettronica = this.getDatiComuni();
		this.populateFatturaConDatiSpecifici(fatturaElettronica);

		return fatturaElettronica;
	}

	
	protected abstract void populateFatturaConDatiSpecifici(FatturaElettronica fatturaElettronica);

	private FatturaElettronica getDatiComuni() throws Exception {
		FatturaElettronica fatturaElettronica = new FatturaElettronica();

		fatturaElettronica.setIdentificativoSdi(this.params.getIdentificativoSdI());
		fatturaElettronica.setPosizione(this.params.getPosizioneFatturaPA());
		fatturaElettronica.setNomeFile(this.params.getNomeFile());
		fatturaElettronica.setMessageId(this.params.getMessageId());
		fatturaElettronica.setProtocollo(this.params.getProtocollo());
		fatturaElettronica.setCodiceDestinatario(this.params.getCodiceDestinatario());
		fatturaElettronica.setImportoTotaleDocumento(this.getImportoTotaleDocumento());
		fatturaElettronica.setImportoTotaleRiepilogo(this.getImportoTotaleRiepilogo());
		fatturaElettronica.setStatoConsegna(StatoConsegnaType.NON_CONSEGNATA);
		fatturaElettronica.setStatoProtocollazione(StatoProtocollazioneType.NON_PROTOCOLLATA);
		
		fatturaElettronica.setTentativiConsegna(new Integer(0));

		//Cedente prestatore
		if(this.params.getCedentePrestatore().getDenominazione() != null) {
			fatturaElettronica.setCedentePrestatoreDenominazione(this.params.getCedentePrestatore().getDenominazione());
		} else {
			fatturaElettronica.setCedentePrestatoreDenominazione(this.params.getCedentePrestatore().getNome() + " " + this.params.getCedentePrestatore().getCognome());
		}
		
		fatturaElettronica.setCedentePrestatoreCodiceFiscale(this.params.getCedentePrestatore().getIdCodice());
		fatturaElettronica.setCedentePrestatorePaese(this.params.getCedentePrestatore().getIdPaese());

		
		//Cessionario committente
		if(this.params.getCessionarioCommittente().getDenominazione() != null) {
			fatturaElettronica.setCessionarioCommittenteDenominazione(this.params.getCessionarioCommittente().getDenominazione());
		} else {
			fatturaElettronica.setCessionarioCommittenteDenominazione(this.params.getCessionarioCommittente().getNome() + " " + this.params.getCessionarioCommittente().getCognome());
		}
		
		if(this.params.getCessionarioCommittente().getIdCodice() == null || this.params.getCessionarioCommittente().getIdPaese() == null) {
			if(this.params.getCessionarioCommittente().getCodiceFiscale() != null) {
				fatturaElettronica.setCessionarioCommittenteCodiceFiscale(this.params.getCessionarioCommittente().getCodiceFiscale());
			}
		} else {
			if(this.params.getCessionarioCommittente().getIdCodice() != null && this.params.getCessionarioCommittente().getIdPaese() != null) {
				fatturaElettronica.setCessionarioCommittenteCodiceFiscale(this.params.getCessionarioCommittente().getIdCodice());
				fatturaElettronica.setCessionarioCommittentePaese(this.params.getCessionarioCommittente().getIdPaese());
			}
		}
		
		//Terzo intermediario o soggetto emittente
		if(this.params.getTerzoIntermediarioOSoggettoEmittente() != null) {
			
			if(this.params.getTerzoIntermediarioOSoggettoEmittente().getDenominazione() != null) {
				fatturaElettronica.setTerzoIntermediarioOSoggettoEmittenteDenominazione(this.params.getTerzoIntermediarioOSoggettoEmittente().getDenominazione());
			} else {
				fatturaElettronica.setTerzoIntermediarioOSoggettoEmittenteDenominazione(this.params.getTerzoIntermediarioOSoggettoEmittente().getNome() + " " + this.params.getTerzoIntermediarioOSoggettoEmittente().getCognome());
			}
			
			if(this.params.getTerzoIntermediarioOSoggettoEmittente().getIdCodice() == null || this.params.getTerzoIntermediarioOSoggettoEmittente().getIdPaese() == null) {
				if(this.params.getTerzoIntermediarioOSoggettoEmittente().getCodiceFiscale() != null) {
					fatturaElettronica.setTerzoIntermediarioOSoggettoEmittenteCodiceFiscale(this.params.getTerzoIntermediarioOSoggettoEmittente().getCodiceFiscale());
				}
			} else {
				if(this.params.getTerzoIntermediarioOSoggettoEmittente().getIdCodice() != null && this.params.getTerzoIntermediarioOSoggettoEmittente().getIdPaese() != null) {
					fatturaElettronica.setTerzoIntermediarioOSoggettoEmittenteCodiceFiscale(this.params.getTerzoIntermediarioOSoggettoEmittente().getIdCodice());
					fatturaElettronica.setTerzoIntermediarioOSoggettoEmittentePaese(this.params.getTerzoIntermediarioOSoggettoEmittente().getIdPaese());
				}
			}
		}
		
		if(this.params.getDataRicezione()!= null) {
			fatturaElettronica.setDataRicezione(this.params.getDataRicezione());
		} else {
			fatturaElettronica.setDataRicezione(new Date());
		}
		
		return fatturaElettronica;
	}
	
	public abstract List<AllegatoFattura> getAllegati();

	public T getFattura() {
		return this.fattura;
	}


}
