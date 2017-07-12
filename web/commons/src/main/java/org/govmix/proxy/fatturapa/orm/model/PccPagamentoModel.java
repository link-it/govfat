/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2017 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2017 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
package org.govmix.proxy.fatturapa.orm.model;

import org.govmix.proxy.fatturapa.orm.PccPagamento;

import org.openspcoop2.generic_project.beans.AbstractModel;
import org.openspcoop2.generic_project.beans.IField;
import org.openspcoop2.generic_project.beans.Field;
import org.openspcoop2.generic_project.beans.ComplexField;


/**     
 * Model PccPagamento 
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class PccPagamentoModel extends AbstractModel<PccPagamento> {

	public PccPagamentoModel(){
	
		super();
	
		this.IMPORTO_PAGATO = new Field("importoPagato",double.class,"PccPagamento",PccPagamento.class);
		this.NATURA_SPESA = new Field("naturaSpesa",java.lang.String.class,"PccPagamento",PccPagamento.class);
		this.CAPITOLI_SPESA = new Field("capitoliSpesa",java.lang.String.class,"PccPagamento",PccPagamento.class);
		this.ESTREMI_IMPEGNO = new Field("estremiImpegno",java.lang.String.class,"PccPagamento",PccPagamento.class);
		this.NUMERO_MANDATO = new Field("numeroMandato",java.lang.String.class,"PccPagamento",PccPagamento.class);
		this.DATA_MANDATO = new Field("dataMandato",java.util.Date.class,"PccPagamento",PccPagamento.class);
		this.ID_FISCALE_IVA_BENEFICIARIO = new Field("idFiscaleIvaBeneficiario",java.lang.String.class,"PccPagamento",PccPagamento.class);
		this.CODICE_CIG = new Field("CodiceCig",java.lang.String.class,"PccPagamento",PccPagamento.class);
		this.CODICE_CUP = new Field("CodiceCup",java.lang.String.class,"PccPagamento",PccPagamento.class);
		this.DESCRIZIONE = new Field("descrizione",java.lang.String.class,"PccPagamento",PccPagamento.class);
		this.ID_FATTURA = new org.govmix.proxy.fatturapa.orm.model.IdFatturaModel(new Field("idFattura",org.govmix.proxy.fatturapa.orm.IdFattura.class,"PccPagamento",PccPagamento.class));
		this.DATA_RICHIESTA = new Field("dataRichiesta",java.util.Date.class,"PccPagamento",PccPagamento.class);
		this.DATA_QUERY = new Field("dataQuery",java.util.Date.class,"PccPagamento",PccPagamento.class);
	
	}
	
	public PccPagamentoModel(IField father){
	
		super(father);
	
		this.IMPORTO_PAGATO = new ComplexField(father,"importoPagato",double.class,"PccPagamento",PccPagamento.class);
		this.NATURA_SPESA = new ComplexField(father,"naturaSpesa",java.lang.String.class,"PccPagamento",PccPagamento.class);
		this.CAPITOLI_SPESA = new ComplexField(father,"capitoliSpesa",java.lang.String.class,"PccPagamento",PccPagamento.class);
		this.ESTREMI_IMPEGNO = new ComplexField(father,"estremiImpegno",java.lang.String.class,"PccPagamento",PccPagamento.class);
		this.NUMERO_MANDATO = new ComplexField(father,"numeroMandato",java.lang.String.class,"PccPagamento",PccPagamento.class);
		this.DATA_MANDATO = new ComplexField(father,"dataMandato",java.util.Date.class,"PccPagamento",PccPagamento.class);
		this.ID_FISCALE_IVA_BENEFICIARIO = new ComplexField(father,"idFiscaleIvaBeneficiario",java.lang.String.class,"PccPagamento",PccPagamento.class);
		this.CODICE_CIG = new ComplexField(father,"CodiceCig",java.lang.String.class,"PccPagamento",PccPagamento.class);
		this.CODICE_CUP = new ComplexField(father,"CodiceCup",java.lang.String.class,"PccPagamento",PccPagamento.class);
		this.DESCRIZIONE = new ComplexField(father,"descrizione",java.lang.String.class,"PccPagamento",PccPagamento.class);
		this.ID_FATTURA = new org.govmix.proxy.fatturapa.orm.model.IdFatturaModel(new ComplexField(father,"idFattura",org.govmix.proxy.fatturapa.orm.IdFattura.class,"PccPagamento",PccPagamento.class));
		this.DATA_RICHIESTA = new ComplexField(father,"dataRichiesta",java.util.Date.class,"PccPagamento",PccPagamento.class);
		this.DATA_QUERY = new ComplexField(father,"dataQuery",java.util.Date.class,"PccPagamento",PccPagamento.class);
	
	}
	
	

	public IField IMPORTO_PAGATO = null;
	 
	public IField NATURA_SPESA = null;
	 
	public IField CAPITOLI_SPESA = null;
	 
	public IField ESTREMI_IMPEGNO = null;
	 
	public IField NUMERO_MANDATO = null;
	 
	public IField DATA_MANDATO = null;
	 
	public IField ID_FISCALE_IVA_BENEFICIARIO = null;
	 
	public IField CODICE_CIG = null;
	 
	public IField CODICE_CUP = null;
	 
	public IField DESCRIZIONE = null;
	 
	public org.govmix.proxy.fatturapa.orm.model.IdFatturaModel ID_FATTURA = null;
	 
	public IField DATA_RICHIESTA = null;
	 
	public IField DATA_QUERY = null;
	 

	@Override
	public Class<PccPagamento> getModeledClass(){
		return PccPagamento.class;
	}
	
	@Override
	public String toString(){
		if(this.getModeledClass()!=null){
			return this.getModeledClass().getName();
		}else{
			return "N.D.";
		}
	}

}