package org.govmix.proxy.fatturapa.orm.utils;

import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.govmix.fatturapa.parer.beans.UnitaDocumentariaFatturaAttivaInput;
import org.govmix.fatturapa.parer.beans.UnitaDocumentariaFatturaPassivaInput;
import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.web.commons.utils.CommonsProperties;

public class TipoDocumentoUtils {

	private static TipoDocumentoUtils instance;
	
	public static TipoDocumentoUtils getInstance(Logger log) throws Exception {
		if(instance == null) {
			instance = new TipoDocumentoUtils(log);
		}
		
		return instance;
	}
	private Map<String, TipoDocumento> tipidocumento;
	
	public TipoDocumentoUtils(Logger log) throws Exception {
		this.tipidocumento = CommonsProperties.getInstance(log).getTipidocumento();
	}

	public String getTipoDocumentoConsevazione(FatturaElettronica input) throws Exception {
		String tipoConservazione = null;
		if(this.tipidocumento.containsKey(input.get_value_tipoDocumento())) {
			tipoConservazione = this.tipidocumento.get(input.get_value_tipoDocumento()).getConservazione();
		}
		
		if(tipoConservazione != null) {
			return tipoConservazione;
		}
		
		throw new Exception("Impossibile inviare in conservazione la fattura con tipo documento ["+input.get_value_tipoDocumento()+"]");
	}

	public String getTipoDocumentoConsevazioneFatturaPassiva(UnitaDocumentariaFatturaPassivaInput input) throws Exception {
		return getTipoDocumentoConsevazione(input.getFattura());
	}

	public String getTipoDocumentoConsevazioneFatturaAttiva(UnitaDocumentariaFatturaAttivaInput input) throws Exception {
		return getTipoDocumentoConsevazione(input.getFattura());
	}

	public boolean isTipoNotaCredito(FatturaElettronica input) {
		return input.get_value_tipoDocumento().equals("TD04");
	}
	public Set<String> getValues() {
		return tipidocumento.keySet();
	}

}
