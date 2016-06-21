/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2016 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2016 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
package org.govmix.proxy.pcc.fatture.serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;

import it.tesoro.fatture.ProxyDatiFatturaRichiestaTipo;
import it.tesoro.fatture.ProxyInserimentoFatturaRichiestaTipo;
import it.tesoro.fatture.ProxyOperazioneContabileRichiestaTipo;
import it.tesoro.fatture.ProxyPagamentoIvaRichiestaTipo;
import it.tesoro.fatture.QueryDatiFatturaRispostaTipo;
import it.tesoro.fatture.QueryInserimentoFatturaRispostaTipo;
import it.tesoro.fatture.QueryOperazioneContabileRispostaTipo;
import it.tesoro.fatture.QueryPagamentoIvaRispostaTipo;
import it.tesoro.fatture.ReadDownloadDocumentoRichiestaTipo;
import it.tesoro.fatture.ReadDownloadDocumentoRispostaTipo;
import it.tesoro.fatture.ReadElencoMovimentiErarioIvaRichiestaTipo;
import it.tesoro.fatture.ReadElencoMovimentiErarioIvaRispostaTipo;
import it.tesoro.fatture.ReadStatoFatturaRichiestaTipo;
import it.tesoro.fatture.ReadStatoFatturaRispostaTipo;

public class JAXBSerializer {

	private Marshaller marshaller;
	private Unmarshaller unmarshaller;
	private static final String NAMESPACE = "http://www.tesoro.it/fatture";
	
	public JAXBSerializer() throws JAXBException, IOException {
		JAXBContext jaxbContext = JAXBContext.newInstance(ProxyOperazioneContabileRichiestaTipo.class.getPackage().getName());
		this.marshaller = jaxbContext.createMarshaller(); 
		this.unmarshaller = jaxbContext.createUnmarshaller(); 
		this.marshaller.setProperty("com.sun.xml.bind.xmlDeclaration", Boolean.FALSE);
	}
	
	public byte[] toXML(ProxyOperazioneContabileRichiestaTipo input) throws JAXBException, IOException {
        ByteArrayOutputStream baos = null;
        try {
        	baos = new ByteArrayOutputStream();
	        JAXBElement<ProxyOperazioneContabileRichiestaTipo> inputj = new JAXBElement<ProxyOperazioneContabileRichiestaTipo>(new QName(NAMESPACE, "proxyOperazioneContabileRichiestaTipo"), ProxyOperazioneContabileRichiestaTipo.class, input);
			this.marshaller.marshal(inputj, baos);
			return baos.toByteArray();
        } finally {
        	if(baos != null) {
    	        baos.flush();
    	        baos.close();
        	} 
        }
	}


	public byte[] toXML(ReadStatoFatturaRichiestaTipo input) throws JAXBException, IOException {
        ByteArrayOutputStream baos = null;
        try {
        	baos = new ByteArrayOutputStream();
	        JAXBElement<ReadStatoFatturaRichiestaTipo> inputj = new JAXBElement<ReadStatoFatturaRichiestaTipo>(new QName(NAMESPACE, "readStatoFatturaRichiestaTipo"), ReadStatoFatturaRichiestaTipo.class, input);
			this.marshaller.marshal(inputj, baos);
			return baos.toByteArray();
        } finally {
        	if(baos != null) {
    	        baos.flush();
    	        baos.close();
        	} 
        }
	}

	public byte[] toXML(ReadElencoMovimentiErarioIvaRichiestaTipo input) throws JAXBException, IOException {
        ByteArrayOutputStream baos = null;
        try {
        	baos = new ByteArrayOutputStream();
	        JAXBElement<ReadElencoMovimentiErarioIvaRichiestaTipo> inputj = new JAXBElement<ReadElencoMovimentiErarioIvaRichiestaTipo>(new QName(NAMESPACE, "readElencoMovimentiErarioIvaRichiestaTipo"), ReadElencoMovimentiErarioIvaRichiestaTipo.class, input);
			this.marshaller.marshal(inputj, baos);
			return baos.toByteArray();
        } finally {
        	if(baos != null) {
    	        baos.flush();
    	        baos.close();
        	} 
        }
	}

	public byte[] toXML(ReadDownloadDocumentoRichiestaTipo input) throws JAXBException, IOException {
        ByteArrayOutputStream baos = null;
        try {
        	baos = new ByteArrayOutputStream();
	        JAXBElement<ReadDownloadDocumentoRichiestaTipo> inputj = new JAXBElement<ReadDownloadDocumentoRichiestaTipo>(new QName(NAMESPACE, "readDownloadDocumentoRichiestaTipo"), ReadDownloadDocumentoRichiestaTipo.class, input);
			this.marshaller.marshal(inputj, baos);
			return baos.toByteArray();
        } finally {
        	if(baos != null) {
    	        baos.flush();
    	        baos.close();
        	} 
        }
	}

	public byte[] toXML(QueryPagamentoIvaRispostaTipo input) throws JAXBException, IOException {
        ByteArrayOutputStream baos = null;
        try {
        	baos = new ByteArrayOutputStream();
	        JAXBElement<QueryPagamentoIvaRispostaTipo> inputj = new JAXBElement<QueryPagamentoIvaRispostaTipo>(new QName(NAMESPACE, "queryPagamentoIvaRispostaTipo"), QueryPagamentoIvaRispostaTipo.class, input);
			this.marshaller.marshal(inputj, baos);
			return baos.toByteArray();
        } finally {
        	if(baos != null) {
    	        baos.flush();
    	        baos.close();
        	} 
        }
	}

	public byte[] toXML(QueryDatiFatturaRispostaTipo input) throws JAXBException, IOException {
        ByteArrayOutputStream baos = null;
        try {
        	baos = new ByteArrayOutputStream();
	        JAXBElement<QueryDatiFatturaRispostaTipo> inputj = new JAXBElement<QueryDatiFatturaRispostaTipo>(new QName(NAMESPACE, "queryDatiFatturaRispostaTipo"), QueryDatiFatturaRispostaTipo.class, input);
			this.marshaller.marshal(inputj, baos);
			return baos.toByteArray();
        } finally {
        	if(baos != null) {
    	        baos.flush();
    	        baos.close();
        	} 
        }
	}

	public byte[] toXML(QueryOperazioneContabileRispostaTipo input) throws JAXBException, IOException {
        ByteArrayOutputStream baos = null;
        try {
        	baos = new ByteArrayOutputStream();
	        JAXBElement<QueryOperazioneContabileRispostaTipo> inputj = new JAXBElement<QueryOperazioneContabileRispostaTipo>(new QName(NAMESPACE, "queryOperazioneContabileRispostaTipo"), QueryOperazioneContabileRispostaTipo.class, input);
			this.marshaller.marshal(inputj, baos);
			return baos.toByteArray();
        } finally {
        	if(baos != null) {
    	        baos.flush();
    	        baos.close();
        	} 
        }
	}

	public byte[] toXML(QueryInserimentoFatturaRispostaTipo input) throws JAXBException, IOException {
        ByteArrayOutputStream baos = null;
        try {
        	baos = new ByteArrayOutputStream();
	        JAXBElement<QueryInserimentoFatturaRispostaTipo> inputj = new JAXBElement<QueryInserimentoFatturaRispostaTipo>(new QName(NAMESPACE, "queryInserimentoFatturaRispostaTipo"), QueryInserimentoFatturaRispostaTipo.class, input);
			this.marshaller.marshal(inputj, baos);
			return baos.toByteArray();
        } finally {
        	if(baos != null) {
    	        baos.flush();
    	        baos.close();
        	} 
        }
	}

	public byte[] toXML(ProxyInserimentoFatturaRichiestaTipo input) throws JAXBException, IOException {
        ByteArrayOutputStream baos = null;
        try {
        	baos = new ByteArrayOutputStream();
	        JAXBElement<ProxyInserimentoFatturaRichiestaTipo> inputj = new JAXBElement<ProxyInserimentoFatturaRichiestaTipo>(new QName(NAMESPACE, "proxyInserimentoFatturaRichiestaTipo"), ProxyInserimentoFatturaRichiestaTipo.class, input);
			this.marshaller.marshal(inputj, baos);
			return baos.toByteArray();
        } finally {
        	if(baos != null) {
    	        baos.flush();
    	        baos.close();
        	} 
        }
	}

	public byte[] toXML(ProxyDatiFatturaRichiestaTipo input) throws JAXBException, IOException {
        ByteArrayOutputStream baos = null;
        try {
        	baos = new ByteArrayOutputStream();
	        JAXBElement<ProxyDatiFatturaRichiestaTipo> inputj = new JAXBElement<ProxyDatiFatturaRichiestaTipo>(new QName(NAMESPACE, "proxyDatiFatturaRichiestaTipo"), ProxyDatiFatturaRichiestaTipo.class, input);
			this.marshaller.marshal(inputj, baos);
			return baos.toByteArray();
        } finally {
        	if(baos != null) {
    	        baos.flush();
    	        baos.close();
        	} 
        }
	}

	public byte[] toXML(ProxyPagamentoIvaRichiestaTipo input) throws JAXBException, IOException {
        ByteArrayOutputStream baos = null;
        try {
        	baos = new ByteArrayOutputStream();
	        JAXBElement<ProxyPagamentoIvaRichiestaTipo> inputj = new JAXBElement<ProxyPagamentoIvaRichiestaTipo>(new QName(NAMESPACE, "proxyPagamentoIvaRichiestaTipo"), ProxyPagamentoIvaRichiestaTipo.class, input);
			this.marshaller.marshal(inputj, baos);
			return baos.toByteArray();
        } finally {
        	if(baos != null) {
    	        baos.flush();
    	        baos.close();
        	} 
        }
	}

	public byte[] toXML(ReadDownloadDocumentoRispostaTipo input) throws JAXBException, IOException {
        ByteArrayOutputStream baos = null;
        try {
        	baos = new ByteArrayOutputStream();
	        JAXBElement<ReadDownloadDocumentoRispostaTipo> inputj = new JAXBElement<ReadDownloadDocumentoRispostaTipo>(new QName(NAMESPACE, "readDownloadDocumentoRispostaTipo"), ReadDownloadDocumentoRispostaTipo.class, input);
			this.marshaller.marshal(inputj, baos);
			return baos.toByteArray();
        } finally {
        	if(baos != null) {
    	        baos.flush();
    	        baos.close();
        	} 
        }
	}

	public byte[] toXML(ReadElencoMovimentiErarioIvaRispostaTipo input) throws JAXBException, IOException {
        ByteArrayOutputStream baos = null;
        try {
        	baos = new ByteArrayOutputStream();
	        JAXBElement<ReadElencoMovimentiErarioIvaRispostaTipo> inputj = new JAXBElement<ReadElencoMovimentiErarioIvaRispostaTipo>(new QName(NAMESPACE, "readElencoMovimentiErarioIvaRispostaTipo"), ReadElencoMovimentiErarioIvaRispostaTipo.class, input);
			this.marshaller.marshal(inputj, baos);
			return baos.toByteArray();
        } finally {
        	if(baos != null) {
    	        baos.flush();
    	        baos.close();
        	} 
        }
	}

	public byte[] toXML(ReadStatoFatturaRispostaTipo input) throws JAXBException, IOException {
        ByteArrayOutputStream baos = null;
        try {
        	baos = new ByteArrayOutputStream();
	        JAXBElement<ReadStatoFatturaRispostaTipo> inputj = new JAXBElement<ReadStatoFatturaRispostaTipo>(new QName(NAMESPACE, "readStatoFatturaRispostaTipo"), ReadStatoFatturaRispostaTipo.class, input);
			this.marshaller.marshal(inputj, baos);
			return baos.toByteArray();
        } finally {
        	if(baos != null) {
    	        baos.flush();
    	        baos.close();
        	} 
        }
	}

	public ProxyOperazioneContabileRichiestaTipo toProxyOperazioneContabileRichiestaTipo(byte[] input) throws JAXBException, IOException {
		ByteArrayInputStream bais = null;
		try {
			bais = new ByteArrayInputStream(input);
			JAXBElement<ProxyOperazioneContabileRichiestaTipo> unmarshal = (JAXBElement<ProxyOperazioneContabileRichiestaTipo>) unmarshaller.unmarshal(bais);
			return unmarshal.getValue();
		} finally {
			if(bais != null) {
				bais.close();
			}
		}
	}

	public ProxyInserimentoFatturaRichiestaTipo toProxyInserimentoFatturaRichiestaTipo(byte[] input) throws JAXBException, IOException {
		ByteArrayInputStream bais = null;
		try {
			bais = new ByteArrayInputStream(input);
			JAXBElement<ProxyInserimentoFatturaRichiestaTipo> unmarshal = (JAXBElement<ProxyInserimentoFatturaRichiestaTipo>) unmarshaller.unmarshal(bais);
			return unmarshal.getValue();
		} finally {
			if(bais != null) {
				bais.close();
			}
		}

	}

	public ProxyDatiFatturaRichiestaTipo toProxyDatiFatturaRichiestaTipo(byte[] input) throws JAXBException, IOException {
		ByteArrayInputStream bais = null;
		try {
			bais = new ByteArrayInputStream(input);
			JAXBElement<ProxyDatiFatturaRichiestaTipo> unmarshal = (JAXBElement<ProxyDatiFatturaRichiestaTipo>) unmarshaller.unmarshal(bais);
			return unmarshal.getValue();
		} finally {
			if(bais != null) {
				bais.close();
			}
		}

	}

	public ProxyPagamentoIvaRichiestaTipo toProxyPagamentoIvaRichiestaTipo(byte[] input) throws JAXBException, IOException {
		ByteArrayInputStream bais = null;
		try {
			bais = new ByteArrayInputStream(input);
			JAXBElement<ProxyPagamentoIvaRichiestaTipo> unmarshal = (JAXBElement<ProxyPagamentoIvaRichiestaTipo>) unmarshaller.unmarshal(bais);
			return unmarshal.getValue();
		} finally {
			if(bais != null) {
				bais.close();
			}
		}

	}
	
	public QueryOperazioneContabileRispostaTipo toQueryOperazioneContabileRispostaTipo(byte[] input) throws JAXBException, IOException {
		ByteArrayInputStream bais = null;
		try {
			bais = new ByteArrayInputStream(input);
			JAXBElement<QueryOperazioneContabileRispostaTipo> unmarshal = (JAXBElement<QueryOperazioneContabileRispostaTipo>) unmarshaller.unmarshal(bais);
			return unmarshal.getValue();
		} finally {
			if(bais != null) {
				bais.close();
			}
		}
	}

	public QueryInserimentoFatturaRispostaTipo toQueryInserimentoFatturaRispostaTipo(byte[] input) throws JAXBException, IOException {
		ByteArrayInputStream bais = null;
		try {
			bais = new ByteArrayInputStream(input);
			JAXBElement<QueryInserimentoFatturaRispostaTipo> unmarshal = (JAXBElement<QueryInserimentoFatturaRispostaTipo>) unmarshaller.unmarshal(bais);
			return unmarshal.getValue();
		} finally {
			if(bais != null) {
				bais.close();
			}
		}

	}

	public QueryDatiFatturaRispostaTipo toQueryDatiFatturaRispostaTipo(byte[] input) throws JAXBException, IOException {
		ByteArrayInputStream bais = null;
		try {
			bais = new ByteArrayInputStream(input);
			JAXBElement<QueryDatiFatturaRispostaTipo> unmarshal = (JAXBElement<QueryDatiFatturaRispostaTipo>) unmarshaller.unmarshal(bais);
			return unmarshal.getValue();
		} finally {
			if(bais != null) {
				bais.close();
			}
		}

	}

	public QueryPagamentoIvaRispostaTipo toQueryPagamentoIvaRispostaTipo(byte[] input) throws JAXBException, IOException {
		ByteArrayInputStream bais = null;
		try {
			bais = new ByteArrayInputStream(input);
			JAXBElement<QueryPagamentoIvaRispostaTipo> unmarshal = (JAXBElement<QueryPagamentoIvaRispostaTipo>) unmarshaller.unmarshal(bais);
			return unmarshal.getValue();
		} finally {
			if(bais != null) {
				bais.close();
			}
		}

	}
}
