package com.example.dianyinggoupiao_springboot.util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 暂时不使用
 */
public class RefreshStaticResources {
    private static final String RESOURCE_DIRECTORY = "classpath:static/";
    private static final Map<String, String> staticResources = new ConcurrentHashMap<>();

    public static String getResourceContent(String resourceName) {
        if (!staticResources.containsKey(resourceName)) {
            loadStaticResource(resourceName);
        }
        return staticResources.get(resourceName);
    }

    /**
     * 加载资源到staticResources中
     * @param resourceName 资源所在的路径和名称
     */
    private static void loadStaticResource(String resourceName) {
        try {
            Path resourcePath = Paths.get(RESOURCE_DIRECTORY + resourceName);
            String content = new String(Files.readAllBytes(resourcePath));
            staticResources.put(resourceName, content);
        } catch (Exception e) {
            System.err.println("无法加载资源: " + resourceName);
            e.printStackTrace();
        }
    }

    /**
     * 释放资源
     * @param resourceName 能唯一标识到要操作的资源即可。
     */
    public static void releaseResource(String resourceName) {
        staticResources.remove(resourceName);
        System.out.println("已释放的资源: " + resourceName);
    }

    public static void main(String[] args) {
        // 在需要使用资源时加载
        String avatarContent = getResourceContent("avatar1.jpg");
        System.out.println("Avatar content: " + avatarContent);

        // 当资源不再需要时释放内存
        releaseResource("avatar1.jpg");

        // 可以在其他地方继续使用 getResourceContent 和 releaseResource 方法管理资源
    }
}



