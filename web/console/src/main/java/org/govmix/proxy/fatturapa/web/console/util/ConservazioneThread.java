package org.govmix.proxy.fatturapa.web.console.util;

import java.util.List;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.constants.StatoConservazioneType;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.FatturaBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.filter.FatturaFilter;
import org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager;

public class ConservazioneThread implements Runnable{

	private List<Long> ids = null;
	private boolean isAll;
	private FatturaBD fatturaBD;
	private FatturaFilter fatturaFilter;
	private static Logger log = LoggerManager.getConsoleLogger();
	private StatoConservazioneType statoConservazione;

	public ConservazioneThread() {
		ConservazioneThread.log.debug("Init Conservazione Thread completata.");
	}

	@Override
	public void run() {
		try {
			ConservazioneThread.log.debug("Conservazione Thread inizio elaborazione");
			
			if(isAll){
				this.fatturaBD.inviaInConservazione(fatturaFilter, this.statoConservazione);
			} else {
				this.fatturaBD.inviaInConservazione(ids, this.statoConservazione);
			}

			ConservazioneThread.log.debug("Conservazione Thread elaborazione completata");
		} catch(Exception e) {
			ConservazioneThread.log.error("Conservazione Thread elaborazione terminata con errore: " + e.getMessage(), e);
		}
	}

	public List<Long> getIds() {
		return ids;
	}


	public void setIds(List<Long> ids) {
		this.ids = ids;
	}


	public boolean isAll() {
		return isAll;
	}


	public void setAll(boolean isAll) {
		this.isAll = isAll;
	}


	public FatturaBD getFatturaBD() {
		return fatturaBD;
	}


	public void setFatturaBD(FatturaBD fatturaBD) {
		this.fatturaBD = fatturaBD;
	}


	public FatturaFilter getFatturaFilter() {
		return fatturaFilter;
	}


	public void setFatturaFilter(FatturaFilter fatturaFilter) {
		this.fatturaFilter = fatturaFilter;
	}

	public StatoConservazioneType getStatoConservazione() {
		return statoConservazione;
	}

	public void setStatoConservazione(StatoConservazioneType statoConservazione) {
		this.statoConservazione = statoConservazione;
	}

}
