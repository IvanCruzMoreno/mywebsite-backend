package com.ivanmoreno.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ivanmoreno.entities.Email;
import com.ivanmoreno.entities.GitHubProject;
import com.ivanmoreno.services.EmailService;
import com.ivanmoreno.services.GitHubService;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping(value = "/api")
public class GitHubController {
	
	@Autowired
	private GitHubService ghService;
	
	@Autowired
	private EmailService emailService;
	
	@GetMapping("/repos/description")
	public List<GitHubProject> getReposWithDes(){
        return ghService.getAllReposWithDescription();
	}
	
	@GetMapping("/repos")
	public  List<GitHubProject> getRepos(){
        return ghService.getRepos();
	}
	
	@PostMapping("/email")
	public Email sentEmail(@RequestBody Email email) {
		try {
			if(emailService.sendEmail(email) == false) {
				return null;
			}
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return email;
	}
}
