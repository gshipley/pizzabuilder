package com.redhat.ai.pizzabuilder.helpers;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.Exec;
import io.kubernetes.client.util.Config;
import io.kubernetes.client.openapi.ApiException;
import java.io.IOException;


public class PlatformObjectHelper {

    private ApiClient client;
    private CoreV1Api api;

    public PlatformObjectHelper() {
        try {
            // Let's establish a connection to the API server
            client = Config.defaultClient();

            Configuration.setDefaultApiClient(client);

            // the CoreV1Api loads default api-client from global configuration.
            api = new CoreV1Api();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void executeCommand(String podName, String command, String hardware, String model, String database,
            String chatclient) throws ApiException, IOException {
        Exec exec = new Exec(client);
        exec.exec("pizzabuilder", podName, new String[] { "sh", "-c", command }, true);
    }

    public static String getCurrentPodName() throws IOException, ApiException {
        // Get the hostname of the current container
        String hostname = System.getenv("HOSTNAME");
        if (hostname == null) {
            throw new RuntimeException("HOSTNAME environment variable not set");
        }
        return hostname;
    }

    public static String getCurrentNamespace() throws IOException, ApiException {
        String namespace = System.getenv("OPENSHIFT_BUILD_NAMESPACE");
        if (namespace == null) {
            throw new RuntimeException("NAMESPACE environment variable not set");
        }
        return namespace;
    }

}