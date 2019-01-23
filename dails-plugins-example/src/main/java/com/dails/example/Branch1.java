package com.dails.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;

public class Branch1 extends Example {

	@Override
	public void initFiles() {
		File rootfile = new File(getRooturl());
		// 判断文件夹是否存在,如果不存在则创建文件夹
		if (!rootfile.exists()) {
			System.err.println("项目根路径不存在");
			return;
		}
		String workUrl = getWorkUrl();
		File javafile = new File(workUrl);
		if (!javafile.exists()) {// 如果不存在java目录，创建它
			javafile.mkdirs();
		}

		Tools.mkFile(workUrl + "/controller");
		Tools.mkFile(workUrl + "/dao");
		Tools.mkFile(workUrl + "/service");
		Tools.mkFile(workUrl + "/domain");
		Tools.mkFile(workUrl + "/utils");
		Tools.mkFile(workUrl + "/service/impl");
		Tools.mkFile(workUrl + "/dao/impl");
		Tools.mkFile(workUrl + "/utils");

		Tools.mkFile(getRooturl() + "/src/main/resources/templates");

		addLauncher();
	}

	@Override
	public void initJava() {
		addController();
		addDao();
		addService();

	}

	@Override
	public void initJsp() {
		String name = getEntityClass().getSimpleName();
		String name2 = name.substring(0, 1).toLowerCase() + name.substring(1);
		String urlString = getRooturl() + "/src/main/resources/templates/" + name2;
		Tools.mkFile(urlString);// 添加文件夹

		File indexFile = new File(urlString + "/index.html");
		File newFile = new File(urlString + "/new.html");
		File editFile = new File(urlString + "/edit.html");
		File showFile = new File(urlString + "/show.html");
		createJsp(indexFile,name, "/branch1/views/index.html");
		createJsp(newFile,name, "/branch1/views/new.html");
		createJsp(editFile, name,"/branch1/views/edit.html");
		createJsp(showFile, name,"/branch1/views/show.html");
	}

	private void addLauncher() {
		// branch1 添加 Launcher.java

		File file = new File(getWorkUrl() + "/Launcher.txt");
		if (!file.exists()) {
			try {
				file.createNewFile();
				System.out.println("新增文件: " + file.getName());
				BufferedWriter output = new BufferedWriter(new FileWriter(file));
				String string = readFile("/branch1/Launcher.java");

				String myurl = getUrl().replaceAll("/", ".");
				string = string.replaceAll("\\$\\{url\\}", myurl);
				output.write(string);
				output.close();
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println("不存在路径：" + getWorkUrl());
			}
		} else {
			System.out.println("已存在: " + file.getName());
		}

	}

	public void addController() {
		System.out.println(System.getProperty("user.dir"));// user.dir指定了当前的路径

		String domainUrl = getEntityClass().getPackage().getName().toString();
		String name = getEntityClass().getSimpleName();

		File controllerFile = new File(getWorkUrl() + "/controller/" + name + "Controller.java");
		createFile(controllerFile, domainUrl, name, "/branch1/ObjController.txt");

	}

	public void addService() {
		String domainUrl = getEntityClass().getPackage().getName().toString();
		String name = getEntityClass().getSimpleName();
		String workUrl = getWorkUrl();

		File iServiceFile = new File(workUrl + "/service/I" + name + "Service.java");
		File serviceimplFile = new File(workUrl + "/service/impl/" + name + "Service.java");
		createFile(iServiceFile, domainUrl, name, "/branch1/IObjService.txt");
		createFile(serviceimplFile, domainUrl, name, "/branch1/ObjService.txt");
	}

	public void addDao() {
		String domainUrl = getEntityClass().getPackage().getName().toString();
		String name = getEntityClass().getSimpleName();
		File daoFile = new File(getWorkUrl() + "/dao/" + name + "Dao.java");

		createFile(daoFile, domainUrl, name, "/branch1/ObjDao.txt");

	}

	private void createFile(File file, String domainUrl, String name, String mobanUrl) {
		if (!file.exists()) {
			try {
				file.createNewFile();
				System.out.println("createNewFile: " + file.getName());
				String name2 = name.substring(0, 1).toLowerCase() + name.substring(1);
				BufferedWriter output = new BufferedWriter(new FileWriter(file));

				String string = readFile(mobanUrl);
				string = string.replaceAll("\\$\\{Objname\\}", name);
				string = string.replaceAll("\\$\\{objname\\}", name2);
				string = string.replaceAll("\\$\\{domainUrl\\}", domainUrl);

				String myurl = getUrl().replaceAll("/", ".");
				string = string.replaceAll("\\$\\{url\\}", myurl);
				output.write(string);
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("路径不存在，请先执行Dails.getInstance().addFiles()");
			}
		} else {
			System.out.println("已存在JAVA: " + file.getName());
		}
	}

	private void createJsp(File file,String ObjName, String mobanUrl) {
		if (!file.exists()) {
			try {
				file.createNewFile();
				System.out.println("createNewFile: " + file.getName());
				String name = file.getName();
				BufferedWriter output = new BufferedWriter(new FileWriter(file));

				String string = readFile(mobanUrl);

				String[] filedNames = getFiledName();
				String temp = "";
				String temp2 = "";
				if (name.equals("index.html")) {
					for (int i = 0; i < filedNames.length; i++) {
						if (!filedNames[i].equals("serialVersionUID")) {
							temp += "								<td>" + filedNames[i] + "</td>\n";
							temp2 += "										<td th:text='\\$\\{s." + filedNames[i] + "\\}'></td>\n";
						}
					}

					string = string.replaceAll("\\$\\{tr1\\}", temp);
					string = string.replaceAll("\\$\\{tr2\\}", temp2);
				}
				if (name.equals("new.html")) {
					for (int i = 0; i < filedNames.length; i++) {
						if (!filedNames[i].equals("serialVersionUID") && !filedNames[i].equals("id")) {
							temp += "<div class='form-group col-lg-12'>" + "						<label for='"+filedNames[i]+"' class='col-sm-2 control-label'>"+filedNames[i]+"</label>" + "						<div class='col-sm-10'>"
									+ "							<input type='text' class='form-control' name='"+filedNames[i]+"' id='"+filedNames[i]+"' th:value='${obj."+filedNames[i]+"}' />" 
									+ "						</div>" + "					</div>";
						}
					}
					string = string.replace("newForm", temp);
				}
				if (name.equals("show.html")) {
					for (int i = 0; i < filedNames.length; i++) {
						if (!filedNames[i].equals("serialVersionUID")) {
							temp += "	<div>  \n" + "		<span class='label label-default'>" + filedNames[i] +"</span> \n <p th:text='\\$\\{obj." + filedNames[i] + "\\}'" + "></p>	</div> \n";
						}
					}
					string = string.replaceAll("\\$\\{showForm\\}", temp);
				}
				String objName = ObjName.substring(0, 1).toLowerCase() + ObjName.substring(1);
				
				string = string.replaceAll("\\$\\{objname\\}", objName);
				string = string.replaceAll("\\$\\{Objname\\}", ObjName);
				
				output.write(string);
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("已存在JSP: " + file.getName());
		}
	}

	private String[] getFiledName() {
		Field[] fields = getEntityClass().getDeclaredFields();
		String[] fieldNames = new String[fields.length];
		for (int i = 0; i < fields.length; i++) {
			fieldNames[i] = fields[i].getName();
		}
		return fieldNames;
	}

	public String readFile(String url) {
		File file;
		String ss = "";
		try {
			URL url2 = this.getClass().getResource(url);
			
			
			ClassPathResource resource = new ClassPathResource(url);
			InputStream inputStream = resource.getInputStream();
			ss = Tools.inputStream2String(inputStream);
			
			/*file = ResourceUtils.getFile(url2);//
			FileReader reader = new FileReader(file);// 定义一个fileReader对象，用来初始化BufferedReader
			BufferedReader bReader = new BufferedReader(reader);// new一个BufferedReader对象，将文件内容读取到缓存
			String s = "";
			while ((s = bReader.readLine()) != null) {// 逐行读取文件内容，不读取换行符和末尾的空格
				sb.append(s + "\n");// 将读取的字符串添加换行符后累加存放在缓存中
			}
			bReader.close();*/
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ss;
	}

}
