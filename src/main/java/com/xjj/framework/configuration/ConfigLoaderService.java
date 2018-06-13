package com.xjj.framework.configuration;

import java.util.Map;

public interface ConfigLoaderService {
	public Map<String,String> getAllConfig();
	public void saveAllConfig(Map<String,String> configs);
}
