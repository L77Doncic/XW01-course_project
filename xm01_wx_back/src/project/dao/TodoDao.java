package project.dao;

import java.rmi.Remote;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import project.dao.Db;
import project.dao.DbRemote;

import javax.xml.transform.Result;

public class TodoDao {
	public void showDebug(String msg){
		System.out.println("["+(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date())+"][device/dao/Db]"+msg);
	}
	private boolean checkParamValid(JSONObject param,String field) throws JSONException{
		boolean ok=false;
		ok=param.has(field) && param.getString(field)!=null && !param.getString(field).isEmpty() && !param.getString(field).equals("undefined") && !param.getString(field).equals("null");
		return ok;
	}
	/*添加记录*/
	public void addDeviceRecord(Data data, JSONObject json) throws JSONException, SQLException {
		//构造sql语句，根据传递过来的条件参数
		String deviceId=data.getParam().has("id")?data.getParam().getString("id"):null;
		String deviceName=data.getParam().has("title")?data.getParam().getString("title"):null;
		String deviceType=data.getParam().has("type")?data.getParam().getString("type"):null;
		String createTime=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
		if(deviceId!=null && deviceName!=null){
			String sql="insert into device_file(device_id,device_name,device_type,used_tag,creator_id,creator,create_time)";
			sql=sql+" values('"+deviceId+"'";
			sql=sql+" ,'"+deviceName+"','"+deviceType+"',1,'20220000000000001','student','"+createTime+"')";
			data.getParam().put("sql",sql);
			updateRecord(data,json);

			String str=data.getParam().getString("attachment_ids");
			showDebug("[addRecord]整理之前的str："+str);
			str=str.substring(1,str.length()-1);
			showDebug("[addRecord]整理之后的str："+str);
			List<String> list = Arrays.asList(str.split(","));
			for(int i=0;i<list.size();i++){
				String attachmentId = list.get(i);
				sql="update attachment set parent_id='"+deviceId+"' where object_id="+attachmentId+"";
				data.getParam().put("sql",sql);
				updateRecord(data,json);
			}
		}
	}
	public void adduser(Data data, JSONObject json) throws JSONException, SQLException {
		//构造sql语句，根据传递过来的条件参数
		String phone_number=data.getParam().has("phonenumber")?data.getParam().getString("phonenumber"):null;
		String nick_name=data.getParam().has("nick_name")?data.getParam().getString("nick_name"):null;
		String user_id=data.getParam().has("id")?data.getParam().getString("id"):null;
		String user_name=data.getParam().has("user_name")?data.getParam().getString("user_name"):null;
		String email=data.getParam().has("email")?data.getParam().getString("email"):null;
		String password=data.getParam().has("password")?data.getParam().getString("password"):null;
		String createTime=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
		if(user_id!=null && user_name!=null){
			String sql="insert into xm01_sys_user(user_id,user_name,password,email,nick_name,phonenumber,create_time)";
			sql=sql+" values('"+user_id+"'";
			sql=sql+" ,'"+user_name+"','"+password+"','"+email+"','"+nick_name+"','"+phone_number+"','"+createTime+"')";
			data.getParam().put("sql",sql);
			showDebug("构造的添加语句是："+sql);
			updateRecord(data,json);
//			String str=data.getParam().getString("att
//			achment_ids");
//			showDebug("[addRecord]整理之前的str："+str);
//			str=str.substring(1,str.length()-1);
//			showDebug("[addRecord]整理之后的str："+str);
//			List<String> list = Arrays.asList(str.split(","));
//			for(int i=0;i<list.size();i++){
//				String attachmentId = list.get(i);
//				sql="update attachment set parent_id='"+deviceId+"' where object_id="+attachmentId+"";
//				data.getParam().put("sql",sql);
//				updateRecord(data,json);
//			}
		}
	}
	public void addcar(Data data, JSONObject json) throws JSONException, SQLException {
		//构造sql语句，根据传递过来的条件参数
		String car_number=data.getParam().has("car_number")?data.getParam().getString("car_number"):null;
		String type=data.getParam().has("type")?data.getParam().getString("type"):null;
		String user_id=data.getParam().has("id")?data.getParam().getString("id"):null;
		String traffic_type=data.getParam().has("traffic_type")?data.getParam().getString("traffic_type"):null;
		String time=data.getParam().has("time")?data.getParam().getString("time"):null;
		String speed=data.getParam().has("speed")?data.getParam().getString("speed"):null;
		if(user_id!=null && car_number!=null){
			String sql="insert into xm01_cardata(id,car_number,car_type,traffic_type,time,car_speed)";
			sql=sql+" values('"+user_id+"'";
			sql=sql+" ,'"+car_number+"','"+type+"','"+traffic_type+"','"+time+"','"+speed+"')";
			data.getParam().put("sql",sql);
			showDebug("构造的添加语句是："+sql);
			updateRecord(data,json);
//			String str=data.getParam().getString("attachment_ids");
//			showDebug("[addRecord]整理之前的str："+str);
//			str=str.substring(1,str.length()-1);
//			showDebug("[addRecord]整理之后的str："+str);
//			List<String> list = Arrays.asList(str.split(","));
//			for(int i=0;i<list.size();i++){
//				String attachmentId = list.get(i);
//				sql="update attachment set parent_id='"+deviceId+"' where object_id="+attachmentId+"";
//				data.getParam().put("sql",sql);
//				updateRecord(data,json);
//			}
		}
	}
	public void addtodo(Data data, JSONObject json) throws JSONException, SQLException {
		//构造sql语句，根据传递过来的条件参数
		String title=data.getParam().has("title")?data.getParam().getString("title"):null;
		String content=data.getParam().has("content")?data.getParam().getString("content"):null;
		String user_id=data.getParam().has("id")?data.getParam().getString("id"):null;
		String begintime=data.getParam().has("begintime")?data.getParam().getString("begintime"):null;
		String limittime=data.getParam().has("limittime")?data.getParam().getString("limittime"):null;
		String creator=data.getParam().has("creator")?data.getParam().getString("creator"):null;
		String createTime=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
		if(user_id!=null && title!=null){
			String sql="insert into xm01_project_todo(id,title,content,limit_time,begin_time,creator)";
			sql=sql+" values('"+user_id+"'";
			sql=sql+" ,'"+title+"','"+content+"','"+limittime+"','"+begintime+"','"+creator+"')";
			data.getParam().put("sql",sql);
			showDebug("构造的添加语句是："+sql);
			updateRecord(data,json);
//			String str=data.getParam().getString("attachment_ids");
//			showDebug("[addRecord]整理之前的str："+str);
//			str=str.substring(1,str.length()-1);
//			showDebug("[addRecord]整理之后的str："+str);
//			List<String> list = Arrays.asList(str.split(","));
//			for(int i=0;i<list.size();i++){
//				String attachmentId = list.get(i);
//				sql="update attachment set parent_id='"+deviceId+"' where object_id="+attachmentId+"";
//				data.getParam().put("sql",sql);
//				updateRecord(data,json);
//			}
		}
	}

	/*删除记录*/
	public void deleteDeviceRecord(Data data,JSONObject json) throws JSONException, SQLException{
		//构造sql语句，根据传递过来的条件参数
		String id=data.getParam().has("id")?data.getParam().getString("id"):null;
		if(id!=null){
			String sql="delete from xm01_project_todo where id="+data.getParam().getString("id");
			data.getParam().put("sql",sql);
			updateRecord(data,json);
		}
	}
	public void deleteUserRecord(Data data,JSONObject json) throws JSONException, SQLException{
		//构造sql语句，根据传递过来的条件参数
		String id=data.getParam().has("id")?data.getParam().getString("id"):null;
		if(id!=null){
			String sql="delete from xm01_sys_user where user_id="+data.getParam().getString("id");
			data.getParam().put("sql",sql);
			updateRecord(data,json);
		}
	}
	public void deletecarRecord2(Data data,JSONObject json) throws JSONException, SQLException{
		//构造sql语句，根据传递过来的条件参数
		String id=data.getParam().has("id")?data.getParam().getString("id"):null;
		if(id!=null){
			String sql="delete from xm01_cardata where id="+data.getParam().getString("id");
			data.getParam().put("sql",sql);
			updateRecord(data,json);
		}
	}
	public void deletecarRecord(Data data,JSONObject json) throws JSONException, SQLException{
		//构造sql语句，根据传递过来的条件参数
		String id=data.getParam().has("id")?data.getParam().getString("id"):null;
		if(id!=null){
			String sql="delete from xm01_parking where id="+data.getParam().getString("id");
			data.getParam().put("sql",sql);
			updateRecord(data,json);
		}
	}
	/*修改记录*/
	public void modifyDeviceRecord(Data data,JSONObject json) throws JSONException, SQLException{
		//构造sql语句，根据传递过来的条件参数
		String id=data.getParam().has("id")?data.getParam().getString("id"):null;
		String deviceName=data.getParam().has("title")?data.getParam().getString("title"):null;
		if(id!=null){
			String sql="update device_file";
			sql=sql+" set device_name='"+deviceName+"'";
			sql=sql+" where device_id="+id;
			data.getParam().put("sql",sql);
			updateRecord(data,json);
		}
	}
	public void modifyuser(Data data,JSONObject json) throws JSONException, SQLException{
		//构造sql语句，根据传递过来的条件参数
		showDebug("执行到这里了");
		String user_id=data.getParam().has("user_id")?data.getParam().getString("user_id"):null;
		String user_name=data.getParam().has("user_name")?data.getParam().getString("user_name"):null;
		String nick_name=data.getParam().has("nick_name")?data.getParam().getString("nick_name"):null;
		String email=data.getParam().has("email")?data.getParam().getString("email"):null;
		String phonenumber=data.getParam().has("phonenumber")?data.getParam().getString("phonenumber"):null;
		showDebug(user_id);
		if(user_id!=null){

			String sql="update xm01_sys_user";
			sql=sql+" set user_name='"+user_name+"', email='"+email+"', phonenumber='"+phonenumber+"', nick_name='"+nick_name+"'";
			sql=sql+" where user_id="+user_id;
			data.getParam().put("sql",sql);
			updateRecord(data,json);
		}
	}
	public void modifytodo(Data data,JSONObject json) throws JSONException, SQLException{
		//构造sql语句，根据传递过来的条件参数
		showDebug("执行到这里了");
		String user_id=data.getParam().has("id")?data.getParam().getString("id"):null;
		String title=data.getParam().has("title")?data.getParam().getString("title"):null;
		String content=data.getParam().has("content")?data.getParam().getString("content"):null;
		String limit_time=data.getParam().has("limittime")?data.getParam().getString("limittime"):null;
		String begin_time=data.getParam().has("begintime")?data.getParam().getString("begintime"):null;
		showDebug(user_id);
		if(user_id!=null){

			String sql="update xm01_project_todo";
			sql=sql+" set title='"+title+"', content='"+content+"', limit_time='"+limit_time+"', begin_time='"+begin_time+"'";
			sql=sql+" where id="+user_id;
			data.getParam().put("sql",sql);
			updateRecord(data,json);
		}
	}
	public void modifycar(Data data,JSONObject json) throws JSONException, SQLException{
		//构造sql语句，根据传递过来的条件参数
		showDebug("执行到这里了");
		String user_id=data.getParam().has("id")?data.getParam().getString("id"):null;
		String car_number=data.getParam().has("car_number")?data.getParam().getString("car_number"):null;
		String location=data.getParam().has("location")?data.getParam().getString("location"):null;
		String time=data.getParam().has("time")?data.getParam().getString("time"):null;
		showDebug(user_id);
		if(user_id!=null){

			String sql="update xm01_parking";
			sql=sql+" set car_number='"+car_number+"', location='"+location+"', time='"+time+"'";
			sql=sql+" where id="+user_id;
			data.getParam().put("sql",sql);
			updateRecord(data,json);
		}
	}
	public void modifycar2(Data data,JSONObject json) throws JSONException, SQLException{
		//构造sql语句，根据传递过来的条件参数
		showDebug("执行到这里了");
		String user_id=data.getParam().has("id")?data.getParam().getString("id"):null;
		String car_number=data.getParam().has("car_number")?data.getParam().getString("car_number"):null;
		String location=data.getParam().has("location")?data.getParam().getString("location"):null;
		String time=data.getParam().has("time")?data.getParam().getString("time"):null;
		showDebug(user_id);
		if(user_id!=null){

			String sql="update xm01_cardata";
			sql=sql+" set car_number='"+car_number+"', location='"+location+"', time='"+time+"'";
			sql=sql+" where id="+user_id;
			data.getParam().put("sql",sql);
			updateRecord(data,json);
		}
	}
	/*查询记录*/
	public void getDeviceRecord(Data data,JSONObject json) throws JSONException, SQLException{
		//构造sql语句，根据传递过来的查询条件参数
		String sql=createGetRecordSql(data);			//构造sql语句，根据传递过来的查询条件参数
		data.getParam().put("sql",sql);
		queryRecord(data,json);
	}
	public void getDeviceRecord1(Data data,JSONObject json) throws JSONException, SQLException{
		//构造sql语句，根据传递过来的查询条件参数
		String sql=createGetRecordSql1(data);			//构造sql语句，根据传递过来的查询条件参数
		data.getParam().put("sql",sql);
		queryRecord(data,json);
	}
	public void getDeviceRecord2(Data data,JSONObject json) throws JSONException, SQLException{
		//构造sql语句，根据传递过来的查询条件参数
		String sql=createGetRecordSql2(data);			//构造sql语句，根据传递过来的查询条件参数
		data.getParam().put("sql",sql);
		queryRecord(data,json);
	}
	public void getDeviceRecordmore(Data data,JSONObject json) throws JSONException, SQLException{
		//构造sql语句，根据传递过来的查询条件参数
		String sql=createGetRecordSql(data);			//构造sql语句，根据传递过来的查询条件参数
		data.getParam().put("sql",sql);
		queryRecord(data,json);
	}

	/*
	 * 这是一个样板的函数，可以拷贝做修改用
	 */

	private void updateRecord(Data data,JSONObject json) throws JSONException, SQLException{
		/*--------------------获取变量 开始--------------------*/
		JSONObject param=data.getParam();
		int resultCode=0;
		String resultMsg="ok";
		/*--------------------获取变量 完毕--------------------*/
		/*--------------------数据操作 开始--------------------*/
		DbRemote updateDb = new DbRemote("wtqjsz2025");
		String sql=data.getParam().getString("sql");
		showDebug("[updateRecord]"+sql);
		updateDb.executeUpdate(sql);
		updateDb.close();
		/*--------------------数据操作 结束--------------------*/
		/*--------------------返回数据 开始--------------------*/
		json.put("result_msg",resultMsg);															//如果发生错误就设置成"error"等
		json.put("result_code",resultCode);														//返回0表示正常，不等于0就表示有错误产生，错误代码
		/*--------------------返回数据 结束--------------------*/
	}
	private void queryRecord(Data data,JSONObject json) throws JSONException, SQLException{
		/*--------------------获取变量 开始--------------------*/
		String resultMsg = "ok";
		int resultCode = 0;
		List jsonList = new ArrayList();
		List jsonName = new ArrayList();
		/*--------------------获取变量 完毕--------------------*/
		/*--------------------数据操作 开始--------------------*/
		DbRemote queryDb = new DbRemote("wtqjsz2025");
		String sql=data.getParam().getString("sql");
		showDebug("[queryRecord]构造的SQL语句是：" + sql);
		try {
			ResultSet rs = queryDb.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			int fieldCount = rsmd.getColumnCount();
			while (rs.next()) {
				Map map = new HashMap();
				for (int i = 0; i < fieldCount; i++) {
					map.put(rsmd.getColumnName(i + 1), rs.getString(rsmd.getColumnName(i + 1)));
				}
				jsonList.add(map);
			}
			rs.close();
			//加表头
			for(int i=0;i<rsmd.getColumnCount();i++){
				String columnLabel=rsmd.getColumnLabel(i+1);
				jsonName.add(columnLabel);
			}
		} catch (Exception e) {
			e.printStackTrace();
			showDebug("[queryRecord]查询数据库出现错误：" + sql);
			resultCode = 10;
			resultMsg = "查询数据库出现错误！" + e.getMessage();
		}
		queryDb.close();
		/*--------------------数据操作 结束--------------------*/
		/*--------------------返回数据 开始--------------------*/
		json.put("aaData",jsonList);
		json.put("aaFieldName",jsonName);
		json.put("result_msg",resultMsg);															//如果发生错误就设置成"error"等
		json.put("result_code",resultCode);														//返回0表示正常，不等于0就表示有错误产生，错误代码
		/*--------------------返回数据 结束--------------------*/
	}
	public void getGpsReceiveCountByHour(Data data, JSONObject json) throws JSONException,SQLException{
		String resultMsg = "ok";
		int resultCode=0;
		List jsonList = new ArrayList();
		Calendar cal=Calendar.getInstance();
		cal.add(Calendar.DATE,-1);
		//String timeFrom=(new SimpleDateFormat("yyyy-MM-dd 00:00:00")).format(cal.getTime());
		String timeFrom="2023-11-06 00:00:00";
		//String timeTo=(new SimpleDateFormat("yyyy-MM-dd 23:59:59")).format(cal.getTime());
		String timeTo="2023-11-07 23:50:00";

		Db queryDb=new Db("test");
		String sql="select DATE_FORMAT(gps_time,\"%Y-%m-%d %H\") as time_interval,count(*) as total from gps_history  ";
		sql=sql+" where gps_time BETWEEN' "+timeFrom+"' and '"+timeTo+"'";
		sql=sql+" group by DATE_FORMAT(gps_time,\"%Y-%m-%d %H\")";
		showDebug("[getGpsStatistic]构造的SQL语句是："+sql);
		try{
			ResultSet rs=queryDb.executeQuery(sql);
			ResultSetMetaData rsmd=rs.getMetaData();
			int fieldCount = rsmd.getColumnCount();
			while(rs.next()){
				HashMap map=new HashMap();
				map.put("time_interval",rs.getString("time_interval"));
				map.put("total",rs.getInt("total"));
				jsonList.add(map);
			}
			rs.close();
		}
		catch (Exception e){
			e.printStackTrace();
			showDebug("[getGpsStatistic]查询数据库出错:"+sql);
			resultCode=10;
			resultMsg="查询数据库出错"+e.getMessage();
		}
		queryDb.close();
		json.put("aaData",jsonList);
		json.put("result_msg",resultMsg);
		json.put("result_code",resultCode);
	}

	private String createGetRecordSql(Data data) throws JSONException {
		JSONObject param=data.getParam();
		String sql="select * from xm01_project_todo";
		String where="";
		if(checkParamValid(param,"id")){
			where="id="+param.getString("id");
		}
		if(checkParamValid(param,"id")){
			where="id="+param.getString("id");
		}
		if(checkParamValid(param,"time_from") && checkParamValid(param,"time_to")){
			if(!where.isEmpty()){
				where=where+" and create_time between '"+param.getString("time_from")+"' and '"+param.getString("time_to")+"'";
			}else{
				where="create_time between '"+param.getString("time_from")+"' and '"+param.getString("time_to")+"'";
			}
		}
		if(checkParamValid(param,"device_id")){
			if(!where.isEmpty()){
				where=where+" and device_id = '"+param.getString("device_id")+"'";
			}else{
				where="device_id ='"+param.getString("device_id")+"'";
			}
		}
		if(checkParamValid(param,"device_type")){
			if(!where.isEmpty()){
				where=where+" and device_type = '"+param.getString("device_type")+"'";
			}else{
				where="device_type ='"+param.getString("device_type")+"'";
			}
		}
		if(checkParamValid(param,"device_name")){
			if(!where.isEmpty()){
				where=where+" and device_name like '%"+param.getString("device_name")+"%'";
			}else{
				where="device_name like '%"+param.getString("device_name")+"%'";
			}
		}
		if(!where.isEmpty()){
			sql=sql+" where "+where;
		}
		if(checkParamValid(param,"order_by")){
			sql=sql+" order by "+param.getString("order_by");
		}
		return sql;
	}
	private String createGetRecordSql1(Data data) throws JSONException {
		JSONObject param=data.getParam();
		String sql="select * from xm01_project_todo";
		String where="";
		if(checkParamValid(param,"id")){
			where="id="+param.getString("id");
		}
		if(checkParamValid(param,"id")){
			where="id="+param.getString("id");
		}
		if(checkParamValid(param,"time_from") && checkParamValid(param,"time_to")){
			if(!where.isEmpty()){
				where=where+" and create_time between '"+param.getString("time_from")+"' and '"+param.getString("time_to")+"'";
			}else{
				where="create_time between '"+param.getString("time_from")+"' and '"+param.getString("time_to")+"'";
			}
		}
		if(checkParamValid(param,"device_id")){
			if(!where.isEmpty()){
				where=where+" and device_id = '"+param.getString("device_id")+"'";
			}else{
				where="device_id ='"+param.getString("device_id")+"'";
			}
		}
		if(checkParamValid(param,"device_type")){
			if(!where.isEmpty()){
				where=where+" and device_type = '"+param.getString("device_type")+"'";
			}else{
				where="device_type ='"+param.getString("device_type")+"'";
			}
		}
		if(checkParamValid(param,"device_name")){
			if(!where.isEmpty()){
				where=where+" and device_name like '%"+param.getString("device_name")+"%'";
			}else{
				where="device_name like '%"+param.getString("device_name")+"%'";
			}
		}
		if(!where.isEmpty()){
			sql=sql+" where "+where;
		}
		if(checkParamValid(param,"order_by")){
			sql=sql+" order by "+param.getString("order_by");
		}
		sql=sql+" order by  id";
		return sql;
	}
	private String createGetRecordSql2(Data data) throws JSONException {
		JSONObject param=data.getParam();
		String sql="select * from xm01_project_todo";
		String where="";
		if(checkParamValid(param,"id")){
			where="id="+param.getString("id");
		}
		if(checkParamValid(param,"id")){
			where="id="+param.getString("id");
		}
		if(checkParamValid(param,"time_from") && checkParamValid(param,"time_to")){
			if(!where.isEmpty()){
				where=where+" and create_time between '"+param.getString("time_from")+"' and '"+param.getString("time_to")+"'";
			}else{
				where="create_time between '"+param.getString("time_from")+"' and '"+param.getString("time_to")+"'";
			}
		}
		if(checkParamValid(param,"device_id")){
			if(!where.isEmpty()){
				where=where+" and device_id = '"+param.getString("device_id")+"'";
			}else{
				where="device_id ='"+param.getString("device_id")+"'";
			}
		}
		if(checkParamValid(param,"device_type")){
			if(!where.isEmpty()){
				where=where+" and device_type = '"+param.getString("device_type")+"'";
			}else{
				where="device_type ='"+param.getString("device_type")+"'";
			}
		}
		if(checkParamValid(param,"device_name")){
			if(!where.isEmpty()){
				where=where+" and device_name like '%"+param.getString("device_name")+"%'";
			}else{
				where="device_name like '%"+param.getString("device_name")+"%'";
			}
		}
		if(!where.isEmpty()){
			sql=sql+" where "+where;
		}
		if(checkParamValid(param,"order_by")){
			sql=sql+" order by "+param.getString("order_by");
		}
		sql=sql+" order by limit_time";
		return sql;
	}
    public void saveUploadFileRecord(JSONObject json, Data data) throws JSONException, SQLException {
		//构造sql语句，根据传递过来的查询条件参数
		//首先分析json里有多少文件，多个文件需要用循环构造多个sql语句
//		String file_path_name=json.getString("file_path_name");
//		String objectID=json.getString("file_object_id");
//		String file_url_name=json.getString("file_url_name");
//		showDebug(file_path_name);

		showDebug("[saveUploadFileRecord]保存文件后，文件和字段信息json是："+json.toString());
		JSONArray array=json.getJSONArray("upload_files");
		//showDebug(array.toString());
		for(int i=0;i<array.length();i++){
			HashMap map=(HashMap)array.get(0);
			String file_path_name=(String)map.get("file_path_name");
			String file_object_id=(String)map.get("file_object_id");
			String file_url_name=(String)map.get("file_url_name");
			String sql="insert into attachment(object_id,file_name,url_name)";
			sql=sql+"values('"+file_object_id+"','"+file_path_name+"','"+file_url_name+"')";
			data.getParam().put("sql",sql);
			showDebug("创建的sql是"+sql);
			updateRecord(data,json);
			json.put("attachment",file_object_id);
		}

		/*--------------------循环 开始--------------------*/
		//for(int i=0;i<nCount;i++){
		//String sql=createSaveUploadFileRecordSql(data);			//构造sql语句，根据传递过来的查询条件参数
		//data.getParam().put("sql",sql);
		//updateRecord(data,json);
		//}
		/*--------------------循环 结束--------------------*/
    }
	public void getGpsStatus(Data data, JSONObject json) throws JSONException, SQLException{
		/*--------------------获取变量 开始--------------------*/
		String resultMsg = "ok";
		int resultCode = 0;
		List jsonList = new ArrayList();
		String timeFrom="2023-5-30 00:00:00";
		String timeTo="2024-5-30 23:50:00";
		/*--------------------获取变量 完毕--------------------*/
		/*--------------------数据操作 开始--------------------*/
		Db queryDb = new Db("test");
		String sql="SELECT count(*) as total FROM gps_history where gps_time BETWEEN '"+timeFrom+"' and '"+timeTo+"'";
		showDebug("[queryRecord]构造的SQL语句是：" + sql);
		int totalGpsActiveCount=0;
		try {
			ResultSet rs = queryDb.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			int fieldCount = rsmd.getColumnCount();
			while (rs.next()) {
				totalGpsActiveCount=rs.getInt("total");
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			showDebug("[queryRecord]查询数据库出现错误：" + sql);
			resultCode = 10;
			resultMsg = "查询数据库出现错误！" + e.getMessage();
		}
		queryDb.close();
		/*--------------------数据操作 结束--------------------*/
		/*--------------------返回数据 开始--------------------*/
		json.put("aaData",jsonList);
		json.put("gps_vehicle_active_number",totalGpsActiveCount);
		json.put("result_msg",resultMsg);															//如果发生错误就设置成"error"等
		json.put("result_code",resultCode);														//返回0表示正常，不等于0就表示有错误产生，错误代码
		/*--------------------返回数据 结束--------------------*/
	}
	public void getuserinfo(Data data, JSONObject json) throws JSONException, SQLException{
		/*--------------------获取变量 开始--------------------*/
		String resultMsg = "ok";
		int resultCode = 0;
		List jsonList = new ArrayList();
		List jsonName = new ArrayList();
		/*--------------------获取变量 完毕--------------------*/
		/*--------------------数据操作 开始--------------------*/
		DbRemote queryDb = new DbRemote("wtqjsz2025");
		String sql="select * from xm01_sys_user";
		showDebug("[getuserinfo]构造的SQL语句是：" + sql);
		try {
			ResultSet rs = queryDb.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			int fieldCount = rsmd.getColumnCount();
			while (rs.next()) {
				Map map = new HashMap();
				for (int i = 0; i < fieldCount; i++) {
					map.put(rsmd.getColumnName(i + 1), rs.getString(rsmd.getColumnName(i + 1)));
				}
				jsonList.add(map);
			}
			rs.close();
			//加表头
			for(int i=0;i<rsmd.getColumnCount();i++){
				String columnLabel=rsmd.getColumnLabel(i+1);
				jsonName.add(columnLabel);
			}
		} catch (Exception e) {
			e.printStackTrace();
			showDebug("[getuserinfo]查询数据库出现错误：" + sql);
			resultCode = 10;
			resultMsg = "查询数据库出现错误！" + e.getMessage();
		}
		queryDb.close();
		/*--------------------数据操作 结束--------------------*/
		/*--------------------返回数据 开始--------------------*/
		json.put("aaData",jsonList);
		json.put("aaFieldName",jsonName);
		json.put("result_msg",resultMsg);															//如果发生错误就设置成"error"等
		json.put("result_code",resultCode);														//返回0表示正常，不等于0就表示有错误产生，错误代码
		/*--------------------返回数据 结束--------------------*/
	}
	public void getVehicleNumber(Data data, JSONObject json) throws JSONException, SQLException{
		/*--------------------获取变量 开始--------------------*/
		String resultMsg = "ok";
		int resultCode = 0;
		List jsonList = new ArrayList();
		List jsonName = new ArrayList();
		/*--------------------获取变量 完毕--------------------*/
		/*--------------------数据操作 开始--------------------*/
		DbRemote queryDb = new DbRemote("wtqjsz2025");
		String sql="select * from xm01_statistic";
		showDebug("[getuserinfo]构造的SQL语句是：" + sql);
		try {
			ResultSet rs = queryDb.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			int fieldCount = rsmd.getColumnCount();
			while (rs.next()) {
				Map map = new HashMap();
				for (int i = 0; i < fieldCount; i++) {
					map.put(rsmd.getColumnName(i + 1), rs.getString(rsmd.getColumnName(i + 1)));
				}
				jsonList.add(map);
			}
			rs.close();
			//加表头
			for(int i=0;i<rsmd.getColumnCount();i++){
				String columnLabel=rsmd.getColumnLabel(i+1);
				jsonName.add(columnLabel);
			}
		} catch (Exception e) {
			e.printStackTrace();
			showDebug("[getcarinfo]查询数据库出现错误：" + sql);
			resultCode = 10;
			resultMsg = "查询数据库出现错误！" + e.getMessage();
		}
		queryDb.close();
		/*--------------------数据操作 结束--------------------*/
		/*--------------------返回数据 开始--------------------*/
		json.put("aaData",jsonList);
		json.put("aaFieldName",jsonName);
		json.put("result_msg",resultMsg);															//如果发生错误就设置成"error"等
		json.put("result_code",resultCode);														//返回0表示正常，不等于0就表示有错误产生，错误代码
		/*--------------------返回数据 结束--------------------*/
	}
	public void getvehicleinfo(Data data, JSONObject json) throws JSONException, SQLException{
		/*--------------------获取变量 开始--------------------*/
		String resultMsg = "ok";
		int resultCode = 0;
		List jsonList = new ArrayList();
		List jsonName = new ArrayList();
		/*--------------------获取变量 完毕--------------------*/
		/*--------------------数据操作 开始--------------------*/
		Db queryDb = new Db("test");
		String sql="select * from test_vehicle_monitor";
		showDebug("[getvehicleinfo]构造的SQL语句是：" + sql);
		try {
			ResultSet rs = queryDb.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			int fieldCount = rsmd.getColumnCount();
			while (rs.next()) {
				Map map = new HashMap();
				for (int i = 0; i < fieldCount; i++) {
					map.put(rsmd.getColumnName(i + 1), rs.getString(rsmd.getColumnName(i + 1)));
				}
				jsonList.add(map);
			}
			rs.close();
			//加表头
			for(int i=0;i<rsmd.getColumnCount();i++){
				String columnLabel=rsmd.getColumnLabel(i+1);
				jsonName.add(columnLabel);
			}
		} catch (Exception e) {
			e.printStackTrace();
			showDebug("[getvehicleinfo]查询数据库出现错误：" + sql);
			resultCode = 10;
			resultMsg = "查询数据库出现错误！" + e.getMessage();
		}
		queryDb.close();
		/*--------------------数据操作 结束--------------------*/
		/*--------------------返回数据 开始--------------------*/
		json.put("aaData",jsonList);
		json.put("aaFieldName",jsonName);
		json.put("result_msg",resultMsg);															//如果发生错误就设置成"error"等
		json.put("result_code",resultCode);														//返回0表示正常，不等于0就表示有错误产生，错误代码
		/*--------------------返回数据 结束--------------------*/
	}
	public void getvehiclecarinfo(Data data, JSONObject json) throws JSONException, SQLException{
		/*--------------------获取变量 开始--------------------*/
		String resultMsg = "ok";
		int resultCode = 0;
		List jsonList = new ArrayList();
		List jsonName = new ArrayList();
		/*--------------------获取变量 完毕--------------------*/
		/*--------------------数据操作 开始--------------------*/
		DbRemote queryDb = new DbRemote("wtqjsz2025");
		String sql="select * from xm01_cardata";
		showDebug("[getvehicleinfo]构造的SQL语句是：" + sql);
		try {
			ResultSet rs = queryDb.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			int fieldCount = rsmd.getColumnCount();
			while (rs.next()) {
				Map map = new HashMap();
				for (int i = 0; i < fieldCount; i++) {
					map.put(rsmd.getColumnName(i + 1), rs.getString(rsmd.getColumnName(i + 1)));
				}
				jsonList.add(map);
			}
			rs.close();
			//加表头
			for(int i=0;i<rsmd.getColumnCount();i++){
				String columnLabel=rsmd.getColumnLabel(i+1);
				jsonName.add(columnLabel);
			}
		} catch (Exception e) {
			e.printStackTrace();
			showDebug("[getvehicleinfo]查询数据库出现错误：" + sql);
			resultCode = 10;
			resultMsg = "查询数据库出现错误！" + e.getMessage();
		}
		queryDb.close();
		/*--------------------数据操作 结束--------------------*/
		/*--------------------返回数据 开始--------------------*/
		json.put("aaData",jsonList);
		json.put("aaFieldName",jsonName);
		json.put("result_msg",resultMsg);															//如果发生错误就设置成"error"等
		json.put("result_code",resultCode);														//返回0表示正常，不等于0就表示有错误产生，错误代码
		/*--------------------返回数据 结束--------------------*/
	}
	public void getvehicleinfo1(Data data, JSONObject json) throws JSONException, SQLException{
		/*--------------------获取变量 开始--------------------*/
		String resultMsg = "ok";
		int resultCode = 0;
		List jsonList = new ArrayList();
		List jsonName = new ArrayList();
		/*--------------------获取变量 完毕--------------------*/
		/*--------------------数据操作 开始--------------------*/
		DbRemote queryDb = new DbRemote("wtqjsz2025");
		String sql="select * from xm01_yellowline";
		showDebug("[getvehicleinfo]构造的SQL语句是：" + sql);
		try {
			ResultSet rs = queryDb.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			int fieldCount = rsmd.getColumnCount();
			while (rs.next()) {
				Map map = new HashMap();
				for (int i = 0; i < fieldCount; i++) {
					map.put(rsmd.getColumnName(i + 1), rs.getString(rsmd.getColumnName(i + 1)));
				}
				jsonList.add(map);
			}
			rs.close();
			//加表头
			for(int i=0;i<rsmd.getColumnCount();i++){
				String columnLabel=rsmd.getColumnLabel(i+1);
				jsonName.add(columnLabel);
			}
		} catch (Exception e) {
			e.printStackTrace();
			showDebug("[getvehicleinfo]查询数据库出现错误：" + sql);
			resultCode = 10;
			resultMsg = "查询数据库出现错误！" + e.getMessage();
		}
		queryDb.close();
		/*--------------------数据操作 结束--------------------*/
		/*--------------------返回数据 开始--------------------*/
		json.put("aaData",jsonList);
		json.put("aaFieldName",jsonName);
		json.put("result_msg",resultMsg);															//如果发生错误就设置成"error"等
		json.put("result_code",resultCode);														//返回0表示正常，不等于0就表示有错误产生，错误代码
		/*--------------------返回数据 结束--------------------*/
	}
	public void getvehicleinfo2(Data data, JSONObject json) throws JSONException, SQLException{
		/*--------------------获取变量 开始--------------------*/
		String resultMsg = "ok";
		int resultCode = 0;
		List jsonList = new ArrayList();
		List jsonName = new ArrayList();
		/*--------------------获取变量 完毕--------------------*/
		/*--------------------数据操作 开始--------------------*/
		DbRemote queryDb = new DbRemote("wtqjsz2025");
		String sql="select * from xm01_parking";
		showDebug("[getvehicleinfo]构造的SQL语句是：" + sql);
		try {
			ResultSet rs = queryDb.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			int fieldCount = rsmd.getColumnCount();
			while (rs.next()) {
				Map map = new HashMap();
				for (int i = 0; i < fieldCount; i++) {
					map.put(rsmd.getColumnName(i + 1), rs.getString(rsmd.getColumnName(i + 1)));
				}
				jsonList.add(map);
			}
			rs.close();
			//加表头
			for(int i=0;i<rsmd.getColumnCount();i++){
				String columnLabel=rsmd.getColumnLabel(i+1);
				jsonName.add(columnLabel);
			}
		} catch (Exception e) {
			e.printStackTrace();
			showDebug("[getvehicleinfo]查询数据库出现错误：" + sql);
			resultCode = 10;
			resultMsg = "查询数据库出现错误！" + e.getMessage();
		}
		queryDb.close();
		/*--------------------数据操作 结束--------------------*/
		/*--------------------返回数据 开始--------------------*/
		json.put("aaData",jsonList);
		json.put("aaFieldName",jsonName);
		json.put("result_msg",resultMsg);															//如果发生错误就设置成"error"等
		json.put("result_code",resultCode);														//返回0表示正常，不等于0就表示有错误产生，错误代码
		/*--------------------返回数据 结束--------------------*/
	}
	public void getvehicleinfo3(Data data, JSONObject json) throws JSONException, SQLException{
		/*--------------------获取变量 开始--------------------*/
		String resultMsg = "ok";
		int resultCode = 0;
		List jsonList = new ArrayList();
		List jsonName = new ArrayList();
		/*--------------------获取变量 完毕--------------------*/
		/*--------------------数据操作 开始--------------------*/
		DbRemote queryDb = new DbRemote("wtqjsz2025");
		String sql="select * from xm01_overspeeds";
		showDebug("[getvehicleinfo]构造的SQL语句是：" + sql);
		try {
			ResultSet rs = queryDb.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			int fieldCount = rsmd.getColumnCount();
			while (rs.next()) {
				Map map = new HashMap();
				for (int i = 0; i < fieldCount; i++) {
					map.put(rsmd.getColumnName(i + 1), rs.getString(rsmd.getColumnName(i + 1)));
				}
				jsonList.add(map);
			}
			rs.close();
			//加表头
			for(int i=0;i<rsmd.getColumnCount();i++){
				String columnLabel=rsmd.getColumnLabel(i+1);
				jsonName.add(columnLabel);
			}
		} catch (Exception e) {
			e.printStackTrace();
			showDebug("[getvehicleinfo]查询数据库出现错误：" + sql);
			resultCode = 10;
			resultMsg = "查询数据库出现错误！" + e.getMessage();
		}
		queryDb.close();
		/*--------------------数据操作 结束--------------------*/
		/*--------------------返回数据 开始--------------------*/
		json.put("aaData",jsonList);
		json.put("aaFieldName",jsonName);
		json.put("result_msg",resultMsg);															//如果发生错误就设置成"error"等
		json.put("result_code",resultCode);														//返回0表示正常，不等于0就表示有错误产生，错误代码
		/*--------------------返回数据 结束--------------------*/
	}
	public void getvehicleinfo4(Data data, JSONObject json) throws JSONException, SQLException{
		/*--------------------获取变量 开始--------------------*/
		String resultMsg = "ok";
		int resultCode = 0;
		List jsonList = new ArrayList();
		List jsonName = new ArrayList();
		/*--------------------获取变量 完毕--------------------*/
		/*--------------------数据操作 开始--------------------*/
		DbRemote queryDb = new DbRemote("wtqjsz2025");
		String sql="select * from xm01_redlights";
		showDebug("[getvehicleinfo]构造的SQL语句是：" + sql);
		try {
			ResultSet rs = queryDb.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			int fieldCount = rsmd.getColumnCount();
			while (rs.next()) {
				Map map = new HashMap();
				for (int i = 0; i < fieldCount; i++) {
					map.put(rsmd.getColumnName(i + 1), rs.getString(rsmd.getColumnName(i + 1)));
				}
				jsonList.add(map);
			}
			rs.close();
			//加表头
			for(int i=0;i<rsmd.getColumnCount();i++){
				String columnLabel=rsmd.getColumnLabel(i+1);
				jsonName.add(columnLabel);
			}
		} catch (Exception e) {
			e.printStackTrace();
			showDebug("[getvehicleinfo]查询数据库出现错误：" + sql);
			resultCode = 10;
			resultMsg = "查询数据库出现错误！" + e.getMessage();
		}
		queryDb.close();
		/*--------------------数据操作 结束--------------------*/
		/*--------------------返回数据 开始--------------------*/
		json.put("aaData",jsonList);
		json.put("aaFieldName",jsonName);
		json.put("result_msg",resultMsg);															//如果发生错误就设置成"error"等
		json.put("result_code",resultCode);														//返回0表示正常，不等于0就表示有错误产生，错误代码
		/*--------------------返回数据 结束--------------------*/
	}
	public void getvehicleinfo5(Data data, JSONObject json) throws JSONException, SQLException{
		/*--------------------获取变量 开始--------------------*/
		String resultMsg = "ok";
		int resultCode = 0;
		List jsonList = new ArrayList();
		List jsonName = new ArrayList();
		/*--------------------获取变量 完毕--------------------*/
		/*--------------------数据操作 开始--------------------*/
		DbRemote queryDb = new DbRemote("wtqjsz2025");
		String sql="select * from xm01_opposites";
		showDebug("[getvehicleinfo]构造的SQL语句是：" + sql);
		try {
			ResultSet rs = queryDb.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			int fieldCount = rsmd.getColumnCount();
			while (rs.next()) {
				Map map = new HashMap();
				for (int i = 0; i < fieldCount; i++) {
					map.put(rsmd.getColumnName(i + 1), rs.getString(rsmd.getColumnName(i + 1)));
				}
				jsonList.add(map);
			}
			rs.close();
			//加表头
			for(int i=0;i<rsmd.getColumnCount();i++){
				String columnLabel=rsmd.getColumnLabel(i+1);
				jsonName.add(columnLabel);
			}
		} catch (Exception e) {
			e.printStackTrace();
			showDebug("[getvehicleinfo]查询数据库出现错误：" + sql);
			resultCode = 10;
			resultMsg = "查询数据库出现错误！" + e.getMessage();
		}
		queryDb.close();
		/*--------------------数据操作 结束--------------------*/
		/*--------------------返回数据 开始--------------------*/
		json.put("aaData",jsonList);
		json.put("aaFieldName",jsonName);
		json.put("result_msg",resultMsg);															//如果发生错误就设置成"error"等
		json.put("result_code",resultCode);														//返回0表示正常，不等于0就表示有错误产生，错误代码
		/*--------------------返回数据 结束--------------------*/
	}
}
