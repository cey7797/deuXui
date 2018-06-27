package deu.com.vo;

public class FileUpVO {
	private String originalName;
	private String uploadName;
	private String size;
	private String upLoadPath;
	private String localPath;
	private String logicalPath;
	private String fileExtension;
	private String mark;
	private String responseCustomValue;
	private String fileMasterNo;
	private String fileSeq;
	private String filePath;
	private String orgFileNm;
	private String reFileNm;
	public String getOriginalName() {
		return originalName;
	}
	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}
	public String getUploadName() {
		return uploadName;
	}
	public void setUploadName(String uploadName) {
		this.uploadName = uploadName;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getUpLoadPath() {
		return upLoadPath;
	}
	public void setUpLoadPath(String upLoadPath) {
		this.upLoadPath = upLoadPath;
	}
	public String getLocalPath() {
		return localPath;
	}
	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}
	public String getLogicalPath() {
		return logicalPath;
	}
	public void setLogicalPath(String logicalPath) {
		this.logicalPath = logicalPath;
	}
	public String getFileExtension() {
		return fileExtension;
	}
	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public String getResponseCustomValue() {
		return responseCustomValue;
	}
	public void setResponseCustomValue(String responseCustomValue) {
		this.responseCustomValue = responseCustomValue;
	}
	public String getFileMasterNo() {
		return fileMasterNo;
	}
	public void setFileMasterNo(String fileMasterNo) {
		this.fileMasterNo = fileMasterNo;
	}
	public String getFileSeq() {
		return fileSeq;
	}
	public void setFileSeq(String fileSeq) {
		this.fileSeq = fileSeq;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getOrgFileNm() {
		return orgFileNm;
	}
	public void setOrgFileNm(String orgFileNm) {
		this.orgFileNm = orgFileNm;
	}
	public String getReFileNm() {
		return reFileNm;
	}
	public void setReFileNm(String reFileNm) {
		this.reFileNm = reFileNm;
	}
	@Override
	public String toString() {
		return "FileUpVO [originalName=" + originalName + ", uploadName="
				+ uploadName + ", size=" + size + ", upLoadPath=" + upLoadPath
				+ ", localPath=" + localPath + ", logicalPath=" + logicalPath
				+ ", fileExtension=" + fileExtension + ", mark=" + mark
				+ ", responseCustomValue=" + responseCustomValue
				+ ", fileMasterNo=" + fileMasterNo + ", fileSeq=" + fileSeq
				+ ", filePath=" + filePath + ", orgFileNm=" + orgFileNm
				+ ", reFileNm=" + reFileNm + "]";
	}
	
}


