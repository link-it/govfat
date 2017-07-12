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
package org.govmix.proxy.fatturapa.web.commons.exporter;

import java.io.OutputStream;
import java.sql.Connection;
import java.util.List;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.IdFattura;
import org.govmix.proxy.fatturapa.orm.IdLotto;
import org.govmix.proxy.fatturapa.orm.LottoFatture;
import org.govmix.proxy.fatturapa.orm.dao.ILottoFattureServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.JDBCLottoFattureServiceSearch;
import org.govmix.proxy.fatturapa.web.commons.dao.DAOFactory;
import org.govmix.proxy.fatturapa.web.commons.exporter.exception.ExportException;
import org.openspcoop2.generic_project.exception.MultipleResultException;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.IExpression;

public class LottoSingleFileExporter extends AbstractSingleFileExporter<LottoFatture, IdLotto> {

	private ILottoFattureServiceSearch lottoFattureSearchDAO;

	public LottoSingleFileExporter(Logger log) throws ServiceException,
	NotImplementedException, Exception {
		super(log);
		this.lottoFattureSearchDAO = DAOFactory.getInstance().getServiceManager().getLottoFattureServiceSearch();
	}

	public LottoSingleFileExporter(Logger log, Connection connection, boolean autocommit) throws ServiceException, NotImplementedException, Exception {
		super(log, connection, autocommit);
		this.lottoFattureSearchDAO = DAOFactory.getInstance().getServiceManager(connection, autocommit).getLottoFattureServiceSearch();
	}

	@Override
	public LottoFatture convertToObject(IdLotto id) throws ExportException {
		try {
			return this.lottoFattureSearchDAO.get(id);
		} catch (ServiceException e) {
			throw new ExportException(e);
		} catch (NotFoundException e) {
			throw new ExportException(e);
		} catch (MultipleResultException e) {
			throw new ExportException(e);
		} catch (NotImplementedException e) {
			throw new ExportException(e);
		}
	}

	@Override
	public byte[] getRawContent(LottoFatture object) {
		return object.getXml();
	}

	private int getIndex(LottoFatture object) {
		String nomeFile = object.getNomeFile(); 
		if(nomeFile.endsWith(".p7m")) {
			nomeFile.substring(0, ".p7m".length());
		}
		return nomeFile.lastIndexOf(".");
	}
	
	@Override
	public String getRawName(LottoFatture object) {
		int endIndex = getIndex(object);

		String nomeLotto = endIndex > 0 ? object.getNomeFile().substring(0, endIndex): object.getNomeFile();
		return "lotto-"+nomeLotto + "-" + object.getIdentificativoSdi();
	}

	@Override
	public String getRawExtension(LottoFatture object) {
		
		int endIndex = getIndex(object);

		if(endIndex > 0) {
			return object.getNomeFile().substring(endIndex + 1);
		} else {
			switch(object.getFormatoArchivioInvioFattura()){
			case P7M: return "xml.p7m";
			case XML: return "xml";
			default: return null;
			}
		}
	}

	@Override
	protected List<IdFattura> findIdFattura(String[] ids, boolean isAll,
			IExpression fattExpr) throws ServiceException, NotFoundException {
		try {
			LottoFatture lotto = ((JDBCLottoFattureServiceSearch)this.lottoFattureSearchDAO).get(Long.parseLong(ids[0]));
			return this.fatturaBD.findAllIdFatturaByIdentificativoSdi(lotto.getIdentificativoSdi());
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void export(LottoFatture object, OutputStream out, FORMAT format)
			throws ExportException {
		switch(format){
		case RAW: this.exportAsRaw(object, out);
		break;
		case PDF:
		case ZIP: 
		default:throw new ExportException("Formato ["+format+"] non supportato");
		}

	}

	@Override
	public LottoFatture convertToObject(String id) throws ExportException {
		try {
			return ((JDBCLottoFattureServiceSearch)this.lottoFattureSearchDAO).get(Long.parseLong(id));
		} catch (ServiceException e) {
			throw new ExportException(e);
		} catch (NotFoundException e) {
			throw new ExportException(e);
		} catch (MultipleResultException e) {
			throw new ExportException(e);
		} catch (NotImplementedException e) {
			throw new ExportException(e);
		}

	}


}
