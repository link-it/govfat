/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2020 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2020 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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

import org.govmix.proxy.fatturapa.orm.PccNotifica;

import org.openspcoop2.generic_project.beans.AbstractModel;
import org.openspcoop2.generic_project.beans.IField;
import org.openspcoop2.generic_project.beans.Field;
import org.openspcoop2.generic_project.beans.ComplexField;


/**     
 * Model PccNotifica 
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class PccNotificaModel extends AbstractModel<PccNotifica> {

	public PccNotificaModel(){
	
		super();
	
		this.ID_TRACCIA = new org.govmix.proxy.fatturapa.orm.model.IdTracciaModel(new Field("idTraccia",org.govmix.proxy.fatturapa.orm.IdTraccia.class,"PccNotifica",PccNotifica.class));
		this.ID_DIPARTIMENTO = new org.govmix.proxy.fatturapa.orm.model.IdDipartimentoModel(new Field("idDipartimento",org.govmix.proxy.fatturapa.orm.IdDipartimento.class,"PccNotifica",PccNotifica.class));
		this.STATO_CONSEGNA = new Field("statoConsegna",java.lang.String.class,"PccNotifica",PccNotifica.class);
		this.DATA_CREAZIONE = new Field("dataCreazione",java.util.Date.class,"PccNotifica",PccNotifica.class);
		this.DATA_CONSEGNA = new Field("dataConsegna",java.util.Date.class,"PccNotifica",PccNotifica.class);
		this.PCC_TRACCIA = new org.govmix.proxy.fatturapa.orm.model.PccTracciaModel(new Field("PccTraccia",org.govmix.proxy.fatturapa.orm.PccTraccia.class,"PccNotifica",PccNotifica.class));
		this.DIPARTIMENTO = new org.govmix.proxy.fatturapa.orm.model.DipartimentoModel(new Field("Dipartimento",org.govmix.proxy.fatturapa.orm.Dipartimento.class,"PccNotifica",PccNotifica.class));
	
	}
	
	public PccNotificaModel(IField father){
	
		super(father);
	
		this.ID_TRACCIA = new org.govmix.proxy.fatturapa.orm.model.IdTracciaModel(new ComplexField(father,"idTraccia",org.govmix.proxy.fatturapa.orm.IdTraccia.class,"PccNotifica",PccNotifica.class));
		this.ID_DIPARTIMENTO = new org.govmix.proxy.fatturapa.orm.model.IdDipartimentoModel(new ComplexField(father,"idDipartimento",org.govmix.proxy.fatturapa.orm.IdDipartimento.class,"PccNotifica",PccNotifica.class));
		this.STATO_CONSEGNA = new ComplexField(father,"statoConsegna",java.lang.String.class,"PccNotifica",PccNotifica.class);
		this.DATA_CREAZIONE = new ComplexField(father,"dataCreazione",java.util.Date.class,"PccNotifica",PccNotifica.class);
		this.DATA_CONSEGNA = new ComplexField(father,"dataConsegna",java.util.Date.class,"PccNotifica",PccNotifica.class);
		this.PCC_TRACCIA = new org.govmix.proxy.fatturapa.orm.model.PccTracciaModel(new ComplexField(father,"PccTraccia",org.govmix.proxy.fatturapa.orm.PccTraccia.class,"PccNotifica",PccNotifica.class));
		this.DIPARTIMENTO = new org.govmix.proxy.fatturapa.orm.model.DipartimentoModel(new ComplexField(father,"Dipartimento",org.govmix.proxy.fatturapa.orm.Dipartimento.class,"PccNotifica",PccNotifica.class));
	
	}
	
	

	public org.govmix.proxy.fatturapa.orm.model.IdTracciaModel ID_TRACCIA = null;
	 
	public org.govmix.proxy.fatturapa.orm.model.IdDipartimentoModel ID_DIPARTIMENTO = null;
	 
	public IField STATO_CONSEGNA = null;
	 
	public IField DATA_CREAZIONE = null;
	 
	public IField DATA_CONSEGNA = null;
	 
	public org.govmix.proxy.fatturapa.orm.model.PccTracciaModel PCC_TRACCIA = null;
	 
	public org.govmix.proxy.fatturapa.orm.model.DipartimentoModel DIPARTIMENTO = null;
	 

	@Override
	public Class<PccNotifica> getModeledClass(){
		return PccNotifica.class;
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