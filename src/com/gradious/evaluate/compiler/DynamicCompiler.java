package com.gradious.evaluate.compiler;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;

public class DynamicCompiler {
	 public static void main(String[] args) throws Exception {
	        // Full name of the class that will be compiled.
	        // If class should be in some package,
	        // fullName should contain it too
	        // (ex. "testpackage.TestClass")
	        String fullName = "TestClass";

	        // Here we specify the source code of the class to be compiled
	        StringBuilder src = new StringBuilder();
	        src.append("public class TestClass {\n");
	        src.append("    public static void main() {} \n public String toString() {\n");
	        src.append("        return \"Hello, I am \" + ");
	        src.append("this.getClass().getSimpleName();\n");
	        src.append("    }\n");
	        src.append("}\n");

	        System.out.println(src);

	        compileAndRunprogram(fullName, src);
	 }

	private static void compileAndRunprogram(String fullName, StringBuilder src)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		// We get an instance of JavaCompiler. Then
		// we create a file manager
		// (our custom implementation of it)
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		JavaFileManager fileManager = new
		    ClassFileManager(compiler
		        .getStandardFileManager(null, null, null));

		// Dynamic compiling requires specifying
		// a list of "files" to compile. In our case
		// this is a list containing one "file" which is in our case
		// our own implementation (see details below)
		List<JavaFileObject> jfiles = new ArrayList<JavaFileObject>();
		jfiles.add(new CharSequenceJavaFileObject(fullName, src));

		// We specify a task to the compiler. Compiler should use our file
		// manager and our list of "files".
		// Then we run the compilation with call()
      boolean isSuccess = compiler.getTask(null, fileManager, null, null,
		    null, jfiles).call();

      if(isSuccess) {
		// Creating an instance of our compiled class and
		// running its toString() method
		Object instance;
		try {
			instance = fileManager.getClassLoader(null)
			    .loadClass(fullName).getDeclaredConstructor().newInstance();
			
			System.out.println(instance);
			
			//Class.forName("TestClass").getDeclaredMethod("main", new Class[] { String[].class })
           // .invoke(null, new Object[] { null });
			
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
   }
	}
}
