package it.konga.framework.kObjects;

/** Multipurpose Internet Mail Extensions
 * @author  Giampaolo Saporito
 * @date 02.10.2014
 * */
public enum MimeType
{
	INVALID("",""),
	AVI("video/avi","avi"),
	BMP("mage/bmp","bmp"),
	CSS("text/css","css"),
	DTD("application/xml-dtd","dtd"),
	DOC("application/msword","doc"),
	DOCX("application/vnd.openxmlformats-officedocument.wordprocessingml.document","docx"),
	EXE("application/octet-stream","exe"),
	GIF("image/gif","gif"),
	GZ("application/x-gzip","gz"),
	HTML("text/html","gtml"),
	JAR("application/java-archive","jar"),
	JPG("image/jpeg","jpg"),
	JS("application/x-javascript","js"),
	MIDI("audio/x-midi","midi"),
	MP3("audio/mpeg","mp3"),
	MPEG("audio/mpeg","mpeg"),
	PDF("application/pdf","pdf"),
	PNG("image/png","png"),
	POTX("application/vnd.openxmlformats-officedocument.presentationml.template","potx"),
	PPSX("application/vnd.openxmlformats-officedocument.presentationml.slideshow","ppsx"),
	PPT("application/vnd.ms-powerpoint","ppt"),
	PPTX("application/vnd.openxmlformats-officedocument.presentationml.presentation","pptx"),
	TGZ("application/x-tar","tgz"),
	TXT("text/plain","txt"),
	WAV("audio/wav","wav"),
	XLS("application/vnd.ms-excel","xls"),
	XLSX("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet","xlsx"),
	XML("application/xml","xml"),
	ZIP("application/zip","zip");
	
	private final String mediaType;
	private final String extension;
	
	MimeType(String mediaType, String extension)
	{
		this.mediaType = mediaType;
		this.extension = extension;
	}
	/** torna la stringa descrittiva del tipo di media */
	public String getMediaType() {return mediaType;}
	/** torna l'estenzione utilizzata più comune per questo tipo di file */
	public String extension() 	{return extension;}

}//EO MimeType