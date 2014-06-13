
public class $R {
	public static Object EXIST(Object... args){		
		try {
			Class.forName(String.valueOf(args[0]).replace(".OBJ", ""));
			return true;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
				//throw new IllegalArgumentException("Class "+args[0]+ "not found!");
			System.err.println("Class "+args[0]+ " not found with $R.EXIST!");

		}
		return false;
	}
}
