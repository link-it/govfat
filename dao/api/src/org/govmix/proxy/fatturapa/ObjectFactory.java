/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2015 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2015 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
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
package org.govmix.proxy.fatturapa;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.govmix.proxy.fatturapa package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 * @author Papandrea Giuseppe (papandrea@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
*/

@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.govmix.proxy.fatturapa
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Ente }
     */
    public Ente createEnte() {
        return new Ente();
    }

    /**
     * Create an instance of {@link IdFattura }
     */
    public IdFattura createIdFattura() {
        return new IdFattura();
    }

    /**
     * Create an instance of {@link DipartimentoProperty }
     */
    public DipartimentoProperty createDipartimentoProperty() {
        return new DipartimentoProperty();
    }

    /**
     * Create an instance of {@link IdEnte }
     */
    public IdEnte createIdEnte() {
        return new IdEnte();
    }

    /**
     * Create an instance of {@link RegistroProperty }
     */
    public RegistroProperty createRegistroProperty() {
        return new RegistroProperty();
    }

    /**
     * Create an instance of {@link IdRegistro }
     */
    public IdRegistro createIdRegistro() {
        return new IdRegistro();
    }

    /**
     * Create an instance of {@link IdUtente }
     */
    public IdUtente createIdUtente() {
        return new IdUtente();
    }

    /**
     * Create an instance of {@link IdProperty }
     */
    public IdProperty createIdProperty() {
        return new IdProperty();
    }

    /**
     * Create an instance of {@link Utente }
     */
    public Utente createUtente() {
        return new Utente();
    }

    /**
     * Create an instance of {@link AllegatoFattura }
     */
    public AllegatoFattura createAllegatoFattura() {
        return new AllegatoFattura();
    }

    /**
     * Create an instance of {@link Dipartimento }
     */
    public Dipartimento createDipartimento() {
        return new Dipartimento();
    }

    /**
     * Create an instance of {@link IdLotto }
     */
    public IdLotto createIdLotto() {
        return new IdLotto();
    }

    /**
     * Create an instance of {@link FatturaElettronica }
     */
    public FatturaElettronica createFatturaElettronica() {
        return new FatturaElettronica();
    }

    /**
     * Create an instance of {@link UtenteDipartimento }
     */
    public UtenteDipartimento createUtenteDipartimento() {
        return new UtenteDipartimento();
    }

    /**
     * Create an instance of {@link IdDipartimento }
     */
    public IdDipartimento createIdDipartimento() {
        return new IdDipartimento();
    }

    /**
     * Create an instance of {@link DipartimentoPropertyValue }
     */
    public DipartimentoPropertyValue createDipartimentoPropertyValue() {
        return new DipartimentoPropertyValue();
    }

    /**
     * Create an instance of {@link LottoFatture }
     */
    public LottoFatture createLottoFatture() {
        return new LottoFatture();
    }

    /**
     * Create an instance of {@link NotificaEsitoCommittente }
     */
    public NotificaEsitoCommittente createNotificaEsitoCommittente() {
        return new NotificaEsitoCommittente();
    }

    /**
     * Create an instance of {@link IdNotificaDecorrenzaTermini }
     */
    public IdNotificaDecorrenzaTermini createIdNotificaDecorrenzaTermini() {
        return new IdNotificaDecorrenzaTermini();
    }

    /**
     * Create an instance of {@link RegistroPropertyValue }
     */
    public RegistroPropertyValue createRegistroPropertyValue() {
        return new RegistroPropertyValue();
    }

    /**
     * Create an instance of {@link NotificaDecorrenzaTermini }
     */
    public NotificaDecorrenzaTermini createNotificaDecorrenzaTermini() {
        return new NotificaDecorrenzaTermini();
    }

    /**
     * Create an instance of {@link Registro }
     */
    public Registro createRegistro() {
        return new Registro();
    }


 }
