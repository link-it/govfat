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
package org.govmix.proxy.pcc.fatture.tracciamento;

import org.govmix.proxy.fatturapa.orm.constants.CausaleType;
import org.govmix.proxy.fatturapa.orm.constants.NaturaSpesaType;

public class RiallineamentoBean {

	public RiallineamentoBean(CausaleType causale, NaturaSpesaType naturaSpesa) {
		this.causale = causale;
		this.naturaSpesa = naturaSpesa;
	}
	
	public CausaleType getCausale() {
		return causale;
	}
	public void setCausale(CausaleType causale) {
		this.causale = causale;
	}
	public NaturaSpesaType getNaturaSpesa() {
		return naturaSpesa;
	}
	public void setNaturaSpesa(NaturaSpesaType naturaSpesa) {
		this.naturaSpesa = naturaSpesa;
	}
	private CausaleType causale;
	private NaturaSpesaType naturaSpesa;
}
