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

import org.slf4j.Logger;
import org.govmix.proxy.fatturapa.orm.IdFattura;
import org.govmix.proxy.fatturapa.orm.NotificaEsitoCommittente;
import org.govmix.proxy.fatturapa.orm.dao.INotificaEsitoCommittenteServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.JDBCNotificaEsitoCommittenteServiceSearch;
import org.govmix.proxy.fatturapa.web.commons.dao.DAOFactory;
import org.govmix.proxy.fatturapa.web.commons.exporter.PDFCreator.TipoXSL;
import org.govmix.proxy.fatturapa.web.commons.exporter.exception.ExportException;
import org.govmix.proxy.fatturapa.web.commons.utils.CommonsProperties;
import org.openspcoop2.generic_project.exception.MultipleResultException;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;

public class ScartoECSingleFileExporter extends AbstractSingleFileXMLExporter<ExtendedNotificaEsitoCommittente, Long> {

	protected INotificaEsitoCommittenteServiceSearch notificaECSearchDAO;

	public ScartoECSingleFileExporter(Logger log) throws ServiceException,
			NotImplementedException, Exception {
		super(log);
		this.notificaECSearchDAO = DAOFactory.getInstance().getServiceManager().getNotificaEsitoCommittenteServiceSearch();
	}

	public ScartoECSingleFileExporter(Logger log, Connection connection, boolean autocommit) throws ServiceException, NotImplementedException, Exception {
		super(log, connection, autocommit);
		this.notificaECSearchDAO = DAOFactory.getInstance().getServiceManager(connection, autocommit).getNotificaEsitoCommittenteServiceSearch();
	}
	
	@Override
	protected String getNomeRisorsaXLST(ExtendedNotificaEsitoCommittente object)
			throws Exception {
		return CommonsProperties.getInstance(this.log).getXslScartoEC();
	}

	@Override
	protected TipoXSL getTipoXsl(ExtendedNotificaEsitoCommittente object) {
		return TipoXSL.SCARTO_EC;
	}

	@Override
	public byte[] getRawContent(ExtendedNotificaEsitoCommittente object) {
		return object.getScartoXml();
	}

	@Override
	public String getRawName(ExtendedNotificaEsitoCommittente object) {
		int endIndex =object.getNomeFile().lastIndexOf(".");

		String index = (object.getIndex() != null) ? "-"+object.getIndex(): "";
		String nomeScarto = endIndex > 0 ? object.getNomeFile().substring(0, endIndex): object.getNomeFile();
		return nomeScarto + index + "-" + object.getIdentificativoSdi() +"-" + object.getPosizione() + "-SC";
	}

	@Override
	public void export(ExtendedNotificaEsitoCommittente object, OutputStream out, org.govmix.proxy.fatturapa.web.commons.exporter.AbstractSingleFileExporter.FORMAT format) throws ExportException {
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
	public ExtendedNotificaEsitoCommittente convertToObject(Long id) throws ExportException {
		try {
			NotificaEsitoCommittente nec = ((JDBCNotificaEsitoCommittenteServiceSearch)this.notificaECSearchDAO).get(id);
			return ExtendedNotificaEsitoCommittente.newExtendedNotificaEsitoCommittente(nec, null);
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
	public ExtendedNotificaEsitoCommittente convertToObject(String id) throws ExportException {
		return convertToObject(Long.valueOf(id));
	}

	@Override
	protected List<IdFattura> findIdFattura(String[] ids, boolean isAll) throws ServiceException, NotFoundException {
		try {
			List<IdFattura> idFatturaRichiesti = new ArrayList<IdFattura>();
			NotificaEsitoCommittente notifica = ((JDBCNotificaEsitoCommittenteServiceSearch)this.notificaECSearchDAO).get(Long.parseLong(ids[0]));
			idFatturaRichiesti.add(notifica.getIdFattura());
			return idFatturaRichiesti;
		} catch (NumberFormatException e) {
			throw new ServiceException(e);
		} catch (MultipleResultException e) {
			throw new ServiceException(e);
		} catch (NotImplementedException e) {
			throw new ServiceException(e);
		} 
	}

}
