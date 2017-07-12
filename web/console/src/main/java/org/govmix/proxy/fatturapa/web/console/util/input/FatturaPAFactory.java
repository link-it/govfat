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
package org.govmix.proxy.fatturapa.web.console.util.input;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.web.console.util.input.factory.FatturaPAInputFactoryImpl;
import org.openspcoop2.generic_project.web.factory.FactoryException;
import org.openspcoop2.generic_project.web.factory.WebGenericProjectFactory;
import org.openspcoop2.generic_project.web.impl.jsf1.FactoryJsf1Impl;
import org.openspcoop2.generic_project.web.input.factory.InputFieldFactory;

public class FatturaPAFactory  extends FactoryJsf1Impl implements WebGenericProjectFactory{
	
	private static String FACTORY_NAME = "org.govmix.proxy.fatturapa.web.console.util.input.FatturaPAFactory";
	
	private transient Logger log = null; 

	public FatturaPAFactory(Logger log) {
		super(FACTORY_NAME,log);
		this.log = log;
	}
	
	public FatturaPAFactory(String name,Logger log){
		super(name, log);
		this.log = log;
		this.log.debug("Factory ["+this.getFactoryName()+"] Inizializzata."); 
	}
	
	
	@Override
	public InputFieldFactory getInputFieldFactory() throws FactoryException {
		return new FatturaPAInputFactoryImpl(this,this.log);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 

}
