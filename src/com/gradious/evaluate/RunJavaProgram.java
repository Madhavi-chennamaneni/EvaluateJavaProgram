package com.gradious.evaluate;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;


public class RunJavaProgram 
{
	
	/*public void handleRequest(InputStream inputStream, 
		      OutputStream outputStream, Context context) {
		        //String input = IOUtils.toString(inputStream, "UTF-8");
		        try {
					outputStream.write(("Hello World - ").getBytes());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }*/
	
    public static void main(String[] arguments) throws IOException,
            InterruptedException
    {
        StringBuilder src = new StringBuilder();
        src.append("public class TestClass {\n");
        src.append("    public static void main(String[] args) {System.out.println(\"HELLO:\");} \n public String toString() {\n");
        src.append("        return \"Hello, I am \" + ");
        src.append("this.getClass().getSimpleName();\n");
        src.append("    }\n");
        src.append("}\n");
        
       // executeProgram(src);
    }

	public String executeProgram(String src) throws IOException, InterruptedException {
		//String classPath = "C:\\Users\\windows 10\\";
		String classPath = "/tmp/";
        String className = "TestClass";
    	String compileoutput = compileProgram(classPath+className+".java",src);
    	
        System.out.println("COMPILER OUTPUT: "+compileoutput);
        if(compileoutput.isEmpty()) {
        	String programoutput = runProgram(classPath,className);
        	System.out.println("PROGRAM OUTPUT: "+programoutput);
        	return programoutput;
        }
        else {
        	return compileoutput;
        }
	}

    private String compileProgram(String classPath, String codeStr) throws IOException, InterruptedException
    {
    	File sourceFile = new File( classPath );
    	Files.write(sourceFile.toPath(), codeStr.getBytes(StandardCharsets.UTF_8));

//        ProcessBuilder processBuilder = new ProcessBuilder("javac",
//                "C:\\Users\\windows 10\\Downloads\\aws-doc-sdk-examples-main\\aws-doc-sdk-examples-main\\javav2\\usecases\\Creating_rds_item_tracker\\src\\main\\java\\Test.java");
//    	
    	 ProcessBuilder processBuilder = new ProcessBuilder("javac",sourceFile.getAbsolutePath());
    	
    	//System.out.println(System.getProperty("java.class.path"));
    	//processBuilder.command("java", "-cp", "C:\\Users\\windows 10\\Downloads\\aws-doc-sdk-examples-main\\aws-doc-sdk-examples-main\\javav2\\usecases\\Creating_rds_item_tracker\\src\\main\\java","Test");
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();
        StringBuilder processOutput = new StringBuilder();

        try (BufferedReader processOutputReader = new BufferedReader(
                new InputStreamReader(process.getInputStream()));)
        {
            String readLine;

            while ((readLine = processOutputReader.readLine()) != null)
            {
                processOutput.append(readLine + System.lineSeparator());
            }

            process.waitFor();
        }

        return processOutput.toString().trim();
    }


    private String runProgram(String classPath,String className) throws IOException, InterruptedException
    {
    	ProcessBuilder processBuilder = new ProcessBuilder();
    	//System.out.println(System.getProperty("java.class.path"));
    	//processBuilder.command("java", "-cp", "C:\\Users\\windows 10\\Downloads\\aws-doc-sdk-examples-main\\aws-doc-sdk-examples-main\\javav2\\usecases\\Creating_rds_item_tracker\\src\\main\\java","Test");

    	processBuilder.command("java", "-cp", classPath,className);
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();
        StringBuilder processOutput = new StringBuilder();

        try (BufferedReader processOutputReader = new BufferedReader(
                new InputStreamReader(process.getInputStream()));)
        {
            String readLine;

            while ((readLine = processOutputReader.readLine()) != null)
            {
                processOutput.append(readLine + System.lineSeparator());
            }

            process.waitFor();
        }

        return processOutput.toString().trim();
    }
}
