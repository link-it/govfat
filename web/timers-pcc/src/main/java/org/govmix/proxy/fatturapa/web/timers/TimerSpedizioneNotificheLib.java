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
package org.govmix.proxy.fatturapa.web.timers;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.orm.PccNotifica;
import org.govmix.proxy.fatturapa.orm.constants.StatoConsegnaType;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.FatturaPassivaBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.SpedizioneNotificheBD;
import org.govmix.proxy.fatturapa.web.commons.dao.DAOFactory;
import org.govmix.proxy.fatturapa.web.commons.sonde.Sonda;
import org.govmix.proxy.fatturapa.web.commons.utils.TransformUtils;
import org.govmix.proxy.fatturapa.web.timers.utils.BatchProperties;
import org.openspcoop2.utils.mail.Sender;
import org.openspcoop2.utils.mail.SenderFactory;
import org.openspcoop2.utils.mail.SenderType;
import org.openspcoop2.utils.resources.TemplateUtils;

import freemarker.template.Template;

/**
 * Implementazione dell'interfaccia {@link TimerSpedizioneNotifiche}.
 * 
 * 
 *  
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author: gbussu $
 * @version $Rev: 9747 $, $Date: 2014-03-10 11:47:43 +0100 (Mon, 10 Mar 2014) $
 */

public class TimerSpedizioneNotificheLib extends AbstractTimerLib {


	private Sender senderCommonsMail;
	
	private String host;
	private int port;
	private String username;
	private String password;
	
	private String from;
	
	private String templateOggetto;
	private String templateMessaggio;
	
	private Map<String, Map<String, String>> operazioni; 

	public TimerSpedizioneNotificheLib(int limit, Logger log, boolean logQuery) throws Exception{
		super(limit, log, logQuery);
		this.senderCommonsMail = SenderFactory.newSender(SenderType.COMMONS_MAIL, log);
		this.senderCommonsMail.setConnectionTimeout(100);
		this.senderCommonsMail.setReadTimeout(5 * 1000);
		
		BatchProperties batchProperties = BatchProperties.getInstance();
		this.host = batchProperties.getMailHost();
		this.port = batchProperties.getMailPort();
		this.username = batchProperties.getMailUsername();
		this.password = batchProperties.getMailPassword();

		this.from = batchProperties.getMailFrom();
		this.operazioni = batchProperties.getOperazioni();

		this.templateOggetto = readFileContentFromClasspath(batchProperties.getMailTemplateOggettoPath());
		this.templateMessaggio = readFileContentFromClasspath(batchProperties.getMailTemplateMessaggioPath());
	}
	
	private String readFileContentFromClasspath(String path) throws Exception {
		InputStream is = null;
		ByteArrayOutputStream baos = null;
		try{
			is = TimerSpedizioneNotificheLib.class.getResourceAsStream(path);
			baos = new ByteArrayOutputStream();
			IOUtils.copy(is, baos);
			
			return baos.toString();
		} finally {
			if(is != null){
				try {is.close();}catch(Exception e) {}
			}
			
			if(baos != null) {
				try {
					baos.flush();
					baos.close();
				}catch(Exception e) {}
			}
		}
		
	}

	@Override
	public void execute() throws Exception {

		Connection connection = null;
		try {
			connection = DAOFactory.getInstance().getConnection();

			SpedizioneNotificheBD notBd = new SpedizioneNotificheBD(log, connection, false);
			FatturaPassivaBD fatturaElettronicaBd = new FatturaPassivaBD(log, connection, false);

			this.log.info("Cerco notifiche da consegnare");
			Date date = new Date();
			long countFatture = notBd.countNotifiche(date);
			this.log.info("Trovate ["+countFatture+"] notifiche da spedire");
			long countFattureElaborate = 0; 

			if(countFatture > 0) {
				connection.setAutoCommit(false);

				this.log.info("Gestisco ["+countFatture+"] notifiche, ["+this.limit+"] alla volta");
				List<PccNotifica> lstId = notBd.findAllNotifiche(date, 0, this.limit);
				while(countFattureElaborate < countFatture) {
					try {
						for(PccNotifica notifica : lstId) {
							List<String> listMail = TransformUtils.toListMailAddress(notifica.getDipartimento().getListaEmailNotifiche());
								try {
									boolean ok = true;
									String error = "";
									
									FatturaElettronica fattura = null;
									if(notifica.getPccTraccia().getIdFattura() > 0) {
										fattura = fatturaElettronicaBd.getById(notifica.getPccTraccia().getIdFattura());
									}
									
									for(String email: listMail) {
										try {
											String oggetto = getMessaggioByTemplate(this.templateOggetto, notifica, email, fattura);
											String messaggio = getMessaggioByTemplate(this.templateMessaggio, notifica, email, fattura);
											
											this.log.debug("Oggetto:" + oggetto);
											this.log.debug("Messaggio:" + messaggio);
											org.openspcoop2.utils.mail.Mail mail = new org.openspcoop2.utils.mail.Mail();
											mail.setServerHost(this.host);
											mail.setServerPort(this.port);
											
											if(this.username != null && !this.username.isEmpty() && this.password != null && !this.password.isEmpty()) {
												mail.setUsername(this.username);
												mail.setPassword(this.password);
											}

											mail.setStartTls(false);
											
											mail.setFrom(this.from);
											mail.setTo(email);
											
											mail.setSubject(oggetto);
											mail.getBody().setMessage(messaggio);
											
											senderCommonsMail.send(mail, true); 
										} catch(Exception e) {
											error += "Errore durante l'invio della notifica con id ["+notifica.getId()+"] al destinatario ["+email+"]:"+e.getMessage() + "\n";
											this.log.warn("Errore durante l'invio della notifica con id ["+notifica.getId()+"] al destinatario ["+email+"]:"+e.getMessage(),e);
											ok = false;
										}
									}
									
									if(!ok) {
										throw new Exception(error);
									}
	
									notifica.setStatoConsegna(StatoConsegnaType.CONSEGNATA);
									notifica.setDataConsegna(new Date());
									notBd.update(notifica);
									countFattureElaborate++;
								} catch(Exception e) {
									this.log.warn("Errore durante l'invio della notifica con id ["+notifica.getId()+"]:"+e.getMessage(),e);
	
									notifica.setStatoConsegna(StatoConsegnaType.ERRORE_CONSEGNA);
									notifica.setDataConsegna(new Date());
									notBd.update(notifica);
									countFattureElaborate++;
								}
							}
						this.log.info("Gestite ["+countFattureElaborate+"\\"+countFatture+"] notifiche da spedire");

						lstId = notBd.findAllNotifiche(date, 0, this.limit);
						connection.commit();
						Sonda.getInstance().registraChiamataServizioOK(this.getTimerName());
					} catch(Exception e) {
						this.log.error("Errore durante l'esecuzione del batch SpedizioneNotifiche: "+e.getMessage(), e);
						connection.rollback();
					}
				}
				this.log.info("Gestite ["+countFattureElaborate+"\\"+countFatture+"] notifiche da spedire. Fine.");
				connection.setAutoCommit(true);
			}

		} finally {
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {}
			}
		}

	}
	
	private String getMessaggioByTemplate(String templateString, PccNotifica notifica, String email, FatturaElettronica fattura) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();  
		map.put("email", email);
		map.put("notifica", notifica);
		for(String lang: this.operazioni.keySet()) {
			map.put("operazione_"+lang, this.operazioni.get(lang).get(notifica.getPccTraccia().getOperazione()));
		}
		if(fattura != null)
			map.put("fattura", fattura);
		
		Template template = TemplateUtils.buildTemplate("template", templateString.getBytes());
		String templatized = TemplateUtils.toString(template, map);
		
		return templatized;
	}

}
