package com.ivanmoreno.services;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ivanmoreno.entities.GitHubProject;

@Service
public class GitHubService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	private static String URL = "https://api.github.com/users/IvanCruzMoreno/repos";
	
	private List<LinkedHashMap<String,?>> getAllRepos(){
		return restTemplate.getForObject(URL, List.class);
	}
	
	public static GitHubProject insertDataIntoGitHubProj(LinkedHashMap<String,?> repo) {
		GitHubProject gh = new GitHubProject();
		gh.setId((Integer) repo.get("id"));
		gh.setName((String) repo.get("name"));
		gh.setHtml_url((String) repo.get("html_url"));
		gh.setLanguage((String) repo.get("language"));
		gh.setDescription((String) repo.get("description"));
		gh.setCreated_at((String) repo.get("created_at"));
		gh.setSize((Integer) repo.get("size"));
		return gh;
	}
	
	public List<GitHubProject> getRepos(){
		List<LinkedHashMap<String,?>> repos = getAllRepos();
		
		List<GitHubProject> allRepos = repos.stream()
				.map(GitHubService::insertDataIntoGitHubProj)
				.collect(Collectors.toList());
		
		return allRepos;
	}
	
	public List<GitHubProject>  getAllReposWithDescription(){
		
		List<LinkedHashMap<String,?>> repos = getAllRepos();
		
		List<GitHubProject> reposWithDescription = repos.stream()
				.filter( repo -> repo.get("description")!=null)
				.map(GitHubService::insertDataIntoGitHubProj)
				.collect(Collectors.toList());
		
		return reposWithDescription;
	}
	
	
}
