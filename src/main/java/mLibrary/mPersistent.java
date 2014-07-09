package mLibrary;

public class mPersistent extends mClass {

	
	public Object $OpenId(Object... args){
		String id = args[0].toString();
		Integer concurrency = args.length>=2?Integer.valueOf(args[1].toString()):-1;
		$Library.Status sc = new $Library.Status(args.length>=3?mFncUtil.integerConverter(args[2]):1);
		return this;
	}	
	
	public Object $ExistsId(Object... args){
		return "";
	}
	
	
}
