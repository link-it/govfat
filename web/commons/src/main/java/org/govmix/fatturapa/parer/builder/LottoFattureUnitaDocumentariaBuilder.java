package org.govmix.fatturapa.parer.builder;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.govmix.fatturapa.parer.beans.DocumentoWrapper;
import org.govmix.fatturapa.parer.beans.FatturaBean;
import org.govmix.fatturapa.parer.beans.ParamWrapper;
import org.govmix.fatturapa.parer.beans.UnitaDocumentariaLottoInput;
import org.govmix.fatturapa.parer.utils.CacheEnti;
import org.govmix.fatturapa.parer.versamento.request.ChiaveType;
import org.govmix.fatturapa.parer.versamento.request.ComponenteType;
import org.govmix.fatturapa.parer.versamento.request.ConfigType;
import org.govmix.fatturapa.parer.versamento.request.DocumentoCollegatoType;
import org.govmix.fatturapa.parer.versamento.request.DocumentoCollegatoType.DocumentoCollegato;
import org.govmix.fatturapa.parer.versamento.request.DocumentoType;
import org.govmix.fatturapa.parer.versamento.request.ProfiloUnitaDocumentariaType;
import org.govmix.fatturapa.parer.versamento.request.StrutturaType;
import org.govmix.fatturapa.parer.versamento.request.StrutturaType.Componenti;
import org.govmix.fatturapa.parer.versamento.request.TipoConservazioneType;
import org.govmix.fatturapa.parer.versamento.request.TipoSupportoType;
import org.govmix.proxy.fatturapa.orm.Ente;
import org.govmix.proxy.fatturapa.orm.constants.EsitoType;

public class LottoFattureUnitaDocumentariaBuilder extends AbstractUnitaDocumentariaBuilder<UnitaDocumentariaLottoInput> {

	public LottoFattureUnitaDocumentariaBuilder(Logger log) {
		super(log);
	}

	@Override
	protected DocumentoCollegatoType getDocumentiCollegati(
			UnitaDocumentariaLottoInput input) {

		DocumentoCollegatoType doc = new DocumentoCollegatoType();
		for(FatturaBean fatBean: input.getFattureLst()) {
			if(fatBean.getFattura().getEsito() == null || fatBean.getFattura().getEsito().equals(EsitoType.INVIATA_ACCETTATO)) {	
				DocumentoCollegato docCollegato = new DocumentoCollegato();
				ChiaveType chiave = new ChiaveType();
				chiave.setAnno(fatBean.getFattura().getAnno());
				chiave.setNumero(fatBean.getFattura().getNumero() + "_" + fatBean.getFattura().getCedentePrestatoreCodiceFiscale());
				chiave.setTipoRegistro("FATTURE PASSIVE");
				docCollegato.setChiaveCollegamento(chiave);
				docCollegato.setDescrizioneCollegamento("Appartenenza a Lotto");
				doc.getDocumentoCollegato().add(docCollegato);
			}
		}
		return doc;
		
	}

	@Override
	protected byte[] getRawDocumentoPrincipale(UnitaDocumentariaLottoInput input) {
		return input.getLotto().getXml();
	}

	@Override
	protected List<DocumentoWrapper> getAnnessi(UnitaDocumentariaLottoInput input) throws Exception {
		if(INCLUDE_METADATI) {
			List<DocumentoWrapper> annessiLst = new ArrayList<DocumentoWrapper>();
			DocumentoType annessoMetadati = new DocumentoType();
			String idDocumentoMetadati = input.getLotto().getIdentificativoSdi() + "-metadati";
			annessoMetadati.setIDDocumento(idDocumentoMetadati);
			
			annessoMetadati.setTipoDocumento("FILE DEI METADATI");
			StrutturaType strutturaOriginale = new StrutturaType();
			Componenti componenti = new Componenti();
			ComponenteType componente = new ComponenteType();
			componente.setID(idDocumentoMetadati);
			componente.setOrdinePresentazione(2);
			componente.setTipoComponente("Contenuto");
			componente.setTipoSupportoComponente(TipoSupportoType.FILE);
			componente.setNomeComponente(input.getLotto().getIdentificativoSdi() + "-metadati");
			componente.setFormatoFileVersato("XML");
			componente.setUtilizzoDataFirmaPerRifTemp(true);
			componenti.getComponente().add(componente);
			strutturaOriginale.setComponenti(componenti);
			annessoMetadati.setStrutturaOriginale(strutturaOriginale);
			DocumentoWrapper doc = new DocumentoWrapper();
			doc.setDoc(annessoMetadati);
			doc.setIndex(2);
			doc.setRaw(getXMLMetadati(input.getLotto()));
			annessiLst.add(doc);
			return annessiLst;
		} else {
			return null;
		}
	}

	@Override
	protected List<DocumentoWrapper> getAllegati(
			UnitaDocumentariaLottoInput input) {
		return null;
	}

	@Override
	protected DocumentoType getDocumentoPrincipale(
			UnitaDocumentariaLottoInput input) {
		DocumentoType documentoPricipale = new DocumentoType();
		String idDocumentoLotto = input.getLotto().getIdentificativoSdi() + "";
		documentoPricipale.setIDDocumento(idDocumentoLotto);
		
		documentoPricipale.setTipoDocumento("LOTTO DI FATTURE");
		
		StrutturaType strutturaOriginaleDocumentoPrincipale = new StrutturaType();
		Componenti componentiDocumentoPrincipale = new Componenti();
		ComponenteType componenteDocumentoPrincipale = new ComponenteType();
		componenteDocumentoPrincipale.setID(idDocumentoLotto);
		componenteDocumentoPrincipale.setOrdinePresentazione(1);
		componenteDocumentoPrincipale.setTipoComponente("Contenuto");
		componenteDocumentoPrincipale.setTipoSupportoComponente(TipoSupportoType.FILE);
		componenteDocumentoPrincipale.setNomeComponente(input.getLotto().getNomeFile());
		componenteDocumentoPrincipale.setFormatoFileVersato("XML.P7M");
		componenteDocumentoPrincipale.setUtilizzoDataFirmaPerRifTemp(true);
		componentiDocumentoPrincipale.getComponente().add(componenteDocumentoPrincipale);
		
		strutturaOriginaleDocumentoPrincipale.setComponenti(componentiDocumentoPrincipale);
		documentoPricipale.setStrutturaOriginale(strutturaOriginaleDocumentoPrincipale);
		return documentoPricipale;

	}

	@Override
	protected List<ParamWrapper> getParams(UnitaDocumentariaLottoInput input) {
		
		List<ParamWrapper> params = new ArrayList<ParamWrapper>();
		if(input.getLotto().getCedentePrestatoreDenominazione() != null) {
			params.add(new ParamWrapper("DenominazioneMittente", input.getLotto().getCedentePrestatoreDenominazione()));
			params.add(new ParamWrapper("TipoDenominazioneMittente", "RagioneSociale"));
		} else {
			params.add(new ParamWrapper("DenominazioneMittente", input.getLotto().getCedentePrestatoreNome() + " " + input.getLotto().getCedentePrestatoreCognome()));
			params.add(new ParamWrapper("TipoDenominazioneMittente", "NomeCognome"));
		}
		
		
		if(input.getLotto().getCedentePrestatoreCodiceFiscale() != null) {
			params.add(new ParamWrapper("IdentificativoMittente", input.getLotto().getCedentePrestatoreCodiceFiscale()));
			params.add(new ParamWrapper("TipoIdentificativoMittente", "CF"));
		} else {
			params.add(new ParamWrapper("IdentificativoMittente", input.getLotto().getCedentePrestatoreCodice()));
			params.add(new ParamWrapper("TipoIdentificativoMittente", "PIVA"));
		}
		
		return params;

	}

	@Override
	protected ProfiloUnitaDocumentariaType getProfiloUnitaDocumentaria(UnitaDocumentariaLottoInput input) throws Exception {
		ProfiloUnitaDocumentariaType profiloUnitaDocumentaria = new ProfiloUnitaDocumentariaType();
		
		profiloUnitaDocumentaria.setOggetto("Lotto contenente "+input.getFattureLst().size()+" fatture - Mittente: ["+input.getLotto().getCessionarioCommittenteDenominazione()+"]");
		profiloUnitaDocumentaria.setData(toXMLGC(input.getLotto().getDataRicezione()));
		return profiloUnitaDocumentaria;

	}

	
	@Override
	protected String getTipologiaUnitaDocumentaria(UnitaDocumentariaLottoInput input) {
		return "LOTTO DI FATTURE";
	}
	
	@Override
	protected Ente getEnte(UnitaDocumentariaLottoInput input) throws Exception {
		return new CacheEnti(log).getEnte(input.getLotto().getCodiceDestinatario());
	}

	public ChiaveType getChiave(UnitaDocumentariaLottoInput input) {
		ChiaveType chiave = new ChiaveType();
		chiave.setNumero(""+input.getLotto().getIdentificativoSdi());
		chiave.setAnno(input.getFattureLst().get(0).getFattura().getAnno());
		chiave.setTipoRegistro("LOTTI_FATTURE");
		return chiave;
	}

	@Override
	protected ConfigType getConfigurazione(UnitaDocumentariaLottoInput input) {
		ConfigType  config = new ConfigType();
		config.setTipoConservazione(TipoConservazioneType.FISCALE);
		config.setForzaAccettazione(true);
		config.setForzaConservazione(false);
		config.setForzaCollegamento(true);
		return config;

	}
	
	
	protected String getVersioneDatiSpecifici(UnitaDocumentariaLottoInput input) {
		return "1.0";
	}


}
