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
package org.govmix.proxy.fatturapa.model;

/**     
 * Factory
 *
 * @author Papandrea Giuseppe (papandrea@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class ModelFactory {

	public static LottoFattureModel LOTTO_FATTURE = new LottoFattureModel();
	
	public static FatturaElettronicaModel FATTURA_ELETTRONICA = new FatturaElettronicaModel();
	
	public static NotificaEsitoCommittenteModel NOTIFICA_ESITO_COMMITTENTE = new NotificaEsitoCommittenteModel();
	
	public static NotificaDecorrenzaTerminiModel NOTIFICA_DECORRENZA_TERMINI = new NotificaDecorrenzaTerminiModel();
	
	public static AllegatoFatturaModel ALLEGATO_FATTURA = new AllegatoFatturaModel();
	
	public static EnteModel ENTE = new EnteModel();
	
	public static RegistroModel REGISTRO = new RegistroModel();
	
	public static RegistroPropertyModel REGISTRO_PROPERTY = new RegistroPropertyModel();
	
	public static UtenteModel UTENTE = new UtenteModel();
	
	public static DipartimentoModel DIPARTIMENTO = new DipartimentoModel();
	
	public static DipartimentoPropertyModel DIPARTIMENTO_PROPERTY = new DipartimentoPropertyModel();
	

}