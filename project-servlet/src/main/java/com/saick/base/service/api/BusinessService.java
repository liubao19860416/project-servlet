package com.saick.base.service.api;

import com.saick.base.service.PrivilegeInfo;


public interface BusinessService {

	@PrivilegeInfo(value = "添加方法")
	public void add();

	@PrivilegeInfo(value = "删除方法")
	public void delete();

	@PrivilegeInfo(value = "更新方法")
	public void update();

	@PrivilegeInfo(value = "查询方法")
	public void query();

}
