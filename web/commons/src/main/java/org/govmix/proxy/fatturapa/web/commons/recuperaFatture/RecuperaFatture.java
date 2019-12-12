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
package org.govmix.proxy.fatturapa.web.commons.recuperaFatture;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.orm.IdDipartimento;
import org.govmix.proxy.fatturapa.orm.IdFattura;
import org.govmix.proxy.fatturapa.orm.IdLotto;
import org.govmix.proxy.fatturapa.orm.IdUtente;
import org.govmix.proxy.fatturapa.orm.Utente;
import org.govmix.proxy.fatturapa.orm.constants.StatoConsegnaType;
import org.govmix.proxy.fatturapa.recuperofatture.Fattura;
import org.govmix.proxy.fatturapa.recuperofatture.ListaFattureNonConsegnateResponse;
import org.govmix.proxy.fatturapa.recuperofatture.ListaFattureResponse;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.DipartimentoBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.FatturaPassivaBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.LottoFatturePassiveBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.UtenteBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.filter.FatturaPassivaFilter;
import org.govmix.proxy.fatturapa.web.commons.exporter.FatturaSingleFileExporter;

public class RecuperaFatture {

	private FatturaPassivaBD fatturaPassivaBD;
	private LottoFatturePassiveBD lottoFattureBD;
	private DipartimentoBD dipartimentoBD;
	private UtenteBD utenteBD;
	private FatturaSingleFileExporter sfe;
	
	private static Marshaller marshaller;
	
	static {
		try {
			JAXBContext ctx = JAXBContext.newInstance(ListaFattureNonConsegnateResponse.class,ListaFattureResponse.class);
			marshaller = ctx.createMarshaller();
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public RecuperaFatture() throws Exception {
		this(Logger.getLogger(RecuperaFatture.class));
	}
	
	public IdFattura newIdFattura() {
		return this.fatturaPassivaBD.newIdFattura();
	}
	public RecuperaFatture(Logger log) throws Exception {
		this.fatturaPassivaBD = new FatturaPassivaBD(log);
		this.lottoFattureBD = new LottoFatturePassiveBD(log);
		this.dipartimentoBD = new DipartimentoBD(log);
		this.utenteBD = new UtenteBD(log);
		
		this.sfe = new FatturaSingleFileExporter(log);

	}
	
	public String cercaFattureNonConsegnate(IdUtente idUtente, Integer limit) throws Exception {
		Utente utente = this.utenteBD.findByUsername(idUtente.getUsername());
		
		FatturaPassivaFilter filter = this.fatturaPassivaBD.newFilter();
		filter.setUtente(utente);
		filter.setModalitaPush(false);
		filter.setStatiConsegna(Arrays.asList(StatoConsegnaType.NON_CONSEGNATA));
		
		filter.setOffset(0);
		filter.setLimit(limit);

		List<FatturaElettronica> lst = this.fatturaPassivaBD.findAll(filter);
		
		ListaFattureNonConsegnateResponse response = new ListaFattureNonConsegnateResponse();
		
		for(FatturaElettronica id : lst) {
			Fattura fattura = new Fattura();
			fattura.setIdSDI(new BigInteger(id.getIdentificativoSdi().toString()));
			fattura.setPosizione(new BigInteger(id.getPosizione().toString()));
			response.getFattura().add(fattura);
		}
		
		ByteArrayOutputStream os = null;
		try {
			os = new ByteArrayOutputStream();
			marshaller.marshal(response, os);
			return os.toString();
		} finally {
			if(os != null) {
				try {
					os.close();
				} catch(Exception e) {}
			}
		}
		
	}
	
	public byte[] recuperaFatturaNonConsegnata(IdUtente idUtente, IdFattura idFattura) throws Exception {
		ByteArrayOutputStream os = null;
		try {
			
			this.checkFattura(idUtente, idFattura);
			
			//aggiorno lo stato della consegna
			this.fatturaPassivaBD.updateStatoConsegna(idFattura, StatoConsegnaType.CONSEGNATA, null);
			
			
			os = new ByteArrayOutputStream();
			FatturaElettronica fattura = this.fatturaPassivaBD.get(idFattura); 
			sfe.exportAsZip(Arrays.asList(fattura), os);
			return os.toByteArray();
		} finally {
			if(os != null) {
				try {
					os.flush();
					os.close();
				} catch(Exception e) {}
			}
		}
		
	}

	private void checkFattura(IdUtente idUtente, IdFattura idFattura) throws Exception {

		FatturaElettronica fattura = fatturaPassivaBD.get(idFattura);

		//check fattura consegnata
		//NOTA: 8-04-2015: Vincolo rilassato
//		if(!StatoConsegnaType.NON_CONSEGNATA.equals(fattura.getStatoConsegna())) {
//			throw new Exception("Fattura ["+idFattura.toJson()+"] gia' consegnata");
//		}
		
		IdDipartimento idDipartimento = new IdDipartimento();
		idDipartimento.setCodice(fattura.getCodiceDestinatario());
		
		//check utente appartenente a quel dipartimento
		if(!this.utenteBD.belongsTo(idUtente, idDipartimento)) {
			throw new Exception("L'utente ["+idUtente.toJson()+"] non appartiene al dipartimento destinatario della fattura.");
		}
		
		//check dipartimento pull
		if(!dipartimentoBD.isPull(idDipartimento)) {
			throw new Exception("Il dipartimento destinatario della fattura ["+idDipartimento+"] ha abilitato la modalita' push di consegna delle fatture.");
		}
		
	}

	public String cercaLottiRicevuti(String codiceUfficio, Date dataIniziale,
			Date dataFinale) throws Exception {
		List<IdLotto> lst = this.lottoFattureBD.
				getIdLottiRicevuti(codiceUfficio, dataIniziale, dataFinale);
		
		ListaFattureResponse response = new ListaFattureResponse();
		
		for(IdLotto id : lst) {
			Fattura fattura = new Fattura();
			fattura.setIdSDI(new BigInteger(id.getIdentificativoSdi().toString()));
//			fattura.setPosizione(new BigInteger(id.getPosizione().toString()));
			response.getFattura().add(fattura);
		}
		
		ByteArrayOutputStream os = null;
		try {
			os = new ByteArrayOutputStream();
			marshaller.marshal(response, os);
			return os.toString();
		} finally {
			if(os != null) {
				try {
					os.close();
				} catch(Exception e) {}
			}
		}

	}

	public String cercaFattureRicevute(String idSdi) throws Exception {
		List<IdFattura> lst = this.fatturaPassivaBD.
				getIdFattureRicevute(idSdi);
		
		ListaFattureResponse response = new ListaFattureResponse();
		
		for(IdFattura id : lst) {
			Fattura fattura = new Fattura();
			fattura.setIdSDI(new BigInteger(id.getIdentificativoSdi().toString()));
			fattura.setPosizione(new BigInteger(id.getPosizione().toString()));
			response.getFattura().add(fattura);
		}
		
		ByteArrayOutputStream os = null;
		try {
			os = new ByteArrayOutputStream();
			marshaller.marshal(response, os);
			return os.toString();
		} finally {
			if(os != null) {
				try {
					os.close();
				} catch(Exception e) {}
			}
		}

	}
	
}
