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
package org.govmix.proxy.fatturapa.orm;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.govmix.proxy.fatturapa.orm package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
*/

@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.govmix.proxy.fatturapa.orm
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Utente }
     */
    public Utente createUtente() {
        return new Utente();
    }

    /**
     * Create an instance of {@link UtenteDipartimento }
     */
    public UtenteDipartimento createUtenteDipartimento() {
        return new UtenteDipartimento();
    }

    /**
     * Create an instance of {@link AllegatoFattura }
     */
    public AllegatoFattura createAllegatoFattura() {
        return new AllegatoFattura();
    }

    /**
     * Create an instance of {@link LottoFatture }
     */
    public LottoFatture createLottoFatture() {
        return new LottoFatture();
    }

    /**
     * Create an instance of {@link IdLotto }
     */
    public IdLotto createIdLotto() {
        return org.govmix.proxy.fatturapa.orm.IdLotto.newIdLotto();
    }

    /**
     * Create an instance of {@link DipartimentoPropertyValue }
     */
    public DipartimentoPropertyValue createDipartimentoPropertyValue() {
        return new DipartimentoPropertyValue();
    }

    /**
     * Create an instance of {@link PccUtenteOperazione }
     */
    public PccUtenteOperazione createPccUtenteOperazione() {
        return new PccUtenteOperazione();
    }

    /**
     * Create an instance of {@link PccNotifica }
     */
    public PccNotifica createPccNotifica() {
        return new PccNotifica();
    }

    /**
     * Create an instance of {@link IdEnte }
     */
    public IdEnte createIdEnte() {
        return new IdEnte();
    }

    /**
     * Create an instance of {@link IdOperazione }
     */
    public IdOperazione createIdOperazione() {
        return new IdOperazione();
    }

    /**
     * Create an instance of {@link IdTrasmissione }
     */
    public IdTrasmissione createIdTrasmissione() {
        return new IdTrasmissione();
    }

    /**
     * Create an instance of {@link RegistroPropertyValue }
     */
    public RegistroPropertyValue createRegistroPropertyValue() {
        return new RegistroPropertyValue();
    }

    /**
     * Create an instance of {@link PccDipartimentoOperazione }
     */
    public PccDipartimentoOperazione createPccDipartimentoOperazione() {
        return new PccDipartimentoOperazione();
    }

    /**
     * Create an instance of {@link IdRegistroProperty }
     */
    public IdRegistroProperty createIdRegistroProperty() {
        return new IdRegistroProperty();
    }

    /**
     * Create an instance of {@link PccTracciaTrasmissione }
     */
    public PccTracciaTrasmissione createPccTracciaTrasmissione() {
        return new PccTracciaTrasmissione();
    }

    /**
     * Create an instance of {@link IdRispedizione }
     */
    public IdRispedizione createIdRispedizione() {
        return new IdRispedizione();
    }

    /**
     * Create an instance of {@link IdNotificaDecorrenzaTermini }
     */
    public IdNotificaDecorrenzaTermini createIdNotificaDecorrenzaTermini() {
        return new IdNotificaDecorrenzaTermini();
    }

    /**
     * Create an instance of {@link Dipartimento }
     */
    public Dipartimento createDipartimento() {
        return new Dipartimento();
    }

    /**
     * Create an instance of {@link PccOperazione }
     */
    public PccOperazione createPccOperazione() {
        return new PccOperazione();
    }

    /**
     * Create an instance of {@link PccTracciaTrasmissioneEsito }
     */
    public PccTracciaTrasmissioneEsito createPccTracciaTrasmissioneEsito() {
        return new PccTracciaTrasmissioneEsito();
    }

    /**
     * Create an instance of {@link TracciaSDI }
     */
    public TracciaSDI createTracciaSDI() {
        return new TracciaSDI();
    }

    /**
     * Create an instance of {@link PccRispedizione }
     */
    public PccRispedizione createPccRispedizione() {
        return new PccRispedizione();
    }

    /**
     * Create an instance of {@link IdDipartimento }
     */
    public IdDipartimento createIdDipartimento() {
        return new IdDipartimento();
    }

    /**
     * Create an instance of {@link IdPagamento }
     */
    public IdPagamento createIdPagamento() {
        return new IdPagamento();
    }

    /**
     * Create an instance of {@link IdUtente }
     */
    public IdUtente createIdUtente() {
        return new IdUtente();
    }

    /**
     * Create an instance of {@link IdComunicazione }
     */
    public IdComunicazione createIdComunicazione() {
        return new IdComunicazione();
    }

    /**
     * Create an instance of {@link IdFattura }
     */
    public IdFattura createIdFattura() {
        return org.govmix.proxy.fatturapa.orm.IdFattura.newIdFattura();
    }

    /**
     * Create an instance of {@link IdScadenza }
     */
    public IdScadenza createIdScadenza() {
        return new IdScadenza();
    }

    /**
     * Create an instance of {@link FatturaElettronica }
     */
    public FatturaElettronica createFatturaElettronica() {
        return new FatturaElettronica();
    }

    /**
     * Create an instance of {@link PccPagamento }
     */
    public PccPagamento createPccPagamento() {
        return new PccPagamento();
    }

    /**
     * Create an instance of {@link PccErroreElaborazione }
     */
    public PccErroreElaborazione createPccErroreElaborazione() {
        return new PccErroreElaborazione();
    }

    /**
     * Create an instance of {@link IdContabilizzazione }
     */
    public IdContabilizzazione createIdContabilizzazione() {
        return new IdContabilizzazione();
    }

    /**
     * Create an instance of {@link NotificaEsitoCommittente }
     */
    public NotificaEsitoCommittente createNotificaEsitoCommittente() {
        return new NotificaEsitoCommittente();
    }

    /**
     * Create an instance of {@link Metadato }
     */
    public Metadato createMetadato() {
        return new Metadato();
    }

    /**
     * Create an instance of {@link PccScadenza }
     */
    public PccScadenza createPccScadenza() {
        return new PccScadenza();
    }

    /**
     * Create an instance of {@link NotificaDecorrenzaTermini }
     */
    public NotificaDecorrenzaTermini createNotificaDecorrenzaTermini() {
        return new NotificaDecorrenzaTermini();
    }

    /**
     * Create an instance of {@link IdTraccia }
     */
    public IdTraccia createIdTraccia() {
        return new IdTraccia();
    }

    /**
     * Create an instance of {@link Protocollo }
     */
    public Protocollo createProtocollo() {
        return new Protocollo();
    }

    /**
     * Create an instance of {@link RegistroProperty }
     */
    public RegistroProperty createRegistroProperty() {
        return new RegistroProperty();
    }

    /**
     * Create an instance of {@link Registro }
     */
    public Registro createRegistro() {
        return new Registro();
    }

    /**
     * Create an instance of {@link Evento }
     */
    public Evento createEvento() {
        return new Evento();
    }

    /**
     * Create an instance of {@link PccTraccia }
     */
    public PccTraccia createPccTraccia() {
        return new PccTraccia();
    }

    /**
     * Create an instance of {@link IdDocumento }
     */
    public IdDocumento createIdDocumento() {
        return new IdDocumento();
    }

    /**
     * Create an instance of {@link DipartimentoProperty }
     */
    public DipartimentoProperty createDipartimentoProperty() {
        return new DipartimentoProperty();
    }

    /**
     * Create an instance of {@link PccContabilizzazione }
     */
    public PccContabilizzazione createPccContabilizzazione() {
        return new PccContabilizzazione();
    }

    /**
     * Create an instance of {@link Ente }
     */
    public Ente createEnte() {
        return new Ente();
    }

    /**
     * Create an instance of {@link IdProtocollo }
     */
    public IdProtocollo createIdProtocollo() {
        return new IdProtocollo();
    }

    /**
     * Create an instance of {@link IdRegistro }
     */
    public IdRegistro createIdRegistro() {
        return new IdRegistro();
    }

    /**
     * Create an instance of {@link IdDipartimentoProperty }
     */
    public IdDipartimentoProperty createIdDipartimentoProperty() {
        return new IdDipartimentoProperty();
    }

    /**
     * Create an instance of {@link IdTrasmissioneEsito }
     */
    public IdTrasmissioneEsito createIdTrasmissioneEsito() {
        return new IdTrasmissioneEsito();
    }


 }
