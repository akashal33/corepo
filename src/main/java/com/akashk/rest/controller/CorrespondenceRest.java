package com.akashk.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akashk.binding.CorrespondenceResponce;
import com.akashk.service.CorrespondenceService;

@RestController
public class CorrespondenceRest {
	
	@Autowired
	private CorrespondenceService correspondenceService;
	
	@GetMapping("/process")
	public ResponseEntity<CorrespondenceResponce> correspondense() {
		
		CorrespondenceResponce correspondenceResponce = correspondenceService.correspondenceProcess();
		
		return new ResponseEntity<CorrespondenceResponce>(correspondenceResponce, HttpStatus.OK);
		
	}
	

}
