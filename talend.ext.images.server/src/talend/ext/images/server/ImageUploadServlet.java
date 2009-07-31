package talend.ext.images.server;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import talend.ext.images.server.util.Uuid;

public class ImageUploadServlet extends HttpServlet {

	private Logger logger = Logger.getLogger(this.getClass());

	private List okFileTypes = null;
	private String defaultFilefieldName="";
	private String defaultCatalogfieldName="";
	private String outputFormat="xml";
	
	private String uploadPath = "/upload"; // the folder to upload
	private String tempPath = "/upload_tmp"; // the folder used to store temporary files of uploading

	private String sourceFileName = "";
	private String sourceFileType = "";
	private String targetUri = "upload";

	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		String types = config.getInitParameter("image-file-types");
		String[] typeArray = types.split("\\|");
		okFileTypes = Arrays.asList(typeArray);
		defaultFilefieldName = config.getInitParameter("default-file-field-name");
		defaultCatalogfieldName =  config.getInitParameter("default-catalog-field-name");
		outputFormat = config.getInitParameter("output-format");

		uploadPath = getServletContext().getRealPath(uploadPath);
		tempPath = getServletContext().getRealPath(tempPath);

		logger.debug("Images Upload Base Path: " + uploadPath);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		targetUri="upload";//reset
		String result=onUpload(request, response);
		
		PrintWriter writer = response.getWriter();
        writer.write(result.toString());
        writer.close();
	}

	private String onUpload(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			// Check that we have a file upload request
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			if (isMultipart) {

				DiskFileItemFactory factory = new DiskFileItemFactory();
				// Set cache buffer 4kb
				factory.setSizeThreshold(4096);
				// If file size overflow store in temporary folder
				factory.setRepository(new File(tempPath));

				// Create a new file upload handler
				ServletFileUpload sevletFileUpload = new ServletFileUpload(
						factory);
				// Set max size, 4m
				sevletFileUpload.setSizeMax(4 * 1024 * 1024);
				sevletFileUpload.setHeaderEncoding("utf8");
				// Create a progress listener
				ProgressListener progressListener = new ProgressListener() {
					public void update(long pBytesRead, long pContentLength,
							int pItems) {
						System.out.println("We are currently reading item "
								+ pItems);
						if (pContentLength == -1) {
							System.out.println("So far, " + pBytesRead
									+ " bytes have been read.");
						} else {
							System.out
									.println("So far, " + pBytesRead + " of "
											+ pContentLength
											+ " bytes have been read.");
						}
					}
				};
				sevletFileUpload.setProgressListener(progressListener);

				// Parse the request
				List fileItems = null;
				
				fileItems = sevletFileUpload.parseRequest(request);
				 
				// Process upload
				if (fileItems != null && fileItems.size() > 0) {
					Object uploadFileItemObj = getFileItem(fileItems, defaultFilefieldName);
					if (uploadFileItemObj != null) {
						FileItem uploadFileItem = (FileItem) uploadFileItemObj;
						String catalogName = "";
						Object catalogNameObj = getFileItem(fileItems, defaultCatalogfieldName);
						if (catalogNameObj != null)
							catalogName = (String) catalogNameObj;

						if (processUploadedFile(catalogName, uploadFileItem,
								true) == 1) {
							logger.info(sourceFileName
									+ " has been uploaded successfully!");
							return buildUploadResult(true,targetUri);
						}
					}

				}

			}
			
		} catch (SizeLimitExceededException e) {
			e.printStackTrace();
			return buildUploadResult(false,"File Size Limit Exceeded Exception! ");
		} catch (Exception e) {
			e.printStackTrace();
			return buildUploadResult(false,"Exception occured during uploading! ");
		}
		
		return buildUploadResult(false,"It seems that Upload Task has not been executed, please check your post enctype and post field name! ");
	}

	private int processUploadedFile(String catalogName, FileItem item,
			boolean writeToFile) throws Exception {

		// Process a file upload
		if (!item.isFormField()) {
			String fieldName = item.getFieldName();
			String fileName = item.getName();
			String contentType = item.getContentType();
			boolean isInMemory = item.isInMemory();
			long sizeInBytes = item.getSize();

			String uid = Uuid.get32Code().toString();
			sourceFileName = parseFileFullName(fileName)[0];
			sourceFileType = parseFileFullName(fileName)[1];

			if (!okFileTypes.contains(this.sourceFileType.toLowerCase())) {
				return -1;
			}

			if (writeToFile) {
				StringBuffer upath = new StringBuffer();
				// generate catalogName
				if (StringUtils.isEmpty(catalogName))
					catalogName = generateCatalogName();

				upath.append(uploadPath);
				if(!catalogName.equals("/"))upath.append(File.separator).append(catalogName);
				locateCatalog(upath);
				upath.append(File.separator).append(uid).append(".").append(
						sourceFileType);

				if(!catalogName.equals("/"))targetUri += ("/"+catalogName);
				targetUri += ("/" + uid + "." + sourceFileType);

				File uploadedFile = new File(upath.toString());
				item.write(uploadedFile);
				return 1;

			} else {
				InputStream uploadedStream = item.getInputStream();
				uploadedStream.close();
			}
		}
		return 0;

	}

	private Object getFileItem(List fileItems, String fieldName) {

		for (Iterator iterator = fileItems.iterator(); iterator.hasNext();) {
			FileItem item = (FileItem) iterator.next();
			String name = item.getFieldName();
			if (StringUtils.isNotEmpty(name) && name.equals(fieldName)) {

				if (item.isFormField()) {
					String value = "";

					try {
						value = new String(item.getString("UTF-8"));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}

					return value;
				} else {

					return item;

				}

			}

		}
		return null;
	}

	private String[] parseFileFullName(String fileName) {
		String[] result = new String[2];
		String simpleFileName = "";
		if (fileName.indexOf("/") == -1 && fileName.indexOf("\\") == -1) {
			simpleFileName = fileName;
		} else {
			// get file full name through regExp
			String regExp = ".+\\\\(.+)$";
			Pattern p = Pattern.compile(regExp);
			Matcher m = p.matcher(fileName);
			m.find();
			simpleFileName = m.group(1);
		}

		int point = simpleFileName.lastIndexOf(".");
		result[0] = simpleFileName.substring(0, point);
		result[1] = simpleFileName.substring(point + 1);

		return result;
	}

	private String generateCatalogName() {
		String catalogName;
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH) + 1;
		String showMonth = month < 10 ? ("0" + month) : String.valueOf(month);
		catalogName = "c" + year + showMonth;
		return catalogName;
	}

	private void locateCatalog(StringBuffer basePath) {
		File d = new File(basePath.toString());
		if (d.exists()) {

		} else {
			d.mkdir();
			logger
					.info("The catalog folder of this file has been created yet. ");
		}
	}
	
	private String buildUploadResult(boolean success,String message) {
		StringBuffer sb=new StringBuffer();
		
		if(outputFormat.equals("xml")){
			
			sb.append("<UploadResult>"); 
			sb.append("<success>"+success+"</success>"); 
			sb.append("<message>").append(message).append("</message>");
			sb.append("</UploadResult>");
			
		}else if(outputFormat.equals("json")){
			
			sb.append("{"); 
			sb.append("\"success\":"+success+","); 
			sb.append("\"message\":\"").append(message).append("\"");
			sb.append("}");
			
		}
		
		return sb.toString();

	}
	

}
