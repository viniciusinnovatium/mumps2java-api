import java.net.URI;


public class $File {
	public static Object NormalizeDirectory(Object dir){
		return URI.create(String.valueOf(dir)).normalize().toString();
	}
}
