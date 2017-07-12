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
import java.util.Date;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.IdFattura;
import org.govmix.proxy.fatturapa.orm.PccContabilizzazione;
import org.govmix.proxy.fatturapa.orm.PccPagamento;
import org.govmix.proxy.fatturapa.orm.PccScadenza;

import it.tesoro.fatture.EsitoOkKoTipo;

public class PccOperazioneContabileBD extends BaseBD {

	private PccContabilizzazioneBD contabilizzazioneBD = null;
	private PccScadenzaBD scadenzaBD = null;
	private PccPagamentoBD pagamentoBD = null;

	public PccOperazioneContabileBD() throws Exception {
		this(Logger.getLogger(PccOperazioneContabileBD.class));
	}

	public PccOperazioneContabileBD(Logger log) throws Exception {
		super(log);
		this.pagamentoBD = new PccPagamentoBD(log);
		this.contabilizzazioneBD = new PccContabilizzazioneBD(log);
		this.scadenzaBD = new PccScadenzaBD(log);
	}

	public PccOperazioneContabileBD(Logger log, Connection connection, boolean autocommit) throws Exception {
		super(log, connection, autocommit);
		this.pagamentoBD = new PccPagamentoBD(log);
		this.scadenzaBD = new PccScadenzaBD(log);
		this.contabilizzazioneBD = new PccContabilizzazioneBD(log);
	}

	public void create(PccScadenza scadenza) throws Exception {
		this.scadenzaBD.create(scadenza);
	}

	public void create(PccPagamento pagamento) throws Exception {
		this.pagamentoBD.create(pagamento);
	}

	public void create(PccContabilizzazione contabilizzazione) throws Exception {
		this.contabilizzazioneBD.create(contabilizzazione);
	}

//	public void deleteScadenze(IdFattura idFattura) throws Exception {
//		this.scadenzaBD.deleteScadenze(idFattura); 
//	}

	public void deleteScadenze(IdFattura idFattura, boolean deleteAll) throws Exception {
		this.scadenzaBD.deleteScadenze(idFattura, deleteAll); 
	}

	public void deleteContabilizzazioni(IdFattura idFattura) throws Exception {
		this.contabilizzazioneBD.deleteContabilizzazioni(idFattura); 
	}

	public void updateCancellazioneComunicazioniScadenzaByIdFattura(IdFattura idFattura, EsitoOkKoTipo esito, Date dataQuery) throws Exception {
		this.scadenzaBD.updateCancellazioneComunicazioniScadenzaByIdFattura(idFattura, esito, dataQuery);
	}

	public void updateStornoPagamentoByIdFattura(IdFattura idFattura, EsitoOkKoTipo esito, Date dataQuery) throws Exception {
		this.pagamentoBD.updateStornoPagamentoByIdFattura(idFattura, esito, dataQuery);
	}
	public void updateStornoContabilizzazioneByIdFattura(IdFattura idFattura, EsitoOkKoTipo esito, Date dataQuery) throws Exception {
		this.contabilizzazioneBD.updateStornoContabilizzazioneByIdFattura(idFattura, esito, dataQuery); 
	}

	public void updateScadenzaByIdFattura(IdFattura idFattura, EsitoOkKoTipo esito, Date dataQuery) throws Exception {
		this.scadenzaBD.updateScadenzaByIdFattura(idFattura, esito, dataQuery);
	}

	public boolean existScadenze(IdFattura idFattura) throws Exception {
		return this.scadenzaBD.getNumeroScadenzeByIdFattura(idFattura) > 0;
	}
	
	public boolean existScadenze(IdFattura idFattura, boolean includeAll) throws Exception {
		return this.scadenzaBD.getNumeroScadenzeByIdFattura(idFattura, includeAll) > 0;
	}
	
	public double getTotalePagamentiByIdFattura(IdFattura idFattura) throws Exception {
		return this.pagamentoBD.getTotalePagamentiByIdFattura(idFattura);
	}
	
	public Date getDataUltimaOperazioneByIdFattura(IdFattura idFattura) throws Exception {
		Date dataPagamento = this.pagamentoBD.getMaxDateByIdFattura(idFattura);
		Date dataScadenza = this.scadenzaBD.getMaxDateByIdFattura(idFattura);
		Date dataContabilizzazione = this.contabilizzazioneBD.getMaxDateByIdFattura(idFattura);
		
		Date dataMax = dataPagamento;

		if(dataScadenza != null)
			if(dataMax == null || dataMax.compareTo(dataScadenza) < 0) dataMax = dataScadenza;
		if(dataContabilizzazione != null)
			if(dataMax == null || dataMax.compareTo(dataContabilizzazione) < 0) dataMax = dataContabilizzazione;
		
		return dataMax;
		
	}
}
