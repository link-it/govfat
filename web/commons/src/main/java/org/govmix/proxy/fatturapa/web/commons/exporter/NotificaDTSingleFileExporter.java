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
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.orm.IdFattura;
import org.govmix.proxy.fatturapa.orm.NotificaDecorrenzaTermini;
import org.govmix.proxy.fatturapa.orm.dao.INotificaDecorrenzaTerminiServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.JDBCNotificaDecorrenzaTerminiServiceSearch;
import org.govmix.proxy.fatturapa.web.commons.dao.DAOFactory;
import org.govmix.proxy.fatturapa.web.commons.exporter.PDFCreator.TipoXSL;
import org.govmix.proxy.fatturapa.web.commons.exporter.exception.ExportException;
import org.govmix.proxy.fatturapa.web.commons.utils.CommonsProperties;
import org.openspcoop2.generic_project.exception.ExpressionException;
import org.openspcoop2.generic_project.exception.ExpressionNotImplementedException;
import org.openspcoop2.generic_project.exception.MultipleResultException;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.IPaginatedExpression;
import org.openspcoop2.generic_project.expression.SortOrder;

public class NotificaDTSingleFileExporter extends AbstractSingleFileXMLExporter<NotificaDecorrenzaTermini, Long> {

	private INotificaDecorrenzaTerminiServiceSearch notificaDTSearchDAO;

	public NotificaDTSingleFileExporter(Logger log, Connection connection, boolean autocommit) throws ServiceException,
	NotImplementedException, Exception {
		super(log, connection, autocommit);
		this.notificaDTSearchDAO = DAOFactory.getInstance().getServiceManager(connection, autocommit).getNotificaDecorrenzaTerminiServiceSearch();
	}

	public NotificaDTSingleFileExporter(Logger log) throws ServiceException,
	NotImplementedException, Exception {
		super(log);
		this.notificaDTSearchDAO = DAOFactory.getInstance().getServiceManager().getNotificaDecorrenzaTerminiServiceSearch();
	}

	@Override
	protected String getNomeRisorsaXLST(NotificaDecorrenzaTermini object)
			throws Exception {
		return CommonsProperties.getInstance(this.log).getXslNotificaDT();
	}
	

	@Override
	protected TipoXSL getTipoXsl(NotificaDecorrenzaTermini object) {
		return TipoXSL.NOTIFICA_DT;
	}


	@Override
	public NotificaDecorrenzaTermini convertToObject(Long id) throws ExportException {
		try {
			FatturaElettronica fattura = this.getFatturaBD().getById(id);

			return this.notificaDTSearchDAO.get(fattura.getIdDecorrenzaTermini());
		} catch (ServiceException e) {
			throw new ExportException(e);
		} catch (MultipleResultException e) {
			throw new ExportException(e);
		} catch (NotImplementedException e) {
			throw new ExportException(e);
		} catch (NotFoundException e) {
			throw new ExportException(e);
		}
	}

	@Override
	public byte[] getRawContent(NotificaDecorrenzaTermini object) {
		return object.getXml();
	}

	@Override
	public String getRawName(NotificaDecorrenzaTermini object) {
		int endIndex =object.getNomeFile().lastIndexOf(".");

		String nomeFattura = endIndex > 0 ? object.getNomeFile().substring(0, endIndex): object.getNomeFile();
		return nomeFattura + "-" + object.getIdentificativoSdi();

	}

	public List<NotificaDecorrenzaTermini> getNotificheDTPerFattura(IdFattura id) throws ServiceException {
		try {
			// Notifica Esito Committente
			IPaginatedExpression pagExpr = this.notificaDTSearchDAO.newPaginatedExpression();
			pagExpr = this.notificaDTSearchDAO.newPaginatedExpression();
			pagExpr.sortOrder(SortOrder.ASC);
			pagExpr.addOrder(NotificaDecorrenzaTermini.model().NOME_FILE);
			pagExpr.equals(NotificaDecorrenzaTermini.model().IDENTIFICATIVO_SDI, 
					id.getIdentificativoSdi());

			return this.notificaDTSearchDAO.findAll(pagExpr);

		} catch (ExpressionNotImplementedException e) {
			throw new ServiceException(e);
		} catch (ExpressionException e) {
			throw new ServiceException(e);
		} catch (NotImplementedException e) {
			throw new ServiceException(e);
		}
	}
	

	@Override
	protected List<IdFattura> findIdFattura(String[] ids, boolean isAll) throws ServiceException, NotFoundException {
		try {
			NotificaDecorrenzaTermini decorrenza = ((JDBCNotificaDecorrenzaTerminiServiceSearch)this.notificaDTSearchDAO).get(Long.parseLong(ids[0]));
			return this.getFatturaBD().findAllIdFatturaByIdentificativoSdi(decorrenza.getIdentificativoSdi());
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void export(NotificaDecorrenzaTermini object, OutputStream out,	FORMAT format) throws ExportException {
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
	public NotificaDecorrenzaTermini convertToObject(String id)
			throws ExportException {
		return convertToObject(Long.valueOf(id));
	}

}
