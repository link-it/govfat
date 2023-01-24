package org.govmix.fatturapa.parer.builder;

import org.apache.log4j.Logger;
import org.govmix.fatturapa.parer.beans.UnitaDocumentariaFatturaAttivaInput;
import org.govmix.fatturapa.parer.versamento.request.ChiaveType;
import org.govmix.fatturapa.parer.versamento.request.ComponenteType;
import org.govmix.fatturapa.parer.versamento.request.ConfigType;
import org.govmix.fatturapa.parer.versamento.request.DocumentoCollegatoType;
import org.govmix.fatturapa.parer.versamento.request.DocumentoCollegatoType.DocumentoCollegato;
import org.govmix.fatturapa.parer.versamento.request.DocumentoType;
import org.govmix.fatturapa.parer.versamento.request.StrutturaType;
import org.govmix.fatturapa.parer.versamento.request.StrutturaType.Componenti;
import org.govmix.fatturapa.parer.versamento.request.TipoConservazioneType;

public class FatturaAttivaMultiplaUnitaDocumentariaBuilder extends
		AbstractFatturaAttivaUnitaDocumentariaBuilder {

	public FatturaAttivaMultiplaUnitaDocumentariaBuilder(Logger log) {
		super(log);
	}

	@Override
	protected DocumentoCollegatoType getDocumentiCollegati(
			UnitaDocumentariaFatturaAttivaInput input) {
//		DocumentoCollegatoType documentiCollegati = new DocumentoCollegatoType();
//		DocumentoCollegato collegamentoALotto = new DocumentoCollegato();
//		
//		ChiaveType chiaveCollegamentoALotto = new ChiaveType();
//		chiaveCollegamentoALotto.setAnno(input.getFattura().getAnno());
//		chiaveCollegamentoALotto.setNumero(""+input.getLotto().getIdentificativoSdi());
//		chiaveCollegamentoALotto.setTipoRegistro("LOTTI_FATTURE");
//		collegamentoALotto.setChiaveCollegamento(chiaveCollegamentoALotto);
//		collegamentoALotto.setDescrizioneCollegamento("Appartenenza a Lotto");
//		documentiCollegati.getDocumentoCollegato().add(collegamentoALotto);
//		return documentiCollegati;
		return null; //11-10-2018 non si aggiunge piu' il collegamento da fattura a lotto
	}

	@Override
	protected byte[] getRawDocumentoPrincipale(
			UnitaDocumentariaFatturaAttivaInput input) {
		return input.getFattura().getXml();
	}

	@Override
	protected DocumentoType getDocumentoPrincipale(UnitaDocumentariaFatturaAttivaInput input) throws Exception {

		DocumentoType documentoPricipale = new DocumentoType();
		String idDocumento = input.getFattura().getNumero();
		documentoPricipale.setIDDocumento(idDocumento);

		documentoPricipale.setTipoDocumento(this.getTipoDocumento(input));		
		StrutturaType strutturaOriginaleDocumentoPrincipale = new StrutturaType();
		Componenti componentiDocumentoPrincipale = new Componenti();
		ComponenteType componenteDocumentoPrincipale = new ComponenteType();
		componenteDocumentoPrincipale.setID(idDocumento);
		componenteDocumentoPrincipale.setTipoComponente("Contenuto");
		componenteDocumentoPrincipale.setOrdinePresentazione(1);
		componenteDocumentoPrincipale.setNomeComponente(input.getFattura().getNomeFile() + "_"+input.getFattura().getPosizione());
		componenteDocumentoPrincipale.setUtilizzoDataFirmaPerRifTemp(true);

		componenteDocumentoPrincipale.setFormatoFileVersato("XML");
		componentiDocumentoPrincipale.getComponente().add(componenteDocumentoPrincipale);
		
		strutturaOriginaleDocumentoPrincipale.setComponenti(componentiDocumentoPrincipale);
		documentoPricipale.setStrutturaOriginale(strutturaOriginaleDocumentoPrincipale);
		return documentoPricipale;
		
	}

	@Override
	protected ConfigType getConfigurazione(UnitaDocumentariaFatturaAttivaInput input) {
		ConfigType  config = new ConfigType();
		config.setTipoConservazione(TipoConservazioneType.FISCALE);
		config.setForzaAccettazione(true);
		config.setForzaConservazione(true);
		config.setForzaCollegamento(false);
		return config;

	}

}
