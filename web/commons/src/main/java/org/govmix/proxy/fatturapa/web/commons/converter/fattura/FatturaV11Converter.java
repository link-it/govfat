/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2019 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2019 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 3, as published by
 * the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.govmix.proxy.fatturapa.web.commons.converter.fattura;

import java.util.ArrayList;
import java.util.List;

import org.govmix.proxy.fatturapa.orm.AllegatoFattura;
import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.web.commons.consegnaFattura.ConsegnaFatturaParameters;
import org.openspcoop2.generic_project.exception.DeserializerException;
import org.openspcoop2.generic_project.exception.ValidationException;

import it.gov.fatturapa.sdi.fatturapa.v1_1.AllegatiType;
import it.gov.fatturapa.sdi.fatturapa.v1_1.DatiGeneraliDocumentoType;
import it.gov.fatturapa.sdi.fatturapa.v1_1.DatiRiepilogoType;
import it.gov.fatturapa.sdi.fatturapa.v1_1.FatturaElettronicaBodyType;
import it.gov.fatturapa.sdi.fatturapa.v1_1.FatturaElettronicaType;
import it.gov.fatturapa.sdi.fatturapa.v1_1.utils.serializer.JaxbDeserializer;

public class FatturaV11Converter extends AbstractFatturaConverter<FatturaElettronicaType> {

		private static JaxbDeserializer deserializer;
		
		static {
			deserializer = new JaxbDeserializer();
		}
		
		private static FatturaElettronicaType readFatturaElettronicaType(byte[] fatturaBytes) throws ValidationException {
			try {
				return deserializer.readFatturaElettronicaType(fatturaBytes);
			} catch(DeserializerException e) {
				throw new ValidationException(e);
			}
		}

		
		public FatturaV11Converter(byte[] fattura, ConsegnaFatturaParameters params) throws ValidationException {
			super(FatturaV11Converter.readFatturaElettronicaType(fattura), fattura, params);
		}
		

		@Override
		public void populateFatturaConDatiSpecifici(FatturaElettronica fatturaElettronica) {

			DatiGeneraliDocumentoType datiGeneraliDocumento =  this.getFattura().getFatturaElettronicaBody(0).getDatiGenerali().getDatiGeneraliDocumento();
			
			fatturaElettronica.setDivisa(datiGeneraliDocumento.getDivisa());

			fatturaElettronica.setData(datiGeneraliDocumento.getData());
			fatturaElettronica.setAnno(new Integer(this.getSdfYear().format(datiGeneraliDocumento.getData())));

			fatturaElettronica.setNumero(datiGeneraliDocumento.getNumero());
		}
		
		

		@Override
		public List<String> getCausali() {
			FatturaElettronicaBodyType fatturaBody = getFattura().getFatturaElettronicaBody(0);
			
			List<String> lst = new ArrayList<String>();
			
			if(fatturaBody.getDatiGenerali() != null && 
					fatturaBody.getDatiGenerali().getDatiGeneraliDocumento() != null &&
					fatturaBody.getDatiGenerali().getDatiGeneraliDocumento().getCausaleList() != null) {
				return fatturaBody.getDatiGenerali().getDatiGeneraliDocumento().getCausaleList();
			}
			
			return lst;
		}
		@Override
		public List<AllegatoFattura> getAllegati() {
			
			FatturaElettronicaBodyType fatturaBody = getFattura().getFatturaElettronicaBody(0);

			List<AllegatoFattura> lst = new ArrayList<AllegatoFattura>();
			if(fatturaBody.getAllegatiList() != null) {

				for(AllegatiType allegatoType: fatturaBody.getAllegatiList()) {
					AllegatoFattura allegato = new AllegatoFattura();

					allegato.setNomeAttachment(allegatoType.getNomeAttachment());
					allegato.setAlgoritmoCompressione(allegatoType.getAlgoritmoCompressione());
					allegato.setFormatoAttachment(allegatoType.getFormatoAttachment());
					allegato.setDescrizioneAttachment(allegatoType.getDescrizioneAttachment());
					allegato.setAttachment(allegatoType.getAttachment());
					
					lst.add(allegato);

				}
			}
			
			return lst;
		}

		@Override
		public void validate(boolean strictValidation) throws ValidationException {
			FatturaElettronicaType getFattura = this.getFattura();
			if(getFattura == null)
				throw new ValidationException("File fattura non presente");
			if(getFattura.getFatturaElettronicaBodyList() == null || getFattura.getFatturaElettronicaBodyList().isEmpty())
				throw new ValidationException("Nessuna fattura contenuta nel lotto ricevuto");
			
			if(getFattura.getFatturaElettronicaBodyList().size() != 1)
				throw new ValidationException("Impossibile gestire fatture multiple. Trovate ["+getFattura().getFatturaElettronicaBodyList().size()+"] fatture");
			
			FatturaElettronicaBodyType fatturaBody = getFattura.getFatturaElettronicaBody(0);

			if(fatturaBody.getDatiGenerali() == null)
				throw new ValidationException("La fattura non contiene l'elemento datiGenerali");
			
			if(fatturaBody.getDatiGenerali().getDatiGeneraliDocumento() == null)
				throw new ValidationException("La fattura non contiene l'elemento datiGenerali.datiGeneraliDocumento");
			
			DatiGeneraliDocumentoType datiGeneraliDocumento = fatturaBody.getDatiGenerali().getDatiGeneraliDocumento();
			
			if(strictValidation) {
				if(datiGeneraliDocumento.getTipoDocumento()==null)
					throw new ValidationException("La fattura non contiene l'elemento datiGenerali.datiGeneraliDocumento.tipoDocumento");
			}
			
			if(datiGeneraliDocumento.getData() == null)
				throw new ValidationException("La fattura non contiene l'elemento datiGenerali.datiGeneraliDocumento.data");

		}
		
		@Override
		public double getImportoTotaleDocumento() {
			//NOTA: FIX 15.4.2015 a seguito della CR 82. Vedi corpo del metodo _getImportoTotale()
			return _getImportoTotale();
		}
		
//		private double _getImportoTotaleDettagli() {
	//
//			double importo = 0;
//			List<DettaglioLineeType> riepilogoLst = fattura.getFatturaElettronicaBody(0).getDatiBeniServizi().getDettaglioLineeList();
//			
//			for(DettaglioLineeType dettaglio : riepilogoLst) {
//				importo += dettaglio.getPrezzoTotale().doubleValue() + (dettaglio.getPrezzoTotale().doubleValue() * (dettaglio.getAliquotaIVA().doubleValue() / 100.0));
//			}
//			
//			return importo;
	//
//		}
		
		private Double _getImportoTotaleDocumento() {
			return getFattura().getFatturaElettronicaBody(0).getDatiGenerali().getDatiGeneraliDocumento().getImportoTotaleDocumento();
		}
		
		@Override
		public double getImportoTotaleRiepilogo() {
			//NOTA: FIX 15.4.2015 a seguito della CR 82. Vedi corpo del metodo _getImportoTotale()
			return _getImportoTotale();
		}

		private double _getImportoTotale() {
			//NOTA: a seguito della CR 82: l'importo totale torna ad essere uno solo e viene cosi' calcolato: 
			// - se l'importo totale documento e' valorizzato, si usa quello
			// - altrimenti si calcola dagli importi indicati nel riepilogo 
			Double importo = _getImportoTotaleDocumento();
			if(importo == null) {
				importo = _getImportoTotaleRiepilogo();
			}
			return importo;
		}
		
		private double _getImportoTotaleRiepilogo() {

			double importo = 0;
			List<DatiRiepilogoType> riepilogoLst = getFattura().getFatturaElettronicaBody(0).getDatiBeniServizi().getDatiRiepilogoList();
			
			for(DatiRiepilogoType riepilogo : riepilogoLst) {
				importo += riepilogo.getImponibileImporto().doubleValue() + riepilogo.getImposta().doubleValue();
			}
			
			return importo;

		}

	}
