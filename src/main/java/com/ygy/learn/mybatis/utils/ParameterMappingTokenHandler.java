package com.ygy.learn.mybatis.utils;


import com.ygy.learn.mybatis.entity.ParameterMapping;

import java.util.ArrayList;
import java.util.List;

public class ParameterMappingTokenHandler implements TokenHandler {
	private List<ParameterMapping> parameterMappings = new ArrayList<>();

	// context是参数名称
	@Override
	public String handleToken(String content) {
		parameterMappings.add(buildParameterMapping(content));
		return "?";
	}

	private ParameterMapping buildParameterMapping(String content) {
		return new ParameterMapping(content);
	}

	public List<ParameterMapping> getParameterMappings() {
		return parameterMappings;
	}

	public void setParameterMappings(List<ParameterMapping> parameterMappings) {
		this.parameterMappings = parameterMappings;
	}

}
