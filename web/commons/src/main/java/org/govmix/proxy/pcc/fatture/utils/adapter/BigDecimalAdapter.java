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
package org.govmix.proxy.pcc.fatture.utils.adapter;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class BigDecimalAdapter {

    private BigDecimalAdapter() {
    }

    public static BigDecimal parseBigDecimal(String s) {
        if (s == null) {
            return null;
        }
        return new BigDecimal(s);
    }
    
    public static String printBigDecimal(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return null;
        }
        
        DecimalFormat df = new DecimalFormat("#.##");
        
        return df.format(bigDecimal.doubleValue());
        
    }
}