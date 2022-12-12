package org.govmix.fatturapa.parer.builder;

import java.util.List;

import org.apache.log4j.Logger;
import org.govmix.fatturapa.parer.beans.DocumentoWrapper;
import org.govmix.fatturapa.parer.beans.UnitaDocumentariaFatturaPassivaInput;
import org.govmix.fatturapa.parer.versamento.request.ComponenteType;
import org.govmix.fatturapa.parer.versamento.request.DocumentoCollegatoType;
import org.govmix.fatturapa.parer.versamento.request.DocumentoType;
import org.govmix.fatturapa.parer.versamento.request.StrutturaType;
import org.govmix.fatturapa.parer.versamento.request.StrutturaType.Componenti;
import org.govmix.fatturapa.parer.versamento.request.TipoSupportoType;

public class FatturaPassivaSingolaUnitaDocumentariaBuilder extends
		AbstractFatturaPassivaUnitaDocumentariaBuilder {

	public FatturaPassivaSingolaUnitaDocumentariaBuilder(Logger log) {
		super(log);
	}

	@Override
	protected DocumentoCollegatoType getDocumentiCollegati(UnitaDocumentariaFatturaPassivaInput input) {
		return null;
	}

	@Override
	protected byte[] getRawDocumentoPrincipale(UnitaDocumentariaFatturaPassivaInput input) {
		return input.getLotto().getXml();
	}

	@Override
	protected DocumentoType getDocumentoPrincipale(UnitaDocumentariaFatturaPassivaInput input) {

		DocumentoType documentoPricipale = new DocumentoType();
		String idDocumento = input.getFattura().getId() + getSuffixFattura();
		documentoPricipale.setIDDocumento(idDocumento); //OK
		
		documentoPricipale.setTipoDocumento(this.getTipoDocumento(input));		
		StrutturaType strutturaOriginaleDocumentoPrincipale = new StrutturaType();
		Componenti componentiDocumentoPrincipale = new Componenti();
		ComponenteType componenteDocumentoPrincipale = new ComponenteType();
		componenteDocumentoPrincipale.setID(idDocumento);
		componenteDocumentoPrincipale.setTipoComponente("Contenuto");
		componenteDocumentoPrincipale.setOrdinePresentazione(1);
		componenteDocumentoPrincipale.setNomeComponente(input.getFattura().getNomeFile());
		
		switch(input.getLotto().getFormatoArchivioInvioFattura()) {
		case P7M: componenteDocumentoPrincipale.setFormatoFileVersato("XML.P7M");
			break; 
		case XML:  componenteDocumentoPrincipale.setFormatoFileVersato("XML");
			break;
		default:
			break;
		
		}

		componenteDocumentoPrincipale.setUtilizzoDataFirmaPerRifTemp(true);
		componentiDocumentoPrincipale.getComponente().add(componenteDocumentoPrincipale);

		strutturaOriginaleDocumentoPrincipale.setComponenti(componentiDocumentoPrincipale);
		documentoPricipale.setStrutturaOriginale(strutturaOriginaleDocumentoPrincipale);
		return documentoPricipale;
	}
	
	@Override
	protected List<DocumentoWrapper> getAnnessi(UnitaDocumentariaFatturaPassivaInput input) throws Exception {
		List<DocumentoWrapper> annessi = super.getAnnessi(input);
		
		if(INCLUDE_METADATI) {
			DocumentoType annessoMetadati = new DocumentoType();
			String idDocumentoMetadati = input.getLotto().getId() + getSuffixMetadati();
			annessoMetadati.setIDDocumento(idDocumentoMetadati); //OK
			
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
			
			DocumentoWrapper annessoMetadatiDoc = new DocumentoWrapper();
			annessoMetadatiDoc.setDoc(annessoMetadati);
			annessoMetadatiDoc.setIndex(2);
			annessoMetadatiDoc.setRaw(this.getXMLMetadati(input.getLotto()));
			annessi.add(annessoMetadatiDoc);
		}
		
		return annessi;
	}
}
