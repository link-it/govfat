/**
 * 
 */
package org.govmix.proxy.fatturapa.soap;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.activation.DataHandler;
import javax.annotation.Resource;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.apache.log4j.Logger;
import org.govmix.fatturapa.AuthorizationFault_Exception;
import org.govmix.fatturapa.FattureAttive;
import org.govmix.fatturapa.GenericFault_Exception;
import org.govmix.fatturapa.InviaFatturaRichiestaTipo;
import org.govmix.fatturapa.NotificheTipo;
import org.govmix.fatturapa.ProtocolloTipo;
import org.govmix.fatturapa.RiceviNotificheRichiestaTipo;
import org.govmix.fatturapa.RiceviNotificheRispostaTipo;
import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.orm.IdFattura;
import org.govmix.proxy.fatturapa.orm.TracciaSDI;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.DipartimentoBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.FatturaAttivaBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.TracciaSdIBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.filter.TracciaSdIFilter;
import org.govmix.proxy.fatturapa.web.commons.consegnaFattura.InserimentoLotti;
import org.govmix.proxy.fatturapa.web.commons.consegnaFattura.InserimentoLottoRequest;
import org.govmix.proxy.fatturapa.web.commons.consegnaFattura.InserimentoLottoResponse;
import org.govmix.proxy.fatturapa.web.commons.fatturaattiva.EsitoInvioFattura.ESITO;
import org.govmix.proxy.fatturapa.web.commons.utils.CommonsProperties;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.openspcoop2.utils.resources.FileSystemUtilities;

public class FattureAttiveImpl implements FattureAttive {

	@Resource 
	private WebServiceContext wsContext;

	private String getPrincipal() throws Exception {
		List<String> principals = ((Map<Object, List<String>>)wsContext.getMessageContext().get(MessageContext.HTTP_REQUEST_HEADERS)).get("PRINCIPAL_PROXY");
		
		if(principals != null && principals.size() > 0) {
			
			return principals.get(0);
		} else {
			throw new Exception("Principal utente non trovato");
		}

	}

	private Logger log;

	public FattureAttiveImpl() throws Exception {
		this.log = LoggerManager.getEndpointFattureAttiveLogger();
		this.log.info("Init fattureAttive Service completato. Info versione: " + CommonsProperties.getInstance(this.log).getInfoVersione());
	}

	@Override
	public void inviaFattura(InviaFatturaRichiestaTipo inviaFatturaRichiestaTipo)
			throws GenericFault_Exception, AuthorizationFault_Exception {

		this.log.info("Invoke inviaFattura...");

		ByteArrayOutputStream baos = null;
		try {
			InserimentoLotti inserimento = new InserimentoLotti(this.log);

			inserimento.setDipartimenti(new DipartimentoBD(this.log).getListaDipartimentiUtente(getPrincipal()));
			List<InserimentoLottoRequest> requestList = new ArrayList<InserimentoLottoRequest>();
			InserimentoLottoRequest request = new InserimentoLottoRequest();

			request.setDipartimento(inviaFatturaRichiestaTipo.getCodiceUO());
			request.setNomeFile(inviaFatturaRichiestaTipo.getNomeFileFattura());
			
			baos = new ByteArrayOutputStream();
			FileSystemUtilities.copy(inviaFatturaRichiestaTipo.getRefFileFattura().getInputStream(), baos);
			
			request.setXml(baos.toByteArray());

			requestList.add(request);
			InserimentoLottoResponse inserisciLotto = inserimento.inserisciLotto(requestList);

			if(ESITO.OK.toString().equals(inserisciLotto.getEsito().toString())) {
				this.log.info("inviaFattura completata con successo");
				return;
			} else {
				throw inserisciLotto.getEccezione();
			}
		} catch(Exception e) {
			this.log.error("inviaFattura completata con errore: "  + e.getMessage(), e);
			throw new GenericFault_Exception(e.getMessage(), e);
		} finally {
			if(baos != null) {
				try {
					baos.flush();
					baos.close();
				} catch(Exception e) {}
			}
		}
	}

	@Override
	public RiceviNotificheRispostaTipo riceviNotifiche(RiceviNotificheRichiestaTipo richiesta)
			throws GenericFault_Exception, AuthorizationFault_Exception {
		this.log.info("Invoke riceviNotifiche...");

		try {
			RiceviNotificheRispostaTipo risposta = new RiceviNotificheRispostaTipo();
			
			risposta.setIdentificativoSDI(richiesta.getIdentificativoSDI());
			risposta.setIdentificativoUO(richiesta.getIdentificativoUO());
			
			FatturaAttivaBD fatturaBD = new FatturaAttivaBD(this.log);
			
			FatturaElettronica fattura = null;
			if(richiesta.getIdentificativoSDI()!=null) {
				IdFattura id = new IdFattura(true);
				id.setIdentificativoSdi(richiesta.getIdentificativoSDI().getIdSDI().intValue());
				id.setPosizione(richiesta.getIdentificativoSDI().getPosizione());
				fattura = fatturaBD.get(id);
			} else {
				fattura = fatturaBD.findByCodDipartimentoNumeroData(richiesta.getIdentificativoUO().getCodiceUO(),richiesta.getIdentificativoUO().getNumeroFattura(),richiesta.getIdentificativoUO().getDataFattura());
			}


			if(fattura.getProtocollo()!=null) {
				String[] split = fattura.getProtocollo().split("/");
				if(split.length != 3) {
					throw new Exception("Protocollo non valido, atteso formato numero / anno / registro");
				}
				
				ProtocolloTipo protocollo = new ProtocolloTipo();
				protocollo.setNumero(Integer.parseInt(split[0].trim()));
				protocollo.setAnno(Integer.parseInt(split[1].trim()));
				protocollo.setRegistro(split[2].trim());
				risposta.setProtocollo(protocollo);
			}
			
			TracciaSdIBD tracciaBD = new TracciaSdIBD(this.log);
			
			TracciaSdIFilter filter = tracciaBD.newFilter();
			filter.setIdentificativoSdi(fattura.getIdentificativoSdi());
			List<TracciaSDI> tracce = tracciaBD.findAll(filter);
			
			if(tracce != null && !tracce.isEmpty()) {
				NotificheTipo notifiche = new NotificheTipo();
				for(TracciaSDI traccia: tracce) {
					switch(traccia.getTipoComunicazione()) {
					case AT:
						notifiche.setAttestazioneTrasmissioneFattura(new DataHandler(traccia.getRawData(), traccia.getContentType()));
						break;
					case DT:
						notifiche.setNotificaDecorrenzaTermini(new DataHandler(traccia.getRawData(), traccia.getContentType()));
						break;
					case EC: //Solo fatturazione passiva
						break;
					case FAT_IN: //Solo fatturazione passiva
						break;
					case FAT_OUT: //traccia della fattura
						break;
					case MC:
						notifiche.setNotificaMancataConsegna(new DataHandler(traccia.getRawData(), traccia.getContentType()));
						break;
					case MT: //Metadati non gestiti
						break;
					case NE:
						if(traccia.getPosizione()==null || traccia.getPosizione().equals(fattura.getPosizione())) { // la posizione e' opzionale
							notifiche.setNotificaEsito(new DataHandler(traccia.getRawData(), traccia.getContentType()));
						}
						break;
					case NS:
						notifiche.setNotificaScarto(new DataHandler(traccia.getRawData(), traccia.getContentType()));
						break;
					case RC:
						notifiche.setRicevutaConsegna(new DataHandler(traccia.getRawData(), traccia.getContentType()));
						break;
					case SE: //Solo fatturazione passiva
						break;
					default:
						break;}
				}
				risposta.setNotifiche(notifiche);
			}
			
			this.log.info("riceviNotifiche completata con successo");
			return risposta;
		} catch(Exception e) {
			this.log.error("riceviNotifiche completata con errore: "  + e.getMessage(), e);
			throw new GenericFault_Exception(e.getMessage(), e);
		}
	}

}