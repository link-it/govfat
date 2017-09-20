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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.IdFattura;
import org.govmix.proxy.fatturapa.orm.IdTraccia;
import org.govmix.proxy.fatturapa.orm.PccTraccia;
import org.govmix.proxy.fatturapa.orm.dao.IPccTracciaServiceSearch;
import org.govmix.proxy.fatturapa.web.commons.dao.DAOFactory;
import org.govmix.proxy.fatturapa.web.commons.exporter.PDFCreator.TipoXSL;
import org.govmix.proxy.fatturapa.web.commons.exporter.exception.ExportException;
import org.govmix.proxy.fatturapa.web.commons.utils.CommonsProperties;
import org.openspcoop2.generic_project.exception.MultipleResultException;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;

public class PccTracciaResponseSingleFileExporter extends AbstractSingleFileXMLExporter<PccTraccia, IdTraccia> {

	protected IPccTracciaServiceSearch pccTracciaSearchDAO;

	public PccTracciaResponseSingleFileExporter(Logger log) throws ServiceException,
			NotImplementedException, Exception {
		super(log);
		this.pccTracciaSearchDAO = DAOFactory.getInstance().getServiceManager().getPccTracciaServiceSearch();
	}

	public PccTracciaResponseSingleFileExporter(Logger log, Connection connection, boolean autocommit) throws ServiceException, NotImplementedException, Exception {
		super(log, connection, autocommit);
		this.pccTracciaSearchDAO = DAOFactory.getInstance().getServiceManager(connection, autocommit).getPccTracciaServiceSearch();
	}

	@Override
	protected String getNomeRisorsaXLST(PccTraccia object)
			throws Exception {
		return CommonsProperties.getInstance(this.log).getXslPccRiallineamento();
	}

	@Override
	protected TipoXSL getTipoXsl(PccTraccia object) {
		return TipoXSL.PCC_RIALLINEAMENTO;
	}

	@Override
	public PccTraccia convertToObject(IdTraccia id)
			throws ExportException {
		try {
			return this.pccTracciaSearchDAO.get(id);
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
	public byte[] getRawContent(PccTraccia object) {
		return object.getRispostaXml();
	}

	@Override
	public String getRawName(PccTraccia object) {
		return object.getIdPaTransazione() + "-riallineamento";
	}

	@Override
	protected List<IdFattura> findIdFattura(String[] ids, boolean isAll) throws ServiceException, NotFoundException {
		try {
			List<IdFattura> idFatturaRichiesti = new ArrayList<IdFattura>();
			PccTraccia traccia =  this.convertToObject(ids[0]);
			
			IdFattura id = this.getFatturaBD().convertToId(this.getFatturaBD().getById(traccia.getIdFattura()));
			idFatturaRichiesti.add(id);
			return idFatturaRichiesti;
		} catch (NumberFormatException e) {
			throw new ServiceException(e);
		} catch (ExportException e) {
			throw new ServiceException(e);
		} 
	}


	@Override
	public void export(PccTraccia object, OutputStream out,	FORMAT format) throws ExportException {
		switch(format) {
		case PDF: this.exportAsPdf(object, out);
		break;
		case RAW:this.exportAsRaw(object, out);
		break;
		case ZIP:this.exportAsZip(Arrays.asList(object), out);
		break;
		default: throw new ExportException("Formato ["+format+"] non supportato");
		}
	}

	@Override
	public PccTraccia convertToObject(String id)
			throws ExportException {
		IdTraccia idTraccia = new IdTraccia();
		idTraccia.setIdTraccia(Long.parseLong(id));
		return convertToObject(idTraccia);
	}

}
