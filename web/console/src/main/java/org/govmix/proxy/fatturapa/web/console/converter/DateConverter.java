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
package org.govmix.proxy.fatturapa.web.console.converter;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;
import org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils;

public class DateConverter  implements Converter {
	public static String DATE_PATTERN = "dd/MM/yyyy";
	//eg 02/02/2012 12:00  (note Rich:calendar has no support for seconds)

	private boolean consentiDataVuota;
	private static Logger log = LoggerManager.getConsoleLogger();
	
	public DateConverter(){
		init(false);
	}
	
	public DateConverter(boolean consentiDataVuota){
		init(consentiDataVuota);
	}
	
	private void init(boolean consentiDataVuota){
		this.consentiDataVuota= consentiDataVuota;
	}
	
	public Object getAsObject(FacesContext context, UIComponent component, String value)
			throws ConverterException {
		Date result = null;
		log.debug("getAsObject ["+value+"]"); 

		if(StringUtils.isEmpty(value)){
			if(!this.isConsentiDataVuota()){
				String msg = Utils.getInstance().getMessageWithParamsFromResourceBundle("commons.dataObbligatoria",value);
				FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR,msg,null);
				throw new ConverterException(m);
			}
			else 
				return null;
		}

		SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
		if(value!= null && value.length() > 0) {
			try {
				result = sdf.parse(value);
			} catch (Exception e) {
				log.error(e.getMessage());
				String msg = Utils.getInstance().getMessageWithParamsFromResourceBundle("commons.formatoDataNonValido",value, DATE_PATTERN);
				FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR,msg,null);
				throw new ConverterException(m);
			}
		}

		return result;
	}

	public String getAsString(FacesContext context, UIComponent component, Object value)
			throws ConverterException {

		log.debug("getAsString ["+value+"]");
		String result = "";
		if(value == null)
			return result;

		SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
		if(value instanceof String){
			String valueStr = (String) value;

			if (valueStr!= null && valueStr.length() > 0) {
				try {
					Date date = sdf.parse(valueStr);
					result = sdf.format(date);
				} catch (Exception e) {
					log.error(e.getMessage());
					String msg = Utils.getInstance().getMessageWithParamsFromResourceBundle("commons.formatoDataNonValido",value, DATE_PATTERN);
					FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR,msg,null);
					throw new ConverterException(m);
				}
			}

		}

		if(value instanceof Date){
			try {
				result = sdf.format(value);
			} catch (Exception e) {
				String msg = Utils.getInstance().getMessageWithParamsFromResourceBundle("commons.formatoDataNonValido",value, DATE_PATTERN);
				FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR,msg,null);
				throw new ConverterException(m);
			}
		}
		return result;
	}

	public boolean isConsentiDataVuota() {
		return consentiDataVuota;
	}

	public void setConsentiDataVuota(boolean consentiDataVuota) {
		this.consentiDataVuota = consentiDataVuota;
	}

}