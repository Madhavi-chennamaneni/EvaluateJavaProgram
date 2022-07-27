package com.gradious.evaluate;

import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;


public class RunJavaScriptProgram {
    public static void main(String[] args) throws Exception {
    	
    	ScriptEngineManager sem = new ScriptEngineManager();
    	List<ScriptEngineFactory> factories = sem.getEngineFactories();
    	for (ScriptEngineFactory factory : factories)
    	    System.out.println(factory.getEngineName() + " " + factory.getEngineVersion() + " " + factory.getNames());
    	if (factories.isEmpty())
    	    System.out.println("No Script Engines found");
    	
        // create a script engine manager
        ScriptEngineManager factory = new ScriptEngineManager();
        // create a JavaScript engine
        ScriptEngine engine = factory.getEngineByName("JavaScript");
        // evaluate JavaScript code from String
        engine.eval("print('Hello, World')");
    }
}

