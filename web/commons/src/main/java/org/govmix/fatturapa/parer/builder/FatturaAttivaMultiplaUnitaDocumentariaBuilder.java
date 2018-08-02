package org.govmix.fatturapa.parer.builder;

import org.govmix.fatturapa.parer.beans.UnitaDocumentariaFatturaInput;
import org.govmix.fatturapa.parer.versamento.request.ChiaveType;
import org.govmix.fatturapa.parer.versamento.request.ComponenteType;
import org.govmix.fatturapa.parer.versamento.request.ConfigType;
import org.govmix.fatturapa.parer.versamento.request.DocumentoCollegatoType;
import org.govmix.fatturapa.parer.versamento.request.DocumentoCollegatoType.DocumentoCollegato;
import org.govmix.fatturapa.parer.versamento.request.DocumentoType;
import org.govmix.fatturapa.parer.versamento.request.StrutturaType;
import org.govmix.fatturapa.parer.versamento.request.StrutturaType.Componenti;
import org.govmix.fatturapa.parer.versamento.request.TipoConservazioneType;

import org.apache.log4j.Logger;

public class FatturaAttivaMultiplaUnitaDocumentariaBuilder extends
		AbstractFatturaAttivaUnitaDocumentariaBuilder {

	public FatturaAttivaMultiplaUnitaDocumentariaBuilder(Logger log) {
		super(log);
	}

	@Override
	protected DocumentoCollegatoType getDocumentiCollegati(
			UnitaDocumentariaFatturaInput input) {
		DocumentoCollegatoType documentiCollegati = new DocumentoCollegatoType();
		DocumentoCollegato collegamentoALotto = new DocumentoCollegato();
		
		ChiaveType chiaveCollegamentoALotto = new ChiaveType();
		chiaveCollegamentoALotto.setAnno(input.getFattura().getAnno());
		chiaveCollegamentoALotto.setNumero(""+input.getLotto().getIdentificativoSdi());
		chiaveCollegamentoALotto.setTipoRegistro("LOTTI_FATTURE");
		collegamentoALotto.setChiaveCollegamento(chiaveCollegamentoALotto);
		collegamentoALotto.setDescrizioneCollegamento("Appartenenza a Lotto");
		documentiCollegati.getDocumentoCollegato().add(collegamentoALotto);
		return documentiCollegati;
	}

	@Override
	protected byte[] getRawDocumentoPrincipale(
			UnitaDocumentariaFatturaInput input) {
		return input.getFattura().getXml();
	}

	@Override
	protected DocumentoType getDocumentoPrincipale(UnitaDocumentariaFatturaInput input) {

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
	protected ConfigType getConfigurazione(UnitaDocumentariaFatturaInput input) {
		ConfigType  config = new ConfigType();
		config.setTipoConservazione(TipoConservazioneType.FISCALE);
		config.setForzaAccettazione(true);
		config.setForzaConservazione(true);
		config.setForzaCollegamento(false);
		return config;

	}

}
