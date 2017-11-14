package com.util.general;

public class Test {

	public static void main(String[] args) {
		 try {
			 
			 String s;
			 
			 byte[] encript = UtilsX.cifra("admin");
			 System.out.println(encript);
			 s = new String(encript, "US-ASCII");
			System.out.println("string"+s);
			
			byte[] b = {-99, 10, -49, -19, -22, 44, -90, -100, -30, 22, -112, -56, -78, 117, -69, -13};
			System.out.println("D "+UtilsX.descifra(b));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
