package com.easyrun.commons.utils;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.easyrun.commons.dto.FilterDto;
import com.easyrun.commons.exception.BadFilterException;
import com.google.common.collect.Lists;

@Component
public class Utilities {
	
	public List<FilterDto> parseFilter(Map<String, String> map) throws BadFilterException {
		List<FilterDto> filters = Lists.newArrayList();
		map.keySet().stream().forEach(key -> {
			String[] arr = map.get(key).split(":");
			if (arr.length == 2) {
				FilterDto filter = FilterDto.builder().name(key).oper(arr[0]).value(arr[1]).build();
				filters.add(filter);
			} 
		});
		return filters;
		
		
	}
		
}
