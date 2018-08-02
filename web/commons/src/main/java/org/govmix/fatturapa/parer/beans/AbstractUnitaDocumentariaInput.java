package org.govmix.fatturapa.parer.beans;

import org.govmix.fatturapa.parer.utils.ConservazioneProperties;

public abstract class AbstractUnitaDocumentariaInput {

	private ConservazioneProperties properties;

	public ConservazioneProperties getProperties() {
		return properties;
	}

	public void setProperties(ConservazioneProperties properties) {
		this.properties = properties;
	}

}
