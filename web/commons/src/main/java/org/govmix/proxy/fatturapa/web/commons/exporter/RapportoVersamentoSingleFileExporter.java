package org.govmix.proxy.fatturapa.web.commons.exporter;

import java.io.OutputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.orm.IdFattura;
import org.govmix.proxy.fatturapa.web.commons.exporter.exception.ExportException;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;

public class RapportoVersamentoSingleFileExporter extends AbstractSingleFileExporter<FatturaElettronica, IdFattura>{
	
	public RapportoVersamentoSingleFileExporter(Logger log) throws ServiceException, NotImplementedException, Exception {
		super(log);
	}

	public RapportoVersamentoSingleFileExporter(Logger log, Connection connection, boolean autocommit) throws ServiceException, NotImplementedException, Exception {
		super(log, connection, autocommit);
	}

	@Override
	public void export(FatturaElettronica object, OutputStream out, FORMAT format) throws ExportException {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRawName(FatturaElettronica object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected List<IdFattura> findIdFattura(String[] ids, boolean isAll) throws ServiceException, NotFoundException {
		try {
			List<IdFattura> idFatturaRichiesti = new ArrayList<IdFattura>();
			for (String idFattura : ids) {
				FatturaElettronica fattura = this.getFatturaBD().getById(Long.parseLong(idFattura));
				IdFattura idFattura2 = this.getFatturaBD().convertToId(fattura);
				idFatturaRichiesti.add(idFattura2);
			}
			return idFatturaRichiesti;
		} catch (NumberFormatException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public String getRawExtension(FatturaElettronica object) {
		// TODO Auto-generated method stub
		return null;
	}

}
