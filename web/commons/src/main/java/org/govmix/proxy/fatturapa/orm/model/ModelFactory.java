/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2020 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2020 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
package org.govmix.proxy.fatturapa.orm.model;

/**     
 * Factory
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class ModelFactory {

	public static LottoFattureModel LOTTO_FATTURE = new LottoFattureModel();
	
	public static DipartimentoModel DIPARTIMENTO = new DipartimentoModel();
	
	public static TracciaSDIModel TRACCIA_SDI = new TracciaSDIModel();
	
	public static FatturaElettronicaModel FATTURA_ELETTRONICA = new FatturaElettronicaModel();
	
	public static NotificaEsitoCommittenteModel NOTIFICA_ESITO_COMMITTENTE = new NotificaEsitoCommittenteModel();
	
	public static NotificaDecorrenzaTerminiModel NOTIFICA_DECORRENZA_TERMINI = new NotificaDecorrenzaTerminiModel();
	
	public static AllegatoFatturaModel ALLEGATO_FATTURA = new AllegatoFatturaModel();
	
	public static EnteModel ENTE = new EnteModel();
	
	public static ProtocolloModel PROTOCOLLO = new ProtocolloModel();
	
	public static RegistroModel REGISTRO = new RegistroModel();
	
	public static RegistroPropertyModel REGISTRO_PROPERTY = new RegistroPropertyModel();
	
	public static SIPModel SIP = new SIPModel();
	
	public static UtenteModel UTENTE = new UtenteModel();
	
	public static DipartimentoPropertyModel DIPARTIMENTO_PROPERTY = new DipartimentoPropertyModel();
	
	public static PccOperazioneModel PCC_OPERAZIONE = new PccOperazioneModel();
	
	public static PccDipartimentoOperazioneModel PCC_DIPARTIMENTO_OPERAZIONE = new PccDipartimentoOperazioneModel();
	
	public static PccUtenteOperazioneModel PCC_UTENTE_OPERAZIONE = new PccUtenteOperazioneModel();
	
	public static PccContabilizzazioneModel PCC_CONTABILIZZAZIONE = new PccContabilizzazioneModel();
	
	public static PccScadenzaModel PCC_SCADENZA = new PccScadenzaModel();
	
	public static PccPagamentoModel PCC_PAGAMENTO = new PccPagamentoModel();
	
	public static PccTracciaModel PCC_TRACCIA = new PccTracciaModel();
	
	public static PccTracciaTrasmissioneModel PCC_TRACCIA_TRASMISSIONE = new PccTracciaTrasmissioneModel();
	
	public static PccTracciaTrasmissioneEsitoModel PCC_TRACCIA_TRASMISSIONE_ESITO = new PccTracciaTrasmissioneEsitoModel();
	
	public static PccErroreElaborazioneModel PCC_ERRORE_ELABORAZIONE = new PccErroreElaborazioneModel();
	
	public static PccRispedizioneModel PCC_RISPEDIZIONE = new PccRispedizioneModel();
	
	public static PccNotificaModel PCC_NOTIFICA = new PccNotificaModel();
	
	public static EventoModel EVENTO = new EventoModel();
	

}