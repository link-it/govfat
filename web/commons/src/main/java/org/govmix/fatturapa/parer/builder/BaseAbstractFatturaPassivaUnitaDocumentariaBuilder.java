package org.govmix.fatturapa.parer.builder;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.tika.Tika;
import org.govmix.fatturapa.parer.beans.DocumentoWrapper;
import org.govmix.fatturapa.parer.beans.UnitaDocumentariaFatturaPassivaInput;
import org.govmix.fatturapa.parer.versamento.request.ComponenteType;
import org.govmix.fatturapa.parer.versamento.request.ConfigType;
import org.govmix.fatturapa.parer.versamento.request.DocumentoType;
import org.govmix.fatturapa.parer.versamento.request.StrutturaType;
import org.govmix.fatturapa.parer.versamento.request.TipoConservazioneType;
import org.govmix.fatturapa.parer.versamento.request.StrutturaType.Componenti;
import org.govmix.fatturapa.parer.versamento.request.TipoSupportoType;
import org.govmix.proxy.fatturapa.orm.AllegatoFattura;
import org.govmix.proxy.fatturapa.orm.NotificaEsitoCommittente;

public abstract class BaseAbstractFatturaPassivaUnitaDocumentariaBuilder extends AbstractUnitaDocumentariaBuilder<UnitaDocumentariaFatturaPassivaInput> {

	public BaseAbstractFatturaPassivaUnitaDocumentariaBuilder(Logger log) {
		super(log);
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
	
	@Override
	protected List<DocumentoWrapper> getAllegati(UnitaDocumentariaFatturaPassivaInput input) throws Exception {

		Tika tika = new Tika();

		List<DocumentoWrapper> allegatiLst = new ArrayList<DocumentoWrapper>();

		if(input.getAllegati() != null && !input.getAllegati().isEmpty()) {

			int index = 1;
			for(AllegatoFattura allegatoInput: input.getAllegati()) {
				if(allegatoInput.getAttachment() != null && allegatoInput.getAttachment().length > 0) {
					DocumentoType allegato = new DocumentoType();
					String idDocumentoAllegato = input.getFattura().getNomeFile() + "_ALL"+index;
					allegato.setIDDocumento(idDocumentoAllegato);
	
					allegato.setTipoDocumento("GENERICO");
	
					StrutturaType strutturaOriginale = new StrutturaType();
					Componenti componenti = new Componenti();
					ComponenteType componente = new ComponenteType();
					componente.setID(idDocumentoAllegato);
					componente.setOrdinePresentazione(index);
					componente.setNomeComponente(allegatoInput.getNomeAttachment());
					
					String formato = tika.detect(allegatoInput.getAttachment());
					componente.setFormatoFileVersato(formato);
	
					componente.setUtilizzoDataFirmaPerRifTemp(false);
					componente.setRiferimentoTemporale(toXMLGC(input.getFattura().getData()));
					componente.setDescrizioneRiferimentoTemporale("Data di firma");
					componenti.getComponente().add(componente);
	
					strutturaOriginale.setComponenti(componenti);
					allegato.setStrutturaOriginale(strutturaOriginale);
					DocumentoWrapper doc = new DocumentoWrapper();
					doc.setDoc(allegato);
					doc.setIndex(index++);
					doc.setRaw(allegatoInput.getAttachment());
					allegatiLst.add(doc);
				}
			}

		}
		
		if(!allegatiLst.isEmpty()) {
			return allegatiLst;
		} else {
			return null;
		}


	}
	
	protected String getVersioneDatiSpecifici(UnitaDocumentariaFatturaPassivaInput input) {
		return "1.0";
	}
	
	@Override
	protected ConfigType getConfigurazione(UnitaDocumentariaFatturaPassivaInput input) {
		ConfigType  config = new ConfigType();
		config.setTipoConservazione(TipoConservazioneType.FISCALE);
		config.setForzaAccettazione(true);
		config.setForzaConservazione(true);
		config.setForzaCollegamento(false);
		return config;

	}


}
