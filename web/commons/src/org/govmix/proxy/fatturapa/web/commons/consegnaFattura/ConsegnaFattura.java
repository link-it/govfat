/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2015 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2015 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
package org.govmix.proxy.fatturapa.web.commons.consegnaFattura;

import java.sql.Connection;
import java.util.List;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.AllegatoFattura;
import org.govmix.proxy.fatturapa.FatturaElettronica;
import org.govmix.proxy.fatturapa.IdFattura;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.AllegatoFatturaBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.FatturaElettronicaBD;
import org.govmix.proxy.fatturapa.web.commons.converter.fattura.AbstractFatturaConverter;
import org.govmix.proxy.fatturapa.web.commons.converter.fattura.FatturaV10Converter;
import org.govmix.proxy.fatturapa.web.commons.converter.fattura.FatturaV11Converter;

public class ConsegnaFattura {

	private FatturaElettronicaBD fatturaBD;
	private AllegatoFatturaBD allegatoBD;
	private boolean validazioneDAOAbilitata;
	private Logger log;
	
	public ConsegnaFattura(Logger log, boolean validazioneDaoAbilitata) throws Exception {
		this.log = log;
		this.validazioneDAOAbilitata = validazioneDaoAbilitata;
		this.fatturaBD = new FatturaElettronicaBD(this.log);
		this.allegatoBD = new AllegatoFatturaBD(this.log);
	}

	public ConsegnaFattura(Logger log, boolean validazioneDaoAbilitata, Connection conn, boolean autocommit) throws Exception {
		this.log = log;
		this.validazioneDAOAbilitata = validazioneDaoAbilitata;
		this.fatturaBD = new FatturaElettronicaBD(this.log, conn, autocommit);
		this.allegatoBD = new AllegatoFatturaBD(this.log, conn, autocommit);
	}

	public void consegnaFattura(ConsegnaFatturaParameters params) throws Exception {

		AbstractFatturaConverter<?> converter = null;

		if(it.gov.fatturapa.sdi.fatturapa.v1_0.constants.FormatoTrasmissioneType.SDI10.equals(params.getFormatoFatturaPA())) {
			converter = new FatturaV10Converter(params.getXml(), params);
		}else if(it.gov.fatturapa.sdi.fatturapa.v1_1.constants.FormatoTrasmissioneType.SDI11.equals(params.getFormatoFatturaPA())) {
			converter = new FatturaV11Converter(params.getXml(), params);
		}


		FatturaElettronica fatturaElettronica = converter.getFatturaElettronica();
		List<AllegatoFattura> allegatiLst = converter.getAllegati();

		if(this.validazioneDAOAbilitata) {
			this.fatturaBD.validate(fatturaElettronica);
			if(allegatiLst != null) {
				IdFattura idFattura = this.fatturaBD.convertToId(fatturaElettronica);

				for(AllegatoFattura allegato: allegatiLst) {
					
					allegato.setIdFattura(idFattura);
					this.allegatoBD.validate(allegato);
				}
			}	
		}

		this.fatturaBD.create(fatturaElettronica);

		if(allegatiLst != null) {
			for(AllegatoFattura allegato: allegatiLst) {
				this.allegatoBD.create(allegato);
			}
		}

	}

}
