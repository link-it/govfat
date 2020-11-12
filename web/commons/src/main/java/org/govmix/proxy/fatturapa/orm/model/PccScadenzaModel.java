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

import org.govmix.proxy.fatturapa.orm.PccScadenza;

import org.openspcoop2.generic_project.beans.AbstractModel;
import org.openspcoop2.generic_project.beans.IField;
import org.openspcoop2.generic_project.beans.Field;
import org.openspcoop2.generic_project.beans.ComplexField;


/**     
 * Model PccScadenza 
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class PccScadenzaModel extends AbstractModel<PccScadenza> {

	public PccScadenzaModel(){
	
		super();
	
		this.IMPORTO_IN_SCADENZA = new Field("importoInScadenza",java.lang.Double.class,"PccScadenza",PccScadenza.class);
		this.IMPORTO_INIZIALE = new Field("importoIniziale",java.lang.Double.class,"PccScadenza",PccScadenza.class);
		this.PAGATO_RICONTABILIZZATO = new Field("pagatoRicontabilizzato",java.lang.Double.class,"PccScadenza",PccScadenza.class);
		this.DATA_SCADENZA = new Field("dataScadenza",java.util.Date.class,"PccScadenza",PccScadenza.class);
		this.ID_FATTURA = new org.govmix.proxy.fatturapa.orm.model.IdFatturaModel(new Field("idFattura",org.govmix.proxy.fatturapa.orm.IdFattura.class,"PccScadenza",PccScadenza.class));
		this.SISTEMA_RICHIEDENTE = new Field("sistemaRichiedente",java.lang.String.class,"PccScadenza",PccScadenza.class);
		this.UTENTE_RICHIEDENTE = new Field("utenteRichiedente",java.lang.String.class,"PccScadenza",PccScadenza.class);
		this.DATA_RICHIESTA = new Field("dataRichiesta",java.util.Date.class,"PccScadenza",PccScadenza.class);
		this.DATA_QUERY = new Field("dataQuery",java.util.Date.class,"PccScadenza",PccScadenza.class);
	
	}
	
	public PccScadenzaModel(IField father){
	
		super(father);
	
		this.IMPORTO_IN_SCADENZA = new ComplexField(father,"importoInScadenza",java.lang.Double.class,"PccScadenza",PccScadenza.class);
		this.IMPORTO_INIZIALE = new ComplexField(father,"importoIniziale",java.lang.Double.class,"PccScadenza",PccScadenza.class);
		this.PAGATO_RICONTABILIZZATO = new ComplexField(father,"pagatoRicontabilizzato",java.lang.Double.class,"PccScadenza",PccScadenza.class);
		this.DATA_SCADENZA = new ComplexField(father,"dataScadenza",java.util.Date.class,"PccScadenza",PccScadenza.class);
		this.ID_FATTURA = new org.govmix.proxy.fatturapa.orm.model.IdFatturaModel(new ComplexField(father,"idFattura",org.govmix.proxy.fatturapa.orm.IdFattura.class,"PccScadenza",PccScadenza.class));
		this.SISTEMA_RICHIEDENTE = new ComplexField(father,"sistemaRichiedente",java.lang.String.class,"PccScadenza",PccScadenza.class);
		this.UTENTE_RICHIEDENTE = new ComplexField(father,"utenteRichiedente",java.lang.String.class,"PccScadenza",PccScadenza.class);
		this.DATA_RICHIESTA = new ComplexField(father,"dataRichiesta",java.util.Date.class,"PccScadenza",PccScadenza.class);
		this.DATA_QUERY = new ComplexField(father,"dataQuery",java.util.Date.class,"PccScadenza",PccScadenza.class);
	
	}
	
	

	public IField IMPORTO_IN_SCADENZA = null;
	 
	public IField IMPORTO_INIZIALE = null;
	 
	public IField PAGATO_RICONTABILIZZATO = null;
	 
	public IField DATA_SCADENZA = null;
	 
	public org.govmix.proxy.fatturapa.orm.model.IdFatturaModel ID_FATTURA = null;
	 
	public IField SISTEMA_RICHIEDENTE = null;
	 
	public IField UTENTE_RICHIEDENTE = null;
	 
	public IField DATA_RICHIESTA = null;
	 
	public IField DATA_QUERY = null;
	 

	@Override
	public Class<PccScadenza> getModeledClass(){
		return PccScadenza.class;
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