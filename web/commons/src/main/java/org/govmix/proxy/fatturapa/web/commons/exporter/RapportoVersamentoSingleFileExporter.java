package org.govmix.proxy.fatturapa.web.commons.exporter;

import java.io.OutputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.orm.IdFattura;
import org.govmix.proxy.fatturapa.orm.SIP;
import org.govmix.proxy.fatturapa.orm.dao.ISIPService;
import org.govmix.proxy.fatturapa.web.commons.dao.DAOFactory;
import org.govmix.proxy.fatturapa.web.commons.exporter.exception.ExportException;
import org.openspcoop2.generic_project.exception.MultipleResultException;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;

public class RapportoVersamentoSingleFileExporter extends AbstractSingleFileExporter<FatturaElettronica, IdFattura>{
	
	private ISIPService sipDAO;

	public RapportoVersamentoSingleFileExporter(Logger log) throws ServiceException, NotImplementedException, Exception {
		super(log);
		this.sipDAO = DAOFactory.getInstance().getServiceManager().getSIPService();
	}

	public RapportoVersamentoSingleFileExporter(Logger log, Connection connection, boolean autocommit) throws ServiceException, NotImplementedException, Exception {
		super(log, connection, autocommit);
		this.sipDAO = DAOFactory.getInstance().getServiceManager().getSIPService();
	}

	@Override
	public void export(FatturaElettronica object, OutputStream out, FORMAT format) throws ExportException {
		switch(format) {
		case RAW:this.exportAsRaw(object, out);
		break;
		default: throw new ExportException("Formato ["+format+"] non supportato");
		}

	}

	@Override
	public FatturaElettronica convertToObject(IdFattura id) throws ExportException {
		try {
			return this.getFatturaBD().get(id);
		} catch(NotFoundException e) {
			throw new ExportException(e);
		} catch (ServiceException e) {
			throw new ExportException(e);
		}
	}

	@Override
	public FatturaElettronica convertToObject(String id) throws ExportException {
		try {
			return this.getFatturaBD().getById(Long.parseLong(id));
		} catch (NotFoundException e) {
			throw new ExportException(e);
		} catch (ServiceException e) {
			throw new ExportException(e);
		}
	}

	@Override
	public byte[] getRawContent(FatturaElettronica object) {
		try {
			SIP sip = this.sipDAO.get(object.getIdSIP());
			return sip.getRapportoVersamento().getBytes();
		} catch (ServiceException e) {
			log.error(e.getMessage(),e);
		} catch (NotFoundException e) {
			log.error(e.getMessage(),e);
		} catch (MultipleResultException e) {
			log.error(e.getMessage(),e);
		} catch (NotImplementedException e) {
			log.error(e.getMessage(),e);
		}
		return null;
	}

	@Override
	public String getRawName(FatturaElettronica object) {
		try {
			SIP sip = this.sipDAO.get(object.getIdSIP());
			// formato "NUMERO + ANNO + REGISTRO"
			String name =  sip.getNumero() + "_"  + sip.getAnno() +"_" + sip.getRegistro();
			return name; 
		} catch (ServiceException e) {
			log.error(e.getMessage(),e);
		} catch (NotFoundException e) {
			log.error(e.getMessage(),e);
		} catch (MultipleResultException e) {
			log.error(e.getMessage(),e);
		} catch (NotImplementedException e) {
			log.error(e.getMessage(),e);
		}
		return null;
	}

	@Override
	protected List<String> findCodiciDipartimento(String[] ids, boolean fatturazioneAttiva) throws ServiceException, NotFoundException{
		try {
			List<String> idFatturaRichiesti = new ArrayList<String>();
			for (String idFattura : ids) {
				FatturaElettronica fattura = this.getFatturaBD().getById(Long.parseLong(idFattura));
				idFatturaRichiesti.add(fattura.getCodiceDestinatario());
			}
			return idFatturaRichiesti;
		} catch (NumberFormatException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public String getRawExtension(FatturaElettronica object) {
		return "xml";
	}

}
