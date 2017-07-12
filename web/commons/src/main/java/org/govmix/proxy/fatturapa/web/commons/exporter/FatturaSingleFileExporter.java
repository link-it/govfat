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
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.AllegatoFattura;
import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.orm.IdFattura;
import org.govmix.proxy.fatturapa.orm.IdLotto;
import org.govmix.proxy.fatturapa.orm.LottoFatture;
import org.govmix.proxy.fatturapa.orm.NotificaDecorrenzaTermini;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.JDBCFatturaElettronicaServiceSearch;
import org.govmix.proxy.fatturapa.web.commons.exporter.exception.ExportException;
import org.govmix.proxy.fatturapa.web.commons.utils.CommonsProperties;
import org.openspcoop2.generic_project.exception.MultipleResultException;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.IExpression;

public class FatturaSingleFileExporter extends AbstractSingleFileXMLExporter<FatturaElettronica, IdFattura> {

	private AllegatoSingleFileExporter allegatoSFE;
	private NotificaECSingleFileExporter notificaECSFE;
	private NotificaDTSingleFileExporter notificaDTSFE;
	private LottoSingleFileExporter lottoSFE;

	public FatturaSingleFileExporter(Logger log) throws ServiceException, NotImplementedException, Exception {
		super(log);
		this.allegatoSFE = new AllegatoSingleFileExporter(log);
		this.notificaECSFE = new NotificaECSingleFileExporter(log);
		this.notificaDTSFE = new NotificaDTSingleFileExporter(log);
		this.lottoSFE = new LottoSingleFileExporter(log);
	}

	public FatturaSingleFileExporter(Logger log, Connection connection, boolean autocommit) throws ServiceException, NotImplementedException, Exception {
		super(log, connection, autocommit);
		this.allegatoSFE = new AllegatoSingleFileExporter(log, connection, autocommit);
		this.notificaECSFE = new NotificaECSingleFileExporter(log, connection, autocommit);
		this.notificaDTSFE = new NotificaDTSingleFileExporter(log, connection, autocommit);
		this.lottoSFE = new LottoSingleFileExporter(log, connection, autocommit);
	}

	@Override
	protected String getNomeRisorsaXLST(FatturaElettronica object) throws Exception {
		switch(object.getFormatoTrasmissione()) {
		case FPA12: return CommonsProperties.getInstance(this.log).getXslFatturaV12();
		case FPR12: return CommonsProperties.getInstance(this.log).getXslFatturaV12();
		case SDI10: return CommonsProperties.getInstance(this.log).getXslFatturaSDI10();
		case SDI11: return CommonsProperties.getInstance(this.log).getXslFatturaSDI11();
		default: return CommonsProperties.getInstance(this.log).getXslFatturaV12();
		}
	}

	@Override
	public FatturaElettronica convertToObject(IdFattura id) throws ExportException {
		try {
			return this.getFatturaSearchDAO().get(id);
		} catch(NotFoundException e) {
			throw new ExportException(e);
		} catch (ServiceException e) {
			throw new ExportException(e);
		} catch (MultipleResultException e) {
			throw new ExportException(e);
		} catch (NotImplementedException e) {
			throw new ExportException(e);
		}
	}

	@Override
	public void exportAsZip(FatturaElettronica object, ZipOutputStream zip, String rootDir) throws ExportException {
		this._exportAsZip(object, zip, rootDir, true);
	}

	public void exportAsZipNoLotto(FatturaElettronica object, ZipOutputStream zip, String rootDir) throws ExportException {
		this._exportAsZip(object, zip, rootDir, false);
	}

	private void _exportAsZip(FatturaElettronica object, ZipOutputStream zip, String rootDir, boolean exportLotto) throws ExportException {
		try{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			String nomeEntryFattura = this.getRawName(object);

			String fatturaDir = rootDir + File.separatorChar + nomeEntryFattura + File.separatorChar;

			// Creazione File XML
			String nomeFatturaXML = fatturaDir + this.exportAsRaw(object, baos);
			addEntryToZip(zip, nomeFatturaXML, baos.toByteArray());

			baos = new ByteArrayOutputStream();
			// Creazione File PDF
			String nomeFatturaPDF = fatturaDir + this.exportAsPdf(object, baos);
			addEntryToZip(zip, nomeFatturaPDF, baos.toByteArray());


			
			String allegatiDir = fatturaDir +   "allegati"+ File.separatorChar;
			IdFattura idFattura = this.getFatturaSearchDAO().convertToId(object);
			List<AllegatoFattura> lstAllegati = this.allegatoSFE.getAllegatiPerFattura(idFattura);

			if(lstAllegati != null && !lstAllegati.isEmpty())
				this.allegatoSFE.exportAsZip(lstAllegati, zip, allegatiDir);

			// Notifica Esito Committente
			
			List<ExtendedNotificaEsitoCommittente> listaNotificheEC = this.notificaECSFE.getNotificheECPerFattura(idFattura);

			String notificheECDir = fatturaDir +   "notificaEsitoCommittente"+ File.separatorChar;
			if(listaNotificheEC != null && listaNotificheEC.size() > 0){
				this.notificaECSFE.exportAsZip(listaNotificheEC, zip, notificheECDir);
			}

			// Notifica decorrenza termini se e' presente nella fattura
			if(object.getIdDecorrenzaTermini() != null){
				List<NotificaDecorrenzaTermini> listaNotificheDT = this.notificaDTSFE.getNotificheDTPerFattura(idFattura);

				String notificheDTDir = fatturaDir  + "notificaDecorrenzaTermini"+ File.separatorChar;
				if(listaNotificheDT != null && listaNotificheDT.size() > 0){
					this.notificaDTSFE.exportAsZip(listaNotificheDT, zip, notificheDTDir); 
				}
			}

			if(exportLotto) {
				try{
					IdLotto id = new IdLotto();
					id.setIdentificativoSdi(object.getIdentificativoSdi());
					LottoFatture lotto = this.lottoSFE.convertToObject(id);
					this.lottoSFE.exportAsZip(lotto, zip, fatturaDir);
				}catch(ExportException e){
					log.debug("Lotto Fatture id["+object.getIdentificativoSdi()+"], non trovato..."); //Per alcuni db che all'inizio non supportavano il lotto fatture
				}
			}

		}catch(NotFoundException e){
			this.log.error("Errore durante esportazione fattura: ",e);
			throw new ExportException("Risorsa richiesta non trovata.");
		}catch(IOException ioe){
			String msg = "Si e' verificato un errore durante l'esportazione della fattura: Identificativo SDI["+object.getIdentificativoSdi() + "] Posizione["+object.getPosizione()+"]";
			this.log.error("Errore durante esportazione fattura: "+ioe.getMessage(),ioe);
			throw new ExportException(msg, ioe);
		}catch(Exception e){
			String msg = "Si e' verificato un errore durante l'esportazione della fattura: Identificativo SDI["+object.getIdentificativoSdi() + "] Posizione["+object.getPosizione()+"]";
			this.log.error("Errore durante esportazione fattura: "+e.getMessage(),e);
			throw new ExportException(msg, e);
		}

	}

	@Override
	protected List<IdFattura> findIdFattura(String[] ids, boolean isAll, IExpression fattExpr) throws ServiceException, NotFoundException {
		try {
			List<IdFattura> idFatturaRichiesti = new ArrayList<IdFattura>();
			for (String idFattura : ids) {
				FatturaElettronica fattura = ((JDBCFatturaElettronicaServiceSearch)this.getFatturaSearchDAO()).get(Long.parseLong(idFattura));
				IdFattura idFattura2 = this.getFatturaSearchDAO().convertToId(fattura);
				idFatturaRichiesti.add(idFattura2);
			}
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
	public byte[] getRawContent(FatturaElettronica object) {
		return object.getXml();
	}

	@Override
	public String getRawName(FatturaElettronica object) {
		int endIndex =object.getNomeFile().lastIndexOf(".");

		String nomeFattura = endIndex > 0 ? object.getNomeFile().substring(0, endIndex): object.getNomeFile();
		return nomeFattura + "-" + object.getIdentificativoSdi() +"-" + object.getPosizione();
	}

	@Override
	public void export(FatturaElettronica object, OutputStream out,	FORMAT format) throws ExportException {
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


	public void exportListById(List<IdFattura> lstId, ByteArrayOutputStream out, FORMAT format) throws ExportException {
		List<FatturaElettronica> lst = new ArrayList<FatturaElettronica>();
		for(IdFattura id: lstId) {
			lst.add(this.convertToObject(id));
		}
		this.exportList(lst, out, format);
	}

	public void exportList(List<FatturaElettronica> lstFatture, ByteArrayOutputStream out, FORMAT format) throws ExportException {
		switch(format) {
		case ZIP:this.exportAsZip(lstFatture, out);
		break;
		case PDF:
		case RAW:
		default: throw new ExportException("Formato ["+format+"] non supportato per export lista");
		}
		
	}

	@Override
	public FatturaElettronica convertToObject(String id) throws ExportException {
		try {
			return ((JDBCFatturaElettronicaServiceSearch)this.getFatturaSearchDAO()).get(Long.parseLong(id));
		} catch(NotFoundException e) {
			throw new ExportException(e);
		} catch (ServiceException e) {
			throw new ExportException(e);
		} catch (MultipleResultException e) {
			throw new ExportException(e);
		} catch (NotImplementedException e) {
			throw new ExportException(e);
		}
	}

}
