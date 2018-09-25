package com.easyrun.commons.dto;

public interface EasyDto<KEY, ID> {
	public KEY geUniqueKey();
	public ID getId();
}
