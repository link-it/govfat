package org.govmix.fatturapa.parer.builder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.govmix.fatturapa.parer.beans.DocumentoWrapper;
import org.govmix.fatturapa.parer.beans.ParamWrapper;
import org.govmix.fatturapa.parer.beans.UnitaDocumentariaFatturaPassivaInput;
import org.govmix.fatturapa.parer.utils.CacheEnti;
import org.govmix.fatturapa.parer.versamento.request.ChiaveType;
import org.govmix.fatturapa.parer.versamento.request.ComponenteType;
import org.govmix.fatturapa.parer.versamento.request.DocumentoType;
import org.govmix.fatturapa.parer.versamento.request.ProfiloUnitaDocumentariaType;
import org.govmix.fatturapa.parer.versamento.request.StrutturaType;
import org.govmix.fatturapa.parer.versamento.request.TipoSupportoType;
import org.govmix.fatturapa.parer.versamento.request.StrutturaType.Componenti;
import org.govmix.proxy.fatturapa.orm.Ente;
import org.govmix.proxy.fatturapa.orm.NotificaEsitoCommittente;
import org.govmix.proxy.fatturapa.orm.constants.StatoConsegnaType;
import org.govmix.proxy.fatturapa.orm.constants.TipoDocumentoType;

public abstract class AbstractFatturaPassivaUnitaDocumentariaBuilder extends BaseAbstractFatturaPassivaUnitaDocumentariaBuilder{

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
	protected List<ParamWrapper> getParams(UnitaDocumentariaFatturaPassivaInput input) {
		
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
	protected String getTipologiaUnitaDocumentaria(UnitaDocumentariaFatturaPassivaInput input) {
		return "FATTURA PASSIVA";
	}

	public ChiaveType getChiave(UnitaDocumentariaFatturaPassivaInput input) {
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
	protected ProfiloUnitaDocumentariaType getProfiloUnitaDocumentaria(UnitaDocumentariaFatturaPassivaInput input) throws Exception {
		ProfiloUnitaDocumentariaType profiloUnitaDocumentaria = new ProfiloUnitaDocumentariaType();
		
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);
		profiloUnitaDocumentaria.setOggetto("Fattura n. ["+input.getFattura().getNumero()+"] del ["+sdf.format(input.getFattura().getData())+"] - Mittente: ["+input.getFattura().getCedentePrestatoreDenominazione()+"]");
		profiloUnitaDocumentaria.setData(toXMLGC(input.getFattura().getData()));
		return profiloUnitaDocumentaria;

	}

	@Override
	protected Ente getEnte(UnitaDocumentariaFatturaPassivaInput input) throws Exception {
		return new CacheEnti(log).getEnte(input.getFattura().getCodiceDestinatario());
	}

	protected String getTipoDocumento(UnitaDocumentariaFatturaPassivaInput input) {
		switch(input.getFattura().getTipoDocumento()){
		case TD01: return "FATTURA";
		case TD02:return "ACCONTO/ANTICIPO SU FATTURA";
		case TD03:return "ACCONTO/ANTICIPO SU PARCELLA";
		case TD04:return "NOTA DI CREDITO";
		case TD05:return "NOTA DI DEBITO";
		case TD06:return "PARCELLA";
		case TDXX:return "SCONOSCIUTO";
		default:return null;
		}
	}
	
	@Override
	protected List<DocumentoWrapper> getAnnessi(UnitaDocumentariaFatturaPassivaInput input) throws Exception {


		List<DocumentoWrapper> annessiLst = new ArrayList<DocumentoWrapper>();

		int index = 1;


		if(input.getNotificaEC() != null && !input.getNotificaEC().isEmpty()) {

			for(NotificaEsitoCommittente nec: input.getNotificaEC()) {
				if(nec.getXml() != null) {
					DocumentoType annessoNEC = new DocumentoType();
					String idDocumentoNotificaEC = 	nec.getIdFattura().getIdentificativoSdi() + "_" +nec.getIdFattura().getPosizione() + "_EC"+index;
					annessoNEC.setIDDocumento(idDocumentoNotificaEC);

					annessoNEC.setTipoDocumento("NOTIFICA DI ESITO COMMITTENTE");
					StrutturaType strutturaOriginale = new StrutturaType();
					Componenti componenti = new Componenti();
					ComponenteType componente = new ComponenteType();
					componente.setID(idDocumentoNotificaEC);
					componente.setOrdinePresentazione(2);
					componente.setTipoComponente("Contenuto");
					componente.setTipoSupportoComponente(TipoSupportoType.FILE);
					componente.setNomeComponente(nec.getNomeFile());
					componente.setFormatoFileVersato("XML");
					componente.setUtilizzoDataFirmaPerRifTemp(true);
					componenti.getComponente().add(componente);
					strutturaOriginale.setComponenti(componenti);
					annessoNEC.setStrutturaOriginale(strutturaOriginale);

					DocumentoWrapper docEC = new DocumentoWrapper();
					docEC.setDoc(annessoNEC);
					docEC.setIndex(index++);
					docEC.setRaw(nec.getXml());
					annessiLst.add(docEC);

					//SCARTO
					if(nec.getScartoXml() != null) {
						DocumentoType annessoSEC = new DocumentoType();
						String idDocumentoScarto = 	nec.getIdFattura().getIdentificativoSdi() + "_" +nec.getIdFattura().getPosizione() + "_EC_SC"+(index-1);
						annessoSEC.setIDDocumento(idDocumentoScarto);

						annessoSEC.setTipoDocumento("SCARTO ESITO COMMITTENTE");
						StrutturaType strutturaOriginaleSEC = new StrutturaType();
						Componenti componentiSEC = new Componenti();
						ComponenteType componenteSEC = new ComponenteType();
						componenteSEC.setID(idDocumentoScarto);
						componenteSEC.setOrdinePresentazione(annessiLst.size()+2);
						componenteSEC.setTipoComponente("Contenuto");
						componenteSEC.setTipoSupportoComponente(TipoSupportoType.FILE);
						componenteSEC.setNomeComponente(nec.getNomeFile());
						componenteSEC.setFormatoFileVersato("XML");
						componenteSEC.setUtilizzoDataFirmaPerRifTemp(true);
						componentiSEC.getComponente().add(componenteSEC);
						strutturaOriginaleSEC.setComponenti(componentiSEC);
						annessoSEC.setStrutturaOriginale(strutturaOriginaleSEC);

						DocumentoWrapper docSEC = new DocumentoWrapper();
						docSEC.setDoc(annessoSEC);
						docSEC.setIndex(index++);
						docSEC.setRaw(nec.getScartoXml());
						annessiLst.add(docSEC);

					}

				}
			}
		}
		if(input.getNotificaDT() != null) {
			DocumentoType annessoNDT = new DocumentoType();
			String idDocumentoNotificaDT = input.getNotificaDT().getIdentificativoSdi() + "_DT"; 
			annessoNDT.setIDDocumento(idDocumentoNotificaDT);

			annessoNDT.setTipoDocumento("NOTIFICA DI DECORRENZA DEI TERMINI");
			StrutturaType strutturaOriginale = new StrutturaType();
			Componenti componenti = new Componenti();
			ComponenteType componente = new ComponenteType();
			componente.setID(idDocumentoNotificaDT);
			componente.setOrdinePresentazione(annessiLst.size()+2);
			componente.setTipoComponente("Contenuto");
			componente.setTipoSupportoComponente(TipoSupportoType.FILE);
			componente.setNomeComponente(input.getNotificaDT().getNomeFile());
			componente.setFormatoFileVersato("XML");
			componente.setUtilizzoDataFirmaPerRifTemp(true);
			componenti.getComponente().add(componente);
			strutturaOriginale.setComponenti(componenti);
			annessoNDT.setStrutturaOriginale(strutturaOriginale);

			DocumentoWrapper docDT = new DocumentoWrapper();
			docDT.setDoc(annessoNDT);
			docDT.setIndex(index++);
			docDT.setRaw(input.getNotificaDT().getXml());
			annessiLst.add(docDT);

		}

		if(annessiLst.size() > 0)
			return annessiLst;
		else 
			return null;
	}
}
