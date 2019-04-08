package MD5;

public class convertMD5 {
	public static String conventMD5(String inStr){
		  char[] a = inStr.toCharArray();  
	        for (int i = 0; i < a.length; i++){  
	            a[i] = (char) (a[i] ^ 't');  
	        }  
	        String s = new String(a);  
	        return s;  
	}
}
