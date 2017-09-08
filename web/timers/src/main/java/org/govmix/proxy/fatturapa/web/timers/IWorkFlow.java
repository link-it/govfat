package org.govmix.proxy.fatturapa.web.timers;

import java.sql.Connection;
import java.util.List;

import org.slf4j.Logger;
import org.govmix.proxy.fatturapa.orm.FatturaElettronica;

public interface IWorkFlow {

	public void init(Logger log, Connection connection, int limit) throws Exception;

	public long count() throws Exception;

	public List<FatturaElettronica> getNextListaFatture() throws Exception;

	public void process(FatturaElettronica fattura) throws Exception;

}
