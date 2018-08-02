package org.govmix.fatturapa.parer.builder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.govmix.fatturapa.parer.beans.ParamWrapper;
import org.govmix.fatturapa.parer.beans.UnitaDocumentariaFatturaInput;
import org.govmix.fatturapa.parer.utils.CacheEnti;
import org.govmix.fatturapa.parer.versamento.request.ChiaveType;
import org.govmix.fatturapa.parer.versamento.request.ProfiloUnitaDocumentariaType;
import org.govmix.proxy.fatturapa.orm.Ente;
import org.govmix.proxy.fatturapa.orm.constants.StatoConsegnaType;
import org.govmix.proxy.fatturapa.orm.constants.TipoDocumentoType;

public abstract class AbstractFatturaPassivaUnitaDocumentariaBuilder extends AbstractFatturaUnitaDocumentariaBuilder{

	private List<Integer> lstFattureSenzaDenominazioneMittente;
	private List<Integer> lstFattureConDataInChiave;
	public AbstractFatturaPassivaUnitaDocumentariaBuilder(Logger log) {
		super(log);
		this.lstFattureSenzaDenominazioneMittente = new ArrayList<Integer>();
		this.lstFattureSenzaDenominazioneMittente.add(17563421);
		this.lstFattureSenzaDenominazioneMittente.add(25345733);
		
		this.lstFattureConDataInChiave = new ArrayList<Integer>();
		this.lstFattureConDataInChiave.add(38414974);
		this.lstFattureConDataInChiave.add(38798942);
	}

	@Override
	protected List<ParamWrapper> getParams(UnitaDocumentariaFatturaInput input) {
		
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);
		
		List<ParamWrapper> param = new ArrayList<ParamWrapper>();
		if(StatoConsegnaType.CONSEGNATA.equals(input.getFattura().getStatoConsegna()) && input.getFattura().getProtocollo() != null) {
			param.add(new ParamWrapper("NumeroProtocollo", input.getFattura().getProtocollo()));
			param.add(new ParamWrapper("DataProtocollo", sdf.format(input.getFattura().getDataConsegna())));
		}
		
		param.add(new ParamWrapper("NumeroEmissione", input.getFattura().getNumero()));
		param.add(new ParamWrapper("DataEmissione", sdf.format(input.getFattura().getData())));
		if(this.lstFattureSenzaDenominazioneMittente.contains(input.getFattura().getIdentificativoSdi())) { //fatture gestite a mano perche' non contenenti la denominazione del cedente prestatore
			param.add(new ParamWrapper("DenominazioneMittente", "-"));;
		} else {
			param.add(new ParamWrapper("DenominazioneMittente", input.getFattura().getCedentePrestatoreDenominazione()));
		}
		param.add(new ParamWrapper("TipoDenominazioneMittente", "RagioneSociale"));
		param.add(new ParamWrapper("IdentificativoMittente", input.getFattura().getCedentePrestatoreCodiceFiscale()));
		param.add(new ParamWrapper("TipoIdentificativoMittente", "CF"));
		
		return param;
	}
	
	@Override
	protected String getTipologiaUnitaDocumentaria(UnitaDocumentariaFatturaInput input) {
		return "FATTURA PASSIVA";
	}

	public ChiaveType getChiave(UnitaDocumentariaFatturaInput input) {
		ChiaveType chiave = new ChiaveType();
		if(input.getFattura().getTipoDocumento().equals(TipoDocumentoType.TD04)) {
			// Dopo aver verificato che alcune note di credito generavano la stessa chiave della fattura, si e' deciso di prependere la stringa NC_ alla chiave in caso di note di credito 
			chiave.setNumero("NC_" + input.getFattura().getNumero() + "_" + input.getFattura().getCedentePrestatoreCodiceFiscale());
		} else {
			if(this.lstFattureConDataInChiave.contains(input.getFattura().getIdentificativoSdi())) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				chiave.setNumero(input.getFattura().getNumero() + "_" + input.getFattura().getCedentePrestatoreCodiceFiscale() + "_" + sdf.format(input.getFattura().getData()));
			} else {
				chiave.setNumero(input.getFattura().getNumero() + "_" + input.getFattura().getCedentePrestatoreCodiceFiscale());
			}
		}
		chiave.setAnno(input.getFattura().getAnno());
		chiave.setTipoRegistro("FATTURE PASSIVE");
		return chiave;
	}


	@Override
	protected ProfiloUnitaDocumentariaType getProfiloUnitaDocumentaria(UnitaDocumentariaFatturaInput input) throws Exception {
		ProfiloUnitaDocumentariaType profiloUnitaDocumentaria = new ProfiloUnitaDocumentariaType();
		
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);
		profiloUnitaDocumentaria.setOggetto("Fattura n. ["+input.getFattura().getNumero()+"] del ["+sdf.format(input.getFattura().getData())+"] - Mittente: ["+input.getFattura().getCedentePrestatoreDenominazione()+"]");
		profiloUnitaDocumentaria.setData(toXMLGC(input.getFattura().getData()));
		return profiloUnitaDocumentaria;

	}

	@Override
	protected Ente getEnte(UnitaDocumentariaFatturaInput input) throws Exception {
		return new CacheEnti(log).getEnte(input.getFattura().getCodiceDestinatario());
	}

}
