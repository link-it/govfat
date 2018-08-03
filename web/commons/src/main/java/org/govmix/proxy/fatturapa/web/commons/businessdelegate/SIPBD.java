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
package org.govmix.proxy.fatturapa.web.commons.businessdelegate;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.IdSip;
import org.govmix.proxy.fatturapa.orm.SIP;
import org.govmix.proxy.fatturapa.orm.constants.StatoConsegnaType;
import org.govmix.proxy.fatturapa.orm.dao.ISIPService;
import org.govmix.proxy.fatturapa.orm.dao.ISIPServiceSearch;
import org.openspcoop2.generic_project.beans.UpdateField;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.IExpression;

public class SIPBD extends BaseBD {

	protected ISIPServiceSearch serviceSearch;
	protected ISIPService service;

	public SIPBD(Logger log) throws Exception {
		super(log);
		this.service = this.serviceManager.getSIPService();
		this.serviceSearch = this.serviceManager.getSIPServiceSearch();
	}

	public SIPBD(Logger log, Connection connection, boolean autocommit) throws Exception {
		super(log, connection, autocommit);
		this.service = this.serviceManager.getSIPService();
		this.serviceSearch = this.serviceManager.getSIPServiceSearch();
	}

	public SIPBD() throws Exception {
		this(Logger.getLogger(SIPBD.class));
	}
	
	public void create(SIP sip) throws Exception {
		try {
			this.service.create(sip);
		} catch (ServiceException e) {
			throw new Exception(e);
		} catch (NotImplementedException e) {
			throw new Exception(e);
		}
	}

	public void update(IdSip idSip, String rapportoVersamento, StatoConsegnaType statoConsegna, String numero, Integer anno, String registro) throws Exception {
		try {
			
			List<UpdateField> fields = new ArrayList<UpdateField>();
			if(numero != null)
				fields.add(new UpdateField(SIP.model().NUMERO, numero));
			if(anno != null)
				fields.add(new UpdateField(SIP.model().ANNO, anno));
			if(registro != null)
				fields.add(new UpdateField(SIP.model().REGISTRO, registro));
			if(rapportoVersamento != null)			
				fields.add(new UpdateField(SIP.model().RAPPORTO_VERSAMENTO, rapportoVersamento));
			
			fields.add(new UpdateField(SIP.model().STATO_CONSEGNA, statoConsegna));
			fields.add(new UpdateField(SIP.model().DATA_ULTIMA_CONSEGNA, new Date()));
			
			this.service.updateFields(idSip, fields.toArray(new UpdateField[]{}));
		} catch (ServiceException e) {
			throw new Exception(e);
		} catch (NotImplementedException e) {
			throw new Exception(e);
		}
	}

	public IdSip convertToId(SIP sip) throws Exception {
		try {
			return this.service.convertToId(sip);
		} catch (ServiceException e) {
			throw new Exception(e);
		} catch (NotImplementedException e) {
			throw new Exception(e);
		}
	}

	public boolean exists(String numero, int anno, String registro) throws Exception {
		try {
			IExpression exp = this.service.newExpression();
			exp.equals(SIP.model().NUMERO, numero);
			exp.equals(SIP.model().ANNO, anno);
			exp.equals(SIP.model().REGISTRO, registro);
			return this.service.count(exp).longValue() > 0;
		} catch (ServiceException e) {
			throw new Exception(e);
		} catch (NotImplementedException e) {
			throw new Exception(e);
		}
	}

	public List<SIP> findAllSipFatture(Long idEnte) throws Exception {
		String query = "select sip.numero, sip.anno, sip.registro, sip.rapporto_versamento from sip join fatture on sip.id = fatture.id_sip where (fatture.esito = 'INVIATA_ACCETTATO' or (fatture.esito is null and fatture.id_notifica_decorrenza_termini is not null)) and fatture.codice_destinatario in (select codice from dipartimenti where id_ente = ?)  and sip.stato_consegna='CONSEGNATA'";
		return _findAllSip(query, idEnte);
	}

	public List<SIP> findAllSipLotti(Long idEnte) throws Exception {
		String query = "select sip.numero, sip.anno, sip.registro, sip.rapporto_versamento from sip join lotti on sip.id = lotti.id_sip where identificativo_sdi in (select identificativo_sdi from fatture where id_sip is not null and (fatture.esito = 'INVIATA_ACCETTATO' or (fatture.esito is null and fatture.id_notifica_decorrenza_termini is not null)) and fatture.codice_destinatario in (select codice from dipartimenti where id_ente = ?) group by identificativo_sdi having count(*) > 1) and sip.stato_consegna='CONSEGNATA'";
		return _findAllSip(query, idEnte);
	}

	public List<SIP> _findAllSip(String query, Long idEnte) throws Exception {
		try {
			List<SIP> lst = new ArrayList<SIP>();
			
			List<Class<?>> returnClasses = new ArrayList<Class<?>>();
			returnClasses.add(String.class);
			returnClasses.add(Integer.class);
			returnClasses.add(String.class);
			returnClasses.add(String.class);

			List<List<Object>> select = this.service.nativeQuery(query, returnClasses, idEnte);
			
			for(List<Object> row: select) {
				SIP sip = new SIP();
				sip.setNumero((String)row.get(0));
				sip.setAnno((Integer)row.get(1));
				sip.setRegistro((String)row.get(2));
				sip.setRapportoVersamento((String)row.get(3));
				lst.add(sip);
				
			}
			
			return lst;
		} catch (ServiceException e) {
			throw new Exception(e);
		} catch (NotImplementedException e) {
			throw new Exception(e);
		}
	}

	public SIP findById (IdSip id) throws Exception {
		return this.service.get(id);
	}

}
