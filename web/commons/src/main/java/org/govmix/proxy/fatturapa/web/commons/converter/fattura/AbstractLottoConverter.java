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
package org.govmix.proxy.fatturapa.web.commons.converter.fattura;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.govmix.proxy.fatturapa.orm.AllegatoFattura;
import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.orm.LottoFatture;
import org.govmix.proxy.fatturapa.orm.constants.FormatoArchivioInvioFatturaType;
import org.govmix.proxy.fatturapa.orm.constants.FormatoTrasmissioneType;
import org.govmix.proxy.fatturapa.orm.constants.StatoConsegnaType;
import org.govmix.proxy.fatturapa.orm.constants.StatoInserimentoType;
import org.govmix.proxy.fatturapa.orm.constants.StatoProtocollazioneType;
import org.govmix.proxy.fatturapa.web.commons.consegnaFattura.ConsegnaFatturaParameters;
import org.govmix.proxy.fatturapa.web.commons.consegnaFattura.InserimentoLottiException;
import org.govmix.proxy.fatturapa.web.commons.consegnaFattura.XPathUtils;

public abstract class AbstractLottoConverter<T> implements ILottoConverter {

	private T lotto;
	protected ConsegnaFatturaParameters params;
	protected byte[] lottoAsByte;

	public AbstractLottoConverter(T t, byte[] fatturaAsString, ConsegnaFatturaParameters params) throws InserimentoLottiException {
		this.lotto = t;
		this.lottoAsByte = fatturaAsString;
		this.params = params;
		if(this.params!=null)
			this.validate(this.params.isFatturazioneAttiva()); //la validazione per la fatturazione attiva e' piu' restrittiva, nella fatturazione passiva sono ammessi alcuni default
	}

	protected SimpleDateFormat getSdfYear() {
		return new SimpleDateFormat("yyyy");
	}
	public abstract void validate(boolean strictValidation) throws InserimentoLottiException;

	public abstract List<String> getCausali(int index);
	public abstract double getImportoTotaleDocumento(int index);
	public abstract double getImportoTotaleRiepilogo(int index);

	public String getCausale(int index) {
		StringBuffer sbCausale = new StringBuffer();
		List<String> causali = this.getCausali(index); 
		if(causali != null) {
			for(String causale: causali) {
				if(sbCausale.length() > 0) {
					sbCausale.append('|');
				}
				sbCausale.append(causale);
			}
		}
		if(sbCausale.length() > 0) {
			return sbCausale.toString();
		} else return null;
	}

	public LottoFatture getLottoFatture() throws InserimentoLottiException {

		LottoFatture fatturaElettronica = this.getDatiComuni();
		this.populateLottoConDatiSpecifici(fatturaElettronica);

		return fatturaElettronica;
	}
	protected abstract List<AllegatoFattura> getAllegati(int index);
	public List<AllegatoFattura> getAllegati(String key) {
		int index = Integer.parseInt(key) - 1;
		return this.getAllegati(index);
	}

	public FatturaElettronica getFatturaElettronica(String key) throws InserimentoLottiException{

		initFatture();
		if(this.fatture.containsKey(key)) {
			return this.fatture.get(key);
		} else {
			return null;
		}
	}

	protected FatturaElettronica initFatturaElettronica(int index, byte[] xml) {
		FatturaElettronica fatturaElettronica = this.getDatiComuniFattura(index, xml);
		this.populateFatturaConDatiSpecifici(fatturaElettronica, index);

		return fatturaElettronica;
	}

	protected Map<String, FatturaElettronica> fatture;
	protected abstract void initFatture() throws InserimentoLottiException; 

	public List<String> getIdentificativiInterniFatture() throws InserimentoLottiException{
		initFatture();
		return Arrays.asList(this.fatture.keySet().toArray(new String[]{}));
	}
	protected abstract void populateLottoConDatiSpecifici(LottoFatture lottoFatture) ;

	protected abstract void populateFatturaConDatiSpecifici(FatturaElettronica fatturaElettronica, int index) ;

	private FatturaElettronica getDatiComuniFattura(int index, byte[] xml) {
		FatturaElettronica fatturaElettronica = new FatturaElettronica();
		fatturaElettronica.setIdentificativoSdi(this.params.getIdentificativoSdI());
		fatturaElettronica.setPosizione(index+1);

		fatturaElettronica.setCodiceDestinatario(this.params.getCodiceDestinatario());
		fatturaElettronica.setFormatoTrasmissione(FormatoTrasmissioneType.valueOf(this.params.getFormatoFatturaPA()));

		fatturaElettronica.setNomeFile(this.getNomeFileFattura());
		fatturaElettronica.setMessageId(this.params.getMessageId());

		fatturaElettronica.setFatturazioneAttiva(this.params.isFatturazioneAttiva());
		fatturaElettronica.setDataRicezione(this.params.getDataRicezione());

		fatturaElettronica.setProtocollo(this.params.getProtocollo());
		fatturaElettronica.setStatoConsegna(StatoConsegnaType.NON_CONSEGNATA);
		fatturaElettronica.setTentativiConsegna(new Integer(0));
		fatturaElettronica.setStatoProtocollazione(StatoProtocollazioneType.NON_PROTOCOLLATA);

		fatturaElettronica.setXml(xml);

		return fatturaElettronica;
	}
	
	private String getNomeFileFattura() {
		if(FormatoArchivioInvioFatturaType.P7M.equals(this.params.getFormatoArchivioInvioFattura())) {
			if(this.params.getNomeFile().toLowerCase().endsWith(".p7m")) {
				int index = this.params.getNomeFile().toLowerCase().indexOf(".p7m");
				return this.params.getNomeFile().substring(0, index);
			} else {
				return this.params.getNomeFile();
			}
		} else {
			return this.params.getNomeFile();
		}

	}

	private LottoFatture getDatiComuni() throws InserimentoLottiException {
		LottoFatture lottoFatture = new LottoFatture();

		lottoFatture.setIdentificativoSdi(this.params.getIdentificativoSdI());

		lottoFatture.setCodiceDestinatario(this.params.getCodiceDestinatario());
		lottoFatture.setFormatoTrasmissione(FormatoTrasmissioneType.valueOf(this.params.getFormatoFatturaPA()));
		lottoFatture.setFormatoArchivioInvioFattura(this.params.getFormatoArchivioInvioFattura());

		lottoFatture.setIdEgov(this.params.getIdEgov());
		lottoFatture.setNomeFile(this.params.getNomeFile());
		lottoFatture.setMessageId(this.params.getMessageId());

		lottoFatture.setFatturazioneAttiva(this.params.isFatturazioneAttiva());
		lottoFatture.setDataRicezione(this.params.getDataRicezione());
		lottoFatture.setDominio(this.params.getDominio());
		lottoFatture.setSottodominio(this.params.getSottodominio());
		lottoFatture.setStatoElaborazioneInUscita(this.params.getStato());

		lottoFatture.setProtocollo(this.params.getProtocollo());
		lottoFatture.setStatoConsegna(StatoConsegnaType.NON_CONSEGNATA);
		lottoFatture.setTentativiConsegna(new Integer(0));
		lottoFatture.setStatoInserimento(StatoInserimentoType.NON_INSERITO);
		lottoFatture.setStatoProtocollazione(StatoProtocollazioneType.NON_PROTOCOLLATA);

		if(this.params.getNodoCodicePagamento()!=null) {
			this.params.getLog().debug("Cerco numero avviso PagoPA: dipartimento ["+this.params.getCodiceDestinatario()+"]. Nodo ["+this.params.getNodoCodicePagamento()+"]. Prefisso ["+this.params.getPrefissoCodicePagamento()+"]");
			lottoFatture.setPagoPA(XPathUtils.getPagoPA(this.lottoAsByte, this.params.getNodoCodicePagamento(), this.params.getPrefissoCodicePagamento(), this.params.getNomeFile(), this.params.getLog()));
		} else {
			this.params.getLog().debug("Non cerco numero avviso PagoPA"); 
		}

		lottoFatture.setXml(this.params.getRaw());

		return lottoFatture;
	}

	public T getLotto() {
		return this.lotto;
	}

}
