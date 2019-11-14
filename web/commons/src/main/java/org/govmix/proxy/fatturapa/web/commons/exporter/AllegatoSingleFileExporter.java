/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2019 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2019 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.AllegatoFattura;
import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
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
		Set<String> allegatiNameSet = new HashSet<String>();

		for (AllegatoFattura object: list) {
			String ext = this.getRawExtension(object);
			String nome = this.getRawName(object);
			String nomeCompleto = nome+ "." + ext;
			allegatiNameSet.add(nomeCompleto);
		}

		for (AllegatoFattura object: list) {
			try {
					String ext = this.getRawExtension(object);
					String nome = this.getRawName(object);
					
					String nomeCompleto = nome+ "." + ext;
					int indexFinale = 0;
					while(allegatiName.containsKey(nomeCompleto)) {
						Integer index = allegatiName.get(nomeCompleto);
						indexFinale = index + 1;
						nomeCompleto = nome + "-" + indexFinale + "." + ext;
						while(allegatiNameSet.contains(nomeCompleto)) {
							indexFinale++;
							nomeCompleto= nome + "-" + indexFinale + "." + ext;
						}
					}

					allegatiName.put(nomeCompleto, indexFinale);

					if(indexFinale > 0)
						object.setNomeAttachment(nomeCompleto);
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
			pagExpr.and();
			pagExpr.equals(AllegatoFattura.model().ID_FATTURA.FATTURAZIONE_ATTIVA, id.getFatturazioneAttiva());

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
	protected List<String> findCodiciDipartimento(String[] ids, boolean fatturazioneAttiva) throws ServiceException, NotFoundException{
		try {
			AllegatoFattura allegato = ((JDBCAllegatoFatturaServiceSearch)this.allegatoSearchDAO).get(Long.parseLong(ids[0]));
			List<String> codDipartimentoRichiesti = new ArrayList<String>();
			FatturaElettronica fattura = this.getFatturaBD().get(allegato.getIdFattura());
			codDipartimentoRichiesti.add(fattura.getCodiceDestinatario());

			return codDipartimentoRichiesti;
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
	public String exportAsRaw(AllegatoFattura object, OutputStream out) throws ExportException {
		try {
			out.write(this.getRawContent(object));
		} catch (IOException e) {
			throw new ExportException(e);
		}
		
		if(this.isNameCoherent(object)) {
			return object.getNomeAttachment();
		} else {
			return object.getNomeAttachment() + "." +  this.getRawExtension(object);
		}
	}

	@Override
	public String getRawName(AllegatoFattura object) {
		if(this.isNameCoherent(object)) {
			int endIndex = object.getNomeAttachment().lastIndexOf(".");
			return endIndex > 0 ? object.getNomeAttachment().substring(0, endIndex): object.getNomeAttachment();
		} else {
			return object.getNomeAttachment();
		}
	}
	
	private boolean isNameCoherent(AllegatoFattura object) {
		if(object.getFormatoAttachment() == null) return true;
		
		int endIndex = object.getNomeAttachment().lastIndexOf(".");
		if(endIndex < 0)
			return false;
		
		String formatoAttachment = object.getFormatoAttachment().replaceAll("\\.", "").toLowerCase();
		
		String nomeAttachment = object.getNomeAttachment(); 
		if(object.getNomeAttachment().toLowerCase().endsWith(".p7m") && !formatoAttachment.equalsIgnoreCase("p7m")) {
			nomeAttachment = nomeAttachment.substring(0, object.getNomeAttachment().toLowerCase().lastIndexOf(".p7m")).toLowerCase();
		}
		if(nomeAttachment.endsWith(formatoAttachment))
			return true;
		
		return false;
	}

	@Override
	public String getRawExtension(AllegatoFattura object) {
		if(this.isNameCoherent(object)) {
			int endIndex = object.getNomeAttachment().lastIndexOf(".");
			return object.getNomeAttachment().substring(endIndex + 1);
		} else {
			String formatoAttachment = object.getFormatoAttachment();
			
			if(formatoAttachment == null)
				return "bin";
			
			formatoAttachment = formatoAttachment.replaceAll("\\.", "");
			
			try {
				String ext = MimeTypes.getInstance().getExtension(formatoAttachment);
				if(ext == null){
					String mimeType = MimeTypes.getInstance().getMimeType(formatoAttachment);
	
					if(mimeType!= null)
						ext = MimeTypes.getInstance().getExtension(mimeType);
				}
				if(ext != null)
					return ext;
				else
					return "bin";
	
			} catch (UtilsException e) {
				return "bin";
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
