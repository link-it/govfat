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


import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.LottoFatture;
import org.govmix.proxy.fatturapa.orm.constants.FormatoArchivioInvioFatturaType;
import org.govmix.proxy.fatturapa.web.commons.consegnaFattura.ConsegnaFatturaParameters;
import org.govmix.proxy.fatturapa.web.commons.consegnaFattura.FatturaDeserializerUtils;
import org.govmix.proxy.fatturapa.web.commons.converter.fattura.ILottoConverter;

public class LottoReaderTest {

	public static void main(String[] args) {
		try {
			byte[] bytes = Files.readAllBytes(Paths.get("/tmp/UBL111/UBL/UBL-Invoice-2.1-Example_addIdentificativo.xml"));
//			byte[] bytes = Files.readAllBytes(Paths.get("/home/bussu/NO_BACKUP/git/linkit-git/GovFat/web/fileAccreditamento/pa/IT01234567890_WS005.xml"));
			ConsegnaFatturaParameters params = new ConsegnaFatturaParameters();
			params.setCodiceDestinatario("AAAAAA");
			params.setDataRicezione(new Date());
			params.setFatturazioneAttiva(false);
			params.setFormatoArchivioInvioFattura(FormatoArchivioInvioFatturaType.XML);
			params.setFormatoFatturaPA("UBL21");
			params.setIdentificativoSdI(123456l);
			params.setMessageId("123456");
			params.setNomeFile("IT01234567890_WS005.xml");
			params.setRaw(bytes);
			params.setLog(Logger.getRootLogger());
			params.setXml(FatturaDeserializerUtils.getLottoXml(params.getFormatoArchivioInvioFattura(), params.getRaw(), params.getIdentificativoSdI(), Logger.getRootLogger()));
	
			ILottoConverter conv = FatturaDeserializerUtils.getLottoConverter(params);

			LottoFatture lotto = conv.getLottoFatture();
			
			System.out.println(lotto.getCodiceDestinatario());
			System.out.println(conv.getCodiceDestinatario());

		} catch(Exception e) {
			e.printStackTrace(System.err);
			System.err.println(e.getMessage());
		}		
		
	}
}
