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

<%
Logger log = LoggerManager.getConsoleLogger();
String tipiAccettati = ConsoleProperties.getInstance(log).getFatturaAttivaCaricamentoTipologieFileAccettati();

String servletUrl = request.getContextPath() + FatturaElettronicaAttivaUploadServlet.FATTURA_ELETTRONICA_ATTIVA_UPLOAD_SERVLET_PATH;

%>

</head>
<body>

	<!-- The file upload form used as target for the file upload widget -->
    <form id="fileupload" action="<%=servletUrl %>" method="POST" enctype="multipart/form-data">
        <!-- The fileupload-buttonbar contains buttons to add/delete files and start/cancel the upload -->
        <div class="row fileupload-buttonbar">
            <div class="col-lg-7">
                <!-- The fileinput-button span is used to style the file input field as button -->
                <span class="btn btn-success fileinput-button">
                    <i class="glyphicon glyphicon-plus"></i>
                    <span>Add files...</span>
                    <input type="file" name="files[]" multiple>
                </span>
                <button type="submit" class="btn btn-primary start">
                    <i class="glyphicon glyphicon-upload"></i>
                    <span>Start upload</span>
                </button>
                <button type="reset" class="btn btn-warning cancel">
                    <i class="glyphicon glyphicon-ban-circle"></i>
                    <span>Cancel upload</span>
                </button>
                <button type="button" class="btn btn-danger delete">
                    <i class="glyphicon glyphicon-trash"></i>
                    <span>Delete</span>
                </button>
                <input type="checkbox" class="toggle">
                <!-- The global file processing state -->
                <span class="fileupload-process"></span>
            </div>
            <!-- The global progress state -->
            <div class="col-lg-5 fileupload-progress fade">
                <!-- The global progress bar -->
                <div class="progress progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100">
                    <div class="progress-bar progress-bar-success" style="width:0%;"></div>
                </div>
                <!-- The extended global progress state -->
                <div class="progress-extended">&nbsp;</div>
            </div>
        </div>
        <!-- The table listing the files available for upload/download -->
        <table role="presentation" class="table table-striped"><tbody class="files"></tbody></table>
    </form>

<!-- The template to display files available for upload -->
<script id="template-upload" type="text/x-tmpl">
{% for (var i=0, file; file=o.files[i]; i++) { %}
    <tr class="template-upload fade">
        <td>
            <span class="preview"></span>
        </td>
        <td>
            <p class="name">{%=file.name%}</p>
            <strong class="error text-danger"></strong>
        </td>
        <td>
            <p class="size">Processing...</p>
            <div class="progress progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100" aria-valuenow="0"><div class="progress-bar progress-bar-success" style="width:0%;"></div></div>
        </td>
        <td>
            {% if (!i && !o.options.autoUpload) { %}
                <button class="btn btn-primary start" disabled>
                    <i class="glyphicon glyphicon-upload"></i>
                    <span>Start</span>
                </button>
            {% } %}
            {% if (!i) { %}
                <button class="btn btn-warning cancel">
                    <i class="glyphicon glyphicon-ban-circle"></i>
                    <span>Cancel</span>
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
        <td>
            <span class="preview">
                {% if (file.thumbnailUrl) { %}
                    <a href="{%=file.url%}" title="{%=file.name%}" download="{%=file.name%}" data-gallery><img src="{%=file.thumbnailUrl%}"></a>
                {% } %}
            </span>
        </td>
        <td>
            <p class="name">
                {% if (file.url) { %}
                    <a href="{%=file.url%}" title="{%=file.name%}" download="{%=file.name%}" {%=file.thumbnailUrl?'data-gallery':''%}>{%=file.name%}</a>
                {% } else { %}
                    <span>{%=file.name%}</span>
                {% } %}
            </p>
            {% if (file.error) { %}
                <div><span class="label label-danger">Error</span> {%=file.error%}</div>
            {% } %}
        </td>
        <td>
            <span class="size">{%=o.formatFileSize(file.size)%}</span>
        </td>
        <td>
            {% if (file.deleteUrl) { %}
                <button class="btn btn-danger delete" data-type="{%=file.deleteType%}" data-url="{%=file.deleteUrl%}"{% if (file.deleteWithCredentials) { %} data-xhr-fields='{"withCredentials":true}'{% } %}>
                    <i class="glyphicon glyphicon-trash"></i>
                    <span>Delete</span>
                </button>
                <input type="checkbox" name="delete" value="1" class="toggle">
            {% } else { %}
                <button class="btn btn-warning cancel">
                    <i class="glyphicon glyphicon-ban-circle"></i>
                    <span>Cancel</span>
                </button>
            {% } %}
        </td>
    </tr>
{% } %}
</script>
<!-- blueimp Gallery script -->
<!-- <script src="//blueimp.github.io/Gallery/js/jquery.blueimp-gallery.min.js"></script> -->

<script src="../../fileupload/js/jquery.min.js"></script>
<!-- The jQuery UI widget factory, can be omitted if jQuery UI is already included -->
<script src="../../fileupload/js/vendor/jquery.ui.widget.js"></script>
<!-- The Templates plugin is included to render the upload/download listings -->
<script src="../../fileupload/js/tmpl.min.js"></script>
<!-- The Load Image plugin is included for the preview images and image resizing functionality -->
<script src="../../fileupload/js/load-image.all.min.js"></script>
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
// url: '<%=servletUrl %>',
jQuery(function () {
    'use strict';

    // Initialize the jQuery File Upload widget:
    jQuery('#fileupload').fileupload({
        // Uncomment the following to send cross-domain cookies:
        //xhrFields: {withCredentials: true},
      
        disableImageResize: true,
        maxFileSize: 999000,
        acceptFileTypes: /(\.|\/)(<%=tipiAccettati %>)$/i
    });

 // Upload server status check for browsers with CORS support:
    if (jQuery.support.cors) {
    	jQuery.ajax({
            url: '<%=servletUrl %>',
            type: 'OPTIONS'
        }).fail(function () {
        	jQuery('<div class="alert alert-danger"/>')
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