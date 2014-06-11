package $Library;


public class File {
	public static Object Exists(Object dir){
		return new java.io.File(String.valueOf(dir)).exists();
	}
}
