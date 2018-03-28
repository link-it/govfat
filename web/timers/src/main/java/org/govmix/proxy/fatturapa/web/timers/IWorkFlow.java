package org.govmix.proxy.fatturapa.web.timers;

import java.sql.Connection;
import java.util.List;

import org.apache.log4j.Logger;

public interface IWorkFlow<T> {

	public void init(Logger log, Connection connection, int limit) throws Exception;

	public long count() throws Exception;

	public List<T> getNextLista() throws Exception;

	public void process(T object) throws Exception;

}
