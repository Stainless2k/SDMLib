/*
   Copyright (c) 2012 zuendorf 
   
   Permission is hereby granted, free of charge, to any person obtaining a copy of this software 
   and associated documentation files (the "Software"), to deal in the Software without restriction, 
   including without limitation the rights to use, copy, modify, merge, publish, distribute, 
   sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is 
   furnished to do so, subject to the following conditions: 
   
   The above copyright notice and this permission notice shall be included in all copies or 
   substantial portions of the Software. 
   
   The Software shall be used for Good, not Evil. 
   
   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING 
   BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND 
   NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, 
   DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */
   
package org.sdmlib.examples.studyright;
   
import java.util.LinkedHashSet;

import javax.tools.JavaCompiler;

import org.junit.Assert;
import org.junit.Test;
import org.sdmlib.codegen.Parser;
import org.sdmlib.codegen.SymTabEntry;
import org.sdmlib.models.classes.Association;
import org.sdmlib.models.classes.Attribute;
import org.sdmlib.models.classes.ClassModel;
import org.sdmlib.models.classes.Clazz;
import org.sdmlib.models.classes.Role;
import org.sdmlib.scenarios.LogEntry;
import org.sdmlib.scenarios.Scenario;
import org.sdmlib.scenarios.ScenarioManager;

import com.sun.tools.javac.Main;
   
public class StudyRightClassesCodeGen 
{
   @Test
   public void testStudyRightClassesCodeGen()
   {
      Scenario scenario = new Scenario("StudyRightClassesCodeGen");
      
      
      //============================================================
      scenario.add("1. generate class University");
      
      ClassModel model = new ClassModel();
      
      Clazz uniClass = new Clazz("org.sdmlib.examples.studyright.University")
      .withAttribute("name", "String");
            
      scenario.addImage(model.dumpClassDiag("StudyRightClasses01"));
      
      
      //============================================================
      scenario.add("2. generate class Student with new notation", 
         IMPLEMENTATION, "zuendorf", "18.03.2012 23:05:42", 1, 20);
      
      Clazz studClass = new Clazz("org.sdmlib.examples.studyright.Student")
      .withAttribute("name", "String")
      .withAttribute("matrNo", "int");

      scenario.addImage(model.dumpClassDiag("StudyRightClasses02"));
      
      
      //============================================================
      scenario.add("3. add uni --> stud assoc");
      
      Association uniToStud = new Association()
      .withSource("uni", uniClass, Role.ONE, Role.AGGREGATION)
      .withTarget("students", studClass, Role.MANY); 
      
      scenario.addImage(model.dumpClassDiag("StudyRightClasses03"));
      
      
      //============================================================
      scenario.add("4. add uni --> room");
      
      Clazz roomClass = new Clazz("org.sdmlib.examples.studyright.Room")
      .withAttribute("roomNo", "String");
      
      Association uniToRoom = new Association()
      .withSource("uni", uniClass, Role.ONE, Role.AGGREGATION)
      .withTarget("rooms", roomClass, Role.MANY); 
            
      scenario.addImage(model.dumpClassDiag("StudyRightClasses04"));


      //============================================================
      model.updateFromCode("examples test src", "org.sdmlib.examples");
      
      model.generate("examples");
      
      scenario.add("5. generate generic set for attributes", 
         IMPLEMENTATION, "zuendorf", "18.03.2012 23:05:42", 1, 20);
      
      Parser parser = studClass.getOrCreateParser("examples");
      int pos = parser.indexOf(Parser.METHOD + ":set(String,Object)");
      
      Assert.assertTrue("did not find method set(String,Object) in class student", pos >= 0);
      
      SymTabEntry symTabEntry = parser.getSymTab().get(Parser.METHOD + ":set(String,Object)");
      
      Assert.assertNotNull("did not find symtab entry for method set(String,Object)", symTabEntry);
      
      String methodText = "   " + parser.getFileBody().substring(symTabEntry.getStartPos(), symTabEntry.getEndPos()+1);
      
      scenario.add(methodText);
      
      
      //============================================================
      scenario.add("6. generate generic get for attributes", 
         IMPLEMENTATION, "zuendorf", "19.03.2012 00:13:42", 1, 18);
      
      pos = parser.indexOf(Parser.METHOD + ":get(String)");
      
      Assert.assertTrue("did not find method get(String) in class student", pos >= 0);
      
      symTabEntry = parser.getSymTab().get(Parser.METHOD + ":get(String)");
      
      Assert.assertNotNull("did not find symtab entry for method get(String)", symTabEntry);
      
      methodText = "   " + parser.getFileBody().substring(symTabEntry.getStartPos(), symTabEntry.getEndPos()+1);
      
      scenario.add(methodText);
      
      //============================================================
      scenario.add("x. generate removeYou method");
      scenario.add("x. generate imports");
      scenario.add("x. generate property change support");

      
      scenario.add("next. compile University.java");
      
      String javaClassPath = System.getProperty("java.class.path");

      String[] compArgs = new String[]
         {
         "-d", "bin",
         "-sourcepath", "examples",
         "-classpath", javaClassPath,
         "examples/org/sdmlib/examples/studyright/University.java"
         };

      int compResult = Main.compile(compArgs);
      
      Assert.assertEquals("compile did not work: ", 0, compResult);
      
      scenario.add("next. use University class to build object structure");
      
      University uni = new University()
         .withName("StudyRight");
      
      Student stud1 = new Student()
      .withUni(uni);
      
      Student stud2 = new Student()
      .withUni(uni);
      
      Assert.assertEquals("false number of students:" , 2, uni.getStudents().size());
      
      ScenarioManager.get()
      .add(scenario)
      .dumpHTML();
   }

   private static final String MODELING = "modeling";
   private static final String ACTIVE = "active";
   private static final String DONE = "done";
   private static final String IMPLEMENTATION = "implementation";
   private static final String BACKLOG = "backlog";
}


