package org.govmix.proxy.fatturapa.web.commons.converter.fattura;

import java.util.List;

import org.govmix.proxy.fatturapa.orm.AllegatoFattura;
import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.orm.LottoFatture;
import org.govmix.proxy.fatturapa.web.commons.consegnaFattura.InserimentoLottiException;

public interface ILottoConverter {

	public LottoFatture getLottoFatture() throws InserimentoLottiException;
	public List<String> getIdentificativiInterniFatture()throws InserimentoLottiException;
	public FatturaElettronica getFatturaElettronica(String key)throws InserimentoLottiException;
	public List<AllegatoFattura> getAllegati(String key)throws InserimentoLottiException;
	
	public String getDenominazioneDestinatario();
	public String getNomeDestinatario();
	public String getCognomeDestinatario();
	public String getCodiceDestinatario();
	public String getIndirizzoDestinatario();
	public String getCapDestinatario();
	public String getComuneDestinatario();
	public String getProvincia();
	public String getStato();	
	public String getPartitaIVADestinatario();
	public String getCFMittente();
	public String getCodiceFiscaleDestinatario();
}
