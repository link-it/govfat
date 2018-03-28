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
package org.govmix.proxy.fatturapa.web.commons.exporter;

import java.io.OutputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.AllegatoFattura;
import org.govmix.proxy.fatturapa.orm.IdFattura;
import org.govmix.proxy.fatturapa.orm.dao.IAllegatoFatturaServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.JDBCAllegatoFatturaServiceSearch;
import org.govmix.proxy.fatturapa.web.commons.dao.DAOFactory;
import org.govmix.proxy.fatturapa.web.commons.exporter.exception.ExportException;
import org.openspcoop2.generic_project.exception.ExpressionException;
import org.openspcoop2.generic_project.exception.ExpressionNotImplementedException;
import org.openspcoop2.generic_project.exception.MultipleResultException;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.IPaginatedExpression;
import org.openspcoop2.generic_project.expression.SortOrder;
import org.openspcoop2.utils.UtilsException;
import org.openspcoop2.utils.resources.MimeTypes;

public class AllegatoSingleFileExporter extends	AbstractSingleFileExporter<AllegatoFattura, Long> {

	private IAllegatoFatturaServiceSearch allegatoSearchDAO;

	public AllegatoSingleFileExporter(Logger log) throws ServiceException, NotImplementedException, Exception {
		super(log);
		this.allegatoSearchDAO = DAOFactory.getInstance().getServiceManager().getAllegatoFatturaServiceSearch();
	}

	public AllegatoSingleFileExporter(Logger log, Connection connection, boolean autocommit) throws ServiceException, NotImplementedException, Exception {
		super(log, connection, autocommit);
		this.allegatoSearchDAO = DAOFactory.getInstance().getServiceManager(connection, autocommit).getAllegatoFatturaServiceSearch();
	}

	@Override
	public AllegatoFattura convertToObject(Long id) throws ExportException {
		try {
			return ((JDBCAllegatoFatturaServiceSearch)this.allegatoSearchDAO).get(id);
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
	public void exportAsZip(List<AllegatoFattura> list, ZipOutputStream zip,
			String rootDir) throws ExportException {
		Map<String, Integer> allegatiName = new HashMap<String, Integer>();
		for (AllegatoFattura object: list) {
			try {
				String ext = this.getRawExtension(object);
				String nome = this.getRawName(object);
				String nomeCompleto = nome+ "." + ext;
				Integer indexFinale = null;
				if(!allegatiName.containsKey(nomeCompleto)) {
					allegatiName.put(nomeCompleto, 0);
				} else {
					Integer index = allegatiName.remove(nomeCompleto);
					allegatiName.put(nomeCompleto, index + 1);
					indexFinale = index + 1;
				}

				if(indexFinale != null)
					object.setNomeAttachment(nome + "-" + indexFinale);
				this.exportAsZip(object, zip, rootDir);
			} catch(Exception e) {
				throw new ExportException(e);
			}
		}
	}

	public List<AllegatoFattura> getAllegatiPerFattura(IdFattura id) throws ExportException {
		try {
			// Creazione allegati
			IPaginatedExpression pagExpr = this.allegatoSearchDAO.newPaginatedExpression();
			pagExpr.sortOrder(SortOrder.ASC);
			pagExpr.addOrder(AllegatoFattura.model().NOME_ATTACHMENT);
			pagExpr.equals(AllegatoFattura.model().ID_FATTURA.IDENTIFICATIVO_SDI, id.getIdentificativoSdi());
			pagExpr.and();
			pagExpr.equals(AllegatoFattura.model().ID_FATTURA.POSIZIONE, id.getPosizione());

			return this.allegatoSearchDAO.findAll(pagExpr);
		} catch(ExpressionException e) {
			throw new ExportException(e);
		} catch (ExpressionNotImplementedException e) {
			throw new ExportException(e);
		} catch (ServiceException e) {
			throw new ExportException(e);
		} catch (NotImplementedException e) {
			throw new ExportException(e);
		}
	}


	@Override
	protected List<IdFattura> findIdFattura(String[] ids, boolean isAll) throws ServiceException, NotFoundException {
		try {
			List<IdFattura> idFatturaRichiesti = new ArrayList<IdFattura>();
			AllegatoFattura allegato = ((JDBCAllegatoFatturaServiceSearch)this.allegatoSearchDAO).get(Long.parseLong(ids[0]));
			idFatturaRichiesti.add(allegato.getIdFattura());
			return idFatturaRichiesti;
		} catch (NumberFormatException e) {
			throw new ServiceException(e);
		} catch (MultipleResultException e) {
			throw new ServiceException(e);
		} catch (NotImplementedException e) {
			throw new ServiceException(e);
		} 
	}

	@Override
	public byte[] getRawContent(AllegatoFattura object) {
		return object.getAttachment();
	}

	@Override
	public String getRawName(AllegatoFattura object) {
		int endIndex = object.getNomeAttachment().lastIndexOf(".");

		return endIndex > 0 ? object.getNomeAttachment().substring(0, endIndex): object.getNomeAttachment();
	}

	@Override
	public String getRawExtension(AllegatoFattura object) {
		
		int endIndex = object.getNomeAttachment().lastIndexOf(".");

		if(endIndex > 0) {
			return object.getNomeAttachment().substring(endIndex + 1);
		} else {
			String formatoAttachment = object.getFormatoAttachment();
	
			if(formatoAttachment == null)
				return null;
			
			formatoAttachment = formatoAttachment.replaceAll("\\.", "");
			
			try {
				String ext = MimeTypes.getInstance().getExtension(formatoAttachment);
				if(ext == null){
					String mimeType = MimeTypes.getInstance().getMimeType(formatoAttachment);
	
					if(mimeType!= null)
						ext = MimeTypes.getInstance().getExtension(mimeType);
				}
				return ext;
	
			} catch (UtilsException e) {
				return null;
			}
		}
	}

	@Override
	public void export(AllegatoFattura object, OutputStream out, FORMAT format)
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
	public AllegatoFattura convertToObject(String id) throws ExportException {
		return convertToObject(Long.valueOf(id));
	}

}
