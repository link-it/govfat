/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2018 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2018 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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

import org.govmix.proxy.fatturapa.orm.Dipartimento;

import org.openspcoop2.generic_project.beans.AbstractModel;
import org.openspcoop2.generic_project.beans.IField;
import org.openspcoop2.generic_project.beans.Field;
import org.openspcoop2.generic_project.beans.ComplexField;


/**     
 * Model Dipartimento 
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class DipartimentoModel extends AbstractModel<Dipartimento> {

	public DipartimentoModel(){
	
		super();
	
		this.CODICE = new Field("codice",java.lang.String.class,"Dipartimento",Dipartimento.class);
		this.ENTE = new org.govmix.proxy.fatturapa.orm.model.IdEnteModel(new Field("ente",org.govmix.proxy.fatturapa.orm.IdEnte.class,"Dipartimento",Dipartimento.class));
		this.DESCRIZIONE = new Field("descrizione",java.lang.String.class,"Dipartimento",Dipartimento.class);
		this.FATTURAZIONE_ATTIVA = new Field("fatturazioneAttiva",boolean.class,"Dipartimento",Dipartimento.class);
		this.ID_PROCEDIMENTO = new Field("idProcedimento",java.lang.String.class,"Dipartimento",Dipartimento.class);
		this.ID_PROCEDIMENTO_B2B = new Field("idProcedimentoB2B",java.lang.String.class,"Dipartimento",Dipartimento.class);
		this.FIRMA_AUTOMATICA = new Field("firmaAutomatica",boolean.class,"Dipartimento",Dipartimento.class);
		this.ACCETTAZIONE_AUTOMATICA = new Field("accettazioneAutomatica",boolean.class,"Dipartimento",Dipartimento.class);
		this.MODALITA_PUSH = new Field("modalitaPush",boolean.class,"Dipartimento",Dipartimento.class);
		this.REGISTRO = new org.govmix.proxy.fatturapa.orm.model.IdRegistroModel(new Field("registro",org.govmix.proxy.fatturapa.orm.IdRegistro.class,"Dipartimento",Dipartimento.class));
		this.LISTA_EMAIL_NOTIFICHE = new Field("listaEmailNotifiche",java.lang.String.class,"Dipartimento",Dipartimento.class);
		this.DIPARTIMENTO_PROPERTY_VALUE = new org.govmix.proxy.fatturapa.orm.model.DipartimentoPropertyValueModel(new Field("DipartimentoPropertyValue",org.govmix.proxy.fatturapa.orm.DipartimentoPropertyValue.class,"Dipartimento",Dipartimento.class));
	
	}
	
	public DipartimentoModel(IField father){
	
		super(father);
	
		this.CODICE = new ComplexField(father,"codice",java.lang.String.class,"Dipartimento",Dipartimento.class);
		this.ENTE = new org.govmix.proxy.fatturapa.orm.model.IdEnteModel(new ComplexField(father,"ente",org.govmix.proxy.fatturapa.orm.IdEnte.class,"Dipartimento",Dipartimento.class));
		this.DESCRIZIONE = new ComplexField(father,"descrizione",java.lang.String.class,"Dipartimento",Dipartimento.class);
		this.FATTURAZIONE_ATTIVA = new ComplexField(father,"fatturazioneAttiva",boolean.class,"Dipartimento",Dipartimento.class);
		this.ID_PROCEDIMENTO = new ComplexField(father,"idProcedimento",java.lang.String.class,"Dipartimento",Dipartimento.class);
		this.ID_PROCEDIMENTO_B2B = new ComplexField(father,"idProcedimentoB2B",java.lang.String.class,"Dipartimento",Dipartimento.class);
		this.FIRMA_AUTOMATICA = new ComplexField(father,"firmaAutomatica",boolean.class,"Dipartimento",Dipartimento.class);
		this.ACCETTAZIONE_AUTOMATICA = new ComplexField(father,"accettazioneAutomatica",boolean.class,"Dipartimento",Dipartimento.class);
		this.MODALITA_PUSH = new ComplexField(father,"modalitaPush",boolean.class,"Dipartimento",Dipartimento.class);
		this.REGISTRO = new org.govmix.proxy.fatturapa.orm.model.IdRegistroModel(new ComplexField(father,"registro",org.govmix.proxy.fatturapa.orm.IdRegistro.class,"Dipartimento",Dipartimento.class));
		this.LISTA_EMAIL_NOTIFICHE = new ComplexField(father,"listaEmailNotifiche",java.lang.String.class,"Dipartimento",Dipartimento.class);
		this.DIPARTIMENTO_PROPERTY_VALUE = new org.govmix.proxy.fatturapa.orm.model.DipartimentoPropertyValueModel(new ComplexField(father,"DipartimentoPropertyValue",org.govmix.proxy.fatturapa.orm.DipartimentoPropertyValue.class,"Dipartimento",Dipartimento.class));
	
	}
	
	

	public IField CODICE = null;
	 
	public org.govmix.proxy.fatturapa.orm.model.IdEnteModel ENTE = null;
	 
	public IField DESCRIZIONE = null;
	 
	public IField FATTURAZIONE_ATTIVA = null;
	 
	public IField ID_PROCEDIMENTO = null;
	 
	public IField ID_PROCEDIMENTO_B2B = null;
	 
	public IField FIRMA_AUTOMATICA = null;
	 
	public IField ACCETTAZIONE_AUTOMATICA = null;
	 
	public IField MODALITA_PUSH = null;
	 
	public org.govmix.proxy.fatturapa.orm.model.IdRegistroModel REGISTRO = null;
	 
	public IField LISTA_EMAIL_NOTIFICHE = null;
	 
	public org.govmix.proxy.fatturapa.orm.model.DipartimentoPropertyValueModel DIPARTIMENTO_PROPERTY_VALUE = null;
	 

	@Override
	public Class<Dipartimento> getModeledClass(){
		return Dipartimento.class;
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