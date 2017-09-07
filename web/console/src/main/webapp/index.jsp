<!-- 
  ProxyFatturaPA - Gestione del formato Fattura Elettronica 
  http://www.gov4j.it/fatturapa
  
  Copyright (c) 2014-2017 Link.it srl (http://link.it). 
  Copyright (c) 2014-2017 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
  
  This program is free software: you can redistribute it and/or modify
  it under the terms of the GNU General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.
 
  This program is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.
 
  You should have received a copy of the GNU General Public License
  along with this program.  If not, see <http://www.gnu.org/licenses/>.
-->

<% 

String scheme = request.getScheme();             // http
String serverName = request.getServerName();     // hostname.com
int serverPort = request.getServerPort();        // 80
String contextPath = request.getContextPath();   // /mywebapp

String destinationUrl = "/fattura/list/listaFatture.jsf";

//if(ConfigProperties.getInstance().isLoginApplication())
//	destinationUrl = "/public/login.jsf";
// Reconstruct original requesting URL
StringBuilder url = new StringBuilder();
url.append(scheme).append("://").append(serverName);

if (serverPort != 80 && serverPort != 443) {
    url.append(":").append(serverPort);
}

url.append(contextPath).append(destinationUrl);

//response.sendRedirect(request.getContextPath()+"/public/login.jsf");
response.sendRedirect(url.toString());

%>
