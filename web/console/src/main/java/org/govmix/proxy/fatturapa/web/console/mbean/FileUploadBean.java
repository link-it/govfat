package org.govmix.proxy.fatturapa.web.console.mbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;

import org.openspcoop2.generic_project.web.impl.jsf1.utils.MessageUtils;
import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;

public class FileUploadBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//	private static Logger log = LoggerManager.getConsoleLogger();
	
	private List<String> nomeFile;
	private List<byte[]> contenuto;
	private List<Integer> dimensioneFile;
	private String fileUploadErrorMessage;
	
	private List<UploadItem> files = new ArrayList<UploadItem>();
//	private boolean isAdd= true;
	private boolean caricato = false;
	private String acceptedTypes = "";
	private int numeroFile = 1;
	
	public FileUploadBean() {
		this.nomeFile = new ArrayList<String>();
		this.contenuto = new ArrayList<byte[]>();
		this.dimensioneFile = new ArrayList<Integer>();
	}

	public void setDto(boolean isAdd, byte[] contenuto, String fileName){
//		this.isAdd = isAdd;
//		this.nomeFile.add(fileName);
//		this.contenuto.add(contenuto);
//		this.setContenuto(contenuto);
	}
	
//	public String getNomeFileLabel() {
//		if(StringUtils.isNotEmpty(this.nomeFile)){
//			return org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageWithParamsFromResourceBundle("fileUpload.nomeFile", this.nomeFile);
//		}
//		return this.nomeFile;
//	}
	
	
//	public String getNomeFile() {
//		return this.nomeFile;
//	}
//	public void setNomeFile(String nomeFile) {
//		this.nomeFile = nomeFile;
//	}

//	public byte[] getContenuto() {
//		return this.contenuto;
//	}
//	public void setContenuto(byte[] contenuto) {
//		this.contenuto = contenuto;
//		this.dimensioneFile = this.contenuto != null ? this.contenuto.length : null;
//	}
//	public String getFileCaricato() {
//		if(this.caricato){
//			if(this.getContenuto() != null && this.getContenuto().length > 0 && this.getNumeroFile() != 1){
//				return "";
//			}
//		}
//		else
//			if(this.getContenuto() != null && this.getContenuto().length > 0){
//				return "";
//			}
//
//		return null;
//		
//	}
//	public void setFileCaricato(String fileCaricato) {
//	}
	public String getDimensioneFile() {
		if(this.dimensioneFile != null){
			return org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageWithParamsFromResourceBundle("fileUpload.dimensioneFile", this.dimensioneFile);
		}
		
		return org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("fileUpload.dimensioneFileNonDisponibile");
	}
	public void setDimensioneFile(String dimensioneFile) {
	}

	public String getFileUploadErrorMessage() {
		return this.fileUploadErrorMessage;
	}

	public void setFileUploadErrorMessage(String fileUploadErrorMessage) {
		this.fileUploadErrorMessage = fileUploadErrorMessage;
	}

	public void setNumeroFile(int numeroFile) {
		this.numeroFile = numeroFile;
	}
	
	public void fileUploadListener(UploadEvent event) {
		this.fileUploadErrorMessage = null;
		UploadItem item = event.getUploadItem();

		if(item!= null && item.getData() != null){
			this.contenuto.add(item.getData());
			this.nomeFile.add(item.getFileName());
			this.dimensioneFile.add(item.getFileSize());
			this.caricato = true;
		}
		else {
			MessageUtils.addErrorMsg("Si e' verificato un errore durante il caricamento del file. Riprovare.");
			this.fileUploadErrorMessage = "Si e' verificato un errore durante il caricamento del file. Riprovare.";
		}
	}
	
	public final List<UploadItem> getFiles() {
		return this.files;
	}
	
	public final void clear(final ActionEvent e) {
		this.files = new ArrayList<UploadItem>();
		this.nomeFile = new ArrayList<String>();
		this.contenuto = new ArrayList<byte[]>();
		this.dimensioneFile = new ArrayList<Integer>();
		this.caricato = false;
		this.fileUploadErrorMessage = null;
	}

	public int getNumeroFile(){
		return this.numeroFile - this.files.size();
	}
	
	
	public String download(){
	//	FileUploadBean.log.debug("Downloading File: "+this.getNomeFile());
//		try{
//			//recupero informazioni sul file
//
//			// We must get first our context
//			FacesContext context = FacesContext.getCurrentInstance();
//
//			// Then we have to get the Response where to write our file
//			HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
//
//			// Now we create some variables we will use for writting the file to the
//			// response
//			// String filePath = null;
//			int read = 0;
//			byte[] bytes = new byte[1024];
//
//			// This is another important attribute for the header of the response
//			// Here fileName, is a String with the name that you will suggest as a
//			// name to save as
//			// I use the same name as it is stored in the file system of the server.
//			String fileName = this.getNomeFile();
//			
//			int idx =fileName.lastIndexOf(".");
//			String ext = idx > -1 ? fileName.substring(idx +1) : "bin";
//
//			// Setto Proprietà Export File
//			HttpUtilities.setOutputFile(response, true, fileName, org.openspcoop2.utils.resources.MimeTypes.getInstance().getMimeType(ext));
//
//			// Streams we will use to read, write the file bytes to our response
//			ByteArrayInputStream bis = null;
//			OutputStream os = null;
//
//			// First we load the file in our InputStream
//			bis = new ByteArrayInputStream(this.getContenuto());
//			os = response.getOutputStream();
//
//			// While there are still bytes in the file, read them and write them to
//			// our OutputStream
//			while ((read = bis.read(bytes)) != -1) {
//				os.write(bytes, 0, read);
//			}
//
//			// Clean resources
//			os.flush();
//			os.close();
//
//			FacesContext.getCurrentInstance().responseComplete();
//
//			// End of the method
//		}catch (Exception e) {
//			FatturaElettronicaUploadMBean.log.error(e.getMessage(), e);
//			MessageUtils.addErrorMsg(org.openspcoop2.generic_project.web.impl.jsf1.utils.Utils.getInstance().getMessageFromResourceBundle("richiesta.erroreDownLoad")); 
//		}
		return null;
	}

	public String getAcceptedTypes() {
		return this.acceptedTypes;
	}

	public void setAcceptedTypes(String acceptedTypes) {
		this.acceptedTypes = acceptedTypes;
	}

	public List<String> getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(List<String> nomeFile) {
		this.nomeFile = nomeFile;
	}

	public List<byte[]> getContenuto() {
		return contenuto;
	}

	public void setContenuto(List<byte[]> contenuto) {
		this.contenuto = contenuto;
	}

	public void setDimensioneFile(List<Integer> dimensioneFile) {
		this.dimensioneFile = dimensioneFile;
	}
	
}