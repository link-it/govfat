package org.govmix.fatturapa.parer.beans;
import org.govmix.fatturapa.parer.versamento.request.DocumentoType;


public class DocumentoWrapper implements Comparable<DocumentoWrapper>{

	private int index;
	private DocumentoType doc;
	private byte[] raw;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public DocumentoType getDoc() {
		return doc;
	}

	public void setDoc(DocumentoType doc) {
		this.doc = doc;
	}

	public int compareTo(DocumentoWrapper o) {
		return this.index - o.getIndex();
	}

	public byte[] getRaw() {
		return raw;
	}

	public void setRaw(byte[] raw) {
		this.raw = raw;
	}
	
}
