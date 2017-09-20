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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.IdFattura;
import org.govmix.proxy.fatturapa.orm.NotificaEsitoCommittente;
import org.govmix.proxy.fatturapa.orm.dao.INotificaEsitoCommittenteServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.JDBCNotificaEsitoCommittenteServiceSearch;
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

public class NotificaECSingleFileExporter extends AbstractSingleFileXMLExporter<ExtendedNotificaEsitoCommittente, Long> {

	protected INotificaEsitoCommittenteServiceSearch notificaECSearchDAO;
	private ScartoECSingleFileExporter scartoSFE;


	public NotificaECSingleFileExporter(Logger log) throws ServiceException,
			NotImplementedException, Exception {
		super(log);
		this.notificaECSearchDAO = DAOFactory.getInstance().getServiceManager().getNotificaEsitoCommittenteServiceSearch();
		this.scartoSFE = new ScartoECSingleFileExporter(log);
	}

	public NotificaECSingleFileExporter(Logger log, Connection connection, boolean autocommit) throws ServiceException, NotImplementedException, Exception {
		super(log, connection, autocommit);
		this.notificaECSearchDAO = DAOFactory.getInstance().getServiceManager(connection, autocommit).getNotificaEsitoCommittenteServiceSearch();
		this.scartoSFE = new ScartoECSingleFileExporter(log, connection, autocommit);
	}

	@Override
	protected String getNomeRisorsaXLST(ExtendedNotificaEsitoCommittente object)
			throws Exception {
		return CommonsProperties.getInstance(this.log).getXslNotificaEC();
	}

	@Override
	protected TipoXSL getTipoXsl(ExtendedNotificaEsitoCommittente object) {
		return TipoXSL.NOTIFICA_EC;
	}

	@Override
	public ExtendedNotificaEsitoCommittente convertToObject(Long id)
			throws ExportException {
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
	public byte[] getRawContent(ExtendedNotificaEsitoCommittente object) {
		return object.getXml();
	}

	@Override
	public String getRawName(ExtendedNotificaEsitoCommittente object) {
		
		int endIndex =object.getNomeFile().lastIndexOf(".");
		String nomeNotifica = endIndex > 0 ? object.getNomeFile().substring(0, endIndex): object.getNomeFile();
		String index = (object.getIndex() != null) ? "-"+object.getIndex(): "";
		return nomeNotifica + index +  "-" + object.getIdentificativoSdi() +"-" + object.getPosizione();
	}

	public List<ExtendedNotificaEsitoCommittente> getNotificheECPerFattura(IdFattura id) throws ServiceException {
		try {
			// Notifica Esito Committente
			IPaginatedExpression pagExpr = this.notificaECSearchDAO.newPaginatedExpression();
			pagExpr.sortOrder(SortOrder.ASC);
			pagExpr.addOrder(NotificaEsitoCommittente.model().DATA_INVIO_SDI);
			pagExpr.equals(NotificaEsitoCommittente.model().ID_FATTURA.IDENTIFICATIVO_SDI, id.getIdentificativoSdi());
			pagExpr.and();
			pagExpr.equals(NotificaEsitoCommittente.model().ID_FATTURA.POSIZIONE, id.getPosizione());

			List<NotificaEsitoCommittente> necLst = this.notificaECSearchDAO.findAll(pagExpr);
			
			List<ExtendedNotificaEsitoCommittente> extNecLst = new ArrayList<ExtendedNotificaEsitoCommittente>();
			
			for(int i = 0; i < necLst.size(); i++) {
				Integer index = null;
				if(i > 0) {index = i+1;}
				extNecLst.add(ExtendedNotificaEsitoCommittente.newExtendedNotificaEsitoCommittente(necLst.get(i), index));
			}
			
			return extNecLst;
			

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


	@Override
	public void export(ExtendedNotificaEsitoCommittente object, OutputStream out,	FORMAT format) throws ExportException {
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
	public ExtendedNotificaEsitoCommittente convertToObject(String id)
			throws ExportException {
		return convertToObject(Long.valueOf(id));
	}

	
	@Override
	public void exportAsZip(ExtendedNotificaEsitoCommittente object, ZipOutputStream zip, String rootDir) throws ExportException {
		try{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			// Creazione File XML
			String nomeFatturaXML = rootDir + this.exportAsRaw(object, baos);
			addEntryToZip(zip, nomeFatturaXML, baos.toByteArray());

			baos = new ByteArrayOutputStream();
			// Creazione File PDF
			String nomeFatturaPDF = rootDir + this.exportAsPdf(object, baos);
			addEntryToZip(zip, nomeFatturaPDF, baos.toByteArray());

			if(object.getScartoXml() != null) {
				baos = new ByteArrayOutputStream();
				String nomeScartoXML = rootDir + this.scartoSFE.exportAsRaw(object, baos);
				addEntryToZip(zip, nomeScartoXML, baos.toByteArray());
				
				baos = new ByteArrayOutputStream();
				String nomeScartoPDF = rootDir + this.scartoSFE.exportAsPdf(object, baos);
				addEntryToZip(zip, nomeScartoPDF, baos.toByteArray());
			}

		}catch(NotFoundException e){
			this.log.debug("Errore durante esportazione NotificaEsitoCommittente: ",e);
			throw new ExportException("Risorsa richiesta non trovata.");
		}catch(IOException ioe){
			String msg = "Si e' verificato un errore durante l'esportazione della NotificaEsitoCommittente: Identificativo SDI["+object.getIdentificativoSdi() + "] Posizione["+object.getPosizione()+"]";
			throw new ExportException(msg, ioe);
		}catch(Exception e){
			String msg = "Si e' verificato un errore durante l'esportazione della NotificaEsitoCommittente: Identificativo SDI["+object.getIdentificativoSdi() + "] Posizione["+object.getPosizione()+"]";
			throw new ExportException(msg, e);
		}

	}

}
