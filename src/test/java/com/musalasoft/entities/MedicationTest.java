package com.musalasoft.entities;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class MedicationTest {
	
	String codeRegex="^([A-Z]*\\_*[0-9]*)+";
	String nameRegex="^([A-Za-z]*[0-9]*\\_*\\-*)+";
	@Test
	public void testMedictionHasUnderScoreCode(){

		String code="SDF_SDF099";
		
		 Pattern pattern = Pattern.compile(codeRegex);
		    Matcher matcher = pattern.matcher(code);
		    int matches = 0;
		    while (matcher.find()) {
		        matches++;
		    }
		    assertTrue(matches>0);
		assertTrue(code.matches(codeRegex));
		
	}
	
	@Test
	public void testMedictionBeginWithUnderScoreCode(){

		String code="_SDFSDF099";
		
		 Pattern pattern = Pattern.compile(codeRegex);
		    Matcher matcher = pattern.matcher(code);
		    int matches = 0;
		    while (matcher.find()) {
		        matches++;
		    }
		    assertTrue(matches>0);
		assertTrue(code.matches(codeRegex));
		
	}
	
	@Test
	public void testMedictionHasNUmberOnlyCode(){

		String code="123456";
		
		 Pattern pattern = Pattern.compile(codeRegex);
		    Matcher matcher = pattern.matcher(code);
		    int matches = 0;
		    while (matcher.find()) {
		        matches++;
		    }
		    assertTrue(matches>0);
		assertTrue(code.matches(codeRegex));
		
	}
	
	@Test
	public void testMedictionIncorrectLowerCaseCode(){
			String code="_SDFS_sdDF099";
		    assertFalse(code.matches(codeRegex));
	}
	
	@Test
	public void testMedictionUpperLettersOnlyCode(){

		String code="ASDSDG";
		
		 Pattern pattern = Pattern.compile(codeRegex);
		    Matcher matcher = pattern.matcher(code);
		    int matches = 0;
		    while (matcher.find()) {
		        matches++;
		    }
		    assertTrue(matches>0);
		assertTrue(code.matches(codeRegex));
		
	}
	
	@Test
	public void testMedictionLetterOnlyName(){
		
		String name="drone";
		assertTrue(name.matches(nameRegex));
	}
	
	@Test
	public void testMedictionLetterWith_Name(){
		
		String name="_drone_";
		assertTrue(name.matches(nameRegex));
	}
	
	@Test
	public void testMedictionLetterWithDashName(){
		
		String name="dron-e";
		assertTrue(name.matches(nameRegex));
	}
	
	@Test
	public void testMedictionNameWithNumbers_(){
		
		String name="dron-e_1";
		String name2="dron2";
		assertTrue(name.matches(nameRegex));
		assertTrue(name2.matches(nameRegex));
	}

}
