package com.dails.example;

public class Test {
	public static void main(String[] args) {
		FactoryBranch1 fbranch1 = new FactoryBranch1();
		Branch1 branch1 = fbranch1.createExample();
		
		branch1.setRooturl("E:/code/dms/dms/dails-plugins/dails-example");
		branch1.setUrl("com/dms");
//		branch1.initFiles();
//		branch1.setEntityClass(Haha.class);
		branch1.initJava();
		branch1.initJsp();
		
	}
}
