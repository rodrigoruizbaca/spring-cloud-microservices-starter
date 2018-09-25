package com.easyrun.lambda.function;

import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Component;
@Component("s3Function")
public class S3HandlerFunction implements Function<Map<String, Object>, Map<String, Object>>{

	@Override
	public Map<String, Object> apply(Map<String, Object> t) {
		return t;
	}

	

}
