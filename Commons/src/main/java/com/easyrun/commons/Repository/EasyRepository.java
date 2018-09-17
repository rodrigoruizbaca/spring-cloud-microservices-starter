package com.easyrun.commons.Repository;

public interface EasyRepository<ENTITY, KEY> {
	ENTITY getByUniqueKey(KEY key);
}
