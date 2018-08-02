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

public abstract class AbstractFatturaAttivaUnitaDocumentariaBuilder extends AbstractFatturaUnitaDocumentariaBuilder{

	public AbstractFatturaAttivaUnitaDocumentariaBuilder(Logger log) {
		super(log);
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
		param.add(new ParamWrapper("DenominazioneDestinatario", input.getFattura().getCessionarioCommittenteDenominazione()));
		param.add(new ParamWrapper("TipoDenominazioneDestinatario", "RagioneSociale"));
		param.add(new ParamWrapper("IdentificativoDestinatario", input.getFattura().getCessionarioCommittenteCodiceFiscale()));
		param.add(new ParamWrapper("TipoIdentificativoDestinatario", "CF"));
		return param;
	}

	@Override
	protected String getTipologiaUnitaDocumentaria(UnitaDocumentariaFatturaInput input) {
		return "FATTURA ATTIVA";
	}

	public ChiaveType getChiave(UnitaDocumentariaFatturaInput input) {
		ChiaveType chiave = new ChiaveType();
		chiave.setNumero(input.getFattura().getNumero());
		chiave.setAnno(input.getFattura().getAnno());
		chiave.setTipoRegistro("FATTURE ATTIVE");
		return chiave;
	}

	@Override
	protected ProfiloUnitaDocumentariaType getProfiloUnitaDocumentaria(UnitaDocumentariaFatturaInput input) throws Exception {
		ProfiloUnitaDocumentariaType profiloUnitaDocumentaria = new ProfiloUnitaDocumentariaType();
		
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);
		profiloUnitaDocumentaria.setOggetto("Fattura n. ["+input.getFattura().getNumero()+"] del ["+sdf.format(input.getFattura().getData())+"] - Destinatario: ["+input.getFattura().getCessionarioCommittenteDenominazione()+"]");
		profiloUnitaDocumentaria.setData(toXMLGC(input.getFattura().getData()));
		return profiloUnitaDocumentaria;

	}

	@Override
	protected Ente getEnte(UnitaDocumentariaFatturaInput input) throws Exception {
		return new CacheEnti(log).getEnte(input.getFattura().getCodiceDestinatario());
	}
}
