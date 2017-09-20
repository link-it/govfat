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
package org.govmix.proxy.fatturapa.web.commons.businessdelegate;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.orm.IdFattura;
import org.govmix.proxy.fatturapa.orm.IdLotto;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.converter.FatturaElettronicaFieldConverter;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.filter.FatturaAttivaFilter;
import org.openspcoop2.generic_project.beans.UpdateField;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;

public class FatturaAttivaBD extends FatturaBD {


	public FatturaAttivaBD(Logger log) throws Exception {
		super(log);
	}

	public FatturaAttivaBD(Logger log, Connection connection, boolean autocommit) throws Exception {
		super(log, connection, autocommit);
	}

	public FatturaAttivaBD() throws Exception {
		this(Logger.getLogger(FatturaAttivaBD.class));
	}

	public FatturaAttivaFilter newFilter() {
		return new FatturaAttivaFilter(this.service);
	}

	public void assegnaIdentificativoSDIAInteroLotto(IdLotto idLotto, Integer identificativoSDI) throws Exception {
		try {

			StringBuffer update = new StringBuffer();

			List<Object> listObjects = new ArrayList<Object>();

			FatturaElettronicaFieldConverter converter = new FatturaElettronicaFieldConverter(this.serviceManager.getJdbcProperties().getDatabase());
			
			update.append("update "+converter.toTable(FatturaElettronica.model())+" set ");
			update.append(converter.toColumn(FatturaElettronica.model().IDENTIFICATIVO_SDI, false)).append(" = ? ");
			listObjects.add(identificativoSDI);
			
			update.append(" where ").append(converter.toColumn(FatturaElettronica.model().IDENTIFICATIVO_SDI, false)).append(" = ? ");
			listObjects.add(idLotto.getIdentificativoSdi());
			
			this.serviceSearch.nativeUpdate(update.toString(), listObjects.toArray(new Object[]{}));
			
		} catch (ServiceException e) {
			this.log.error("Errore durante la assegnaIdentificativoSDIAInteroLotto: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la assegnaIdentificativoSDIAInteroLotto: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public void aggiornaProtocollo(IdFattura idFattura, String protocollo) throws Exception {
		try {
			this.service.updateFields(idFattura, new UpdateField(FatturaElettronica.model().PROTOCOLLO, protocollo));
		} catch (ServiceException e) {
			this.log.error("Errore durante la aggiornaProtocollo: " + e.getMessage(), e);
			throw new Exception(e);
		} catch (NotImplementedException e) {
			this.log.error("Errore durante la aggiornaProtocollo: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}


}
