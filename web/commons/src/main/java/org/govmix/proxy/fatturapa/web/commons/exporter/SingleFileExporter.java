/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2016 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2016 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.AllegatoFattura;
import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.orm.IdFattura;
import org.govmix.proxy.fatturapa.orm.IdLotto;
import org.govmix.proxy.fatturapa.orm.IdTraccia;
import org.govmix.proxy.fatturapa.orm.LottoFatture;
import org.govmix.proxy.fatturapa.orm.NotificaDecorrenzaTermini;
import org.govmix.proxy.fatturapa.orm.NotificaEsitoCommittente;
import org.govmix.proxy.fatturapa.orm.PccTraccia;
import org.govmix.proxy.fatturapa.orm.Utente;
import org.govmix.proxy.fatturapa.orm.constants.FormatoTrasmissioneType;
import org.govmix.proxy.fatturapa.orm.constants.UserRole;
import org.govmix.proxy.fatturapa.orm.dao.IAllegatoFatturaServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.IFatturaElettronicaServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.ILottoFattureServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.INotificaDecorrenzaTerminiServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.INotificaEsitoCommittenteServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.IPccTracciaServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.IUtenteServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.JDBCAllegatoFatturaServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.JDBCFatturaElettronicaServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.JDBCNotificaEsitoCommittenteServiceSearch;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.JDBCPccTracciaServiceSearch;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.FatturaElettronicaBD;
import org.govmix.proxy.fatturapa.web.commons.dao.DAOFactory;
import org.govmix.proxy.fatturapa.web.commons.exporter.exception.ExportException;
import org.govmix.proxy.fatturapa.web.commons.utils.CommonsProperties;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.expression.IExpression;
import org.openspcoop2.generic_project.expression.IPaginatedExpression;
import org.openspcoop2.generic_project.expression.SortOrder;
import org.openspcoop2.utils.UtilsException;
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
	public static final int XSL_PCC_RIALLINEAMENTO = 5;

	public static final String PARAMETRO_ACTION_FATTURA = "f";
	public static final String PARAMETRO_ACTION_ALLEGATO = "a";
	public static final String PARAMETRO_ACTION_NOTIFICA_EC = "ec";
	public static final String PARAMETRO_ACTION_NOTIFICA_DT = "dt";
	public static final String PARAMETRO_ACTION_SCARTO = "sc";
	public static final String PARAMETRO_ACTION_PCC_RIALLINEAMENTO = "pccDf";

	private Logger log = null;

	private IFatturaElettronicaServiceSearch fatturaSearchDAO= null;
	private IPccTracciaServiceSearch tracciaSearchDAO= null;
	private IAllegatoFatturaServiceSearch allegatoSearchDAO=null;
	private INotificaDecorrenzaTerminiServiceSearch notificaDTSearchDAO= null;
	private INotificaEsitoCommittenteServiceSearch notificaECSearchDAO = null;
	private ILottoFattureServiceSearch lottoFattureSearchDAO = null;
	private IUtenteServiceSearch utenteSearchDAO = null;
	private FatturaElettronicaBD fatturaBD = null;

	public SingleFileExporter(Logger log){
		this.log = log;
		try{
			this.fatturaSearchDAO = DAOFactory.getInstance().getServiceManager().getFatturaElettronicaServiceSearch();
			this.tracciaSearchDAO = DAOFactory.getInstance().getServiceManager().getPccTracciaServiceSearch();
			this.allegatoSearchDAO = DAOFactory.getInstance().getServiceManager().getAllegatoFatturaServiceSearch();
			this.notificaDTSearchDAO = DAOFactory.getInstance().getServiceManager().getNotificaDecorrenzaTerminiServiceSearch();
			this.notificaECSearchDAO = DAOFactory.getInstance().getServiceManager().getNotificaEsitoCommittenteServiceSearch();
			this.lottoFattureSearchDAO = DAOFactory.getInstance().getServiceManager().getLottoFattureServiceSearch();
			this.utenteSearchDAO = DAOFactory.getInstance().getServiceManager().getUtenteServiceSearch();
			this.fatturaBD = new FatturaElettronicaBD(log);
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
			case  XSL_PCC_RIALLINEAMENTO:
				nomeFile = baseDir + File.separatorChar + CommonsProperties.getInstance(this.log).getXslPccRiallineamento();
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

	public void exportAsZip(IExpression fattExpr , OutputStream out) throws ExportException {

		try{
			ZipOutputStream zip = new ZipOutputStream(out);

			Date startTime = Calendar.getInstance().getTime();


			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

			int start = 0;
			int limit = 100;

			SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
			this.log.debug("Avvio esportazione ...");
			this.log.debug("Inizio esportazione alle:"+time.format(startTime));

			IPaginatedExpression pagExpr = null;

			if(fattExpr != null){
				pagExpr = this.getFatturaSearchDAO().toPaginatedExpression(fattExpr);
				pagExpr.sortOrder(SortOrder.DESC);
				pagExpr.addOrder(FatturaElettronica.model().DATA_RICEZIONE);
			}
			else 
				pagExpr = this.getFatturaSearchDAO().newPaginatedExpression();

			pagExpr.offset(start);
			pagExpr.limit(limit);
			List<FatturaElettronica> listFattura = this.getFatturaSearchDAO().findAll(pagExpr);

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

					listFattura = this.getFatturaSearchDAO().findAll(pagExpr);
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
				listFattura.add(((JDBCFatturaElettronicaServiceSearch)this.getFatturaSearchDAO()).get(Long.parseLong(id))); 
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

		}catch(NotFoundException e){
			this.log.debug("Errore durante esportazione fattura: ",e);
			throw new ExportException("Risorsa richiesta non trovata.");
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
				listFattura.add(this.getFatturaSearchDAO().get(id)); 
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

		}catch(NotFoundException e){
			this.log.debug("Errore durante esportazione fattura: ",e);
			throw new ExportException("Risorsa richiesta non trovata.");
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

				baos.write(fattura.getXml());

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


				InputStream xmlfile = new ByteArrayInputStream(fattura.getXml());
				int xslFattura = fattura.getFormatoTrasmissione().equals(FormatoTrasmissioneType.SDI10) ? XSL_FATTURA_SDI10 : XSL_FATTURA_SDI11;
				InputStream xsltfile = getXsltFileStream(xslFattura);
				PDFCreator.getInstance(log).createPDF(xmlfile, xsltfile, baos);

				//				baos.write(fattura.getXml());

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

			}catch(NotFoundException e){
				this.log.debug("Errore durante esportazione fattura: ",e);
				throw new ExportException("Risorsa richiesta non trovata.");
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
		FatturaElettronica fattura = this.getFatturaSearchDAO().get(idFattura); 
		return getNomeEntryFattura(fattura);
	}

	private void exportAllegati(String rootDir,  List<AllegatoFattura> allegati,ZipOutputStream zip) throws ExportException{

		byte[] buf = new byte[1024];

		InputStream in = null;


		Map<String, Integer> allegatiName = new HashMap<String, Integer>();

		for (AllegatoFattura allegato : allegati) {

			try{
				ByteArrayOutputStream baos = new ByteArrayOutputStream();

				if(allegato.getAttachment() != null){
					
					String ext = getExtensionAttachment(allegato);
					String nomeCompleto = allegato.getNomeAttachment()+ "." + ext;
					if(!allegatiName.containsKey(nomeCompleto)) {
						allegatiName.put(nomeCompleto, 1);
					} else {
						Integer index = allegatiName.remove(nomeCompleto);
						allegatiName.put(nomeCompleto, index + 1);
						nomeCompleto += "." + index;
					}
						
					// Creazione File XML
					zip.putNextEntry(new ZipEntry(rootDir+nomeCompleto));

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

	private String getExtensionAttachment(AllegatoFattura allegato)
			throws UtilsException {
		String formatoAttachment = allegato.getFormatoAttachment();
		
		if(formatoAttachment == null)
			formatoAttachment = "bin";//"application/octet-stream";
		
		String ext = MimeTypes.getInstance().getExtension(formatoAttachment);

		if(ext == null){
			String mimeType = MimeTypes.getInstance().getMimeType(formatoAttachment);

			if(mimeType!= null)
				ext = MimeTypes.getInstance().getExtension(mimeType);
		}
		return ext;
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

					baos.write(notifica.getXml());

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

					xmlfile = new ByteArrayInputStream(notifica.getXml());
					xsltfile = getXsltFileStream(XSL_NOTIFICA_EC);
					PDFCreator.getInstance(log).createPDF(xmlfile, xsltfile, baos);

					//				baos.write(notifica.getXml());

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
					baos.write(notifica.getScartoXml());

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

					xmlfile = new ByteArrayInputStream(notifica.getScartoXml());
					xsltfile = getXsltFileStream(XSL_SCARTO_EC);
					PDFCreator.getInstance(log).createPDF(xmlfile, xsltfile, baos);

					//					baos.write(notifica.getScartoXml());

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

					baos.write(notifica.getXml());

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

					InputStream xmlfile = new ByteArrayInputStream(notifica.getXml());
					InputStream xsltfile = getXsltFileStream(XSL_NOTIFICA_DT);
					PDFCreator.getInstance(log).createPDF(xmlfile, xsltfile, baos);

					//				baos.write(notifica.getXml());

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

			FatturaElettronica fattura = this.getFatturaSearchDAO().get(idFattura); 

			String nomeFile = exportFattura(formato,fattura,out,exportLottoXML);

			Date dataFine = Calendar.getInstance().getTime();

			this.log.debug("Fine esportazione alle:"+formatter.format(dataFine));
			this.log.debug("Esportazione completata.");

			return nomeFile;
		}catch(NotFoundException e){
			this.log.debug("Errore durante esportazione fattura: ",e);
			throw new ExportException("Risorsa richiesta non trovata.");
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

			FatturaElettronica fattura = ((JDBCFatturaElettronicaServiceSearch)this.getFatturaSearchDAO()).get(Long.parseLong(idFattura)); 

			String nomeFile = exportFattura(formato,fattura,out,exportLottoXML);

			Date dataFine = Calendar.getInstance().getTime();

			this.log.debug("Fine esportazione alle:"+formatter.format(dataFine));
			this.log.debug("Esportazione completata.");

			return nomeFile;
		}catch(NotFoundException e){
			this.log.debug("Errore durante esportazione fattura: ",e);
			throw new ExportException("Risorsa richiesta non trovata.");
		}
		catch(ExportException e){
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
					baos.write(fattura.getXml());
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
						baos.write(fattura.getXml());
						nomeFile = baseNomeFile + ".xml";
					}
				}
			}

			if(formato.equals(FORMATO_PDF)){
				InputStream xmlfile = new ByteArrayInputStream(fattura.getXml());
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
		}catch(NotFoundException e){
			this.log.debug("Errore durante esportazione fattura: ",e);
			throw new ExportException("Risorsa richiesta non trovata.");
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
		}catch(NotFoundException e){
			this.log.debug("Errore durante esportazione fattura: ",e);
			throw new ExportException("Risorsa richiesta non trovata.");
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
				baos.write(notifica.getXml());
				nomeFile = baseNomeFile + "-EC.xml"; 
			}

			if(formato.equals(FORMATO_PDF)){
				InputStream xmlfile = new ByteArrayInputStream(notifica.getXml());
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
		}catch(NotFoundException e){
			this.log.debug("Errore durante esportazione fattura: ",e);
			throw new ExportException("Risorsa richiesta non trovata.");
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
				baos.write(notifica.getScartoXml());
				nomeFile = baseNomeFile + "-SC.xml"; 
			}

			if(formato.equals(FORMATO_PDF)){
				InputStream xmlfile = new ByteArrayInputStream(notifica.getScartoXml());
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

			FatturaElettronica fattura = ((JDBCFatturaElettronicaServiceSearch) this.getFatturaSearchDAO()).get(Long.parseLong(idFattura)) ;

			NotificaDecorrenzaTermini notificaDT = this.notificaDTSearchDAO.get(fattura.getIdDecorrenzaTermini());

			String baseNomeFile = getNomeEntryFattura(fattura);

			String nomeFile = baseNomeFile + exportNotificaDT(formato,notificaDT,out);

			Date dataFine = Calendar.getInstance().getTime();

			this.log.debug("Fine esportazione alle:"+formatter.format(dataFine));
			this.log.debug("Esportazione completata.");

			return nomeFile;
		}catch(NotFoundException e){
			this.log.debug("Errore durante esportazione fattura: ",e);
			throw new ExportException("Risorsa richiesta non trovata.");
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
				baos.write(notifica.getXml());
				nomeFile = "-DT.xml"; 
			}

			if(formato.equals(FORMATO_PDF)){
				InputStream xmlfile = new ByteArrayInputStream(notifica.getXml());
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
		}catch(NotFoundException e){
			this.log.debug("Errore durante esportazione fattura: ",e);
			throw new ExportException("Risorsa richiesta non trovata.");
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

			String ext = getExtensionAttachment(allegato);

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

	public boolean checkautorizzazioneExport(String username, String []ids, String tipoExport, boolean isAll,IExpression fattExpr) throws ExportException {

		try {
			log.debug("Controllo autorizzazione per l'utente ["+username+"] in corso...");
			IExpression utenteExpr = this.utenteSearchDAO.newExpression();
			utenteExpr.equals(Utente.model().USERNAME, username);
			Utente utente = null;
			try{
				utente = this.utenteSearchDAO.find(utenteExpr);
				log.debug("Controllo autorizzazione per l'utente ["+username+"] completato.");
			}catch(NotFoundException e){
				log.debug("Controllo autorizzazione per l'utente ["+username+"] fallito, utente non registrato.");
				throw new ExportException("Controllo autorizzazione per l'utente ["+username+"] fallito, utente non registrato.");
			}

			UserRole role = utente.getRole();
			log.debug("Controllo autorizzazione per l'utente ["+username+"] Ruolo Trovato["+role.toString()+"].");
			if(role.equals(UserRole.ADMIN)){
				log.debug("Controllo autorizzazione per l'utente ["+username+"] completato, l'utente con ruolo ADMIN e' autorizzato.");
				return true;
			}

			List<IdFattura> idFatturaRichiesti = null;
			try{
				if(!isAll){

					// ottenere l'id fattura dal id dell'oggetto che si vuole scaricare
					idFatturaRichiesti = new ArrayList<IdFattura>();
					// Fattura o gruppi di fatture sia in zip che in pdf/xml
					if(tipoExport.equals(PARAMETRO_ACTION_FATTURA)){
						for (String idFattura : ids) {
							FatturaElettronica fattura = ((JDBCFatturaElettronicaServiceSearch)this.getFatturaSearchDAO()).get(Long.parseLong(idFattura));
							IdFattura idFattura2 = this.fatturaSearchDAO.convertToId(fattura);
							idFatturaRichiesti.add(idFattura2);
						}

					}else if(tipoExport.equals(PARAMETRO_ACTION_ALLEGATO)){
						AllegatoFattura allegato  = ((JDBCAllegatoFatturaServiceSearch)this.allegatoSearchDAO).get(Long.parseLong(ids[0])); 
						idFatturaRichiesti.add(allegato.getIdFattura());
					}else if(tipoExport.equals(PARAMETRO_ACTION_NOTIFICA_DT)){
						FatturaElettronica fattura = ((JDBCFatturaElettronicaServiceSearch)this.getFatturaSearchDAO()).get(Long.parseLong(ids[0]));
						IdFattura idFattura2 = this.fatturaSearchDAO.convertToId(fattura);
						idFatturaRichiesti.add(idFattura2);
					}else if(tipoExport.equals(PARAMETRO_ACTION_NOTIFICA_EC)){
						NotificaEsitoCommittente notificaEC = ((JDBCNotificaEsitoCommittenteServiceSearch)this.notificaECSearchDAO).get(Long.parseLong(ids[0])); 
						idFatturaRichiesti.add(notificaEC.getIdFattura());
					}else if(tipoExport.equals(PARAMETRO_ACTION_SCARTO)){
						NotificaEsitoCommittente notificaEC = ((JDBCNotificaEsitoCommittenteServiceSearch)this.notificaECSearchDAO).get(Long.parseLong(ids[0])); 
						idFatturaRichiesti.add(notificaEC.getIdFattura());
					}else if(tipoExport.equals(PARAMETRO_ACTION_PCC_RIALLINEAMENTO)){
						PccTraccia tracciaPcc = ((JDBCPccTracciaServiceSearch)this.tracciaSearchDAO).get(Long.parseLong(ids[0])); 
						FatturaElettronica fatturaElettronica = ((JDBCFatturaElettronicaServiceSearch)this.fatturaSearchDAO).get(tracciaPcc.getIdFattura());
						IdFattura idFattura = new IdFattura();
						idFattura.setIdentificativoSdi(fatturaElettronica.getIdentificativoSdi());
						idFattura.setPosizione(fatturaElettronica.getPosizione());
						idFatturaRichiesti.add(idFattura);
					}else{
						// tipo non riconosciuto
						return false;
					}


				}
				else{
					IPaginatedExpression pagExpr = null;

					if(fattExpr != null){
						pagExpr = this.getFatturaSearchDAO().toPaginatedExpression(fattExpr);
						pagExpr.sortOrder(SortOrder.DESC);
						pagExpr.addOrder(FatturaElettronica.model().DATA_RICEZIONE);
					}
					else 
						pagExpr = this.getFatturaSearchDAO().newPaginatedExpression();

					idFatturaRichiesti = this.fatturaSearchDAO.findAllIds(pagExpr);


				}
			}catch(NotFoundException e){
				log.debug("Impossibile trovare la risorsa richiesta.");
				throw new ExportException("Impossibile trovare la risorsa richiesta.");
			}
			// passo alla bd l'id utente che mi restituisce una lista di idFattura 
			List<IdFattura> idFattureByUtente = this.fatturaBD.getIdFattureByUtente(utente);


			for (IdFattura idFattura : idFatturaRichiesti) {
				boolean found = false;
				for (IdFattura idFatturaAutorizzata : idFattureByUtente) {
					if(idFattura.getIdentificativoSdi().intValue() == idFatturaAutorizzata.getIdentificativoSdi().intValue() && 
							idFattura.getPosizione().intValue() == idFatturaAutorizzata.getPosizione().intValue()){
						found = true;
						break;
					}
				}
				if(!found)
					return false;
			}

			return true;


		}catch(Exception e){
			log.error("Si e' verificato un errore durante la verifica delle autorizzazione export per l'utente ["+username+"]: "+ e.getMessage() , e);
			throw new ExportException("Si e' verificato un errore durante la verifica delle autorizzazione export per l'utente ["+username+"]");
		}

	}
	
	public String exportPccRiallineamentoAsPdf(String idTracciaPCC,OutputStream out)  throws ExportException{

		InputStream in = null;
		try{
			IdTraccia idTraccia = new IdTraccia();
			idTraccia.setIdTraccia(Long.parseLong(idTracciaPCC));
			PccTraccia pccTraccia = this.tracciaSearchDAO.get(idTraccia);
			
			byte[] rispostaXml = pccTraccia.getRispostaXml();
			
			in = new ByteArrayInputStream(rispostaXml);
					
			PDFCreator.getInstance(log).createPDF(in, this.getXsltFileStream(XSL_PCC_RIALLINEAMENTO)
					, out);

			return "riallineamento.pdf";
		}catch(IOException ioe){
			String msg = "Si e' verificato un errore durante l'esportazione della richiesta di riallineamento per la traccia ["+idTracciaPCC+"]";
			throw new ExportException(msg, ioe);
		}catch(Exception e){
			String msg = "Si e' verificato un errore durante l'esportazione della richiesta di riallineamento per la traccia ["+idTracciaPCC+"]";
			throw new ExportException(msg, e);
		}

	}

	public IFatturaElettronicaServiceSearch getFatturaSearchDAO() {
		return fatturaSearchDAO;
	}

	public void setFatturaSearchDAO(IFatturaElettronicaServiceSearch fatturaSearchDAO) {
		this.fatturaSearchDAO = fatturaSearchDAO;
	}
}
