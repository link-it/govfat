package org.govmix.proxy.fatturapa.orm.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.govmix.fatturapa.parer.beans.UnitaDocumentariaFatturaAttivaInput;
import org.govmix.fatturapa.parer.beans.UnitaDocumentariaFatturaPassivaInput;
import org.govmix.proxy.fatturapa.orm.FatturaElettronica;

public class TipoDocumentoUtils {

	private static TipoDocumentoUtils instance;
	
	public static TipoDocumentoUtils getInstance() {
		if(instance == null) {
			instance = new TipoDocumentoUtils();
		} 
		
		return instance;
	}
	private Map<String, TipoDocumento> tipidocumento;
	
	public TipoDocumentoUtils() {
		this.tipidocumento = new HashMap<String, TipoDocumento>();
		this.add(this.tipidocumento,"TD01","FATTURA");
		this.add(this.tipidocumento,"TD02","ACCONTO/ANTICIPO SU FATTURA");
		this.add(this.tipidocumento,"TD03","ACCONTO/ANTICIPO SU PARCELLA");
		this.add(this.tipidocumento,"TD04","NOTA DI CREDITO");
		this.add(this.tipidocumento,"TD05","NOTA DI DEBITO");
		this.add(this.tipidocumento,"TD06","PARCELLA");
		this.add(this.tipidocumento,"TD16","INTEGRAZIONE FATTURA REVERSE CHARGE INTERNO");
		this.add(this.tipidocumento,"TD17","INTEGRAZIONE/AUTOFATTURA PER ACQUISTO SERVIZI DALL'ESTERO");
		this.add(this.tipidocumento,"TD18","INTEGRAZIONE PER ACQUISTO DI BENI INTRACOMUNITARI");
		this.add(this.tipidocumento,"TD19","INTEGRAZIONE/AUTOFATTURA PER ACQUISTO DI BENI EX ART.17");
		this.add(this.tipidocumento,"TD20","AUTOFATTURA");
		this.add(this.tipidocumento,"TD21","AUTOFATTURA PER SPLAFONAMENTO");
		this.add(this.tipidocumento,"TD22","ESTRAZIONE BENI DA DEPOSITO IVA");
		this.add(this.tipidocumento,"TD23","ESTRAZIONE BENI DA DEPOSITO IVA CON VERSAMENTO DELL'IVA");
		this.add(this.tipidocumento,"TD24","FATTURA DIFFERITA DI CUI ALL'ART. 21 4A)");
		this.add(this.tipidocumento,"TD25","FATTURA DIFFERITA DI CUI ALL'ART. 21 4B)");
		this.add(this.tipidocumento,"TD26","CESSIONE DI BENI AMMORTIZZABILI E PER PASSAGGI INTERNI");
		this.add(this.tipidocumento,"TD27","FATTURA PER AUTOCONSUMO/CESSIONI GRATUITE");
	}

	private void add(Map<String, TipoDocumento> tipidocumento, String cod, String cons) {
		TipoDocumento tipodoc = new TipoDocumento();
		tipodoc.setCodice(cod);
		tipodoc.setConservazione(cons);
		
		this.tipidocumento.put(cod, tipodoc);
	}

	public String getTipoDocumentoConsevazione(FatturaElettronica input) {
		if(tipidocumento.containsKey(input.get_value_tipoDocumento())) {
			return tipidocumento.get(input.get_value_tipoDocumento()).getConservazione();
		}
		
		return null;
	}

	public String getTipoDocumentoConsevazioneFatturaPassiva(UnitaDocumentariaFatturaPassivaInput input) {
		return getTipoDocumentoConsevazione(input.getFattura());
	}

	public String getTipoDocumentoConsevazioneFatturaAttiva(UnitaDocumentariaFatturaAttivaInput input) {
		return getTipoDocumentoConsevazione(input.getFattura());
	}

	public boolean isTipoNotaCredito(FatturaElettronica input) {
		return input.get_value_tipoDocumento().equals("TD04");
	}
	public Set<String> getValues() {
		return tipidocumento.keySet();
	}

}
