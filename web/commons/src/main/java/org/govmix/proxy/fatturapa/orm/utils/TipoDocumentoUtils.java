package org.govmix.proxy.fatturapa.orm.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.govmix.fatturapa.parer.beans.UnitaDocumentariaFatturaAttivaInput;
import org.govmix.fatturapa.parer.beans.UnitaDocumentariaFatturaPassivaInput;
import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.web.commons.utils.CommonsProperties;

public class TipoDocumentoUtils {

	private static TipoDocumentoUtils instance;
	
	public static final String TIPO_DOCUMENTO_NOTA_CREDITO = "TD04";
	public static final String TIPO_DOCUMENTO_SCONOSCIUTO = "TDXX";
	
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
		if(this.tipidocumento.containsKey(input.getTipoDocumento())) {
			tipoConservazione = this.tipidocumento.get(input.getTipoDocumento()).getConservazione();
		}
		
		if(tipoConservazione != null) {
			return tipoConservazione;
		}
		
		throw new Exception("Impossibile inviare in conservazione la fattura con tipo documento ["+input.getTipoDocumento()+"]");
	}

	public String getTipoDocumentoConsevazioneFatturaPassiva(UnitaDocumentariaFatturaPassivaInput input) throws Exception {
		return getTipoDocumentoConsevazione(input.getFattura());
	}

	public String getTipoDocumentoConsevazioneFatturaAttiva(UnitaDocumentariaFatturaAttivaInput input) throws Exception {
		return getTipoDocumentoConsevazione(input.getFattura());
	}

	public boolean isTipoNotaCredito(FatturaElettronica input) {
		return input.getTipoDocumento().equals(TIPO_DOCUMENTO_NOTA_CREDITO);
	}
	public Collection<String> getValues() {
		Comparator<? super String> c = new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		};

		Set<String> keySet = tipidocumento.keySet();
		
		String[] array = keySet.toArray(new String[] {});
		Arrays.sort(array, c);
		return Arrays.asList(array);
	}

}
