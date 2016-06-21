/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2015 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2015 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
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
package org.govmix.proxy.fatturapa.web.api.converter.fattura;

import it.gov.fatturapa.sdi.fatturapa.v1_1.DettaglioLineeType;
import it.gov.fatturapa.sdi.fatturapa.v1_1.AllegatiType;
import it.gov.fatturapa.sdi.fatturapa.v1_1.DatiRiepilogoType;
import it.gov.fatturapa.sdi.fatturapa.v1_1.FatturaElettronicaBodyType;
import it.gov.fatturapa.sdi.fatturapa.v1_1.FatturaElettronicaType;
import it.gov.fatturapa.sdi.fatturapa.v1_1.utils.serializer.JaxbDeserializer;

import java.util.ArrayList;
import java.util.List;

import org.govmix.proxy.fatturapa.AllegatoFattura;
import org.govmix.proxy.fatturapa.FatturaElettronica;
import org.govmix.proxy.fatturapa.constants.FormatoTrasmissioneType;
import org.govmix.proxy.fatturapa.constants.TipoDocumentoType;
import org.govmix.proxy.fatturapa.web.api.business.consegnaFattura.ConsegnaFatturaParameters;

public class FatturaV11Converter extends AbstractFatturaConverter<FatturaElettronicaType> {

		private static JaxbDeserializer deserializer;
		
		static {
			deserializer = new JaxbDeserializer();
		}
		
		public FatturaV11Converter(String fatturaString, ConsegnaFatturaParameters params) throws Exception {
			super(deserializer.readFatturaElettronicaTypeFromString(fatturaString), fatturaString, params);
		}
		

		@Override
		public void populateFatturaConDatiSpecifici(FatturaElettronica fatturaElettronica) {

			FatturaElettronicaBodyType fatturaBody = fattura.getFatturaElettronicaBody(0);

			TipoDocumentoType tipoDoc;
			switch(fatturaBody.getDatiGenerali().getDatiGeneraliDocumento().getTipoDocumento()) {
			case TD01: tipoDoc = TipoDocumentoType.TD01;
				break;
			case TD02: tipoDoc = TipoDocumentoType.TD02;
				break;
			case TD03: tipoDoc = TipoDocumentoType.TD03;
				break;
			case TD04: tipoDoc = TipoDocumentoType.TD04;
				break;
			case TD05: tipoDoc = TipoDocumentoType.TD05;
				break;
			case TD06: tipoDoc = TipoDocumentoType.TD06;
				break;
			default:  tipoDoc = TipoDocumentoType.TD01;
				break;
			
			}
			
			fatturaElettronica.setTipoDocumento(tipoDoc);
			
			fatturaElettronica.setFormatoTrasmissione(FormatoTrasmissioneType.SDI11);
			fatturaElettronica.setDivisa(fatturaBody.getDatiGenerali().getDatiGeneraliDocumento().getDivisa());
			fatturaElettronica.setData(fatturaBody.getDatiGenerali().getDatiGeneraliDocumento().getData());
			fatturaElettronica.setNumero(fatturaBody.getDatiGenerali().getDatiGeneraliDocumento().getNumero());
			fatturaElettronica.setAnno(new Integer(sdfYear.format(fatturaElettronica.getData())));
			
			fatturaElettronica.setCausale(getCausale());

			fatturaElettronica.setXml(this.fatturaAsString);
		}
		
		

		@Override
		public List<String> getCausali() {
			FatturaElettronicaBodyType fatturaBody = fattura.getFatturaElettronicaBody(0);
			
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
			
			FatturaElettronicaBodyType fatturaBody = fattura.getFatturaElettronicaBody(0);

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
		public void validate() throws Exception {
			if(fattura.getFatturaElettronicaBodyList() == null || fattura.getFatturaElettronicaBodyList().size() == 0){
				throw new Exception("Nessuna fattura contenuta nel lotto ricevuto");
			} else if(fattura.getFatturaElettronicaBodyList().size() != 1) {
				throw new Exception("Impossibile gestire fatture multiple. Trovate ["+fattura.getFatturaElettronicaBodyList().size()+"] fatture");
			}
		}

		@Override
		public double getImportoTotaleDocumento() {
		
			double importo = 0;
			List<DettaglioLineeType> riepilogoLst = fattura.getFatturaElettronicaBody(0).getDatiBeniServizi().getDettaglioLineeList();
			
			for(DettaglioLineeType dettaglio : riepilogoLst) {
				importo += dettaglio.getPrezzoTotale().doubleValue() + (dettaglio.getPrezzoTotale().doubleValue() * (dettaglio.getAliquotaIVA().doubleValue() / 100.0));
			}
			
			return importo;

		}
		
		@Override
		public double getImportoTotaleRiepilogo() {
		
			double importo = 0;
			List<DatiRiepilogoType> riepilogoLst = fattura.getFatturaElettronicaBody(0).getDatiBeniServizi().getDatiRiepilogoList();
			
			for(DatiRiepilogoType riepilogo : riepilogoLst) {
				importo += riepilogo.getImponibileImporto().doubleValue() + riepilogo.getImposta().doubleValue();
			}
			
			return importo;

		}
	}
