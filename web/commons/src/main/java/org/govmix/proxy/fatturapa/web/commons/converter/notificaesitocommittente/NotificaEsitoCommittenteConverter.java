/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2020 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2020 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
package org.govmix.proxy.fatturapa.web.commons.converter.notificaesitocommittente;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.govmix.proxy.fatturapa.notificaesitocommittente.MotivoRifiuto;
import org.govmix.proxy.fatturapa.notificaesitocommittente.NotificaEC;
import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.orm.IdFattura;
import org.govmix.proxy.fatturapa.orm.IdUtente;
import org.govmix.proxy.fatturapa.orm.NotificaEsitoCommittente;
import org.govmix.proxy.fatturapa.orm.constants.EsitoCommittenteType;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.FatturaPassivaBD;

public class NotificaEsitoCommittenteConverter {

	private static Unmarshaller unmarshaller;
	
	static {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(NotificaEC.class);
			unmarshaller = jaxbContext.createUnmarshaller();
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private NotificaEC esito;
	private IdUtente idUtente;
	private boolean modalitaBatch;
	private FatturaPassivaBD fatturaElettronicaBD;
	
	public NotificaEsitoCommittenteConverter(InputStream is, IdUtente idUtente) throws Exception {
		this((NotificaEC) unmarshaller.unmarshal(is), idUtente);
	}
	
	public NotificaEsitoCommittenteConverter(NotificaEC esito, IdUtente idUtente) throws Exception {
		this(esito, idUtente, false);
	}
	
	public NotificaEsitoCommittenteConverter(NotificaEC esito, IdUtente idUtente, boolean modalitaBatch) throws Exception {
		this.esito =  esito;
		this.idUtente = idUtente;
		this.modalitaBatch = modalitaBatch;
		this.fatturaElettronicaBD = new FatturaPassivaBD();
		
	}
	
	public NotificaEsitoCommittente getNotificaEsitoCommittente() throws Exception {
		NotificaEsitoCommittente notificaEsitoCommittente = new NotificaEsitoCommittente();
		
		IdFattura idFattura = this.fatturaElettronicaBD.newIdFattura();
		idFattura.setIdentificativoSdi(this.esito.getIdentificativoSdi().longValue());
		idFattura.setPosizione(this.esito.getPosizione().intValue());
		notificaEsitoCommittente.setIdFattura(idFattura);
		
		FatturaElettronica fattura = this.fatturaElettronicaBD.get(idFattura);
		
		notificaEsitoCommittente.setNomeFile(fattura.getNomeFile());
		notificaEsitoCommittente.setAnno(fattura.getAnno());
		notificaEsitoCommittente.setNumeroFattura(fattura.getNumero());
		notificaEsitoCommittente.setDataInvioEnte(new Date());
		
		notificaEsitoCommittente.setIdentificativoSdi(idFattura.getIdentificativoSdi());
		notificaEsitoCommittente.setPosizione(idFattura.getPosizione());
		notificaEsitoCommittente.setTentativiConsegnaSdi(0);
		
		notificaEsitoCommittente.setUtente(this.getIdUtente());
		notificaEsitoCommittente.setModalitaBatch(this.modalitaBatch);
		
		EsitoCommittenteType esitoCommittente;
		switch(this.esito.getEsito()) {
		case EC_01: esitoCommittente = EsitoCommittenteType.EC01;
			break;
		case EC_02: esitoCommittente = EsitoCommittenteType.EC02;
			break;
		default: esitoCommittente = EsitoCommittenteType.EC02;
			break;
		}
		
		notificaEsitoCommittente.setEsito(esitoCommittente);
		
		notificaEsitoCommittente.setMotiviRifiuto(this.getMotiviRifiuto());
		notificaEsitoCommittente.setDescrizione(this.getDescrizione());

		return notificaEsitoCommittente;
		
	}

	private String getMotiviRifiuto() {
		if(this.esito.getMotivoRifiuto()!=null &&!this.esito.getMotivoRifiuto().isEmpty()) {
			
			StringBuffer sb = new StringBuffer();
			for(MotivoRifiuto mr: this.esito.getMotivoRifiuto()) {
				if(sb.length()>0) {
					sb.append(",");
				}
				sb.append(mr.toString());
			}
			
			return sb.toString();
		} else {
			return null;
		}
	}

	private String getDescrizione() {
		if(this.esito.getMotivoRifiuto()!=null &&!this.esito.getMotivoRifiuto().isEmpty()) {
			
			StringBuffer sb = new StringBuffer();
			for(MotivoRifiuto mr: this.esito.getMotivoRifiuto()) {
				if(sb.length()>0) {
					sb.append(". ");
				}
				sb.append(convertToDescrizione(mr));
			}
			
			return sb.toString();
		} else {
			return null;
		}
	}

	private String convertToDescrizione(MotivoRifiuto mr) {
		switch(mr) {
		case MR_01: return "Soggetto destinatario errato - falscher Empf\u00E4nger";
		case MR_02: return "Omesso o errato CIG o CUP - fehlender oder falscher CIG oder CUP";
		case MR_03: return "Omesso o errato numero e data dell’atto d’impegno - fehlende oder falsche Nummer und Datum des Zweckbindungsaktes";
		default: return null;
		
		}
	}
	
	public static List<MotivoRifiuto> getMotiviRifiuto(String motiviRifiutoString) {
		List<MotivoRifiuto> mrLst = new ArrayList<MotivoRifiuto>();
		if(motiviRifiutoString == null)
			return mrLst;
		
		String[] mr = motiviRifiutoString.split(",");
		
		for(String m: mr) {
			try {
				mrLst.add(MotivoRifiuto.fromValue(m));
			} catch(Exception e) {}
		}
		
		return mrLst;
		
	}

	public IdUtente getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(IdUtente idUtente) {
		this.idUtente = idUtente;
	}
}
