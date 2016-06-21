/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2015 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2015 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
package org.govmix.proxy.fatturapa.web.commons.exporter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.AllegatoFattura;
import org.govmix.proxy.fatturapa.FatturaElettronica;
import org.govmix.proxy.fatturapa.IdFattura;
import org.govmix.proxy.fatturapa.IdLotto;
import org.govmix.proxy.fatturapa.LottoFatture;
import org.govmix.proxy.fatturapa.NotificaDecorrenzaTermini;
import org.govmix.proxy.fatturapa.NotificaEsitoCommittente;
import org.govmix.proxy.fatturapa.constants.FormatoTrasmissioneType;
import org.govmix.proxy.fatturapa.dao.IAllegatoFatturaServiceSearch;
import org.govmix.proxy.fatturapa.dao.IFatturaElettronicaServiceSearch;
import org.govmix.proxy.fatturapa.dao.ILottoFattureServiceSearch;
import org.govmix.proxy.fatturapa.dao.INotificaDecorrenzaTerminiServiceSearch;
import org.govmix.proxy.fatturapa.dao.INotificaEsitoCommittenteServiceSearch;
import org.govmix.proxy.fatturapa.dao.jdbc.JDBCAllegatoFatturaServiceSearch;
import org.govmix.proxy.fatturapa.dao.jdbc.JDBCFatturaElettronicaServiceSearch;
import org.govmix.proxy.fatturapa.dao.jdbc.JDBCNotificaEsitoCommittenteServiceSearch;
import org.govmix.proxy.fatturapa.web.commons.dao.DAOFactory;
import org.govmix.proxy.fatturapa.web.commons.exporter.exception.ExportException;
import org.govmix.proxy.fatturapa.web.commons.utils.CommonsProperties;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.expression.IPaginatedExpression;
import org.openspcoop2.generic_project.expression.SortOrder;
import org.openspcoop2.utils.resources.MimeTypes;

public class SingleFileExporter {

	public static final String BASE_DIR_NAME = "Fatture";
	public static final String FORMATO_ZIP_CON_ALLEGATI = "zipWA";
	public static final String FORMATO_ZIP = "zip";
	public static final String FORMATO_PDF = "pdf";
	public static final String FORMATO_XML = "xml";

	public static final int XSL_FATTURA_SDI10 = 0;
	public static final int XSL_FATTURA_SDI11 = 1;
	public static final int XSL_NOTIFICA_EC = 2;
	public static final int XSL_NOTIFICA_DT = 3;
	public static final int XSL_SCARTO_EC = 4;

	private Logger log = null;

	private IFatturaElettronicaServiceSearch fatturaSearchDAO= null;
	private IAllegatoFatturaServiceSearch allegatoSearchDAO=null;
	private INotificaDecorrenzaTerminiServiceSearch notificaDTSearchDAO= null;
	private INotificaEsitoCommittenteServiceSearch notificaECSearchDAO = null;
	private ILottoFattureServiceSearch lottoFattureSearchDAO = null;

	public SingleFileExporter(Logger log){
		this.log = log;
		try{
			this.fatturaSearchDAO = DAOFactory.getInstance(log).getServiceManager().getFatturaElettronicaServiceSearch();
			this.allegatoSearchDAO = DAOFactory.getInstance(log).getServiceManager().getAllegatoFatturaServiceSearch();
			this.notificaDTSearchDAO = DAOFactory.getInstance(log).getServiceManager().getNotificaDecorrenzaTerminiServiceSearch();
			this.notificaECSearchDAO = DAOFactory.getInstance(log).getServiceManager().getNotificaEsitoCommittenteServiceSearch();
			this.lottoFattureSearchDAO = DAOFactory.getInstance(log).getServiceManager().getLottoFattureServiceSearch();
		}catch(Exception e){
			this.log.error("Si e' verificato un errore durante la creazione del livello DAO: " + e.getMessage(), e);
		}
	}

	private InputStream getXsltFileStream(int tipoNotifica) throws Exception {

		InputStream xsltfile = null;
		String nomeFile = null;
		try{

			log.debug("Caricamento dello schema XSL in corso...");

			String baseDir = StringUtils.isEmpty(CommonsProperties.getInstance(this.log).getXslBaseDir()) ? "" :
				File.separatorChar + CommonsProperties.getInstance(this.log).getXslBaseDir();

			switch (tipoNotifica) {
			case XSL_FATTURA_SDI10:
				nomeFile = baseDir + File.separatorChar + CommonsProperties.getInstance(this.log).getXslFatturaSDI10(); 
				break;
			case XSL_FATTURA_SDI11:
				nomeFile = baseDir + File.separatorChar + CommonsProperties.getInstance(this.log).getXslFatturaSDI11(); 
				break;
			case XSL_NOTIFICA_DT:
				nomeFile = baseDir + File.separatorChar + CommonsProperties.getInstance(this.log).getXslNotificaDT();
				break;
			case XSL_NOTIFICA_EC:
				nomeFile = baseDir + File.separatorChar + CommonsProperties.getInstance(this.log).getXslNotificaEC();
				break;
			case  XSL_SCARTO_EC:
				nomeFile = baseDir + File.separatorChar + CommonsProperties.getInstance(this.log).getXslScartoEC();
				break;
			}

			log.debug("Verra' utilizzato il file ["+nomeFile+"]");

			xsltfile = SingleFileExporter.class.getResourceAsStream(nomeFile);

			if(xsltfile == null)
				throw new NullPointerException("Impossibile caricare il file ["+nomeFile+"]");
		}catch(Exception e){
			this.log.error("Si e' verificato un errore durante il caricamento dell'xsl ["+nomeFile+"]: " + e.getMessage(), e);
			throw e;
		}

		return xsltfile;
	}

	public void exportAsZip(OutputStream out) throws ExportException {

		try{
			ZipOutputStream zip = new ZipOutputStream(out);

			Date startTime = Calendar.getInstance().getTime();


			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

			int start = 0;
			int limit = 100;

			SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
			this.log.debug("Avvio esportazione ...");
			this.log.debug("Inizio esportazione alle:"+time.format(startTime));

			IPaginatedExpression pagExpr = this.fatturaSearchDAO.newPaginatedExpression();
			pagExpr.offset(start);
			pagExpr.limit(limit);
			List<FatturaElettronica> listFattura = this.fatturaSearchDAO.findAll(pagExpr);

			String rootDir = "" ; //BASE_DIR_NAME +File.separatorChar;

			// inserisco una unica entry
			//			if(this.formatoExport.equalsIgnoreCase("pdf"))
			//				this.zip.putNextEntry(new ZipEntry(rootDir+"Eventi.pdf"));
			//			else  if(this.formatoExport.equalsIgnoreCase("csv"))
			//				this.zip.putNextEntry(new ZipEntry(rootDir+"Eventi.csv"));

			try{
				//				this.zip.putNextEntry(new ZipEntry(rootDir+"SearchFilter.xml"));
				//				writeSearchFilterXml((EventiSearchForm)this.eventiService.getSearch(),this.zip);
				//				this.zip.flush();
				//				this.zip.closeEntry();

				while(listFattura.size()>0){

					export(rootDir,listFattura,zip, false);

					start+=limit;
					// Aggiorno la posizione dell'export
					pagExpr.offset(start);
					pagExpr.limit(limit);

					listFattura = this.fatturaSearchDAO.findAll(pagExpr);
				}

				// chiusura entry
				zip.flush();
				zip.closeEntry();

				//chiusura zip

				zip.flush();
				zip.close();
			}catch(IOException ioe){
				String msg = "Si e' verificato un errore durante l'esportazione degli eventi";
				msg+=" Non sono riuscito a creare il file SearchFilter.xml ("+ioe.getMessage()+")";
				this.log.error(msg,ioe);
				throw new ExportException(msg, ioe);
			}

			Date dataFine = Calendar.getInstance().getTime();

			this.log.debug("Fine esportazione alle:"+formatter.format(dataFine));
			this.log.debug("Esportazione completata.");

		}catch(ExportException e){
			this.log.error("Errore durante esportazione zip",e);
			throw e;
		}catch(Exception e){
			this.log.error("Errore durante esportazione zip",e);
			throw new ExportException("Errore durante esportazione zip", e);
		}

	}

	public void exportAsZipFromListaId(List<String> idFattura,OutputStream out)  throws ExportException{
		exportAsZipFromListaId(idFattura, out, false); 
	}

	public void exportAsZipFromListaId(List<String> idFattura,OutputStream out,boolean exportLottoXML)  throws ExportException{

		ZipOutputStream zip = new ZipOutputStream(out);
		Date startTime = Calendar.getInstance().getTime();
		try{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

			SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
			this.log.debug("Avvio esportazione ...");
			this.log.debug("Inizio esportazione alle:"+time.format(startTime));
			List<FatturaElettronica> listFattura = new ArrayList<FatturaElettronica>();

			for (String id: idFattura) {
				listFattura.add(((JDBCFatturaElettronicaServiceSearch)this.fatturaSearchDAO).get(Long.parseLong(id))); 
			}

			String rootDir = ""; // BASE_DIR_NAME +File.separatorChar;

			export(rootDir,listFattura,zip, exportLottoXML);

			// chiusura entry
			zip.flush();
			zip.closeEntry();

			Date dataFine = Calendar.getInstance().getTime();

			// chiusura zip

			zip.flush();
			zip.close();

			this.log.debug("Fine esportazione alle:"+formatter.format(dataFine));
			this.log.debug("Esportazione completata.");

		}catch(ExportException e){
			this.log.error("Errore durante esportazione zip",e);
			throw e;
		}catch(Exception e){
			this.log.error("Errore durante esportazione zip",e);
			throw new ExportException("Errore durante esportazione zip", e);
		}
	}

	public void exportAsZip(List<IdFattura> idFattura,OutputStream out)  throws ExportException{
		this.exportAsZip(idFattura, out, false);
	}
	public void exportAsZip(List<IdFattura> idFattura,OutputStream out, boolean exportLottoXML)  throws ExportException{

		Date startTime = Calendar.getInstance().getTime();
		ZipOutputStream zip = new ZipOutputStream(out);
		try{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

			SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
			this.log.debug("Avvio esportazione ...");
			this.log.debug("Inizio esportazione alle:"+time.format(startTime));
			List<FatturaElettronica> listFattura = new ArrayList<FatturaElettronica>();

			for (IdFattura id: idFattura) {
				listFattura.add(this.fatturaSearchDAO.get(id)); 
			}

			String rootDir = "";// BASE_DIR_NAME +File.separatorChar;

			export(rootDir,listFattura,zip,exportLottoXML);

			// chiusura entry
			zip.flush();
			zip.closeEntry();

			Date dataFine = Calendar.getInstance().getTime();

			// chiusura zip

			zip.flush();
			zip.close();

			this.log.debug("Fine esportazione alle:"+formatter.format(dataFine));
			this.log.debug("Esportazione completata.");

		}catch(ExportException e){
			this.log.error("Errore durante esportazione zip",e);
			throw e;
		}catch(Exception e){
			this.log.error("Errore durante esportazione zip",e);
			throw new ExportException("Errore durante esportazione zip", e);
		}

	}

	private void export(String rootDir, List<FatturaElettronica> fatture,ZipOutputStream zip, boolean exportLottoXML) throws ExportException{

		byte[] buf = new byte[1024];

		InputStream in = null;

		for (FatturaElettronica fattura : fatture) {

			try{
				ByteArrayOutputStream baos = new ByteArrayOutputStream();

				String nomeEntryFattura = getNomeEntryFattura(fattura);

				String fatturaDir = rootDir + nomeEntryFattura + File.separatorChar;

				// Creazione File XML
				zip.putNextEntry(new ZipEntry(fatturaDir + nomeEntryFattura + ".xml"));

				baos.write(fattura.getXml().getBytes());

				in = new ByteArrayInputStream(baos.toByteArray());
				int len;
				while ((len = in.read(buf)) > 0) {
					zip.write(buf, 0, len);
				}

				if(in!=null)
					in.close();

				zip.flush();
				zip.closeEntry();

				baos = new ByteArrayOutputStream();
				// Creazione File PDF
				zip.putNextEntry(new ZipEntry(fatturaDir + nomeEntryFattura + ".pdf"));


				InputStream xmlfile = new ByteArrayInputStream(fattura.getXml().getBytes());
				int xslFattura = fattura.getFormatoTrasmissione().equals(FormatoTrasmissioneType.SDI10) ? XSL_FATTURA_SDI10 : XSL_FATTURA_SDI11;
				InputStream xsltfile = getXsltFileStream(xslFattura);
				PDFCreator.getInstance(log).createPDF(xmlfile, xsltfile, baos);

				//				baos.write(fattura.getXml().getBytes());

				in = new ByteArrayInputStream(baos.toByteArray());
				while ((len = in.read(buf)) > 0) {
					zip.write(buf, 0, len);
				}

				if(in!=null)
					in.close();

				zip.flush();
				zip.closeEntry();

				// Creazione allegati
				IPaginatedExpression pagExpr = this.allegatoSearchDAO.newPaginatedExpression();
				pagExpr.sortOrder(SortOrder.ASC);
				pagExpr.addOrder(AllegatoFattura.model().NOME_ATTACHMENT);
				pagExpr.equals(AllegatoFattura.model().ID_FATTURA.IDENTIFICATIVO_SDI, fattura.getIdentificativoSdi());
				pagExpr.and();
				pagExpr.equals(AllegatoFattura.model().ID_FATTURA.POSIZIONE, fattura.getPosizione());

				List<AllegatoFattura> listaAllegati = this.allegatoSearchDAO.findAll(pagExpr);

				String allegatiDir = fatturaDir +   "allegati"+ File.separatorChar;
				if(listaAllegati != null && listaAllegati.size() > 0){
					exportAllegati(allegatiDir, listaAllegati,zip); 
				}


				// Notifica Esito Committente
				pagExpr = this.notificaECSearchDAO.newPaginatedExpression();
				pagExpr.sortOrder(SortOrder.ASC);
				pagExpr.addOrder(NotificaEsitoCommittente.model().DATA_INVIO_SDI);
				pagExpr.equals(NotificaEsitoCommittente.model().ID_FATTURA.IDENTIFICATIVO_SDI, fattura.getIdentificativoSdi());
				pagExpr.and();
				pagExpr.equals(NotificaEsitoCommittente.model().ID_FATTURA.POSIZIONE, fattura.getPosizione());

				List<NotificaEsitoCommittente> listaNotificheEC = this.notificaECSearchDAO.findAll(pagExpr);

				String notificheECDir = fatturaDir +   "notificaEsitoCommittente"+ File.separatorChar;
				if(listaNotificheEC != null && listaNotificheEC.size() > 0){
					exportNotificheEsitoCommittente(notificheECDir, nomeEntryFattura, listaNotificheEC,zip); 
				}

				// Notifica decorrenza termini se e' presente nella fattura
				if(fattura.getIdDecorrenzaTermini() != null){
					pagExpr = this.notificaDTSearchDAO.newPaginatedExpression();
					pagExpr.sortOrder(SortOrder.ASC);
					pagExpr.addOrder(NotificaDecorrenzaTermini.model().NOME_FILE);
					pagExpr.equals(NotificaDecorrenzaTermini.model().IDENTIFICATIVO_SDI, 
							fattura.getIdDecorrenzaTermini().getIdentificativoSdi());

					List<NotificaDecorrenzaTermini> listaNotificheDT = this.notificaDTSearchDAO.findAll(pagExpr);

					String notificheDTDir = fatturaDir  + "notificaDecorrenzaTermini"+ File.separatorChar;
					if(listaNotificheDT != null && listaNotificheDT.size() > 0){
						exportNotificheDecorrenzaTermini(notificheDTDir,  nomeEntryFattura, listaNotificheDT,zip); 
					}
				}

				if(exportLottoXML) {
					IdLotto id = new IdLotto();
					id.setIdentificativoSdi(fattura.getIdentificativoSdi());
					try{
					LottoFatture lotto = this.lottoFattureSearchDAO.get(id);
					exportLotto(fatturaDir,  getNomeEntryLottoFatture(lotto), lotto,zip);
					}catch(NotFoundException e){
						log.debug("Lotto Fatture id["+fattura.getIdentificativoSdi()+"], non trovato...");
					}
				}

			}catch(IOException ioe){
				String msg = "Si e' verificato un errore durante l'esportazione della fattura: Identificativo SDI["+fattura.getIdentificativoSdi() + "] Posizione["+fattura.getPosizione()+"]";
				throw new ExportException(msg, ioe);
			}catch(Exception e){
				String msg = "Si e' verificato un errore durante l'esportazione della fattura: Identificativo SDI["+fattura.getIdentificativoSdi() + "] Posizione["+fattura.getPosizione()+"]";
				throw new ExportException(msg, e);
			}

		}

	}

	

	public void exportAsZip(LottoFatture lotto,OutputStream out)throws Exception {
		Date startTime = Calendar.getInstance().getTime();
		ZipOutputStream zip = new ZipOutputStream(out);
		try{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

			SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
			this.log.debug("Avvio esportazione ...");
			this.log.debug("Inizio esportazione alle:"+time.format(startTime));
			String rootDir = "";// BASE_DIR_NAME +File.separatorChar;
			exportLotto(rootDir,getNomeEntryLottoFatture(lotto), lotto,zip);

			// chiusura entry
			zip.flush();
			zip.closeEntry();

			Date dataFine = Calendar.getInstance().getTime();

			// chiusura zip

			zip.flush();
			zip.close();

			this.log.debug("Fine esportazione alle:"+formatter.format(dataFine));
			this.log.debug("Esportazione completata.");

		}catch(ExportException e){
			this.log.error("Errore durante esportazione zip",e);
			throw e;
		}catch(Exception e){
			this.log.error("Errore durante esportazione zip",e);
			throw new ExportException("Errore durante esportazione zip", e);
		}

	}
	
	private void exportLotto(String rootDir, String nomeEntryFattura,
			LottoFatture lotto, ZipOutputStream zip) throws ExportException {

		byte[] buf = new byte[1024];

		InputStream in = null;
		try{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			// Creazione File XML
			zip.putNextEntry(new ZipEntry(rootDir+ nomeEntryFattura));

			baos.write(lotto.getXml());

			in = new ByteArrayInputStream(baos.toByteArray());
			int len;
			while ((len = in.read(buf)) > 0) {
				zip.write(buf, 0, len);
			}

			if(in!=null)
				in.close();

			zip.flush();
			zip.closeEntry();

		}catch(IOException ioe){
			String msg = "Si e' verificato un errore durante l'esportazione del Lotto Fatture con IdSdI ["+lotto.getIdentificativoSdi()+"]";
			throw new ExportException(msg, ioe);
		}catch(Exception e){
			String msg = "Si e' verificato un errore durante l'esportazione del Lotto Fatture con IdSdI ["+lotto.getIdentificativoSdi()+"]";
			throw new ExportException(msg, e);
		}
	}





	private String getNomeEntryFattura(FatturaElettronica fattura) {
		int endIndex =fattura.getNomeFile().lastIndexOf(".");

		String nomeFattura = endIndex > 0 ? fattura.getNomeFile().substring(0, endIndex): fattura.getNomeFile();
		String nomeEntryFattura = nomeFattura + "-" + fattura.getPosizione();
		return nomeEntryFattura;
	}


	private String getNomeEntryLottoFatture(LottoFatture lotto) {
		return "lotto-"+lotto.getNomeFile();
	}

	private String getNomeEntryFattura(IdFattura idFattura) throws Exception{
		FatturaElettronica fattura = this.fatturaSearchDAO.get(idFattura); 
		return getNomeEntryFattura(fattura);
	}

	private void exportAllegati(String rootDir,  List<AllegatoFattura> allegati,ZipOutputStream zip) throws ExportException{

		byte[] buf = new byte[1024];

		InputStream in = null;



		for (AllegatoFattura allegato : allegati) {

			try{
				ByteArrayOutputStream baos = new ByteArrayOutputStream();

				if(allegato.getAttachment() != null){

					String ext = MimeTypes.getInstance().getExtension(allegato.getFormatoAttachment());

					if(ext == null){
						String mimeType = MimeTypes.getInstance().getMimeType(allegato.getFormatoAttachment());

						if(mimeType!= null)
							ext = MimeTypes.getInstance().getExtension(mimeType);
					}

					// Creazione File XML
					zip.putNextEntry(new ZipEntry(rootDir+allegato.getNomeAttachment()+ "." + ext));

					baos.write(allegato.getAttachment());

					in = new ByteArrayInputStream(baos.toByteArray());
					int len;
					while ((len = in.read(buf)) > 0) {
						zip.write(buf, 0, len);
					}

					if(in!=null)
						in.close();

					zip.flush();
					zip.closeEntry();
				}
			}catch(IOException ioe){
				String msg = "Si e' verificato un errore durante l'esportazione dell'allegato ["+allegato.getNomeAttachment()
						+"] della Fattura: Identificativo SDI["+allegato.getIdFattura().getIdentificativoSdi() + "] Posizione["+allegato.getIdFattura().getPosizione()+"]";
				throw new ExportException(msg, ioe);
			}catch(Exception e){
				String msg = "Si e' verificato un errore durante l'esportazione dell'allegato ["+allegato.getNomeAttachment()
						+"] della Fattura: Identificativo SDI["+allegato.getIdFattura().getIdentificativoSdi() + "] Posizione["+allegato.getIdFattura().getPosizione()+"]";
				throw new ExportException(msg, e);
			}

		}

	}


	private void exportNotificheEsitoCommittente(String rootDir,String nomeEntryFattura, List<NotificaEsitoCommittente> listaNotificheEC,ZipOutputStream zip) throws ExportException{

		byte[] buf = new byte[1024];

		InputStream in = null;
		InputStream xmlfile =  null;
		InputStream xsltfile =  null;
		int len;
		int i =1 ;
		for (NotificaEsitoCommittente notifica : listaNotificheEC) {

			try{
				ByteArrayOutputStream baos = new ByteArrayOutputStream();

				if(notifica.getXml() != null){
					// Creazione File XML
					zip.putNextEntry(new ZipEntry(rootDir+ nomeEntryFattura + "-EC-" + i + ".xml"));

					baos.write(notifica.getXml().getBytes());

					in = new ByteArrayInputStream(baos.toByteArray());

					while ((len = in.read(buf)) > 0) {
						zip.write(buf, 0, len);
					}

					if(in!=null)
						in.close();

					zip.flush();
					zip.closeEntry();

					// Creazione File PDF
					baos = new ByteArrayOutputStream();
					zip.putNextEntry(new ZipEntry(rootDir+ nomeEntryFattura + "-EC-" + i + ".pdf"));

					xmlfile = new ByteArrayInputStream(notifica.getXml().getBytes());
					xsltfile = getXsltFileStream(XSL_NOTIFICA_EC);
					PDFCreator.getInstance(log).createPDF(xmlfile, xsltfile, baos);

					//				baos.write(notifica.getXml().getBytes());

					in = new ByteArrayInputStream(baos.toByteArray());

					while ((len = in.read(buf)) > 0) {
						zip.write(buf, 0, len);
					}

					if(in!=null)
						in.close();

					zip.flush();
					zip.closeEntry();

				}
				// Se e' presente lo scarto allora inserisco altre due entry

				if(notifica.getScarto() != null && notifica.getScartoXml() != null){
					// Creazione File XML
					zip.putNextEntry(new ZipEntry(rootDir+ nomeEntryFattura + "-SC-" + i + ".xml"));
					baos = new ByteArrayOutputStream();
					baos.write(notifica.getScartoXml().getBytes());

					in = new ByteArrayInputStream(baos.toByteArray());
					while ((len = in.read(buf)) > 0) {
						zip.write(buf, 0, len);
					}

					if(in!=null)
						in.close();

					zip.flush();
					zip.closeEntry();

					// Creazione File PDF
					baos = new ByteArrayOutputStream();
					zip.putNextEntry(new ZipEntry(rootDir+ nomeEntryFattura + "-SC-" + i + ".pdf"));

					xmlfile = new ByteArrayInputStream(notifica.getScartoXml().getBytes());
					xsltfile = getXsltFileStream(XSL_SCARTO_EC);
					PDFCreator.getInstance(log).createPDF(xmlfile, xsltfile, baos);

					//					baos.write(notifica.getScartoXml().getBytes());

					in = new ByteArrayInputStream(baos.toByteArray());

					while ((len = in.read(buf)) > 0) {
						zip.write(buf, 0, len);
					}

					if(in!=null)
						in.close();

					zip.flush();
					zip.closeEntry();
				}

			}catch(IOException ioe){
				String msg = "Si e' verificato un errore durante l'esportazione della NotificaEsitoCommittente ["+notifica.getId()
						+"] della Fattura: Identificativo SDI["+notifica.getIdFattura().getIdentificativoSdi() + "] Posizione["+notifica.getIdFattura().getPosizione()+"]";
				throw new ExportException(msg, ioe);
			}catch(Exception e){
				String msg = "Si e' verificato un errore durante l'esportazione della NotificaEsitoCommittente ["+notifica.getId()
						+"] della Fattura: Identificativo SDI["+notifica.getIdFattura().getIdentificativoSdi() + "] Posizione["+notifica.getIdFattura().getPosizione()+"]";
				throw new ExportException(msg, e);
			}
			i++;
		}
	}

	private void exportNotificheDecorrenzaTermini(String rootDir,String nomeEntryFattura, List<NotificaDecorrenzaTermini> listaNotificheDT,ZipOutputStream zip) throws ExportException{

		byte[] buf = new byte[1024];

		InputStream in = null;


		int i =1 ;
		for (NotificaDecorrenzaTermini notifica : listaNotificheDT) {

			try{
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				if(notifica.getXml() != null){
					// Creazione File XML
					zip.putNextEntry(new ZipEntry(rootDir+ nomeEntryFattura + "-DT-" + i + ".xml"));

					baos.write(notifica.getXml().getBytes());

					in = new ByteArrayInputStream(baos.toByteArray());
					int len;
					while ((len = in.read(buf)) > 0) {
						zip.write(buf, 0, len);
					}

					if(in!=null)
						in.close();

					zip.flush();
					zip.closeEntry();

					// Creazione File PDF
					baos = new ByteArrayOutputStream();
					zip.putNextEntry(new ZipEntry(rootDir+ nomeEntryFattura + "-DT-" + i + ".pdf"));

					InputStream xmlfile = new ByteArrayInputStream(notifica.getXml().getBytes());
					InputStream xsltfile = getXsltFileStream(XSL_NOTIFICA_DT);
					PDFCreator.getInstance(log).createPDF(xmlfile, xsltfile, baos);

					//				baos.write(notifica.getXml().getBytes());

					in = new ByteArrayInputStream(baos.toByteArray());

					while ((len = in.read(buf)) > 0) {
						zip.write(buf, 0, len);
					}

					if(in!=null)
						in.close();

					zip.flush();
					zip.closeEntry();
				}

			}catch(IOException ioe){
				String msg = "Si e' verificato un errore durante l'esportazione della NotificaDecorrenzaTermini ["+notifica.getId()
						+"] della Fattura: Identificativo SDI["+notifica.getIdentificativoSdi() + "]";
				throw new ExportException(msg, ioe);
			}catch(Exception e){
				String msg = "Si e' verificato un errore durante l'esportazione della NotificaDecorrenzaTermini ["+notifica.getId()
						+"] della Fattura: Identificativo SDI["+notifica.getIdentificativoSdi() + "]";
				throw new ExportException(msg, e);
			}
			i++;
		}
	}

	public String exportFatturaAsPdf(IdFattura idFattura,OutputStream out)  throws ExportException{
		return exportFattura(idFattura, out, FORMATO_PDF,false);
	}

	public String exportFatturaAsXml(IdFattura idFattura,OutputStream out)  throws ExportException{
		return exportFattura(idFattura, out, FORMATO_XML,true);
	}

	private String exportFattura(IdFattura idFattura,OutputStream out, String formato, boolean exportLottoXML)  throws ExportException{

		Date startTime = Calendar.getInstance().getTime();
		try{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

			SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
			this.log.debug("Avvio esportazione ...");
			this.log.debug("Inizio esportazione alle:"+time.format(startTime));

			FatturaElettronica fattura = this.fatturaSearchDAO.get(idFattura); 

			String nomeFile = exportFattura(formato,fattura,out,exportLottoXML);

			Date dataFine = Calendar.getInstance().getTime();

			this.log.debug("Fine esportazione alle:"+formatter.format(dataFine));
			this.log.debug("Esportazione completata.");

			return nomeFile;
		}catch(ExportException e){
			this.log.error("Errore durante esportazione fattura",e);
			throw e;
		}catch(Exception e){
			this.log.error("Errore durante esportazione fattura",e);
			throw new ExportException("Errore durante esportazione fattura", e);
		}

	}

	public String exportFatturaAsPdf(String idFattura,OutputStream out)  throws ExportException{
		return exportFattura(idFattura, out, FORMATO_PDF,false);
	}

	public String exportFatturaAsXml(String idFattura,OutputStream out)  throws ExportException{
		return exportFattura(idFattura, out, FORMATO_XML,true);
	}

	private String exportFattura(String idFattura,OutputStream out,String formato, boolean exportLottoXML)  throws ExportException{

		Date startTime = Calendar.getInstance().getTime();
		try{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

			SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
			this.log.debug("Avvio esportazione ...");
			this.log.debug("Inizio esportazione alle:"+time.format(startTime));

			FatturaElettronica fattura = ((JDBCFatturaElettronicaServiceSearch)this.fatturaSearchDAO).get(Long.parseLong(idFattura)); 

			String nomeFile = exportFattura(formato,fattura,out,exportLottoXML);

			Date dataFine = Calendar.getInstance().getTime();

			this.log.debug("Fine esportazione alle:"+formatter.format(dataFine));
			this.log.debug("Esportazione completata.");

			return nomeFile;
		}catch(ExportException e){
			this.log.error("Errore durante esportazione fattura",e);
			throw e;
		}catch(Exception e){
			this.log.error("Errore durante esportazione fattura",e);
			throw new ExportException("Errore durante esportazione fattura", e);
		}

	}

	private String exportFattura(String formato, FatturaElettronica fattura, OutputStream out, boolean exportLottoXML) throws ExportException{

		byte[] buf = new byte[1024];

		InputStream in = null;

		try{
			// Nome del file 
			String baseNomeFile = getNomeEntryFattura(fattura);
			String nomeFile = "";

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			// Creazione File XML
			if(formato.equals(FORMATO_XML)){
				if(!exportLottoXML){
					baos.write(fattura.getXml().getBytes());
					nomeFile = baseNomeFile + ".xml";
				} else {
					Integer identificativoSdi = fattura.getIdentificativoSdi();
					IdLotto idLotto = new IdLotto();
					idLotto.setIdentificativoSdi(identificativoSdi);
					try{
						LottoFatture lottoFatture = this.lottoFattureSearchDAO.get(idLotto );
						baos.write(lottoFatture.getXml());
						nomeFile = lottoFatture.getNomeFile();
					}catch(NotFoundException e){
						log.debug("Lotto Fatture id["+identificativoSdi+"], non trovato...");
						baos.write(fattura.getXml().getBytes());
						nomeFile = baseNomeFile + ".xml";
					}
				}
			}

			if(formato.equals(FORMATO_PDF)){
				InputStream xmlfile = new ByteArrayInputStream(fattura.getXml().getBytes());
				int xslFattura = fattura.getFormatoTrasmissione().equals(FormatoTrasmissioneType.SDI10) ? XSL_FATTURA_SDI10 : XSL_FATTURA_SDI11;
				InputStream xsltfile = getXsltFileStream(xslFattura);
				PDFCreator.getInstance(log).createPDF(xmlfile, xsltfile, baos);

				nomeFile = baseNomeFile + ".pdf";
			}

			in = new ByteArrayInputStream(baos.toByteArray());
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}

			if(in!=null)
				in.close();

			out.close();


			return nomeFile;
		}catch(IOException ioe){
			String msg = "Si e' verificato un errore durante l'esportazione della fattura: Identificativo SDI["+fattura.getIdentificativoSdi() + "] Posizione["+fattura.getPosizione()+"]";
			throw new ExportException(msg, ioe);
		}catch(Exception e){
			String msg = "Si e' verificato un errore durante l'esportazione della fattura: Identificativo SDI["+fattura.getIdentificativoSdi() + "] Posizione["+fattura.getPosizione()+"]";
			throw new ExportException(msg, e);
		}


	}

	public String exportNotificaECAsPdf(String idNotifica,OutputStream out)  throws ExportException{
		return exportNotificaEC(idNotifica, out, FORMATO_PDF);
	}

	public String exportNotificaECAsXml(String idNotifica,OutputStream out)  throws ExportException{
		return exportNotificaEC(idNotifica, out, FORMATO_XML);
	}

	private String exportNotificaEC(String idNotifica,OutputStream out,String formato)  throws ExportException{

		Date startTime = Calendar.getInstance().getTime();
		try{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

			SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
			this.log.debug("Avvio esportazione ...");
			this.log.debug("Inizio esportazione alle:"+time.format(startTime));

			NotificaEsitoCommittente notificaEC = ((JDBCNotificaEsitoCommittenteServiceSearch)this.notificaECSearchDAO).get(Long.parseLong(idNotifica)); 

			String nomeFile = exportNotificaEC(formato,notificaEC,out);

			Date dataFine = Calendar.getInstance().getTime();

			this.log.debug("Fine esportazione alle:"+formatter.format(dataFine));
			this.log.debug("Esportazione completata.");

			return nomeFile;
		}catch(ExportException e){
			this.log.error("Errore durante esportazione Notifica Esito Committente",e);
			throw e;
		}catch(Exception e){
			this.log.error("Errore durante esportazione Notifica Esito Committente",e);
			throw new ExportException("Errore durante esportazione Notifica Esito Committente", e);
		}

	}

	private String exportNotificaEC(String formato, NotificaEsitoCommittente notifica, OutputStream out) throws ExportException{

		byte[] buf = new byte[1024];

		InputStream in = null;

		try{
			// Nome del file 
			String baseNomeFile = getNomeEntryFattura(notifica.getIdFattura());
			String nomeFile = "";

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			// Creazione File XML
			if(formato.equals(FORMATO_XML)){
				baos.write(notifica.getXml().getBytes());
				nomeFile = baseNomeFile + "-EC.xml"; 
			}

			if(formato.equals(FORMATO_PDF)){
				InputStream xmlfile = new ByteArrayInputStream(notifica.getXml().getBytes());
				InputStream xsltfile = getXsltFileStream(XSL_NOTIFICA_EC);
				PDFCreator.getInstance(log).createPDF(xmlfile, xsltfile, baos);
				nomeFile = baseNomeFile + "-EC.pdf";
			}

			in = new ByteArrayInputStream(baos.toByteArray());
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}

			if(in!=null)
				in.close();

			out.close();

			return nomeFile;
		}catch(IOException ioe){
			String msg = "Si e' verificato un errore durante l'esportazione Notifica Esito Committente per la fattura: Identificativo SDI["+notifica.getIdFattura().getIdentificativoSdi() + "] Posizione["+notifica.getIdFattura().getPosizione()+"]";
			throw new ExportException(msg, ioe);
		}catch(Exception e){
			String msg = "Si e' verificato un errore durante l'esportazione Notifica Esito Committente per la fattura: Identificativo SDI["+notifica.getIdFattura().getIdentificativoSdi() + "] Posizione["+notifica.getIdFattura().getPosizione()+"]";
			throw new ExportException(msg, e);
		}
	}


	public String exportScartoNoteECAsPdf(String idNotifica,OutputStream out)  throws ExportException{
		return exportScartoNoteEC(idNotifica, out, FORMATO_PDF);
	}

	public String exportScartoNoteECAsXml(String idNotifica,OutputStream out)  throws ExportException{
		return exportScartoNoteEC(idNotifica, out, FORMATO_XML);
	}

	private String exportScartoNoteEC(String idNotifica,OutputStream out,String formato)  throws ExportException{

		Date startTime = Calendar.getInstance().getTime();
		try{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

			SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
			this.log.debug("Avvio esportazione ...");
			this.log.debug("Inizio esportazione alle:"+time.format(startTime));

			NotificaEsitoCommittente notificaEC = ((JDBCNotificaEsitoCommittenteServiceSearch)this.notificaECSearchDAO).get(Long.parseLong(idNotifica)); 

			String nomeFile = exportScartoNoteEC(formato,notificaEC,out);

			Date dataFine = Calendar.getInstance().getTime();

			this.log.debug("Fine esportazione alle:"+formatter.format(dataFine));
			this.log.debug("Esportazione completata.");

			return nomeFile;
		}catch(ExportException e){
			this.log.error("Errore durante esportazione Scarto Note Notifica Esito Committente",e);
			throw e;
		}catch(Exception e){
			this.log.error("Errore durante esportazione Scarto Note Notifica Esito Committente",e);
			throw new ExportException("Errore durante esportazione Scarto Note Notifica Esito Committente", e);
		}

	}

	private String exportScartoNoteEC(String formato, NotificaEsitoCommittente notifica, OutputStream out) throws ExportException{

		byte[] buf = new byte[1024];

		InputStream in = null;

		try{
			// Nome del file 
			String baseNomeFile = getNomeEntryFattura(notifica.getIdFattura());
			String nomeFile = "";

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			// Creazione File XML
			if(formato.equals(FORMATO_XML)){
				baos.write(notifica.getScartoXml().getBytes());
				nomeFile = baseNomeFile + "-SC.xml"; 
			}

			if(formato.equals(FORMATO_PDF)){
				InputStream xmlfile = new ByteArrayInputStream(notifica.getScartoXml().getBytes());
				InputStream xsltfile = getXsltFileStream(XSL_SCARTO_EC);
				PDFCreator.getInstance(log).createPDF(xmlfile, xsltfile, baos);
				nomeFile = baseNomeFile + "-SC.pdf";
			}

			in = new ByteArrayInputStream(baos.toByteArray());
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}

			if(in!=null)
				in.close();

			out.close();

			return nomeFile;
		}catch(IOException ioe){
			String msg = "Si e' verificato un errore durante l'esportazione Scarto Note Notifica Esito Committente per la fattura: Identificativo SDI["+notifica.getIdFattura().getIdentificativoSdi() + "] Posizione["+notifica.getIdFattura().getPosizione()+"]";
			throw new ExportException(msg, ioe);
		}catch(Exception e){
			String msg = "Si e' verificato un errore durante l'esportazione Scarto Note Notifica Esito Committente per la fattura: Identificativo SDI["+notifica.getIdFattura().getIdentificativoSdi() + "] Posizione["+notifica.getIdFattura().getPosizione()+"]";
			throw new ExportException(msg, e);
		}
	}

	public String exportNotificaDTAsPdf(String idFattura,OutputStream out)  throws ExportException{
		return exportNotificaDT(idFattura, out, FORMATO_PDF);
	}

	public String exportNotificaDTAsXml(String idFattura,OutputStream out)  throws ExportException{
		return exportNotificaDT(idFattura, out, FORMATO_XML);
	}

	private String exportNotificaDT(String idFattura,OutputStream out,String formato)  throws ExportException{

		Date startTime = Calendar.getInstance().getTime();
		try{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

			SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
			this.log.debug("Avvio esportazione ...");
			this.log.debug("Inizio esportazione alle:"+time.format(startTime));

			FatturaElettronica fattura = ((JDBCFatturaElettronicaServiceSearch) this.fatturaSearchDAO).get(Long.parseLong(idFattura)) ;

			NotificaDecorrenzaTermini notificaDT = this.notificaDTSearchDAO.get(fattura.getIdDecorrenzaTermini());

			String baseNomeFile = getNomeEntryFattura(fattura);

			String nomeFile = baseNomeFile + exportNotificaDT(formato,notificaDT,out);

			Date dataFine = Calendar.getInstance().getTime();

			this.log.debug("Fine esportazione alle:"+formatter.format(dataFine));
			this.log.debug("Esportazione completata.");

			return nomeFile;
		}catch(ExportException e){
			this.log.error("Errore durante esportazione Notifica Decorrenza Termini",e);
			throw e;
		}catch(Exception e){
			this.log.error("Errore durante esportazione Notifica Decorrenza Termini",e);
			throw new ExportException("Errore durante esportazione Notifica Decorrenza Termini", e);
		}

	}

	private String exportNotificaDT(String formato, NotificaDecorrenzaTermini notifica, OutputStream out) throws ExportException{

		byte[] buf = new byte[1024];

		InputStream in = null;

		try{

			// Nome del file 
			String nomeFile = "";

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			// Creazione File XML
			if(formato.equals(FORMATO_XML)){
				baos.write(notifica.getXml().getBytes());
				nomeFile = "-DT.xml"; 
			}

			if(formato.equals(FORMATO_PDF)){
				InputStream xmlfile = new ByteArrayInputStream(notifica.getXml().getBytes());
				InputStream xsltfile = getXsltFileStream(XSL_NOTIFICA_DT);
				PDFCreator.getInstance(log).createPDF(xmlfile, xsltfile, baos);
				nomeFile = "-DT.pdf";
			}

			in = new ByteArrayInputStream(baos.toByteArray());
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}

			if(in!=null)
				in.close();

			out.close();

			return nomeFile;
		}catch(IOException ioe){
			String msg = "Si e' verificato un errore durante l'esportazione Notifica Decorrenza Termini per la fattura: Identificativo SDI["+notifica.getIdentificativoSdi() + "]";
			throw new ExportException(msg, ioe);
		}catch(Exception e){
			String msg = "Si e' verificato un errore durante l'esportazione Notifica Decorrenza Termini per la fattura: Identificativo SDI["+notifica.getIdentificativoSdi() + "]";
			throw new ExportException(msg, e);
		}
	}


	public String exportAllegato(String idAllegato,OutputStream out)  throws ExportException{

		Date startTime = Calendar.getInstance().getTime();
		try{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

			SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
			this.log.debug("Avvio esportazione ...");
			this.log.debug("Inizio esportazione alle:"+time.format(startTime));

			AllegatoFattura allegato  = ((JDBCAllegatoFatturaServiceSearch)this.allegatoSearchDAO).get(Long.parseLong(idAllegato)); 

			String nomeFile = exportAllegato(allegato, out);

			Date dataFine = Calendar.getInstance().getTime();

			this.log.debug("Fine esportazione alle:"+formatter.format(dataFine));
			this.log.debug("Esportazione completata.");

			return nomeFile;
		}catch(ExportException e){
			this.log.error("Errore durante esportazione Allegato",e);
			throw e;
		}catch(Exception e){
			this.log.error("Errore durante esportazione Allegato",e);
			throw new ExportException("Errore durante esportazione Allegato", e);
		}

	}

	private String exportAllegato(AllegatoFattura allegato, OutputStream out) throws ExportException{

		byte[] buf = new byte[1024];

		InputStream in = null;

		try{
			// Nome del file 
			String baseNomeFile = allegato.getNomeAttachment();
			String nomeFile = "";

			String ext = MimeTypes.getInstance().getExtension(allegato.getFormatoAttachment());

			if(ext == null){
				String mimeType = MimeTypes.getInstance().getMimeType(allegato.getFormatoAttachment());

				if(mimeType!= null)
					ext = MimeTypes.getInstance().getExtension(mimeType);
			}

			// Creazione File XML
			nomeFile = baseNomeFile + "." + ext; 

			in = new ByteArrayInputStream(allegato.getAttachment());
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}

			if(in!=null)
				in.close();

			out.close();

			return nomeFile;
		}catch(IOException ioe){
			String msg = "Si e' verificato un errore durante l'esportazione Allegato per la fattura: Identificativo SDI["+allegato.getIdFattura().getIdentificativoSdi() + "] Posizione["+allegato.getIdFattura().getPosizione()+"]";
			throw new ExportException(msg, ioe);
		}catch(Exception e){
			String msg = "Si e' verificato un errore durante l'esportazione Allegato per la fattura: Identificativo SDI["+allegato.getIdFattura().getIdentificativoSdi() + "] Posizione["+allegato.getIdFattura().getPosizione()+"]";
			throw new ExportException(msg, e);
		}
	}
}
