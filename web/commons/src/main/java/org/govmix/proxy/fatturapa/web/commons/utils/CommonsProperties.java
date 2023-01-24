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
package org.govmix.proxy.fatturapa.web.commons.utils;

import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.govmix.proxy.fatturapa.orm.constants.TipoComunicazioneType;
import org.govmix.proxy.fatturapa.orm.utils.TipoDocumento;
import org.govmix.proxy.fatturapa.web.commons.exporter.PDFCreator.TipoXSL;
import org.govmix.proxy.pcc.fatture.utils.AbstractProperties;


/***
 * 
 * Classe di properties per il progetto commons
 * 
 * 
 * @author pintori
 *
 */
public class CommonsProperties extends AbstractProperties {

	/** XSL **/

	private String discriminatorHeaderNameSPCoop;
	private String discriminatorHeaderValueSPCoop;
	private String idEgovHeaderFatturazioneAttiva;
	private String idEgovHeaderSPCoop;
	private String idEgovHeaderSDICoop;
	
	private String xslFatturaSDI10;
	private String xslFatturaSDI11;
	private String xslFatturaV12;
	private String xslNotificaEC;
	private String xslNotificaDT;
	private String xslScartoEC;
	private String xslPccRiallineamento;
	private String xslBaseDir;

	private Properties xslTraccia;
	private URL invioFatturaURL;
	private String invioFatturaUsername;
	private String invioFatturaPassword;

	private int maxTentativiRispedizione;
	private int fattoreRispedizione;
	private int maxTentativiRispedizioneWFM;
	private int fattoreRispedizioneWFM;
	private int maxTentativiRispedizioneSdI;
	private int fattoreRispedizioneSdI;

	private Map<String, TipoDocumento> tipidocumento;

	public int getMaxTentativiRispedizione() {
		return maxTentativiRispedizione;
	}

	public void setMaxTentativiRispedizione(int maxTentativiRispedizione) {
		this.maxTentativiRispedizione = maxTentativiRispedizione;
	}

	public int getMaxTentativiRispedizioneWFM() {
		return maxTentativiRispedizioneWFM;
	}

	public void setMaxTentativiRispedizioneWFM(int maxTentativiRispedizioneWFM) {
		this.maxTentativiRispedizioneWFM = maxTentativiRispedizioneWFM;
	}

	public int getMaxTentativiRispedizioneSdI() {
		return maxTentativiRispedizioneSdI;
	}

	public void setMaxTentativiRispedizioneSdI(int maxTentativiRispedizioneSdI) {
		this.maxTentativiRispedizioneSdI = maxTentativiRispedizioneSdI;
	}

	private static final String propertiesPath = "/webCommons.properties";
	/** Copia Statica */
	private static CommonsProperties commonsProperties = null;

	public static synchronized void initialize(org.apache.log4j.Logger log) throws Exception{

		if(CommonsProperties.commonsProperties==null)
			CommonsProperties.commonsProperties = new CommonsProperties(log, propertiesPath);	
	}

	public static CommonsProperties getInstance(org.apache.log4j.Logger log) throws Exception{

		if(CommonsProperties.commonsProperties==null)
			initialize(log);

		return CommonsProperties.commonsProperties;
	}

	/* ********  F I E L D S  P R I V A T I  ******** */

	/** Reader delle proprieta' impostate nel file 'server.properties' */
	private Properties reader;


	/* ********  C O S T R U T T O R E  ******** */

	/**
	 * Viene chiamato in causa per istanziare il properties reader
	 *
	 * 
	 */
	public CommonsProperties(org.apache.log4j.Logger log, String path) throws Exception{

		super(path);
		/* ---- Lettura del cammino del file di configurazione ---- */

		Properties propertiesReader = new Properties();
		java.io.InputStream properties = null;
		try{  
			properties = CommonsProperties.class.getResourceAsStream(path);
			if(properties==null){
				throw new Exception("Properties "+path+" not found");
			}
			propertiesReader.load(properties);
			properties.close();
		}catch(java.io.IOException e) {
			log.error("Riscontrato errore durante la lettura del file '"+path+"': "+e.getMessage(),e);
			try{
				if(properties!=null)
					properties.close();
			}catch(Exception er){}
			throw e;
		}	

		this.reader = propertiesReader;
		this.discriminatorHeaderNameSPCoop = this.getProperty("discriminator.spcoop.headerName", true);
		this.discriminatorHeaderValueSPCoop = this.getProperty("discriminator.spcoop.headerValue", true);
		this.idEgovHeaderFatturazioneAttiva = this.getProperty("header.fatturazioneAttiva.idegov", true);
		this.idEgovHeaderSPCoop = this.getProperty("header.spcoop.idegov", true);
		this.idEgovHeaderSDICoop = this.getProperty("header.sdicoop.idegov", true);

		//carico le properties
		this.xslFatturaSDI10 = getProperty("xsl.fatturapa.sdi10", true);
		this.xslFatturaSDI11 = getProperty("xsl.fatturapa.sdi11", true);
		this.xslFatturaV12 = getProperty("xsl.fatturapa.v12", true);
		this.xslNotificaDT = getProperty("xsl.notificaDT", true);
		this.xslNotificaEC = getProperty("xsl.notificaEC", true);
		this.xslScartoEC = getProperty("xsl.scartoEC", true);
		this.xslPccRiallineamento = getProperty("xsl.PccRiallineamento", true);


		String entiListString = getProperty("xsl.traccia.list", true);

		this.xslTraccia = new Properties();
		if(entiListString != null && !entiListString.isEmpty()) {
			String[] entiList = entiListString.split(",");
			for(String ente: entiList) {
				this.xslTraccia.put(ente, getProperty("xsl.traccia."+ente, true));
			}
		}

		this.xslBaseDir = getProperty("xsl.baseDir", true);

		this.invioFatturaURL = new URL(this.getProperty("invioFattura.url", true));
		this.invioFatturaUsername = this.getProperty("invioFattura.username", false);
		this.invioFatturaPassword = this.getProperty("invioFattura.password", false);

		this.maxTentativiRispedizione = Integer.parseInt(this.getProperty("rispedizioni.maxTentativiRispedizione", true));
		this.fattoreRispedizione = Integer.parseInt(this.getProperty("rispedizioni.fattoreRispedizione", true));
		this.maxTentativiRispedizioneWFM = Integer.parseInt(this.getProperty("rispedizioni.maxTentativiRispedizioneWFM", true));
		this.fattoreRispedizioneWFM = Integer.parseInt(this.getProperty("rispedizioni.fattoreRispedizioneWFM", true));
		this.maxTentativiRispedizioneSdI = Integer.parseInt(this.getProperty("rispedizioni.maxTentativiRispedizioneSdI", true));
		this.fattoreRispedizioneSdI = Integer.parseInt(this.getProperty("rispedizioni.fattoreRispedizioneSdI", true));

		this.tipidocumento = new HashMap<String, TipoDocumento>();
		
        String tipiDocString = getProperty("tipo_documento.list", true);

        if(tipiDocString != null && !tipiDocString.isEmpty()) {
                String[] tipiDocList = tipiDocString.split(",");
                for(String tipoDoc: tipiDocList) {
            		TipoDocumento tipodoc = new TipoDocumento();
            		tipodoc.setCodice(tipoDoc);
            		tipodoc.setConservazione(getProperty("tipo_documento.conservazione."+tipoDoc, false));

                	this.tipidocumento.put(tipoDoc, tipodoc);
                }
        }

	}

	/* ********  P R O P E R T I E S  ******** */

	public int getFattoreRispedizione() {
		return fattoreRispedizione;
	}

	public void setFattoreRispedizione(int fattoreRispedizione) {
		this.fattoreRispedizione = fattoreRispedizione;
	}

	public int getFattoreRispedizioneWFM() {
		return fattoreRispedizioneWFM;
	}

	public void setFattoreRispedizioneWFM(int fattoreRispedizioneWFM) {
		this.fattoreRispedizioneWFM = fattoreRispedizioneWFM;
	}

	public int getFattoreRispedizioneSdI() {
		return fattoreRispedizioneSdI;
	}

	public void setFattoreRispedizioneSdI(int fattoreRispedizioneSdI) {
		this.fattoreRispedizioneSdI = fattoreRispedizioneSdI;
	}

	public Enumeration<?> keys(){
		return this.reader.propertyNames();
	}

	public String getXslNotificaEC() {
		return xslNotificaEC;
	}

	public void setXslNotificaEC(String xslNotificaEC) {
		this.xslNotificaEC = xslNotificaEC;
	}

	public String getXslNotificaDT() {
		return xslNotificaDT;
	}

	public void setXslNotificaDT(String xslNotificaDT) {
		this.xslNotificaDT = xslNotificaDT;
	}

	public String getXslScartoEC() {
		return xslScartoEC;
	}

	public void setXslScartoEC(String xslScartoEC) {
		this.xslScartoEC = xslScartoEC;
	}

	public String getXslBaseDir() {
		return xslBaseDir;
	}

	public void setXslBaseDir(String xslBaseDir) {
		this.xslBaseDir = xslBaseDir;
	}

	public String getXslFatturaSDI10() {
		return this.xslFatturaSDI10;
	}

	public void setXslFatturaSDI10(String xslFatturaSDI10) {
		this.xslFatturaSDI10 = xslFatturaSDI10;
	}

	public String getXslFatturaSDI11() {
		return this.xslFatturaSDI11;
	}

	public void setXslFatturaSDI11(String xslFatturaSDI11) {
		this.xslFatturaSDI11 = xslFatturaSDI11;
	}

	public String getXslPccRiallineamento() {
		return this.xslPccRiallineamento;
	}

	public void setXslPccRiallineamento(String xslPccRiallineamento) {
		this.xslPccRiallineamento = xslPccRiallineamento;
	}

	public String getXslFatturaV12() {
		return xslFatturaV12;
	}

	public void setXslFatturaV12(String xslFatturaV12) {
		this.xslFatturaV12 = xslFatturaV12;
	}

	public URL getInvioFatturaURL() {
		return invioFatturaURL;
	}
	public String getInvioFatturaUsername() {
		return invioFatturaUsername;
	}
	public String getInvioFatturaPassword() {
		return invioFatturaPassword;
	}

	public String getXslTraccia(TipoComunicazioneType tipoComunicazione) {
		if(this.xslTraccia.containsKey(tipoComunicazione.toString())) {
			return this.xslTraccia.getProperty(tipoComunicazione.toString());
		}
		return null;
	}

	public String getXslTraccia(TipoXSL tipoXSL) {
		switch(tipoXSL) {
		case FATTURA_V10: return this.getXslFatturaSDI10();
		case FATTURA_V11: return this.getXslFatturaSDI11();
		case FATTURA_V12: return this.getXslFatturaV12();
		case NOTIFICA_DT: return this.getXslNotificaDT();
		case NOTIFICA_EC: return this.getXslNotificaEC();
		case PCC_RIALLINEAMENTO: return this.getXslPccRiallineamento();
		case SCARTO_EC: return this.getXslScartoEC();
		case TRACCIA_AT:return this.getXslTraccia(TipoComunicazioneType.AT);
		case TRACCIA_MC:return this.getXslTraccia(TipoComunicazioneType.MC);
		case TRACCIA_MT:return this.getXslTraccia(TipoComunicazioneType.MT);
		case TRACCIA_NE:return this.getXslTraccia(TipoComunicazioneType.NE);
		case TRACCIA_NS:return this.getXslTraccia(TipoComunicazioneType.NS);
		case TRACCIA_RC:return this.getXslTraccia(TipoComunicazioneType.RC);
		default: return null;
		}

	}

	public String getInfoVersione() {
		return "Versione Software["+CostantiFatturaPA.VERSIONE+"] Commit["+CostantiFatturaPA.COMMIT_ID+"]";
	}

	public String getIdEgovHeaderSPCoop() {
		return idEgovHeaderSPCoop;
	}

	public String getIdEgovHeaderSDICoop() {
		return idEgovHeaderSDICoop;
	}

	public String getDiscriminatorHeaderNameSPCoop() {
		return discriminatorHeaderNameSPCoop;
	}

	public String getDiscriminatorHeaderValueSPCoop() {
		return discriminatorHeaderValueSPCoop;
	}

	public Map<String, TipoDocumento> getTipidocumento() {
		return tipidocumento;
	}
	
	public String getIdEgovHeaderFatturazioneAttiva() {
		return idEgovHeaderFatturazioneAttiva;
	}

	public void setIdEgovHeaderFatturazioneAttiva(String idEgovHeaderFatturazioneAttiva) {
		this.idEgovHeaderFatturazioneAttiva = idEgovHeaderFatturazioneAttiva;
	}



}
