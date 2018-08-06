<%@page import="org.govmix.proxy.fatturapa.web.console.mbean.LoginMBean"%>
<%@page import="java.util.Locale"%>
<%@page import="org.govmix.proxy.fatturapa.web.console.util.Utils"%>
<%@page import="org.govmix.proxy.fatturapa.web.console.servlet.FatturaElettronicaAttivaUploadServlet"%>
<%@page import="org.govmix.proxy.fatturapa.web.commons.utils.LoggerManager"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="org.govmix.proxy.fatturapa.web.console.util.ConsoleProperties"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<!-- Force latest IE rendering engine or ChromeFrame if installed -->
<!--[if IE]><meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"><![endif]-->
<meta charset="UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Form Fattura</title>
<!-- Bootstrap styles -->
<link rel="stylesheet" href="../../fileupload/css/bootstrap.min.css">
<!-- Generic page styles -->
<link rel="stylesheet" href="../../fileupload/css/style.css">
<!-- CSS to style the file input field as button and adjust the Bootstrap progress bars -->
<link rel="stylesheet" href="../../fileupload/css/jquery.fileupload.css">
<!-- Icone applicazione -->
<link rel="stylesheet" href="../../css/icons.css">
<%
Logger log = LoggerManager.getConsoleLogger();
String tipiAccettati = ConsoleProperties.getInstance(log).getFatturaAttivaCaricamentoTipologieFileAccettati();
int numeroMassimoFileAccettati = ConsoleProperties.getInstance(log).getFatturaAttivaCaricamentoMaxNumeroFile();
String servletUrl = request.getContextPath() + FatturaElettronicaAttivaUploadServlet.FATTURA_ELETTRONICA_ATTIVA_UPLOAD_SERVLET_PATH;
Long dimensioneMassimaFile = ConsoleProperties.getInstance(log).getFatturaAttivaCaricamentoDimensioneMassimaFile();

LoginMBean lb = (LoginMBean) session.getAttribute("loginBean");
Locale locale = lb.getCurrentLocal();

String addLabel = Utils.getInstance().getMessageFromResourceBundle("fileUpload.addControlLabel",locale);
String uploadLabel = Utils.getInstance().getMessageFromResourceBundle("fileUpload.uploadControlLabel",locale);
String cancelLabel = Utils.getInstance().getMessageFromResourceBundle("fileUpload.cancelEntryControlLabel",locale);
String deleteAllLabel = Utils.getInstance().getMessageFromResourceBundle("fileUpload.clearAllControlLabel",locale);
String deleteLabel = Utils.getInstance().getMessageFromResourceBundle("fileUpload.clearControlLabel",locale);
String erroreLabel = Utils.getInstance().getMessageFromResourceBundle("fileUpload.errorLabel",locale);
String processingLabel = Utils.getInstance().getMessageFromResourceBundle("fileUpload.processingLabel",locale);
%>

</head>
<body>

	<!-- The file upload form used as target for the file upload widget -->
    <form id="fileupload" method="POST" enctype="multipart/form-data">
        <!-- The fileupload-buttonbar contains buttons to add/delete files and start/cancel the upload -->
        <div class="row fileupload-buttonbar">
            <div class="col-lg-7">
            	<div class="rich-fileupload-toolbar-decor">
	                <!-- The fileinput-button span is used to style the file input field as button -->
	                <span class="btn icon-add fileinput-button" id="fiBtn">
	                    <span><%=addLabel %></span>
	                    <input type="file" name="files[]" multiple/>
	                </span>
	                <div style="display: none;">
		                <button type="submit" class="btn icon-start-upload start">
		                    <span><%=uploadLabel %></span>
		                </button>
		                <button type="reset" class="btn icon-cancel cancel">
		                    <span><%=cancelLabel %></span>
		                </button>
	                 </div>
	                <div style="float: right; padding-right: 13px;">
		                <button type="button" class="btn icon-delete delete">
		                    <span><%=deleteAllLabel %></span>
		                </button>
		                <span>
		                	<input type="checkbox" class="toggle">
		                </span>
	                </div>
	                <!-- The global file processing state -->
	                <span class="fileupload-process"></span>
                </div>
            </div>
            <!-- The global progress state -->
            <div class="col-lg-5 fileupload-progress fade">
                <!-- The global progress bar -->
                <div class="progress progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100">
                    <div class="progress-bar progress-bar-success" style="width:0%;"></div>
                </div>
                <!-- The extended global progress state -->
                <div class="progress-extended"></div>
            </div>
        </div>
        <div class="listaFile rich-fileupload-list-overflow">
        	<!-- The table listing the files available for upload/download -->
        	<table role="presentation" class="table table-striped"><tbody class="files"></tbody></table>
        </div>
    </form>
<!-- The template to display files available for upload -->
<script id="template-upload" type="text/x-tmpl">
{% for (var i=0, file; file=o.files[i]; i++) { %}
    <tr class="template-upload fade">
        <td >
            <p class="name">{%=file.name%}</p>
			<p class="size"><%=processingLabel %></p>
        </td>
		 <td style="width: 25%">
			<strong class="error text-danger"></strong>
            <div class="progress progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100" aria-valuenow="0"><div class="progress-bar progress-bar-success" style="width:0%;"></div></div>
        </td>
        <td style="width: 26%">
            {% if (!i && !o.options.autoUpload) { %}
				<div style="display: none;">
                <button class="btn icon-start-upload start" disabled>
                    <span><%=uploadLabel %></span>
                </button>
				</div>
            {% } %}
            {% if (!i) { %}
                <button class="btn icon-cancel cancel">
                    <span><%=cancelLabel %></span>
                </button>
            {% } %}
        </td>
    </tr>
{% } %}
</script>
<!-- The template to display files available for download -->
<script id="template-download" type="text/x-tmpl">
{% for (var i=0, file; file=o.files[i]; i++) { %}
    <tr class="template-download fade">
        <td >
            <p class="name">
                <span>{%=file.name%}</span>
				{% if (!file.error) { %}
					<span class="iconCaricamentoOk icon-ok"></span>
				{% } %}
            </p>
			<span class="size">{%=o.formatFileSize(file.size)%}</span>
        </td>
        <td style="width: 25%">
            {% if (file.error) { %}
                <div><span class="label label-danger"><%=erroreLabel %></span> {%=file.error%}</div>
            {% } %}
        </td>
        <td style="width: 26%">
            {% if (file.deleteUrl) { %}
				<div>
                	<button class="btn icon-delete delete" data-type="{%=file.deleteType%}" data-url="{%=file.deleteUrl%}"{% if (file.deleteWithCredentials) { %} data-xhr-fields='{"withCredentials":true}'{% } %}>
                    	<span><%=deleteLabel %></span>
                	</button>
                	<input type="checkbox" name="delete" value="1" class="toggle">
				</div>
            {% } else { %}
                <button class="btn icon-cancel cancel">
                    <span><%=cancelLabel %></span>
                </button>
            {% } %}
        </td>
    </tr>
{% } %}
</script>
<script src="../../fileupload/js/jquery.min.js"></script>
<!-- The jQuery UI widget factory, can be omitted if jQuery UI is already included -->
<script src="../../fileupload/js/vendor/jquery.ui.widget.js"></script>
<!-- The Templates plugin is included to render the upload/download listings -->
<script src="../../fileupload/js/tmpl.min.js"></script>
<!-- The Load Image plugin is included for the preview images and image resizing functionality -->
<script src="../../fileupload/js/load-image.js"></script>
<script src="../../fileupload/js/load-image-scale.js"></script>
<script src="../../fileupload/js/load-image-meta.js"></script>
<script src="../../fileupload/js/load-image-exif.js"></script>
<script src="../../fileupload/js/load-image-exif-map.js"></script>
<script src="../../fileupload/js/load-image-orientation.js"></script>
<!-- The Canvas to Blob plugin is included for image resizing functionality -->
<script src="../../fileupload/js/canvas-to-blob.min.js"></script>
<!-- Bootstrap JS is not required, but included for the responsive demo navigation -->
<script src="../../fileupload/js/bootstrap.min.js"></script>
<!-- blueimp Gallery script -->
<script src="../../fileupload/js/jquery.blueimp-gallery.min.js"></script>
<!-- The Iframe Transport is required for browsers without support for XHR file uploads -->
<script src="../../fileupload/js/jquery.iframe-transport.js"></script>
<!-- The basic File Upload plugin -->
<script src="../../fileupload/js/jquery.fileupload.js"></script>
<!-- The File Upload processing plugin -->
<script src="../../fileupload/js/jquery.fileupload-process.js"></script>
<!-- The File Upload image preview & resize plugin -->
<script src="../../fileupload/js/jquery.fileupload-image.js"></script>
<!-- The File Upload audio preview plugin -->
<script src="../../fileupload/js/jquery.fileupload-audio.js"></script>
<!-- The File Upload video preview plugin -->
<script src="../../fileupload/js/jquery.fileupload-video.js"></script>
<!-- The File Upload validation plugin -->
<script src="../../fileupload/js/jquery.fileupload-validate.js"></script>
<!-- The File Upload user interface plugin -->
<script src="../../fileupload/js/jquery.fileupload-ui.js"></script>
<script>
/*
 * jQuery File Upload Plugin JS Example
 * https://github.com/blueimp/jQuery-File-Upload
 *
 * Copyright 2010, Sebastian Tschan
 * https://blueimp.net
 *
 * Licensed under the MIT license:
 * https://opensource.org/licenses/MIT  
 */

/* global $, window */
$(function () {
    'use strict';

    // Initialize the jQuery File Upload widget:
    $('#fileupload').fileupload({
        // Uncomment the following to send cross-domain cookies:
        //xhrFields: {withCredentials: true},
        autoUpload : true,
        sequentialUploads: true,
        progressInterval: 200,
        url: '<%=servletUrl %>',
        disableImageLoad : true,
        disableImagePreview : true,
      	maxNumberOfFiles : <%=numeroMassimoFileAccettati %>,
        disableImageResize: true,
        <% if(dimensioneMassimaFile != null){%>
	        maxFileSize: <%= dimensioneMassimaFile %>,
        <% } %>
        acceptFileTypes: /(\.|\/)(<%=tipiAccettati %>)$/i
    });
    
    // evento scelta file
    $('#fileupload').bind('fileuploadadd', function (e, data) {
      //  console.log('add File scelto');
    });
    
    // evento upload ok
    $('#fileupload').bind('fileuploaddone', function (e, data) {
    	var file = data.jqXHR.responseJSON.files[0];
    //	console.log('upload done');
    	parent.window.aggiungiFile(file);
    });
    
    // evento upload fail
    $('#fileupload').bind('fileuploadfail', function (e, data) {
     //   console.log('upload fail');
        parent.window.endUploadFail();
    });
    
 // evento cancellazione
    $('#fileupload').bind('fileuploaddestroyed', function (e, data) {
    //	console.log('cancellazione file');
    	parent.window.cancellaFile(data);
    	parent.window.endDeleteOk();
    });
 
    // evento upload start
    $('#fileupload').bind('fileuploadstop', function (e, data) {
     //   console.log('stop uploads');
        parent.window.stopUploads();
        
        setTimeout(fixVistaChrome, 200);
    });
    
	function fixVistaChrome(){
        if($('.template-upload.fade').length > 0){
        	$('.template-upload.fade').addClass('in');
        }		
	}
    
    // evento upload start
    $('#fileupload').bind('fileuploadstart', function (e, data) {
     //   console.log('start uploads');
        parent.window.startUploads();
    });
    
 // Upload server status check for browsers with CORS support:
    if ($.support.cors) {
    	$.ajax({
            url: '<%=servletUrl %>',
            type: 'OPTIONS'
        }).fail(function () {
        	$('<div class="alert alert-danger"/>')
                .text('Upload server currently unavailable - ' +
                        new Date())
                .appendTo('#fileupload');
        });
    }

 
});
</script>
<!-- The XDomainRequest Transport is included for cross-domain file deletion for IE 8 and IE 9 -->
<!--[if (gte IE 8)&(lt IE 10)]>
<script src="../../fileupload/js/cors/jquery.xdr-transport.js"></script>
<![endif]-->
</body>
</html>