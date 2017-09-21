package com.yonyou.live.broadcast.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.live.broadcast.sdk.model.LiveAuthEntity;

@Repository
public interface LiveAuthDao {

	LiveAuthEntity getAuthByappId(Map<String,Object> paramMap);
}
