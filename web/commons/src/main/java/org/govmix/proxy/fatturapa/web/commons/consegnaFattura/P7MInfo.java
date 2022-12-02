package org.govmix.proxy.fatturapa.web.commons.consegnaFattura;

import java.io.ByteArrayInputStream;

import org.apache.log4j.Logger;
import java.io.StringReader;

//import org.bouncycastle.util.io.pem.PemReader;
import org.openspcoop2.utils.io.Base64Utilities;

/**
 * P7MInfo
 * 
 * @author Andrea Poli (apoli@link.it)
 * @author $Author: apoli $
 * @version $Rev: 11424 $, $Date: 2016-01-26 10:59:24 +0100 (Tue, 26 Jan 2016) $
 */
public class P7MInfo {

	/*
	 * Infatti il formato del file rispecchia quanto indicato nella delibera CNIPA numero 45 del 9 Novembre 2009 all’articolo 21, comma 3.
	 * 
	 * La delibera cita quanto segue:
	 * “Per la codifica della busta crittografica deve essere utilizzato il formato
	 * ASN.1 (ISO/IEC8824) in rappresentazione binaria (ISO/IEC 8825, BER - DER) o
	 * alfanumerica ottenuta applicando la trasformazione BASE 64 (RFC 1421, RFC
	 * 2045). La testata e la coda previsti nelle specifiche RFC 1421 e RFC 2045
	 * possono essere assenti.”
	 *
	 * In pratica la fattura P7M puo' essere ricevuta direttamente come
	 * rappresentazione binaria o codificata in base64.
	 * 
	 **/
	
	private byte[] xmlDecoded;
	private boolean base64Encoded = false;
	
	public P7MInfo(byte [] fattura, Logger log) throws Throwable{
		
		try{
//			org.bouncycastle.cms.CMSSignedData cmsSignedData = new org.bouncycastle.cms.CMSSignedData(new ByteArrayInputStream(fattura));
//			this.xmlDecoded = (byte[]) cmsSignedData.getSignedContent().getContent();
			
		}catch(Throwable e){
			
			byte[] decoded = null;
			try{
				decoded = Base64Utilities.decode(new String(fattura));
				String fatturaS = new String(fattura);
				if(fatturaS.trim().startsWith("-----BEGIN")) {
//					PemReader pemReader = null;
					StringReader stringReader = null;
					try {
						stringReader = new StringReader(fatturaS);
//						pemReader = new PemReader(stringReader);
//						decoded = pemReader.readPemObject().getContent();
					}catch(Throwable eDecodePEM) {
						//System.out.println("ERRORE PEM");
						log.error("DecodificaBase64 via PEMReader non riuscita: "+eDecodePEM.getMessage(), eDecodePEM);
					}finally {
//						try {
//							pemReader.close();
//						}catch(Throwable eClose) {}
						try {
							stringReader.close();
						}catch(Throwable eClose) {}
					}
//				} else {
//					System.out.println("NO PEM");
				}
				if(decoded==null) {
					decoded = Base64Utilities.decode(fatturaS);
				}
			}catch(Throwable eDecode){
				log.error("DecodificaBase64 non riuscita: "+eDecode.getMessage(), eDecode);
				throw e; // lancio l'eccezione originale, poiche' piu' interessante. La seconda mi informa solo che non e' una rappresentazione Base64
			}
			this.base64Encoded = true;
			
//			try{
//				org.bouncycastle.cms.CMSSignedData cmsSignedData = new org.bouncycastle.cms.CMSSignedData(new ByteArrayInputStream(decoded));
//				this.xmlDecoded = (byte[]) cmsSignedData.getSignedContent().getContent();				
//			}catch(Throwable eSecond){
//				throw eSecond;
//			}
		}
		
	}
	
	public byte[] getXmlDecoded() {
		return this.xmlDecoded;
	}
	public boolean isBase64Encoded() {
		return this.base64Encoded;
	}
	
}