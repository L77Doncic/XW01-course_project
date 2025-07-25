package project.todo;
/*
 * 待完成：用MVC模式分开DB和Action操作
 * 增删改查看导印统功能的实现
 */
import project.dao.Data;
import project.dao.TodoDao;
import project.dao.MySQLDao;

import java.io.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONException;
import org.json.JSONObject;

public class ServletAction extends HttpServlet {
	String module="project";
	String sub="todo";
	Data data=null;
	public void showDebug(String msg){
		System.out.println("["+(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date())+"]["+module+"/"+sub+"/ServletAction]"+msg);
	}
	/*
	 * 处理顺序：先是service，后根据情况doGet或者doPost
	 */
	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		try {
			processAction(request,response);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	/*========================================函数分流 开始========================================*/
	public void processAction(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, JSONException {
		HttpSession session = request.getSession();
		request.setCharacterEncoding("UTF-8");
		//设置以下头信息允许跨域
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "*");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "*");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		data=getPageParameters(request,response);
		String action = data.getParam().getString("action");
		boolean actionOk = false;
		int resultCode=0;
		String resultMsg="ok";
		JSONObject json=new JSONObject(); 
		showDebug("[processAction]收到的action是："+action);
		if (action == null){
			resultMsg="传递过来的action是NULL";
		}else{
			json.put("action",action);
			json.put("result_code",0);
			json.put("result_msg","ok");
			//这几个常规增删改查功能
			if (action.equals("get_device_record")) {
				actionOk=true;
				try {
					getDeviceRecord(request, response, json);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (action.equals("get_device_record1")) {
				actionOk=true;
				try {
					getDeviceRecord1(request, response, json);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (action.equals("get_device_record2")) {
				actionOk=true;
				try {
					getDeviceRecord2(request, response, json);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (action.equals("get_device_record_more")) {
				actionOk=true;
				try {
					getDeviceRecord(request, response, json);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (action.equals("add_device_record")) {
				actionOk=true;
				try {
					addDeviceRecord(request, response, json);
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (action.equals("add_user_record")) {
				actionOk=true;
				try {
					adduser(request, response, json);
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (action.equals("add_car_record")) {
				actionOk=true;
				try {
					addcar(request, response, json);
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (action.equals("add_todo_record")) {
				actionOk=true;
				try {
					addtodo(request, response, json);
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (action.equals("modify_user_record")) {
				actionOk=true;
				try {
					modifyuser(request, response, json);
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (action.equals("modify_todo_record")) {
				actionOk=true;
				try {
					modifytodo(request, response, json);
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (action.equals("modify_car_record")) {
				actionOk=true;
				try {
					modifycar(request, response, json);
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (action.equals("modify_car_record2")) {
				actionOk=true;
				try {
					modifycar2(request, response, json);
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (action.equals("modify_device_record")) {
				actionOk=true;
				try {
					modifyDeviceRecord(request, response, json);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (action.equals("vehicle_number_record")) {
				actionOk=true;
				try {
					getvehiclenumber(request, response, json);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (action.equals("delete_device_record")) {
				actionOk=true;
				try {
					deleteDeviceRecord(request, response, json);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (action.equals("delete_user_record")) {
				actionOk=true;
				try {
					deleteUserRecord(request, response, json);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (action.equals("delete_car_record")) {
				actionOk=true;
				try {
					deletecarRecord(request, response, json);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (action.equals("delete_car_record2")) {
				actionOk=true;
				try {
					deletecarRecord2(request, response, json);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (action.equals("get_gps_status")) {
				actionOk=true;
				try {
					getGpsStatus(request, response, json);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (action.equals("export_device_record")) {
				actionOk=true;
				try {
					exportDeviceRecord(request, response, json);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (action.equals("vehicle_monitor")) {
				actionOk=true;
				try {
					vehicle_monitor(request, response, json);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (action.equals("vehicle_car")) {
				actionOk=true;
				try {
					vehicle_car(request, response, json);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (action.equals("vehicle_monitor1")) {
				actionOk=true;
				try {
					vehicle_monitor1(request, response, json);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (action.equals("vehicle_monitor2")) {
				actionOk=true;
				try {
					vehicle_monitor2(request, response, json);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (action.equals("vehicle_monitor3")) {
				actionOk=true;
				try {
					vehicle_monitor3(request, response, json);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (action.equals("vehicle_monitor4")) {
				actionOk=true;
				try {
					vehicle_monitor4(request, response, json);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (action.equals("vehicle_monitor5")) {
				actionOk=true;
				try {
					vehicle_monitor5(request, response, json);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			if (action.equals("query_record")) {
				actionOk=true;
				try {
					queryRecord(request, response, json);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (action.equals("update_record")) {
				actionOk=true;
				try {
					updateRecord(request, response, json);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (action.equals("get_gps_receive_count_by_hour")) {
				actionOk=true;
				try {
					getGpsReceiveCountByHour(request, response, json);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (action.equals("user_info")) {
				actionOk=true;
				try {
					getuserinfo(request, response, json);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (action.equals("add_user_record")) {
				actionOk=true;
				try {
					adduser(request, response, json);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(action.equals("upload_file")){
				actionOk=true;
				try {
					uploadFile(request, response, json);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			try {
				responseBack(request,response,data,json);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	/*========================================函数分流 结束========================================*/
	/*========================================公共函数 开始========================================*/
	private Data getPageParameters(HttpServletRequest request,HttpServletResponse response) throws JSONException{
		Data data=new Data();
		HttpSession session = request.getSession();
		/*----------------------------------------获取所有表单信息 开始----------------------------------------*/
		showDebug("[getPageParameters]----------------------------------------获取所有表单信息 开始----------------------------------------");
		JSONObject param=data.getParam();
		Enumeration requestNames=request.getParameterNames();
		for(Enumeration e=requestNames;e.hasMoreElements();){
			String thisName=e.nextElement().toString();
			String thisValue=request.getParameter(thisName);
			showDebug("[getPageParameters]"+thisName+"="+thisValue);
			param.put(thisName, thisValue);
		}
		showDebug("[getPageParameters]data的Param="+data.getParam().toString());
		showDebug("[getPageParameters]----------------------------------------获取所有表单信息 完毕----------------------------------------");
		/*----------------------------------------获取所有表单信息 完毕----------------------------------------*/
		return data;
	}
	private void responseBack(HttpServletRequest request,HttpServletResponse response,Data data,JSONObject json) throws JSONException {
		/*----------------------------------------获取所有服务器端信息 开始----------------------------------------*/
		boolean isAjax=true;if (request.getHeader("x-requested-with") == null || request.getHeader("x-requested-with").equals("com.tencent.mm")){isAjax=false;}	//判断是异步请求还是同步请求，腾讯的特殊
		//如果有参数就另外处理
		if(data.getParam().has("ajax") && data.getParam().getString("ajax").equals("true")){isAjax=true;};
		json.put("ajax",isAjax);
		/*----------------------------------------获取所有服务器端信息 结束----------------------------------------*/
		if(json.has("ajax") && json.getBoolean("ajax")){
			showDebug("[responseBack]ajax方式返回数据，页面局部刷新");
			response.setContentType("application/json; charset=UTF-8");
			try {
				response.getWriter().print(json);
				response.getWriter().flush();
				response.getWriter().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			showDebug("[responseBack]跳转方式返回数据，页面跳转到指定页面");
			String action=json.getString("action");
			String errorNo="0";
			String errorMsg="ok";
			String url = module+"/"+sub+"/result.jsp?action="+action+"&result_code="+errorNo+ "&result_msg=" + errorMsg;
			if(json.has("redirect_url")) url=json.getString("redirect_url");
			try {
				response.sendRedirect("device/file/device_file.jsp");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/*========================================公共函数 结束========================================*/
	/*========================================MySQL HTTP操作通用函数 开始========================================*/
	private void updateRecord(HttpServletRequest request, HttpServletResponse response,JSONObject json) throws JSONException, SQLException {
		MySQLDao dao=new MySQLDao();
		dao.updateRecord(data,json);
	}
	private void queryRecord(HttpServletRequest request, HttpServletResponse response,JSONObject json) throws JSONException, SQLException {
		MySQLDao dao=new MySQLDao();
		dao.queryRecord(data,json);
	}
	/*========================================MySQL HTTP操作通用函数 结束========================================*/
	/*========================================CRUD业务函数 开始========================================*/
	private void getDeviceRecord(HttpServletRequest request, HttpServletResponse response,JSONObject json) throws JSONException, SQLException {
		TodoDao dao=new TodoDao();
		dao.getDeviceRecord(data,json);
	}
	private void getDeviceRecord1(HttpServletRequest request, HttpServletResponse response,JSONObject json) throws JSONException, SQLException {
		TodoDao dao=new TodoDao();
		dao.getDeviceRecord1(data,json);
	}
	private void getDeviceRecord2(HttpServletRequest request, HttpServletResponse response,JSONObject json) throws JSONException, SQLException {
		TodoDao dao=new TodoDao();
		dao.getDeviceRecord2(data,json);
	}
	private void modifyDeviceRecord(HttpServletRequest request, HttpServletResponse response,JSONObject json) throws JSONException, SQLException {
		TodoDao dao=new TodoDao();
		dao.modifyDeviceRecord(data,json);
	}
	private void deleteDeviceRecord(HttpServletRequest request, HttpServletResponse response,JSONObject json) throws JSONException, SQLException {
		TodoDao dao=new TodoDao();
		dao.deleteDeviceRecord(data,json);
	}
	private void deleteUserRecord(HttpServletRequest request, HttpServletResponse response,JSONObject json) throws JSONException, SQLException {
		TodoDao dao=new TodoDao();
		dao.deleteUserRecord(data,json);
	}

	private void deletecarRecord(HttpServletRequest request, HttpServletResponse response,JSONObject json) throws JSONException, SQLException {
		TodoDao dao=new TodoDao();
		dao.deletecarRecord(data,json);
	}
	private void deletecarRecord2(HttpServletRequest request, HttpServletResponse response,JSONObject json) throws JSONException, SQLException {
		TodoDao dao=new TodoDao();
		dao.deletecarRecord2(data,json);
	}
	private void addDeviceRecord(HttpServletRequest request, HttpServletResponse response,JSONObject json) throws JSONException, SQLException {
		TodoDao dao=new TodoDao();
		dao.addDeviceRecord(data,json);
	}
	private void adduser(HttpServletRequest request, HttpServletResponse response,JSONObject json) throws JSONException, SQLException {
		TodoDao dao=new TodoDao();
		Data data=getPageParameters(request,response);
		dao.adduser(data,json);
	}
	private void addcar(HttpServletRequest request, HttpServletResponse response,JSONObject json) throws JSONException, SQLException {
		TodoDao dao=new TodoDao();
		Data data=getPageParameters(request,response);
		dao.addcar(data,json);
	}
	private void addtodo(HttpServletRequest request, HttpServletResponse response,JSONObject json) throws JSONException, SQLException {
		TodoDao dao=new TodoDao();
		Data data=getPageParameters(request,response);
		dao.addtodo(data,json);
	}
	private void modifyuser(HttpServletRequest request, HttpServletResponse response,JSONObject json) throws JSONException, SQLException {
		TodoDao dao=new TodoDao();
		Data data=getPageParameters(request,response);
		dao.modifyuser(data,json);
	}
	private void modifytodo(HttpServletRequest request, HttpServletResponse response,JSONObject json) throws JSONException, SQLException {
		TodoDao dao=new TodoDao();
		Data data=getPageParameters(request,response);
		dao.modifytodo(data,json);
	}
	private void modifycar(HttpServletRequest request, HttpServletResponse response,JSONObject json) throws JSONException, SQLException {
		TodoDao dao=new TodoDao();
		Data data=getPageParameters(request,response);
		dao.modifycar(data,json);
	}
	private void modifycar2(HttpServletRequest request, HttpServletResponse response,JSONObject json) throws JSONException, SQLException {
		TodoDao dao=new TodoDao();
		Data data=getPageParameters(request,response);
		dao.modifycar2(data,json);
	}
	private void getDeviceRecordmore(HttpServletRequest request, HttpServletResponse response,JSONObject json) throws JSONException, SQLException {
		TodoDao dao=new TodoDao();
		dao.getDeviceRecordmore(data,json);
	}
	private void getGpsStatus(HttpServletRequest request, HttpServletResponse response,JSONObject json) throws JSONException, SQLException {
		TodoDao dao=new TodoDao();
		Data data=getPageParameters(request,response);
		dao.getGpsStatus(data,json);
	}
	private void exportDeviceRecord(HttpServletRequest request, HttpServletResponse response,JSONObject json) throws JSONException, SQLException, IOException {
		TodoDao dao=new TodoDao();
		Data data=getPageParameters(request,response);
		dao.getDeviceRecord(data,json);
		getExportDeviceRecordToFile(json, data);
		getExportDeviceRecordToTxt(json, data);
		getExportDeviceRecordToPdf(json, data);
	}
	private void getGpsReceiveCountByHour(HttpServletRequest request, HttpServletResponse response,JSONObject json) throws JSONException, SQLException {
		TodoDao dao=new TodoDao();
		Data data=getPageParameters(request,response);
		dao.getGpsReceiveCountByHour(data,json);
	}

	private void getExportDeviceRecordToPdf(JSONObject json, Data data) {

	}
	private void getuserinfo(HttpServletRequest request, HttpServletResponse response,JSONObject json) throws JSONException, SQLException {
		TodoDao dao=new TodoDao();
		Data data=getPageParameters(request,response);
		dao.getuserinfo(data,json);
	}
	private void vehicle_monitor(HttpServletRequest request, HttpServletResponse response,JSONObject json) throws JSONException, SQLException {
		TodoDao dao=new TodoDao();
		Data data=getPageParameters(request,response);
		dao.getvehicleinfo(data,json);
	}
	private void vehicle_car(HttpServletRequest request, HttpServletResponse response,JSONObject json) throws JSONException, SQLException {
		TodoDao dao=new TodoDao();
		Data data=getPageParameters(request,response);
		dao.getvehiclecarinfo(data,json);
	}
	private void vehicle_monitor1(HttpServletRequest request, HttpServletResponse response,JSONObject json) throws JSONException, SQLException {
		TodoDao dao=new TodoDao();
		Data data=getPageParameters(request,response);
		dao.getvehicleinfo1(data,json);
	}
	private void vehicle_monitor2(HttpServletRequest request, HttpServletResponse response,JSONObject json) throws JSONException, SQLException {
		TodoDao dao=new TodoDao();
		Data data=getPageParameters(request,response);
		dao.getvehicleinfo2(data,json);
	}
	private void vehicle_monitor3(HttpServletRequest request, HttpServletResponse response,JSONObject json) throws JSONException, SQLException {
		TodoDao dao=new TodoDao();
		Data data=getPageParameters(request,response);
		dao.getvehicleinfo3(data,json);
	}
	private void vehicle_monitor4(HttpServletRequest request, HttpServletResponse response,JSONObject json) throws JSONException, SQLException {
		TodoDao dao=new TodoDao();
		Data data=getPageParameters(request,response);
		dao.getvehicleinfo4(data,json);
	}
	private void vehicle_monitor5(HttpServletRequest request, HttpServletResponse response,JSONObject json) throws JSONException, SQLException {
		TodoDao dao=new TodoDao();
		Data data=getPageParameters(request,response);
		dao.getvehicleinfo5(data,json);
	}
	private void getvehiclenumber(HttpServletRequest request, HttpServletResponse response,JSONObject json) throws JSONException, SQLException {
		TodoDao dao=new TodoDao();
		Data data=getPageParameters(request,response);
		dao.getVehicleNumber(data,json);
	}



	private void getExportDeviceRecordToTxt(JSONObject json, Data data) {

	}


	private void getExportDeviceRecordToFile(JSONObject json, Data data) throws JSONException {
		String jsonStr = json.toString();
		File jsonFile=new File("C:\\upload\\maintain\\device\\export_device.txt");
		json.put("download_url","/upload/maintain/device/export_device.txt");
		try{
			if(!jsonFile.exists()){
				jsonFile.createNewFile();
			}
			FileWriter fileWriter = new FileWriter(jsonFile.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fileWriter);
			bw.write(jsonStr);
			bw.close();
		}catch (IOException e){
			e.printStackTrace();
		}

	}

	/*========================================CRUD业务函数 结束========================================*/
	/*========================================上传文件函数 开始========================================*/
	private void uploadFile(HttpServletRequest request, HttpServletResponse response,JSONObject json) throws JSONException, SQLException {
		uploadFileAttachment(request, response, json);				//上传文件到指定目录，返回文件存放目录和构造出来的url，放在json里传递出来
		saveFileAttachmentRecord(data,json);						//把文件上传后，存在json传递的文件存放信息，保存进数据库表里
	}
	private void uploadFileAttachment(HttpServletRequest request, HttpServletResponse response,JSONObject json) throws JSONException, SQLException {
		//先做初始化工作，定义一堆目录变量
		String fileUrl=null;
		String rootPath="E:\\temp";
		String filePath=rootPath+"\\teach\\"+(new SimpleDateFormat("yyyyMMddHH")).format(new Date());
		String rootUrl="/upload";
		String filePathUrl=rootUrl+"/teach/"+(new SimpleDateFormat("yyyyMMddHH")).format(new Date());
		String tmpPath = rootPath + "\\temp\\"; // 临时路径
		showDebug("[uploadFileAttachment]服务器保存根目录："+rootPath);
		showDebug("[uploadFileAttachment]服务器保存目录："+filePath+"，临时目录："+tmpPath);
		showDebug("[uploadFileAttachment]服务器保存后的URL根路径："+rootUrl);

		//接着检查目录是否存在，不存在就创建目录
		File fileRepository = new File(filePath);
		if (!fileRepository.exists() && !fileRepository.isDirectory())
			fileRepository.mkdirs();
		File tmpRepository = new File(tmpPath);
		if (!tmpRepository.exists() && !tmpRepository.isDirectory())
			tmpRepository.mkdirs();

		//定义两个传递结果出来的数组，字段数组和文件数组
		List fileList = new ArrayList();							//保存收到的文件信息
		List fieldList = new ArrayList();							//保存传递过来的字段信息

		//开始接收上传文件
		try {
			if (ServletFileUpload.isMultipartContent(request)) {
				DiskFileItemFactory dff = new DiskFileItemFactory();//创建该对象
				dff.setRepository(tmpRepository);					//指定上传文件的临时目录
				dff.setSizeThreshold(1024000);						//指定在内存中缓存数据大小,单位为byte
				ServletFileUpload sfu = new ServletFileUpload(dff);	//创建该对象
				sfu.setHeaderEncoding("UTF-8");						//设定编码用统一的
				sfu.setSizeMax(1000000000);							//指定单个上传文件的最大尺寸
				/*--------------------收文件 开始--------------------*/
				List<FileItem> list = sfu.parseRequest(request);

				for (Iterator<FileItem> iter = list.iterator(); iter.hasNext();) {
					FileItem item = iter.next();
					//item可能是文件，也可能是字段类型，分别区分对待
					if (item.isFormField()) {
						//如果是字段，例如：device_id、device_name等
						String fieldName=item.getFieldName();
						String fieldValue=item.getString("UTF-8");
						HashMap map = new HashMap();
						map.put(fieldName, fieldValue);
						fieldList.add(map);
						showDebug("[uploadFileAttachment]收到字段："+fieldName+"="+fieldValue);
					}else{
						//如果是form-data
						String objectId=null;
						String filePathName=null;
						String fileName=item.getName();
						int fileSize=0;
						showDebug("[uploadFileAttachment]收到文件："+fileName);
						if(!fileName.isEmpty()){
							//如果带有路径，就去掉路径，找文件名
							fileName=fileName.substring(fileName.lastIndexOf("\\")+1);
							//fileName=(new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date());						//或者自行编写一个流水号的文件名
							objectId="UPLOAD_"+(new SimpleDateFormat("yyyyMMddHHmmssSSS")).format(new Date());	//生成一个流水号附件id给前端
							showDebug("[uploadFileAttachment]文件路径："+filePath + "\\" + fileName);
							filePathName=filePath + "\\" + fileName;
							/*--------------------接收文件数据 开始--------------------*/
							FileOutputStream out=new FileOutputStream(filePath + "\\" + fileName);
							InputStream in = item.getInputStream();
							byte buffer[] = new byte[1024];
							int len = 0;
							while((len=in.read(buffer))>0){
								out.write(buffer,0,len);
								fileSize=fileSize+len;
							}
							in.close();
							out.close();
							/*--------------------接收文件数据 结束--------------------*/
							fileUrl=filePathUrl+"/"+fileName;															//fileUrl是保存文件后，构造出供前端访问的fileUrl
						}
						// 构造返回结果的json
						HashMap map = new HashMap();
						map.put("file_size", fileSize);
						map.put("file_path_name", filePathName);
						map.put("file_url_name", fileUrl);
						map.put("file_object_id", objectId);
						fileList.add(map);
						showDebug("[uploadFileAttachment]存到：fileName="+fileName+",filePath="+filePath+",fileSize="+fileSize+",fileUrl="+fileUrl);
					}
				}
				/*--------------------收文件 结束--------------------*/
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		json.put("upload_files", fileList);
		json.put("upload_fields", fieldList);
	}
	/*
	功能：把上传文件uploadFileAttachment后，得到的文件信息，保存进数据库表里
	 */
	private void saveFileAttachmentRecord(Data data,JSONObject json) throws JSONException, SQLException {
		//这部分需要自己写
		showDebug("[saveFileRecord]收完文件后，传递出来的json是："+json.toString());
		TodoDao dao=new TodoDao();
		dao.saveUploadFileRecord(json,data);
	}
	/*========================================上传文件函数 结束========================================*/
}
