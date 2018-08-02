/**
 * 
 */
package org.govmix.proxy.fatturapa.web.timers;

import org.govmix.fatturapa.parer.versamento.request.ChiaveType;
import org.govmix.proxy.fatturapa.orm.FatturaElettronica;

/**
 * @author Bussu Giovanni (bussu@link.it)
 * @author  $Author: bussu $
 * @version $ Rev: 12563 $, $Date: 31 lug 2018 $
 * 
 */
public class ConservazioneUtils {

	
	public static ChiaveType getChiave(FatturaElettronica fattura) {
		ChiaveType chiave = new ChiaveType();
		chiave.setNumero(fattura.getNumero() + "_" + fattura.getCedentePrestatoreCodiceFiscale());
		chiave.setAnno(fattura.getAnno());
		chiave.setTipoRegistro("FATTURE PASSIVE");
		return chiave;
	}

	public static ChiaveType getChiaveLotto(FatturaElettronica fattura) {
		ChiaveType chiave = new ChiaveType();
		chiave.setNumero(""+fattura.getLottoFatture().getIdentificativoSdi());
		chiave.setAnno(fattura.getAnno());
		chiave.setTipoRegistro("LOTTI_FATTURE");
		return chiave;
	}

}
