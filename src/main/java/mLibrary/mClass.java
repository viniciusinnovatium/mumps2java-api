package mLibrary;


public class mClass {
	protected mContext m$ = new mContext();

	public void cmd$Do(String methodName, Object... parameters) {
		throw new UnsupportedOperationException();
	}

	public void cmd$Do(String methodName) {
		cmd$Do(methodName, (Object[]) null);
	}

	public Object fnc$(String methodName, Object... parameters) {
		throw new UnsupportedOperationException();
	}

	public void cmd$Write(String string) {
		throw new UnsupportedOperationException();
	}
}
