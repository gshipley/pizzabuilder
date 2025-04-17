package com.redhat.ai.pizzabuilder.controllers;

import java.io.IOException;
import com.redhat.ai.pizzabuilder.helpers.PlatformObjectHelper;
import io.kubernetes.client.openapi.ApiException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*")
@RestController
public class APIController {


	@RequestMapping("/egg")
	public String easterEgg() {
		System.out.println("Easter egg requested");
		try {
			return "Every app needs an easter egg!! With pod name: " + PlatformObjectHelper.getCurrentPodName() + " and namespace: " + PlatformObjectHelper.getCurrentNamespace();
		} catch (IOException | ApiException e) {
			e.printStackTrace();
			return "Error: " + e.getMessage();
		}

	}

	@CrossOrigin
	@RequestMapping("/deploy")
	public void deployChart(@RequestParam(value = "hardware") String hardware, 
							@RequestParam(value = "model") String model, 
							@RequestParam(value = "database") String database,
							@RequestParam(value = "chatclient") String chatclient) throws ApiException, IOException {
		PlatformObjectHelper helper = new PlatformObjectHelper();
		helper.executeCommand("pizzabuilder", "helm install", hardware, model, database, chatclient);
	}
} 