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
package org.govmix.proxy.fatturapa.web.commons.consegnaFattura;

import java.sql.Connection;
import java.util.List;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.AllegatoFattura;
import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.orm.IdFattura;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.AllegatoFatturaBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.FatturaAttivaBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.FatturaPassivaBD;
import org.govmix.proxy.fatturapa.web.commons.converter.fattura.AbstractFatturaConverter;
import org.govmix.proxy.fatturapa.web.commons.converter.fattura.FPA12Converter;
import org.govmix.proxy.fatturapa.web.commons.converter.fattura.FatturaV10Converter;
import org.govmix.proxy.fatturapa.web.commons.converter.fattura.FatturaV11Converter;

public class ConsegnaFattura {

	private FatturaPassivaBD fatturaPassivaBD;
	private FatturaAttivaBD fatturaAttivaBD;
	private AllegatoFatturaBD allegatoBD;
	private boolean validazioneDAOAbilitata;
	private Logger log;
	
	public ConsegnaFattura(Logger log, boolean validazioneDaoAbilitata) throws Exception {
		this.log = log;
		this.validazioneDAOAbilitata = validazioneDaoAbilitata;
		this.fatturaPassivaBD = new FatturaPassivaBD(this.log);
		this.fatturaAttivaBD = new FatturaAttivaBD(this.log);
		this.allegatoBD = new AllegatoFatturaBD(this.log);
	}

	public ConsegnaFattura(Logger log, boolean validazioneDaoAbilitata, Connection conn, boolean autocommit) throws Exception {
		this.log = log;
		this.validazioneDAOAbilitata = validazioneDaoAbilitata;
		this.fatturaPassivaBD = new FatturaPassivaBD(this.log, conn, autocommit);
		this.fatturaAttivaBD = new FatturaAttivaBD(this.log, conn, autocommit);
		this.allegatoBD = new AllegatoFatturaBD(this.log, conn, autocommit);
	}

	public void consegnaFattura(ConsegnaFatturaParameters params) throws Exception {

		AbstractFatturaConverter<?> converter = null;

		if(it.gov.fatturapa.sdi.fatturapa.v1_0.constants.FormatoTrasmissioneType.SDI10.equals(params.getFormatoFatturaPA())) {
			converter = new FatturaV10Converter(params.getXml(), params);
		}else if(it.gov.fatturapa.sdi.fatturapa.v1_1.constants.FormatoTrasmissioneType.SDI11.equals(params.getFormatoFatturaPA())) {
			converter = new FatturaV11Converter(params.getXml(), params);
		}else if(it.gov.agenziaentrate.ivaservizi.docs.xsd.fatture.v1_2.constants.FormatoTrasmissioneType.FPA12.equals(params.getFormatoFatturaPA()) || 
				it.gov.agenziaentrate.ivaservizi.docs.xsd.fatture.v1_2.constants.FormatoTrasmissioneType.FPR12.equals(params.getFormatoFatturaPA())) {
			converter = new FPA12Converter(params.getXml(), params);
		} else {
			throw new Exception("Formato FatturaPA ["+params.getFormatoFatturaPA()+"] non riconosciuto");
		}

		

		FatturaElettronica fatturaElettronica = converter.getFatturaElettronica();
		List<AllegatoFattura> allegatiLst = converter.getAllegati();

		IdFattura idFattura = this.fatturaPassivaBD.convertToId(fatturaElettronica);

		if(this.validazioneDAOAbilitata) {
			this.fatturaPassivaBD.validate(fatturaElettronica);
			if(allegatiLst != null) {
				for(AllegatoFattura allegato: allegatiLst) {
					allegato.setIdFattura(idFattura);
					this.allegatoBD.validate(allegato);
				}
			}	
		}
		
		if(params.isFatturazioneAttiva()) {
			this.fatturaAttivaBD.createFatturaAttiva(fatturaElettronica);
		} else {
			this.fatturaPassivaBD.createFatturaPassiva(fatturaElettronica);
		}

		if(allegatiLst != null) {
			for(AllegatoFattura allegato: allegatiLst) {
				allegato.setIdFattura(idFattura);
				this.allegatoBD.create(allegato);
			}
		}

	}

}
