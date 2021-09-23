package org.govmix.fatturapa.parer.builder;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.tika.Tika;
import org.govmix.fatturapa.parer.beans.DocumentoWrapper;
import org.govmix.fatturapa.parer.beans.UnitaDocumentariaFatturaAttivaInput;
import org.govmix.fatturapa.parer.versamento.request.ComponenteType;
import org.govmix.fatturapa.parer.versamento.request.ConfigType;
import org.govmix.fatturapa.parer.versamento.request.DocumentoType;
import org.govmix.fatturapa.parer.versamento.request.StrutturaType;
import org.govmix.fatturapa.parer.versamento.request.StrutturaType.Componenti;
import org.govmix.fatturapa.parer.versamento.request.TipoConservazioneType;
import org.govmix.fatturapa.parer.versamento.request.TipoSupportoType;
import org.govmix.proxy.fatturapa.orm.AllegatoFattura;
import org.govmix.proxy.fatturapa.orm.TracciaSDI;
import org.govmix.proxy.fatturapa.orm.constants.DominioType;
import org.govmix.proxy.fatturapa.orm.constants.TipoComunicazioneType;

public abstract class BaseAbstractFatturaAttivaUnitaDocumentariaBuilder extends AbstractUnitaDocumentariaBuilder<UnitaDocumentariaFatturaAttivaInput> {

	public BaseAbstractFatturaAttivaUnitaDocumentariaBuilder(Logger log) {
		super(log);
	}

	protected String getTipoDocumento(UnitaDocumentariaFatturaAttivaInput input) {
		switch(input.getFattura().getTipoDocumento()){
		case TD01: return "FATTURA";
		case TD02: return "ACCONTO/ANTICIPO SU FATTURA";
		case TD03: return "ACCONTO/ANTICIPO SU PARCELLA";
		case TD04: return "NOTA DI CREDITO";
		case TD05: return "NOTA DI DEBITO";
		case TD06: return "PARCELLA";
		case TD16: return "INTEGRAZIONE FATTURA REVERSE CHARGE INTERNO";
		case TD17: return "INTEGRAZIONE/AUTOFATTURA PER ACQUISTO SERVIZI DALL'ESTERO";
		case TD18: return "INTEGRAZIONE PER ACQUISTO DI BENI INTRACOMUNITARI";
		case TD19: return "INTEGRAZIONE/AUTOFATTURA PER ACQUISTO DI BENI EX ART.17";
		case TD20: return "AUTOFATTURA";
		case TD21: return "AUTOFATTURA PER SPLAFONAMENTO";
		case TD22: return "ESTRAZIONE BENI DA DEPOSITO IVA";
		case TD23: return "ESTRAZIONE BENI DA DEPOSITO IVA CON VERSAMENTO DELL'IVA";
		case TD24: return "FATTURA DIFFERITA DI CUI ALL'ART. 21 4A)";
		case TD25: return "FATTURA DIFFERITA DI CUI ALL'ART. 21 4B)";
		case TD26: return "CESSIONE DI BENI AMMORTIZZABILI E PER PASSAGGI INTERNI";
		case TD27: return "FATTURA PER AUTOCONSUMO/CESSIONI GRATUITE";
		case TDXX:
			break;
		}

		return null;
	}
	
	
	@Override
	protected List<DocumentoWrapper> getAnnessi(UnitaDocumentariaFatturaAttivaInput input) throws Exception {
		List<DocumentoWrapper> annessiLst = new ArrayList<DocumentoWrapper>();
		
		if(input.getTracce() != null && !input.getTracce().isEmpty()) {
			int index = 1;
			for(TracciaSDI traccia: input.getTracce()) {
				boolean add = false;

				String tipo = null;
				if(traccia.getTipoComunicazione().equals(TipoComunicazioneType.AT)) {
					tipo = "ZIP";
				} else {
					tipo = "XML";
				}
				
				switch (traccia.getTipoComunicazione()) {
				case NS:
				case RC:
				case MC:
				case NE:
				case AT:
				case DT:
					add= true;
					break;
				
				case EC:
				case FAT_IN:
				case FAT_OUT:
				case MT:
				case SE:
				default:
					break;
				}
				
				if(add) {
					DocumentoType annesso = new DocumentoType();
					StringBuilder sb = new StringBuilder();
					sb.append(traccia.getIdentificativoSdi()).append("_");
					
					if(traccia.getPosizione() != null)
						sb.append(traccia.getPosizione()).append("_");
					
					sb.append(traccia.get_value_tipoComunicazione()).append(index);
					
					String idDocumentoAnnesso = sb.toString();
					
					annesso.setIDDocumento(idDocumentoAnnesso);
					annesso.setTipoDocumento(this.getTipoComunicazione(traccia.getTipoComunicazione()));
					StrutturaType strutturaOriginale = new StrutturaType();
					Componenti componenti = new Componenti();
					ComponenteType componente = new ComponenteType();
					componente.setID(idDocumentoAnnesso);
					componente.setOrdinePresentazione(index);
					componente.setTipoComponente("Contenuto");
					componente.setTipoSupportoComponente(TipoSupportoType.FILE);
					componente.setNomeComponente(traccia.getNomeFile());
					componente.setFormatoFileVersato(tipo);
					componente.setUtilizzoDataFirmaPerRifTemp(true);
					componenti.getComponente().add(componente);
					strutturaOriginale.setComponenti(componenti);
					annesso.setStrutturaOriginale(strutturaOriginale);

					DocumentoWrapper docEC = new DocumentoWrapper();
					docEC.setDoc(annesso);
					docEC.setIndex(index++);
					docEC.setRaw(traccia.getRawData());
					annessiLst.add(docEC);
				}
			}
		}

		if(annessiLst.size() > 0)
			return annessiLst;
		else 
			return null;
	}
	
	private String getTipoComunicazione(TipoComunicazioneType tipoComunicazioneType) {
		switch (tipoComunicazioneType) {
		case NS:
			return "NOTIFICA DI SCARTO";
		case RC:
			return "RICEVUTA DI CONSEGNA";
		case MC:
			return "NOTIFICA DI MANCATA CONSEGNA";
		case NE:
			return "NOTIFICA DI ESITO";
		case AT:
			return "ATTESTAZIONE IMPOSSIBILITA' DI RECAPITO";
		case DT:
			return "NOTIFICA DI DECORRENZA DEI TERMINI";
		
		case EC:
		case FAT_IN:
		case FAT_OUT:
		case MT:
		case SE:
		default:
			return null;
		}
	}

	@Override
	protected List<DocumentoWrapper> getAllegati(UnitaDocumentariaFatturaAttivaInput input) throws Exception {

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
//					componente.setTipoRappresentazioneComponente("Allegato generico alla fattura");
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
	
	protected String getVersioneDatiSpecifici(UnitaDocumentariaFatturaAttivaInput input) {
		return "2.0";
	}

	@Override
	protected ConfigType getConfigurazione(UnitaDocumentariaFatturaAttivaInput input) {
		ConfigType  config = new ConfigType();
		config.setTipoConservazione(TipoConservazioneType.FISCALE);
		config.setForzaAccettazione(true);
		config.setForzaConservazione(input.getLotto().getDominio().equals(DominioType.B2B));
		config.setForzaCollegamento(false);
		return config;

	}

//	private String getFormato(String nomeAttachment, String formatoAttachment, String formatoCompressione) {
//		
//		if(formatoCompressione != null) {
//			return formatoCompressione.replace(".", "");
//		}
//		
//		if(formatoAttachment != null) {
//			return formatoAttachment.replace(".", "");
//		}
//		
//		int idx = nomeAttachment.lastIndexOf('.');
//		String ext = nomeAttachment.substring(idx + 1);
//		
//		if("p7m".equalsIgnoreCase(ext)) {
//			
//			int idxReal = nomeAttachment.substring(0, idx).lastIndexOf('.');
//			return nomeAttachment.substring(idxReal + 1);
//		} else {
//			return ext;
//		}
//
//	}
//	

}
