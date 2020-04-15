/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2018 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2018 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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

import java.util.HashMap;
import java.util.List;

import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.web.commons.consegnaFattura.ConsegnaFatturaParameters;
import org.govmix.proxy.fatturapa.web.commons.consegnaFattura.InserimentoLottiException;
import org.govmix.proxy.fatturapa.web.commons.consegnaFattura.InserimentoLottiException.CODICE;
import org.openspcoop2.protocol.sdi.utils.SDILottoUtils;

public abstract class AbstractLottoSDIConverter<T> extends AbstractLottoConverter<T> {

	public AbstractLottoSDIConverter(T t, byte[] fatturaAsString, ConsegnaFatturaParameters params) throws InserimentoLottiException {
		super(t, fatturaAsString, params);
	}

	protected void initFatture() throws InserimentoLottiException {
		try {
			if(this.fatture == null) {
				List<byte[]> fattureLst = SDILottoUtils.splitLotto(this.lottoAsByte);

				this.fatture = new HashMap<String, FatturaElettronica>();
				for(int i =0; i < fattureLst.size(); i++) {
					FatturaElettronica fat = initFatturaElettronica(i, fattureLst.get(i));
					this.fatture.put((i+1) + "", fat);
				}
			}
		} catch(Exception e) {
			throw new InserimentoLottiException(CODICE.ERRORE_GENERICO, e.getMessage());
		}
	} 
}
