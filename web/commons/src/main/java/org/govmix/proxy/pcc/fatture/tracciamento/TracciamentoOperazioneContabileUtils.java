/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2016 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2016 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
package org.govmix.proxy.pcc.fatture.tracciamento;


import it.tesoro.fatture.DettaglioMovimentoTipo;
import it.tesoro.fatture.PianoComunicazioneScadenzaTipo;
import it.tesoro.fatture.QueryDatiFatturaRispostaTipo;
import it.tesoro.fatture.RipartizioneAttualeTipo;
import it.tesoro.fatture.StatoContabileFatturaTipo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.govmix.pcc.fatture.ComunicaScadenzaTipo;
import org.govmix.pcc.fatture.ComunicazioneScadenzaTipo;
import org.govmix.pcc.fatture.ContabilizzazioneTipo;
import org.govmix.pcc.fatture.NaturaSpesaContabiliTipo;
import org.govmix.pcc.fatture.NaturaSpesaTipo;
import org.govmix.pcc.fatture.OperazioneContabilizzazioneTipo;
import org.govmix.pcc.fatture.OperazioneTipo;
import org.govmix.pcc.fatture.PagamentoStornoTipo;
import org.govmix.pcc.fatture.PagamentoTipo;
import org.govmix.pcc.fatture.ProxyOperazioneContabileRichiestaTipo;
import org.govmix.pcc.fatture.StatoDebitoTipo;
import org.govmix.pcc.fatture.TipoOperazioneTipo;
import org.govmix.proxy.fatturapa.orm.IdFattura;
import org.govmix.proxy.fatturapa.orm.PccContabilizzazione;
import org.govmix.proxy.fatturapa.orm.PccPagamento;
import org.govmix.proxy.fatturapa.orm.PccScadenza;
import org.govmix.proxy.fatturapa.orm.constants.CausaleType;
import org.govmix.proxy.fatturapa.orm.constants.NaturaSpesaType;
import org.govmix.proxy.fatturapa.orm.constants.StatoDebitoType;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.FilterSortWrapper;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.PccContabilizzazioneBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.PccOperazioneContabileBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.PccPagamentoBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.PccScadenzaBD;
import org.govmix.proxy.fatturapa.web.commons.utils.TransformUtils;
import org.govmix.proxy.pcc.fatture.authorization.AuthorizationBeanResponse;
import org.govmix.proxy.pcc.fatture.utils.PccProperties;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.SortOrder;

public class TracciamentoOperazioneContabileUtils {

	private PccOperazioneContabileBD pccOperazioneContabileBD;
	private PccPagamentoBD pccPagamentoBD;
	private PccContabilizzazioneBD pccContabilizzazioneBD;
	private PccScadenzaBD scadenzaBD;
	private List<TipoOperazioneTipo> operazioniTracciabili;
	private Logger log;

	public TracciamentoOperazioneContabileUtils(Logger log) throws Exception {
		this.log = log;
		this.pccOperazioneContabileBD = new PccOperazioneContabileBD(log);
		this.pccPagamentoBD = new PccPagamentoBD(log);
		this.scadenzaBD = new PccScadenzaBD(log);
		this.pccContabilizzazioneBD = new PccContabilizzazioneBD(log);
		this.operazioniTracciabili = new ArrayList<TipoOperazioneTipo>();
		this.operazioniTracciabili.add(TipoOperazioneTipo.CO);
		this.operazioniTracciabili.add(TipoOperazioneTipo.CP);
		this.operazioniTracciabili.add(TipoOperazioneTipo.SP);
		this.operazioniTracciabili.add(TipoOperazioneTipo.CS);
		this.operazioniTracciabili.add(TipoOperazioneTipo.CCS);
		this.operazioniTracciabili.add(TipoOperazioneTipo.SC);

	}

	public void tracciaOperazioneContabile(ProxyOperazioneContabileRichiestaTipo proxyOperazioneContabileRichiestaTipo, AuthorizationBeanResponse auth, IdFattura idFattura) throws Exception {
		OperazioneTipo operaz = proxyOperazioneContabileRichiestaTipo.getDatiRichiesta().getListaOperazione().getOperazione();

		if(this.operazioniTracciabili.contains(operaz.getTipoOperazione())) {

			switch(operaz.getTipoOperazione()) {
			case CCS: tracciaCancellazioneComunicazioniScadenza(operaz, idFattura);
			break;
			case CO: tracciaContabilizzazioneFattura(operaz, auth, idFattura);
			break;
			case CP: tracciaPagamento(operaz, idFattura);
			break;
			case CS: tracciaComunicazioneScadenza(operaz, auth, idFattura);
			break;
			case RC: //NOP
			break;
			case RF: //NOP
				break;
			case SC: tracciaStornoContabilizzazione(operaz, idFattura);
			break;
			case SP: tracciaStornoPagamento(operaz, idFattura);
			break;
			default:
				break;
			}
		}
	}

	private List<PccPagamento> tracciaStornoPagamento(OperazioneTipo operaz, IdFattura idFattura)  throws Exception {
		
		List<PccPagamento> lstPagamento = getStorno(operaz);
		for(PccPagamento pccPagamento: lstPagamento) { 
			pccPagamento.setIdFattura(idFattura);
			this.pccOperazioneContabileBD.create(pccPagamento);
		}
		return lstPagamento;
	}

	public double getImportoByIdFattura(IdFattura idFattura, String naturaSpesa) throws Exception {
		List<PccPagamento> pagamentiByIdFattura = this.pccPagamentoBD.getPagamentiByIdFattura(idFattura);
		double importo =0;
		for(PccPagamento pagamento: pagamentiByIdFattura) {
			if(pagamento.getNaturaSpesa().getValue().equals(naturaSpesa)) {
				importo += pagamento.getImportoPagato();
			}
		}
		
		return importo;
		
	}
	
	private void tracciaStornoContabilizzazione(OperazioneTipo operaz, IdFattura idFattura)  throws Exception {
		this.pccOperazioneContabileBD.deleteContabilizzazioni(idFattura);
	}

	private List<PccScadenza> tracciaComunicazioneScadenza(OperazioneTipo operaz, AuthorizationBeanResponse auth, IdFattura idFattura) throws Exception {

		List<PccScadenza> scadenzaLst = new ArrayList<PccScadenza>();

		for(ComunicazioneScadenzaTipo comunicazioneScadenza: operaz.getStrutturaDatiOperazione().getComunicazioneScadenza()) {
			PccScadenza scadenza = new PccScadenza();
			
			scadenza.setDataRichiesta(new Date());
			if(comunicazioneScadenza.getDataScadenza() != null)
				scadenza.setDataScadenza(comunicazioneScadenza.getDataScadenza());
			
			scadenza.setIdFattura(idFattura);
			
			if(comunicazioneScadenza.getImporto() != null) {
				scadenza.setImportoInScadenza(comunicazioneScadenza.getImporto().doubleValue());
				scadenza.setImportoIniziale(comunicazioneScadenza.getImporto().doubleValue());
			}
			
			scadenza.setPagatoRicontabilizzato(0d);
			scadenza.setSistemaRichiedente(auth.getSistemaRichiedente());
			scadenza.setUtenteRichiedente(auth.getUtenteRichiedente());
			this.pccOperazioneContabileBD.create(scadenza);
			scadenzaLst.add(scadenza);
		}
		return scadenzaLst;
	}

	private List<PccPagamento> tracciaPagamento(OperazioneTipo operaz, IdFattura idFattura)  throws Exception {
		
		List<PccPagamento> lstPagamento = getPagamento(operaz);
		for(PccPagamento pccPagamento: lstPagamento) { 
			pccPagamento.setIdFattura(idFattura);
			this.pccOperazioneContabileBD.create(pccPagamento);
		}
		return lstPagamento;
	}

	private List<PccPagamento> getStorno(OperazioneTipo operaz) {

		List<PccPagamento> pccPagamentoLst = new ArrayList<PccPagamento>();

		for(PagamentoStornoTipo pagamentoInput: operaz.getStrutturaDatiOperazione().getPagamentoStorno()) {
			PccPagamento pccPagamento = new PccPagamento();
			double pagamentoStorno = pagamentoInput.getImportoStorno().doubleValue();
			
			if(pagamentoStorno > 0) {
				pagamentoStorno *= -1;
			}
			
			pccPagamento.setDataRichiesta(new Date());
			pccPagamento.setImportoPagato(pagamentoStorno);
			pccPagamento.setNaturaSpesa(NaturaSpesaType.toEnumConstant(pagamentoInput.getNaturaSpesa().value()));
			pccPagamento.setIdFiscaleIvaBeneficiario(pagamentoInput.getIdFiscaleIVABeneficiario());
			
			pccPagamentoLst.add(pccPagamento);
		}
		
		return pccPagamentoLst;
	}

	private List<PccPagamento> getPagamento(OperazioneTipo operaz) {
		
		List<PccPagamento> pccPagamentoLst = new ArrayList<PccPagamento>();

		for(PagamentoTipo pagamentoInput: operaz.getStrutturaDatiOperazione().getPagamento()) {
			
			PccPagamento pccPagamento = new PccPagamento();
			pccPagamento.setDataRichiesta(new Date());
			pccPagamento.setImportoPagato(pagamentoInput.getImportoPagato().doubleValue());
			pccPagamento.setNaturaSpesa(NaturaSpesaType.toEnumConstant(pagamentoInput.getNaturaSpesa().value()));
			pccPagamento.setCapitoliSpesa(pagamentoInput.getCapitoliSpesa());
			pccPagamento.setEstremiImpegno(pagamentoInput.getEstremiImpegno());
			
			if(pagamentoInput.getMandatoPagamento() != null) {
				pccPagamento.setNumeroMandato(pagamentoInput.getMandatoPagamento().getNumero());
				pccPagamento.setDataMandato(pagamentoInput.getMandatoPagamento().getDataMandatoPagamento());
			}
			
			pccPagamento.setIdFiscaleIvaBeneficiario(pagamentoInput.getIdFiscaleIVABeneficiario());
			pccPagamento.setCodiceCig(pagamentoInput.getCodiceCIG());
			pccPagamento.setCodiceCup(pagamentoInput.getCodiceCUP());
			
			pccPagamento.setDescrizione(pagamentoInput.getDescrizione());
			
			pccPagamentoLst.add(pccPagamento);
		}
		
		return pccPagamentoLst;
	}

	private void tracciaContabilizzazioneFattura(OperazioneTipo operaz, AuthorizationBeanResponse auth, IdFattura idFattura)  throws Exception {
		
		if(PccProperties.getInstance().getSistemaRichiedenteCruscotto().equals(auth.getSistemaRichiedente())) {
			this.pccContabilizzazioneBD.deleteByIdFatturaSistemaRichiedente(idFattura, auth.getSistemaRichiedente());			
		}
		
		for(ContabilizzazioneTipo contabilizzazione: operaz.getStrutturaDatiOperazione().getListaContabilizzazione()) {
			
			PccContabilizzazione pccContabilizzazione = new PccContabilizzazione();
			pccContabilizzazione.setDataRichiesta(new Date());
			pccContabilizzazione.setIdImporto(contabilizzazione.getIdentificativoMovimento());
			pccContabilizzazione.setSistemaRichiedente(auth.getSistemaRichiedente());
			pccContabilizzazione.setUtenteRichiedente(auth.getUtenteRichiedente());
			pccContabilizzazione.setImportoMovimento(contabilizzazione.getImportoMovimento().doubleValue());
			pccContabilizzazione.setNaturaSpesa(NaturaSpesaType.toEnumConstant(contabilizzazione.getNaturaSpesa().toString()));
			pccContabilizzazione.setCapitoliSpesa(contabilizzazione.getCapitoliSpesa());
			pccContabilizzazione.setStatoDebito(StatoDebitoType.toEnumConstant(contabilizzazione.getOperazione().getStatoDebito().value()));
			
			if(contabilizzazione.getOperazione().getCausale() != null)
				pccContabilizzazione.setCausale(CausaleType.toEnumConstant(contabilizzazione.getOperazione().getCausale()));
			
			pccContabilizzazione.setDescrizione(contabilizzazione.getDescrizione());
			pccContabilizzazione.setEstremiImpegno(contabilizzazione.getEstremiImpegno());
			pccContabilizzazione.setCodiceCig(contabilizzazione.getCodiceCIG());
			pccContabilizzazione.setCodiceCup(contabilizzazione.getCodiceCUP());
			pccContabilizzazione.setIdFattura(idFattura);
			this.pccOperazioneContabileBD.create(pccContabilizzazione);
		}
	}

	private void tracciaCancellazioneComunicazioniScadenza(OperazioneTipo operaz, IdFattura idFattura)  throws Exception {
		this.pccOperazioneContabileBD.deleteScadenze(idFattura, false);
	}

	public List<ContabilizzazioneTipo> getContabilizzazioniByIdFatturaEDiversoSistemaRichiedente(IdFattura idFattura, String sistemaRichiedente) throws Exception {
		List<PccContabilizzazione> pccContabilizzazioneList = this.pccContabilizzazioneBD.getContabilizzazioniByIdFatturaEDiversoSistemaRichiedente(idFattura, sistemaRichiedente);
		List<ContabilizzazioneTipo> lst = new ArrayList<ContabilizzazioneTipo>();
		for(PccContabilizzazione contabilizzazione: pccContabilizzazioneList) {
			lst.add(toPcc(contabilizzazione));
		}
		return lst;
	}

	public List<ComunicazioneScadenzaTipo> getScadenzeByIdFattura(IdFattura idFattura) throws ServiceException {
		List<PccScadenza> pccScadenzaList = this.scadenzaBD.getScadenzeByIdFattura(idFattura);
		List<ComunicazioneScadenzaTipo> lst = new ArrayList<ComunicazioneScadenzaTipo>();
		for(PccScadenza scadenza: pccScadenzaList) {
			lst.add(toPcc(scadenza));
		}
		return lst;
	}


	private ContabilizzazioneTipo toPcc(PccContabilizzazione contabilizzazione) {
		ContabilizzazioneTipo cont = new ContabilizzazioneTipo();
		cont.setCapitoliSpesa(contabilizzazione.getCapitoliSpesa());
		cont.setCodiceCIG(contabilizzazione.getCodiceCig());
		cont.setCodiceCUP(contabilizzazione.getCodiceCup());
		cont.setDescrizione(contabilizzazione.getDescrizione());
		cont.setEstremiImpegno(contabilizzazione.getEstremiImpegno());
		cont.setIdentificativoMovimento(contabilizzazione.getIdImporto());
		cont.setImportoMovimento(new BigDecimal(contabilizzazione.getImportoMovimento()));
		NaturaSpesaContabiliTipo nat = null;
		switch(contabilizzazione.getNaturaSpesa()) {
		case CA: nat = NaturaSpesaContabiliTipo.CA;
			break;
		case CO: nat = NaturaSpesaContabiliTipo.CO;
			break;
		case NA: nat = NaturaSpesaContabiliTipo.NA;
			break;
		default:
			break;
		
		}
		cont.setNaturaSpesa(nat);
		OperazioneContabilizzazioneTipo op = new OperazioneContabilizzazioneTipo();
		
		if(contabilizzazione.getCausale() != null)
			op.setCausale(contabilizzazione.getCausale().getValue());
		
		op.setStatoDebito(StatoDebitoTipo.fromValue(contabilizzazione.getStatoDebito().getValue()));
		
		cont.setOperazione(op);
		
		return cont;
	}

	private ComunicazioneScadenzaTipo toPcc(PccScadenza scadenza) {
		ComunicazioneScadenzaTipo comunicazioneScadenza= new ComunicazioneScadenzaTipo();
		comunicazioneScadenza.setComunicaScadenza(ComunicaScadenzaTipo.SI);
		if(scadenza.getDataScadenza() != null)
			comunicazioneScadenza.setDataScadenza(scadenza.getDataScadenza());
		
		if(scadenza.getImportoInScadenza() != null)
			comunicazioneScadenza.setImporto(new BigDecimal(scadenza.getImportoInScadenza()));
		
		return comunicazioneScadenza;
	}

	public void riallineaDatiFattura(AuthorizationBeanResponse beanResponse, StatoContabileFatturaTipo statoContabileFatturaTipo) throws Exception {
		
		this.pccContabilizzazioneBD.deleteContabilizzazioni(beanResponse.getIdLogicoFattura());

		List<PccContabilizzazione> lstContabilizzazioni = getListaContabilizzazioni(statoContabileFatturaTipo.getDatiDocumento().getRipartizioneAttuale(), beanResponse);
		
		for(PccContabilizzazione pccContabilizzazione: lstContabilizzazioni) {
			this.pccOperazioneContabileBD.create(pccContabilizzazione);	
		}
		
		double pagato = statoContabileFatturaTipo.getDatiDocumento().getRipartizioneAttuale().getImportoPagato().doubleValue();

		this.pccPagamentoBD.deletePagamenti(beanResponse.getIdLogicoFattura());

		if(pagato > 0) {
			PccPagamento pagamento = new PccPagamento();
			pagamento.setIdFattura(beanResponse.getIdLogicoFattura());
			pagamento.setCodiceCig("NA");
			pagamento.setCodiceCup("NA");
			pagamento.setImportoPagato(pagato);
			pagamento.setIdFiscaleIvaBeneficiario(statoContabileFatturaTipo.getDatiDocumento().getIdFiscaleIVA());
			pagamento.setDataRichiesta(new Date());
			pagamento.setNaturaSpesa(NaturaSpesaType.NA);
	
			this.pccPagamentoBD.create(pagamento);
		}
		
	}

	private List<PccContabilizzazione> getListaContabilizzazioni(RipartizioneAttualeTipo ripartizioneAttualeTipo, AuthorizationBeanResponse beanResponse) {
		List<PccContabilizzazione> lst = new ArrayList<PccContabilizzazione>();
		
		String pccSistemaRichiedente = beanResponse.getSistemaRichiedente();
		String pccUtenteRichiedente = beanResponse.getUtenteRichiedente();
		int i = 0;
		if(ripartizioneAttualeTipo.getImportoNonLiquidato().doubleValue() > 0) {
			PccContabilizzazione pccContabilizzazione = new PccContabilizzazione();
			pccContabilizzazione.setIdImporto(pccSistemaRichiedente +"_" +(i++));
			pccContabilizzazione.setSistemaRichiedente(pccSistemaRichiedente);
			pccContabilizzazione.setUtenteRichiedente(pccUtenteRichiedente);
			pccContabilizzazione.setNaturaSpesa(NaturaSpesaType.NA);	

			pccContabilizzazione.setImportoMovimento(ripartizioneAttualeTipo.getImportoNonLiquidato().doubleValue());

			pccContabilizzazione.setStatoDebito(StatoDebitoType.NOLIQ);
			pccContabilizzazione.setCodiceCig("NA");
			pccContabilizzazione.setCodiceCup("NA");
			pccContabilizzazione.setDataRichiesta(new Date());

			pccContabilizzazione.setIdFattura(beanResponse.getIdLogicoFattura());
			lst.add(pccContabilizzazione);
		}
		
		if(ripartizioneAttualeTipo.getImportoSospeso().doubleValue() > 0) {
			PccContabilizzazione pccContabilizzazione = new PccContabilizzazione();
			pccContabilizzazione.setIdImporto(pccSistemaRichiedente +"_" +(i++));
			pccContabilizzazione.setSistemaRichiedente(pccSistemaRichiedente);
			pccContabilizzazione.setUtenteRichiedente(pccUtenteRichiedente);
			pccContabilizzazione.setNaturaSpesa(NaturaSpesaType.NA);	

			pccContabilizzazione.setImportoMovimento(ripartizioneAttualeTipo.getImportoSospeso().doubleValue());

			pccContabilizzazione.setStatoDebito(StatoDebitoType.SOSP);
			pccContabilizzazione.setCodiceCig("NA");
			pccContabilizzazione.setCodiceCup("NA");
			pccContabilizzazione.setDataRichiesta(new Date());
			pccContabilizzazione.setIdFattura(beanResponse.getIdLogicoFattura());
			lst.add(pccContabilizzazione);
		}
		
		double liquidato = ripartizioneAttualeTipo.getImportoLiquidato().doubleValue();
		
		if(liquidato > 0) {
			PccContabilizzazione pccContabilizzazione = new PccContabilizzazione();
			pccContabilizzazione.setIdImporto(pccSistemaRichiedente +"_" +(i++));
			pccContabilizzazione.setSistemaRichiedente(pccSistemaRichiedente);
			pccContabilizzazione.setUtenteRichiedente(pccUtenteRichiedente);
			pccContabilizzazione.setNaturaSpesa(NaturaSpesaType.NA);	

			pccContabilizzazione.setImportoMovimento(liquidato);

			pccContabilizzazione.setStatoDebito(StatoDebitoType.LIQ);
			pccContabilizzazione.setCodiceCig("NA");
			pccContabilizzazione.setCodiceCup("NA");
			pccContabilizzazione.setDataRichiesta(new Date());
			
			pccContabilizzazione.setIdFattura(beanResponse.getIdLogicoFattura());
			lst.add(pccContabilizzazione);
		}
		
		return lst;
	}

	public boolean existScadenze(IdFattura idFattura) throws Exception {
		return this.pccOperazioneContabileBD.existScadenze(idFattura);
	}

	public List<PccScadenza> getScadenze(IdFattura idFattura) throws Exception {
		return this.scadenzaBD.getScadenzeByIdFattura(idFattura);
	}

	public boolean existContabilizzazioni(IdFattura idFattura) throws Exception {
		return this.pccContabilizzazioneBD.exists(idFattura);
	}

	public Date getDataScadenza(IdFattura idFattura, double importoTotaleDocumento) throws Exception {
		List<PccScadenza> scadenzeLst = this.scadenzaBD.getScadenzeByIdFattura(idFattura, null, null, null );
		
		PccScadenza primaScadenza = null;
		
		for(int i = 0; i < scadenzeLst.size(); i++) {
			PccScadenza scadenza = scadenzeLst.get(i);

			if(scadenza.getDataScadenza() == null) {
				return new Date(0);
			} else if(primaScadenza == null || (scadenza.getImportoInScadenza() > 0 && primaScadenza.getDataScadenza().after(scadenza.getDataScadenza()))) {
				primaScadenza = scadenza;
			}
		}
		
		if(primaScadenza != null)
			return primaScadenza.getDataScadenza();
		
		return null;
		
		
	}

	public boolean isDaPagare(IdFattura idFattura) throws Exception {
		List<PccScadenza> lst = this.scadenzaBD.getScadenzeByIdFattura(idFattura);
		for(PccScadenza scadenza: lst) {
			if(scadenza.getImportoIniziale() == null || scadenza.getImportoIniziale() > 0)
				return true;
		}
		return false;
	}

	public void riallineaStatoContabileFattura(IdFattura idFattura, QueryDatiFatturaRispostaTipo datiFattura) throws Exception {
		
		this.log.debug("Riallineamento stato contabile per id fattura ["+idFattura.toJson()+"]");

		if(datiFattura.getDatiRisposta().getDettaglioFattura() != null) {
			List<PccContabilizzazione> lstLiq = new ArrayList<PccContabilizzazione>();
			List<PccContabilizzazione> lstSosp = new ArrayList<PccContabilizzazione>();
			List<PccContabilizzazione> lstNoliq = new ArrayList<PccContabilizzazione>();
			List<PccPagamento> lstPag = new ArrayList<PccPagamento>();
			

			if(datiFattura.getDatiRisposta().getDettaglioFattura().getDatiDocumento().getListaDettaglioMovimento() != null && datiFattura.getDatiRisposta().getDettaglioFattura().getDatiDocumento().getListaDettaglioMovimento().getMovimento() != null) {
				for(int i = datiFattura.getDatiRisposta().getDettaglioFattura().getDatiDocumento().getListaDettaglioMovimento().getMovimento().size()-1; i >= 0;i--) {
					
					DettaglioMovimentoTipo movimento = datiFattura.getDatiRisposta().getDettaglioFattura().getDatiDocumento().getListaDettaglioMovimento().getMovimento().get(i);
					String statoDebito = movimento.getStatoDebito().trim();
					
					this.log.debug("Trovato statoDebito ["+statoDebito+"]");
					if("Liquidazione".equalsIgnoreCase(statoDebito) || "Ricontabilizzazione Liquidato".equalsIgnoreCase(statoDebito) || "Liquidazione da Storno Pagamento".equalsIgnoreCase(statoDebito)) {
						this.log.debug("Trovato statoDebito ["+statoDebito+"], aggiungo contabilizzazioni LIQ");
						lstLiq.addAll(getContabilizzazione(movimento));
					} else if("Sospeso".equalsIgnoreCase(statoDebito) || "Ricontabilizzazione Sospeso".equalsIgnoreCase(statoDebito)) {
						this.log.debug("Trovato statoDebito ["+statoDebito+"], aggiungo contabilizzazioni SOSP");
						lstSosp.addAll(getContabilizzazione(movimento));
					} else if("Non Liquidato".equalsIgnoreCase(statoDebito) || "Ricontabilizzazione Non Liquidato".equalsIgnoreCase(statoDebito)) {
						this.log.debug("Trovato statoDebito ["+statoDebito+"], aggiungo contabilizzazioni NOLIQ");
						lstNoliq.addAll(getContabilizzazione(movimento));
					} else if("Adeguamento Liquidato da Sospeso".equalsIgnoreCase(statoDebito)) {
						this.log.debug("Trovato statoDebito ["+statoDebito+"], sottraggo SOSP");
						sottraiImporto(movimento, lstSosp);
					} else if("Adeguamento Liquidato da Non Liquidato".equalsIgnoreCase(statoDebito) || "Adeguamento  Liquidato da Non Liquidato".equalsIgnoreCase(statoDebito)) {
						this.log.debug("Trovato statoDebito ["+statoDebito+"], sottraggo NOLIQ");
						sottraiImporto(movimento, lstNoliq);
					} else if("Pagamento su Liquidato".equalsIgnoreCase(statoDebito)) {
						this.log.debug("Trovato statoDebito ["+statoDebito+"], sottraggo LIQ");
						sottraiImporto(movimento, lstLiq);
					} else if("Pagamento".equalsIgnoreCase(statoDebito)) {
						this.log.debug("Trovato statoDebito ["+statoDebito+"], aggiungo Pagamento");
						lstPag.addAll(getPagamento(movimento, false));
					} else if("Storno Conto Sospeso".equalsIgnoreCase(statoDebito)) {
						this.log.debug("Trovato statoDebito ["+statoDebito+"], azzero SOSP");
						lstSosp.clear();
					} else if("Storno Pagamento".equalsIgnoreCase(statoDebito)) {
						this.log.debug("Trovato statoDebito ["+statoDebito+"], aggiungo PAG negativo");
						lstPag.addAll(getPagamento(movimento, true));
					} else if("Storno Conto Liquidato".equalsIgnoreCase(statoDebito)) {
						this.log.debug("Trovato statoDebito ["+statoDebito+"], azzero LIQ");
						lstLiq.clear();
					} else if("Storno Conto Non Liquidato".equalsIgnoreCase(statoDebito)) {
						this.log.debug("Trovato statoDebito ["+statoDebito+"], azzero NOLIQ");
						lstNoliq.clear();
					} else {
						this.log.debug("StatoDebito ["+statoDebito+"] corrisponde a NOP");
					}
				}
			}
			
			this.pccContabilizzazioneBD.deleteContabilizzazioni(idFattura);
			
			if(lstLiq.size() > 0) {
				this.log.debug("Inserisco ["+lstLiq.size()+"] LIQ");
				for(PccContabilizzazione cont: lstLiq) {
					cont.setIdFattura(idFattura);
					cont.setStatoDebito(StatoDebitoType.LIQ);
					if(cont.getSistemaRichiedente() == null) {
						TransformUtils.populateContabilizzazioneDefault(cont, false);
					}
					this.pccContabilizzazioneBD.create(cont);
				}
			} else {
				this.log.debug("Non inserisco LIQ");
			}

			if(lstSosp.size() > 0) {
				this.log.debug("Inserisco ["+lstSosp.size()+"] SOSP");
				for(PccContabilizzazione cont: lstSosp) {
					cont.setIdFattura(idFattura);
					cont.setStatoDebito(StatoDebitoType.SOSP);
					if(cont.getSistemaRichiedente() == null) {
						TransformUtils.populateContabilizzazioneDefault(cont, true);
					}
					this.pccContabilizzazioneBD.create(cont);
				}
			} else {
				this.log.debug("Non inserisco SOSP");
			}

			if(lstNoliq.size() > 0) {
				this.log.debug("Inserisco ["+lstNoliq.size()+"] NOLIQ");
				for(PccContabilizzazione cont: lstNoliq) {
					cont.setIdFattura(idFattura);
					cont.setStatoDebito(StatoDebitoType.NOLIQ);
					if(cont.getSistemaRichiedente() == null) {
						TransformUtils.populateContabilizzazioneDefault(cont, true);
					}
					this.pccContabilizzazioneBD.create(cont);
				}
			} else {
				this.log.debug("Non inserisco NOLIQ");
			}

			this.pccPagamentoBD.deletePagamenti(idFattura);

			if(lstPag.size() > 0) {
				this.log.debug("Inserisco ["+lstPag.size()+"] PAG");
				for(PccPagamento pag: lstPag) {
					pag.setIdFattura(idFattura);
					pag.setIdFiscaleIvaBeneficiario(datiFattura.getDatiRisposta().getDettaglioFattura().getDatiDocumento().getIdFiscaleIVA()); //TODO verificare
					this.pccOperazioneContabileBD.create(pag);
				}
			} else {
				this.log.debug("Non inserisco PAG");
			}
			
			this.scadenzaBD.deleteScadenze(idFattura, true);

			if(datiFattura.getDatiRisposta().getDettaglioFattura().getListaComunicazioneScadenza() != null && datiFattura.getDatiRisposta().getDettaglioFattura().getListaComunicazioneScadenza().getComunicazioneScadenza() != null) {
				List<PianoComunicazioneScadenzaTipo> comunicazioneScadenza = datiFattura.getDatiRisposta().getDettaglioFattura().getListaComunicazioneScadenza().getComunicazioneScadenza();
				if(comunicazioneScadenza.size() > 0) {
					this.log.debug("Inserisco ["+comunicazioneScadenza.size()+"] SCAD");
					for(PianoComunicazioneScadenzaTipo scadenza: comunicazioneScadenza) {
						PccScadenza pccScadenza = new PccScadenza();
						pccScadenza.setDataScadenza(scadenza.getDataScadenza());
						pccScadenza.setImportoInScadenza(scadenza.getImportoInScadenza().doubleValue());
						pccScadenza.setImportoIniziale(scadenza.getImportoIniziale().doubleValue());
						pccScadenza.setPagatoRicontabilizzato(scadenza.getPagatoRicontabilizzato().doubleValue());
						pccScadenza.setIdFattura(idFattura);
						pccScadenza.setDataRichiesta(new Date());
						
						pccScadenza.setSistemaRichiedente(PccProperties.getInstance().getSistemaRichiedenteGestionale());
						pccScadenza.setUtenteRichiedente(PccProperties.getInstance().getUtenteRichiedenteGestionale());
						this.scadenzaBD.create(pccScadenza);
					}
				} else {
					this.log.debug("Non inserisco SCAD");
				}
			}
			this.log.debug("Riallineamento stato contabile per id fattura ["+idFattura.toJson()+"] completato con successo");
		} else {
			this.log.debug("Riallineamento stato contabile per id fattura ["+idFattura.toJson()+"] non possibile");
		}
			
	}
	
	private void sottraiImporto(DettaglioMovimentoTipo movimento, List<PccContabilizzazione> lstCont) throws Exception {
		
		List<PccContabilizzazione> contLst = getContabilizzazione(movimento);
		
		for(PccContabilizzazione contDaSottrarre: contLst) {
			double rimanenza = contDaSottrarre.getImportoMovimento();
			
			if(rimanenza < 0) rimanenza = -rimanenza;
			
			List<PccContabilizzazione> lstContabilizzazioniStessoCigCup = new ArrayList<PccContabilizzazione>();
			List<PccContabilizzazione> lstContabilizzazioniDiversoCigCup = new ArrayList<PccContabilizzazione>();
			for(PccContabilizzazione cont: lstCont) {
				if(cont.getCodiceCig() != null && cont.getCodiceCup() != null && cont.getNaturaSpesa() != null
						&&
						cont.getCodiceCig().equals(contDaSottrarre.getCodiceCig()) && cont.getCodiceCup().equals(contDaSottrarre.getCodiceCup()) && cont.getNaturaSpesa().getValue().equals(contDaSottrarre.getNaturaSpesa().getValue())) {
					lstContabilizzazioniStessoCigCup.add(cont);
				} else {
					lstContabilizzazioniDiversoCigCup.add(cont);
				}
			}
				
			for(PccContabilizzazione contStessoCigCup: lstContabilizzazioniStessoCigCup) {
				if(rimanenza > 0) {
					if(contStessoCigCup.getImportoMovimento() > rimanenza) {
						contStessoCigCup.setImportoMovimento(contStessoCigCup.getImportoMovimento() - rimanenza);
						rimanenza = 0;
					} else {
						rimanenza -= contStessoCigCup.getImportoMovimento();
						contStessoCigCup.setImportoMovimento(0);
					}
				} else {
					continue;
				}
			}

			for(PccContabilizzazione contDiversoCigCup: lstContabilizzazioniDiversoCigCup) {
				if(rimanenza > 0) {
					if(contDiversoCigCup.getImportoMovimento() > rimanenza) {
						contDiversoCigCup.setImportoMovimento(contDiversoCigCup.getImportoMovimento() - rimanenza);
						rimanenza = 0;
					} else {
						rimanenza -= contDiversoCigCup.getImportoMovimento();
						contDiversoCigCup.setImportoMovimento(0);
					}
				} else {
					continue;
				}
			}
			
			lstCont.clear();
			
			for(PccContabilizzazione contStessoCigCup: lstContabilizzazioniStessoCigCup) {
				if(contStessoCigCup.getImportoMovimento() > 0) {
					lstCont.add(contStessoCigCup);
				}
			}

			for(PccContabilizzazione contDiversoCigCup: lstContabilizzazioniDiversoCigCup) {
				if(contDiversoCigCup.getImportoMovimento() > 0) {
					lstCont.add(contDiversoCigCup);
				}
			}
		}
	}

	private PccContabilizzazione getContabilizzazione(DettaglioMovimentoTipo movimento, NaturaSpesaType naturaSpesa, double importo) throws Exception {
		PccContabilizzazione pccContabilizzazione = new PccContabilizzazione();
		pccContabilizzazione.setDataRichiesta(movimento.getDataMovimento());
		pccContabilizzazione.setImportoMovimento(Math.abs(importo));
		pccContabilizzazione.setNaturaSpesa(naturaSpesa);
		pccContabilizzazione.setCapitoliSpesa(movimento.getCapitoloPgConto());
		
		if(movimento.getCausale() != null)
			pccContabilizzazione.setCausale(CausaleType.toEnumConstant(movimento.getCausale()));
		try {
			TransformUtils.populateContabilizzazione(pccContabilizzazione, movimento.getDescrizioneContabilizzazione());
		} catch(Exception e) {
			this.log.info("Descrizione ["+movimento.getDescrizioneContabilizzazione()+"] non nel giusto formato, inserisco valori di default");
		}
		pccContabilizzazione.setEstremiImpegno(movimento.getEstremiImpegno());
		
		if(movimento.getCodiceCIG() != null) {
			pccContabilizzazione.setCodiceCig(movimento.getCodiceCIG());
		} else {
			pccContabilizzazione.setCodiceCig("NA");
		}
		
		if(movimento.getCodiceCUP() != null) {
			pccContabilizzazione.setCodiceCup(movimento.getCodiceCUP());
		} else {
			pccContabilizzazione.setCodiceCup("NA");
		}

		return pccContabilizzazione;
	}
	
	private List<PccContabilizzazione> getContabilizzazione(DettaglioMovimentoTipo movimento) throws Exception {
		List<PccContabilizzazione> lstContabilizzazione = new ArrayList<PccContabilizzazione>();
		
		double contoCapitale = movimento.getImportoContoCapitale() != null ? movimento.getImportoContoCapitale().doubleValue() : 0;
		double naturaCorrente = movimento.getImportoNaturaCorrente() != null ? movimento.getImportoNaturaCorrente().doubleValue() : 0;
		if(contoCapitale != 0) {
			if(naturaCorrente != 0) {
				if((naturaCorrente + contoCapitale) != movimento.getImporto().doubleValue()) {
					throw new Exception("Somma importo natura corrente["+naturaCorrente
							+"] e importo conto capitale ["+contoCapitale+"] dovrebbe essere uguale all'importo ["
							+movimento.getImporto().doubleValue()+"] totale del movimento");
				}
				
				lstContabilizzazione.add(getContabilizzazione(movimento, NaturaSpesaType.CO, naturaCorrente));
				lstContabilizzazione.add(getContabilizzazione(movimento, NaturaSpesaType.CA, contoCapitale));
			} else {
				lstContabilizzazione.add(getContabilizzazione(movimento, NaturaSpesaType.CA, movimento.getImporto().doubleValue()));
			}
		} else {
			if(naturaCorrente != 0) {
				lstContabilizzazione.add(getContabilizzazione(movimento, NaturaSpesaType.CO, naturaCorrente));
			} else {
				lstContabilizzazione.add(getContabilizzazione(movimento, NaturaSpesaType.NA, movimento.getImporto().doubleValue()));
			}
			
		}
		
		return lstContabilizzazione;
	}

	private PccPagamento getPagamento(DettaglioMovimentoTipo movimento, NaturaSpesaType naturaSpesa, double importo, boolean storno) throws Exception {
		PccPagamento pccPagamento = new PccPagamento();
		pccPagamento.setDataRichiesta(movimento.getDataMovimento());
		pccPagamento.setDescrizione(movimento.getDescrizioneContabilizzazione());
		pccPagamento.setNaturaSpesa(naturaSpesa);
		pccPagamento.setCapitoliSpesa(movimento.getCapitoloPgConto());
		
		pccPagamento.setEstremiImpegno(movimento.getEstremiImpegno());
		
		if(movimento.getCodiceCIG() != null) {
			pccPagamento.setCodiceCig(movimento.getCodiceCIG());
		} else {
			pccPagamento.setCodiceCig("NA");
		}
		
		if(movimento.getCodiceCUP() != null) {
			pccPagamento.setCodiceCup(movimento.getCodiceCUP());
		} else {
			pccPagamento.setCodiceCup("NA");
		}

		if(storno) {
			pccPagamento.setImportoPagato(-Math.abs(importo));
		} else {
			pccPagamento.setImportoPagato(Math.abs(importo));
		}

		return pccPagamento;
	}

	private List<PccPagamento> getPagamento(DettaglioMovimentoTipo movimento, boolean storno) throws Exception {
		List<PccPagamento> lstPagamento = new ArrayList<PccPagamento>();
		
		double contoCapitale = movimento.getImportoContoCapitale() != null ? movimento.getImportoContoCapitale().doubleValue() : 0;
		double naturaCorrente = movimento.getImportoNaturaCorrente() != null ? movimento.getImportoNaturaCorrente().doubleValue() : 0;

		if(contoCapitale != 0) {
			if(naturaCorrente != 0) {
				if((naturaCorrente + contoCapitale) != movimento.getImporto().doubleValue()) {
					throw new Exception("Somma importo natura corrente["+naturaCorrente
							+"] e importo conto capitale ["+contoCapitale+"] dovrebbe essere uguale all'importo ["
							+movimento.getImporto().doubleValue()+"] totale del movimento");
				}
				
				lstPagamento.add(getPagamento(movimento, NaturaSpesaType.CO, naturaCorrente, storno));
				lstPagamento.add(getPagamento(movimento, NaturaSpesaType.CA, contoCapitale, storno));
			} else {
				lstPagamento.add(getPagamento(movimento, NaturaSpesaType.CA, movimento.getImporto().doubleValue(), storno));
			}
		} else {
			if(naturaCorrente != 0) {
				lstPagamento.add(getPagamento(movimento, NaturaSpesaType.CO, naturaCorrente, storno));
			} else {
				lstPagamento.add(getPagamento(movimento, NaturaSpesaType.NA, movimento.getImporto().doubleValue(), storno));
			}
			
		}

		return lstPagamento;
	}
	
	public void aggiornaContabilizzazioniEScadenzeDopoPagamento(IdFattura idFattura, PagamentoTipo pagamento) throws Exception {
		double rimanenza = pagamento.getImportoPagato().doubleValue();
		
		List<List<PccContabilizzazione>> listaDiListeContabilizzazioni = getListaDiListeContabilizzazioni(idFattura, pagamento);
		
		List<PccContabilizzazione> contabilizzazioniDaCancellare = new ArrayList<PccContabilizzazione>();
		
		for(int i = 0; i< listaDiListeContabilizzazioni.size(); i++) { //l'ordine e' importante
			List<PccContabilizzazione> listaCont = listaDiListeContabilizzazioni.get(i);
			if(rimanenza > 0) {
				for(PccContabilizzazione contabilizzazione: listaCont) {
					if(rimanenza > 0) {
						if(contabilizzazione.getImportoMovimento() > rimanenza) {
							
							contabilizzazione.setImportoMovimento(contabilizzazione.getImportoMovimento() - rimanenza);
							this.pccContabilizzazioneBD.update(contabilizzazione);
							rimanenza = 0;
							
						} else {
							rimanenza -= contabilizzazione.getImportoMovimento();
							contabilizzazioniDaCancellare.add(contabilizzazione);
						}
					} else {
						break;
					}				
				}
			} else {
				break;
			}
		}
		
		for(PccContabilizzazione delete: contabilizzazioniDaCancellare) {
			this.pccContabilizzazioneBD.delete(delete);
		}
		
		//scadenze
		
		List<FilterSortWrapper> list = new ArrayList<FilterSortWrapper>();
		FilterSortWrapper wr = new FilterSortWrapper();
		wr.setField(PccScadenza.model().DATA_SCADENZA);
		wr.setSortOrder(SortOrder.ASC);
		list.add(wr);
		List<PccScadenza> scadenzeLst = this.scadenzaBD.getScadenzeByIdFattura(idFattura, null, null, list );

		PccScadenza ultimaScadenza = null;
		double importo = pagamento.getImportoPagato().doubleValue();
		for(int i =0; i < scadenzeLst.size(); i++) {
			PccScadenza scad = scadenzeLst.get(i);
			if(scad.getImportoInScadenza() != null && scad.getImportoInScadenza().doubleValue() > 0) {
				ultimaScadenza = scad;
				if(importo > 0) {
					Double importoScad = scad.getImportoInScadenza();
					Double pagatoRicontabilizzato = scad.getPagatoRicontabilizzato();
					if(importo > importoScad) {
						scad.setImportoInScadenza(0d);
						scad.setPagatoRicontabilizzato(pagatoRicontabilizzato  + importoScad);
						this.scadenzaBD.update(scad);
					} else {
						scad.setImportoInScadenza(importoScad - importo);
						scad.setPagatoRicontabilizzato(pagatoRicontabilizzato + importo);
						this.scadenzaBD.update(scad);
					}
					importo -= importoScad;
				} else {
					break;
				}
			}
		}
		
		if(importo > 0 && ultimaScadenza != null) {
			ultimaScadenza.setPagatoRicontabilizzato(ultimaScadenza.getPagatoRicontabilizzato().doubleValue() + importo);
		}
	}

	public void aggiornaContabilizzazioniDopoStornoPagamento(IdFattura idFattura, PagamentoStornoTipo storno, AuthorizationBeanResponse response) throws Exception {
		
		PccContabilizzazione contabilizzazione = new PccContabilizzazione();
		NaturaSpesaType naturaSpesa = null;
		switch(storno.getNaturaSpesa()) {
		case CA:naturaSpesa = NaturaSpesaType.CA;
			break;
		case CO:naturaSpesa = NaturaSpesaType.CO;
			break;
		default:
			break;
		
		}
		
		contabilizzazione.setDataRichiesta(new Date());
		contabilizzazione.setNaturaSpesa(naturaSpesa);
		contabilizzazione.setCodiceCig("NA");
		contabilizzazione.setCodiceCup("NA");
		contabilizzazione.setStatoDebito(StatoDebitoType.LIQ);
		contabilizzazione.setImportoMovimento(storno.getImportoStorno().doubleValue());
		contabilizzazione.setIdFattura(idFattura);
		contabilizzazione.setSistemaRichiedente(response.getSistemaRichiedente());
		contabilizzazione.setUtenteRichiedente(response.getUtenteRichiedente());
		contabilizzazione.setIdImporto(UUID.randomUUID().toString().substring(0, 3));
		
		this.pccContabilizzazioneBD.create(contabilizzazione);

		//scadenze
		
		List<FilterSortWrapper> list = new ArrayList<FilterSortWrapper>();
		FilterSortWrapper wr = new FilterSortWrapper();
		wr.setField(PccScadenza.model().DATA_SCADENZA);
		wr.setSortOrder(SortOrder.DESC);
		list.add(wr);
		List<PccScadenza> scadenzeLst = this.scadenzaBD.getScadenzeByIdFattura(idFattura, null, null, list );

		double importo = storno.getImportoStorno().doubleValue();
		for(int i =0; i < scadenzeLst.size(); i++) {
			PccScadenza scad = scadenzeLst.get(i);
			if(scad.getPagatoRicontabilizzato() != null && scad.getPagatoRicontabilizzato().doubleValue() > 0) {
				if(importo > 0) {
					Double importoScad = scad.getImportoInScadenza();
					Double pagatoRicontabilizzato = scad.getPagatoRicontabilizzato();
					if(importo > pagatoRicontabilizzato) {
						scad.setImportoInScadenza(importoScad + pagatoRicontabilizzato);
						scad.setPagatoRicontabilizzato(0d);
						this.scadenzaBD.update(scad);
					} else {
						scad.setImportoInScadenza(importoScad + importo);
						scad.setPagatoRicontabilizzato(pagatoRicontabilizzato - importo);
						this.scadenzaBD.update(scad);
					}
					importo -= pagatoRicontabilizzato;
				} else {
					break;
				}
			}
		}

	}
	
	private List<List<PccContabilizzazione>> getListaDiListeContabilizzazioni(IdFattura idFattura, PagamentoTipo pagamento) throws ServiceException {
		
		String cig = pagamento.getCodiceCIG();
		String cup = pagamento.getCodiceCUP();
		NaturaSpesaTipo naturaSpesa = pagamento.getNaturaSpesa();

		ArrayList<List<PccContabilizzazione>> listaDiListe = new ArrayList<List<PccContabilizzazione>>();
		List<PccContabilizzazione> contabilizzazioniByIdFattura = this.pccContabilizzazioneBD.getContabilizzazioniByIdFattura(idFattura);
		
		List<PccContabilizzazione> contabilizzazioniLiqConStessiDati = new ArrayList<PccContabilizzazione>();
		List<PccContabilizzazione> contabilizzazioniLiqConDatiDiversi = new ArrayList<PccContabilizzazione>();
		List<PccContabilizzazione> contabilizzazioniSosp = new ArrayList<PccContabilizzazione>();
		List<PccContabilizzazione> contabilizzazioniNoliq = new ArrayList<PccContabilizzazione>();
		
		for(PccContabilizzazione contabilizzazione: contabilizzazioniByIdFattura) {
			if(contabilizzazione.getStatoDebito().equals(StatoDebitoType.LIQ)) {
				if(contabilizzazione.getCodiceCig() != null && contabilizzazione.getCodiceCup() != null && contabilizzazione.getNaturaSpesa() != null
						&&
						contabilizzazione.getCodiceCig().equals(cig) && contabilizzazione.getCodiceCup().equals(cup) && contabilizzazione.getNaturaSpesa().getValue().equals(naturaSpesa.value())) {
					contabilizzazioniLiqConStessiDati.add(contabilizzazione);
				} else if(contabilizzazione.getNaturaSpesa() != null && contabilizzazione.getNaturaSpesa().getValue().equals(naturaSpesa.value())) {
					contabilizzazioniLiqConDatiDiversi.add(contabilizzazione);
				}
			} else if(contabilizzazione.getStatoDebito().equals(StatoDebitoType.SOSP)) {
				contabilizzazioniSosp.add(contabilizzazione);
			} else if(contabilizzazione.getStatoDebito().equals(StatoDebitoType.NOLIQ)) {
				contabilizzazioniNoliq.add(contabilizzazione);
			}
		}
		listaDiListe.add(contabilizzazioniLiqConStessiDati);
		listaDiListe.add(contabilizzazioniLiqConDatiDiversi);
		listaDiListe.add(contabilizzazioniSosp);
		listaDiListe.add(contabilizzazioniNoliq);
		return listaDiListe;
	}

	public void aggiornaScadenzeDopoContabilizzazione(IdFattura idFattura) throws Exception {
		List<PccScadenza> scadenzeLst = this.scadenzaBD.getScadenzeByIdFattura(idFattura);

		double importoInScadenza = 0;

		for(PccScadenza scadenza: scadenzeLst) {
			importoInScadenza += scadenza.getImportoInScadenza();
		}
		
		if(importoInScadenza > 0) {
			List<PccContabilizzazione> lstCont = this.pccContabilizzazioneBD.getContabilizzazioniByIdFattura(idFattura);
			double importoSospLiq = 0;

			for(PccContabilizzazione contabil: lstCont) {
				if(contabil.getStatoDebito().equals(StatoDebitoType.LIQ) || contabil.getStatoDebito().equals(StatoDebitoType.SOSP)) {
					importoSospLiq += contabil.getImportoMovimento();
				}
			}

			double differenzaNoLiq = importoInScadenza - importoSospLiq;

			if(differenzaNoLiq > 0) {
				for(int i =0; i < scadenzeLst.size(); i++) {
					PccScadenza scad = scadenzeLst.get(i);
					if(scad.getImportoInScadenza() != null && scad.getImportoInScadenza().doubleValue() > 0) {
						if(differenzaNoLiq > 0) {
							Double importoScad = scad.getImportoInScadenza();
							Double pagatoRicontabilizzato = scad.getPagatoRicontabilizzato();
							if(differenzaNoLiq > importoScad) {
								scad.setImportoInScadenza(0d);
								scad.setPagatoRicontabilizzato(pagatoRicontabilizzato  + importoScad);
								this.scadenzaBD.update(scad);
							} else {
								scad.setImportoInScadenza(importoScad - differenzaNoLiq);
								scad.setPagatoRicontabilizzato(pagatoRicontabilizzato + differenzaNoLiq);
								this.scadenzaBD.update(scad);
							}
							differenzaNoLiq -= importoScad;
						} else {
							break;
						}
					}
				}
			}
		}
	}

	public void checkImportoScadenze(IdFattura idFattura, double importoTotalePianoScadenze)throws Exception {
		List<PccContabilizzazione> lstCont = this.pccContabilizzazioneBD.getContabilizzazioniByIdFattura(idFattura);
		double importoSospLiq = 0;

		for(PccContabilizzazione contabil: lstCont) {
			if(contabil.getStatoDebito().equals(StatoDebitoType.LIQ) || contabil.getStatoDebito().equals(StatoDebitoType.SOSP)) {
				importoSospLiq += contabil.getImportoMovimento();
			}
		}
		
		if(importoTotalePianoScadenze > importoSospLiq) {
			throw new OperazioneNonPermessaException("Impossibile impostare un piano scadenze di ["+importoTotalePianoScadenze+"], in quanto il totale degli importi contabilizzati in stato SOSP e LIQ e' inferiore ("+importoSospLiq+").");
		}
	}

	public List<ContabilizzazioneTipo> getContabilizzazioniByIdFattura(IdFattura idFattura) throws Exception {
			List<PccContabilizzazione> pccContabilizzazioneList = this.pccContabilizzazioneBD.getContabilizzazioniByIdFattura(idFattura);
			List<ContabilizzazioneTipo> lst = new ArrayList<ContabilizzazioneTipo>();
			for(PccContabilizzazione contabilizzazione: pccContabilizzazioneList) {
				lst.add(toPcc(contabilizzazione));
			}
			return lst;
		}

	public List<ContabilizzazioneTipo> getContabilizzazioniByIdFatturaDiversoSistemaRichiedenteEIdImportoDiversi(
			IdFattura idFattura, String sistemaRichiedente,
			List<ContabilizzazioneTipo> contabilizzazioniOriginali) throws ServiceException {
		
		List<String> lstId = new ArrayList<String>();
		
		for(ContabilizzazioneTipo cont: contabilizzazioniOriginali) {
			lstId.add(cont.getIdentificativoMovimento());
		}
		
		List<PccContabilizzazione> pccContabilizzazioneList = this.pccContabilizzazioneBD.getContabilizzazioniByIdFatturaDiversoSistemaRichiedenteEIdImportoDiversi(idFattura, sistemaRichiedente, lstId);
		List<ContabilizzazioneTipo> lst = new ArrayList<ContabilizzazioneTipo>();
		for(PccContabilizzazione contabilizzazione: pccContabilizzazioneList) {
			lst.add(toPcc(contabilizzazione));
		}
		return lst;

	}


}
